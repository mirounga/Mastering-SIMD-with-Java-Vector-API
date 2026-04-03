package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class SingleNumberTriplesVectorized {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;

  public static int find(int[] input) {
    IntVector _zero = IntVector.zero(SP);
    IntVector _ones = IntVector.zero(SP);
    IntVector _twos = IntVector.zero(SP);
    IntVector _threes;

    for (int i = 0; i < input.length; i += SP.length()) {
      var _mask = SP.indexInRange(i, input.length);

      IntVector _num = IntVector.fromArray(SP, input, i, _mask);

      _twos = _ones.and(_num).or(_twos);
      _ones = _ones.lanewise(XOR, _num);
      _threes = _ones.and(_twos);
      _ones = _ones.lanewise(AND_NOT, _threes);
      _twos = _twos.lanewise(AND_NOT, _threes);
    }

    for (int step = 1; step < SP.length(); step <<= 1) {
      IntVector _os = _ones.slice(step, _zero);
      IntVector _ts = _twos.slice(step, _zero);

      _os = _os.or(_ts);

      _twos = _ones.and(_os).or(_twos);
      _ones = _ones.lanewise(XOR, _os);
      _threes = _ones.and(_twos);
      _ones = _ones.lanewise(AND_NOT, _threes);
      _twos = _twos.lanewise(AND_NOT, _threes);

      _twos = _ones.and(_ts).or(_twos);
      _ones = _ones.lanewise(XOR, _ts);
      _threes = _ones.and(_twos);
      _ones = _ones.lanewise(AND_NOT, _threes);
      _twos = _twos.lanewise(AND_NOT, _threes);
    }

    int result = _ones.lane(0);

    return result;
  }
}
