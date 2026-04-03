package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class EvenFibonacciNumbersVectorized {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;

  public static int compute(int limit) {
    final IntVector _zero = IntVector.zero(SP);
    final IntVector _one = IntVector.broadcast(SP, 1);

    IntVector _a00 = _one, _a01 = _one, _a10 = _one;
    IntVector _a11 = _zero;

    for (int step = 1; step < SP.length(); step <<= 1) {
      IntVector _b00 = _a00.slice(step, _one);
      IntVector _b01 = _a01.slice(step, _zero);
      IntVector _b10 = _a10.slice(step, _zero);
      IntVector _b11 = _a11.slice(step, _one);

      IntVector _c00 = _a00.mul(_b00).add(_a01.mul(_b10));
      IntVector _c01 = _a00.mul(_b01).add(_a01.mul(_b11));
      IntVector _c10 = _a10.mul(_b00).add(_a11.mul(_b10));
      IntVector _c11 = _a10.mul(_b01).add(_a11.mul(_b11));

      _a00 = _c00;
      _a01 = _c01;
      _a10 = _c10;
      _a11 = _c11;
    }

    IntVector _sum = _zero;

    int f0 = 2, f1 = 1;

    while (true) {
      IntVector _c0 = _a00.mul(f0).add(_a01.mul(f1));
      IntVector _c1 = _a10.mul(f0).add(_a11.mul(f1));

      var _mask = _c0.compare(LE, limit);

      var _even = _c0.and(0x01).compare(EQ, _zero, _mask);
      _sum = _sum.add(_c0, _even);

      if (!_mask.allTrue())
        break;

      f0 = _c0.lane(0);
      f1 = _c1.lane(0);
    }

    int sum = 2 + _sum.reduceLanes(ADD);

    return sum;
  }
}
