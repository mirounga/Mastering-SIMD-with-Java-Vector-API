package com.nonpareilcoder.realleetcode;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class AdditiveScanBenchmark {
  static final int arraySize = 100_000_007;
  static final int arrayMax = 100_007;

  int[] input;

  @Setup(Level.Trial)
  public void generateRandomArray() {
    Random rand = new Random(42);
    input = new int[arraySize];

    for (int i = 0; i < arraySize; i++) {
      input[i] = rand.nextInt(arrayMax);
    }
  }

  @Benchmark
  public void scalarPrefix(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanScalar.prefixAdd(input, output);
  }

  @Benchmark
  public void scalarSuffix(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanScalar.suffixAdd(input, output);
  }

  @Benchmark
  public void horizontalPrefix(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanHorizontal.prefixAdd(input, output);
  }

  @Benchmark
  public void horizontalSuffix(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanHorizontal.suffixAdd(input, output);
  }

  @Benchmark
  public void horizontalPrefixX4(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanHorizontalX4.prefixAdd(input, output);
  }

  @Benchmark
  public void horizontalSuffixX4(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanHorizontalX4.suffixAdd(input, output);
  }

  @Benchmark
  public void transposedPrefixX4(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanTransposedX4.prefixAdd(input, output);
  }

  @Benchmark
  public void transposedSuffixX4(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanTransposedX4.suffixAdd(input, output);
  }

  @Benchmark
  public void transposedPrefixX8(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanTransposedX8.prefixAdd(input, output);
  }

  @Benchmark
  public void transposedSuffixX8(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanTransposedX8.suffixAdd(input, output);
  }

  @Benchmark
  public void transposedPrefixX16(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanTransposedX16.prefixAdd(input, output);
  }

  @Benchmark
  public void transposedSuffixX16(Blackhole bh) {
    int[] output = new int[arraySize];
    AdditiveScanTransposedX16.suffixAdd(input, output);
  }
}
