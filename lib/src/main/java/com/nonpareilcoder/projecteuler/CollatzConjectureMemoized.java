package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;
import static com.nonpareilcoder.projecteuler.CollatzConjectureCombined.*;

public class CollatzConjectureMemoized {
  static final VectorSpecies<Long> SL =
    LongVector.SPECIES_PREFERRED;
  static final VectorSpecies<Short> SS =
    ShortVector.SPECIES_PREFERRED;

  static final long[] d = {
    0, 1, 2, 8, 3, 6, 9, 17, 4, 20, 7, 15, 10, 10, 18, 18,
    5, 13, 21, 21, 8, 8, 16, 16, 11, 24, 11, 112,
    19, 19, 19, 107,
    6, 27, 14, 14, 22, 22, 22, 35, 9, 110, 9, 30, 17, 17, 17, 105,
    12, 25, 25, 25, 12, 12, 113, 113, 20, 33, 20, 33,
    20, 20, 108, 108,
    7, 28, 28, 28, 15, 15, 15, 103, 23, 116, 23, 15, 23, 23, 36, 36,
    10, 23, 111, 111, 10, 10, 31, 31, 18, 31, 18, 93,
    18, 18, 106, 106,
    13, 119, 26, 26, 26, 26, 26, 88, 13, 39, 13, 101,
    114, 114, 114, 70,
    21, 13, 34, 34, 21, 21, 34, 34, 21, 96, 21, 47,
    109, 109, 109, 47};

  static public long longestOrbit(int problemSize) {
    long[] orbits = new long[problemSize];
    final int vs = SS.length();

    long xShift, yMask;
    ShortVector _a0, _b0, _c0;
    ShortVector _a1, _b1, _c1;

    switch (vs) {
      case 8:
        xShift = 5;
        yMask = ~(-1 << xShift);

        _a0 = ShortVector.fromArray(SS, a128, 0);
        _b0 = ShortVector.fromArray(SS, b128, 0);
        _c0 = ShortVector.fromArray(SS, c128, 0);

        _a1 = ShortVector.fromArray(SS, a128, vs);
        _b1 = ShortVector.fromArray(SS, b128, vs);
        _c1 = ShortVector.fromArray(SS, c128, vs);
        break;

      case 16:
        xShift = 6;
        yMask = ~(-1 << xShift);

        _a0 = ShortVector.fromArray(SS, a256, 0);
        _b0 = ShortVector.fromArray(SS, b256, 0);
        _c0 = ShortVector.fromArray(SS, c256, 0);

        _a1 = ShortVector.fromArray(SS, a256, vs);
        _b1 = ShortVector.fromArray(SS, b256, vs);
        _c1 = ShortVector.fromArray(SS, c256, vs);
        break;

      default:
        xShift = 7;
        yMask = ~(-1 << xShift);

        var _load = SS.indexInRange(0, 64);
        _a0 = ShortVector.fromArray(SS, a512, 0, _load);
        _b0 = ShortVector.fromArray(SS, b512, 0, _load);
        _c0 = ShortVector.fromArray(SS, c512, 0, _load);

        _load = SS.indexInRange(vs, 64);
        _a1 = ShortVector.fromArray(SS, a512, vs, _load);
        _b1 = ShortVector.fromArray(SS, b512, vs, _load);
        _c1 = ShortVector.fromArray(SS, c512, vs, _load);
    }

    final int vl = SL.length();
    final int cacheSize = Math.min(128, problemSize);
    final long longMask = 0x000000000000FFFFL;
    final LongVector _zero = LongVector.zero(SL);

    LongVector _max = _zero;
    LongVector _start = _zero;

    LongVector _idx = VectorShuffle.iota(SL, 0, 1, false)
      .toVector().reinterpretAsLongs();

    for (int i = 0; i < cacheSize; i += vl) {
      var _inRange = SL.indexInRange(i, problemSize);

      LongVector _pos = _idx.add(i, _inRange);
      LongVector _orbit = LongVector.fromArray(SL, d, i, _inRange);

      VectorMask<Long> _less = _max.lt(_orbit);

      _max = _max.blend(_orbit, _less);
      _start = _start.blend(_pos, _less);

      _orbit.intoArray(orbits, i, _inRange);
    }

    for (int i = cacheSize; i < problemSize; i += vl) {
      var _inRange = SL.indexInRange(i, problemSize);

      LongVector _val = _idx.add(i, _inRange);
      LongVector _pos = _val;
      LongVector _orbit = _zero;

      int[] gather = new int[2 * vl];

      for (; ; ) {
        LongVector _ctz = _val.lanewise(TRAILING_ZEROS_COUNT);

        _val = _val.lanewise(LSHR, _ctz);
        _orbit = _orbit.add(_ctz);

        VectorMask<Long> _done = _val.lt(i);

        if (_done.allTrue()) {
          ((IntVector) _val.convert(L2I, 0)).intoArray(gather, 0);
          var _v = LongVector.fromArray(SL, orbits, 0, gather, 0);

          _orbit = _orbit.add(_v);

          _orbit.intoArray(orbits, i, _inRange);

          VectorMask<Long> _less = _max.lt(_orbit);

          _max = _max.blend(_orbit, _less);
          _start = _start.blend(_pos, _less);

          break;
        } else {
          var _x = _val.lanewise(LSHR, xShift);
          var _y = _val.and(yMask).lanewise(LSHR, 1);
          var _m = _y.reinterpretAsShorts();

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

      VectorMask<Long> _less = _max.compare(LT, _shiftedMax);
      _max = _max.blend(_shiftedMax, _less);
      _start = _start.blend(_shiftedStart, _less);
    }

    long start = _start.lane(0);
    return start;
  }
}
