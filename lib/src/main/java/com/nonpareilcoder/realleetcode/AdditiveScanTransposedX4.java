package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

public class AdditiveScanTransposedX4 {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;
  static final int vl = SP.length();

  public static void prefixAdd(int[] input, int[] output) {
    if (input.length != output.length)
      throw new ArrayIndexOutOfBoundsException(output.length);

    int arraySize = input.length;
    if (arraySize == 0)
      return;

    final IntVector _zero = IntVector.broadcast(SP, 0);

    final var _zip0 = VectorShuffle.makeZip(SP, 0);
    final var _zip1 = VectorShuffle.makeZip(SP, 1);
    final var _uzp0 = VectorShuffle.makeUnzip(SP, 0);
    final var _uzp1 = VectorShuffle.makeUnzip(SP, 1);

    int longIterations = arraySize / (4 * vl);
    int longRemainder = arraySize % (4 * vl);

    int acc = 0;

    int pos = 0;
    for (int i = 0; i < longIterations; i++) {
      var _part0 = IntVector.fromArray(SP, input, pos);
      var _part1 = IntVector.fromArray(SP, input, pos + vl);
      var _part2 = IntVector.fromArray(SP, input, pos + 2 * vl);
      var _part3 = IntVector.fromArray(SP, input, pos + 3 * vl);

      var _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part1);
      _part1 = _t0.rearrange(_uzp1, _part1);
      var _t1 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part3);
      _part3 = _t1.rearrange(_uzp1, _part3);

      _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_uzp0, _part3);
      _part2 = _t0.rearrange(_uzp1, _part2);
      _part3 = _t1.rearrange(_uzp1, _part3);

      _part1 = _part1.add(_part0);
      _part3 = _part3.add(_part2);

      _part3 = _part3.add(_part1);

      var _prev = IntVector.broadcast(SP, acc);

      var _partZ = _prev.slice(vl - 1, _part3);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _zero.slice(vl - step, _partZ);
        _partZ = _partZ.add(_shifted);
      }

      _part0 = _part0.add(_partZ);
      _part1 = _part1.add(_partZ);
      _part3 = _part3.add(_partZ);

      _part2 = _part2.add(_part1);

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

      _part0.intoArray(output, pos + 0 * vl);
      _part1.intoArray(output, pos + 1 * vl);
      _part2.intoArray(output, pos + 2 * vl);
      _part3.intoArray(output, pos + 3 * vl);

      pos += 4 * vl;
    }

    while (longRemainder-- > 0) {
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

    final IntVector _zero = IntVector.zero(SP);

    final var _zip0 = VectorShuffle.makeZip(SP, 0);
    final var _zip1 = VectorShuffle.makeZip(SP, 1);
    final var _uzp0 = VectorShuffle.makeUnzip(SP, 0);
    final var _uzp1 = VectorShuffle.makeUnzip(SP, 1);

    int longIterations = arraySize / (4 * vl);
    int longRemainder = arraySize % (4 * vl);
    ;

    int acc = 0, pos = input.length;
    while (longRemainder-- > 0) {
      acc += input[--pos];
      output[pos] = acc;
    }

    var _prev = IntVector.broadcast(SP, acc);

    for (int i = 0; i < longIterations; i++) {
      pos -= 4 * vl;

      var _part0 = IntVector.fromArray(SP, input, pos);
      var _part1 = IntVector.fromArray(SP, input, pos + vl);
      var _part2 = IntVector.fromArray(SP, input, pos + 2 * vl);
      var _part3 = IntVector.fromArray(SP, input, pos + 3 * vl);

      var _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part1);
      _part1 = _t0.rearrange(_uzp1, _part1);
      var _t1 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part3);
      _part3 = _t1.rearrange(_uzp1, _part3);

      _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_uzp0, _part3);
      _part2 = _t0.rearrange(_uzp1, _part2);
      _part3 = _t1.rearrange(_uzp1, _part3);

      _part2 = _part2.add(_part3);
      _part0 = _part0.add(_part1);

      _part0 = _part0.add(_part2);

      var _partZ = _part0.slice(1, _prev);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _partZ.slice(step, _zero);
        _partZ = _partZ.add(_shifted);
      }

      _part3 = _part3.add(_partZ);
      _part2 = _part2.add(_partZ);
      _part0 = _part0.add(_partZ);

      _part1 = _part1.add(_part2);

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

      _part0.intoArray(output, pos + 0 * vl);
      _part1.intoArray(output, pos + 1 * vl);
      _part2.intoArray(output, pos + 2 * vl);
      _part3.intoArray(output, pos + 3 * vl);
    }
  }
}
