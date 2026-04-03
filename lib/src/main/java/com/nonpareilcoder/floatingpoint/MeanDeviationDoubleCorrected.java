package com.nonpareilcoder.floatingpoint;

public class MeanDeviationDoubleCorrected {
  static public float[] compute(float[] input) {
    int N = input.length;

    double s1 = 0.0, s2 = 0.0;
    double c1 = 0.0f, c2 = 0.0f;

    for (float v : input) {
      double t1 = s1 + v;
      c1 += (s1 - t1) + v;
      s1 = t1;

      double v2 = v * v;
      double t2 = s2 + v2;
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
