async function d2a(filename: string = 'input') {
    const maxRed = 12;
    const maxGreen = 13;
    const maxBlue = 14;
    const file = Bun.file(filename);
    const input = await file.text();
    const lines = input.trim().split('\n');
    const regRed = /(\d+) red/g;
    const regGreen = /(\d+) green/g;
    const regBlue = /(\d+) blue/g;
    return lines
        .map((line: string) => {
            // Let's spare us to handle if there is no color in the game
            line += '; 0 red 0 green 0 blue';
            const game = parseInt(line.match(/Game (\d+)/)![1]);
            // parseInt ignores the pending color string
            const reds =   Math.max(...line.match(regRed)!.map((s: string) => parseInt(s)));
            const greens = Math.max(...line.match(regGreen)!.map((s: string) => parseInt(s)));
            const blues =  Math.max(...line.match(regBlue)!.map((s: string) => parseInt(s)));
            
            if (reds <= maxRed && greens <= maxGreen && blues <= maxBlue)
                return game;
            else
                return 0;
        }).reduce((a: number, n: number) => a + n, 0);

}

async function d2b(filename: string = 'input') {
    const file = Bun.file(filename);
    const input = await file.text();
    const lines = input.trim().split('\n');
    const regRed = /(\d+) red/g;
    const regGreen = /(\d+) green/g;
    const regBlue = /(\d+) blue/g;
    return lines
        .map((line: string) => {
            // Let's spare us to handle if there is no color in the game
            line += '; 1 red 1 green 1 blue';
            // parseInt ignores the pending color string
            const reds = Math.max(...line.match(regRed)!.map((s: string) => parseInt(s)));
            const greens = Math.max(...line.match(regGreen)!.map((s: string) => parseInt(s)));
            const blues = Math.max(...line.match(regBlue)!.map((s: string) => parseInt(s)));

            return reds * greens * blues;
        }).reduce((a: number, n: number) => a + n, 0);

}

console.log('d2a', await d2a());
console.log('d2a', await d2b());
export { };