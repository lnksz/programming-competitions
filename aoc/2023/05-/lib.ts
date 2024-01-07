export async function getLines(filename: string = 'input') {
  const input = await Bun.file(filename).text();
  return input.split('\n');
}