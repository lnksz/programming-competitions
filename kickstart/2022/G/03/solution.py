from itertools import combinations
from pprint import pprint
from functools import lru_cache


@lru_cache
def is_happy(array):
    sum = 0
    for e in array:
        sum += e
        if sum < 0:
            return False
    return True


num_testc = int(input())

for tid in range(1, num_testc + 1):
    happysums = 0
    nnums = int(input())
    nums = tuple([int(n) for n in input().split()])
    unhappy_idx = -1
    # pprint(nums)
    # For all subarrays in nums
    for x, y in combinations(range(nnums + 1), r=2):
        if x == unhappy_idx:
            continue
        # If a single number, we don't need partial sums
        if (y - x) == 1:
            if nums[x] < 0:
                unhappy_idx = x
            else:
                # pprint(nums[x])
                happysums += nums[x]
            continue

        subs = nums[x:y]
        # pprint(subs)
        if is_happy(subs):
            happysums += sum(subs)
        else:
            unhappy_idx = x
    print(f"Case #{tid}: {happysums}")
