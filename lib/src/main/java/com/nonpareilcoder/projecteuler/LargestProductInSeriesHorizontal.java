package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;
import jdk.incubator.vector.VectorOperators.Conversion;

import static jdk.incubator.vector.VectorOperators.*;

public class LargestProductInSeriesHorizontal {
  static final String thousandDigits =
    LargestProductInSeriesBaseline.thousandDigits +
      "00000000000000000000";

  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_256;
  static final int vl = SP.length();
  static final VectorSpecies<Short> CHAR_SPECIES =
    ShortVector.SPECIES_256;
  static final Conversion<Short, Long> CONV =
    Conversion
      .ofCast(short.class, long.class);

  public static long[] preprocess(String chars) {
    var data = chars.toCharArray();

    var digits = new long[data.length];

    final long numBase = '0';
    var _mask = CHAR_SPECIES.indexInRange(0, vl);

    for (int i = 0; i < data.length; i += vl) {
      var shorts = ShortVector.fromCharArray(
        CHAR_SPECIES, data, i, _mask);
      var longs = shorts.convertShape(CONV, SP, 0)
        .reinterpretAsLongs().sub(numBase);
      longs.intoArray(digits, i);
    }

    return digits;
  }

  public static long compute() {
    var digits = preprocess(thousandDigits);

    final var _one = LongVector.broadcast(SP, 1L);

    var _max = LongVector.zero(SP);

    var _part0 = LongVector.fromArray(SP, digits, 0);
    var _part1 = LongVector.fromArray(SP, digits, 4);
    var _part2 = LongVector.fromArray(SP, digits, 8);

    for (int i = 12; i < digits.length; i += 12) {
      var _prefix3 = LongVector.fromArray(SP, digits, i);
      var _prefix4 = LongVector.fromArray(SP, digits, i + 4);
      var _prefix5 = LongVector.fromArray(SP, digits, i + 8);

      var _suffix0 = _part0.slice(1, _part1).mul(_part0);
      var _suffix1 = _part1.slice(1, _part2).mul(_part1);
      var _suffix2 = _part2.slice(1, _one).mul(_part2);

      _part0 = _prefix3;
      _part1 = _prefix4;
      _part2 = _prefix5;

      _prefix5 = _prefix4.slice(3, _prefix5).mul(_prefix5);
      _prefix4 = _prefix3.slice(3, _prefix4).mul(_prefix4);
      _prefix3 = _one.slice(3, _prefix3).mul(_prefix3);

      _suffix0 = _suffix0.slice(2, _suffix1).mul(_suffix0);
      _suffix1 = _suffix1.slice(2, _suffix2).mul(_suffix1);
      _suffix2 = _suffix2.slice(2, _one).mul(_suffix2);

      _prefix5 = _prefix4.slice(2, _prefix5).mul(_prefix5);
      _prefix4 = _prefix3.slice(2, _prefix4).mul(_prefix4);
      _prefix3 = _one.slice(2, _prefix3).mul(_prefix3);

      _suffix0 = _suffix1.mul(_suffix0);
      _suffix1 = _suffix2.mul(_suffix1);

      _prefix5 = _prefix4.mul(_prefix5);
      _prefix4 = _prefix3.mul(_prefix4);

      _suffix0 = _suffix2.mul(_suffix0);

      _prefix5 = _prefix3.mul(_prefix5);

      _suffix0 = _suffix0.mul(_prefix3);
      _suffix1 = _suffix1.mul(_prefix4);
      _suffix2 = _suffix2.mul(_prefix5);

      _max = _max.max(_suffix0);
      _max = _max.max(_suffix1);
      _max = _max.max(_suffix2);
    }

    long maxValue = _max.reduceLanes(MAX);

    return maxValue;
  }
}
