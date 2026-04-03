package com.nonpareilcoder.floatingpoint;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MeanDeviationTest {
  static final int arraySize = 10007;
  static final float arrayMax = 1.0f;
  static final long arraySeed = 42L;

  public static float[] generateRandomArray() {
    Random rand = new Random(arraySeed);
    float[] array = new float[arraySize];

    for (int i = 0; i < arraySize; i++) {
      array[i] = rand.nextFloat(arrayMax);
    }

    return array;
  }

  static final float expectedMean = 0.50682557f;
  static final float expectedDeviation = 0.28694576f;

  @Test
  public void doubleCorrected() {
    float[] input = generateRandomArray();

    float[] actual = MeanDeviationDoubleCorrected.compute(input);

    System.out.println(Arrays.toString(actual));

    assertEquals(expectedMean, actual[0], 1e-7f);
    assertEquals(expectedDeviation, actual[1], 1e-7f);
  }

  @Test
  public void single() {
    float[] input = generateRandomArray();

    float[] actual = MeanDeviationSinglePrecision.compute(input);

    System.out.println(Arrays.toString(actual));

    assertEquals(expectedMean, actual[0], 1e-6f);
    assertEquals(expectedDeviation, actual[1], 1e-6f);
  }

  @Test
  public void singlePass() {
    float[] input = generateRandomArray();

    float[] actual = MeanDeviationSinglePass.compute(input);

    System.out.println(Arrays.toString(actual));

    assertEquals(expectedMean, actual[0], 1e-6f);
    assertEquals(expectedDeviation, actual[1], 1e-6f);
  }

  @Test
  public void corrected() {
    float[] input = generateRandomArray();

    float[] actual = MeanDeviationSingleCorrected.compute(input);

    System.out.println(Arrays.toString(actual));

    assertEquals(expectedMean, actual[0], 1e-7f);
    assertEquals(expectedDeviation, actual[1], 1e-7f);
  }

  @Test
  public void reduced() {
    float[] input = generateRandomArray();

    float[] actual = MeanDeviationReduced.compute(input);

    System.out.println(Arrays.toString(actual));

    assertEquals(expectedMean, actual[0], 1e-6f);
    assertEquals(expectedDeviation, actual[1], 1e-6f);
  }

  @Test
  public void lanewise() {
    float[] input = generateRandomArray();

    float[] actual = MeanDeviationLanewise.compute(input);

    System.out.println(Arrays.toString(actual));

    assertEquals(expectedMean, actual[0], 1e-7f);
    assertEquals(expectedDeviation, actual[1], 1e-7f);
  }

  @Test
  public void lanewiseX4() {
    float[] input = generateRandomArray();

    float[] actual = MeanDeviationLanewiseX4.compute(input);

    System.out.println(Arrays.toString(actual));

    assertEquals(expectedMean, actual[0], 1e-7f);
    assertEquals(expectedDeviation, actual[1], 1e-7f);
  }

  @Test
  public void lanewiseCorrected() {
    float[] input = generateRandomArray();

    float[] actual = MeanDeviationLanewiseCorrected.compute(input);

    System.out.println(Arrays.toString(actual));

    assertEquals(expectedMean, actual[0], 1e-7f);
    assertEquals(expectedDeviation, actual[1], 1e-7f);
  }

  @Test
  public void lanewiseCorrectedX4() {
    float[] input = generateRandomArray();

    float[] actual = MeanDeviationLanewiseCorrectedX4.compute(input);

    System.out.println(Arrays.toString(actual));

    assertEquals(expectedMean, actual[0], 1e-7f);
    assertEquals(expectedDeviation, actual[1], 1e-7f);
  }
}
