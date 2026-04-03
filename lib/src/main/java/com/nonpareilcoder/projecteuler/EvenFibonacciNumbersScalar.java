package com.nonpareilcoder.projecteuler;

public class EvenFibonacciNumbersScalar {
  public static int compute(int limit) {
    int fib1 = 1, fib2 = 2, sum = fib2;

    while (true) {
      int fib3 = fib1 + fib2;
      fib1 = fib2;
      fib2 = fib3;

      if (fib2 > limit)
        break;

      if (fib2 % 2 == 0)
        sum += fib2;
    }

    return sum;
  }
}
