package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class MergeArraysAsymmetric {
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

    int stop0 = input0.length - 4 * vl;
    int stop1 = input1.length - 4 * vl;

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

      int advance0 = _cmp0.trueCount() + _cmp1.trueCount();
      int advance1 = 2 * vl - advance0;

      i0 += advance0;
      i1 += advance1;

      _part0 = _part2.blend(_part0, _cmp0);
      _part1 = _part3.blend(_part1, _cmp1);

      var _part4 = IntVector.fromArray(SP, input0, i0);
      var _part5 = IntVector.fromArray(SP, input0, i0 + vl);
      var _part7 = IntVector.fromArray(SP, input1, i1);
      var _part6 = IntVector.fromArray(SP, input1, i1 + vl);

      _part7 = _part7.rearrange(_rev);
      _part6 = _part6.rearrange(_rev);

      _cmp0 = _part4.compare(LT, _part6);
      _cmp1 = _part5.compare(LT, _part7);

      _part4 = _part6.blend(_part4, _cmp0);
      _part5 = _part7.blend(_part5, _cmp1);

      advance0 = _cmp0.trueCount() + _cmp1.trueCount();
      advance1 = 2 * vl - advance0;

      i0 += advance0;
      i1 += advance1;

      _cmp0 = _part0.compare(LT, _part1);

      var _t0 = _part0;
      _part0 = _part1.blend(_part0, _cmp0);
      _part1 = _t0.blend(_part1, _cmp0);

      for (int i = 1; i < vl; i <<= 1) {
        _t0 = _part0;
        _part0 = _part0.rearrange(_zip0, _part1);
        _part1 = _t0.rearrange(_zip1, _part1);

        _cmp1 = _part4.compare(LT, _part5);

        var _t1 = _part4;
        _part4 = _part5.blend(_part4, _cmp1);
        _part5 = _t1.blend(_part5, _cmp1);

        _t1 = _part4;
        _part4 = _part4.rearrange(_zip0, _part5);
        _part5 = _t1.rearrange(_zip1, _part5);

        _cmp0 = _part0.compare(LT, _part1);

        _t0 = _part0;
        _part0 = _part1.blend(_part0, _cmp0);
        _part1 = _t0.blend(_part1, _cmp0);
      }

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part1);
      _part1 = _t0.rearrange(_zip1, _part1);

      _cmp1 = _part4.compare(LT, _part5);

      var _t1 = _part4;
      _part4 = _part5.blend(_part4, _cmp1);
      _part5 = _t1.blend(_part5, _cmp1);

      _t1 = _part4;
      _part4 = _part4.rearrange(_zip0, _part5);
      _part5 = _t1.rearrange(_zip1, _part5);

      _part0.intoArray(output, j);
      _part1.intoArray(output, j + vl);
      _part4.intoArray(output, j + 2 * vl);
      _part5.intoArray(output, j + 3 * vl);

      j += 4 * vl;
    }

    MergeArraysScalar.mergeCore(input0, input1, output, i0, i1, j);

    return output;
  }
}
