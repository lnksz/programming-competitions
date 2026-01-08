package aoc202503a

import "testing"

func TestMaxJolt(t *testing.T) {
	tests := []struct {
		input string
		want  int
	}{
		{ input: "987654321111111", want:  98, },
		{ input: "234234234234278", want:  78, },
		{ input: "818181911112111", want:  92, },
		{ input: "811111111111119", want:  89, },
	}
	for _, tt := range tests {
		t.Run("MaxJolt()", func(t *testing.T) {
			if got := MaxJolt(tt.input); got != tt.want {
				t.Errorf("MaxJolt(%v) = %v, but want %v", tt.input, got, tt.want)
			}
		})
		t.Run("MaxJolt2()", func(t *testing.T) {
			if got := MaxJolt2(tt.input, 2); got != tt.want {
				t.Errorf("MaxJolt2(%v, 2) = %v, but want %v", tt.input, got, tt.want)
			}
		})
	}
}

func TestT1(t *testing.T) {
	tests := []struct {
		input string
		want  int
	}{
		{ input: "bsp", want:  357, },
		{ input: "inp", want:  16812, },
	}

	for _, tt := range tests {
		t.Run(tt.input, func(t *testing.T) {
			if got := T1(tt.input); got != tt.want {
				t.Errorf("T1() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestMaxJolt2(t *testing.T) {
	tests := []struct {
		input string
		want  int
	}{
		{ input: "987654321111111", want:  987654321111, },
		{ input: "234234234234278", want:  434234234278, },
		{ input: "818181911112111", want:  888911112111, },
		{ input: "811111111111119", want:  811111111119, },
	}
	for _, tt := range tests {
		t.Run("MaxJolt2()", func(t *testing.T) {
			if got := MaxJolt2(tt.input, 12); got != tt.want {
				t.Errorf("MaxJolt2(%v) = %v, but want %v", tt.input, got, tt.want)
			}
		})
	}
}

func TestT2(t *testing.T) {
	tests := []struct {
		input string
		want  int
	}{
		{ input: "bsp", want:  3121910778619, },
		{ input: "inp", want:  166345822896410, },
	}

	for _, tt := range tests {
		t.Run(tt.input, func(t *testing.T) {
			if got := T2(tt.input); got != tt.want {
				t.Errorf("T2() = %v, want %v", got, tt.want)
			}
		})
	}
}

