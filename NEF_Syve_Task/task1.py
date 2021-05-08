def read_list_from_file(name,has_header = True):
    file = open(name,"r")
    filetext = file.read()
    stored_list = filetext.splitlines()
    if(has_header):
      stored_list.pop(0)
    stored_list.sort()
    for i in range(len(stored_list)):
      stored_list[i] = stored_list[i].split()
    return stored_list

def format_text(txt,column_length):
  while(len(txt) < column_length):
    txt += " "
  return(txt)

def run():
  filetextarray = read_list_from_file("input1.txt")
  #used in task 3
  pods_list = [[filetextarray[0]]]
  j=0
  maxCpu = filetextarray[0]
  maxMem = filetextarray[0]
  maxPods = [["NAME_OF_POD","MAX_CPU","MAX_MEM"]]
  for i in range(len(filetextarray)-1):

    prev = filetextarray[i]
    curr = filetextarray[i+1]
    
    if curr[0] == prev[0]:
      pods_list[j].append(curr)
      #check max vals
      if int(curr[1]) > int(maxCpu[1]):
        maxCpu = curr
        #print("maxCpu changed",maxCpu)
      if int(curr[2]) > int(maxMem[2]):
        maxMem = curr
        #print("maxMem changed",maxMem)
    else:
      pods_list.append([])
      j+=1
      pods_list[j].append(curr)
      maxPods.append([maxCpu[0],""+maxCpu[1],""+maxMem[2]])
      maxCpu = curr
      maxMem = curr 
  #append last pod
  maxPods.append([maxCpu[0],""+maxCpu[1],""+maxMem[2]])

  f = open("result1.txt", "w")


  for p in maxPods:
    f.write(format_text(p[0],16)+format_text(p[1],9)+format_text(p[2],9)+"\n")
  f.close()
  
  return pods_list

if __name__ == '__main__':
    run()