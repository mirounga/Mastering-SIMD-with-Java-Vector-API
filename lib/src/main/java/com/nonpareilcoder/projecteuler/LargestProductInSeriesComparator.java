package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;
import jdk.incubator.vector.VectorOperators.Conversion;

import static jdk.incubator.vector.VectorOperators.*;

public class LargestProductInSeriesComparator {
  static final String thousandDigits =
    LargestProductInSeriesBaseline.thousandDigits +
      "00000000000000000000";

  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_256;
  static final int vl = SP.length();
  static final VectorSpecies<Short> CHAR_SPECIES =
    ShortVector.SPECIES_256;
  static final Conversion<Short, Long> CONV =
    Conversion.ofCast(short.class, long.class);


  public static long[] computeScalar() {
    var output = new long[thousandDigits.length()];

    long maxValue = 0;

    for (int i = 0; i < 987; i++) {
      long currentValue = 1;
      for (int j = 0; j < 13; j++) {
        int idx = i + j;
        currentValue *= Character.getNumericValue(
          thousandDigits.charAt(idx));
      }

      output[i] = currentValue;

      if (maxValue < currentValue) {
        maxValue = currentValue;
      }
    }

    return output;
  }

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

  static final LongVector _one =
    LongVector.broadcast(SP, 1L);

  public static long[] computeHorizontal() {
    var output = new long[thousandDigits.length()];

    var digits = preprocess(thousandDigits);

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

      _suffix0.intoArray(output, i - 12);
      _suffix1.intoArray(output, i - 8);
      _suffix2.intoArray(output, i - 4);

      _max = _max.max(_suffix0);
      _max = _max.max(_suffix1);
      _max = _max.max(_suffix2);
    }

    return output;
  }

  public static long[] computeVertical() {
    var output = new long[thousandDigits.length()];

    var digits = preprocess(thousandDigits);

    final var _zero = LongVector.zero(SP);

    final var _uzp0 = VectorShuffle.makeUnzip(SP, 0);
    final var _uzp1 = VectorShuffle.makeUnzip(SP, 1);

    var indexMap = new int[vl];
    for (int i = 0; i < vl; i++)
      indexMap[i] = i * 12;

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

      _part0 = _part0.slice(1, _zero).mul(_suffix0);

      var _suffix1 = _part1.mul(_suffix2);
      _part1 = _prefix1.slice(1, _zero).mul(_suffix1);

      var _prefix2 = _part2.mul(_prefix1);
      _part2 = _prefix2.slice(1, _zero).mul(_suffix2);

      var _suffix3 = _part3.mul(_suffix4);
      _part3 = _prefix3.slice(1, _zero).mul(_suffix3);

      var _prefix4 = _part4.mul(_prefix3);
      _part4 = _prefix4.slice(1, _zero).mul(_suffix4);

      var _suffix5 = _part5.mul(_suffix6);
      _part5 = _prefix5.slice(1, _zero).mul(_suffix5);

      var _prefix6 = _part6.mul(_prefix5);
      _part6 = _prefix6.slice(1, _zero).mul(_suffix6);

      var _suffix7 = _part7.mul(_suffix8);
      _part7 = _prefix7.slice(1, _zero).mul(_suffix7);

      var _prefix8 = _part8.mul(_prefix7);
      _part8 = _prefix8.slice(1, _zero).mul(_suffix8);

      var _suffix9 = _part9.mul(_suffixA);
      _part9 = _prefix9.slice(1, _zero).mul(_suffix9);

      var _prefixA = _partA.mul(_prefix9);
      _partA = _prefixA.slice(1, _zero).mul(_suffixA);

      _partB = _prefixB.slice(1, _zero).mul(_partB);

      _part0.intoArray(output, i, indexMap, 0);
      _part1.intoArray(output, i + 1, indexMap, 0);
      _part2.intoArray(output, i + 2, indexMap, 0);
      _part3.intoArray(output, i + 3, indexMap, 0);
      _part4.intoArray(output, i + 4, indexMap, 0);
      _part5.intoArray(output, i + 5, indexMap, 0);
      _part6.intoArray(output, i + 6, indexMap, 0);
      _part7.intoArray(output, i + 7, indexMap, 0);
      _part8.intoArray(output, i + 8, indexMap, 0);
      _part9.intoArray(output, i + 9, indexMap, 0);
      _partA.intoArray(output, i + 10, indexMap, 0);
      _partB.intoArray(output, i + 11, indexMap, 0);
    }

    return output;
  }

  public static long[] computeGather() {
    var output = new long[thousandDigits.length()];

    var digits = preprocess(thousandDigits);

    final var _zero = LongVector.zero(SP);

    var indexMap = new int[vl];
    for (int i = 0; i < vl; i++)
      indexMap[i] = i * 12;

    for (int i = 0; i < 1000; i += 12 * (vl - 1)) {
      var _part0 = LongVector
        .fromArray(SP, digits, i, indexMap, 0);
      var _part1 = LongVector
        .fromArray(SP, digits, i + 1, indexMap, 0);
      var _part2 = LongVector
        .fromArray(SP, digits, i + 2, indexMap, 0);
      var _part3 = LongVector
        .fromArray(SP, digits, i + 3, indexMap, 0);
      var _part4 = LongVector
        .fromArray(SP, digits, i + 4, indexMap, 0);
      var _part5 = LongVector
        .fromArray(SP, digits, i + 5, indexMap, 0);
      var _part6 = LongVector
        .fromArray(SP, digits, i + 6, indexMap, 0);
      var _part7 = LongVector
        .fromArray(SP, digits, i + 7, indexMap, 0);
      var _part8 = LongVector
        .fromArray(SP, digits, i + 8, indexMap, 0);
      var _part9 = LongVector
        .fromArray(SP, digits, i + 9, indexMap, 0);
      var _partA = LongVector
        .fromArray(SP, digits, i + 10, indexMap, 0);
      var _partB = LongVector
        .fromArray(SP, digits, i + 11, indexMap, 0);

      var _suffix2 = _part2.mul(_part3);
      var _suffix6 = _part6.mul(_part7);
      var _suffixA = _partA.mul(_partB);

      var _prefix1 = _part1.mul(_part0);
      var _prefix5 = _part5.mul(_part4);
      var _prefix9 = _part9.mul(_part8);

      var _suffix0 = _prefix1.mul(_suffix2);
      var _suffix4 = _prefix5.mul(_suffix6);
      var _suffix8 = _prefix9.mul(_suffixA);

      var _prefix3 = _suffix2.mul(_prefix1);
      var _prefix7 = _suffix6.mul(_prefix5);
      var _prefixB = _suffixA.mul(_prefix9);

      _suffix4 = _suffix4.mul(_suffix8);

      _prefix7 = _prefix7.mul(_prefix3);

      _suffix0 = _suffix0.mul(_suffix4);

      _prefixB = _prefixB.mul(_prefix7);

      _suffix2 = _suffix2.mul(_suffix4);
      _suffix6 = _suffix6.mul(_suffix8);

      _prefix5 = _prefix5.mul(_prefix3);
      _prefix9 = _prefix9.mul(_prefix7);

      _part0 = _part0.slice(1, _zero).mul(_suffix0);

      var _suffix1 = _part1.mul(_suffix2);
      _part1 = _prefix1.slice(1, _zero).mul(_suffix1);

      var _prefix2 = _part2.mul(_prefix1);
      _part2 = _prefix2.slice(1, _zero).mul(_suffix2);

      var _suffix3 = _part3.mul(_suffix4);
      _part3 = _prefix3.slice(1, _zero).mul(_suffix3);

      var _prefix4 = _part4.mul(_prefix3);
      _part4 = _prefix4.slice(1, _zero).mul(_suffix4);

      var _suffix5 = _part5.mul(_suffix6);
      _part5 = _prefix5.slice(1, _zero).mul(_suffix5);

      var _prefix6 = _part6.mul(_prefix5);
      _part6 = _prefix6.slice(1, _zero).mul(_suffix6);

      var _suffix7 = _part7.mul(_suffix8);
      _part7 = _prefix7.slice(1, _zero).mul(_suffix7);

      var _prefix8 = _part8.mul(_prefix7);
      _part8 = _prefix8.slice(1, _zero).mul(_suffix8);

      var _suffix9 = _part9.mul(_suffixA);
      _part9 = _prefix9.slice(1, _zero).mul(_suffix9);

      var _prefixA = _partA.mul(_prefix9);
      _partA = _prefixA.slice(1, _zero).mul(_suffixA);

      _partB = _prefixB.slice(1, _zero).mul(_partB);

      _part0.intoArray(output, i, indexMap, 0);
      _part1.intoArray(output, i + 1, indexMap, 0);
      _part2.intoArray(output, i + 2, indexMap, 0);
      _part3.intoArray(output, i + 3, indexMap, 0);
      _part4.intoArray(output, i + 4, indexMap, 0);
      _part5.intoArray(output, i + 5, indexMap, 0);
      _part6.intoArray(output, i + 6, indexMap, 0);
      _part7.intoArray(output, i + 7, indexMap, 0);
      _part8.intoArray(output, i + 8, indexMap, 0);
      _part9.intoArray(output, i + 9, indexMap, 0);
      _partA.intoArray(output, i + 10, indexMap, 0);
      _partB.intoArray(output, i + 11, indexMap, 0);
    }

    return output;
  }
}
