package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

import java.math.BigInteger;

public class ConvergentsOfEHorizontal {
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
    var _idx = VectorShuffle.iota(SP, vl - 1, -1, false)
      .toVector().reinterpretAsLongs().add(2L);

    final var _zero = LongVector.zero(SP);
    final var _one = LongVector.broadcast(SP, 1L);

    for (long k = 0; k < 32; k += vl) {
      var _a11 = _idx.add(_idx);
      var _a10 = _a11.add(1);
      var _a01 = _a10;
      var _a00 = _a11.add(2);

      for (int step = 1; step < vl; step <<= 1) {
        var _b00 = _a00.slice(step, _one);
        var _b01 = _a01.slice(step, _zero);
        var _b10 = _a10.slice(step, _zero);
        var _b11 = _a11.slice(step, _one);

        var _c00 = _a00.mul(_b00).add(_a01.mul(_b10));
        var _c01 = _a00.mul(_b01).add(_a01.mul(_b11));
        var _c10 = _a10.mul(_b00).add(_a11.mul(_b10));
        var _c11 = _a10.mul(_b01).add(_a11.mul(_b11));

        _a00 = _c00;
        _a01 = _c01;
        _a10 = _c10;
        _a11 = _c11;
      }

      var a00 = BigInteger.valueOf(_a00.lane(0));
      var a01 = BigInteger.valueOf(_a01.lane(0));
      var a10 = BigInteger.valueOf(_a10.lane(0));
      var a11 = BigInteger.valueOf(_a11.lane(0));
      tmp = p0.multiply(a00).add(p1.multiply(a01));
      p1 = p0.multiply(a10).add(p1.multiply(a11));
      p0 = tmp;

      _idx = _idx.add(vl);
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
