package com.nonpareilcoder.projecteuler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplesOf3Or5Test {
  public final int expected = 233168;

  @Test
  public void naive() {
    int sum = MultiplesOf3Or5Naive.compute(1000);

    assertEquals(expected, sum);
  }

  @Test
  public void vectorized() {
    int sum = MultiplesOf3Or5Vectorized.compute(1000);

    assertEquals(expected, sum);
  }

  @Test
  public void smart() {
    int sum = MultiplesOf3Or5Smart.compute(1000);

    assertEquals(expected, sum);
  }

  @Test
  public void fast() {
    int sum = MultiplesOf3Or5Fast.compute(1000);

    assertEquals(expected, sum);
  }
}
