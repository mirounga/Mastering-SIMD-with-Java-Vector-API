package com.nonpareilcoder.floatingpoint;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class RandomGeneratorVectorized {
  static final VectorSpecies<Long> SL =
    LongVector.SPECIES_PREFERRED;
  static final VectorSpecies<Float> SF =
    FloatVector.SPECIES_PREFERRED;
  static final VectorSpecies<Integer> SI =
    IntVector.SPECIES_PREFERRED;
  static final int vl = SL.length();

  static final long a = RandomGeneratorScalar.a;
  static final long b = RandomGeneratorScalar.b;
  static final long mask = RandomGeneratorScalar.mask;

  private static final long[] multiplier0;
  private static final long[] multiplier1;
  private static final long[] addend0;
  private static final long[] addend1;

  static {
    multiplier0 = new long[vl];
    multiplier1 = new long[vl];
    addend0 = new long[vl];
    addend1 = new long[vl];

    final var _zero = LongVector.zero(SL);
    final var _one = LongVector.broadcast(SL, 1L);

    var _multiplier1 = LongVector.broadcast(SL, a);

    _multiplier1 = _multiplier1.mul(_multiplier1).and(mask);

    for (int step = 1; step < vl; step <<= 1) {
      var _shifted = _one.slice(vl - step, _multiplier1);
      _multiplier1 = _multiplier1.mul(_shifted).and(mask);
    }

    var _multiplier0 = _one.slice(vl - 1, _multiplier1);
    _multiplier0 = _multiplier0.mul(a).and(mask);

    _multiplier0.intoArray(multiplier0, 0);
    _multiplier1.intoArray(multiplier1, 0);

    var _addend0 = _multiplier0;
    var _addend1 = _multiplier1;

    for (int step = 1; step < vl; step <<= 1) {
      var _shifted0 = _zero.slice(vl - step, _addend0);
      var _shifted1 = _zero.slice(vl - step, _addend1);
      _addend0 = _addend0.add(_shifted0).and(mask);
      _addend1 = _addend1.add(_shifted1).and(mask);
    }

    var _t = _addend0.add(_addend1).and(mask);
    _addend1 = _zero.slice(vl - 1, _addend1).add(_addend0).and(mask);
    _addend0 = _zero.slice(vl - 1, _t).and(mask);

    _addend0 = _addend0.add(1).mul(b).and(mask);
    _addend1 = _addend1.add(1).mul(b).and(mask);

    _addend0.intoArray(addend0, 0);
    _addend1.intoArray(addend1, 0);
  }

  static float scale = RandomGeneratorScalar.scale;

  static public float[] generateFloatArray(
    long seed, float arrayMax, int arraySize) {
    float[] result = new float[arraySize];

    long val = (seed ^ a) & mask;

    final var _multiplier0 = LongVector.fromArray(SL, multiplier0, 0);
    final var _multiplier1 = LongVector.fromArray(SL, multiplier1, 0);
    final var _addend0 = LongVector.fromArray(SL, addend0, 0);
    final var _addend1 = LongVector.fromArray(SL, addend1, 0);

    for (int i = 0; i < arraySize; i += vl * 2) {
      var _part0 = _multiplier0.mul(val).and(mask)
        .add(_addend0).and(mask);
      var _part1 = _multiplier1.mul(val).and(mask)
        .add(_addend1).and(mask);

      val = _part1.lane(vl - 1);

      _part0 = _part0.lanewise(LSHR, 24);
      _part1 = _part1.lanewise(LSHR, 24);

      _part1 = _part1.lanewise(LSHL, 32);
      _part0 = _part1.or(_part0);

      var _ival = _part0.reinterpretAsInts();

      var _fval = (FloatVector) _ival.convert(I2F, 0);

      _fval = _fval.div(scale).mul(arrayMax);

      var _store = SF.indexInRange(i, arraySize);

      _fval.intoArray(result, i, _store);
    }

    return result;
  }

  static public int[] generateIntArray(
    long seed, int arrayMax, int arraySize) {
    int[] result = new int[arraySize];

    final long c = Long.divideUnsigned(-1L, arrayMax) + 1;

    long val = (seed ^ a) & mask;

    final var _multiplier0 = LongVector.fromArray(SL, multiplier0, 0);
    final var _multiplier1 = LongVector.fromArray(SL, multiplier1, 0);
    final var _addend0 = LongVector.fromArray(SL, addend0, 0);
    final var _addend1 = LongVector.fromArray(SL, addend1, 0);

    for (int i = 0; i < arraySize; ) {
      var _part0 = _multiplier0.mul(val).and(mask)
        .add(_addend0).and(mask);
      var _part1 = _multiplier1.mul(val).and(mask)
        .add(_addend1).and(mask);

      val = _part1.lane(vl - 1);

      _part0 = _part0.lanewise(LSHR, 17);
      _part1 = _part1.lanewise(LSHR, 17);

      var _low0 = _part0.mul(c);
      var _low1 = _part1.mul(c);

      var _lo0 = _low0.and(0xFFFFFFFFL);
      var _lo1 = _low1.and(0xFFFFFFFFL);
      var _hi0 = _low0.lanewise(LSHR, 32);
      var _hi1 = _low1.lanewise(LSHR, 32);
      _lo0 = _lo0.mul(arrayMax).lanewise(LSHR, 32);
      _lo1 = _lo1.mul(arrayMax).lanewise(LSHR, 32);
      _hi0 = _hi0.mul(arrayMax);
      _hi1 = _hi1.mul(arrayMax);

      var _rem0 = _hi0.add(_lo0).lanewise(LSHR, 32);
      var _rem1 = _hi1.add(_lo1).lanewise(LSHR, 32);

      _part1 = _part1.lanewise(LSHL, 32);
      _part0 = _part1.or(_part0);

      _rem1 = _rem1.lanewise(LSHL, 32);
      _rem0 = _rem1.or(_rem0);

      var _ival = _part0.reinterpretAsInts();
      var _irem = _rem0.reinterpretAsInts();

      var _keep = _ival.sub(_irem)
        .add(arrayMax)
        .compare(GE, 1);

      var _store = SI
        .indexInRange(i, arraySize).and(_keep);

      _irem.compress(_keep).intoArray(result, i, _store);

      i += _store.trueCount();
    }

    return result;
  }
}
