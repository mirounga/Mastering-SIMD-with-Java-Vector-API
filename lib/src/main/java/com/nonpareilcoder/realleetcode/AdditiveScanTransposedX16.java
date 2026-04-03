package com.nonpareilcoder.realleetcode;

import jdk.incubator.vector.*;

public class AdditiveScanTransposedX16 {
  static final VectorSpecies<Integer> SP =
    IntVector.SPECIES_PREFERRED;
  static final int vl = SP.length();

  public static void prefixAdd(int[] input, int[] output) {
    if (input.length != output.length)
      throw new ArrayIndexOutOfBoundsException(output.length);

    int arraySize = input.length;
    if (arraySize == 0)
      return;

    final IntVector _zero = IntVector.broadcast(SP, 0);

    final var _zip0 = VectorShuffle.makeZip(SP, 0);
    final var _zip1 = VectorShuffle.makeZip(SP, 1);
    final var _uzp0 = VectorShuffle.makeUnzip(SP, 0);
    final var _uzp1 = VectorShuffle.makeUnzip(SP, 1);

    int longIterations = arraySize / (16 * vl);
    int longRemposnder = arraySize % (16 * vl);

    var _prev = _zero;

    int pos = 0;
    for (int i = 0; i < longIterations; i++) {
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

      _part1 = _part1.add(_part0);
      _part3 = _part3.add(_part2);
      _part5 = _part5.add(_part4);
      _part7 = _part7.add(_part6);
      _part9 = _part9.add(_part8);
      _partB = _partB.add(_partA);
      _partD = _partD.add(_partC);
      _partF = _partF.add(_partE);

      _part3 = _part3.add(_part1);
      _part7 = _part7.add(_part5);
      _partB = _partB.add(_part9);
      _partF = _partF.add(_partD);

      _part7 = _part7.add(_part3);
      _partF = _partF.add(_partB);

      _partF = _partF.add(_part7);

      var _partZ = _prev.slice(vl - 1, _partF);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _zero.slice(vl - step, _partZ);
        _partZ = _partZ.add(_shifted);
      }

      _part0 = _part0.add(_partZ);
      _part1 = _part1.add(_partZ);
      _part3 = _part3.add(_partZ);
      _part7 = _part7.add(_partZ);
      _partF = _partF.add(_partZ);

      _partB = _partB.add(_part7);

      _part5 = _part5.add(_part3);
      _part9 = _part9.add(_part7);
      _partD = _partD.add(_partB);

      _part2 = _part2.add(_part1);
      _part4 = _part4.add(_part3);
      _part6 = _part6.add(_part5);
      _part8 = _part8.add(_part7);
      _partA = _partA.add(_part9);
      _partC = _partC.add(_partB);
      _partE = _partE.add(_partD);

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

      _part0.intoArray(output, pos);
      _part1.intoArray(output, pos + vl);
      _part2.intoArray(output, pos + 2 * vl);
      _part3.intoArray(output, pos + 3 * vl);
      _part4.intoArray(output, pos + 4 * vl);
      _part5.intoArray(output, pos + 5 * vl);
      _part6.intoArray(output, pos + 6 * vl);
      _part7.intoArray(output, pos + 7 * vl);
      _part8.intoArray(output, pos + 8 * vl);
      _part9.intoArray(output, pos + 9 * vl);
      _partA.intoArray(output, pos + 10 * vl);
      _partB.intoArray(output, pos + 11 * vl);
      _partC.intoArray(output, pos + 12 * vl);
      _partD.intoArray(output, pos + 13 * vl);
      _partE.intoArray(output, pos + 14 * vl);
      _partF.intoArray(output, pos + 15 * vl);

      pos += 16 * vl;
    }

    int acc = _prev.lane(vl - 1);

    while (longRemposnder-- > 0) {
      acc += input[pos];
      output[pos++] = acc;
    }
  }

  public static void suffixAdd(int[] input, int[] output) {
    if (input.length != output.length)
      throw new ArrayIndexOutOfBoundsException(output.length);

    int arraySize = input.length;
    if (arraySize == 0)
      return;

    final IntVector _zero = IntVector.broadcast(SP, 0);

    final var _zip0 = VectorShuffle.makeZip(SP, 0);
    final var _zip1 = VectorShuffle.makeZip(SP, 1);
    final var _uzp0 = VectorShuffle.makeUnzip(SP, 0);
    final var _uzp1 = VectorShuffle.makeUnzip(SP, 1);

    int longIterations = arraySize / (16 * vl);
    int longRemposnder = arraySize % (16 * vl);

    int acc = 0;

    int pos = input.length;
    while (longRemposnder-- > 0) {
      acc += input[--pos];
      output[pos] = acc;
    }

    var _prev = IntVector.broadcast(SP, acc);

    for (int i = 0; i < longIterations; i++) {
      pos -= 16 * vl;

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

      _partE = _partE.add(_partF);
      _partC = _partC.add(_partD);
      _partA = _partA.add(_partB);
      _part8 = _part8.add(_part9);
      _part6 = _part6.add(_part7);
      _part4 = _part4.add(_part5);
      _part2 = _part2.add(_part3);
      _part0 = _part0.add(_part1);

      _partC = _partC.add(_partE);
      _part8 = _part8.add(_partA);
      _part4 = _part4.add(_part6);
      _part0 = _part0.add(_part2);

      _part8 = _part8.add(_partC);
      _part0 = _part0.add(_part4);

      _part0 = _part0.add(_part8);

      var _partZ = _part0.slice(1, _prev);

      for (int step = 1; step < vl; step <<= 1) {
        var _shifted = _partZ.slice(step, _zero);
        _partZ = _partZ.add(_shifted);
      }

      _part0 = _part0.add(_partZ);
      _part8 = _part8.add(_partZ);
      _partC = _partC.add(_partZ);
      _partE = _partE.add(_partZ);
      _partF = _partF.add(_partZ);

      _part4 = _part4.add(_part8);

      _partA = _partA.add(_partC);
      _part6 = _part6.add(_part8);
      _part2 = _part2.add(_part4);

      _partD = _partD.add(_partE);
      _partB = _partB.add(_partC);
      _part9 = _part9.add(_partA);
      _part7 = _part7.add(_part8);
      _part5 = _part5.add(_part6);
      _part3 = _part3.add(_part4);
      _part1 = _part1.add(_part2);

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

      _part0.intoArray(output, pos);
      _part1.intoArray(output, pos + vl);
      _part2.intoArray(output, pos + 2 * vl);
      _part3.intoArray(output, pos + 3 * vl);
      _part4.intoArray(output, pos + 4 * vl);
      _part5.intoArray(output, pos + 5 * vl);
      _part6.intoArray(output, pos + 6 * vl);
      _part7.intoArray(output, pos + 7 * vl);
      _part8.intoArray(output, pos + 8 * vl);
      _part9.intoArray(output, pos + 9 * vl);
      _partA.intoArray(output, pos + 10 * vl);
      _partB.intoArray(output, pos + 11 * vl);
      _partC.intoArray(output, pos + 12 * vl);
      _partD.intoArray(output, pos + 13 * vl);
      _partE.intoArray(output, pos + 14 * vl);
      _partF.intoArray(output, pos + 15 * vl);
    }
  }
}
