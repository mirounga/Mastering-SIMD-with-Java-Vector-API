package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

import java.util.Arrays;

public class SingleNumberPairs2Vectorized {
  static final VectorSpecies<Integer> SP =
      IntVector.SPECIES_PREFERRED;

  public static int[] find(int[] input) {
    IntVector _ones = IntVector.zero(SP);

    for (int i = 0; i < input.length; i += SP.length()) {
      var _mask = SP.indexInRange(i, input.length);

      IntVector _num = IntVector.fromArray(SP, input, i, _mask);

      _ones = _ones.lanewise(XOR, _num);
    }

    int result = _ones.reduceLanes(XOR);

    int set = result & (-result);

    _ones = IntVector.zero(SP);
    IntVector _zeroes = IntVector.zero(SP);

    for (int i = 0; i < input.length; i += SP.length()) {
      var _mask = SP.indexInRange(i, input.length);

      IntVector _num = IntVector.fromArray(SP, input, i, _mask);

      VectorMask<Integer> _set = _num.and(set).eq(set);

      _ones = _ones.lanewise(XOR, _num, _set);
      var _notSet = _set.not();
      _zeroes = _zeroes.lanewise(XOR, _num, _notSet);
    }

    int[] uniques = new int[2];

    uniques[0] = _zeroes.reduceLanes(XOR);
    uniques[1] = _ones.reduceLanes(XOR);

    Arrays.sort(uniques);

    return uniques;
  }
}
