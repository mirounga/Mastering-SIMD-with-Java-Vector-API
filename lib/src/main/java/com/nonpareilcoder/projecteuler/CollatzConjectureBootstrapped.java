package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

public class CollatzConjectureBootstrapped {
  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_PREFERRED;

  static final long[] a4 = new long[]{1, 3, 3, 9};
  static final long[] b4 = new long[]{0, 1, 2, 8};
  static final long[] c4 = new long[]{2, 3, 3, 4};

  static final long[] a8 = new long[]{1, 9, 3, 9, 3, 3, 9, 27};
  static final long[] b8 = new long[]{0, 2, 1, 4, 2, 2, 8, 26};
  static final long[] c8 = new long[]{3, 5, 4, 5, 4, 4, 5, 6};

  static final long[] a16 = {1, 9, 9, 9, 3, 3, 9, 27, 3, 27, 3, 27, 9, 9, 27, 81};
  static final long[] b16 = {0, 1, 2, 2, 1, 1, 4, 13, 2, 17, 2, 20, 8, 8, 26, 80};
  static final long[] c16 = {4, 6, 6, 6, 5, 5, 6, 7, 5, 7, 5, 7, 6, 6, 7, 8};

  static public long longestOrbit(int problemSize) {
    final int vl = SP.length();

    long xShift = 2;
    long yMask = ~(-1 << xShift);

    var _load = SP.indexInRange(0, 4);
    LongVector _a0 = LongVector.fromArray(SP, a4, 0, _load);
    LongVector _b0 = LongVector.fromArray(SP, b4, 0, _load);
    LongVector _c0 = LongVector.fromArray(SP, c4, 0, _load);

    _load = SP.indexInRange(vl, 4);
    LongVector _a1 = LongVector.fromArray(SP, a4, vl, _load);
    LongVector _b1 = LongVector.fromArray(SP, b4, vl, _load);
    LongVector _c1 = LongVector.fromArray(SP, c4, vl, _load);

    final LongVector _zero = LongVector.zero(SP);

    LongVector _max = _zero;
    LongVector _start = _zero;

    LongVector _idx = VectorShuffle.iota(SP, 0, 1, false)
        .toVector().reinterpretAsLongs();

    int i0 = 0;
    var _inRange = SP.indexInRange(i0, problemSize);

    LongVector _v0 = _idx.add(i0, _inRange);
    LongVector _p0 = _v0;
    LongVector _o0 = _zero, _o1 = _zero, _o2 = _zero;

    for (; ; ) {
      VectorMask<Long> _done = _v0.compare(LE, 2);

      if (_done.allTrue()) {
        _o0 = _o0.add(_v0);

        VectorMask<Long> _less = _max.compare(LT, _o0);

        _max = _max.blend(_o0, _less);
        _start = _start.blend(_p0, _less);

        i0 += vl;
        _inRange = SP.indexInRange(i0, problemSize);

        _v0 = _p0 = _idx.add(i0, _inRange);

        break;
      }

      LongVector _x = _v0.lanewise(LSHR, xShift);
      LongVector _y = _v0.and(yMask);

      LongVector _aa = _y.selectFrom(_a0, _a1);
      LongVector _bb = _y.selectFrom(_b0, _b1);
      LongVector _cc = _y.selectFrom(_c0, _c1);

      _v0 = _x.mul(_aa).add(_bb).blend(_v0, _done);
      _o0 = _o0.add(_cc).blend(_o0, _done);
    }

    for (; ; ) {
      VectorMask<Long> _done = _v0.compare(LE, 2);

      if (_done.allTrue()) {
        _o1 = _o1.add(_v0);

        VectorMask<Long> _less = _max.compare(LT, _o1);

        _max = _max.blend(_o1, _less);
        _start = _start.blend(_p0, _less);

        i0 += vl;
        _inRange = SP.indexInRange(i0, problemSize);

        _v0 = _p0 = _idx.add(i0, _inRange);

        break;
      }

      LongVector _x = _v0.lanewise(LSHR, xShift);
      VectorShuffle<Long> _y = _v0.and(yMask).toShuffle();

      LongVector _aa = _a0.rearrange(_y, _a1);
      LongVector _bb = _b0.rearrange(_y, _b1);
      LongVector _cc = _c0.rearrange(_y, _c1);

      _v0 = _x.mul(_aa).add(_bb).blend(_v0, _done);
      _o1 = _o1.add(_cc).blend(_o1, _done);
    }

    switch (vl) {
      case 2:
        break;

      case 4:
        xShift = 3;
        yMask = ~(-1 << xShift);

        _a0 = LongVector.fromArray(SP, a8, 0);
        _b0 = LongVector.fromArray(SP, b8, 0);
        _c0 = LongVector.fromArray(SP, c8, 0);
        _a1 = LongVector.fromArray(SP, a8, vl);
        _b1 = LongVector.fromArray(SP, b8, vl);
        _c1 = LongVector.fromArray(SP, c8, vl);
        break;

      default:
        xShift = 4;
        yMask = ~(-1 << xShift);

        _load = SP.indexInRange(0, 16);
        _a0 = LongVector.fromArray(SP, a16, 0, _load);
        _b0 = LongVector.fromArray(SP, b16, 0, _load);
        _c0 = LongVector.fromArray(SP, c16, 0, _load);

        _load = SP.indexInRange(vl, 16);
        _a1 = LongVector.fromArray(SP, a16, vl, _load);
        _b1 = LongVector.fromArray(SP, b16, vl, _load);
        _c1 = LongVector.fromArray(SP, c16, vl, _load);
    }

    final int i1 = i0;

    for (; ; ) {
      VectorMask<Long> _done = _v0.compare(LT, i1);

      if (_done.allTrue()) {
        VectorShuffle<Long> _v = _v0.toShuffle();
        _o2 = _o0.rearrange(_v, _o1).add(_o2);

        VectorMask<Long> _less = _max.compare(LT, _o2);

        _max = _max.blend(_o2, _less);
        _start = _start.blend(_p0, _less);

        i0 += vl;
        _inRange = SP.indexInRange(i0, problemSize);

        if (!_inRange.anyTrue())
          break;

        _v0 = _p0 = _idx.add(i0, _inRange);
        _o2 = _zero;
      }

      LongVector _x = _v0.lanewise(LSHR, xShift);
      VectorShuffle<Long> _y = _v0.and(yMask).toShuffle();

      LongVector _aa = _a0.rearrange(_y, _a1);
      LongVector _bb = _b0.rearrange(_y, _b1);
      LongVector _cc = _c0.rearrange(_y, _c1);

      _v0 = _x.mul(_aa).add(_bb).blend(_v0, _done);
      _o2 = _o2.add(_cc).blend(_o2, _done);
    }

    for (int step = 1; step < SP.length(); step <<= 1) {
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
