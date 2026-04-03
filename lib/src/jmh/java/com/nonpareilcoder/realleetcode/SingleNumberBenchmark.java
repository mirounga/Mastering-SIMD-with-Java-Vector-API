package com.nonpareilcoder.realleetcode;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SingleNumberBenchmark {
  static final int arraySize = 100_000_007;
  static final int arrayMax = 10077;
  static final int arraySeed = 42;

  int[] input2;
  int[] input3;

  public static int fillRandomArray(int[] input, int repeats) {
    Random rand = new Random(arraySeed);
    final int repeatCount = (input.length - 1) / repeats;

    int idx = 0;
    for (int i = 0; i < repeatCount; i++) {
      int next = rand.nextInt(arrayMax);
      for (int j = 0; j < repeats; j++)
        input[idx++] = next;
    }

    int unique = input[idx] = rand.nextInt(arrayMax);

    for (int i = idx; i > 0; i--) {
      int j = rand.nextInt(i + 1);
      int temp = input[i];
      input[i] = input[j];
      input[j] = temp;
    }

    return unique;
  }

  @Setup(Level.Trial)
  public void generateRandomArrays() {
    input2 = new int[arraySize];
    input3 = new int[arraySize];

    fillRandomArray(input2, 2);
    fillRandomArray(input3, 3);
  }

  @Benchmark
  public void pairsScalar(Blackhole bh) {
    SingleNumberPairsScalar.find(input2);
  }

  @Benchmark
  public void pairsVectorized(Blackhole bh) {
    SingleNumberPairsVectorized.find(input2);
  }

  @Benchmark
  public void triplesScalar(Blackhole bh) {
    SingleNumberTriplesScalar.find(input3);
  }

  @Benchmark
  public void triplesVectorized(Blackhole bh) {
    SingleNumberTriplesVectorized.find(input3);
  }
}
