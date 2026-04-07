package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

func errcheck(err error) {
	if err != nil {
		log.Fatalf("Error: %s", err)
	}
}

func zerosInside(a, z int) int {
	if a == z {
		return 0
	}
	step := 1
	nzero := 0
	if a > z {
		step = -1
	}
	for p := a + step; p != z; p += step {
		// fmt.Println(a,z, p)
		if p % 100 == 0 {
			nzero++
		}
	}
	return nzero
}

func main() {
	file, err := os.Open("input")
	errcheck(err)
	defer file.Close()

	scanner := bufio.NewScanner(file)
	pos := 50
	nzero := 0

	for scanner.Scan() {
		if pos % 100 == 0 {
			nzero += 1
		}
		line := scanner.Text()
		rot, err := strconv.Atoi(line[1:])
		errcheck(err)
		if line[0] == 'L' {
			rot *= -1
		}

		nxt := pos + rot
		nzero += zerosInside(pos, nxt)
		// fmt.Printf("%s %d %d => %d\n", line, rot, pos, nxt)
		pos = nxt
	}
	fmt.Println(nzero)

	if err := scanner.Err(); err != nil {
		log.Fatalf("error reading file: %s", err)
	}
}

