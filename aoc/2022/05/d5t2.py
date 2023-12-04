def parse_cmd(line):
	s = line.split()
	return map(int, [s[5], s[3], s[1]])

stacks = [
	'',
	'BLDTWCFM',
	'NBL',
	'JCHTLV',
	'SPJW',
	'ZSCFTLR',
	'WDGBHNZ',
	'FMSPVGCN',
	'WQRJFVCZ',
	'RPMLH',
	]

with open('input') as f:
	lines = f.readlines()

cmds = map(parse_cmd, lines[10:])
print(stacks)
for dst, src, lng in cmds:
	trans = stacks[src][-lng:]
	stacks[dst] += str(trans)
	stacks[src] = stacks[src][:-lng]
print(stacks)

res = ''

print(list(map(lambda s: s[-1], stacks[1:])))
