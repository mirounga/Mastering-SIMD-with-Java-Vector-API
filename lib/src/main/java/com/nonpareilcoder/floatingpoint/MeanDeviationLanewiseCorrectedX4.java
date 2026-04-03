package com.nonpareilcoder.floatingpoint;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

public class MeanDeviationLanewiseCorrectedX4 {
  static final VectorSpecies<Float> SP =
    FloatVector.SPECIES_512;

  static public float[] compute(float[] input) {
    int N = input.length;

    FloatVector _s01 = FloatVector.zero(SP);
    FloatVector _s02 = FloatVector.zero(SP);
    FloatVector _s11 = _s01, _s21 = _s01, _s31 = _s01;
    FloatVector _s12 = _s02, _s22 = _s02, _s32 = _s02;

    FloatVector _c01 = FloatVector.zero(SP);
    FloatVector _c02 = FloatVector.zero(SP);
    FloatVector _c11 = _c01, _c21 = _c01, _c31 = _c01;
    FloatVector _c12 = _c02, _c22 = _c02, _c32 = _c02;

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

      FloatVector _t01 = _s01.add(_v01);
      FloatVector _t02 = _s01.add(_v02);
      _c01 = _s01.sub(_t01).add(_v01).add(_c01);
      _c02 = _s02.sub(_t02).add(_v02).add(_c02);
      _s01 = _t01;
      _s02 = _t02;

      FloatVector _t11 = _s11.add(_v11);
      FloatVector _t12 = _s11.add(_v12);
      _c11 = _s11.sub(_t11).add(_v11).add(_c11);
      _c12 = _s12.sub(_t12).add(_v12).add(_c12);
      _s11 = _t11;
      _s12 = _t12;

      FloatVector _t21 = _s21.add(_v21);
      FloatVector _t22 = _s21.add(_v22);
      _c21 = _s21.sub(_t21).add(_v21).add(_c21);
      _c22 = _s22.sub(_t22).add(_v22).add(_c22);
      _s21 = _t21;
      _s22 = _t22;

      FloatVector _t31 = _s31.add(_v31);
      FloatVector _t32 = _s31.add(_v32);
      _c31 = _s31.sub(_t31).add(_v31).add(_c31);
      _c32 = _s32.sub(_t32).add(_v32).add(_c32);
      _s31 = _t31;
      _s32 = _t32;
    }

    _s01 = _s01.add(_s11);
    _s02 = _s02.add(_s12);
    _s21 = _s21.add(_s31);
    _s22 = _s22.add(_s32);

    _c01 = _c01.add(_c11);
    _c02 = _c02.add(_c12);
    _c21 = _c21.add(_c31);
    _c22 = _c22.add(_c32);

    _s01 = _s01.add(_s21);
    _s02 = _s02.add(_s22);

    _c01 = _c01.add(_c21);
    _c02 = _c02.add(_c22);

    float s1 = _s01.add(_c01).reduceLanes(ADD);
    float s2 = _s02.add(_c02).reduceLanes(ADD);

    float mean = (float) s1 / N;
    float dev = (float) StrictMath.sqrt(s2 * N - s1 * s1) / N;

    return new float[]{mean, dev};
  }
}
