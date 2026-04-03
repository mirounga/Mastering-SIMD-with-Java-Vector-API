package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

public class TrappingRainWaterHorizontalX4 {
  public static int trap(int[] input) {
    int[] prefixMax = new int[input.length];
    int[] suffixMax = new int[input.length];

    computePrefixMax(input, prefixMax);
    computeSuffixMax(input, suffixMax);

    return TrappingRainWaterHorizontal
      .reduceHeights(input, prefixMax, suffixMax);
  }

  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;
  static final int vl = SP.length();

  static void computePrefixMax(int[] input, int[] prefixMax) {
    final IntVector _zero = IntVector.zero(SP);

    int iterations = input.length / (4 * vl);
    int remainder = input.length % (4 * vl);

    int acc = 0, pos = 0;
    for (int i = 0; i < iterations; i++) {
      var _part0 = IntVector.fromArray(SP, input, pos + 0 * vl);
      var _part1 = IntVector.fromArray(SP, input, pos + 1 * vl);
      var _part2 = IntVector.fromArray(SP, input, pos + 2 * vl);
      var _part3 = IntVector.fromArray(SP, input, pos + 3 * vl);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted0 = _zero.slice(vl - step, _part0);
        var _shifted1 = _zero.slice(vl - step, _part1);
        var _shifted2 = _zero.slice(vl - step, _part2);
        var _shifted3 = _zero.slice(vl - step, _part3);
        _part0 = _part0.max(_shifted0);
        _part1 = _part1.max(_shifted1);
        _part2 = _part2.max(_shifted2);
        _part3 = _part3.max(_shifted3);
      }

      var _accV = IntVector.broadcast(SP, acc);
      _part0 = _part0.max(_accV);
      _accV = IntVector.broadcast(SP, _part0.lane(vl - 1));
      _part1 = _part1.max(_accV);
      _accV = IntVector.broadcast(SP, _part1.lane(vl - 1));
      _part2 = _part2.max(_accV);
      _accV = IntVector.broadcast(SP, _part2.lane(vl - 1));
      _part3 = _part3.max(_accV);

      acc = _part3.lane(vl - 1);

      _part0.intoArray(prefixMax, pos + 0 * vl);
      _part1.intoArray(prefixMax, pos + 1 * vl);
      _part2.intoArray(prefixMax, pos + 2 * vl);
      _part3.intoArray(prefixMax, pos + 3 * vl);

      pos += 4 * vl;
    }

    while (remainder-- > 0) {
      acc = Math.max(acc, input[pos]);
      prefixMax[pos++] = acc;
    }
  }

  static void computeSuffixMax(int[] input, int[] suffixMax) {
    final IntVector _zero = IntVector.zero(SP);

    int iterations = input.length / (4 * vl);
    int remainder = input.length % (4 * vl);

    int acc = 0, pos = input.length;
    while (remainder-- > 0) {
      acc = Math.max(acc, input[--pos]);
      suffixMax[pos] = acc;
    }

    for (int i = 0; i < iterations; i++) {
      pos -= 4 * vl;

      var _part0 = IntVector.fromArray(SP, input, pos + 0 * vl);
      var _part1 = IntVector.fromArray(SP, input, pos + 1 * vl);
      var _part2 = IntVector.fromArray(SP, input, pos + 2 * vl);
      var _part3 = IntVector.fromArray(SP, input, pos + 3 * vl);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted3 = _part3.slice(step, _zero);
        var _shifted2 = _part2.slice(step, _zero);
        var _shifted1 = _part1.slice(step, _zero);
        var _shifted0 = _part0.slice(step, _zero);
        _part3 = _part3.max(_shifted3);
        _part2 = _part2.max(_shifted2);
        _part1 = _part1.max(_shifted1);
        _part0 = _part0.max(_shifted0);
      }

      var _accV = IntVector.broadcast(SP, acc);
      _part3 = _part3.max(_accV);
      _accV = IntVector.broadcast(SP, _part3.lane(0));
      _part2 = _part2.max(_accV);
      _accV = IntVector.broadcast(SP, _part2.lane(0));
      _part1 = _part1.max(_accV);
      _accV = IntVector.broadcast(SP, _part1.lane(0));
      _part0 = _part0.max(_accV);

      acc = _part0.lane(0);

      _part0.intoArray(suffixMax, pos + 0 * vl);
      _part1.intoArray(suffixMax, pos + 1 * vl);
      _part2.intoArray(suffixMax, pos + 2 * vl);
      _part3.intoArray(suffixMax, pos + 3 * vl);
    }
  }
}
