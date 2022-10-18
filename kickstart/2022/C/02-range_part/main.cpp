// len >= 7
// A-Z
// a-z
// 0-9
// [#@*&]

#include <iostream>
#include <string>
#include <cstdio>
#include <cmath>
#include <vector>

using namespace std;

int main(int argc, char **) {
    const bool dkri = (argc > 1);

    size_t ntests;
    size_t n, x, y;
    size_t S; // Sum of first 'n' integers
    size_t A; // Sum of Alen's numbers

    cin >> ntests;
    for (auto t = 0u; t < ntests; ++t)
    {
        cin >> n;
        cin >> x;
        cin >> y;

        S = n * (n + 1) / 2;
        if ((S * x) % (y + x) != 0) {
            printf("Case #%u: IMPOSSIBLE\n", t + 1);
            continue;
        }
        printf("Case #%u: POSSIBLE\n", t + 1);

        A = S * x / (y + x);
        vector<size_t> nums;
        size_t sum = 0;
        for (size_t i = n; i; --i) {
            if (sum == A)
                break;
            
            if (i + sum <= A) {
                sum += i;
                nums.push_back(i);
            }
        }

        auto len = nums.size();
        cout << len << '\n';
        for (size_t i = 0; i < len - 1; ++i) {
            cout << nums[i] << ' ';
        }
        cout << nums[len - 1] << '\n';
    }
    return 0;
}