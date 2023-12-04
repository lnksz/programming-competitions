async function t1a() {
    // const file = await fsPromise.open('./input1.txt', 'r');
    const file = Bun.file('input1.txt')
    const input = await file.text();
    let lines = input.trim().split('\n');
    console.log(lines);
}

t1a();
