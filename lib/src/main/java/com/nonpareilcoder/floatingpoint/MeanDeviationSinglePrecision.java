package com.nonpareilcoder.floatingpoint;

public class MeanDeviationSinglePrecision {
  static public float[] compute(float[] input) {
    int N = input.length;

    float mean = 0.0f;
    for (int i = N; i-- > 0; ) mean += input[i];
    mean /= N;

    float dev = 0.0f;
    for (float v : input) dev += (v - mean) * (v - mean);
    dev = (float) StrictMath.sqrt(dev / N);

    return new float[]{mean, dev};
  }
}
