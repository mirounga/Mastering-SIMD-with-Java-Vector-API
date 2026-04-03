package com.nonpareilcoder.realleetcode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.*;


public class RemoveDuplicatesTest {
  static final int arraySize = 1007;
  static final int arrayMax = 77;


  public static int[] generateRandomArray() {
    Random rand = new Random(42);
    int[] array = new int[arraySize];

    for (int i = 0; i < arraySize; i++) {
      array[i] = rand.nextInt(arrayMax);
    }

    return array;
  }

  @Test
  public void shuffle() {
    int[] baselineInput = generateRandomArray();
    Arrays.sort(baselineInput);

    int[] simdInput = Arrays.copyOf(baselineInput, arraySize);

    int baselineLength = RemoveDuplicatesScalar.compress(baselineInput);
    int simdLength = RemoveDuplicatesShuffle.compress(simdInput);

    assertEquals(baselineLength, simdLength);
    assertArrayEquals(baselineInput, simdInput);
  }

  @Test
  public void shuffleX4() {
    int[] baselineInput = generateRandomArray();
    Arrays.sort(baselineInput);

    int[] simdInput = Arrays.copyOf(baselineInput, arraySize);

    int baselineLength = RemoveDuplicatesScalar.compress(baselineInput);
    int simdLength = RemoveDuplicatesShuffleX4.compress(simdInput);

    assertEquals(baselineLength, simdLength);
    assertArrayEquals(baselineInput, simdInput);
  }

  @Test
  public void load() {
    int[] baselineInput = generateRandomArray();
    Arrays.sort(baselineInput);

    int[] simdInput = Arrays.copyOf(baselineInput, arraySize);

    int baselineLength = RemoveDuplicatesScalar.compress(baselineInput);
    int simdLength = RemoveDuplicatesLoad.compress(simdInput);

    assertEquals(baselineLength, simdLength);
    assertArrayEquals(baselineInput, simdInput);
  }
}
