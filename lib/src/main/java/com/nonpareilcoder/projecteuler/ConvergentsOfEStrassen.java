package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

import java.math.BigInteger;

public class ConvergentsOfEStrassen {
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
        .toVector().reinterpretAsLongs().mul(4L).add(1);

    for (long k = 0; k < 32; k += 4 * SP.length()) {
      _idx = _idx.add(1);

      LongVector _a11 = _idx.add(_idx);
      LongVector _a10 = _a11.add(1);
      LongVector _a01 = _a10;
      LongVector _a00 = _a11.add(2);

      for (int i = 1; i < 4; i++) {
        _idx = _idx.add(1);

        LongVector _b11 = _idx.add(_idx);
        LongVector _b10 = _b11.add(1);
        LongVector _b01 = _b10;
        LongVector _b00 = _b11.add(2);

        LongVector _m1 = _b00.add(_b11).mul(_a00.add(_a11));
        LongVector _m2 = _b10.add(_b11).mul(_a00);
        LongVector _m3 = _a01.sub(_a11).mul(_b00);
        LongVector _m4 = _a10.sub(_a00).mul(_b11);
        LongVector _m5 = _b00.add(_b01).mul(_a11);
        LongVector _m6 = _b10.sub(_b00).mul(_a00.add(_a01));
        LongVector _m7 = _b01.sub(_b11).mul(_a10.add(_a11));

        _a00 = _m1.add(_m4).sub(_m5).add(_m7);
        _a01 = _m3.add(_m5);
        _a10 = _m2.add(_m4);
        _a11 = _m1.sub(_m2).add(_m3).add(_m6);
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

      _idx = _idx.add(4L * (vl - 1));
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
