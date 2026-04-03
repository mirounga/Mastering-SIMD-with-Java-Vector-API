package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

import java.math.BigInteger;

public class ConvergentsOfEKaratsuba {
  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_PREFERRED;

  public static long compute() {
    BigInteger p0 = BigInteger.TWO;
    BigInteger p1 = BigInteger.ONE;

    var tmp = p0.multiply(BigInteger.valueOf(4))
      .add(p1.multiply(BigInteger.valueOf(3)));
    p1 = p0.multiply(BigInteger.valueOf(3))
      .add(p1.multiply(BigInteger.TWO));
    p0 = tmp;

    LongVector _idx =
      VectorShuffle.iota(SP, 0, 1, false)
        .toVector().reinterpretAsLongs().mul(8L).add(3);

    for (long k = 0; k < 32; k += 4 * SP.length()) {
      _idx = _idx.add(2);

      LongVector _a00 = _idx.add(1L);
      LongVector _a01 = _idx;
      LongVector _a10 = _a01;
      LongVector _a11 = _idx.sub(1L);

      for (int i = 1; i < 4; i++) {
        _idx = _idx.add(2);

        LongVector _p0 = _a00.add(_a10).mul(_idx);
        LongVector _p1 = _a01.add(_a11).mul(_idx);

        _a00 = _p0.add(_a00);
        _a10 = _p0.sub(_a10);
        _a01 = _p1.add(_a01);
        _a11 = _p1.sub(_a11);
      }

      var vl = SP.length();
      for (int i = 0; i < vl; i++) {
        tmp = p0.multiply(BigInteger.valueOf(
          _a00.lane(i))).add(p1.multiply(
          BigInteger.valueOf(_a01.lane(i))));
        p1 = p0.multiply(BigInteger.valueOf(
          _a10.lane(i))).add(p1.multiply(
          BigInteger.valueOf(_a11.lane(i))));
        p0 = tmp;
      }

      _idx = _idx.add(8L * (vl - 1));
    }

    long sum = 0;

    while (p0.compareTo(BigInteger.ZERO) != 0) {
      var divRem = p0.divideAndRemainder(BigInteger.TEN);
      sum += divRem[1].longValue();
      p0 = divRem[0];
    }

    return sum;
  }
}
