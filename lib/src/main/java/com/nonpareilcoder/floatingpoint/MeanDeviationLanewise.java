package com.nonpareilcoder.floatingpoint;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

public class MeanDeviationLanewise {
  static final VectorSpecies<Float> SP =
      FloatVector.SPECIES_PREFERRED;

  static public float[] compute(float[] input) {
    int N = input.length;

    FloatVector _s1 = FloatVector.zero(SP);
    FloatVector _s2 = FloatVector.zero(SP);

    for (int i = 0; i < N; i += SP.length()) {
      var _mask = SP.indexInRange(i, N);

      FloatVector _v1 = FloatVector.fromArray(SP, input, i, _mask);
      FloatVector _v2 = _v1.mul(_v1);

      _s1 = _s1.add(_v1);
      _s2 = _s2.add(_v2);
    }

    float s1 = _s1.reduceLanes(ADD);
    float s2 = _s2.reduceLanes(ADD);

    float mean = (float) s1 / N;
    float dev = (float) StrictMath.sqrt(s2 * N - s1 * s1) / N;

    return new float[]{mean, dev};
  }
}

