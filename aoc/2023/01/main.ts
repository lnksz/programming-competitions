async function t1a(filename: string = 'input1.txt') {
    const file = Bun.file(filename);
    const input = await file.text();
    return input.trim().split('\n')
        .map((line: string) => {
            const nums = line.match(/\d/g)!;
            if (nums === null || nums === undefined)
                return 0;
            return parseInt(nums[0] + nums.pop());
        })
        .reduce((a: number, b: number) => a + b, 0)
}

async function t1b(filename: string = 'input1.txt') {
    //
    const numMap: { [key: string]: number } = {
        'one': 1,
        'two': 2,
        'three': 3,
        'four': 4,
        'five': 5,
        'six': 6,
        'seven': 7,
        'eight': 8,
        'nine': 9,
        '0': 0,
        '1': 1,
        '2': 2,
        '3': 3,
        '4': 4,
        '5': 5,
        '6': 6,
        '7': 7,
        '8': 8,
        '9': 9
    }
    const syms = [
        'one',
        'two',
        'three',
        'four',
        'five',
        'six',
        'seven',
        'eight',
        'nine',
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',]

    const findFirstNum = (line: string): number => {
        let minIdx = line.length;
        let minSym = '';
        syms.forEach((sym: string) => {
            const idx = line.indexOf(sym);
            if (idx > -1 && idx < minIdx) {
                minIdx = idx;
                minSym = sym;
            }
        });
        return numMap[minSym];
    };

    const findLastNum = (line: string): number => {
        let lastIdx = -1;
        let lastSym = '';
        syms.forEach((sym: string) => {
            const idx = line.lastIndexOf(sym);
            if (lastIdx < idx) {
                lastIdx = idx;
                lastSym = sym;
            }
        });
        return numMap[lastSym];
    };

    const file = Bun.file(filename);
    const input = await file.text();
    return input.trim().split('\n')
        .map((line: string) => {
            const firstNum = findFirstNum(line);
            const lastNum = findLastNum(line);
            const val = firstNum * 10 + lastNum;
            // console.log(line, firstNum, lastNum, val)
            return val;
        })
        .reduce((a: number, n: number) => a + n, 0)
}

console.log("t1a: ", await t1a());
console.log("t1b: ", await t1b());
export { };
