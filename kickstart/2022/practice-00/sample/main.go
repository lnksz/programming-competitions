package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func solve(nbags int, nkids int, bags []uint16, nth int) {
	var sum uint64
	for b := 0; b < nbags; b++ {
		sum += uint64(bags[b])
	}
	fmt.Printf("Case #%d: %d\n", nth, sum%uint64(nkids))
}

func main() {
	var (
		ntests int
		nbags  int
		nkids  int
	)
	// max line-length of candies is 5 * 100'000
	const cap = 5 * 100000
	scanner := bufio.NewScanner(os.Stdin)
	buf := make([]byte, cap)
	scanner.Buffer(buf, cap)
	scanner.Scan()
	fmt.Sscanf(scanner.Text(), "%d", &ntests)
	bags := make([]uint16, 100000)

	for i := 0; i < ntests; i++ {
		scanner.Scan()
		fmt.Sscanf(scanner.Text(), "%d %d", &nbags, &nkids)
		scanner.Scan()
		for j, c := range strings.Split(scanner.Text(), " ") {
			fmt.Sscanf(c, "%d", &bags[j])
		}
		solve(nbags, nkids, bags, i+1)
	}
}
