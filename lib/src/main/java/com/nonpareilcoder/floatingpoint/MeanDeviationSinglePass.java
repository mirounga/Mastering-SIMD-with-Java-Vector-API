package com.nonpareilcoder.floatingpoint;

public class MeanDeviationSinglePass {
  static public float[] compute(float[] input) {
    int N = input.length;

    float s1 = 0.0f, s2 = 0.0f;

    for (float v : input) {
      float v2 = v * v;

      s1 += v;
      s2 += v2;
    }

    float mean = (float) s1 / N;
    float dev = (float) StrictMath.sqrt(s2 * N - s1 * s1) / N;

    return new float[]{mean, dev};
  }
}
