package com.nonpareilcoder.projecteuler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class ConvergentsOfEBenchmark {
  @Benchmark
  public void scalar(Blackhole bh) {
    long sum = ConvergentsOfEScalar.compute();
  }

  @Benchmark
  public void horizontal(Blackhole bh) {
    long sum = ConvergentsOfEHorizontal.compute();
  }

  @Benchmark
  public void vertical(Blackhole bh) {
    long sum = ConvergentsOfEVertical.compute();
  }

  @Benchmark
  public void strassen(Blackhole bh) {
    long sum = ConvergentsOfEStrassen.compute();
  }

  @Benchmark
  public void karatsuba(Blackhole bh) {
    long sum = ConvergentsOfEKaratsuba.compute();
  }

  @Benchmark
  public void billionaire256(Blackhole bh) {
    long sum = ConvergentsOfEBillionaire256.compute();
  }

  @Benchmark
  public void billionaire128(Blackhole bh) {
    long sum = ConvergentsOfEBillionaire128.compute();
  }
}
