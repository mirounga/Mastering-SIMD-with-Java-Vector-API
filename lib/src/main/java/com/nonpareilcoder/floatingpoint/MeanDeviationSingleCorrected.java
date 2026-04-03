package com.nonpareilcoder.floatingpoint;

public class MeanDeviationSingleCorrected {
  static public float[] compute(float[] input) {
    int N = input.length;

    float s1 = 0.0f, s2 = 0.0f;
    float c1 = 0.0f, c2 = 0.0f;

    for (float v : input) {
      float t1 = s1 + v;
      c1 += (s1 - t1) + v;
      s1 = t1;

      float v2 = v * v;
      float t2 = s2 + v2;
      c2 += (s2 - t2) + v2;
      s2 = t2;
    }

    s1 += c1;
    s2 += c2;

    float mean = (float) s1 / N;
    float dev = (float) StrictMath.sqrt(s2 * N - s1 * s1) / N;

    return new float[]{mean, dev};
  }
}
