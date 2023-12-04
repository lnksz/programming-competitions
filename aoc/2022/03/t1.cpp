#include <cctype>
#include <fstream>
#include <cstdint>
#include <iostream>
#include <cstdio>

inline static int prio(uint64_t bf)
{
	for (int i = 1; i <= 64; ++i)
	{
		if (bf & 1)
			return i;
		bf >>= 1;
	}
}

inline static int shift(char c)
{
	return islower(c) ? c - 'a' : c - 'A' + 26;
}

uint64_t enc(const char *s, int len)
{
	uint64_t bf = 0;
	for (int i = 0; i < len; ++i)
		bf |= (1llu << shift(s[i]));
	return bf;
}

int main()
{
	using namespace std;
	int sum = 0;
	ifstream inp;
	inp.open("input");
	for (std::string line; std::getline(inp, line);)
	{
		const auto half = line.size() / 2;
		const auto cstr = line.c_str();
		const auto e1 = enc(cstr, half);
		const auto e2 = enc(cstr + half, half);
		const uint64_t bf = e1 & e2;
		sum += prio(bf);
	}

	printf("%d\n", sum);
	return 0;
}
