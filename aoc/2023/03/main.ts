function hasSymAround(pLine: string, line: string, nLine: string, start: number, end: number): boolean {
  const ll = line.length;
  const prev = start ? start - 1 : 0;
  const next = (end < ll-1) ? end + 1 : ll - 1;
  const above = pLine.slice(prev, next + 1);
  const below = nLine.slice(prev, next + 1);
  const border = line[prev] + line[next] + above + below;
  return /[^0-9.]/.test(border);
}

async function d3a(filename: string = 'input') {
  const map = await Bun.file(filename).text();
  let lines = map.split('\n');
  // Duplicate the first and last lines to circumvent edgecases in loop
  lines.unshift(lines[0]);
  lines.push(lines.at(-1));
  const reg = /\d+/dg;
  let sum = 0;
  for (let i = 1; i < lines.length - 1; i++) {
    const line = lines[i];
    const lineP = lines[i - 1];
    const lineN = lines[i + 1];
    const m = [...line.matchAll(reg)];
    m.forEach(n => {
      const numStr = n[0];
      const start = n.index!;
      const end = start + numStr.length - 1;
      const hasSym = hasSymAround(lineP, line, lineN, start, end);
      if (hasSym)
        sum += parseInt(numStr);
    });
  }
  return sum;
}

function pushOrCreate(starMap: {[key: string]: number[]}, key: string, value: number) {
  if (key in starMap) {
    starMap[key].push(value);
  } else {
    starMap[key] = [value];
  }
}

function addStarsAroundNum(lines: string[], lineIdx: number, start: number, end: number, num: number, starMap: {[key: string]: number[]}) {
  const pLine = lines[lineIdx - 1];
  const line = lines[lineIdx];
  const nLine = lines[lineIdx + 1];
  const ll = line.length;
  const prev = start ? start - 1 : 0;
  const next = (end < ll-1) ? end + 1 : ll - 1;
  if (line[prev] === '*') {
    const sKey = `${lineIdx},${prev}`;
    pushOrCreate(starMap, sKey, num);
  }
  if (line[next] === '*') {
    const sKey = `${lineIdx},${next}`;
    pushOrCreate(starMap, sKey, num);
  }
  
  for (let i = prev; i < next + 1; i++) {
    const aboveChar = pLine[i];
    if (aboveChar === '*') {
      const sKey = `${lineIdx - 1},${i}`;
      pushOrCreate(starMap, sKey, num);
    }
    const belowChar = nLine[i];
    if (belowChar === '*') {
      const sKey = `${lineIdx + 1},${i}`;
      pushOrCreate(starMap, sKey, num);
    }
  }
}

async function d3b(filename: string = 'input') {
  let starMap: {[key: string]: number[]} = {};
  const map = await Bun.file(filename).text();
  let lines = map.split('\n');
  lines.unshift(lines[0]);
  lines.push(lines.at(-1));
  const reg = /\d+/dg;

  for (let i = 1; i < lines.length - 1; i++) {
    const line = lines[i];
    const m = [...line.matchAll(reg)];
    m.forEach(n => {
        const numStr = n[0];
        const start = n.index!;
        const end = start + numStr.length - 1;
        addStarsAroundNum(lines, i, start, end, parseInt(numStr), starMap);
    });
  }
  return Object.values(starMap).reduce((acc, cur) => {
    if (cur.length === 2) {
      acc += cur[0] * cur[1];
    }
    return acc;
  }, 0);
}

console.log('d3a: ', await d3a());
console.log('d3b: ', await d3b());