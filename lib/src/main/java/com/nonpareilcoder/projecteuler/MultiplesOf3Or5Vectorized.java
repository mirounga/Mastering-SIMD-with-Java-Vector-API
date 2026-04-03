package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class MultiplesOf3Or5Vectorized {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;

  static final int c3 = 0x55555556;
  static final int c5 = 0x33333334;

  public static int compute(int n) {
    var _idx = VectorShuffle.iota(SP, 0, 1, false)
      .toVector().reinterpretAsInts();

    IntVector _sum = IntVector.zero(SP);

    for (int i = 0; i < n; i += SP.length()) {
      var _mask = SP.indexInRange(i, n);

      var _mask3 = _idx.mul(c3).compare(ULT, c3, _mask);
      var _mask5 = _idx.mul(c5).compare(ULT, c5, _mask);

      _sum = _sum.add(_idx, _mask3.or(_mask5));

      _idx = _idx.add(SP.length());
    }

    int sum = _sum.reduceLanes(ADD);

    return sum;
  }
}
