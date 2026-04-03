package com.nonpareilcoder.floatingpoint;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

public class MeanDeviationLanewiseX4 {
  static final VectorSpecies<Float> SP =
      FloatVector.SPECIES_PREFERRED;

  static public float[] compute(float[] input) {
    int N = input.length;

    FloatVector _s01 = FloatVector.zero(SP);
    FloatVector _s02 = FloatVector.zero(SP);
    FloatVector _s11 = _s01, _s21 = _s01, _s31 = _s01;
    FloatVector _s12 = _s02, _s22 = _s02, _s32 = _s02;

    for (int i = 0; i < N; ) {
      var _mask0 = SP.indexInRange(i, N);
      FloatVector _v01 = FloatVector.fromArray(SP, input, i, _mask0);
      FloatVector _v02 = _v01.mul(_v01);
      i += SP.length();

      var _mask1 = SP.indexInRange(i, N);
      FloatVector _v11 = FloatVector.fromArray(SP, input, i, _mask1);
      FloatVector _v12 = _v11.mul(_v11);
      i += SP.length();

      var _mask2 = SP.indexInRange(i, N);
      FloatVector _v21 = FloatVector.fromArray(SP, input, i, _mask2);
      FloatVector _v22 = _v21.mul(_v21);
      i += SP.length();

      var _mask3 = SP.indexInRange(i, N);
      FloatVector _v31 = FloatVector.fromArray(SP, input, i, _mask3);
      FloatVector _v32 = _v31.mul(_v31);
      i += SP.length();

      _s01 = _s01.add(_v01);
      _s02 = _s02.add(_v02);
      _s11 = _s11.add(_v11);
      _s12 = _s12.add(_v12);
      _s21 = _s21.add(_v21);
      _s22 = _s22.add(_v22);
      _s31 = _s31.add(_v31);
      _s32 = _s32.add(_v32);
    }

    _s01 = _s01.add(_s11);
    _s02 = _s02.add(_s12);
    _s21 = _s21.add(_s31);
    _s22 = _s22.add(_s32);

    _s01 = _s01.add(_s21);
    _s02 = _s02.add(_s22);

    float s1 = _s01.reduceLanes(ADD);
    float s2 = _s02.reduceLanes(ADD);

    float mean = (float) s1 / N;
    float dev = (float) StrictMath.sqrt(s2 * N - s1 * s1) / N;

    return new float[]{mean, dev};
  }
}
