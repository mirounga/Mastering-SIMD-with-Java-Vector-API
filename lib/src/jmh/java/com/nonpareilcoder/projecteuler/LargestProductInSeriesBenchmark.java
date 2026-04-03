package com.nonpareilcoder.projecteuler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class LargestProductInSeriesBenchmark {
  @Benchmark
  public void baseline(Blackhole bh) {
    LargestProductInSeriesBaseline.compute();
  }

  @Benchmark
  public void sliding(Blackhole bh) {
    LargestProductInSeriesSliding.compute();
  }

  @Benchmark
  public void online(Blackhole bh) {
    LargestProductInSeriesOnline.compute();
  }

  @Benchmark
  public void horizontal(Blackhole bh) {
    LargestProductInSeriesHorizontal.compute();
  }

  @Benchmark
  public void vertical(Blackhole bh) {
    LargestProductInSeriesVertical.compute();
  }

  @Benchmark
  public void gather(Blackhole bh) {
    LargestProductInSeriesGather.compute();
  }
}
