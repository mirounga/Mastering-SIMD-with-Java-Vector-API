package com.nonpareilcoder.projecteuler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConvergentsOfETest {
  public final long expected = 272;

  @Test
  public void scalar() {
    long sum = ConvergentsOfEScalar.compute();

    assertEquals(expected, sum);
  }

  @Test
  public void horizontal() {
    long sum = ConvergentsOfEHorizontal.compute();

    assertEquals(expected, sum);
  }

  @Test
  public void vertical() {
    long sum = ConvergentsOfEVertical.compute();

    assertEquals(expected, sum);
  }

  @Test
  public void strassen() {
    long sum = ConvergentsOfEStrassen.compute();

    assertEquals(expected, sum);
  }

  @Test
  public void karatsuba() {
    long sum = ConvergentsOfEKaratsuba.compute();

    assertEquals(expected, sum);
  }

  @Test
  public void billionaire256() {
    long sum = ConvergentsOfEBillionaire256.compute();

    assertEquals(expected, sum);
  }

  @Test
  public void billionaire128() {
    long sum = ConvergentsOfEBillionaire128.compute();

    assertEquals(expected, sum);
  }
}
