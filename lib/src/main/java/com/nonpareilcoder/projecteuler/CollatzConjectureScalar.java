package com.nonpareilcoder.projecteuler;

public class CollatzConjectureScalar {
  static public long longestOrbit(int problemSize) {
    int max = 1, start = 1;

    for (int i = 2; i < problemSize; i++) {
      int orbit = 1;
      for (long val = i; val > 1; orbit++) {
        if ((val & 1) == 1)
          val = 3 * val + 1;
        else
          val >>= 1;
      }

      if (orbit > max) {
        max = orbit;
        start = i;
      }
    }

    return start;
  }
}
