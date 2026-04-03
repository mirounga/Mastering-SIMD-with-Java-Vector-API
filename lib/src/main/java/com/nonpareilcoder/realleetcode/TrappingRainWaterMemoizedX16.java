package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

import static jdk.incubator.vector.VectorOperators.*;

public class TrappingRainWaterMemoizedX16 {
  public static int trap(int[] input) {
    int[] prefixMax = new int[input.length];
    int[] pivotedInput = new int[input.length];

    computePrefixMax(input, pivotedInput, prefixMax);
    return computeSuffixMaxFused(pivotedInput, prefixMax);
  }

  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;
  static final int vl = SP.length();

  static final IntVector _zero = IntVector.broadcast(SP, 0);

  static final VectorShuffle<Integer> _uzp0 =
    VectorShuffle.makeUnzip(SP, 0);
  static final VectorShuffle<Integer> _uzp1 =
    VectorShuffle.makeUnzip(SP, 1);

  static void computePrefixMax(
    int[] input, int[] pivotedInput, int[] prefixMax) {
    int iterations = input.length / (16 * vl);
    int remainder = input.length % (16 * vl);

    var _prev = _zero;

    int pos = 0;
    for (int i = 0; i < iterations; i++) {
      var _part0 = IntVector.fromArray(SP, input, pos);
      var _part1 = IntVector.fromArray(SP, input, pos + vl);
      var _part2 = IntVector.fromArray(SP, input, pos + 2 * vl);
      var _part3 = IntVector.fromArray(SP, input, pos + 3 * vl);
      var _part4 = IntVector.fromArray(SP, input, pos + 4 * vl);
      var _part5 = IntVector.fromArray(SP, input, pos + 5 * vl);
      var _part6 = IntVector.fromArray(SP, input, pos + 6 * vl);
      var _part7 = IntVector.fromArray(SP, input, pos + 7 * vl);
      var _part8 = IntVector.fromArray(SP, input, pos + 8 * vl);
      var _part9 = IntVector.fromArray(SP, input, pos + 9 * vl);
      var _partA = IntVector.fromArray(SP, input, pos + 10 * vl);
      var _partB = IntVector.fromArray(SP, input, pos + 11 * vl);
      var _partC = IntVector.fromArray(SP, input, pos + 12 * vl);
      var _partD = IntVector.fromArray(SP, input, pos + 13 * vl);
      var _partE = IntVector.fromArray(SP, input, pos + 14 * vl);
      var _partF = IntVector.fromArray(SP, input, pos + 15 * vl);

      var _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part1);
      _part1 = _t0.rearrange(_uzp1, _part1);
      var _t1 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part3);
      _part3 = _t1.rearrange(_uzp1, _part3);
      var _t2 = _part4;
      _part4 = _part4.rearrange(_uzp0, _part5);
      _part5 = _t2.rearrange(_uzp1, _part5);
      var _t3 = _part6;
      _part6 = _part6.rearrange(_uzp0, _part7);
      _part7 = _t3.rearrange(_uzp1, _part7);
      var _t4 = _part8;
      _part8 = _part8.rearrange(_uzp0, _part9);
      _part9 = _t4.rearrange(_uzp1, _part9);
      var _t5 = _partA;
      _partA = _partA.rearrange(_uzp0, _partB);
      _partB = _t5.rearrange(_uzp1, _partB);
      var _t6 = _partC;
      _partC = _partC.rearrange(_uzp0, _partD);
      _partD = _t6.rearrange(_uzp1, _partD);
      var _t7 = _partE;
      _partE = _partE.rearrange(_uzp0, _partF);
      _partF = _t7.rearrange(_uzp1, _partF);

      _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_uzp0, _part3);
      _part2 = _t0.rearrange(_uzp1, _part2);
      _part3 = _t1.rearrange(_uzp1, _part3);
      _t2 = _part4;
      _part4 = _part4.rearrange(_uzp0, _part6);
      _t3 = _part5;
      _part5 = _part5.rearrange(_uzp0, _part7);
      _part6 = _t2.rearrange(_uzp1, _part6);
      _part7 = _t3.rearrange(_uzp1, _part7);
      _t4 = _part8;
      _part8 = _part8.rearrange(_uzp0, _partA);
      _t5 = _part9;
      _part9 = _part9.rearrange(_uzp0, _partB);
      _partA = _t4.rearrange(_uzp1, _partA);
      _partB = _t5.rearrange(_uzp1, _partB);
      _t6 = _partC;
      _partC = _partC.rearrange(_uzp0, _partE);
      _t7 = _partD;
      _partD = _partD.rearrange(_uzp0, _partF);
      _partE = _t6.rearrange(_uzp1, _partE);
      _partF = _t7.rearrange(_uzp1, _partF);

      _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part4);
      _t1 = _part1;
      _part1 = _part1.rearrange(_uzp0, _part5);
      _t2 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part6);
      _t3 = _part3;
      _part3 = _part3.rearrange(_uzp0, _part7);
      _part4 = _t0.rearrange(_uzp1, _part4);
      _part5 = _t1.rearrange(_uzp1, _part5);
      _part6 = _t2.rearrange(_uzp1, _part6);
      _part7 = _t3.rearrange(_uzp1, _part7);
      _t4 = _part8;
      _part8 = _part8.rearrange(_uzp0, _partC);
      _t5 = _part9;
      _part9 = _part9.rearrange(_uzp0, _partD);
      _t6 = _partA;
      _partA = _partA.rearrange(_uzp0, _partE);
      _t7 = _partB;
      _partB = _partB.rearrange(_uzp0, _partF);
      _partC = _t4.rearrange(_uzp1, _partC);
      _partD = _t5.rearrange(_uzp1, _partD);
      _partE = _t6.rearrange(_uzp1, _partE);
      _partF = _t7.rearrange(_uzp1, _partF);

      _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part8);
      _t1 = _part1;
      _part1 = _part1.rearrange(_uzp0, _part9);
      _t2 = _part2;
      _part2 = _part2.rearrange(_uzp0, _partA);
      _t3 = _part3;
      _part3 = _part3.rearrange(_uzp0, _partB);
      _t4 = _part4;
      _part4 = _part4.rearrange(_uzp0, _partC);
      _t5 = _part5;
      _part5 = _part5.rearrange(_uzp0, _partD);
      _t6 = _part6;
      _part6 = _part6.rearrange(_uzp0, _partE);
      _t7 = _part7;
      _part7 = _part7.rearrange(_uzp0, _partF);
      _part8 = _t0.rearrange(_uzp1, _part8);
      _part9 = _t1.rearrange(_uzp1, _part9);
      _partA = _t2.rearrange(_uzp1, _partA);
      _partB = _t3.rearrange(_uzp1, _partB);
      _partC = _t4.rearrange(_uzp1, _partC);
      _partD = _t5.rearrange(_uzp1, _partD);
      _partE = _t6.rearrange(_uzp1, _partE);
      _partF = _t7.rearrange(_uzp1, _partF);

      _part0.intoArray(pivotedInput, pos);
      _part1.intoArray(pivotedInput, pos + vl);
      _part2.intoArray(pivotedInput, pos + 2 * vl);
      _part3.intoArray(pivotedInput, pos + 3 * vl);
      _part4.intoArray(pivotedInput, pos + 4 * vl);
      _part5.intoArray(pivotedInput, pos + 5 * vl);
      _part6.intoArray(pivotedInput, pos + 6 * vl);
      _part7.intoArray(pivotedInput, pos + 7 * vl);
      _part8.intoArray(pivotedInput, pos + 8 * vl);
      _part9.intoArray(pivotedInput, pos + 9 * vl);
      _partA.intoArray(pivotedInput, pos + 10 * vl);
      _partB.intoArray(pivotedInput, pos + 11 * vl);
      _partC.intoArray(pivotedInput, pos + 12 * vl);
      _partD.intoArray(pivotedInput, pos + 13 * vl);
      _partE.intoArray(pivotedInput, pos + 14 * vl);
      _partF.intoArray(pivotedInput, pos + 15 * vl);

      _part1 = _part1.max(_part0);
      _part3 = _part3.max(_part2);
      _part5 = _part5.max(_part4);
      _part7 = _part7.max(_part6);
      _part9 = _part9.max(_part8);
      _partB = _partB.max(_partA);
      _partD = _partD.max(_partC);
      _partF = _partF.max(_partE);

      _part3 = _part3.max(_part1);
      _part7 = _part7.max(_part5);
      _partB = _partB.max(_part9);
      _partF = _partF.max(_partD);

      _part7 = _part7.max(_part3);
      _partF = _partF.max(_partB);

      _partF = _partF.max(_part7);

      var _partZ = _prev.slice(vl - 1, _partF);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _zero.slice(vl - step, _partZ);
        _partZ = _partZ.max(_shifted);
      }

      _part0 = _part0.max(_partZ);
      _part1 = _part1.max(_partZ);
      _part3 = _part3.max(_partZ);
      _part7 = _part7.max(_partZ);
      _partF = _partF.max(_partZ);

      _partB = _partB.max(_part7);

      _part5 = _part5.max(_part3);
      _part9 = _part9.max(_part7);
      _partD = _partD.max(_partB);

      _part2 = _part2.max(_part1);
      _part4 = _part4.max(_part3);
      _part6 = _part6.max(_part5);
      _part8 = _part8.max(_part7);
      _partA = _partA.max(_part9);
      _partC = _partC.max(_partB);
      _partE = _partE.max(_partD);

      _prev = _partF;

      _part0.intoArray(prefixMax, pos);
      _part1.intoArray(prefixMax, pos + vl);
      _part2.intoArray(prefixMax, pos + 2 * vl);
      _part3.intoArray(prefixMax, pos + 3 * vl);
      _part4.intoArray(prefixMax, pos + 4 * vl);
      _part5.intoArray(prefixMax, pos + 5 * vl);
      _part6.intoArray(prefixMax, pos + 6 * vl);
      _part7.intoArray(prefixMax, pos + 7 * vl);
      _part8.intoArray(prefixMax, pos + 8 * vl);
      _part9.intoArray(prefixMax, pos + 9 * vl);
      _partA.intoArray(prefixMax, pos + 10 * vl);
      _partB.intoArray(prefixMax, pos + 11 * vl);
      _partC.intoArray(prefixMax, pos + 12 * vl);
      _partD.intoArray(prefixMax, pos + 13 * vl);
      _partE.intoArray(prefixMax, pos + 14 * vl);
      _partF.intoArray(prefixMax, pos + 15 * vl);

      pos += 16 * vl;
    }

    int acc = _prev.lane(vl - 1);
    ;

    while (remainder-- > 0) {
      pivotedInput[pos] = input[pos];
      acc += input[pos];
      prefixMax[pos++] = acc;
    }
  }

  static int computeSuffixMaxFused(
    int[] pivotedInput, int[] prefixMax) {
    int iterations = pivotedInput.length / (16 * vl);
    int remainder = pivotedInput.length % (16 * vl);

    int trapped = 0, acc = 0;
    int pos = pivotedInput.length;
    while (remainder-- > 0) {
      acc = Math.max(acc, pivotedInput[--pos]);
      int minVal = Math.min(prefixMax[pos], acc);
      trapped += minVal - pivotedInput[pos];
    }

    var _trapped = IntVector.zero(SP);
    var _prev = IntVector.broadcast(SP, acc);

    for (int i = 0; i < iterations; i++) {
      pos -= 16 * vl;

      int[] pi = pivotedInput;
      var _part0 = IntVector.fromArray(SP, pi, pos);
      var _part1 = IntVector.fromArray(SP, pi, pos + vl);
      var _part2 = IntVector.fromArray(SP, pi, pos + 2 * vl);
      var _part3 = IntVector.fromArray(SP, pi, pos + 3 * vl);
      var _part4 = IntVector.fromArray(SP, pi, pos + 4 * vl);
      var _part5 = IntVector.fromArray(SP, pi, pos + 5 * vl);
      var _part6 = IntVector.fromArray(SP, pi, pos + 6 * vl);
      var _part7 = IntVector.fromArray(SP, pi, pos + 7 * vl);
      var _part8 = IntVector.fromArray(SP, pi, pos + 8 * vl);
      var _part9 = IntVector.fromArray(SP, pi, pos + 9 * vl);
      var _partA = IntVector.fromArray(SP, pi, pos + 10 * vl);
      var _partB = IntVector.fromArray(SP, pi, pos + 11 * vl);
      var _partC = IntVector.fromArray(SP, pi, pos + 12 * vl);
      var _partD = IntVector.fromArray(SP, pi, pos + 13 * vl);
      var _partE = IntVector.fromArray(SP, pi, pos + 14 * vl);
      var _partF = IntVector.fromArray(SP, pi, pos + 15 * vl);

      _partE = _partE.max(_partF);
      _partC = _partC.max(_partD);
      _partA = _partA.max(_partB);
      _part8 = _part8.max(_part9);
      _part6 = _part6.max(_part7);
      _part4 = _part4.max(_part5);
      _part2 = _part2.max(_part3);
      _part0 = _part0.max(_part1);

      _partC = _partC.max(_partE);
      _part8 = _part8.max(_partA);
      _part4 = _part4.max(_part6);
      _part0 = _part0.max(_part2);

      _part8 = _part8.max(_partC);
      _part0 = _part0.max(_part4);

      _part0 = _part0.max(_part8);

      var _partZ = _part0.slice(1, _prev);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _partZ.slice(step, _zero);
        _partZ = _partZ.max(_shifted);
      }

      _part0 = _part0.max(_partZ);
      _part8 = _part8.max(_partZ);
      _partC = _partC.max(_partZ);
      _partE = _partE.max(_partZ);
      _partF = _partF.max(_partZ);

      _part4 = _part4.max(_part8);

      _partA = _partA.max(_partC);
      _part6 = _part6.max(_part8);
      _part2 = _part2.max(_part4);

      _partD = _partD.max(_partE);
      _partB = _partB.max(_partC);
      _part9 = _part9.max(_partA);
      _part7 = _part7.max(_part8);
      _part5 = _part5.max(_part6);
      _part3 = _part3.max(_part4);
      _part1 = _part1.max(_part2);

      _prev = _part0;

      var _pf0 = IntVector.fromArray(SP, prefixMax, pos);
      var _pf1 = IntVector.fromArray(SP, prefixMax, pos + vl);
      var _pf2 = IntVector.fromArray(SP, prefixMax, pos + 2 * vl);
      var _pf3 = IntVector.fromArray(SP, prefixMax, pos + 3 * vl);
      var _pf4 = IntVector.fromArray(SP, prefixMax, pos + 4 * vl);
      var _pf5 = IntVector.fromArray(SP, prefixMax, pos + 5 * vl);
      var _pf6 = IntVector.fromArray(SP, prefixMax, pos + 6 * vl);
      var _pf7 = IntVector.fromArray(SP, prefixMax, pos + 7 * vl);
      var _pf8 = IntVector.fromArray(SP, prefixMax, pos + 8 * vl);
      var _pf9 = IntVector.fromArray(SP, prefixMax, pos + 9 * vl);
      var _pfA = IntVector.fromArray(SP, prefixMax, pos + 10 * vl);
      var _pfB = IntVector.fromArray(SP, prefixMax, pos + 11 * vl);
      var _pfC = IntVector.fromArray(SP, prefixMax, pos + 12 * vl);
      var _pfD = IntVector.fromArray(SP, prefixMax, pos + 13 * vl);
      var _pfE = IntVector.fromArray(SP, prefixMax, pos + 14 * vl);
      var _pfF = IntVector.fromArray(SP, prefixMax, pos + 15 * vl);

      var _pv0 = IntVector.fromArray(SP, pi, pos);
      var _pv1 = IntVector.fromArray(SP, pi, pos + vl);
      var _pv2 = IntVector.fromArray(SP, pi, pos + 2 * vl);
      var _pv3 = IntVector.fromArray(SP, pi, pos + 3 * vl);
      var _pv4 = IntVector.fromArray(SP, pi, pos + 4 * vl);
      var _pv5 = IntVector.fromArray(SP, pi, pos + 5 * vl);
      var _pv6 = IntVector.fromArray(SP, pi, pos + 6 * vl);
      var _pv7 = IntVector.fromArray(SP, pi, pos + 7 * vl);
      var _pv8 = IntVector.fromArray(SP, pi, pos + 8 * vl);
      var _pv9 = IntVector.fromArray(SP, pi, pos + 9 * vl);
      var _pvA = IntVector.fromArray(SP, pi, pos + 10 * vl);
      var _pvB = IntVector.fromArray(SP, pi, pos + 11 * vl);
      var _pvC = IntVector.fromArray(SP, pi, pos + 12 * vl);
      var _pvD = IntVector.fromArray(SP, pi, pos + 13 * vl);
      var _pvE = IntVector.fromArray(SP, pi, pos + 14 * vl);
      var _pvF = IntVector.fromArray(SP, pi, pos + 15 * vl);

      _part0 = _part0.min(_pf0).sub(_pv0);
      _part1 = _part1.min(_pf1).sub(_pv1);
      _part2 = _part2.min(_pf2).sub(_pv2);
      _part3 = _part3.min(_pf3).sub(_pv3);
      _part4 = _part4.min(_pf4).sub(_pv4);
      _part5 = _part5.min(_pf5).sub(_pv5);
      _part6 = _part6.min(_pf6).sub(_pv6);
      _part7 = _part7.min(_pf7).sub(_pv7);
      _part8 = _part8.min(_pf8).sub(_pv8);
      _part9 = _part9.min(_pf9).sub(_pv9);
      _partA = _partA.min(_pfA).sub(_pvA);
      _partB = _partB.min(_pfB).sub(_pvB);
      _partC = _partC.min(_pfC).sub(_pvC);
      _partD = _partD.min(_pfD).sub(_pvD);
      _partE = _partE.min(_pfE).sub(_pvE);
      _partF = _partF.min(_pfF).sub(_pvF);

      _part0 = _part0.add(_part1);
      _part2 = _part2.add(_part3);
      _part4 = _part4.add(_part5);
      _part6 = _part6.add(_part7);
      _part8 = _part8.add(_part9);
      _partA = _partA.add(_partB);
      _partC = _partC.add(_partD);
      _partE = _partE.add(_partF);

      _part0 = _part0.add(_part2);
      _part4 = _part4.add(_part6);
      _part8 = _part8.add(_partA);
      _partC = _partC.add(_partE);

      _part0 = _part0.add(_part4);
      _part8 = _part8.add(_partC);

      _trapped = _trapped.add(_part0);
      _trapped = _trapped.add(_part8);
    }

    trapped += _trapped.reduceLanes(ADD);

    return trapped;
  }
}
