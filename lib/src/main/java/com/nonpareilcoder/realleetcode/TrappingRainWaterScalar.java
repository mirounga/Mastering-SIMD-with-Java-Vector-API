package com.nonpareilcoder.realleetcode;

public class TrappingRainWaterScalar {
  public static int trap(int[] input) {
    int[] prefixMax = new int[input.length];
    int[] suffixMax = new int[input.length];

    computePrefixMax(input, prefixMax);
    computeSuffixMax(input, suffixMax);

    return reduceHeights(input, prefixMax, suffixMax);
  }

  static void computePrefixMax(int[] input, int[] prefixMax) {
    for (int acc = 0, i = 0; i < input.length; i++) {
      acc = Math.max(acc, input[i]);
      prefixMax[i] = acc;
    }
  }

  static void computeSuffixMax(int[] input, int[] suffixMax) {
    for (int acc = 0, i = input.length; i-- > 0; ) {
      acc = Math.max(acc, input[i]);
      suffixMax[i] = acc;
    }
  }

  static int reduceHeights(
    int[] input, int[] prefixMax, int[] suffixMax) {
    int trapped = 0;

    for (int i = 0; i < prefixMax.length; i++) {
      int minMax = Math.min(prefixMax[i], suffixMax[i]);
      trapped += minMax - input[i];
    }

    return trapped;
  }
}
