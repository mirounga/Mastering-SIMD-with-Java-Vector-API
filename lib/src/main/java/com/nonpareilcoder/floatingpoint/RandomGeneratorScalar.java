package com.nonpareilcoder.floatingpoint;

public class RandomGeneratorScalar {
  static final long a = 25_214_903_917L;
  static final long b = 11L;
  static final long mask = (1L << 48) - 1;

  static float scale = ((float) (1 << 24));

  static public float[] generateFloatArray(
    long seed, float arrayMax, int arraySize) {
    float[] result = new float[arraySize];

    long val = (seed ^ a) & mask;

    for (int i = 0; i < arraySize; i++) {
      val = (val * a + b) & mask;

      int bits = (int) (val >>> 24);
      result[i] = bits / scale * arrayMax;
    }

    return result;
  }

  static public int[] generateIntArray(
    long seed, int arrayMax, int arraySize) {
    int[] result = new int[arraySize];

    long val = (seed ^ a) & mask;

    for (int i = 0; i < arraySize; i++) {
      int bits, rem;
      do {
        val = (val * a + b) & mask;
        bits = (int) (val >>> 17);
        rem = bits % arrayMax;
      } while (bits - rem + (arrayMax - 1) < 0);

      result[i] = rem;
    }

    return result;
  }
}
