package com.nonpareilcoder.projecteuler;

public class LargestProductInSeriesSliding {
  static final String thousandDigits =
    LargestProductInSeriesBaseline.thousandDigits;

  public static long compute() {
    long maxValue = 0;

    for (int start = 0, i; start < 1000; start = i) {

      long currentValue = 1;

      for (i = start; i < 1000; i++) {
        int newDigit = Character
          .getNumericValue(thousandDigits.charAt(i));

        if (newDigit == 0) {
          i++;
          break;
        }

        currentValue *= newDigit;

        if (i < start + 12)
          continue;

        if (maxValue < currentValue) {
          maxValue = currentValue;
        }

        int oldDigit = Character
          .getNumericValue(thousandDigits.charAt(i - 12));
        currentValue /= oldDigit;
      }
    }

    return maxValue;
  }
}
