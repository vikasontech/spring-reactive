package org.springreactive;


import org.junit.jupiter.api.Test;

/**
 * You are given a string containing characters and
 *
 * only. Your task is to change it into a string such that there are no matching adjacent characters. To do this, you are allowed to delete zero or more characters in the string.
 *
 * Your task is to find the minimum number of required deletions.
 * Sample input
 * 5
 * AAAA
 * BBBBB
 * ABABABAB
 * BABABA
 * AAABBB
 *
 * Sample Output
 * 3
 * 4
 * 0
 * 0
 * 4
 */
public class AlternatingChars {


  public static int alternatingCharacters(String s) {
    // Write your code here
    final char[] chars = s.toCharArray();
    char lastChar  = '\0';
    int count= 0;
    for(int i=0; i<s.length(); i++) {
      if(lastChar == chars[i]) {
       count ++;
      }
      lastChar = chars[i];
    }
    return count;
  }

  @Test
  void sample() {
//    final String input = "AAAA";
//    final String input = "BBBBB";
//    final String input = "ABABABAB";
//    final String input = "BABABA";
//    final String input = "AAABBB";
    final String input = "AABAAB";
    System.out.println(alternatingCharacters(input));
  }
}
