package com.nonpareilcoder.realleetcode;

import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaximumSubarrayTest {
  static final int arraySeed = 42;
  static final int arraySize = 1007;
  static final int arrayMax = 10077;


  public static int[] generateRandomArray() {
    Random rand = new Random(arraySeed);
    int[] array = new int[arraySize];

    for (int i = 0; i < arraySize; i++) {
      array[i] = rand.nextInt(arrayMax) - arrayMax / 2;
    }

    return array;
  }

  @Test
  public void prefix() {
    int[] input = generateRandomArray();

    int expected = MaximumSubarrayBaseline.compute(input);

    int actual = MaximumSubarrayPrefix.compute(input);

    assertEquals(expected, actual);
  }

  @Test
  public void preferredX8() {
    int[] input = generateRandomArray();

    int expected = MaximumSubarrayBaseline.compute(input);
    int actual = MaximumSubarrayPreferredX8.compute(input);

    assertEquals(expected, actual);
  }

  @Test
  public void shortX8() {
    int[] input = generateRandomArray();

    int expected = MaximumSubarrayBaseline.compute(input);

    int actual = MaximumSubarray128X8.compute(input);

    assertEquals(expected, actual);
  }
}
