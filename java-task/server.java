import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements java Socket server
 * 
 * @author iderkhangai Amarkhuu
 *
 */
public class server {

    // static ServerSocket variable
    private static ServerSocket server;
    // socket server port on which it will listen
    private static int port = 1234;

    // Logger logger = Logger.getLogger(Server.class);
    public static void main(String args[]) throws IOException, ClassNotFoundException {

        while (true) {
            // create the socket server object
            server = new ServerSocket(port);
            // keep listens indefinitely until receives 'exit' call or program terminates
            System.out.println("Server is running on port: " + port + ", Waiting for Client Request");
            // creating socket and waiting for client connection
            Socket socket = server.accept();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            // convert ObjectInputStream object to String
            String message = (String) ois.readObject();
            System.out.println("Message Received from Client: " + message);
            // create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // write object to Socket
            // if (message.contains(""))
            String requestMessage[] = message.split("\\s+");
            System.out.println("Command from Client: " + requestMessage[0]);
            try {
                switch (requestMessage[0].toLowerCase()) {
                    case "add":
                        oos.writeObject("OK");
                        // break;
                    case "remove":
                        oos.writeObject("200");
                        // break;
                    case "info":
                        String naturalNumbers = "";
                        int RandomNumbers = 1000;
                        for (int i = 0; i < RandomNumbers; i++) {
                            int randomNumber = (int) Math.floor((Math.random() * 20) + 1);
                            naturalNumbers += randomNumber + " ";
                        }

                        oos.writeObject(naturalNumbers);
                        System.out.println("info");
                        // break;
                    case "exit":
                        System.out.println("exit");
                        break;
                    default:
                        System.out.println("no match");
                        // break;
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.err.println("[500] Error Occured - " + e.getMessage());
            }
            // close resources
            System.out.println("Connection Closing..");
            if (ois != null) {
                ois.close();
                System.out.println(" Socket Input Stream Closed");
            }

            if (oos != null) {
                oos.close();
                System.out.println("Socket Out Closed");
            }
            if (socket != null) {
                socket.close();
                System.out.println("Socket Closed");
            }
            System.out.println("Shutting down Socket server!!");
            server.close();

        }

    }
}