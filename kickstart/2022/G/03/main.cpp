#include <iostream>
#include <string>
#include <cstdio>
#include <cstring>
#include <vector>
#include <algorithm>
#include <cmath>

using namespace std;


int main(int argc, char **)
{
    size_t ntests;
    size_t nnums;
    cin >> ntests;
    for (auto t = 0u; t < ntests; ++t)
    {
        // find happy subarrays which don't have negative prefix-sums
        //  partial sums staring from each element  
        // sum up them

        printf("Case #%u: ", t + 1);
        vector<int> nums;
        cin >> nnums;
        if (nnums == 1)
        {
            int num;
            cin >> num;
            printf("%d\n", (num < 0) ? 0 : num);
            continue;
        }
    
        nums.reserve(nnums);
        for (auto i = 0u; i < nnums; ++i)
        {
            int x;
            cin >> x;
            nums.emplace_back(x);
        }

        printf("%zu\n", nums.size());
    }
    return 0;
}