package com.nonpareilcoder.realleetcode;

public class SingleNumberTriplesScalar {
  public static int find(int[] input) {
    int ones = 0;
    int twos = 0;

    for (int num : input) {
      twos |= ones & num;

      ones ^= num;

      int threes = ones & twos;

      ones &= ~threes;
      twos &= ~threes;
    }

    return ones;
  }
}
