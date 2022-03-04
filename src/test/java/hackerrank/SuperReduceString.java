package hackerrank;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class SuperReduceString {


  private static UnaryOperator<String> findTheAdjacentLetter = it -> {
    final char[] ar = it.toCharArray();
    int i = 0;
    while (i < ar.length) {
      if (i == ar.length - 1) break;
      if (ar[i] == ar[i + 1]) {
        return it.substring(i, i+ 2);
      }
      i++;
    }
    return "";
  };

  public static UnaryOperator<String> emptyConveror = it ->
      it.isEmpty()?"Empty String":it;

  public static String superReducedString(final String s) {
    //baab
    String aValue = s;
    while (true) {
      final String apply1 = findTheAdjacentLetter.apply(aValue);
      if(apply1.isEmpty()) {
        break;
      }
      aValue = aValue.replaceAll(apply1, "");
    }
    return emptyConveror.apply(aValue);
  }

  @Test
  void name() {
    String input;
    input = "baab";
    input = "aaabccddd";
    input = "aaabccddd";
    input = "aa";
    input = "aaabccddd";
    System.out.println(superReducedString(input));
  }
}
