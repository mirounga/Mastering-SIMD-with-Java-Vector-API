package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

public class CollatzConjectureVectorized {
  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_PREFERRED;

  static public long longestOrbit(int problemSize) {
    final int vl = SP.length();
    final LongVector _zero = LongVector.zero(SP);

    LongVector _max = _zero;
    LongVector _start = _zero;

    LongVector _idx = VectorShuffle.iota(SP, 0, 1, false)
        .toVector().reinterpretAsLongs();

    for (int i = 2; i < problemSize; i += vl) {
      var _inRange = SP.indexInRange(i, problemSize);

      LongVector _val = _idx.add(i, _inRange);
      LongVector _pos = _val;
      LongVector _orbit = LongVector.broadcast(SP, 1);

      for (; ; ) {
        VectorMask<Long> _done = _val.compare(LE, 1);
        if (_done.allTrue())
          break;

        var _odd = _val.and(1).compare(EQ, 1);

        LongVector _valOdd = _val.mul(3).add(1);
        LongVector _valEven = _val.lanewise(LSHR, 1);

        _val = _valEven.blend(_valOdd, _odd).blend(1, _done);
        _orbit = _orbit.add(1).blend(_orbit, _done);
      }

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
