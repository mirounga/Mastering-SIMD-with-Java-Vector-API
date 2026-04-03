package com.nonpareilcoder.floatingpoint;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.*;

public class SimpleSummationTest {
  @Test
  public void lanewise() {
    float[] unexpected = {0.0f, 0.0f, 1.0f, -1.0f};

    float[] actual = SimpleSummationLanewise.add();
    System.out.println(Arrays.toString(actual));

    assertArrayEquals(unexpected, actual);
  }

  @Test
  public void daisyChain() {
    float[] unexpected = {0.0f, 0.0f, 1.0f, -1.0f};

    float[] actual = SimpleSummationDaisyChain.add();
    System.out.println(Arrays.toString(actual));

    assertArrayEquals(unexpected, actual);
  }

  @Test
  public void reduce() {
    float[] unexpected = {0.0f, 2.0f, 1.0f, -1.0f};

    float[] actual = SimpleSummationReduce.add();
    System.out.println(Arrays.toString(actual));

    assertArrayEquals(unexpected, actual);
  }

  @Test
  public void corrected() {
    float[] expected = {1.0f, 1.0f, 1.0f, 1.0f};

    float[] actual = SimpleSummationCorrected.add();
    System.out.println(Arrays.toString(actual));

    assertArrayEquals(expected, actual);
  }
}