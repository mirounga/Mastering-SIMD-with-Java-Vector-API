package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

public class AdditiveScanHorizontalX4 {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;
  static final int vl = SP.length();

  public static void prefixAdd(int[] input, int[] output) {
    if (input.length != output.length)
      throw new ArrayIndexOutOfBoundsException(output.length);

    int arraySize = input.length;
    if (arraySize == 0)
      return;

    final var _zero = IntVector.zero(SP);

    int iterations = arraySize / (4 * vl);
    int remainder = arraySize % (4 * vl);
    ;

    int acc = 0, pos = 0;
    for (int i = 0; i < iterations; i++) {
      var _part0 = IntVector.fromArray(SP, input, pos + 0 * vl);
      var _part1 = IntVector.fromArray(SP, input, pos + 1 * vl);
      var _part2 = IntVector.fromArray(SP, input, pos + 2 * vl);
      var _part3 = IntVector.fromArray(SP, input, pos + 3 * vl);

      for (int step = 1; step < vl; step <<= 1) {
        var _idx = VectorShuffle.iota(SP, -step, 1, false);

        var _shifted0 = _part0.rearrange(_idx, _zero);
        var _shifted1 = _part1.rearrange(_idx, _zero);
        var _shifted2 = _part2.rearrange(_idx, _zero);
        var _shifted3 = _part3.rearrange(_idx, _zero);
        _part0 = _part0.add(_shifted0);
        _part1 = _part1.add(_shifted1);
        _part2 = _part2.add(_shifted2);
        _part3 = _part3.add(_shifted3);
      }

      _part0 = _part0.add(acc);
      _part1 = _part1.add(_part0.lane(vl - 1));
      _part2 = _part2.add(_part1.lane(vl - 1));
      _part3 = _part3.add(_part2.lane(vl - 1));

      acc = _part3.lane(vl - 1);

      _part0.intoArray(output, pos + 0 * vl);
      _part1.intoArray(output, pos + 1 * vl);
      _part2.intoArray(output, pos + 2 * vl);
      _part3.intoArray(output, pos + 3 * vl);

      pos += 4 * vl;
    }

    while (remainder-- > 0) {
      acc += input[pos];
      output[pos++] = acc;
    }
  }

  public static void suffixAdd(int[] input, int[] output) {
    if (input.length != output.length)
      throw new ArrayIndexOutOfBoundsException(output.length);

    int arraySize = input.length;
    if (arraySize == 0)
      return;

    final var _zero = IntVector.zero(SP);

    int iterations = arraySize / (4 * vl);
    int remainder = arraySize % (4 * vl);
    ;

    int acc = 0, pos = input.length;
    while (remainder-- > 0) {
      acc += input[--pos];
      output[pos] = acc;
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

        _part3 = _part3.add(_shifted3);
        _part2 = _part2.add(_shifted2);
        _part1 = _part1.add(_shifted1);
        _part0 = _part0.add(_shifted0);
      }

      _part3 = _part3.add(acc);
      _part2 = _part2.add(_part3.lane(0));
      _part1 = _part1.add(_part2.lane(0));
      _part0 = _part0.add(_part1.lane(0));

      acc = _part0.lane(0);

      _part0.intoArray(output, pos + 0 * vl);
      _part1.intoArray(output, pos + 1 * vl);
      _part2.intoArray(output, pos + 2 * vl);
      _part3.intoArray(output, pos + 3 * vl);
    }
  }
}
