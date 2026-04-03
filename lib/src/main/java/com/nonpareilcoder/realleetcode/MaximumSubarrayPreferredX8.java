package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;
import static jdk.incubator.vector.VectorOperators.*;

public class MaximumSubarrayPreferredX8 {
  static final VectorSpecies<Integer> SP =
      IntVector.SPECIES_PREFERRED;

  static public int compute(int[] input) {
    final int vl = SP.length();
    final int arraySize = input.length;

    final int longIterations = arraySize / (8 * vl);
    final int longRemainder = arraySize % (8 * vl);

    final var _zero = IntVector.zero(SP);
    final var _top = IntVector.broadcast(SP, Integer.MAX_VALUE);

    final var _uzp0 = VectorShuffle.makeUnzip(SP, 0);
    final var _uzp1 = VectorShuffle.makeUnzip(SP, 1);

    var _prev_sum = _zero;
    var _prev_min = _top;
    var _max = _zero;

    int pos = 0;
    for (int i = 0; i < longIterations; i++) {
      var _sum0 = IntVector.fromArray(SP, input, pos + 0 * vl);
      var _sum1 = IntVector.fromArray(SP, input, pos + 1 * vl);
      var _sum2 = IntVector.fromArray(SP, input, pos + 2 * vl);
      var _sum3 = IntVector.fromArray(SP, input, pos + 3 * vl);
      var _sum4 = IntVector.fromArray(SP, input, pos + 4 * vl);
      var _sum5 = IntVector.fromArray(SP, input, pos + 5 * vl);
      var _sum6 = IntVector.fromArray(SP, input, pos + 6 * vl);
      var _sum7 = IntVector.fromArray(SP, input, pos + 7 * vl);

      var _t0 = _sum0;
      _sum0 = _sum0.rearrange(_uzp0, _sum1);
      _sum1 = _t0.rearrange(_uzp1, _sum1);
      var _t1 = _sum2;
      _sum2 = _sum2.rearrange(_uzp0, _sum3);
      _sum3 = _t1.rearrange(_uzp1, _sum3);
      var _t2 = _sum4;
      _sum4 = _sum4.rearrange(_uzp0, _sum5);
      _sum5 = _t2.rearrange(_uzp1, _sum5);
      var _t3 = _sum6;
      _sum6 = _sum6.rearrange(_uzp0, _sum7);
      _sum7 = _t3.rearrange(_uzp1, _sum7);

      _t0 = _sum0;
      _sum0 = _sum0.rearrange(_uzp0, _sum2);
      _t1 = _sum1;
      _sum1 = _sum1.rearrange(_uzp0, _sum3);
      _sum2 = _t0.rearrange(_uzp1, _sum2);
      _sum3 = _t1.rearrange(_uzp1, _sum3);
      _t2 = _sum4;
      _sum4 = _sum4.rearrange(_uzp0, _sum6);
      _t3 = _sum5;
      _sum5 = _sum5.rearrange(_uzp0, _sum7);
      _sum6 = _t2.rearrange(_uzp1, _sum6);
      _sum7 = _t3.rearrange(_uzp1, _sum7);

      _t0 = _sum0;
      _sum0 = _sum0.rearrange(_uzp0, _sum4);
      _t1 = _sum1;
      _sum1 = _sum1.rearrange(_uzp0, _sum5);
      _t2 = _sum2;
      _sum2 = _sum2.rearrange(_uzp0, _sum6);
      _t3 = _sum3;
      _sum3 = _sum3.rearrange(_uzp0, _sum7);
      _sum4 = _t0.rearrange(_uzp1, _sum4);
      _sum5 = _t1.rearrange(_uzp1, _sum5);
      _sum6 = _t2.rearrange(_uzp1, _sum6);
      _sum7 = _t3.rearrange(_uzp1, _sum7);


      _sum1 = _sum1.add(_sum0);
      _sum3 = _sum3.add(_sum2);
      _sum5 = _sum5.add(_sum4);
      _sum7 = _sum7.add(_sum6);

      _sum3 = _sum3.add(_sum1);
      _sum7 = _sum7.add(_sum5);

      _sum7 = _sum7.add(_sum3);

      var _sumZ = _prev_sum.slice(vl - 1, _sum7);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _zero.slice(vl - step, _sumZ);
        _sumZ = _sumZ.add(_shifted);
      }

      _sum0 = _sum0.add(_sumZ);
      _sum1 = _sum1.add(_sumZ);
      _sum3 = _sum3.add(_sumZ);
      _sum7 = _sum7.add(_sumZ);

      _sum5 = _sum5.add(_sum3);

      _sum2 = _sum2.add(_sum1);
      _sum4 = _sum4.add(_sum3);
      _sum6 = _sum6.add(_sum5);


      var _min7 = _sum6;
      var _min6 = _sum5;
      var _min5 = _sum4;
      var _min4 = _sum3;
      var _min3 = _sum2;
      var _min2 = _sum1;
      var _min1 = _sum0;
      var _min0 = _prev_sum.slice(vl - 1, _sum7);

      _prev_sum = _sum7;


      _min1 = _min1.min(_min0);
      _min3 = _min3.min(_min2);
      _min5 = _min5.min(_min4);
      _min7 = _min7.min(_min6);

      _min3 = _min3.min(_min1);
      _min7 = _min7.min(_min5);

      _min7 = _min7.min(_min3);

      var _minZ = _prev_min.slice(vl - 1, _min7);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _top.slice(vl - step, _minZ);
        _minZ = _minZ.min(_shifted);
      }

      _min0 = _min0.min(_minZ);
      _min1 = _min1.min(_minZ);
      _min3 = _min3.min(_minZ);
      _min7 = _min7.min(_minZ);

      _min5 = _min5.min(_min3);

      _min2 = _min2.min(_min1);
      _min4 = _min4.min(_min3);
      _min6 = _min6.min(_min5);

      _prev_min = _min7;

      _max = _sum0.sub(_min0).max(_max);
      _max = _sum1.sub(_min1).max(_max);
      _max = _sum2.sub(_min2).max(_max);
      _max = _sum3.sub(_min3).max(_max);
      _max = _sum4.sub(_min4).max(_max);
      _max = _sum5.sub(_min5).max(_max);
      _max = _sum6.sub(_min6).max(_max);
      _max = _sum7.sub(_min7).max(_max);

      pos += 8 * vl;
    }

    int prefix_sum = _prev_sum.lane(vl - 1);
    int prefix_min = _prev_min.lane(vl - 1);
    int max = _max.reduceLanes(MAX);

    for (int i = 0; i < longRemainder; i++) {
      prefix_min = Math.min(prefix_min, prefix_sum);
      prefix_sum += input[pos++];
      max = Math.max(max, prefix_sum - prefix_min);
    }

    return max;
  }
}
