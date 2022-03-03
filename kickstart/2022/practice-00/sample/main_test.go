package main

import (
	"strings"
	"testing"
)

func TestMe(t *testing.T) {
	input :=
		`2
7 3
1 2 3 4 5 6 7
5 10
7 7 7 7 7
`
	got := Me(strings.NewReader(input))
	if got != 1 {
		t.Errorf("Got %d", got)
	}
}
