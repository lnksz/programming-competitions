from pathlib import PurePosixPath
from os.path import abspath
from functools import reduce

class Dir:
	def __init__(self, p):
		self.path = p
		self.files = []
		self.dirs = []
	def __repr__(self):
		return f'{self.path}/'
	def addf(self, fil):
		self.files.append(fil)
	def addd(self, di):
		self.dirs.append(di)
	def ls(self):
		return (f'ls: {self.path}\n' +
		'\n'.join(map(str,
			self.dirs + self.files)))
	def du(self):
		return reduce(lambda a,b: a+b,
		[f.size for f in self.files], 0)
	def dua(self):
		s = self.du()
		for di in self.dirs:
			if not di in fs:
				continue
			s += fs[di].dua()
		return s	

class File:
	def __init__(self, n, s):
		self.name = n
		self.size = int(s)
	def __repr__(self):
		return f'{self.name} {self.size}'


with open('input') as f:
	lines = f.readlines()

cmd = ''
pwd = PurePosixPath('/')
awd = ''
fs = {}
for line in lines:
	if line.startswith('$ '):
		cmd = line[2:4]
		if cmd == 'cd':
			pwd /= PurePosixPath(line.split()[2])
			awd = abspath(pwd)
			if awd not in fs:
				fs[awd] = Dir(awd)
		print(cmd, awd)
		continue
	
	stat, name = line.split()
	print(f'in {awd} {name} {stat}')
	if stat == 'dir':
		p = abspath(pwd / PurePosixPath(name))
		fs[awd].addd(p)
		continue
	fs[awd].addf(File(name, stat))

total = 70000000
needed = 30000000
used = fs['/'].dua()
avail = total - used
tofree = needed - avail

res = total
for d in fs:
	dua = fs[d].dua()
	if tofree <= dua and dua < res:
		print(f'{d}: {dua}')
		res = dua		
print(used, avail, tofree, res)