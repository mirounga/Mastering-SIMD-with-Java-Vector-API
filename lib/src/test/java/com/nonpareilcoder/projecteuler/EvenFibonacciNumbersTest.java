package com.nonpareilcoder.projecteuler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EvenFibonacciNumbersTest {
  public final int expected = 4613732;

  @Test
  public void scalar() {
    int sum = EvenFibonacciNumbersScalar.compute(4_000_000);

    assertEquals(expected, sum);
  }

  @Test
  public void leapScalar() {
    int sum = EvenFibonacciNumbersLeapScalar.compute(4_000_000);

    assertEquals(expected, sum);
  }

  @Test
  public void vectorized() {
    int sum = EvenFibonacciNumbersVectorized.compute(4_000_000);

    assertEquals(expected, sum);
  }

  @Test
  public void leapVectorized() {
    int sum = EvenFibonacciNumbersLeapVectorized.compute(4_000_000);

    assertEquals(expected, sum);
  }
}
