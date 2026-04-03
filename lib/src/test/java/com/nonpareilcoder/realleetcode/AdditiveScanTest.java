package com.nonpareilcoder.realleetcode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.*;

public class AdditiveScanTest {
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

  public static void addBaselinePrefix(int[] input, int[] output) {
    if (input.length != output.length)
      throw new ArrayIndexOutOfBoundsException(output.length);

    int acc = 0;
    for (int i = 0; i < input.length; i++) {
      acc += input[i];
      output[i] = acc;
    }
  }

  public static void addBaselineSuffix(int[] input, int[] output) {
    if (input.length != output.length)
      throw new ArrayIndexOutOfBoundsException(output.length);

    int acc = 0;
    for (int i = input.length; i-- > 0; ) {
      acc += input[i];
      output[i] = acc;
    }
  }

  @Test
  public void testHorizontalPrefix() {
    int[] input = generateRandomArray();

    int[] baseline = new int[arraySize];
    int[] output = new int[arraySize];

    addBaselinePrefix(input, baseline);
    AdditiveScanHorizontal.prefixAdd(input, output);

    assertArrayEquals(baseline, output);
  }

  @Test
  public void testHorizontalSuffix() {
    int[] input = generateRandomArray();

    int[] baseline = new int[arraySize];
    int[] output = new int[arraySize];

    addBaselineSuffix(input, baseline);
    AdditiveScanHorizontal.suffixAdd(input, output);

    assertArrayEquals(baseline, output);
  }

  @Test
  public void testHorizontalPrefixX4() {
    int[] input = generateRandomArray();

    int[] baseline = new int[arraySize];
    int[] output = new int[arraySize];

    addBaselinePrefix(input, baseline);
    AdditiveScanHorizontalX4.prefixAdd(input, output);

    assertArrayEquals(baseline, output);
  }

  @Test
  public void testHorizontalSuffixX4() {
    int[] input = generateRandomArray();

    int[] baseline = new int[arraySize];
    int[] output = new int[arraySize];

    addBaselineSuffix(input, baseline);
    AdditiveScanHorizontalX4.suffixAdd(input, output);

    assertArrayEquals(baseline, output);
  }

  @Test
  public void testTransposedPrefixX4() {
    int[] input = generateRandomArray();

    int[] baseline = new int[arraySize];
    int[] output = new int[arraySize];

    addBaselinePrefix(input, baseline);
    AdditiveScanTransposedX4.prefixAdd(input, output);

    assertArrayEquals(baseline, output);
  }

  @Test
  public void testTransposedSuffixX4() {
    int[] input = generateRandomArray();

    int[] baseline = new int[arraySize];
    int[] output = new int[arraySize];

    addBaselineSuffix(input, baseline);
    AdditiveScanTransposedX4.suffixAdd(input, output);

    assertArrayEquals(baseline, output);
  }

  @Test
  public void testTransposedPrefixX8() {
    int[] input = generateRandomArray();

    int[] baseline = new int[arraySize];
    int[] output = new int[arraySize];

    addBaselinePrefix(input, baseline);
    AdditiveScanTransposedX8.prefixAdd(input, output);

    assertArrayEquals(baseline, output);
  }

  @Test
  public void testTransposedSuffixX8() {
    int[] input = generateRandomArray();

    int[] baseline = new int[arraySize];
    int[] output = new int[arraySize];

    addBaselineSuffix(input, baseline);
    AdditiveScanTransposedX8.suffixAdd(input, output);

    assertArrayEquals(baseline, output);
  }

  @Test
  public void testTransposedPrefixX16() {
    int[] input = generateRandomArray();

    int[] baseline = new int[arraySize];
    int[] output = new int[arraySize];

    addBaselinePrefix(input, baseline);
    AdditiveScanTransposedX16.prefixAdd(input, output);

    assertArrayEquals(baseline, output);
  }

  @Test
  public void testTransposedSuffixX16() {
    int[] input = generateRandomArray();

    int[] baseline = new int[arraySize];
    int[] output = new int[arraySize];

    addBaselineSuffix(input, baseline);
    AdditiveScanTransposedX16.suffixAdd(input, output);

    assertArrayEquals(baseline, output);
  }
}
