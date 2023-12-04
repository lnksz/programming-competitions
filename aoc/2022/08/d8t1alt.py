import pandas as pd
import numpy as np

ninput = 99
npeek = 12

df = pd.read_fwf('input',
	widths=(1 for _ in range(ninput)),
	header=None,
	dtype=np.uint8,
	index_col=None)

mm = pd.DataFrame(np.zeros((ninput, ninput), dtype=np.uint8),
	dtype=np.uint8)

print(df.iloc[0:npeek, 0:npeek])

def mark_visible(ds, ms, is_rev=False):
	tallest = -1
	seq = ds.iloc[::-1] if is_rev else ds

	for i, t in seq.items():
		if t > tallest:
			ms.iat[i] = 1
			tallest = t
	

for (_, ds), (_, ms) in zip(df.items(),
					mm.items()):
	mark_visible(ds, ms)
	mark_visible(ds, ms, True)
	
for (_, ds), (_, ms) in zip(df.iterrows(), 
					mm.iterrows()):
	mark_visible(ds, ms)
	mark_visible(ds, ms, True)

print(mm.iloc[0:npeek, 0:npeek])
print(mm.sum().sum())