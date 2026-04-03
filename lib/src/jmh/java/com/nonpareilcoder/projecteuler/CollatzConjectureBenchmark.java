package com.nonpareilcoder.projecteuler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class CollatzConjectureBenchmark {
  public final int problemSize = 1_000_000;

  @Benchmark
  public void scalar(Blackhole bh) {
    long start = CollatzConjectureScalar.longestOrbit(problemSize);
  }

  @Benchmark
  public void vectorized(Blackhole bh) {
    long start = CollatzConjectureVectorized.longestOrbit(problemSize);
  }

  @Benchmark
  public void accelerated(Blackhole bh) {
    long start = CollatzConjectureAccelerated.longestOrbit(problemSize);
  }

  @Benchmark
  public void lookup(Blackhole bh) {
    long start = CollatzConjectureLookup.longestOrbit(problemSize);
  }

  @Benchmark
  public void combined(Blackhole bh) {
    long start = CollatzConjectureCombined.longestOrbit(problemSize);
  }

  @Benchmark
  public void piled(Blackhole bh) {
    long start = CollatzConjecturePiled.longestOrbit(problemSize);
  }

  @Benchmark
  public void memoized(Blackhole bh) {
    long start = CollatzConjectureMemoized.longestOrbit(problemSize);
  }

  @Benchmark
  public void comprehensive(Blackhole bh) {
    long start = CollatzConjectureComprehensive.longestOrbit(problemSize);
  }
}
