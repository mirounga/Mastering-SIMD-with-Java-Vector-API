package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

public class TrappingRainWaterTransposedX16 {
  public static int trap(int[] input) {
    int[] prefixMax = new int[input.length];
    int[] suffixMax = new int[input.length];

    computePrefixMax(input, prefixMax);
    computeSuffixMax(input, suffixMax);

    return TrappingRainWaterHorizontal
      .reduceHeights(input, prefixMax, suffixMax);
  }

  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;

  static final int vl = SP.length();

  static final IntVector _zero = IntVector.broadcast(SP, 0);

  static final VectorShuffle<Integer> _zip0 =
    VectorShuffle.makeZip(SP, 0);
  static final VectorShuffle<Integer> _zip1 =
    VectorShuffle.makeZip(SP, 1);
  static final VectorShuffle<Integer> _uzp0 =
    VectorShuffle.makeUnzip(SP, 0);
  static final VectorShuffle<Integer> _uzp1 =
    VectorShuffle.makeUnzip(SP, 1);

  static void computePrefixMax(int[] input, int[] prefixMax) {
    int iterations = input.length / (16 * vl);
    int remainder = input.length % (16 * vl);

    IntVector _prev = _zero;

    int pos = 0;
    for (int i = 0; i < iterations; i++) {
      IntVector _part0 = IntVector.fromArray(SP, input, pos);
      IntVector _part1 = IntVector.fromArray(SP, input, pos + vl);
      IntVector _part2 =
        IntVector.fromArray(SP, input, pos + 2 * vl);
      IntVector _part3 =
        IntVector.fromArray(SP, input, pos + 3 * vl);
      IntVector _part4 =
        IntVector.fromArray(SP, input, pos + 4 * vl);
      IntVector _part5 =
        IntVector.fromArray(SP, input, pos + 5 * vl);
      IntVector _part6 =
        IntVector.fromArray(SP, input, pos + 6 * vl);
      IntVector _part7 =
        IntVector.fromArray(SP, input, pos + 7 * vl);
      IntVector _part8 =
        IntVector.fromArray(SP, input, pos + 8 * vl);
      IntVector _part9 =
        IntVector.fromArray(SP, input, pos + 9 * vl);
      IntVector _partA =
        IntVector.fromArray(SP, input, pos + 10 * vl);
      IntVector _partB =
        IntVector.fromArray(SP, input, pos + 11 * vl);
      IntVector _partC =
        IntVector.fromArray(SP, input, pos + 12 * vl);
      IntVector _partD =
        IntVector.fromArray(SP, input, pos + 13 * vl);
      IntVector _partE =
        IntVector.fromArray(SP, input, pos + 14 * vl);
      IntVector _partF =
        IntVector.fromArray(SP, input, pos + 15 * vl);

      IntVector _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part1);
      _part1 = _t0.rearrange(_uzp1, _part1);
      IntVector _t1 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part3);
      _part3 = _t1.rearrange(_uzp1, _part3);
      IntVector _t2 = _part4;
      _part4 = _part4.rearrange(_uzp0, _part5);
      _part5 = _t2.rearrange(_uzp1, _part5);
      IntVector _t3 = _part6;
      _part6 = _part6.rearrange(_uzp0, _part7);
      _part7 = _t3.rearrange(_uzp1, _part7);
      IntVector _t4 = _part8;
      _part8 = _part8.rearrange(_uzp0, _part9);
      _part9 = _t4.rearrange(_uzp1, _part9);
      IntVector _t5 = _partA;
      _partA = _partA.rearrange(_uzp0, _partB);
      _partB = _t5.rearrange(_uzp1, _partB);
      IntVector _t6 = _partC;
      _partC = _partC.rearrange(_uzp0, _partD);
      _partD = _t6.rearrange(_uzp1, _partD);
      IntVector _t7 = _partE;
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

      IntVector _partZ = _prev.slice(vl - 1, _partF);

      for (int step = 1; step < vl; step <<= 1) {
        IntVector _shifted = _zero.slice(vl - step, _partZ);
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

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part8);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part9);
      _t2 = _part2;
      _part2 = _part2.rearrange(_zip0, _partA);
      _t3 = _part3;
      _part3 = _part3.rearrange(_zip0, _partB);
      _t4 = _part4;
      _part4 = _part4.rearrange(_zip0, _partC);
      _t5 = _part5;
      _part5 = _part5.rearrange(_zip0, _partD);
      _t6 = _part6;
      _part6 = _part6.rearrange(_zip0, _partE);
      _t7 = _part7;
      _part7 = _part7.rearrange(_zip0, _partF);
      _part8 = _t0.rearrange(_zip1, _part8);
      _part9 = _t1.rearrange(_zip1, _part9);
      _partA = _t2.rearrange(_zip1, _partA);
      _partB = _t3.rearrange(_zip1, _partB);
      _partC = _t4.rearrange(_zip1, _partC);
      _partD = _t5.rearrange(_zip1, _partD);
      _partE = _t6.rearrange(_zip1, _partE);
      _partF = _t7.rearrange(_zip1, _partF);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part4);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part5);
      _t2 = _part2;
      _part2 = _part2.rearrange(_zip0, _part6);
      _t3 = _part3;
      _part3 = _part3.rearrange(_zip0, _part7);
      _part4 = _t0.rearrange(_zip1, _part4);
      _part5 = _t1.rearrange(_zip1, _part5);
      _part6 = _t2.rearrange(_zip1, _part6);
      _part7 = _t3.rearrange(_zip1, _part7);
      _t4 = _part8;
      _part8 = _part8.rearrange(_zip0, _partC);
      _t5 = _part9;
      _part9 = _part9.rearrange(_zip0, _partD);
      _t6 = _partA;
      _partA = _partA.rearrange(_zip0, _partE);
      _t7 = _partB;
      _partB = _partB.rearrange(_zip0, _partF);
      _partC = _t4.rearrange(_zip1, _partC);
      _partD = _t5.rearrange(_zip1, _partD);
      _partE = _t6.rearrange(_zip1, _partE);
      _partF = _t7.rearrange(_zip1, _partF);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part3);
      _part2 = _t0.rearrange(_zip1, _part2);
      _part3 = _t1.rearrange(_zip1, _part3);
      _t2 = _part4;
      _part4 = _part4.rearrange(_zip0, _part6);
      _t3 = _part5;
      _part5 = _part5.rearrange(_zip0, _part7);
      _part6 = _t2.rearrange(_zip1, _part6);
      _part7 = _t3.rearrange(_zip1, _part7);
      _t4 = _part8;
      _part8 = _part8.rearrange(_zip0, _partA);
      _t5 = _part9;
      _part9 = _part9.rearrange(_zip0, _partB);
      _partA = _t4.rearrange(_zip1, _partA);
      _partB = _t5.rearrange(_zip1, _partB);
      _t6 = _partC;
      _partC = _partC.rearrange(_zip0, _partE);
      _t7 = _partD;
      _partD = _partD.rearrange(_zip0, _partF);
      _partE = _t6.rearrange(_zip1, _partE);
      _partF = _t7.rearrange(_zip1, _partF);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part1);
      _part1 = _t0.rearrange(_zip1, _part1);
      _t1 = _part2;
      _part2 = _part2.rearrange(_zip0, _part3);
      _part3 = _t1.rearrange(_zip1, _part3);
      _t2 = _part4;
      _part4 = _part4.rearrange(_zip0, _part5);
      _part5 = _t2.rearrange(_zip1, _part5);
      _t3 = _part6;
      _part6 = _part6.rearrange(_zip0, _part7);
      _part7 = _t3.rearrange(_zip1, _part7);
      _t4 = _part8;
      _part8 = _part8.rearrange(_zip0, _part9);
      _part9 = _t4.rearrange(_zip1, _part9);
      _t5 = _partA;
      _partA = _partA.rearrange(_zip0, _partB);
      _partB = _t5.rearrange(_zip1, _partB);
      _t6 = _partC;
      _partC = _partC.rearrange(_zip0, _partD);
      _partD = _t6.rearrange(_zip1, _partD);
      _t7 = _partE;
      _partE = _partE.rearrange(_zip0, _partF);
      _partF = _t7.rearrange(_zip1, _partF);

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
      acc += input[pos];
      prefixMax[pos++] = acc;
    }
  }

  static void computeSuffixMax(int[] input, int[] suffixMax) {
    int iterations = input.length / (16 * vl);
    int remainder = input.length % (16 * vl);

    int acc = 0, pos = input.length;
    while (remainder-- > 0) {
      acc = Math.max(acc, input[--pos]);
      suffixMax[pos] = acc;
    }

    IntVector _prev = IntVector.broadcast(SP, acc);

    for (int i = 0; i < iterations; i++) {
      pos -= 16 * vl;

      IntVector _part0 = IntVector.fromArray(SP, input, pos);
      IntVector _part1 =
        IntVector.fromArray(SP, input, pos + vl);
      IntVector _part2 =
        IntVector.fromArray(SP, input, pos + 2 * vl);
      IntVector _part3 =
        IntVector.fromArray(SP, input, pos + 3 * vl);
      IntVector _part4 =
        IntVector.fromArray(SP, input, pos + 4 * vl);
      IntVector _part5 =
        IntVector.fromArray(SP, input, pos + 5 * vl);
      IntVector _part6 =
        IntVector.fromArray(SP, input, pos + 6 * vl);
      IntVector _part7 =
        IntVector.fromArray(SP, input, pos + 7 * vl);
      IntVector _part8 =
        IntVector.fromArray(SP, input, pos + 8 * vl);
      IntVector _part9 =
        IntVector.fromArray(SP, input, pos + 9 * vl);
      IntVector _partA =
        IntVector.fromArray(SP, input, pos + 10 * vl);
      IntVector _partB =
        IntVector.fromArray(SP, input, pos + 11 * vl);
      IntVector _partC =
        IntVector.fromArray(SP, input, pos + 12 * vl);
      IntVector _partD =
        IntVector.fromArray(SP, input, pos + 13 * vl);
      IntVector _partE =
        IntVector.fromArray(SP, input, pos + 14 * vl);
      IntVector _partF =
        IntVector.fromArray(SP, input, pos + 15 * vl);

      IntVector _t0 = _part0;
      _part0 = _part0.rearrange(_uzp0, _part1);
      _part1 = _t0.rearrange(_uzp1, _part1);
      IntVector _t1 = _part2;
      _part2 = _part2.rearrange(_uzp0, _part3);
      _part3 = _t1.rearrange(_uzp1, _part3);
      IntVector _t2 = _part4;
      _part4 = _part4.rearrange(_uzp0, _part5);
      _part5 = _t2.rearrange(_uzp1, _part5);
      IntVector _t3 = _part6;
      _part6 = _part6.rearrange(_uzp0, _part7);
      _part7 = _t3.rearrange(_uzp1, _part7);
      IntVector _t4 = _part8;
      _part8 = _part8.rearrange(_uzp0, _part9);
      _part9 = _t4.rearrange(_uzp1, _part9);
      IntVector _t5 = _partA;
      _partA = _partA.rearrange(_uzp0, _partB);
      _partB = _t5.rearrange(_uzp1, _partB);
      IntVector _t6 = _partC;
      _partC = _partC.rearrange(_uzp0, _partD);
      _partD = _t6.rearrange(_uzp1, _partD);
      IntVector _t7 = _partE;
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

      IntVector _partZ = _part0.slice(1, _prev);

      for (int step = 1; step < vl; step <<= 1) {
        IntVector _shifted = _partZ.slice(step, _zero);
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

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part8);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part9);
      _t2 = _part2;
      _part2 = _part2.rearrange(_zip0, _partA);
      _t3 = _part3;
      _part3 = _part3.rearrange(_zip0, _partB);
      _t4 = _part4;
      _part4 = _part4.rearrange(_zip0, _partC);
      _t5 = _part5;
      _part5 = _part5.rearrange(_zip0, _partD);
      _t6 = _part6;
      _part6 = _part6.rearrange(_zip0, _partE);
      _t7 = _part7;
      _part7 = _part7.rearrange(_zip0, _partF);
      _part8 = _t0.rearrange(_zip1, _part8);
      _part9 = _t1.rearrange(_zip1, _part9);
      _partA = _t2.rearrange(_zip1, _partA);
      _partB = _t3.rearrange(_zip1, _partB);
      _partC = _t4.rearrange(_zip1, _partC);
      _partD = _t5.rearrange(_zip1, _partD);
      _partE = _t6.rearrange(_zip1, _partE);
      _partF = _t7.rearrange(_zip1, _partF);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part4);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part5);
      _t2 = _part2;
      _part2 = _part2.rearrange(_zip0, _part6);
      _t3 = _part3;
      _part3 = _part3.rearrange(_zip0, _part7);
      _part4 = _t0.rearrange(_zip1, _part4);
      _part5 = _t1.rearrange(_zip1, _part5);
      _part6 = _t2.rearrange(_zip1, _part6);
      _part7 = _t3.rearrange(_zip1, _part7);
      _t4 = _part8;
      _part8 = _part8.rearrange(_zip0, _partC);
      _t5 = _part9;
      _part9 = _part9.rearrange(_zip0, _partD);
      _t6 = _partA;
      _partA = _partA.rearrange(_zip0, _partE);
      _t7 = _partB;
      _partB = _partB.rearrange(_zip0, _partF);
      _partC = _t4.rearrange(_zip1, _partC);
      _partD = _t5.rearrange(_zip1, _partD);
      _partE = _t6.rearrange(_zip1, _partE);
      _partF = _t7.rearrange(_zip1, _partF);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part2);
      _t1 = _part1;
      _part1 = _part1.rearrange(_zip0, _part3);
      _part2 = _t0.rearrange(_zip1, _part2);
      _part3 = _t1.rearrange(_zip1, _part3);
      _t2 = _part4;
      _part4 = _part4.rearrange(_zip0, _part6);
      _t3 = _part5;
      _part5 = _part5.rearrange(_zip0, _part7);
      _part6 = _t2.rearrange(_zip1, _part6);
      _part7 = _t3.rearrange(_zip1, _part7);
      _t4 = _part8;
      _part8 = _part8.rearrange(_zip0, _partA);
      _t5 = _part9;
      _part9 = _part9.rearrange(_zip0, _partB);
      _partA = _t4.rearrange(_zip1, _partA);
      _partB = _t5.rearrange(_zip1, _partB);
      _t6 = _partC;
      _partC = _partC.rearrange(_zip0, _partE);
      _t7 = _partD;
      _partD = _partD.rearrange(_zip0, _partF);
      _partE = _t6.rearrange(_zip1, _partE);
      _partF = _t7.rearrange(_zip1, _partF);

      _t0 = _part0;
      _part0 = _part0.rearrange(_zip0, _part1);
      _part1 = _t0.rearrange(_zip1, _part1);
      _t1 = _part2;
      _part2 = _part2.rearrange(_zip0, _part3);
      _part3 = _t1.rearrange(_zip1, _part3);
      _t2 = _part4;
      _part4 = _part4.rearrange(_zip0, _part5);
      _part5 = _t2.rearrange(_zip1, _part5);
      _t3 = _part6;
      _part6 = _part6.rearrange(_zip0, _part7);
      _part7 = _t3.rearrange(_zip1, _part7);
      _t4 = _part8;
      _part8 = _part8.rearrange(_zip0, _part9);
      _part9 = _t4.rearrange(_zip1, _part9);
      _t5 = _partA;
      _partA = _partA.rearrange(_zip0, _partB);
      _partB = _t5.rearrange(_zip1, _partB);
      _t6 = _partC;
      _partC = _partC.rearrange(_zip0, _partD);
      _partD = _t6.rearrange(_zip1, _partD);
      _t7 = _partE;
      _partE = _partE.rearrange(_zip0, _partF);
      _partF = _t7.rearrange(_zip1, _partF);

      _part0.intoArray(suffixMax, pos);
      _part1.intoArray(suffixMax, pos + vl);
      _part2.intoArray(suffixMax, pos + 2 * vl);
      _part3.intoArray(suffixMax, pos + 3 * vl);
      _part4.intoArray(suffixMax, pos + 4 * vl);
      _part5.intoArray(suffixMax, pos + 5 * vl);
      _part6.intoArray(suffixMax, pos + 6 * vl);
      _part7.intoArray(suffixMax, pos + 7 * vl);
      _part8.intoArray(suffixMax, pos + 8 * vl);
      _part9.intoArray(suffixMax, pos + 9 * vl);
      _partA.intoArray(suffixMax, pos + 10 * vl);
      _partB.intoArray(suffixMax, pos + 11 * vl);
      _partC.intoArray(suffixMax, pos + 12 * vl);
      _partD.intoArray(suffixMax, pos + 13 * vl);
      _partE.intoArray(suffixMax, pos + 14 * vl);
      _partF.intoArray(suffixMax, pos + 15 * vl);
    }
  }
}
