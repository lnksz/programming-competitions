#include <iostream>
#include <string>
#include <numeric>
#include <iterator>
#include <cstdio>

using namespace std;

int main(int argc, char **)
{
    std::size_t        ntests;
    unsigned long long n;
    string             nstr;
    const bool         dkri = (argc > 1);

    cin >> ntests;
    for (auto t = 0u; t < ntests; ++t)
    {
        cin >> nstr;
        auto ndigit  = nstr.length();
        bool is_giga = (ndigit > 19);

        if (dkri && is_giga) puts("giga!");


        int mod;
        if (ndigit < 20)
        {
            n   = strtoull(nstr.c_str(), nullptr, 10);
            mod = n % 9;
        }
        else
        {
            mod = accumulate(next(nstr.cbegin()), nstr.cend(), nstr[0] - '0',
                             [](int m, char c) { return (m + (c - '0')) % 9; });
        }

        int newdig   = mod ? 9 - mod : 0;
        int newplace = newdig ? 0 : 1;
        for (auto i = newplace ? 1lu : 0lu; i < ndigit; ++i)
        {
            int d = (char)nstr[i] - '0';
            if (newdig < d) { break; }
            ++newplace;
        }

        printf("Case #%u: %s\n", t + 1, nstr.insert(newplace, 1, newdig + '0').c_str());
    }
    return 0;
}