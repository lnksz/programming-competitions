#include <iostream>
#include <string>
#include <cstdio>
#include <cstring>
#include <algorithm>

using namespace std;

int main(int argc, char **) {
    size_t ntests;
    size_t ndays;
    size_t ncandids;
    size_t myid;
    size_t *scoreboard[31];

    cin >> ntests;
    for (auto t = 0u; t < ntests; ++t)
    {
        printf("Case #%u: ", t + 1);
        cin >> ncandids;
        cin >> ndays;
        cin >> myid;
        for (auto day = 0u; day < ndays; ++day) {
            scoreboard[day] = (size_t *) calloc(ncandids, sizeof(size_t));
        }

        size_t steps;
        for (auto candid = 0u; candid < ncandids; ++candid) {
            for (auto day = 0u; day < ndays; ++day) {
                std::cin >> steps;
                scoreboard[day][candid] = steps;
            }
        }
        size_t needed_steps = 0;
        size_t myscore, maxscore;
        for (auto day = 0u; day < ndays; ++ day) {
            myscore = scoreboard[day][myid-1];
            maxscore = *max_element(&scoreboard[day][0], &scoreboard[day][ncandids]);
            if (maxscore > myscore)
                needed_steps += (maxscore - myscore);
        }

        for (auto day = 0u; day < ndays; ++day) {
            free(scoreboard[day]);
        }

        printf("%zu\n", needed_steps);
    }
    return 0;
}