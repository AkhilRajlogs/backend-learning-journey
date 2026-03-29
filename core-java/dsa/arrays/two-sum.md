# Two Sum

## Problem
Given an array of integers, return indices of the two numbers such that they add up to a specific target.

---

## Approach (HashMap)

Use a HashMap to store value → index while iterating.

For each element:
- Calculate complement = target - current
- Check if complement exists in map
- If yes → solution found
- If no → store current element in map

---

## Key Insight

Instead of checking all pairs (O(n²)), we reduce it to O(n) using hashing.

---

## Complexity

Time: O(n)  
Space: O(n)

---

## Edge Cases

- No solution → return empty array  
- Duplicate numbers  
- Negative numbers  

---

## Why HashMap Works

HashMap provides O(1) lookup, allowing us to find complement efficiently.