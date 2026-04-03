package com.nonpareilcoder.realleetcode;

public class RemoveDuplicatesScalar {
  public static int compress(int[] array) {
    int arraySize = array.length;

    int writeIndex = 1;

    if (arraySize > 0) {
      for (int ri = 1; ri < arraySize; ri++) {
        if (array[ri] != array[ri - 1])
          array[writeIndex++] = array[ri];
      }
    }

    return writeIndex;
  }
}