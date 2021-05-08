from task1 import read_list_from_file
from task1 import format_text

def run():
    input2_list = read_list_from_file("input2.txt", False)
    result1_list = read_list_from_file("result1.txt")

    #for t in input2_list:
    #    print(t)
    #print()

    #for t in result1_list:
    #    print(t)
    #print()

    pods_beyond_CPU = ["PODS_BEYOND_CPU"]
    pods_beyond_MEM = ["PODS_BEYOND_MEM"]
    pods_below_CPU = ["PODS_BELOW_CPU"]
    pods_below_MEM = ["PODS_BELOW_MEM"]

    #both lists are sorted so...
    #we check all input 2 pods if they match in result1.
    i = 0
    j = 0
    while i < len(input2_list):
        if input2_list[i][0] == result1_list[j][0]:
            if input2_list[i][1] > result1_list[j][1]:
                pods_beyond_CPU.append(input2_list[i][0])
            else:
                pods_below_CPU.append(input2_list[i][0])
            if input2_list[i][2] > result1_list[j][2]:
                pods_beyond_MEM.append(input2_list[i][0])
            else:
                pods_below_MEM.append(input2_list[i][0])
            i += 1
            j += 1
        else:
            if j < len(result1_list): j += 1
            else:
                print("reporting missing pod in result1.txt")
                break

               
    #for p in pods_beyond_CPU:
    #    print(p)
    #print()

    #for p in pods_beyond_MEM:
    #    print(p)
    #print()
    #for p in pods_below_CPU:
    #    print(p)
    #print()

    #for p in pods_below_MEM:
    #    print(p)
    #print()

    #write result
    f = open("result2.txt", "w")


    for i in range(len(input2_list)):
      line = ""
      if(i<len(pods_below_CPU)):
        line+=format_text(pods_below_CPU[i],20)
      else: line += format_text(" ",20)
      if(i<len(pods_below_MEM)):
        line+=format_text(pods_below_MEM[i],20)
      else: line += format_text(" ",20)
      if(i<len(pods_beyond_CPU)):
        line+=format_text(pods_beyond_CPU[i],20)
      else: line += format_text(" ",20)
      if(i<len(pods_beyond_MEM)):
        line+=format_text(pods_beyond_MEM[i],20)
      else: line += format_text(" ",20)
      f.write(line+"\n")
    f.close()
    
if __name__ == '__main__':
    run()
