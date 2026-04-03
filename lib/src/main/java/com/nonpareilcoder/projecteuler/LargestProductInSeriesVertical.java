package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class LargestProductInSeriesVertical {
  static final String thousandDigits =
    LargestProductInSeriesBaseline.thousandDigits +
      "00000000000000000000";

  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_256;

  public static long compute() {
    var digits =
      LargestProductInSeriesHorizontal.preprocess(thousandDigits);

    final var _zero = LongVector.zero(SP);

    var _max = _zero;

    final var _uzp0 = VectorShuffle.makeUnzip(SP, 0);
    final var _uzp1 = VectorShuffle.makeUnzip(SP, 1);

    for (int i = 0; i < 972; i += 36) {
      var _part0 = LongVector.fromArray(SP, digits, i);
      var _part1 = LongVector.fromArray(SP, digits, i + 4);
      var _part2 = LongVector.fromArray(SP, digits, i + 8);
      var _part3 = LongVector.fromArray(SP, digits, i + 12);
      var _part4 = LongVector.fromArray(SP, digits, i + 16);
      var _part5 = LongVector.fromArray(SP, digits, i + 20);
      var _part6 = LongVector.fromArray(SP, digits, i + 24);
      var _part7 = LongVector.fromArray(SP, digits, i + 28);
      var _part8 = LongVector.fromArray(SP, digits, i + 32);
      var _part9 = LongVector.fromArray(SP, digits, i + 36);
      var _partA = LongVector.fromArray(SP, digits, i + 40);
      var _partB = LongVector.fromArray(SP, digits, i + 44);

      var _t0 = _part0.rearrange(_uzp0, _part3);
      var _t1 = _part1.rearrange(_uzp0, _part4);
      var _t2 = _part2.rearrange(_uzp0, _part5);
      var _t3 = _part0.rearrange(_uzp1, _part3);
      var _t4 = _part1.rearrange(_uzp1, _part4);
      var _t5 = _part2.rearrange(_uzp1, _part5);
      var _t6 = _part6.rearrange(_uzp0, _part9);
      var _t7 = _part7.rearrange(_uzp0, _partA);
      var _t8 = _part8.rearrange(_uzp0, _partB);
      var _t9 = _part6.rearrange(_uzp1, _part9);
      var _tA = _part7.rearrange(_uzp1, _partA);
      var _tB = _part8.rearrange(_uzp1, _partB);

      _part0 = _t0.rearrange(_uzp0, _t6);
      _part1 = _t3.rearrange(_uzp0, _t9);
      _part2 = _t0.rearrange(_uzp1, _t6);
      _part3 = _t3.rearrange(_uzp1, _t9);
      _part4 = _t1.rearrange(_uzp0, _t7);
      _part5 = _t4.rearrange(_uzp0, _tA);
      _part6 = _t1.rearrange(_uzp1, _t7);
      _part7 = _t4.rearrange(_uzp1, _tA);
      _part8 = _t2.rearrange(_uzp0, _t8);
      _part9 = _t5.rearrange(_uzp0, _tB);
      _partA = _t2.rearrange(_uzp1, _t8);
      _partB = _t5.rearrange(_uzp1, _tB);

      var _suffix0 = _part0.mul(_part1);
      var _suffix2 = _part2.mul(_part3);
      var _suffix4 = _part4.mul(_part5);
      var _suffix6 = _part6.mul(_part7);
      var _suffix8 = _part8.mul(_part9);
      var _suffixA = _partA.mul(_partB);

      var _prefix1 = _part1.mul(_part0);
      var _prefix3 = _part3.mul(_part2);
      var _prefix5 = _part5.mul(_part4);
      var _prefix7 = _part7.mul(_part6);
      var _prefix9 = _part9.mul(_part8);
      var _prefixB = _partB.mul(_partA);

      _suffix0 = _suffix0.mul(_suffix2);
      _suffix4 = _suffix4.mul(_suffix6);
      _suffix8 = _suffix8.mul(_suffixA);

      _prefix3 = _prefix3.mul(_prefix1);
      _prefix7 = _prefix7.mul(_prefix5);
      _prefixB = _prefixB.mul(_prefix9);

      _suffix4 = _suffix4.mul(_suffix8);

      _prefix7 = _prefix7.mul(_prefix3);

      _suffix0 = _suffix0.mul(_suffix4);

      _prefixB = _prefixB.mul(_prefix7);

      _suffix2 = _suffix2.mul(_suffix4);
      _suffix6 = _suffix6.mul(_suffix8);

      _prefix5 = _prefix5.mul(_prefix3);
      _prefix9 = _prefix9.mul(_prefix7);

      var _r0 = _part0.slice(1, _zero).mul(_suffix0);
      _max = _r0.max(_max);

      var _suffix1 = _part1.mul(_suffix2);
      var _r1 = _prefix1.slice(1, _zero).mul(_suffix1);
      _max = _r1.max(_max);

      var _prefix2 = _part2.mul(_prefix1);
      var _r2 = _prefix2.slice(1, _zero).mul(_suffix2);
      _max = _r2.max(_max);

      var _suffix3 = _part3.mul(_suffix4);
      var _r3 = _prefix3.slice(1, _zero).mul(_suffix3);
      _max = _r3.max(_max);

      var _prefix4 = _part4.mul(_prefix3);
      var _r4 = _prefix4.slice(1, _zero).mul(_suffix4);
      _max = _r4.max(_max);

      var _suffix5 = _part5.mul(_suffix6);
      var _r5 = _prefix5.slice(1, _zero).mul(_suffix5);
      _max = _r5.max(_max);

      var _prefix6 = _part6.mul(_prefix5);
      var _r6 = _prefix6.slice(1, _zero).mul(_suffix6);
      _max = _r6.max(_max);

      var _suffix7 = _part7.mul(_suffix8);
      var _r7 = _prefix7.slice(1, _zero).mul(_suffix7);
      _max = _r7.max(_max);

      var _prefix8 = _part8.mul(_prefix7);
      var _r8 = _prefix8.slice(1, _zero).mul(_suffix8);
      _max = _r8.max(_max);

      var _suffix9 = _part9.mul(_suffixA);
      var _r9 = _prefix9.slice(1, _zero).mul(_suffix9);
      _max = _r9.max(_max);

      var _prefixA = _partA.mul(_prefix9);
      var _rA = _prefixA.slice(1, _zero).mul(_suffixA);
      _max = _rA.max(_max);

      _max = _prefixB.slice(1, _zero).mul(_partB).max(_max);
    }

    long maxValue = _max.reduceLanes(MAX);

    return maxValue;
  }
}
