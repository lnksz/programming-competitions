use std::env;
use std::fs;

// First try before discovering `windows()`
// fn count_inc1(v: &Vec<i32>) {
//     println!("Counting direct increases");
//     let end = v[1..v.len()].iter();
//     let beg = v[0..v.len()-1].iter();
//     let inc = end.zip(beg).fold(0, |acc, pair|
//         if (pair.0 - pair.1) > 0 {
//             return acc + 1;
//         } else {
//             return acc;
//         });
//     println!("Increases: {}", inc);
// }

// After discovering windows <3 <3 <3
fn count_inc(v: &Vec<i32>) -> i32 {
    let incs : i32 = v.windows(2).map(|p| (p[1] > p[0]) as i32).sum();
    return incs;
}

fn count_win(v: &Vec<i32>) -> i32 {
    let wsums : Vec<i32> = v.windows(3).map(|w| w.iter().sum::<i32>()).collect();
    let incs : i32 = count_inc(&wsums);
    return incs;
}

fn main() {
    // $0 filename a/b
    let args: Vec<String> = env::args().collect();
    let filename = &args[1];
    let variant = &args[2];

    let contents = fs::read_to_string(filename)
        .expect("Something went wrong reading the file");

    println!("Loaded file <{}>", filename);

    let data: Vec<i32> = contents.split('\n').map(|x|
            x.parse::<i32>().unwrap()
        ).collect();

    let res = match variant.as_str() {
        "a" => count_inc(&data),
        "b" => count_win(&data),
        _ => -1,
    };
    println!("Increases: {}", res);
}

