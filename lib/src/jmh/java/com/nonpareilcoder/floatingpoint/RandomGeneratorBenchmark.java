package com.nonpareilcoder.floatingpoint;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class RandomGeneratorBenchmark {
  static final int arraySize = 100_000_007;
  static final float arrayFloat = 1.0f;
  static final int arrayMax = 1009;
  static final long arraySeed = 42L;

  @Benchmark
  public void floatBaseline() {
    float[] floatArray = new float[arraySize];

    Random rand = new Random(arraySeed);

    for (int i = 0; i < arraySize; i++) {
      floatArray[i] = rand.nextFloat(arrayMax);
    }
  }

  @Benchmark
  public void intBaseline() {
    int[] intArray = new int[arraySize];

    Random rand = new Random(arraySeed);

    for (int i = 0; i < arraySize; i++) {
      intArray[i] = rand.nextInt(arrayMax);
    }
  }


  @Benchmark
  public void floatScalar() {
    float[] floatArray = RandomGeneratorScalar.generateFloatArray(arraySeed, arrayFloat, arraySize);
  }

  @Benchmark
  public void intScalar() {
    int[] actual = RandomGeneratorScalar.generateIntArray(arraySeed, arrayMax, arraySize);
  }

  @Benchmark
  public void floatVectorized() {
    float[] floatArray = RandomGeneratorVectorized.generateFloatArray(arraySeed, arrayFloat, arraySize);
  }

  @Benchmark
  public void intVectorized() {
    int[] actual = RandomGeneratorVectorized.generateIntArray(arraySeed, arrayMax, arraySize);
  }
}
