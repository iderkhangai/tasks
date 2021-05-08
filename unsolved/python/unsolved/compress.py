from itertools import groupby


n=input("Enter your input: ")
for key, group in groupby(n):
	a= len(list(group)), int(key)
	print(tuple(a), end=" ")