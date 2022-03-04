package org.springreactive;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class MakeStringAngaram {

  //  private static Consumer<char[]> print = System.out::println;
//  private static Consumer<char[]> sort = Arrays::sort;


  public static int makeAnagram(String a, String b) {
    // Write your code here
    if (a.equals(b)) return 0;
    final char[] aChar = a.toCharArray();
    final char[] bChar = b.toCharArray();

    String br = b;
    String ar = a;

    for (int i = 0; i < b.length(); i++) {
      ar = ar.replaceFirst(String.valueOf(bChar[i]), "");
    }
    for (int i = 0; i < a.length(); i++) {
      br = br.replaceFirst(String.valueOf(aChar[i]), "");
    }

    return br.length() + ar.length();
  }


  @Test
  void samle() {
    final String a = "cde";
    final String b = "abc";
    final String cde = b.replaceFirst("[c][d][e]", "");
    System.out.println(cde);
//    System.out.println(makeAnagram(a, b));
  }
}
