package com.nonpareilcoder.projecteuler;

public class MultiplesOf3Or5Naive {
  public static int compute(int n) {
    int sum = 0;
    for (int i = 3; i < n; i++) {
      if (i % 3 == 0 || i % 5 == 0) {
        sum += i;
      }
    }
    return sum;
  }
}
