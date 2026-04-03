package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class CollatzConjectureCombined {
  static final VectorSpecies<Long> SL =
    LongVector.SPECIES_PREFERRED;
  static final VectorSpecies<Short> SS =
    ShortVector.SPECIES_PREFERRED;

  static final short[] a128 = {
    27, 9, 9, 81, 81, 27, 9, 81, 9, 27, 3, 27, 27, 81, 27, 243};
  static final short[] b128 = {
    2, 1, 2, 20, 26, 10, 4, 40, 5, 17, 2, 20, 22, 71, 26, 242};
  static final short[] c128 = {
    8, 7, 7, 9, 9, 8, 7, 9, 7, 8, 6, 8, 8, 9, 8, 10};

  static final short[] a256 = {
    27, 27, 9, 81, 81, 27, 9, 81, 27, 81, 3, 27, 27, 243, 27, 243,
    81, 9, 27, 243, 243, 81, 27, 243, 9, 27, 9, 81, 81, 81, 81, 729};
  static final short[] b256 = {
    1, 2, 1, 10, 13, 5, 2, 20, 8, 26, 1, 10, 11, 107, 13, 121,
    44, 5, 17, 152, 161, 56, 20, 182, 7, 22, 8, 71, 74, 76, 80, 728};
  static final short[] c256 = {
    9, 9, 8, 10, 10, 9, 8, 10, 9, 10, 7, 9, 9, 11, 9, 11,
    10, 8, 9, 11, 11, 10, 9, 11, 8, 9, 8, 10, 10, 10, 10, 12};

  static final short[] a512 = {
    81, 27, 27, 81, 243, 81, 9, 81, 27, 81, 9, 27, 81, 729, 81,
    729, 81, 27, 81, 243, 729, 81, 27, 243, 27, 27, 9, 243, 81, 81,
    81, 729, 27, 81, 9, 243, 81, 27, 27, 243, 81, 243, 3, 81, 27,
    243, 27, 243, 243, 9, 27, 729, 243, 243, 81, 729, 9, 81, 27, 81,
    243, 243, 243, 2187};
  static final short[] b512 = {
    2, 1, 2, 5, 20, 8, 1, 10, 4, 13, 2, 5, 17, 161, 20, 182,
    22, 8, 26, 76, 242, 28, 10, 91, 11, 11, 4, 107, 37, 38, 40,
    364, 14, 44, 5, 137, 47, 16, 17, 152, 53, 161, 2, 56, 19, 175,
    20, 182, 188, 7, 22, 593, 202, 206, 71, 638, 8, 74, 26, 76,
    233, 236, 242, 2186};
  static final short[] c512 = {
    11, 10, 10, 11, 12, 11, 9, 11, 10, 11, 9, 10, 11, 13, 11, 13,
    11, 10, 11, 12, 13, 11, 10, 12, 10, 10, 9, 12, 11, 11, 11, 13,
    10, 11, 9, 12, 11, 10, 10, 12, 11, 12, 8, 11, 10, 12, 10, 12,
    12, 9, 10, 13, 12, 12, 11, 13, 9, 11, 10, 11, 12, 12, 12, 14};

  static final short[] d512 = {
    1, 8, 6, 17, 20, 15, 10, 18, 13, 21, 8, 16, 24, 112, 19, 107,
    27, 14, 22, 35, 110, 30, 17, 105, 25, 25, 12, 113, 33, 33, 20,
    108, 28, 28, 15, 103, 116, 15, 23, 36, 23, 111, 10, 31, 31, 93,
    18, 106, 119, 26, 26, 88, 39, 101, 114, 70, 13, 34, 21, 34, 96,
    47, 109, 47};

  static public long longestOrbit(int problemSize) {
    final int vs = SS.length();

    long xShift, yMask;
    ShortVector _a0, _b0, _c0, _d0;
    ShortVector _a1, _b1, _c1, _d1;

    switch (vs) {
      case 8:
        xShift = 5;
        yMask = ~(-1 << xShift);

        _a0 = ShortVector.fromArray(SS, a128, 0);
        _b0 = ShortVector.fromArray(SS, b128, 0);
        _c0 = ShortVector.fromArray(SS, c128, 0);
        _d0 = ShortVector.fromArray(SS, d512, 0);

        _a1 = ShortVector.fromArray(SS, a128, vs);
        _b1 = ShortVector.fromArray(SS, b128, vs);
        _c1 = ShortVector.fromArray(SS, c128, vs);
        _d1 = ShortVector.fromArray(SS, d512, vs);
        break;

      case 16:
        xShift = 6;
        yMask = ~(-1 << xShift);

        _a0 = ShortVector.fromArray(SS, a256, 0);
        _b0 = ShortVector.fromArray(SS, b256, 0);
        _c0 = ShortVector.fromArray(SS, c256, 0);
        _d0 = ShortVector.fromArray(SS, d512, 0);

        _a1 = ShortVector.fromArray(SS, a256, vs);
        _b1 = ShortVector.fromArray(SS, b256, vs);
        _c1 = ShortVector.fromArray(SS, c256, vs);
        _d1 = ShortVector.fromArray(SS, d512, vs);
        break;

      default:
        xShift = 7;
        yMask = ~(-1 << xShift);

        var _load = SS.indexInRange(0, 64);
        _a0 = ShortVector.fromArray(SS, a512, 0, _load);
        _b0 = ShortVector.fromArray(SS, b512, 0, _load);
        _c0 = ShortVector.fromArray(SS, c512, 0, _load);
        _d0 = ShortVector.fromArray(SS, d512, 0, _load);

        _load = SS.indexInRange(vs, 64);
        _a1 = ShortVector.fromArray(SS, a512, vs, _load);
        _b1 = ShortVector.fromArray(SS, b512, vs, _load);
        _c1 = ShortVector.fromArray(SS, c512, vs, _load);
        _d1 = ShortVector.fromArray(SS, d512, vs, _load);
    }

    final int vl = SL.length();
    final long longMask = 0x000000000000FFFFL;
    final LongVector _zero = LongVector.zero(SL);

    LongVector _max = _zero;
    LongVector _start = _zero;

    LongVector _idx = VectorShuffle.iota(SL, 0, 1, false)
      .toVector().reinterpretAsLongs();

    for (int i = 2; i < problemSize; i += vl) {
      var _inRange = SL.indexInRange(i, problemSize);

      LongVector _val = _idx.add(i, _inRange);
      LongVector _pos = _val;
      LongVector _orbit = _zero;

      for (; ; ) {
        LongVector _ctz = _val.lanewise(TRAILING_ZEROS_COUNT);

        _val = _val.lanewise(LSHR, _ctz);
        _orbit = _orbit.add(_ctz);

        LongVector _x = _val.lanewise(LSHR, xShift);
        var _y = _val.and(yMask).lanewise(LSHR, 1);
        ShortVector _m = _y.reinterpretAsShorts();

        VectorMask<Long> _done = _x.eq(0);

        if (_done.allTrue()) {
          var _dd = _m.selectFrom(_d0, _d1).reinterpretAsLongs();
          _dd = _dd.and(longMask);

          _orbit = _orbit.add(_dd);

          VectorMask<Long> _less = _max.lt(_orbit);

          _max = _max.blend(_orbit, _less);
          _start = _start.blend(_pos, _less);

          break;
        } else {
          var _aa = _m.selectFrom(_a0, _a1).reinterpretAsLongs();
          var _bb = _m.selectFrom(_b0, _b1).reinterpretAsLongs();
          var _cc = _m.selectFrom(_c0, _c1).reinterpretAsLongs();

          _aa = _aa.and(longMask);
          _bb = _bb.and(longMask);
          _cc = _cc.and(longMask);

          _val = _x.mul(_aa).add(_bb).blend(_val, _done);
          _orbit = _orbit.add(_cc).blend(_orbit, _done);
        }
      }
    }

    for (int step = 1; step < SL.length(); step <<= 1) {
      LongVector _shiftedMax = _max.slice(step, _zero);
      LongVector _shiftedStart = _start.slice(step, _zero);

      VectorMask<Long> _less = _max.lt(_shiftedMax);
      _max = _max.blend(_shiftedMax, _less);
      _start = _start.blend(_shiftedStart, _less);
    }

    long start = _start.lane(0);
    return start;
  }
}
