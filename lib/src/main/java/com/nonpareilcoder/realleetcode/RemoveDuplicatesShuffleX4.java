package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class RemoveDuplicatesShuffleX4 {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;
  static final int vl = SP.length();

  public static int compress(int[] array) {
    int arraySize = array.length;

    int writeIndex = 1;

    if (arraySize > 0) {
      var _prev = IntVector.broadcast(SP, array[0]);

      for (int i = 1; i < arraySize; ) {
        var _mask0 = SP.indexInRange(i, arraySize);
        var _current0 = IntVector.fromArray(SP, array, i, _mask0);
        var _shifted0 = _prev.slice(vl - 1, _current0);
        i += vl;

        var _mask1 = SP.indexInRange(i, arraySize);
        var _current1 = IntVector.fromArray(SP, array, i, _mask1);
        var _shifted1 = _current0.slice(vl - 1, _current1);
        i += vl;

        var _mask2 = SP.indexInRange(i, arraySize);
        var _current2 = IntVector.fromArray(SP, array, i, _mask2);
        var _shifted2 = _current1.slice(vl - 1, _current2);
        i += vl;

        var _mask3 = SP.indexInRange(i, arraySize);
        var _current3 = IntVector.fromArray(SP, array, i, _mask3);
        var _shifted3 = _current2.slice(vl - 1, _current3);
        i += vl;

        _prev = _current3;

        var _neq0 = _current0.compare(NE, _shifted0, _mask0);
        var _write0 = _neq0.compress();
        _current0.compress(_neq0).intoArray(array, writeIndex, _write0);
        writeIndex += _neq0.trueCount();

        var _neq1 = _current1.compare(NE, _shifted1, _mask1);
        var _write1 = _neq1.compress();
        _current1.compress(_neq1).intoArray(array, writeIndex, _write1);
        writeIndex += _neq1.trueCount();

        var _neq2 = _current2.compare(NE, _shifted2, _mask2);
        var _write2 = _neq2.compress();
        _current2.compress(_neq2).intoArray(array, writeIndex, _write2);
        writeIndex += _neq2.trueCount();

        var _neq3 = _current3.compare(NE, _shifted3, _mask3);
        var _write3 = _neq3.compress();
        _current3.compress(_neq3).intoArray(array, writeIndex, _write3);
        writeIndex += _neq3.trueCount();
      }
    }

    return writeIndex;
  }
}