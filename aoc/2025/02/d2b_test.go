package aoc202502b

import "testing"

func TestDim(t *testing.T) {
	tests := []struct {
		input int
		want  int
	}{
		{ input: 1, 		want:  1, },
		{ input: 12,		want:  2, },
		{ input: 121,		want:  3, },
		{ input: 1212,	want:  4, },
	}
	for _, tt := range tests {
		t.Run("Dim()", func(t *testing.T) {
			if got := Dim(tt.input); got != tt.want {
				t.Errorf("Dim(%v) = %v, but want %v", tt.input, got, tt.want)
			}
		})
	}
}

func TestIsRep(t *testing.T) {
	tests := []struct {
		input int
		want  bool
	}{
		{ input: 1, want:  false, },
		{ input: 11, want:  true, },
		{ input: 1221, want:  false, },
		{ input: 222, want:  true, },
		{ input: 12345, want:  false, },
		{ input: 1234512345, want:  true, },
		{ input: 121212, want:  true, },
		{ input: 123123123, want:  true, },
	}
	for _, tt := range tests {
		t.Run("IsRep()", func(t *testing.T) {
			if got := IsRep(tt.input); got != tt.want {
				t.Errorf("IsRep(%v) = %v, but want %v", tt.input, got, tt.want)
			}
		})
	}
}

func TestT1b(t *testing.T) {
	tests := []struct {
		input string
		want  int
	}{
		{ input: "exmpl", want:  4174379265, },
		{ input: "input", want:  0, },
	}

	for _, tt := range tests {
		t.Run(tt.input, func(t *testing.T) {
			if got := T1b(tt.input); got != tt.want {
				t.Errorf("T1b() = %v, want %v", got, tt.want)
			}
		})
	}
}

