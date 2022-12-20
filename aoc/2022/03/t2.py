#!/bin/python
from string import ascii_lowercase, ascii_uppercase
from more_itertools import ichunked

lut = ascii_lowercase + ascii_uppercase
res = 0
inp = ''
with open("input") as f:
    inp = f.read()

for (l1, l2, l3) in ichunked(inp.strip().split('\n'), 3):
    item = set(l1) & set(l2) & set(l3)
    res += lut.find(*item) + 1

print(res)
