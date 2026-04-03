package com.nonpareilcoder.realleetcode;

public class MaximumSubarrayPrefix {
  static public int compute(int[] input) {
    int prefix_sum = 0, prefix_min = 0, max = 0;
    for (int i = 0; i < input.length; i++) {
      max = Math.max(max, prefix_sum - prefix_min);
      prefix_min = Math.min(prefix_min, prefix_sum);
      prefix_sum += input[i];
    }
    max = Math.max(max, prefix_sum - prefix_min);

    return max;
  }
}
