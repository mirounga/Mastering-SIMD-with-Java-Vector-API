package com.nonpareilcoder.projecteuler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class MultiplesOf3Or5Benchmark {
  @Benchmark
  public void naive(Blackhole bh) {
    MultiplesOf3Or5Naive.compute(1000);
  }

  @Benchmark
  public void vectorized(Blackhole bh) {
    MultiplesOf3Or5Vectorized.compute(1000);
  }

  @Benchmark
  public void smart(Blackhole bh) {
    MultiplesOf3Or5Smart.compute(1000);
  }

  @Benchmark
  public void fast(Blackhole bh) {
    MultiplesOf3Or5Fast.compute(1000);
  }
}
