# Maximum Subarray

## Problem

Given an integer array `nums`, find the contiguous subarray with the largest sum and return its sum.

A subarray must contain at least one element.

**Examples**

```text
Input: nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
Output: 6

Explanation: The subarray [4, -1, 2, 1] has the largest sum.

Input: nums = [1]
Output: 1

Input: nums = [5, 4, -1, 7, 8]
Output: 23
```

---

## Approach (Kadane's Algorithm)

Use two variables:

- `currentSum`: Maximum sum of a subarray ending at the current position.
- `maxSum`: Maximum subarray sum encountered so far.

For each element:

- Decide whether to start a new subarray or extend the existing one.
- Update `currentSum`.
- Update `maxSum` if the current sum is greater.

Return `maxSum` after traversing the array.

---

## Key Insight

At each position, either:

- Start a new subarray from the current element.
- Extend the previous subarray by adding the current element.

The larger of these two choices gives the maximum subarray sum ending at that position.

---

## Complexity

**Time:** O(n)

**Space:** O(1)

Where n is the number of elements in the array.

---

## Edge Cases

- Single-element array.
- All negative elements.
- All positive elements.
- Array containing zero.
- Mixed positive and negative elements.

---

## Why Kadane's Algorithm Works

The algorithm makes an optimal decision at each position by comparing the current element with the sum obtained by extending the previous subarray.

By maintaining the best sum ending at each position and the overall maximum, it finds the maximum contiguous subarray sum in one traversal.
