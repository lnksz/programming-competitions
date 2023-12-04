res = 0

with open("input") as f:
	for line in f.readlines():
		def1, def2 = line.split(',')
		l1, r1 = map(int, def1.split('-'))
		l2, r2 = map(int, def2.split('-'))
		if (l1 <= l2 and r2 <= r1) or (l2 <= l1 and r1 <= r2):
			res += 1

print(res)
