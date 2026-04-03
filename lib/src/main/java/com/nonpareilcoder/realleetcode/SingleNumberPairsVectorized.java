package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class SingleNumberPairsVectorized {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;

  public static int find(int[] input) {
    IntVector _ones = IntVector.zero(SP);

    for (int i = 0; i < input.length; i += SP.length()) {
      var _mask = SP.indexInRange(i, input.length);

      IntVector _num = IntVector.fromArray(SP, input, i, _mask);

      _ones = _ones.lanewise(XOR, _num);
    }

    int result = _ones.reduceLanes(XOR);

    return result;
  }
}
