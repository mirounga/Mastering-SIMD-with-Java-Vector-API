package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

public class AdditiveScanHorizontal {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;
  static final int vl = SP.length();

  public static void prefixAdd(int[] input, int[] output) {
    int arraySize = input.length;
    if (arraySize == 0)
      return;

    final IntVector _zero = IntVector.broadcast(SP, 0);

    int nIterations = arraySize / vl;
    int nRemainder = arraySize % vl;

    int acc = 0;

    int pos = 0;
    for (int i = 0; i < nIterations; i++) {
      var _current = IntVector.fromArray(SP, input, pos);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _zero.slice(vl - step, _current);
        _current = _current.add(_shifted);
      }

      _current = _current.add(acc);

      acc = _current.lane(vl - 1);

      _current.intoArray(output, pos);

      pos += vl;
    }

    while (nRemainder-- > 0) {
      acc += input[pos];
      output[pos++] = acc;
    }
  }

  public static void suffixAdd(int[] input, int[] output) {
    int arraySize = input.length;
    if (arraySize == 0)
      return;

    final IntVector _zero = IntVector.broadcast(SP, 0);

    int nIterations = arraySize / vl;
    int nRemainder = arraySize % vl;

    int acc = 0;

    int pos = input.length;
    while (nRemainder-- > 0) {
      acc += input[--pos];
      output[pos] = acc;
    }

    for (int i = 0; i < nIterations; i++) {
      pos -= vl;

      var _current = IntVector.fromArray(SP, input, pos);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _current.slice(step, _zero);
        _current = _current.add(_shifted);
      }

      _current = _current.add(acc);

      acc = _current.lane(0);

      _current.intoArray(output, pos);
    }
  }
}
