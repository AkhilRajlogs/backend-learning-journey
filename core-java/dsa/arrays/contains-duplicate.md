# Contains Duplicate

## Problem

Given an integer array `nums`, determine whether any value appears at least twice.

Return `true` if duplicates exist; otherwise, return `false`.

**Examples**

```text
Input: nums = [1, 2, 3, 1]
Output: true

Input: nums = [1, 2, 3, 4]
Output: false

Input: nums = [1, 1, 1, 3, 3, 4, 3, 2, 4, 2]
Output: true
```

---

## Approach (HashSet)

Use a HashSet to keep track of elements already encountered while iterating through the array.

For each element:

- Attempt to add the current element to the HashSet.
- If the element already exists, a duplicate is found.
- If the element is new, continue iterating.
- If the entire array is traversed without finding a duplicate, return false.

---

## Key Insight

A HashSet stores only unique elements.

Instead of comparing every element with every other element (O(n²)), we can use HashSet insertion to detect duplicates efficiently.

The `add()` method returns `false` when the element is already present.

---

## Complexity

**Time:** O(n) average case

**Space:** O(n)

Where n is the number of elements in the array.

---

## Edge Cases

- Empty array → no duplicates.
- Single element → no duplicates.
- All elements are distinct.
- All elements are identical.
- Duplicate elements appearing at different positions.
- Negative numbers.

---

## Why HashSet Works

HashSet uses hashing to efficiently identify whether an element already exists.

Its `add()` operation takes O(1) average time, making it suitable for duplicate detection.

Unlike HashMap, we don't need to maintain an index or key-value mapping because only element existence matters.