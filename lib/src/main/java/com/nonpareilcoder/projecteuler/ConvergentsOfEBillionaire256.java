package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

import java.math.BigInteger;

public class ConvergentsOfEBillionaire256 {
  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_256;

  static final long c10 = 0x1999999AL;
  static final long lowPart = 0xFFFFFFFFL;

  public static long compute() {
    LongVector _idx =
      VectorShuffle.iota(SP, 0, 1, false)
        .toVector().reinterpretAsLongs().mul(16L).add(3);

    var _a00 = LongVector.broadcast(SP, 1).withLane(0, 4L);
    var _a01 = LongVector.zero(SP).withLane(0, 3L);
    LongVector _a10 = _a01;
    var _a11 = LongVector.broadcast(SP, 1).withLane(0, 2L);

    for (int i = 0; i < 8; i++) {
      _idx = _idx.add(2L);

      LongVector _p0 = _a00.add(_a10).mul(_idx);
      LongVector _p1 = _a01.add(_a11).mul(_idx);

      _a00 = _p0.add(_a00);
      _a10 = _p0.sub(_a10);
      _a01 = _p1.add(_a01);
      _a11 = _p1.sub(_a11);
    }

    BigInteger p0 = BigInteger.TWO;
    BigInteger p1 = BigInteger.ONE;

    var vl = SP.length();
    for (int i = 0; i < vl; i++) {
      var tmp =
        p0.multiply(BigInteger.valueOf(_a00.lane(i)))
        .add(p1.multiply(BigInteger.valueOf(_a01.lane(i))));
      p1 = p0.multiply(BigInteger.valueOf(
        _a10.lane(i))).add(p1.multiply(
        BigInteger.valueOf(_a11.lane(i))));
      p0 = tmp;
    }

    long[] billions = new long[8];

    final var billion = BigInteger.valueOf(1_000_000_000L);

    for (int i = 0; i < 8; i++) {
      BigInteger[] divRem = p0.divideAndRemainder(billion);
      billions[i] = divRem[1].longValue();
      p0 = divRem[0];
    }

    LongVector _sum = LongVector.zero(SP);

    LongVector _bil0 = LongVector.fromArray(SP, billions, 0);
    LongVector _bil1 = LongVector.fromArray(SP, billions, 4);

    for (int i = 0; i < 9; i++) {
      LongVector _div0 = _bil0.mul(c10);
      LongVector _div1 = _bil1.mul(c10);
      var _rem0 = _div0.and(lowPart).mul(10L).lanewise(LSHR, 32);
      var _rem1 = _div1.and(lowPart).mul(10L).lanewise(LSHR, 32);
      _sum = _sum.add(_rem0).add(_rem1);
      _bil0 = _div0.lanewise(LSHR, 32);
      _bil1 = _div1.lanewise(LSHR, 32);
    }

    long sum = _sum.reduceLanes(ADD);

    return sum;
  }
}
