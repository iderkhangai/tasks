import matplotlib.pyplot as plt
import numpy as np
import task1
plt.style.use('seaborn-whitegrid')
def plot_pods(input1_pods_list):
  print(input1_pods_list)
  for pl in input1_pods_list:
    plt.figure()
    x = np.array([int(row[1]) for row in pl])
    y = np.array([int(row[2]) for row in pl])
    print(x)
    print(y)
    plt.title(pl[0][0])
    plt.xlabel("CPU")
    plt.ylabel("MEM")
    plt.plot(x, y, '-ok');
    plt.savefig(pl[0][0]+'.png')
    plt.close()


input1 = task1.run()
if __name__ == '__main__':
    plot_pods(input1)