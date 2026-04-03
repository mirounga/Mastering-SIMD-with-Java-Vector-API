package com.nonpareilcoder.realleetcode;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class TrappingRainWaterBenchmark {
  static final int arraySize = 100_000_007;
  static final int arrayMax = 17;

  int[] heights;

  @Setup(Level.Trial)
  public void generateRandomArray() {
    Random rand = new Random(42);
    heights = new int[arraySize];

    for (int i = 0; i < arraySize; i++) {
      heights[i] = rand.nextInt(arrayMax);
    }
  }

  @Benchmark
  public void scalar(Blackhole bh) {
    TrappingRainWaterScalar.trap(heights);
  }

  @Benchmark
  public void horizontal(Blackhole bh) {
    TrappingRainWaterHorizontal.trap(heights);
  }

  @Benchmark
  public void horizontalX4(Blackhole bh) {
    TrappingRainWaterHorizontalX4.trap(heights);
  }

  @Benchmark
  public void transposedX4(Blackhole bh) {
    TrappingRainWaterTransposedX4.trap(heights);
  }

  @Benchmark
  public void transposedX16(Blackhole bh) {
    TrappingRainWaterTransposedX16.trap(heights);
  }

  @Benchmark
  public void memoizedX16(Blackhole bh) {
    TrappingRainWaterMemoizedX16.trap(heights);
  }

  @Benchmark
  public void shortX16(Blackhole bh) {
    TrappingRainWater128X16.trap(heights);
  }
}
