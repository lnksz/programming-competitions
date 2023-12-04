distinct = 0
linecount = 0

with open("input") as f:
	for line in f.readlines():
		linecount += 1
		def1, def2 = line.split(',')
		min1, max1 = map(int, def1.split('-'))
		min2, max2 = map(int, def2.split('-'))
		if (max1 < min2) or (max2 < min1):
				distinct += 1
print(linecount - distinct)
