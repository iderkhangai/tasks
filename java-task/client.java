
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.lang.System.Logger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SelectableChannel;
import java.util.Scanner;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;

public class client {
    // Logger logger = Logger.getLogger(Class.class);
    public enum ResponseCodes {
        success(0), invalid(3), fault(4);

        private final int responseCode;

        ResponseCodes(final int NewResponseCode) {
            responseCode = NewResponseCode;
        }

        public int getResponseCode() {
            return responseCode;
        }
    }

    // variables
    private static String command;
    private static String value;
    private static String chost = null;
    private static String ip = null;
    private static String port = null;
    private static String message;
    private static Scanner scan = new Scanner(System.in);
    private static Boolean isValid = false;

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        try {
            System.out.print("Please enter Host: ");
            while (!isValid) {
                isValid = true;
                ip = scan.nextLine();
                if (ip.equals("")) {
                    InetAddress host = InetAddress.getLocalHost();
                    chost = host.getHostAddress();
                    System.out.print("Host name chosen default - " + chost + "\n");
                }
                if (!validate_IP(chost)) {
                    System.out.println("Ip is not valid, Try again..");
                    isValid = false;
                }

            }
            // System.out.println("1. You entered the Host: " + ip);
            isValid = false;
            System.out.println("Please enter the Port: ");
            while (!isValid) {
                isValid = true;
                port = scan.nextLine();
                System.out.println("2. You entered PORT: " + port);
                if (port.equals("")) {
                    port = "1234";
                    System.out.print("IP address chosen default by: " + port + "\n");
                }
                if (!validate_Port(port)) {
                    System.out.println("Port is not valid, Try again..");
                    isValid = false;
                }

            }

            System.out.println(" Socket Address to make Connection: " + chost + ":" + port + "\n");
            int res = Client();
            System.out.println("Response - " + res);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static int Client() throws UnknownHostException {

        try {

            while (true) {
                isValid = false;
                // establish socket connection to server
                System.out.println("\n************************");
                System.out.println("-'Exit' or 'e' to close connection to server");
                System.out.println("-'Add' or 'a'");
                System.out.println("-'Remove' or 'r'");
                System.out.println("-'Info' or 'i'");
                System.out.println("************************");
                // write to socket using ObjectOutputStream
                System.out.print("Your Choice: ");
                command = scan.nextLine();
                if (command.equals("e") || command.equals("exit")) {
                    command = "exit";
                    sendMessage(command);
                    System.exit(0);
                } else if (command.equals("a") || command.contains("add")) {
                    command = "add";
                    System.out.println(" Enter email and numbers between 1 and 100 (Example: a@a.hu 1 2  3 ...) \n");
                    value = null;
                    while (!isValid) {
                        isValid = true;
                        value = scan.nextLine();
                        String[] words = value.split("\\s+");
                        // Checking input string has valid length...
                        if (words.length < 2) {
                            System.out.println("Invalid format...");
                            System.out.println("Please try again...");
                            isValid = false;
                            continue;
                        }
                        for (int i = 0; i < words.length; i++) {

                            if (i == 0) {
                                isValid = isValid && (isValidEmailAddress(words[0]));
                                if (!isValid) {
                                    System.out.println("Please try again...");
                                    break;
                                } else {
                                    System.out.println("Valid Email...");
                                }
                            } else {
                                // Checking numbers...
                                isValid = isValid && isNumeric(words[i]);
                                if (!isValid) {
                                    System.out.println("Please try again...");
                                    break;
                                } else
                                    System.out.println("Valid number...");
                            }
                        }

                    }
                    message = command + " " + value;
                    if (isValid) {
                        if (sendMessage(message) != 0) {
                            // System.out.print(ResponseCodes.fault.getResponseCode());
                            return 3;
                        }
                    }
                } else if (command.equals("r") || command.equals("remove")) {
                    command = "remove";
                    value = null;
                    while (!isValid) {
                        System.out.print("Please enter email address or Natural Number: ");
                        value = scan.nextLine();
                        isValid = true;
                        if (isValidEmailAddress(value) || isNatural(value)) {
                            System.out.println("Valid Input");
                            message = command + " " + value;
                            if (sendMessage(message) != 0) {
                                // System.out.print("Response Code: " + ResponseCodes.fault.getResponseCode());
                                return 3;
                            }
                        } else {
                            System.out.println("Invalid Input, Please try again...");
                            isValid = false;
                        }
                    }

                } else if (command.equals("i") || command.equals("info")) {
                    command = "info";
                    System.out.println(
                            "Please enter one or more Email addresses or 'All' (Example: a@a.hu b@b.hu) or (All)");
                    while (!isValid) {
                        isValid = true;
                        value = scan.nextLine();
                        if (value.contains("@")) {
                            System.out.println("Emails");
                            String[] emails = value.split("\\s+");
                            for (int i = 0; i < emails.length; i++) {
                                if (!isValidEmailAddress(emails[i])) {
                                    System.err.println("Bad input format, Please try again...");
                                    isValid = false;
                                } else {
                                    System.out.println("Valid Email...");
                                    message = command + " " + value;
                                    if (sendMessage(message) != 0) {
                                        // System.out.print("Response Code: " + ResponseCodes.fault.getResponseCode());
                                        return 3;
                                    }
                                }

                            }
                        } else if (value.toLowerCase().equals("all")) {
                            System.out.println("String 'All' received");
                            message = command + " " + value;
                            if (sendMessage(message) != 0) {
                                // System.out.print("Response Code: " + ResponseCodes.fault.getResponseCode());
                                return 3;
                            }
                        } else {
                            System.err.println("Bad input format, Please try again...");
                            isValid = false;
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scan.close();
        }
        // System.out.println("Response Code: " +
        // ResponseCodes.invalid.getResponseCode());
        return 4;

    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);

        if (!m.matches())
            System.out.println("Invalid Email address");

        return m.matches();
    }

    public static boolean isNumeric(String string) {
        int intValue;
        System.out.println(String.format("Checking input number: \"%s\"", string));

        if (string == null || string.equals("")) {
            System.out.println("Empty value.");
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            if (intValue >= 1 && intValue <= 100)
                return true;
            else
                System.out.println("Please enter Natural numbers between 1 and 100 after email address.");
        } catch (NumberFormatException e) {
            System.err.println("Please enter Natural numbers between 1 and 100 after email address.");
        }
        return false;
    }

    public static boolean isNatural(String number) {
        System.out.println("Checking Natural Number");
        int val;
        try {
            val = Integer.parseInt(number);
            System.out.println("Checking Number: " + val);
            if (val > 0) {
                return true;
            }
            // is natural number
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println("Wrong Natural Number");
        return false;
    }

    public static boolean validate_IP(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }

    public static boolean validate_Port(final String port) {
        String PATTERN = "^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
        return port.matches(PATTERN);
    }

    public static int sendMessage(String msg) throws IOException {
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {

            socket = new Socket(chost, Integer.parseInt(port));
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending request to Socket Server: " + command);
            oos.writeObject(command);
            ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Server Response : " + message);
            if (message != null) {
                System.out.println("Successfull Termination ");
                System.out.println("Response code:" + ResponseCodes.success.getResponseCode());
                return 0;

            } else {
                System.err.println("Server response does not match!");
                // System.err.println("Response Code: " +
                // ResponseCodes.fault.getResponseCode());
            }

            return 3;
        } catch (Exception e) {
            System.err.println("[Server Error] " + e.getMessage());
            socket.close();
            System.out.println("Closing connection with server");
            // System.err.println("Response Code: " +
            // ResponseCodes.fault.getResponseCode());
            return 3;
        }

    }

}
