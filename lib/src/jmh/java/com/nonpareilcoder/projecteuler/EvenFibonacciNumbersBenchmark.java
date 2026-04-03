package com.nonpareilcoder.projecteuler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class EvenFibonacciNumbersBenchmark {
  @Benchmark
  public void baseline(Blackhole b) {
    int sum = EvenFibonacciNumbersScalar.compute(4_000_000);
  }

  @Benchmark
  public void leapScalar(Blackhole b) {
    int sum = EvenFibonacciNumbersLeapScalar.compute(4_000_000);
  }

  @Benchmark
  public void vectorized(Blackhole b) {
    int sum = EvenFibonacciNumbersVectorized.compute(4_000_000);
  }

  @Benchmark
  public void leapVectorized(Blackhole b) {
    int sum = EvenFibonacciNumbersLeapVectorized.compute(4_000_000);
  }
}
