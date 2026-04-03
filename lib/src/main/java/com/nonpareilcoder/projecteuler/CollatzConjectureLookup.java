package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

public class CollatzConjectureLookup {
  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_PREFERRED;

  static final long[] a4 = new long[]{1, 3, 3, 9};
  static final long[] b4 = new long[]{0, 1, 2, 8};
  static final long[] c4 = new long[]{2, 3, 3, 4};

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

    for (int i = 2; i < problemSize; i += vl) {
      var _inRange = SP.indexInRange(i, problemSize);

      LongVector _val = _idx.add(i, _inRange);
      LongVector _pos = _val;
      LongVector _orbit = _zero;

      for (; ; ) {
        VectorMask<Long> _done = _val.compare(LE, 2);
        if (_done.allTrue())
          break;

        LongVector _x = _val.lanewise(LSHR, xShift);
        LongVector _y = _val.and(yMask);

        LongVector _aa = _y.selectFrom(_a0, _a1);
        LongVector _bb = _y.selectFrom(_b0, _b1);
        LongVector _cc = _y.selectFrom(_c0, _c1);

        _val = _x.mul(_aa).add(_bb).blend(_val, _done);
        _orbit = _orbit.add(_cc).blend(_orbit, _done);
      }

      _orbit = _orbit.add(_val);

      var _less = _max.compare(LT, _orbit, _inRange);

      _max = _max.blend(_orbit, _less);
      _start = _start.blend(_pos, _less);
    }

    for (int step = 1; step < SP.length(); step <<= 1) {
      LongVector _shiftedMax = _max.slice(step, _zero);
      LongVector _shiftedStart = _start.slice(step, _zero);

      var _less = _max.compare(LT, _shiftedMax);
      _max = _max.blend(_shiftedMax, _less);
      _start = _start.blend(_shiftedStart, _less);
    }

    long start = _start.lane(0);
    return start;
  }
}
