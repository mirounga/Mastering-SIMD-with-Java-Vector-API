package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class RemoveDuplicatesLoad {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;
  static final int vl = SP.length();

  public static int compress(int[] array) {
    int arraySize = array.length;

    int writeIndex = 1;

    if (arraySize > 0) {
      for (int i = 1; i < arraySize; i += vl) {
        var _mask = SP.indexInRange(i, arraySize);
        var _shifted = IntVector.fromArray(SP, array, i - 1, _mask);
        var _current = IntVector.fromArray(SP, array, i, _mask);

        var _neq = _current.compare(NE, _shifted).and(_mask);

        var _write = _neq.compress();

        _current.compress(_neq).intoArray(array, writeIndex, _write);

        writeIndex += _neq.trueCount();
      }
    }

    return writeIndex;
  }
}