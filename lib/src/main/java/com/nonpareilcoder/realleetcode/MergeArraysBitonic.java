package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class MergeArraysBitonic {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;
  static final int vl = SP.length();

  static final VectorShuffle<Integer> _rev =
    VectorShuffle.iota(SP, vl - 1, -1, false);

  static final VectorShuffle<Integer> _zip0 =
    VectorShuffle.makeZip(SP, 0);
  static final VectorShuffle<Integer> _zip1 =
    VectorShuffle.makeZip(SP, 1);

  public static int[] merge(int[] input0, int[] input1) {
    int[] output = new int[input0.length + input1.length];

    int stop0 = input0.length - 2 * vl;
    int stop1 = input1.length - 2 * vl;

    int i0 = 0, i1 = 0, j = 0;
    for (; i0 < stop0 && i1 < stop1; ) {
      var _part0 = IntVector.fromArray(SP, input0, i0);
      var _part1 = IntVector.fromArray(SP, input0, i0 + vl);
      var _part3 = IntVector.fromArray(SP, input1, i1);
      var _part2 = IntVector.fromArray(SP, input1, i1 + vl);

      _part3 = _part3.rearrange(_rev);
      _part2 = _part2.rearrange(_rev);

      var _cmp0 = _part0.compare(LT, _part2);
      var _cmp1 = _part1.compare(LT, _part3);

      _part0 = _part2.blend(_part0, _cmp0);
      _part1 = _part3.blend(_part1, _cmp1);

      int advance0 = _cmp0.trueCount() + _cmp1.trueCount();
      int advance1 = 2 * vl - advance0;

      i0 += advance0;
      i1 += advance1;

      for (int i = 1; i <= vl; i <<= 1) {
        _cmp0 = _part0.compare(LT, _part1);

        var _t0 = _part0;
        _part0 = _part1.blend(_part0, _cmp0);
        _part1 = _t0.blend(_part1, _cmp0);

        _t0 = _part0;
        _part0 = _part0.rearrange(_zip0, _part1);
        _part1 = _t0.rearrange(_zip1, _part1);
      }

      _part0.intoArray(output, j);
      _part1.intoArray(output, j + vl);

      j += 2 * vl;
    }

    MergeArraysScalar.mergeCore(input0, input1, output, i0, i1, j);

    return output;
  }
}
