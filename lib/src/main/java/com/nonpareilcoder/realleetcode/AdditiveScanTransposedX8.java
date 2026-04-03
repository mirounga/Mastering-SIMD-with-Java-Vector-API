package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

public class AdditiveScanTransposedX8 {
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

    int longIterations = arraySize / (8 * vl);
    int longRemainder = arraySize % (8 * vl);

    var _prev = _zero;

    int pos = 0;
    for (int i = 0; i < longIterations; i++) {
      var _part0 = IntVector.fromArray(SP, input, pos);
      var _part1 = IntVector.fromArray(SP, input, pos + vl);
      var _part2 = IntVector.fromArray(SP, input, pos + 2 * vl);
      var _part3 = IntVector.fromArray(SP, input, pos + 3 * vl);
      var _part4 = IntVector.fromArray(SP, input, pos + 4 * vl);
      var _part5 = IntVector.fromArray(SP, input, pos + 5 * vl);
      var _part6 = IntVector.fromArray(SP, input, pos + 6 * vl);
      var _part7 = IntVector.fromArray(SP, input, pos + 7 * vl);

      var _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part1);
      _part1 = _t0.rearrange(_uzp1, _part1);
      var _t1 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part3);
      _part3 = _t1.rearrange(_uzp1, _part3);
      var _t2 = _part4;
      _part4 = _part4.rearrange(_uzp0, _part5);
      _part5 = _t2.rearrange(_uzp1, _part5);
      var _t3 = _part6;
      _part6 = _part6.rearrange(_uzp0, _part7);
      _part7 = _t3.rearrange(_uzp1, _part7);

      _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_uzp0, _part3);
      _part2 = _t0.rearrange(_uzp1, _part2);
      _part3 = _t1.rearrange(_uzp1, _part3);
      _t2 = _part4;
      _part4 = _part4.rearrange(_uzp0, _part6);
      _t3 = _part5;
      _part5 = _part5.rearrange(_uzp0, _part7);
      _part6 = _t2.rearrange(_uzp1, _part6);
      _part7 = _t3.rearrange(_uzp1, _part7);

      _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part4);
      _t1 = _part1;
      _part1 = _part1.rearrange(_uzp0, _part5);
      _t2 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part6);
      _t3 = _part3;
      _part3 = _part3.rearrange(_uzp0, _part7);
      _part4 = _t0.rearrange(_uzp1, _part4);
      _part5 = _t1.rearrange(_uzp1, _part5);
      _part6 = _t2.rearrange(_uzp1, _part6);
      _part7 = _t3.rearrange(_uzp1, _part7);

      _part1 = _part1.add(_part0);
      _part3 = _part3.add(_part2);
      _part5 = _part5.add(_part4);
      _part7 = _part7.add(_part6);

      _part3 = _part3.add(_part1);
      _part7 = _part7.add(_part5);

      _part7 = _part7.add(_part3);

      var _partZ = _prev.slice(vl - 1, _part7);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _zero.slice(vl - step, _partZ);
        _partZ = _partZ.add(_shifted);
      }

      _part0 = _part0.add(_partZ);
      _part1 = _part1.add(_partZ);
      _part3 = _part3.add(_partZ);
      _part7 = _part7.add(_partZ);

      _part5 = _part5.add(_part3);

      _part2 = _part2.add(_part1);
      _part4 = _part4.add(_part3);
      _part6 = _part6.add(_part5);

      _prev = _part7;

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part4);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part5);
      _t2 = _part2;
      _part2 = _part2.rearrange(_zip0, _part6);
      _t3 = _part3;
      _part3 = _part3.rearrange(_zip0, _part7);
      _part4 = _t0.rearrange(_zip1, _part4);
      _part5 = _t1.rearrange(_zip1, _part5);
      _part6 = _t2.rearrange(_zip1, _part6);
      _part7 = _t3.rearrange(_zip1, _part7);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part3);
      _part2 = _t0.rearrange(_zip1, _part2);
      _part3 = _t1.rearrange(_zip1, _part3);
      _t2 = _part4;
      _part4 = _part4.rearrange(_zip0, _part6);
      _t3 = _part5;
      _part5 = _part5.rearrange(_zip0, _part7);
      _part6 = _t2.rearrange(_zip1, _part6);
      _part7 = _t3.rearrange(_zip1, _part7);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part1);
      _part1 = _t0.rearrange(_zip1, _part1);
      _t1 = _part2;
      _part2 = _part2.rearrange(_zip0, _part3);
      _part3 = _t1.rearrange(_zip1, _part3);
      _t2 = _part4;
      _part4 = _part4.rearrange(_zip0, _part5);
      _part5 = _t2.rearrange(_zip1, _part5);
      _t3 = _part6;
      _part6 = _part6.rearrange(_zip0, _part7);
      _part7 = _t3.rearrange(_zip1, _part7);

      _part0.intoArray(output, pos + 0 * vl);
      _part1.intoArray(output, pos + 1 * vl);
      _part2.intoArray(output, pos + 2 * vl);
      _part3.intoArray(output, pos + 3 * vl);
      _part4.intoArray(output, pos + 4 * vl);
      _part5.intoArray(output, pos + 5 * vl);
      _part6.intoArray(output, pos + 6 * vl);
      _part7.intoArray(output, pos + 7 * vl);

      pos += 8 * vl;
    }

    int acc = _prev.lane(vl - 1);

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

    final IntVector _zero = IntVector.broadcast(SP, 0);

    final var _zip0 = VectorShuffle.makeZip(SP, 0);
    final var _zip1 = VectorShuffle.makeZip(SP, 1);
    final var _uzp0 = VectorShuffle.makeUnzip(SP, 0);
    final var _uzp1 = VectorShuffle.makeUnzip(SP, 1);

    int longIterations = arraySize / (8 * vl);
    int longRemainder = arraySize % (8 * vl);
    ;

    int acc = 0;

    int pos = input.length;
    while (longRemainder-- > 0) {
      acc += input[--pos];
      output[pos] = acc;
    }

    var _prev = IntVector.broadcast(SP, acc);

    for (int i = 0; i < longIterations; i++) {
      pos -= 8 * vl;

      var _part0 = IntVector.fromArray(SP, input, pos);
      var _part1 = IntVector.fromArray(SP, input, pos + vl);
      var _part2 = IntVector.fromArray(SP, input, pos + 2 * vl);
      var _part3 = IntVector.fromArray(SP, input, pos + 3 * vl);
      var _part4 = IntVector.fromArray(SP, input, pos + 4 * vl);
      var _part5 = IntVector.fromArray(SP, input, pos + 5 * vl);
      var _part6 = IntVector.fromArray(SP, input, pos + 6 * vl);
      var _part7 = IntVector.fromArray(SP, input, pos + 7 * vl);

      var _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part1);
      _part1 = _t0.rearrange(_uzp1, _part1);
      var _t1 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part3);
      _part3 = _t1.rearrange(_uzp1, _part3);
      var _t2 = _part4;
      _part4 = _part4.rearrange(_uzp0, _part5);
      _part5 = _t2.rearrange(_uzp1, _part5);
      var _t3 = _part6;
      _part6 = _part6.rearrange(_uzp0, _part7);
      _part7 = _t3.rearrange(_uzp1, _part7);

      _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_uzp0, _part3);
      _part2 = _t0.rearrange(_uzp1, _part2);
      _part3 = _t1.rearrange(_uzp1, _part3);
      _t2 = _part4;
      _part4 = _part4.rearrange(_uzp0, _part6);
      _t3 = _part5;
      _part5 = _part5.rearrange(_uzp0, _part7);
      _part6 = _t2.rearrange(_uzp1, _part6);
      _part7 = _t3.rearrange(_uzp1, _part7);

      _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part4);
      _t1 = _part1;
      _part1 = _part1.rearrange(_uzp0, _part5);
      _t2 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part6);
      _t3 = _part3;
      _part3 = _part3.rearrange(_uzp0, _part7);
      _part4 = _t0.rearrange(_uzp1, _part4);
      _part5 = _t1.rearrange(_uzp1, _part5);
      _part6 = _t2.rearrange(_uzp1, _part6);
      _part7 = _t3.rearrange(_uzp1, _part7);

      _part6 = _part6.add(_part7);
      _part4 = _part4.add(_part5);
      _part2 = _part2.add(_part3);
      _part0 = _part0.add(_part1);

      _part4 = _part4.add(_part6);
      _part0 = _part0.add(_part2);

      _part0 = _part0.add(_part4);

      var _partZ = _part0.slice(1, _prev);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _partZ.slice(step, _zero);
        _partZ = _partZ.add(_shifted);
      }

      _part7 = _part7.add(_partZ);
      _part6 = _part6.add(_partZ);
      _part4 = _part4.add(_partZ);
      _part0 = _part0.add(_partZ);

      _part2 = _part2.add(_part4);

      _part5 = _part5.add(_part6);
      _part3 = _part3.add(_part4);
      _part1 = _part1.add(_part2);

      _prev = _part0;

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part4);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part5);
      _t2 = _part2;
      _part2 = _part2.rearrange(_zip0, _part6);
      _t3 = _part3;
      _part3 = _part3.rearrange(_zip0, _part7);
      _part4 = _t0.rearrange(_zip1, _part4);
      _part5 = _t1.rearrange(_zip1, _part5);
      _part6 = _t2.rearrange(_zip1, _part6);
      _part7 = _t3.rearrange(_zip1, _part7);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part3);
      _part2 = _t0.rearrange(_zip1, _part2);
      _part3 = _t1.rearrange(_zip1, _part3);
      _t2 = _part4;
      _part4 = _part4.rearrange(_zip0, _part6);
      _t3 = _part5;
      _part5 = _part5.rearrange(_zip0, _part7);
      _part6 = _t2.rearrange(_zip1, _part6);
      _part7 = _t3.rearrange(_zip1, _part7);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part1);
      _part1 = _t0.rearrange(_zip1, _part1);
      _t1 = _part2;
      _part2 = _part2.rearrange(_zip0, _part3);
      _part3 = _t1.rearrange(_zip1, _part3);
      _t2 = _part4;
      _part4 = _part4.rearrange(_zip0, _part5);
      _part5 = _t2.rearrange(_zip1, _part5);
      _t3 = _part6;
      _part6 = _part6.rearrange(_zip0, _part7);
      _part7 = _t3.rearrange(_zip1, _part7);

      _part0.intoArray(output, pos + 0 * vl);
      _part1.intoArray(output, pos + 1 * vl);
      _part2.intoArray(output, pos + 2 * vl);
      _part3.intoArray(output, pos + 3 * vl);
      _part4.intoArray(output, pos + 4 * vl);
      _part5.intoArray(output, pos + 5 * vl);
      _part6.intoArray(output, pos + 6 * vl);
      _part7.intoArray(output, pos + 7 * vl);

    }
  }
}
