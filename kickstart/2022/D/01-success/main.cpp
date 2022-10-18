// len >= 7
// A-Z
// a-z
// 0-9
// [#@*&]

#include <iostream>
#include <string>
#include <cstdio>
#include <deque>
#include <vector>
#include <numeric>
#include <algorithm>

using namespace std;


double median(vector<unsigned int>& v) {
    auto s = v.size();
    if (s == 1)
        return v[0];

    if (s & 1)
        return v[s/2];
    else
        return (v[s/2] + v[s/2 - 1]) / 2.0;
}

int main(int argc, char **) {
    const bool dkri = (argc > 1);

    size_t ntests, nreg, ncat;
    cin >> ntests;
    vector<unsigned int> parts;

    for (auto t = 0u; t < ntests; ++t)
    {
        cin >> nreg;
        cin >> ncat;
        parts.clear();
        for (auto i = 0u; i < nreg; ++i)
        {
            unsigned int a;
            cin >> a;
            parts.push_back(a);
        }
        sort(parts.rbegin(), parts.rend());

        printf("Case #%u: ", t + 1);

        if (ncat == 1) {
            printf("%.6lf\n", median(parts));
            continue;
        }

        vector<double> cats;
        for (auto i = 0u; i < ncat; ++i) {
            if (i + 1 == ncat) {
                vector<unsigned int> v(parts.begin() + i, parts.end());
                cats.emplace_back(median(v));
                break;
            }
            cats.emplace_back(parts[i]);
        }

        printf("%.6lf\n", accumulate(cats.begin(), cats.end(), 0.0));
    }
    return 0;
}