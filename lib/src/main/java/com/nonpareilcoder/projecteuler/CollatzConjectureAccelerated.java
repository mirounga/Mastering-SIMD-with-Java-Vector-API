package com.nonpareilcoder.projecteuler;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

public class CollatzConjectureAccelerated {
  static final VectorSpecies<Long> SP =
    LongVector.SPECIES_PREFERRED;

  static public long longestOrbit(int problemSize) {
    final int vl = SP.length();

    LongVector _max = LongVector.zero(SP);
    LongVector _start = LongVector.zero(SP);

    LongVector _idx = VectorShuffle.iota(SP, 0, 1, false)
        .toVector().reinterpretAsLongs();

    for (int i = 2; i < problemSize; i += vl) {
      var _inRange = SP.indexInRange(i, problemSize);

      LongVector _val = _idx.add(i, _inRange);
      LongVector _pos = _val;
      LongVector _orbit = LongVector.broadcast(SP, 1);

      for (;;) {
        var _ctz = _val.lanewise(TRAILING_ZEROS_COUNT);

        _val = _val.lanewise(LSHR, _ctz);
        _orbit = _orbit.add(_ctz);

        var _active = _val.compare(GT, 1);
        if (!_active.anyTrue())
          break;

        _val = _val.mul(3, _active).add(1, _active);
        _val = _val.lanewise(LSHR, 1, _active);
        _orbit = _orbit.add(2, _active);
      }

      var _less = _max.compare(LT, _orbit);

      _max = _max.blend(_orbit, _less);
      _start = _start.blend(_pos, _less);
    }

    LongVector _zero = LongVector.zero(SP);
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
