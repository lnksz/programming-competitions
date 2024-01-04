function numsort(a: number, b: number) {
  return a - b;
}

async function d5a(filename: string = 'input5') {
  const input = await Bun.file(filename).text();
  const lines = input.split('\n');
  let lidx = 0;
  const seeds = lines[lidx++].match!(/\d+/g).map(Number);

  let seed2soil: Array<Array<number>> = [];
  let soil2fert: Array<Array<number>> = [];
  let fert2water: Array<Array<number>> = [];
  let water2light: Array<Array<number>> = [];
  let light2temp: Array<Array<number>> = [];
  let temp2humi: Array<Array<number>> = [];
  let humi2loc: Array<Array<number>> = [];
  let tmpmap = seed2soil;

  for (; lidx < lines.length; lidx++) {
    const line = lines[lidx];
    if (line.length === 0 || line.startsWith('seed'))
      continue;
    if (line.startsWith('soil')) {
      tmpmap = soil2fert;
      continue;
    } else if (line.startsWith('fert')) {
      tmpmap = fert2water;
      continue;
    } else if (line.startsWith('water')) {
      tmpmap = water2light;
      continue;
    } else if (line.startsWith('light')) {
      tmpmap = light2temp;
      continue;
    } else if (line.startsWith('temp')) {
      tmpmap = temp2humi;
      continue;
    } else if (line.startsWith('humi')) {
      tmpmap = humi2loc;
      continue;
    } else if (line.startsWith('loc')) {
      tmpmap = seed2soil;
      continue;
    }

    tmpmap.push(line.match!(/\d+/g).map(Number));
  }
  seed2soil.sort();
  soil2fert.sort();
  fert2water.sort();
  water2light.sort();
  light2temp.sort();
  temp2humi.sort();
  humi2loc.sort();
  let locs: number[] = [];
  locs = seeds.map((seed: number) => {
    return findIn2DArray(humi2loc,
      findIn2DArray(temp2humi,
        findIn2DArray(light2temp,
          findIn2DArray(water2light,
            findIn2DArray(fert2water,
              findIn2DArray(soil2fert,
                findIn2DArray(seed2soil, seed)))))));
  });
  return Math.min(...locs);
}

function bsearch(arr: Array<Array<number>>, x: number): number {
  let lo = 0;
  let hi = arr.length - 1;
  while (lo <= hi) {
    const mid = Math.floor((lo + hi) / 2);
    const [dst, src, n] = arr[mid];
    if (src <= x && x < (src + n))
      return dst + (x - src);
    else if (src + n <= x)
      lo = mid + 1;
    else
      hi = mid - 1;
  }
  return x;
}
function findIn2DArray(map: Array<Array<number>>, x: number) {
  const mapper = map.find((m) => {
    const [_, src, n] = m;
    return (src <= x && x < (src + n));
  });

  if (mapper === undefined)
    return x;

  const [dst, src, _] = mapper;
  return dst + (x - src);
}

function* seedGen(seedRanges: number[]) {
  for (let s = 0; s < seedRanges.length; s += 2) {
    const [start, range] = seedRanges.slice(s, s + 2);
    for (let i = start; i < (start + range); i++) {
      yield i;
    }
  }
  yield Infinity;
}

function getLowestSeedInEachRange(seeds: Array<[number, number]>, breaks: number[]) {
  const seedBreaks = [];
  for (let i = 0; i < seeds.length; i += 2) {
    const [start, range] = seeds.slice(i, i + 2);
    const end = start + range;
    seedBreaks.push([start, end]);
  }

  // console.log(seedBreaks);
  let lowests = [];
  for (const [start, end] of seedBreaks) {
    // console.log(`start: ${start}, end: ${end}`);;
    const breaksInRange = breaks.filter((b) => (b > start && b < end));
    if (breaksInRange.length > 0) {
      lowests.push(...breaksInRange);
    }
    lowests.push(start);
    // console.log(breaksInRange);
  }
  return lowests;
}

function seed2location(o: Object, s: number) {
  const loc =
    bsearch(o.humi2loc,
      bsearch(o.temp2humi,
        bsearch(o.light2temp,
          bsearch(o.water2light,
            bsearch(o.fert2water,
              bsearch(o.soil2fert,
                bsearch(o.seed2soil, s)))))));
  return loc;
}

async function d5b(filename: string = 'input5') {
  const input = await Bun.file(filename).text();
  const lines = input.split('\n');
  let lidx = 0;
  const seedRanges = lines[lidx++].match(/\d+/g).map(Number);
  let seed2soil: Array<Array<number>> = [];
  let soil2fert: Array<Array<number>> = [];
  let fert2water: Array<Array<number>> = [];
  let water2light: Array<Array<number>> = [];
  let light2temp: Array<Array<number>> = [];
  let temp2humi: Array<Array<number>> = [];
  let humi2loc: Array<Array<number>> = [];
  let tmpmap = seed2soil;

  for (; lidx < lines.length; lidx++) {
    const line = lines[lidx];
    if (line.length === 0 || line.startsWith('seed'))
      continue;
    if (line.startsWith('soil')) {
      tmpmap = soil2fert;
      continue;
    } else if (line.startsWith('fert')) {
      tmpmap = fert2water;
      continue;
    } else if (line.startsWith('water')) {
      tmpmap = water2light;
      continue;
    } else if (line.startsWith('light')) {
      tmpmap = light2temp;
      continue;
    } else if (line.startsWith('temp')) {
      tmpmap = temp2humi;
      continue;
    } else if (line.startsWith('humi')) {
      tmpmap = humi2loc;
      continue;
    } else if (line.startsWith('loc')) {
      tmpmap = seed2soil;
      continue;
    }

    tmpmap.push(line!.match(/\d+/g).map(Number));
  }
  const sorter = (a: number[], b: number[]) => a[1] - b[1];
  const mapperObj = {
    seed2soil: seed2soil,
    soil2fert: soil2fert,
    fert2water: fert2water,
    water2light: water2light,
    light2temp: light2temp,
    temp2humi: temp2humi,
    humi2loc: humi2loc,
  };
  for (let m of Object.values(mapperObj)) {
    m.sort(sorter);
  }
  let maps = seed2soil.concat(
    soil2fert, fert2water,
    water2light, light2temp,
    temp2humi, humi2loc);
  maps.sort(sorter);
  const brs = new Set(maps.map(
    (m) => [m[0], m[1], m[1] + m[2], m[0] + m[2]])
    .flat()
    .sort(numsort));

  let minLoc = Infinity;
  // include neighbors of breaks
  const brksp = new Set(Array.from(brs)
    .map((b) => [b - 1, b, b + 1])
    .flat()
    .filter(b => b > 0)
    .sort(numsort));
  for (const sed of getLowestSeedInEachRange(seedRanges, Array.from(brksp))) {
    let loc = seed2location(mapperObj, sed);
    // console.log(`seed ${sed} -> ${loc}`);
    if (loc < minLoc)
      minLoc = loc;
  }
  let diff = seed2location(mapperObj, 55) - 55;
  for (let s = 56; s < 93; s++) {
    let loc = seed2location(mapperObj, s);
    let d = loc - s;
    if (d !== diff) {
      diff = d;
    }
  }
  return minLoc;
}

export {
  d5a, d5b,
  bsearch, findIn2DArray,
  seedGen
};