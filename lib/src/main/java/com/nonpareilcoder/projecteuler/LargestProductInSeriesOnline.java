package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

public class LargestProductInSeriesOnline {
  static final String thousandDigits =
    LargestProductInSeriesBaseline.thousandDigits;

  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_256;

  public static long compute() {
    LongVector _part0 = LongVector.broadcast(SP, 1L);
    LongVector _part1 = _part0, _part2 = _part0;

    long maxValue = 0;

    for (int i = 0; i < 1000; i++) {

      LongVector _currentDigit = LongVector.broadcast(
        SP, Character.getNumericValue(thousandDigits.charAt(i)));

      _part0 = _part0.mul(_currentDigit);
      _part1 = _part1.mul(_currentDigit);
      _part2 = _part2.mul(_currentDigit);

      if (i >= 12) {
        long currentValue = _part0.lane(0);

        if (maxValue < currentValue) {
          maxValue = currentValue;
        }
      }

      _part0 = _part0.slice(1, _part1);
      _part1 = _part1.slice(1, _part2);
      _part2 = _part2.slice(1, _currentDigit);
    }

    return maxValue;
  }
}
