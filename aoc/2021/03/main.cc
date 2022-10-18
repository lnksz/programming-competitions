#include <iostream>
#include <fstream>
#include <string>
#include <cstdint>
#include <array>
#include <vector>
#include <fmt/format.h>
#include <fmt/ranges.h> // printing containers

void a(const char *file)
{
    fmt::print("From {}\n", file);
    std::ifstream in(file);
    auto nlines = 0;
    std::array<unsigned int, 32> ones{0};
    size_t width = 0;

    for (std::string line; std::getline(in, line); ++nlines) {
        auto l = line.length();
        if (l > width)
            width = l;
        auto n = std::stoul(line, nullptr, 2);
        for (auto tmp = n, i = 0UL; tmp; tmp >>= 1, ++i) {
            if (tmp & 1)
                ++ones[i];
        }
    }

    auto mask = 0UL;
    auto gamma = 0UL;
    for (size_t i = 0; i < width; ++i) {
        mask |= (1 << i);
        if (ones[i] > (nlines - ones[i])) {
            gamma |= (1 << i);
        }
    }
    auto eps = ~gamma & mask;
    fmt::print("G:{:012b}\nE:{:012b}\n\t{}\n", gamma, eps, gamma*eps);
}

static inline unsigned int
is_bit_set(unsigned int n, unsigned int b) {
    return (n >> b) & 1;
}

template <class C, typename T>
static unsigned int get_life_support(C &vec, T bit, int pref) {
    if (vec.size() == 1)
        return vec[0];

    C v0, v1, *p;
    for (auto e : vec) {
        if (is_bit_set(e, bit))
            v1.emplace_back(e);
        else
            v0.emplace_back(e);
    }

    auto len0 = v0.size();
    auto len1 = v1.size();
    if (len0 <= len1)
        p = pref ? &v1 : &v0;
    else
        p = pref ? &v0 : &v1;

    return get_life_support(*p, bit - 1, pref);
}

void b(const char *file)
{
    fmt::print("From {}\n", file);
    std::ifstream             in(file);
    auto                      nlines = 0;
    std::vector<unsigned int> nums{0};
    size_t                    width = 0;

    for (std::string line; std::getline(in, line); ++nlines) {
        auto l = line.length();
        if (l > width) width = l;
        nums.emplace_back(std::stoul(line, nullptr, 2));
    }
    
    auto oxy = get_life_support(nums, width - 1, 1);
    auto co2 = get_life_support(nums, width - 1, 0);
    fmt::print("Oxy: {}\nCO2: {}\nLFS: {}\n", oxy, co2, oxy*co2);
}

int main(int argc, char *argv[])
{
    if (argc > 2 && argv[1][0] == 'a')
        a(argv[2]);
    else if (argc > 2 && argv[1][0] == 'b')
        b(argv[2]);
    else
        fmt::print("Invalid input {} {}\n", argc, argv[0]);
}
