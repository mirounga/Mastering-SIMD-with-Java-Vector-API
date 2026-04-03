package com.nonpareilcoder.realleetcode;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class RemoveDuplicatesBenchmark {
  static final int arraySize = 100007;
  static final int arrayMax = 77;

  int[] stencil;
  int[] current;

  @Setup(Level.Trial)
  public void generateRandomArray() {
    Random rand = new Random(42);
    stencil = new int[arraySize];

    for (int i = 0; i < arraySize; i++) {
      stencil[i] = rand.nextInt(arrayMax);
    }
  }

  @Setup(Level.Iteration)
  public void copyData() {
    current = Arrays.copyOf(stencil, arraySize);
  }

  @Benchmark
  public void baseline(Blackhole bh) {
    RemoveDuplicatesScalar.compress(current);
  }

  @Benchmark
  public void shuffle(Blackhole bh) {
    RemoveDuplicatesShuffle.compress(current);
  }

  @Benchmark
  public void shuffleX4(Blackhole bh) {
    RemoveDuplicatesShuffleX4.compress(current);
  }

  @Benchmark
  public void load(Blackhole bh) {
    RemoveDuplicatesLoad.compress(current);
  }
}
