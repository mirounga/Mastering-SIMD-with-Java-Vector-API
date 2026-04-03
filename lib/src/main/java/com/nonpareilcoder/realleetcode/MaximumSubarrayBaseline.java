package com.nonpareilcoder.realleetcode;

public class MaximumSubarrayBaseline {
  static public int compute(int[] input) {
    int maxSoFar = input[0];
    int maxEndingHere = input[0];

    for (int i = 1; i < input.length; i++) {
      // Either extend existing subarray or start new one
      int v = input[i];
      maxEndingHere = Math.max(v, maxEndingHere + v);

      // Update global maximum
      maxSoFar = Math.max(maxSoFar, maxEndingHere);
    }

    return maxSoFar;
  }
}
