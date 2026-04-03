package com.nonpareilcoder.realleetcode;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MergeArraysTest {
  static final int arraySize = 1007;
  static final int arrayMax = 10077;


  public static int[] generateRandomArray(int seed) {
    Random rand = new Random(seed);
    int[] array = new int[arraySize];

    for (int i = 0; i < arraySize; i++) {
      array[i] = rand.nextInt(arrayMax);
    }

    return array;
  }

  public void assertArraySorted(int[] array) {
    for (int i = 1; i < array.length; i++) {
      assertTrue(array[i - 1] <= array[i]);
    }
  }

  @Test
  public void scalar() {
    int[] input0 = generateRandomArray(42);
    Arrays.sort(input0);

    int[] input1 = generateRandomArray(54);
    Arrays.sort(input1);

    int[] output = MergeArraysScalar.merge(input0, input1);

    assertArraySorted(output);
  }

  @Test
  public void bitonic() {
    int[] input0 = generateRandomArray(42);
    Arrays.sort(input0);

    int[] input1 = generateRandomArray(54);
    Arrays.sort(input1);

    int[] output = MergeArraysBitonic.merge(input0, input1);

    assertArraySorted(output);
  }

  @Test
  public void unrolled() {
    int[] input0 = generateRandomArray(42);
    Arrays.sort(input0);

    int[] input1 = generateRandomArray(54);
    Arrays.sort(input1);

    int[] output = MergeArraysUnrolled.merge(input0, input1);

    assertArraySorted(output);
  }

  @Test
  public void asymmetric() {
    int[] input0 = generateRandomArray(42);
    Arrays.sort(input0);

    int[] input1 = generateRandomArray(54);
    Arrays.sort(input1);

    int[] output = MergeArraysAsymmetric.merge(input0, input1);

    assertArraySorted(output);
  }

  @Test
  public void scatter() {
    int[] input0 = generateRandomArray(42);
    Arrays.sort(input0);

    int[] input1 = generateRandomArray(54);
    Arrays.sort(input1);

    int[] output = MergeArraysScatter.merge(input0, input1);

    assertArraySorted(output);
  }
}
