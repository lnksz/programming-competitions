import { expect, test } from "bun:test";
import { d6a, d6b } from "./d6.ts";


test('d6a_sample', async () => {
  const res = await d6a('sample6');
  expect(res).toEqual(288);
});

test('d6a_input', async () => {
  const res = await d6a('input6');
  expect(res).toEqual(345015);
});

test('d6b_sample', async () => {
  const res = await d6b('sample6');
  expect(res).toEqual(71503);
});

test('d6b_input', async () => {
  const res = await d6b('input6');
  expect(res).toEqual(42588603);
});