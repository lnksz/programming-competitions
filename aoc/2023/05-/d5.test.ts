import { expect, test } from "bun:test";
import { d5a, d5b, bsearch, findIn2DArray } from "./d5.ts";

test('d5 binary search', () => {
  const ranges = [
       [30, 9, 1], [0, 4, 4], [4, 0, 2],
  ];
  ranges.sort((a, b) => a[1] - b[1]);
  const T = [
    [0, 4], [1, 5], [2, 2], [3, 3], [4, 0],
    [5, 1], [6, 2], [7, 3], [8, 8], [9, 30],
    [20, 20]
  ];
  for (const [src, dst] of T) {
    expect(bsearch(ranges, src)).toBe(dst);
    expect(findIn2DArray(ranges, src)).toBe(dst);
  }
});

test('d5b_sample', async () => {
  const res = await d5b('sample5');
  expect(res).toEqual(46);
});

test('d5a sample', async () => {
  const res = await d5a('sample5');
  expect(res).toEqual(35);
});

test('d5a input', async () => {
  const res = await d5a();
  expect(res).toEqual(175622908);
});

test('d5b input', async () => {
  const res = await d5b();
  expect(res).toEqual(5200543);
});