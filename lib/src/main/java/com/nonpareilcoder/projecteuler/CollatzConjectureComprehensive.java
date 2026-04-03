package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;
import static com.nonpareilcoder.projecteuler.CollatzConjectureCombined.*;
import static com.nonpareilcoder.projecteuler.CollatzConjectureMemoized.d;

public class CollatzConjectureComprehensive {
  static final VectorSpecies<Long> SL =
    LongVector.SPECIES_PREFERRED;
  static final VectorSpecies<Short> SS =
    ShortVector.SPECIES_PREFERRED;

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

    double used = 0.0;

    for (int i0 = 0; i0 < cacheSize; i0 += vl) {
      var _inRange = SL.indexInRange(i0, problemSize);

      LongVector _pos = _idx.add(i0, _inRange);
      LongVector _orbit = LongVector.fromArray(SL, d, i0, _inRange);

      VectorMask<Long> _less = _max.lt(_orbit);

      _max = _max.blend(_orbit, _less);
      _start = _start.blend(_pos, _less);

      _orbit.intoArray(orbits, i0, _inRange);
    }

    int i0 = cacheSize, i1 = cacheSize;

    VectorMask<Long> _inRange = SL.indexInRange(i0, problemSize);

    LongVector _v0 = _idx.add(i0, _inRange);
    LongVector _p0 = _v0;
    LongVector _o0 = _zero;

    i0 += vl;

    int[] gather = new int[2 * vl];
    int[] scatter = new int[2 * vl];

    while (i0 < problemSize) {
      LongVector _ctz = _v0.lanewise(TRAILING_ZEROS_COUNT);

      _v0 = _v0.lanewise(LSHR, _ctz);
      _o0 = _o0.add(_ctz);

      VectorMask<Long> _done = _v0.lt(i1);

      if (_done.anyTrue()) {
        VectorMask<Long> _active = _done.not();
        LongVector _v1 = _v0.blend(0, _active);

        ((IntVector) _v1.convert(L2I, 0)).intoArray(gather, 0);
        var _result = LongVector.fromArray(SL, orbits, 0, gather, 0, _done);

        _result = _result.add(_o0, _done);

        ((IntVector) _p0.convert(L2I, 0)).intoArray(scatter, 0);
        _result.intoArray(orbits, 0, scatter, 0, _done);

        used += 2;

        VectorMask<Long> _less = _max.compare(LT, _result, _done);

        _max = _max.blend(_result, _less);
        _start = _start.blend(_p0, _less);

        _v0 = _v0.compress(_active);
        _p0 = _p0.compress(_active);
        _o0 = _o0.compress(_active);

        VectorMask<Long> _insert = _active.compress().not();

        _inRange = SL.indexInRange(i0, problemSize);
        var _new = _zero.blend(_idx.add(i0), _inRange).expand(_insert);

        _v0 = _v0.blend(_new, _insert);
        _p0 = _p0.blend(_new, _insert);

        i0 += _done.trueCount();
        i1 = (int) _p0.lane(0);
      } else {
        LongVector _x = _v0.lanewise(LSHR, xShift);
        LongVector _y = _v0.and(yMask).lanewise(LSHR, 1);
        var _m = _y.reinterpretAsShorts();

        var _aa = _m.selectFrom(_a0, _a1).reinterpretAsLongs();
        var _bb = _m.selectFrom(_b0, _b1).reinterpretAsLongs();
        var _cc = _m.selectFrom(_c0, _c1).reinterpretAsLongs();

        _aa = _aa.and(longMask);
        _bb = _bb.and(longMask);
        _cc = _cc.and(longMask);

        _v0 = _x.mul(_aa).add(_bb).blend(_v0, _done);
        _o0 = _o0.add(_cc).blend(_o0, _done);
      }
    }

    for (; ; ) {
      LongVector _ctz = _v0.lanewise(TRAILING_ZEROS_COUNT);

      _v0 = _v0.lanewise(LSHR, _ctz);
      _o0 = _o0.add(_ctz);

      VectorMask<Long> _done = _v0.lt(i1);

      if (_done.allTrue()) {
        LongVector _v1 = _zero.blend(_v0, _done);

        ((IntVector) _v1.convert(L2I, 0)).intoArray(gather, 0);
        var _result = LongVector.fromArray(SL, orbits, 0, gather, 0, _done);

        _result = _result.add(_o0, _done);

        ((IntVector) _p0.convert(L2I, 0)).intoArray(gather, 0);
        _result.intoArray(orbits, 0, gather, 0, _done);

        VectorMask<Long> _less = _max.compare(LT, _result, _done);

        _max = _max.blend(_result, _less);
        _start = _start.blend(_p0, _less);

        break;
      }
      else {
        LongVector _x = _v0.lanewise(LSHR, xShift);
        LongVector _y = _v0.and(yMask).lanewise(LSHR, 1);
        var _m = _y.reinterpretAsShorts();

        var _aa = _m.selectFrom(_a0, _a1).reinterpretAsLongs();
        var _bb = _m.selectFrom(_b0, _b1).reinterpretAsLongs();
        var _cc = _m.selectFrom(_c0, _c1).reinterpretAsLongs();

        _aa = _aa.and(longMask);
        _bb = _bb.and(longMask);
        _cc = _cc.and(longMask);

        _v0 = _x.mul(_aa).add(_bb).blend(_v0, _done);
        _o0 = _o0.add(_cc).blend(_o0, _done);
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
