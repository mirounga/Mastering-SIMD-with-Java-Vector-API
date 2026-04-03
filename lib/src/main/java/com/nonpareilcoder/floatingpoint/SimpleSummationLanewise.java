package com.nonpareilcoder.floatingpoint;

import jdk.incubator.vector.*;

public class SimpleSummationLanewise {
  static final VectorSpecies<Float> SP =
    FloatVector.SPECIES_128;

  static float[] f0 = new float[]{
    33554432.0f, 33554432.0f, 33554432.0f, 33554432.0f};

  static float[] f1 = new float[]{
    2.0f, -1.0f, -33554432.0f, 2.0f};

  static float[] f2 = new float[]{
    0.0f, 0.0f, 0.0f, -33554432.0f};

  static float[] f3 = new float[]{
    -1.0f, 2.0f, 2.0f, 0.0f};

  static float[] f4 = new float[]{
    -33554432.0f, -33554432.0f, -1.0f, -1.0f};

  public static float[] add() {
    FloatVector _f0 = FloatVector.fromArray(SP, f0, 0);
    FloatVector _f1 = FloatVector.fromArray(SP, f1, 0);
    FloatVector _f2 = FloatVector.fromArray(SP, f2, 0);
    FloatVector _f3 = FloatVector.fromArray(SP, f3, 0);
    FloatVector _f4 = FloatVector.fromArray(SP, f4, 0);

    FloatVector _sum = _f0.lanewise(VectorOperators.ADD, _f1);

    _sum = _sum.lanewise(VectorOperators.ADD, _f2);
    _sum = _sum.lanewise(VectorOperators.ADD, _f3);
    _sum = _sum.lanewise(VectorOperators.ADD, _f4);

    float[] result = new float[4];
    _sum.intoArray(result, 0);

    return result;
  }
}