package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

import java.math.BigInteger;

public class ConvergentsOfEBillionaire128 {
  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_128;

  static final long c10 = 0x1999999AL;
  static final long lowPart = 0xFFFFFFFFL;

  public static long compute() {
    LongVector _idxa =
      VectorShuffle.iota(SP, 0, 1, false)
        .toVector().reinterpretAsLongs().mul(32L).add(3L);

    LongVector _idxb =
      VectorShuffle.iota(SP, 0, 1, false)
        .toVector().reinterpretAsLongs().mul(32L).add(19L);

    LongVector _b00 = LongVector.broadcast(SP, 1);
    LongVector _b01 = LongVector.zero(SP);
    LongVector _b10 = _b01;
    LongVector _b11 = LongVector.broadcast(SP, 1);

    LongVector _a00 = _b00.withLane(0, 4L);
    LongVector _a01 = _b01.withLane(0, 3L);
    LongVector _a10 = _a01;
    LongVector _a11 = _b11.withLane(0, 2L);


    for (int i = 0; i < 8; i++) {
      _idxa = _idxa.add(2L);

      LongVector _p0 = _a00.add(_a10).mul(_idxa);
      LongVector _p1 = _a01.add(_a11).mul(_idxa);

      _a00 = _p0.add(_a00);
      _a10 = _p0.sub(_a10);
      _a01 = _p1.add(_a01);
      _a11 = _p1.sub(_a11);

      _idxb = _idxb.add(2L);

      _p0 = _b00.add(_b10).mul(_idxb);
      _p1 = _b01.add(_b11).mul(_idxb);

      _b00 = _p0.add(_b00);
      _b10 = _p0.sub(_b10);
      _b01 = _p1.add(_b01);
      _b11 = _p1.sub(_b11);
    }

    BigInteger p0 = BigInteger.TWO;
    BigInteger p1 = BigInteger.ONE;

    for (int i = 0; i < SP.length(); i++) {
      BigInteger tmp =
        p0.multiply(BigInteger.valueOf(_a00.lane(i)))
        .add(p1.multiply(BigInteger.valueOf(_a01.lane(i))));
      p1 = p0.multiply(BigInteger.valueOf(_a10.lane(i)))
        .add(p1.multiply(BigInteger.valueOf(_a11.lane(i))));
      p0 = tmp;

      tmp = p0.multiply(BigInteger.valueOf(_b00.lane(i)))
        .add(p1.multiply(BigInteger.valueOf(_b01.lane(i))));
      p1 = p0.multiply(BigInteger.valueOf(_b10.lane(i)))
        .add(p1.multiply(BigInteger.valueOf(_b11.lane(i))));
      p0 = tmp;
    }

    long[] billions = new long[8];

    final BigInteger billion = BigInteger.valueOf(1_000_000_000L);

    for (int i = 0; i < 8; i++) {
      BigInteger[] divRem = p0.divideAndRemainder(billion);
      billions[i] = divRem[1].longValue();
      p0 = divRem[0];
    }

    LongVector _sum = LongVector.zero(SP);

    LongVector _bil0 = LongVector.fromArray(SP, billions, 0);
    LongVector _bil1 = LongVector.fromArray(SP, billions, 2);
    LongVector _bil2 = LongVector.fromArray(SP, billions, 4);
    LongVector _bil3 = LongVector.fromArray(SP, billions, 6);

    for (int i = 0; i < 9; i++) {
      LongVector _div0 = _bil0.mul(c10);
      LongVector _div1 = _bil1.mul(c10);
      LongVector _div2 = _bil2.mul(c10);
      LongVector _div3 = _bil3.mul(c10);
      var _rem0 = _div0.and(lowPart).mul(10L).lanewise(LSHR, 32);
      var _rem1 = _div1.and(lowPart).mul(10L).lanewise(LSHR, 32);
      var _rem2 = _div2.and(lowPart).mul(10L).lanewise(LSHR, 32);
      var _rem3 = _div3.and(lowPart).mul(10L).lanewise(LSHR, 32);
      _rem0 = _rem0.add(_rem2);
      _rem1 = _rem1.add(_rem3);
      _sum = _sum.add(_rem0).add(_rem1);
      _bil0 = _div0.lanewise(LSHR, 32);
      _bil1 = _div1.lanewise(LSHR, 32);
      _bil2 = _div2.lanewise(LSHR, 32);
      _bil3 = _div3.lanewise(LSHR, 32);
    }

    long sum = _sum.reduceLanes(ADD);

    return sum;
  }
}
