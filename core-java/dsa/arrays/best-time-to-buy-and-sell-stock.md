# Best Time to Buy and Sell Stock

## Problem

Given an array `prices` where `prices[i]` represents the price of a stock on the `i`th day, determine the maximum profit that can be achieved by buying once and selling once.

The buying day must come before the selling day.

Return `0` if no profit can be made.

**Examples**

```text
Input: prices = [7, 1, 5, 3, 6, 4]
Output: 5

Explanation: Buy at 1 and sell at 6.

Input: prices = [7, 6, 4, 3, 1]
Output: 0

Explanation: Prices keep decreasing, so no profit is possible.
```

---

## Approach (Greedy)

Use a single traversal of the array while maintaining:

- `minPrice`: The minimum stock price encountered so far.
- `maxProfit`: The maximum profit calculated so far.

For each price:

- Update the minimum price if the current price is lower.
- Calculate the potential profit by subtracting the minimum price from the current price.
- Update the maximum profit if the potential profit is greater.

Return the maximum profit after traversing the array.

---

## Key Insight

We only need to remember the lowest buying price encountered before the current selling day.

This avoids checking every possible buying and selling combination.

The algorithm makes one pass through the array.

---

## Complexity

**Time:** O(n)

**Space:** O(1)

Where n is the number of elements in the array.

---

## Edge Cases

- Empty or single-element array.
- Continuously decreasing prices.
- Continuously increasing prices.
- Multiple opportunities to make a profit.
- Repeated stock prices.

---

## Why Greedy Works

At every position, the algorithm considers the best possible profit from buying at the lowest price encountered so far and selling at the current price.

By maintaining the minimum price and maximum profit, it finds the optimal result without requiring nested loops.
