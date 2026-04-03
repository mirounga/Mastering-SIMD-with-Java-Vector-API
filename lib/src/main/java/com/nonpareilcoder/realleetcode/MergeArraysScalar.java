package com.nonpareilcoder.realleetcode;

public class MergeArraysScalar {
  public static int[] merge(int[] input0, int[] input1) {
    int[] output = new int[input0.length + input1.length];
    int i0 = 0, i1 = 0, j = 0;

    mergeCore(input0, input1, output, i0, i1, j);

    return output;
  }

  public static void mergeCore(
    int[] input0, int[] input1, int[] output,
    int i0, int i1, int j) {
    for (; ; ) {
      if (i0 == input0.length) {
        while (i1 < input1.length)
          output[j++] = input1[i1++];

        break;
      }

      if (i1 == input1.length) {
        while (i0 < input0.length)
          output[j++] = input0[i0++];

        break;
      }

      if (input0[i0] < input1[i1])
        output[j++] = input0[i0++];
      else
        output[j++] = input1[i1++];
    }
  }
}
