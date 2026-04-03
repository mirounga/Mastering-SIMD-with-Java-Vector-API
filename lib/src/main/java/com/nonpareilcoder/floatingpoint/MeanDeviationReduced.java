package com.nonpareilcoder.floatingpoint;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

public class MeanDeviationReduced {
  static final VectorSpecies<Float> SP =
      FloatVector.SPECIES_PREFERRED;

  static public float[] compute(float[] input) {
    int N = input.length;

    float s1 = 0.0f, s2 = 0.0f;

    for (int i = 0; i < N; i += SP.length()) {
      var _mask = SP.indexInRange(i, N);

      FloatVector _v1 = FloatVector.fromArray(SP, input, i, _mask);
      FloatVector _v2 = _v1.mul(_v1);

      s1 += _v1.reduceLanes(ADD);
      s2 += _v2.reduceLanes(ADD);
    }

    float mean = (float) s1 / N;
    float dev = (float) StrictMath.sqrt(s2 * N - s1 * s1) / N;

    return new float[]{mean, dev};
  }
}
