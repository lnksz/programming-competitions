async function d4a(filename: string = 'input') {
    const input = await Bun.file(filename).text();
    const lines = input.split('\n');

    return lines.reduce((acc: number, line: string) => {
        let [_, wins, nums] = line.split(/:|\|/g)
            .map(s => s.match(/[0-9]+/g)?.map(Number));
        const winS = new Set(wins);
        const numS = new Set(nums);
        const winNums = winS.intersection(numS);
        const points = Math.floor(Math.pow(2, winNums.size - 1));
        return acc + points;
    }, 0);
}

async function d4b(filename: string = 'input') {
    // Card wins cards...
    const input = await Bun.file(filename).text();
    const lines = input.split('\n');
    let winMap = new Map<number, number>();

    lines.forEach((line: string) => {
        let [card, wins, nums] = line.split(/:|\|/g)
            .map(s => s.match(/[0-9]+/g)?.map(Number));

        const winS = new Set(wins);
        const numS = new Set(nums);
        const winNums = winS.intersection(numS);
        winMap.set(card[0], winNums.size);
    });

    let cardMap = new Map<number, number>();
    for (const [card, _] of winMap) {
        cardMap.set(card, 1);
    }
    for (const [card, wins] of winMap) {
        for (let i = 0; i < cardMap.get(card); i++) {
            for (let i = 1; i <= wins; i++) {
                cardMap.set(card + i, cardMap.get(card + i) + 1);
            }
        }
    }

    let numCards = 0;
    for (const [_, count] of cardMap) {
        numCards += count;
    }
    return numCards;
}

console.log("d4a ", await d4a());
console.log("d4b ", await d4b());