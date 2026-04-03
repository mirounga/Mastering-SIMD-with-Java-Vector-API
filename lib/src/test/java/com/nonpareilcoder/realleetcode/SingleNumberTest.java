package com.nonpareilcoder.realleetcode;

import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleNumberTest {
  static final int arraySize = 1007;
  static final int arrayMax = 10077;

  public static int[] fillRandomArray(int seed, int[] input, int repeats, int unique) {
    Random rand = new Random(seed);
    final int repeatCount = (input.length - unique) / repeats;

    int idx = 0;
    for (int i = 0; i < repeatCount; i++) {
      int next = rand.nextInt(arrayMax);
      for (int j = 0; j < repeats; j++)
        input[idx++] = next;
    }

    int[] uniques = new int[unique];

    for (int i = 0; i < unique; i++) {
      uniques[i] = input[idx++] = rand.nextInt(arrayMax);
    }

    for (int i = idx; i-- > 0; ) {
      int j = rand.nextInt(i + 1);
      int temp = input[i];
      input[i] = input[j];
      input[j] = temp;
    }

    Arrays.sort(uniques);

    return uniques;
  }

  @Test
  public void pairsScalar() {
    final int repeats = 2;

    int[] input = new int[arraySize * repeats + 1];

    int[] expected = fillRandomArray(42, input, repeats, 1);

    int actual = SingleNumberPairsScalar.find(input);

    assertEquals(expected[0], actual);
  }

  @Test
  public void pairsVectorized() {
    final int repeats = 2;

    int[] input = new int[arraySize * repeats + 1];

    int[] expected = fillRandomArray(42, input, repeats, 1);

    int actual = SingleNumberPairsVectorized.find(input);

    assertEquals(expected[0], actual);
  }

  @Test
  public void pairs2Scalar() {
    final int repeats = 2;

    int[] input = new int[arraySize * repeats + 1];

    int[] expected = fillRandomArray(42, input, repeats, 2);

    int[] actual = SingleNumberPairs2Scalar.find(input);

    assertEquals(expected[0], actual[0]);
    assertEquals(expected[1], actual[1]);
  }

  @Test
  public void pairs2Vectorized() {
    final int repeats = 2;

    int[] input = new int[arraySize * repeats + 1];

    int[] expected = fillRandomArray(42, input, repeats, 2);

    int[] actual = SingleNumberPairs2Vectorized.find(input);

    assertEquals(expected[0], actual[0]);
    assertEquals(expected[1], actual[1]);
  }

  @Test
  public void triplesScalar() {
    final int repeats = 3;

    int[] input = new int[arraySize * repeats + 1];

    int[] expected = fillRandomArray(42, input, repeats, 1);

    int actual = SingleNumberTriplesScalar.find(input);

    assertEquals(expected[0], actual);
  }

  @Test
  public void triplesVectorized() {
    final int repeats = 3;

    int[] input = new int[arraySize * repeats + 1];

    int[] expected = fillRandomArray(42, input, repeats, 1);

    int actual = SingleNumberTriplesVectorized.find(input);

    assertEquals(expected[0], actual);
  }
}
