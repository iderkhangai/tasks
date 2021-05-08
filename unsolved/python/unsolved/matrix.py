import re

matrix = list()
n=input("Enter your input: \n")
for _ in range(int(n.split()[0])):
    matrix.append(list(input()))

# Rotating the matrix
matrix = list(zip(*matrix))

# Prep regex sample
sample = str()
for subset in matrix:
    for letter in subset:
        sample += letter

# Substitute invalid characters with a space
print("\n Output :" + re.sub(r'(?<=\w)([^\w\d]+)(?=\w)', ' ', sample))