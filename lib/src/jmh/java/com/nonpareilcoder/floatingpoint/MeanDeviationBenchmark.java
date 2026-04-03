package com.nonpareilcoder.floatingpoint;

import com.nonpareilcoder.realleetcode.MergeArraysScalar;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MeanDeviationBenchmark {
  static final int arraySize = 100_000_007;
  static final float arrayMax = 1.0f;

  static float[] input;

  @Setup(Level.Trial)
  public static void generateRandomArray() {
    Random rand = new Random(42);
    input = new float[arraySize];

    for (int i = 0; i < arraySize; i++) {
      input[i] = rand.nextFloat(arrayMax);
    }
  }

  @Benchmark
  public void single(Blackhole bh) {
    MeanDeviationSinglePrecision.compute(input);
  }

  @Benchmark
  public void singlePass(Blackhole bh) {
    MeanDeviationSinglePass.compute(input);
  }

  @Benchmark
  public void singleCorrected(Blackhole bh) {
    MeanDeviationSingleCorrected.compute(input);
  }

  @Benchmark
  public void doubleCorrected(Blackhole bh) {
    MeanDeviationDoubleCorrected.compute(input);
  }

  @Benchmark
  public void reduced(Blackhole bh) {
    MeanDeviationReduced.compute(input);
  }

  @Benchmark
  public void lanewise(Blackhole bh) {
    MeanDeviationLanewise.compute(input);
  }

  @Benchmark
  public void lanewiseX4(Blackhole bh) {
    MeanDeviationLanewiseX4.compute(input);
  }

  @Benchmark
  public void lanewiseCorrected(Blackhole bh) {
    MeanDeviationLanewiseCorrected.compute(input);
  }

  @Benchmark
  public void lanewiseCorrectedX4(Blackhole bh) {
    MeanDeviationLanewiseCorrectedX4.compute(input);
  }
}
