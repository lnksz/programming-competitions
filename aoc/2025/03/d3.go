package aoc202503a

import (
	"fmt"
	"bufio"
	"os"
	"log"
	// "strings"
	"strconv"
)

func errc(e error) {
	if e != nil {
		log.Fatal(e)
	}
}

func MaxJolt(s string) int {
	l := len(s)
	m := 0
	for i := 0; i < (l-1); i++ {
		for j := i+1; j < l; j++ {
			n, _ := strconv.Atoi(string(s[i]) + string(s[j]))
			if n > m {
				m = n
			}
		}
	}
	return m
}

func T1(f string) int {
	file, _ := os.Open(f)
	defer file.Close()
	scn := bufio.NewScanner(file)
	sum := 0
	for scn.Scan() {
		sum += MaxJolt(scn.Text())
	}
	fmt.Println(sum)
	return sum
}

func NextDigit(nums map[int][]int, from, to int) (int, int) {
	if !(from < to) {
		log.Fatal("From is bigger than to")
	}
	for n := 9; n >= 0; n-- {
		a := nums[n]
		if len(a) == 0 {
			continue
		}
		for _, idx := range a {
			if from <=idx && idx < to {
				return idx, n
			}
		}
	}
	return 0, 0
}

func MaxJolt2(s string, ndigs int) int {
	l := len(s)
	nums := map[int][]int {
		0: {}, 1: {}, 2: {}, 3: {}, 4: {},
		5: {}, 6: {}, 7: {}, 8: {}, 9: {},
	}
	for i := 0; i < l; i++ {
		n := int(s[i] - '0')
		nums[n] = append(nums[n], i)
	}

	r := 0
	fromidx := 0
	for i := 1; i <= ndigs; i++ {
		toidx := l - (ndigs - i)
		ndi, nd := NextDigit(nums, fromidx, toidx)
		fromidx = ndi + 1
		r *= 10
		r += nd
	}
	return r
}

func T2(f string) int {
	file, _ := os.Open(f)
	defer file.Close()
	scn := bufio.NewScanner(file)
	sum := 0
	for scn.Scan() {
		sum += MaxJolt2(scn.Text(), 12)
	}
	fmt.Println(sum)
	return sum
}
