# Move Zeroes

## Problem

Given an integer array `nums`, move all `0`s to the end while maintaining the relative order of all non-zero elements.

The operation must be performed in-place without making a copy of the array.

### Example

**Input:**
```java
nums = [0, 1, 0, 3, 12]
```

**Output:**
```java
[1, 3, 12, 0, 0]
```

## Approach: Two Pointers

We use two pointers:

- `i`: Traverses the array and finds non-zero elements.
- `nonZeroIndex`: Tracks the position where the next non-zero element should be placed.

When `nums[i]` is non-zero, swap it with `nums[nonZeroIndex]` and increment `nonZeroIndex`.

This places non-zero elements at the beginning while preserving their relative order. The remaining positions contain zeroes.

## Java Implementation

```java
package dsa.arrays;

public class MoveZeroes {

    public void moveZeroes(int[] nums) {
        int nonZeroIndex = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int temp = nums[nonZeroIndex];
                nums[nonZeroIndex] = nums[i];
                nums[i] = temp;

                nonZeroIndex++;
            }
        }
    }
}
```

## Complexity Analysis

- **Time Complexity:** `O(n)` — the array is traversed once.
- **Space Complexity:** `O(1)` — elements are rearranged in-place using constant extra space.

## Key Takeaway

The two-pointer technique helps rearrange elements in-place while maintaining the relative order of non-zero elements.