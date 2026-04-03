package com.nonpareilcoder.floatingpoint;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

public class MeanDeviationLanewiseCorrected {
  static final VectorSpecies<Float> SP =
    FloatVector.SPECIES_512;

  static public float[] compute(float[] input) {
    int N = input.length;

    FloatVector _s1 = FloatVector.zero(SP);
    FloatVector _s2 = FloatVector.zero(SP);

    FloatVector _c1 = FloatVector.zero(SP);
    FloatVector _c2 = FloatVector.zero(SP);

    for (int i = 0; i < N; i += SP.length()) {
      var _mask = SP.indexInRange(i, N);

      FloatVector _v1 = FloatVector.fromArray(SP, input, i, _mask);
      FloatVector _v2 = _v1.mul(_v1);

      FloatVector _t1 = _s1.add(_v1);
      FloatVector _t2 = _s1.add(_v2);
      _c1 = _s1.sub(_t1).add(_v1).add(_c1);
      _c2 = _s2.sub(_t2).add(_v2).add(_c2);
      _s1 = _t1;
      _s2 = _t2;
    }

    float s1 = _s1.add(_c1).reduceLanes(ADD);
    float s2 = _s2.add(_c2).reduceLanes(ADD);

    float mean = (float) s1 / N;
    float dev = (float) StrictMath.sqrt(s2 * N - s1 * s1) / N;

    return new float[]{mean, dev};
  }
}
