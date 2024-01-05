import { getLines } from './lib.ts';

function getDist(a: number, x: number): number {
  // f(x) = ax - a^2
  return a * x - a * a;
  // f'(a) = x - 2a
  // f'(a) = 0, a = x/2
}

function getWinCount(t: number, s_lim: number) {
  const t_max = t / 2;
  const s_max = t_max * t - t_max * t_max;
  // s = a * t - a^2
  // a = (t +- sqrt(t^2 - 4s)) / 2)
  if (s_lim >= s_max)
    return 0;

  const sq = Math.sqrt(t * t - 4 * s_lim);
  const t1 = (t - sq) / 2;
  const t2 = (t + sq) / 2;
  return Math.ceil(t2) - Math.floor(t1) - 1;
}

export async function d6a(filename: string = 'input') {
  const lines = await getLines(filename);
  // console.log(lines);
  const times = lines[0].match(/(\d+)/g).map(Number);
  const dists = lines[1].match(/(\d+)/g).map(Number);
  // console.log(times, dists);
  const wins = new Array<number>(times.length).fill(0);
  for (const [i, time] of times.entries()) {
    const record = dists[i];

    for (let charge = 1; charge < time; charge++) {
      const dist = getDist(charge, time);
      if (dist > record) {
        // console.log(`${charge} : ${dist} > ${record}`);
        wins[i] += 1;
      }
    }
  }
  // console.log(wins);
  return wins.reduce((a, b) => a * b, 1);;
}


export async function d6b(filename: string = 'input') {
  const lines = await getLines(filename);
  // console.log(lines);
  const time = parseInt(lines[0].match(/(\d+)/g).join(''));
  const record = parseInt(lines[1].match(/(\d+)/g).join(''));
  // console.log(time, record);
  return getWinCount(time, record);
  // let wins = 0;
  // for (let charge = 1; charge < time; charge++) {
  //   const dist = getDist(charge, time);
  //   if (dist > record) {
  //     // console.log(`${charge} : ${dist} > ${record}`);
  //     wins += 1;
  //   }
  // }
  // // console.log(wins);
  // return wins;
}