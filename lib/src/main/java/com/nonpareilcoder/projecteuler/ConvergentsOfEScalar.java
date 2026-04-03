package com.nonpareilcoder.projecteuler;

import java.math.BigInteger;

public class ConvergentsOfEScalar {
  public static long compute() {
    BigInteger p0 = BigInteger.ONE;
    BigInteger p1 = BigInteger.TWO;

    for (long k = 0; k++ < 33; ) {
      BigInteger tmp = p1.add(p0);
      p0 = tmp.multiply(BigInteger.valueOf(2 * k)).add(p1);
      p1 = p0.add(tmp);
    }

    long sum = 0;

    while (p1.compareTo(BigInteger.ZERO) != 0) {
      var divRem = p1.divideAndRemainder(BigInteger.TEN);
      sum += divRem[1].longValue();
      p1 = divRem[0];
    }

    return sum;
  }
}
