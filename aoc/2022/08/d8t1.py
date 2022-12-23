import pandas as pd
import numpy as np

ninput = 99
npeek = 12

df = pd.read_fwf('input',
	widths=(1 for _ in range(ninput)),
	header=None,
	dtype=np.uint8,
	index_col=None)

m = pd.DataFrame(
	np.zeros((ninput, ninput),
		dtype=np.uint8),
	dtype=np.uint8)
# Edges
m.iloc[0] = 1
m.iloc[-1] = 1
m.iloc[:, 0] = 1
m.iloc[:, -1] = 1

print(df.iloc[0:npeek, 0:npeek])

for cid, col in df.items():
	tallest = col[0]
	for rid, t in col[1:].items():
		if t > tallest:
			m.at[rid, cid] |= 1
			tallest = t
	tallest = col.iloc[-1]
	for rid, t in col[::-1].items():
		if t > tallest:
			m.at[rid, cid] |= 1
			tallest = t
for rid, row in df.iterrows():
	tallest = row[0]
	for cid, t in row[1:].items():
		if t > tallest:
			m.at[rid, cid] |= 1
			tallest = t
	tallest = row.iloc[-1]
	for cid, t in row[::-1].items():
		if t > tallest:
			m.at[rid, cid] |= 1
			tallest = t

print(m.iloc[0:npeek, 0:npeek])
print(m.sum().sum())