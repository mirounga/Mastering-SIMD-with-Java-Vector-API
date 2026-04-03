package com.nonpareilcoder.projecteuler;

public class MultiplesOf3Or5Smart {
  public static int compute(int n) {
    int k = (n - 1) / 3;
    int l = (n - 1) / 5;
    int m = (n - 1) / 15;

    int sum =
      3 * k * (k + 1) / 2 +
        5 * l * (l + 1) / 2 -
        15 * m * (m + 1) / 2;

    return sum;
  }
}
