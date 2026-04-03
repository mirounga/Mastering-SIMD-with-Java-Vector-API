package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

public class TrappingRainWaterTransposedX4 {
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

  static final IntVector _zero = IntVector.broadcast(SP, 0);

  static final VectorShuffle<Integer> _zip0 =
    VectorShuffle.makeZip(SP, 0);
  static final VectorShuffle<Integer> _zip1 =
    VectorShuffle.makeZip(SP, 1);
  static final VectorShuffle<Integer> _uzp0 =
    VectorShuffle.makeUnzip(SP, 0);
  static final VectorShuffle<Integer> _uzp1 =
    VectorShuffle.makeUnzip(SP, 1);

  static void computePrefixMax(int[] input, int[] prefixMax) {
    int iterations = input.length / (4 * vl);
    int remainder = input.length % (4 * vl);

    int acc = 0, pos = 0;
    for (int i = 0; i < iterations; i++) {
      IntVector _part0 = IntVector.fromArray(SP, input, pos);
      IntVector _part1 =
        IntVector.fromArray(SP, input, pos + vl);
      IntVector _part2 =
        IntVector.fromArray(SP, input, pos + 2 * vl);
      IntVector _part3 =
        IntVector.fromArray(SP, input, pos + 3 * vl);

      IntVector _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part1);
      _part1 = _t0.rearrange(_uzp1, _part1);
      IntVector _t1 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part3);
      _part3 = _t1.rearrange(_uzp1, _part3);

      _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_uzp0, _part3);
      _part2 = _t0.rearrange(_uzp1, _part2);
      _part3 = _t1.rearrange(_uzp1, _part3);

      _part1 = _part1.max(_part0);
      _part3 = _part3.max(_part2);

      _part3 = _part3.max(_part1);

      _part2 = _part2.max(_part1);

      IntVector _prev = IntVector.broadcast(SP, acc);

      IntVector _partZ = _prev.slice(vl - 1, _part3);

      for (int step = 1; step < vl; step <<= 1) {
        IntVector _shifted = _zero.slice(vl - step, _partZ);
        _partZ = _partZ.max(_shifted);
      }

      _part0 = _part0.max(_partZ);
      _part1 = _part1.max(_partZ);
      _part2 = _part2.max(_partZ);
      _part3 = _part3.max(_partZ);

      acc = _part3.lane(vl - 1);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part3);
      _part2 = _t0.rearrange(_zip1, _part2);
      _part3 = _t1.rearrange(_zip1, _part3);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part1);
      _part1 = _t0.rearrange(_zip1, _part1);
      _t1 = _part2;
      _part2 = _part2.rearrange(_zip0, _part3);
      _part3 = _t1.rearrange(_zip1, _part3);

      _part0.intoArray(prefixMax, pos);
      _part1.intoArray(prefixMax, pos + vl);
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
    int iterations = input.length / (4 * vl);
    int remainder = input.length % (4 * vl);

    int acc = 0, pos = input.length;
    while (remainder-- > 0) {
      acc = Math.max(acc, input[--pos]);
      suffixMax[pos] = acc;
    }

    IntVector _prev = IntVector.broadcast(SP, acc);

    for (int i = 0; i < iterations; i++) {
      pos -= 4 * vl;

      IntVector _part0 = IntVector.fromArray(SP, input, pos);
      IntVector _part1 =
        IntVector.fromArray(SP, input, pos + vl);
      IntVector _part2 =
        IntVector.fromArray(SP, input, pos + 2 * vl);
      IntVector _part3 =
        IntVector.fromArray(SP, input, pos + 3 * vl);

      IntVector _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part1);
      _part1 = _t0.rearrange(_uzp1, _part1);
      IntVector _t1 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part3);
      _part3 = _t1.rearrange(_uzp1, _part3);

      _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_uzp0, _part3);
      _part2 = _t0.rearrange(_uzp1, _part2);
      _part3 = _t1.rearrange(_uzp1, _part3);

      _part2 = _part2.max(_part3);
      _part0 = _part0.max(_part1);

      _part0 = _part0.max(_part2);

      _part1 = _part1.max(_part2);

      IntVector _partZ = _part0.slice(1, _prev);

      for (int step = 1; step < vl; step <<= 1) {
        IntVector _shifted = _partZ.slice(step, _zero);
        _partZ = _partZ.max(_shifted);
      }

      _part3 = _part3.max(_partZ);
      _part2 = _part2.max(_partZ);
      _part1 = _part1.max(_partZ);
      _part0 = _part0.max(_partZ);

      _prev = _part0;

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part3);
      _part2 = _t0.rearrange(_zip1, _part2);
      _part3 = _t1.rearrange(_zip1, _part3);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part1);
      _part1 = _t0.rearrange(_zip1, _part1);
      _t1 = _part2;
      _part2 = _part2.rearrange(_zip0, _part3);
      _part3 = _t1.rearrange(_zip1, _part3);

      _part0.intoArray(suffixMax, pos);
      _part1.intoArray(suffixMax, pos + vl);
      _part2.intoArray(suffixMax, pos + 2 * vl);
      _part3.intoArray(suffixMax, pos + 3 * vl);
    }
  }
}
