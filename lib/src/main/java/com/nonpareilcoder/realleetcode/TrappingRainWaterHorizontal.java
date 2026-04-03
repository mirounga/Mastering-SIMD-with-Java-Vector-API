package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class TrappingRainWaterHorizontal {
  public static int trap(int[] input) {
    int[] prefixMax = new int[input.length];
    int[] suffixMax = new int[input.length];

    computePrefixMax(input, prefixMax);
    computeSuffixMax(input, suffixMax);

    return reduceHeights(input, prefixMax, suffixMax);
  }

  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;
  static final int vl = SP.length();

  static void computePrefixMax(int[] input, int[] prefixMax) {
    final var _zero = IntVector.zero(SP);

    int acc = 0, pos = 0;
    for (int i = 0; i < input.length; i += vl) {
      var _mask = SP.indexInRange(i, input.length);

      var _current = IntVector.fromArray(SP, input, pos, _mask);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _zero.slice(vl - step, _current);
        _current = _current.max(_shifted);
      }

      _current = _current.max(IntVector.broadcast(SP, acc));

      acc = _current.lane(vl - 1);

      _current.intoArray(prefixMax, pos, _mask);

      pos += vl;
    }
  }

  static void computeSuffixMax(int[] input, int[] suffixMax) {
    final var _zero = IntVector.zero(SP);

    int iterations = input.length / vl;
    int remainder = input.length % vl;

    int acc = 0, pos = input.length;
    while (remainder-- > 0) {
      acc = Math.max(acc, input[--pos]);
      suffixMax[pos] = acc;
    }

    for (int i = 0; i < iterations; i++) {
      pos -= vl;

      var _current = IntVector.fromArray(SP, input, pos);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _current.slice(step, _zero);
        _current = _current.max(_shifted);
      }

      _current = _current.max(
        IntVector.broadcast(SP, acc));

      acc = _current.lane(0);

      _current.intoArray(suffixMax, pos);
    }
  }

  static int reduceHeights(
    int[] input, int[] prefixMax, int[] suffixMax) {
    var _trapped = IntVector.zero(SP);

    for (int i = 0; i < input.length; i += vl) {
      var _mask = SP.indexInRange(i, input.length);
      var _height = IntVector.fromArray(SP, input, i, _mask);
      var _left = IntVector.fromArray(SP, prefixMax, i, _mask);
      var _right = IntVector.fromArray(SP, suffixMax, i, _mask);

      _trapped = _trapped.add(_left.min(_right).sub(_height));
    }

    return _trapped.reduceLanes(ADD);
  }
}
