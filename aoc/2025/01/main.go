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

		fmt.Printf("%s %d %d => %d\n", line, rot, pos, pos + rot)
		pos += rot
	}
	fmt.Println(nzero)

	if err := scanner.Err(); err != nil {
		log.Fatalf("error reading file: %s", err)
	}
}

