package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

public class MergeArraysScatter {
  static final VectorSpecies<Integer> SP =
      IntVector.SPECIES_PREFERRED;
  static final int vl = SP.length();

  public static int[] merge(int[] input0, int[] input1) {
    int[] output = new int[input0.length + input1.length];

    int stop0 = input0.length - vl;
    int stop1 = input1.length - vl;

    IntVector _iota = VectorShuffle.iota(SP, 0, 1, false)
      .toVector().reinterpretAsInts();

    int[] pos0 = new int[vl];
    int[] pos1 = new int[vl];

    int i0 = 0, i1 = 0, j = 0;
    for (; i0 < stop0 && i1 < stop1;) {
      IntVector _part0 = IntVector.fromArray(SP, input0, i0);
      IntVector _part1 = IntVector.fromArray(SP, input1, i1);

      IntVector _shift = IntVector.broadcast(SP, vl);
      IntVector _pos0 = _shift, _pos1 = _shift;

      for (int step = vl; step > 0; step >>= 1) {
        _shift = _shift.lanewise(ASHR, 1,
            _shift.compare(GT, 1));

        IntVector _new0 = _pos0.sub(_shift);
        IntVector _new1 = _pos1.sub(_shift);

        var _comp0 = _part1.rearrange(_new0.toShuffle())
            .compare(GT, _part0);
        var _comp1 = _part0.rearrange(_new1.toShuffle())
            .compare(GE, _part1);

        _pos0 = _pos0.blend(_new0, _comp0);
        _pos1 = _pos1.blend(_new1, _comp1);
      }

      _pos0 = _pos0.add(_iota);
      _pos1 = _pos1.add(_iota);

      _pos0.intoArray(pos0, 0);
      _pos1.intoArray(pos1, 0);

      _part0.intoArray(output, j, pos0, 0);
      _part1.intoArray(output, j, pos1, 0);

      var _cmp = _pos0.compare(LT, vl);
      int advance0 = _cmp.trueCount();
      int advance1 = vl - advance0;

      i0 += advance0;
      i1 += advance1;

      j += vl;
    }

    MergeArraysScalar.mergeCore(input0, input1, output, i0, i1, j);

    return output;
  }
}
