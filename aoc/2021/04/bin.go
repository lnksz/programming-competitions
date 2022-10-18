package aoc

import (
	"fmt"
	"bufio"
)

const Dim int = 5

type Board struct {
	b uint8[Dim][Dim]
	m uint8[Dim][Dim]
	score int
}

func (b Board) HasBingo() bool {
	cols_skip bool[Dim]
	bingo := false
	for r := 0; r < Dim; r++ {
		bingo = true
		for c := 0; c < Dim; c++ {
			if b.m[r][c] == 0 {
				bingo = false
				cols_skip[c] = true
				break
			}
		}
		if bingo {
			fmt.Printf("Bingo (%d,%d)!", r, c)
			return true
		}
	}

	for c := 0; c < Dim; c++ {
		if cols_skip[c] {
			continue
		}
		for r := 0; r < Dim; r++ {
			bingo := true
			if b.m[r][c] == 0 {
				bingo = false
				break
			}
		}
		if bingo {
			fmt.Printf("Bingo (%d,%d)!", r, c)
			return true
		}
	}
	return false
}

func (b Board) Fill(m string[5]) {
	for line in range(m)
}

func readBoards(f string)

func BingoA(f string) int {
	b Bingo
	b.Fill
	fmt.Printf("Bing %q!\n", f)
	return 42
}
