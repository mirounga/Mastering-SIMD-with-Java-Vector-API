package com.nonpareilcoder.floatingpoint;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomGeneratorTest {
  static final int arraySize = 10007;
  static final float arrayFloat = 3.14f;
  static final int arrayMax = 1009;
  static final long arraySeed = 42L;

  public static float[] generateFloatArray() {
    Random rand = new Random(arraySeed);
    float[] array = new float[arraySize];

    for (int i = 0; i < arraySize; i++) {
      array[i] = rand.nextFloat(arrayFloat);
    }

    return array;
  }

  public static int[] generateIntArray() {
    Random rand = new Random(arraySeed);
    int[] array = new int[arraySize];

    for (int i = 0; i < arraySize; i++) {
      array[i] = rand.nextInt(arrayMax);
    }

    return array;
  }

  @Test
  public void floatScalar() {
    float[] expected = generateFloatArray();

    float[] actual = RandomGeneratorScalar.generateFloatArray(arraySeed, arrayFloat, arraySize);

    for (int i = 0; i < arraySize; i++) {
      assertEquals(expected[i], actual[i]);
    }
  }

  @Test
  public void intScalar() {
    int[] expected = generateIntArray();

    int[] actual = RandomGeneratorScalar.generateIntArray(arraySeed, arrayMax, arraySize);

    for (int i = 0; i < arraySize; i++) {
      assertEquals(expected[i], actual[i]);
    }
  }

  @Test
  public void floatVectorized() {
    float[] expected = generateFloatArray();

    float[] actual = RandomGeneratorVectorized.generateFloatArray(arraySeed, arrayFloat, arraySize);

    for (int i = 0; i < arraySize; i++) {
      assertEquals(expected[i], actual[i]);
    }
  }

  @Test
  public void intVectorized() {
    int[] expected = generateIntArray();

    int[] actual = RandomGeneratorVectorized.generateIntArray(arraySeed, arrayMax, arraySize);

    for (int i = 0; i < arraySize; i++) {
      assertEquals(expected[i], actual[i]);
    }
  }
}
