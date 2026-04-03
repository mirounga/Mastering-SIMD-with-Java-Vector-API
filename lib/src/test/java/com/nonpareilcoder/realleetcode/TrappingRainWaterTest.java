package com.nonpareilcoder.realleetcode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.*;

public class TrappingRainWaterTest {
  static final int arraySize = 100_007;
  static final int arrayMax = 17;


  public static int[] generateRandomArray() {
    Random rand = new Random(42);
    int[] array = new int[arraySize];

    for (int i = 0; i < arraySize; i++) {
      array[i] = rand.nextInt(arrayMax);
    }

    return array;
  }

  @Test
  public void scalar() {
    int[] heights1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
    int trapped1 = TrappingRainWaterScalar.trap(heights1);
    assertEquals(6, trapped1);

    int[] heights2 = {4, 2, 0, 3, 2, 5};
    int trapped2 = TrappingRainWaterScalar.trap(heights2);
    assertEquals(9, trapped2);

    int[] heights3 = {2, 0, 1, 2, 1, 3, 0, 1};
    int trapped3 = TrappingRainWaterScalar.trap(heights3);
    assertEquals(5, trapped3);
  }

  @Test
  public void horizonal() {
    int[] heights = generateRandomArray();

    int baseline = TrappingRainWaterScalar.trap(heights);
    int trapped = TrappingRainWaterHorizontal.trap(heights);

    assertEquals(baseline, trapped);
  }

  @Test
  public void horizonalX4() {
    int[] heights = generateRandomArray();

    int baseline = TrappingRainWaterScalar.trap(heights);
    int trapped = TrappingRainWaterHorizontalX4.trap(heights);

    assertEquals(baseline, trapped);
  }

  @Test
  public void transposedX4() {
    int[] heights = generateRandomArray();

    int baseline = TrappingRainWaterScalar.trap(heights);
    int trapped = TrappingRainWaterTransposedX4.trap(heights);

    assertEquals(baseline, trapped);
  }

  @Test
  public void transposedX16() {
    int[] heights = generateRandomArray();

    int baseline = TrappingRainWaterScalar.trap(heights);
    int trapped = TrappingRainWaterTransposedX16.trap(heights);

    assertEquals(baseline, trapped);
  }

  @Test
  public void memoizedX16() {
    int[] heights = generateRandomArray();

    int baseline = TrappingRainWaterScalar.trap(heights);
    int trapped = TrappingRainWaterMemoizedX16.trap(heights);

    assertEquals(baseline, trapped);
  }

  @Test
  public void shortX16() {
    int[] heights = generateRandomArray();

    int baseline = TrappingRainWaterScalar.trap(heights);
    int trapped = TrappingRainWater128X16.trap(heights);

    assertEquals(baseline, trapped);
  }
}
