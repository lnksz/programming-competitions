package aoc

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

func LowerHalf(n int) int {
	d := Dim(n)
	if d == 1 {
		return n
	}
	cut := int(math.Pow10(d / 2))
	return n % cut
}

func UpperHalf(n int) int {
	d := Dim(n)
	if d == 1 {
		return n
	}
	cut := int(math.Pow10(d / 2))
	return n / cut
}

func IsRep(n int) bool {
	d := Dim(n)
	if d % 2 == 1 {
		return false
	}
	cut := int(math.Pow10(d/2))
	nu := n / cut
	return n == (nu * cut + nu)
}

func Rep(x int) int {
	d := Dim(x)
	dim10 := int(math.Pow10(d))
	return x * dim10 + x
}

func NextRep(x int) int {
	if IsRep(x) {
		return x
	}
	d := Dim(x)
	if d % 2 == 1 {
		// fmt.Printf("Next(%v) d=%v\n", x, d)
		y := int(math.Pow10(d))
		return NextRep(y)
	}
	dh := int(math.Pow10(d/2))
	nu := UpperHalf(x)
	nx := nu * dh + nu
	for nu++; nx < x; nu++ {
		nx = nu * dh + nu
	}
	return nx
}

func SumReps(from, until, sum int) int {
	if sum == 0 {
		fmt.Printf("%v-%v\n", from, until)
	}
	next := NextRep(from)
	if next > until {
		return sum
	}
	fmt.Printf("\t%v\n", next)
	return SumReps(next+1, until, next+sum) 
}

func T1(f string) int {
	file, _ := os.Open(f)
	defer file.Close()
	r := csv.NewReader(file)
	records, err := r.ReadAll()
	errc(err)
	lims := parse(records[0])
	// fmt.Println(lims)
	sum := 0
	for _, l := range lims {
		sum += SumReps(l.a, l.b, 0)
	}
	fmt.Println(sum)
	return sum
}
