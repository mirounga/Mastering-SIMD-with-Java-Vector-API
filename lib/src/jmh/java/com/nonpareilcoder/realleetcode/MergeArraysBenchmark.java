package com.nonpareilcoder.realleetcode;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MergeArraysBenchmark {
  static final int arraySize = 100_000_007;
  static final int arrayMax = 17;

  int[] input0;
  int[] input1;

  @Setup(Level.Trial)
  public void generateRandomArrays() {
    Random rand = new Random(42);
    input0 = new int[arraySize];
    input1 = new int[arraySize];

    for (int i = 0; i < arraySize; i++) {
      input0[i] = rand.nextInt(arrayMax);
      input1[i] = rand.nextInt(arrayMax);
    }
  }

  @Benchmark
  public void scalar(Blackhole bh) {
    MergeArraysScalar.merge(input0, input1);
  }

  @Benchmark
  public void bitonic(Blackhole bh) {
    MergeArraysBitonic.merge(input0, input1);
  }

  @Benchmark
  public void unrolled(Blackhole bh) {
    MergeArraysUnrolled.merge(input0, input1);
  }

  @Benchmark
  public void asymmetric(Blackhole bh) {
    MergeArraysAsymmetric.merge(input0, input1);
  }

  @Benchmark
  public void scatter(Blackhole bh) {
    MergeArraysScatter.merge(input0, input1);
  }
}
