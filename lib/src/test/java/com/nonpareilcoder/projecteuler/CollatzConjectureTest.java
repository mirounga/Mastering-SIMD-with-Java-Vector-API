package com.nonpareilcoder.projecteuler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollatzConjectureTest {
  public final int problemSize = 1_000_000;
  public final long expected = 837799;

  @Test
  public void scalar() {
    long actual = CollatzConjectureScalar.longestOrbit(problemSize);

    assertEquals(expected, actual);
  }

  @Test
  public void vectorized() {
    long actual = CollatzConjectureVectorized.longestOrbit(problemSize);

    assertEquals(expected, actual);
  }

  @Test
  public void accelerated() {
    long actual = CollatzConjectureAccelerated.longestOrbit(problemSize);

    assertEquals(expected, actual);
  }

  @Test
  public void lookup() {
    long actual = CollatzConjectureLookup.longestOrbit(problemSize);

    assertEquals(expected, actual);
  }

  @Test
  public void combined() {
    long actual = CollatzConjectureCombined.longestOrbit(problemSize);

    assertEquals(expected, actual);
  }

  @Test
  public void piled() {
    long actual = CollatzConjecturePiled.longestOrbit(problemSize);

    assertEquals(expected, actual);
  }

  @Test
  public void memoized() {
    long actual = CollatzConjectureMemoized.longestOrbit(problemSize);

    assertEquals(expected, actual);
  }

  @Test
  public void comprehensive() {
    long actual = CollatzConjectureComprehensive.longestOrbit(problemSize);

    assertEquals(expected, actual);
  }
}
