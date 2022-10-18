// rolling H index over time (citations don't change though)
// T [1, 100]
// N : num papers over lifetime [1, 1e5]
// C0 C1 ... Cn Citations [1, 1e5]

/*
Per test-case (N):

1. Init H with 0, container A with capacity 1e5
2. Read next citation Ci
3. Is Ci <= H, then go to 2.
4. Add Ci to A so that A stays sorted (Descending sort)
    - find place for Ci : O(log(A.used))
    - insert Ci: assign + memmove : O(2)
5. Recalc H
    - given that "H <= A.used", we can start at index(H-1) -> A' := A[H-1:]
        - init h with H
        - scan until Ci <= h
*/
#include <iostream>
#include <cstdio>
#include <vector>
#include <algorithm>

using namespace std;

template <class T>
int hindex(T &cits) {
    auto start = cits.begin();
    int h = 1;
    for (auto it = start; it != cits.cend(); ++it) {
        const auto n   = distance(start, it) + 1;
        const auto c = *it;
        if (c == n) return c; // Won't get any better
        if (c < n)  return h; // Last one was better
        h = n;                // We could do better
    }
    return h;
}

int main() {
    std::size_t ntests, ncit, cit, h;
    vector<size_t> cits {};
    cits.reserve(1e5);

    cin >> ntests;
    for (auto t = 0u; t < ntests; ++t) {
        cin >> ncit;
        printf("Case #%d:", t + 1);
        if (ncit == 1) {
            printf(" 1\n");
            continue;
        }
        // There is min 1 citation, we push it in without more fuss
        cin >> cit;
        h = 1;
        cits.push_back(cit);
        printf(" %zu", h);

        for (auto ci = 1u; ci < ncit; ++ci) {
            cin >> cit;
            if (cit < h) {
                // new cit cannot increase H
                printf(" %zu", h);
                continue;
            }
            // lower_bound : O(log(last - first)) + O(1)
            // insert : O(1) + O(last-pos) 
            cits.insert(upper_bound(cits.cbegin(), cits.cend(), cit), cit);
            for (auto k = h-1; k < cits.size(); k++) {
                if (cits[k] < (k + 1)) {
                    h = k + 1;
                    cits.erase(cits.begin()+k, cits.end());
                    break;
                }
            }
            printf(" %zu", h);
        }
        puts("");
        if (t + 1 < ntests) cits.clear();
    }
    return 0;
}