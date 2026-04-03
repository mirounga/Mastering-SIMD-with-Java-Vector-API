package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

import java.math.BigInteger;

public class ConvergentsOfEVertical {
  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_PREFERRED;

  public static long compute() {
    BigInteger p0 = BigInteger.TWO;
    BigInteger p1 = BigInteger.ONE;

    var v4 = BigInteger.valueOf(4);
    var v3 = BigInteger.valueOf(3);
    var tmp = p0.multiply(v4).add(p1.multiply(v3));
    p1 = p0.multiply(v3).add(p1.multiply(BigInteger.TWO));
    p0 = tmp;

    final int vl = SP.length();
    var _idx = VectorShuffle.iota(SP, 0, 1, false)
      .toVector().reinterpretAsLongs().mul(4L).add(1);

    for (long k = 0; k < 32; k += 4 * vl) {
      _idx = _idx.add(1);

      var _a11 = _idx.add(_idx);
      var _a10 = _a11.add(1);
      var _a01 = _a10;
      var _a00 = _a11.add(2);

      for (int i = 1; i < 4; i++) {
        _idx = _idx.add(1);

        var _b11 = _idx.add(_idx);
        var _b10 = _b11.add(1);
        var _b01 = _b10;
        var _b00 = _b11.add(2);

        var _c00 = _b00.mul(_a00).add(_b01.mul(_a10));
        var _c01 = _b00.mul(_a01).add(_b01.mul(_a11));
        var _c10 = _b10.mul(_a00).add(_b11.mul(_a10));
        var _c11 = _b10.mul(_a01).add(_b11.mul(_a11));

        _a00 = _c00;
        _a01 = _c01;
        _a10 = _c10;
        _a11 = _c11;
      }

      for (int i = 0; i < vl; i++) {
        var a00 = BigInteger.valueOf(_a00.lane(i));
        var a01 = BigInteger.valueOf(_a01.lane(i));
        var a10 = BigInteger.valueOf(_a10.lane(i));
        var a11 = BigInteger.valueOf(_a11.lane(i));
        tmp = p0.multiply(a00).add(p1.multiply(a01));
        p1 = p0.multiply(a10).add(p1.multiply(a11));
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
