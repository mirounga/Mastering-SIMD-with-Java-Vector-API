package com.nonpareilcoder.realleetcode;

public class AdditiveScanScalar {
  public static void prefixAdd(int[] input, int[] output) {
    int acc = 0;
    for (int i = 0; i < input.length; i++) {
      acc += input[i];
      output[i] = acc;
    }
  }

  public static void suffixAdd(int[] input, int[] output) {
    int acc = 0;
    for (int i = input.length; i-- > 0; ) {
      acc += input[i];
      output[i] = acc;
    }
  }

}
