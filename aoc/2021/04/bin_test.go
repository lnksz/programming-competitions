package aoc

import (
	"testing"
)

func TestBingoA(t *testing.T) {
	res := BingoA("sample")
	win := 4512
	if res != win {
		t.Errorf("Bingo() = %d; want %d", res, win)
	}
}
