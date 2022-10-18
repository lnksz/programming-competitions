// 1 ≤ T ≤ 100
// 1 ≤ R ≤ 105
// 1 ≤ A ≤ 500
// 2×A ≤ B ≤ 1000

#include <iostream>
#include <string>
#include <cstdio>
#include <cmath>

using namespace std;

int main(int argc, char **) {
    const bool dkri = (argc > 1);

    size_t ntests;
    size_t R, A, B;
    unsigned long long r;
    double areas;

    cin >> ntests;
    for (auto t = 0u; t < ntests; ++t)
    {
        cin >> R;
        cin >> A;
        cin >> B;

        if (dkri) printf("%zu %zu %zu\n", R, A, B);


        // First 2 circles are always drawn
        areas = (1 + A*A) * R*R;
        auto is_a = false;
        for (r = (R * A) / B; r > 0; r = is_a ? r*A : r/B) {
            areas += r*r;
            is_a = !is_a;
            if (dkri) printf("%lld %.6f\n", r, areas);
        }

        areas *= M_PI;

        printf("Case #%u: %.6f\n", t + 1, areas);
    }
    return 0;
}