package com.nonpareilcoder.realleetcode;

public class SingleNumberPairsScalar {
  public static int find(int[] input) {
    int result = 0;

    for (int num : input) {
      result ^= num;
    }

    return result;
  }
}
