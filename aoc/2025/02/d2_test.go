package aoc

import "testing"

func TestSumReps(t *testing.T) {
	t.Run("SR", func(t *testing.T) {
		if got := SumReps(11,22,0); got != 33 {
			t.Errorf("SR(%v) = %v, but want %v", 11, got, 33)
		}
	})
}

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

func TestUpperHalf(t *testing.T) {
	tests := []struct {
		input int
		want  int
	}{
		{
			input: 12,
			want:  1,
		},
		{
			input: 1211,
			want:  12,
		},
		{
			input: 1213,
			want:  12,
		},
		{
			input: 1212,
			want:  12,
		},
	}
	for _, tt := range tests {
		t.Run("UpperHalf()", func(t *testing.T) {
			if got := UpperHalf(tt.input); got != tt.want {
				t.Errorf("UpperHalf(%v) = %v, but want %v", tt.input, got, tt.want)
			}
		})
	}
}

func TestLowerHalf(t *testing.T) {
	tests := []struct {
		input int
		want  int
	}{
		{
			input: 12,
			want:  2,
		},
		{
			input: 1211,
			want:  11,
		},
		{
			input: 1213,
			want:  13,
		},
		{
			input: 1212,
			want:  12,
		},
	}
	for _, tt := range tests {
		t.Run("LowerHalf()", func(t *testing.T) {
			if got := LowerHalf(tt.input); got != tt.want {
				t.Errorf("LowerHalf(%v) = %v, but want %v", tt.input, got, tt.want)
			}
		})
	}
}

func TestNextRep(t *testing.T) {
	tests := []struct {
		input int
		want  int
	}{
		{ input: 1, want:  11, },
		{ input: 1211, want:  1212, },
		{ input: 1213, want:  1313, },
		{ input: 1212, want:  1212, },
		{ input: 376884, want:  377377, },
	}
	for _, tt := range tests {
		t.Run("NextRep()", func(t *testing.T) {
			if got := NextRep(tt.input); got != tt.want {
				t.Errorf("NextRep(%v) = %v, but want %v", tt.input, got, tt.want)
			}
		})
	}
}

func TestRep(t *testing.T) {
	tests := []struct {
		input int
		want  int
	}{
		{
			input: 1,
			want:  11,
		},
		{
			input: 2,
			want:  22,
		},
		{
			input: 22,
			want:  2222,
		},
		{
			input: 12345,
			want:  1234512345,
		},
	}
	for _, tt := range tests {
		t.Run("Rep()", func(t *testing.T) {
			if got := Rep(tt.input); got != tt.want {
				t.Errorf("Rep(%v) = %v, but want %v", tt.input, got, tt.want)
			}
		})
	}
}

func TestIsRep(t *testing.T) {
	tests := []struct {
		name  string
		input int
		want  bool
	}{
		{
			name:  "odd-false",
			input: 121,
			want:  false,
		},
		{
			name:  "mini-false",
			input: 1221,
			want:  false,
		},
		{
			name:  "mini-true",
			input: 22,
			want:  true,
		},
		{
			name:  "long-true",
			input: 1234512345,
			want:  true,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := IsRep(tt.input); got != tt.want {
				t.Errorf("IsRep(%v) = %v, but want %v", tt.input, got, tt.want)
			}
		})
	}
}

func TestT1(t *testing.T) {
	tests := []struct {
		name  string
		input string
		want  int
	}{
		{
			name:  "example",
			input: "exmpl",
			want:  1227775554,
		},
		{
			name:  "input",
			input: "input",
			want:  18595663903,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := T1(tt.input); got != tt.want {
				t.Errorf("T1() = %v, want %v", got, tt.want)
			}
		})
	}
}

