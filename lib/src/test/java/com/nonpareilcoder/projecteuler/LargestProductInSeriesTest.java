package com.nonpareilcoder.projecteuler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LargestProductInSeriesTest {
  public final long expected = 23_514_624_000L;

  @Test
  public void baseline() {
    long maxValue = LargestProductInSeriesBaseline.compute();

    assertEquals(expected, maxValue);
  }

  @Test
  public void sliding() {
    long maxValue = LargestProductInSeriesSliding.compute();

    assertEquals(expected, maxValue);
  }

  @Test
  public void online() {
    long maxValue = LargestProductInSeriesOnline.compute();

    assertEquals(expected, maxValue);
  }

  @Test
  public void horizontal() {
    long maxValue = LargestProductInSeriesHorizontal.compute();

    assertEquals(expected, maxValue);
  }

  @Test
  public void vertical() {
    long maxValue = LargestProductInSeriesVertical.compute();

    assertEquals(expected, maxValue);
  }

  @Test
  public void gather() {
    long maxValue = LargestProductInSeriesGather.compute();

    assertEquals(expected, maxValue);
  }

  @Test
  public void convergence() {
    long[] sc = LargestProductInSeriesComparator.computeScalar();

    long[] ho = LargestProductInSeriesComparator.computeHorizontal();

    for (int i = 0; i < 987; i++) {
      assertEquals(sc[i], ho[i]);
    }

    long[] ve = LargestProductInSeriesComparator.computeVertical();

    for (int i = 0; i < 987; i++) {
      assertEquals(sc[i], ve[i]);
    }

    long[] ga = LargestProductInSeriesComparator.computeGather();

    for (int i = 0; i < 987; i++) {
      assertEquals(sc[i], ga[i]);
    }
  }
}
