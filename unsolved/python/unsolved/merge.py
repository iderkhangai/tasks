from collections import OrderedDict

def merge(input, k):

    strlen = len(input)
    for i in range(0,strlen,k):
        print(''.join(OrderedDict.fromkeys(input[i:i + k])))

if __name__ == '__main__':
    input, k = input("Enter Your Input: "), int(input("Enter the denoting length: "))
    merge(input, k)