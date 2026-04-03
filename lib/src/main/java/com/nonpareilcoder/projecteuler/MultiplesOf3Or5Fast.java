package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;

import static jdk.incubator.vector.VectorOperators.*;

public class MultiplesOf3Or5Fast {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_128;

  static final IntVector _c = IntVector
    .fromArray(SP, new int[]{0x5556, 0x3334, 0x1112, 0}, 0);
  static final IntVector _d = IntVector
    .fromArray(SP, new int[]{3, 5, -15, 0}, 0);

  public static int compute(int n) {
    var _klm = _c.mul(n - 1).lanewise(LSHR, 16);

    var _sum = _klm.add(1).mul(_klm).lanewise(LSHR, 1).mul(_d);

    int sum = _sum.reduceLanes(ADD);

    return sum;
  }
}
