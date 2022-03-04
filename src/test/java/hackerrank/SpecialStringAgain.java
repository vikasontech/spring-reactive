package hackerrank;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

/**
 * A string is said to be a special string if either of two conditions is met:
 * <p>
 * All of the characters are the same, e.g. aaa.
 * All characters except the middle one are the same, e.g. aadaa.
 * <p>
 * A special substring is any substring of a string which meets one of those criteria. Given a string, determine how many special substrings can be formed from it.
 * source: https://www.hackerrank.com/challenges/special-palindrome-again/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=strings
 */
public class SpecialStringAgain {


  static Function<String, Boolean> isSameChar = it -> {
    final char[] chars = it.toCharArray();

    for (char aChar : chars) {
      if (aChar != chars[0]) {
        return false;
      }
    }
    return true;
  };

  static Function<String, Boolean> isPalindromNumber = it -> {
    final int i = it.length() / 2;
    final String substring1 = it.substring(0, i);
    final String substring2 = it.substring(i+1, it.length());
    return substring1.equals(substring2);
  };

  //mnonopoo
  static long substrCount(int n, String s) {
    int startIndex = 0, count = 0;
    int i = 1, j = 0;

    while (true) {

      final String substring = s.substring(startIndex, startIndex + i);
      if (i % 2 == 0) {
        if (isSameChar.apply(substring)) {
          count++;
        }else {
          startIndex ++;
        }
      } else {
        if(isPalindromNumber.apply(substring)) {
          count++;
        }
      }
//      startIndex++;
      i++;
      if (startIndex >= n) break;
    }
    return count;
  }

  @Test
  void sample() {
    String input = "mnonopoo";
    System.out.println(substrCount(input.length(), input));
  }
}
