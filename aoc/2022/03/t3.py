from string import ascii_lowercase, ascii_uppercase

lut = ascii_lowercase + ascii_uppercase
res = 0

with open("input") as f:
	for line in f.readlines():
		half = len(line) // 2
		item = set(line[:half]) & set(line[half:])
		res += lut.find(*item) + 1

print(res)
