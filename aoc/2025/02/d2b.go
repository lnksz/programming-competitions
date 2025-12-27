package aoc202502b

import (
	"fmt"
	"encoding/csv"
	"math"
	"os"
	"log"
	"strings"
	"strconv"
)

func errc(e error) {
	if e != nil {
		log.Fatal(e)
	}
}

type Pair struct {
	a int
	b int
}

func parse(strs []string) []Pair {
	pairs := make([]Pair, len(strs))
	for i, s := range strs {
		lims := strings.Split(s, "-")
		n1, _ := strconv.Atoi(lims[0])
		n2, _ := strconv.Atoi(lims[1])
		pairs[i] = Pair{n1, n2}
	}
	return pairs
}

func Dim(n int) int {
	return int(math.Log10(float64(n))) + 1
}

func IsRep(n int) bool {
	s := strconv.Itoa(n)
	d := len(s)
	for i := 1; i <= d/2; i++ {
		t := strings.Repeat(s[:i], d/i)
		if len(t) != d {
			continue
		}
		if t == s {
			return true
		}
	}
	return false
}

func SumReps(from, until int) int {
	sum := 0
	for x := from; x <= until; x++ {
		if IsRep(x) {
			sum += x
		}
	}
	return sum
}

func T1b(f string) int {
	file, _ := os.Open(f)
	defer file.Close()
	r := csv.NewReader(file)
	records, err := r.ReadAll()
	errc(err)
	lims := parse(records[0])
	// fmt.Println(lims)
	sum := 0
	for _, l := range lims {
		sum += SumReps(l.a, l.b)
	}
	fmt.Println(sum)
	return sum
}
