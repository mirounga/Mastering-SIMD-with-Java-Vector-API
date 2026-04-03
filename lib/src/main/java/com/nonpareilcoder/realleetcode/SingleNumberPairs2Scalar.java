package com.nonpareilcoder.realleetcode;

import java.util.*;

public class SingleNumberPairs2Scalar {
  public static int[] find(int[] input) {
    int result = 0;
    for (int num : input) {
      result ^= num;
    }

    int set = result & (-result);

    int[] uniques = new int[2];

    for (int num : input) {
      if ((num & set) == set)
        uniques[0] ^= num;
      else
        uniques[1] ^= num;
    }

    Arrays.sort(uniques);

    return uniques;
  }
}
