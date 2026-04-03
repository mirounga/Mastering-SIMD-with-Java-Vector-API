package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;
import static com.nonpareilcoder.projecteuler.CollatzConjectureCombined.*;

public class CollatzConjecturePiled {
  static final VectorSpecies<Long> SL =
    LongVector.SPECIES_PREFERRED;
  static final VectorSpecies<Short> SS =
    ShortVector.SPECIES_PREFERRED;


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

    int i = 2;

    var _inRange = SL.indexInRange(i, problemSize);

    LongVector _val = _idx.add(i, _inRange);
    LongVector _pos = _val;
    LongVector _orbit = _zero;

    i += vl;

    while (i < problemSize) {
      LongVector _ctz = _val.lanewise(TRAILING_ZEROS_COUNT);

      _val = _val.lanewise(LSHR, _ctz);
      _orbit = _orbit.add(_ctz);

      LongVector _x = _val.lanewise(LSHR, xShift);
      LongVector _y = _val.and(yMask).lanewise(LSHR, 1);
      ShortVector _m = _y.reinterpretAsShorts();

      VectorMask<Long> _done = _x.eq(0);

      if (_done.anyTrue()) {
        var _dd = _m.selectFrom(_d0, _d1).reinterpretAsLongs();
        _dd = _dd.and(longMask);

        LongVector _result = _orbit.add(_dd);

        VectorMask<Long> _less = _max.lt(_result).and(_done);

        _max = _max.blend(_result, _less);
        _start = _start.blend(_pos, _less);

        _inRange = SL.indexInRange(i, problemSize);
        var _new = _zero.blend(_idx.add(i), _inRange).expand(_done);

        _val = _val.blend(_new, _done);
        _pos = _pos.blend(_new, _done);
        _orbit = _orbit.blend(_zero, _done);

        i += _done.trueCount();
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

    for (;;) {
      LongVector _ctz = _val.lanewise(TRAILING_ZEROS_COUNT);

      _val = _val.lanewise(LSHR, _ctz);
      _orbit = _orbit.add(_ctz);

      LongVector _x = _val.lanewise(LSHR, xShift);
      LongVector _y = _val.and(yMask).lanewise(LSHR, 1);
      VectorShuffle<Short> _m = _y.reinterpretAsShorts().toShuffle();

      VectorMask<Long> _done = _x.eq(0);

      if (_done.allTrue()) {
        LongVector _dd = _d0.rearrange(_m, _d1).reinterpretAsLongs();
        _dd = _dd.and(longMask);

        LongVector _result = _orbit.add(_dd);

        VectorMask<Long> _valid = _zero.lt(_pos);
        VectorMask<Long> _less = _max.lt(_result).and(_valid);

        _max = _max.blend(_result, _less);
        _start = _start.blend(_pos, _less);

        break;
      } else {
        LongVector _aa = _a0.rearrange(_m, _a1).reinterpretAsLongs();
        LongVector _bb = _b0.rearrange(_m, _b1).reinterpretAsLongs();
        LongVector _cc = _c0.rearrange(_m, _c1).reinterpretAsLongs();

        _aa = _aa.and(longMask);
        _bb = _bb.and(longMask);
        _cc = _cc.and(longMask);

        _val = _x.mul(_aa).add(_bb).blend(_val, _done);
        _orbit = _orbit.add(_cc).blend(_orbit, _done);
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
