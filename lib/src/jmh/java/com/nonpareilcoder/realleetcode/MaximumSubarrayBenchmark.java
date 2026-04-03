package com.nonpareilcoder.realleetcode;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MaximumSubarrayBenchmark {
  static final int arraySize = 100_000_007;
  static final int arrayMax = 10077;
  static final int arraySeed = 42;

  int[] input;

  @Setup(Level.Trial)
  public void generateRandomArray() {
    Random rand = new Random(arraySeed);
    input = new int[arraySize];

    for (int i = 0; i < arraySize; i++) {
      input[i] = rand.nextInt(arrayMax) - arrayMax / 2;
    }
  }

  @Benchmark
  public void baseline(Blackhole bh) {
    MaximumSubarrayBaseline.compute(input);
  }

  @Benchmark
  public void prefix(Blackhole bh) {
    MaximumSubarrayPrefix.compute(input);
  }

  @Benchmark
  public void preferredX8(Blackhole bh) {
    MaximumSubarrayPreferredX8.compute(input);
  }

  @Benchmark
  public void shortX8(Blackhole bh) {
    MaximumSubarray128X8.compute(input);
  }
}
