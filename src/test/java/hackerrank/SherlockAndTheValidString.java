package hackerrank;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SherlockAndTheValidString {

  public static String isValid(String s) {
    // Write your code here
    final Map<Character, Integer> characterIntegerHashMap = new HashMap<>();
    Consumer<Character> getAndAdd = it -> {
      Integer orDefault = characterIntegerHashMap.getOrDefault(it, 0);
      final Integer value = ++orDefault;
      characterIntegerHashMap.put(it, value);
    };

    final char[] chars = s.toCharArray();
    for (int i = 0; i < s.length(); i++) {
      final char aChar = chars[i];
      getAndAdd.accept(aChar);
    }

    boolean isFirst = true, isOneOddFound = false;
    int constValue = 0;

    final Collection<Integer> values = characterIntegerHashMap.values();
    final Integer[] integers = values.toArray(new Integer[0]);
    for (int i = 0; i < values.size(); i++) {
      if (isFirst) {
        constValue = integers[i];
        isFirst = false;
      }
      if (constValue != integers[i]) {
        if (((1 + constValue) == integers[i])) {
          if (!isOneOddFound) {
            isOneOddFound = true;
          } else {
            return "NO";
          }
        } else {
          if (!isOneOddFound && (1 == integers[i])) {
            isOneOddFound = true;
          } else {
            return "NO";
          }
        }
      }
    }

    return "YES";
  }

  @Test
  void name() {
    String input = "";
    input = "aabbcd";
    input = "aabbccddeefghi";
    input = "abcdefghhgfedecba";
    input = "abc";
    input = "abcc";
    input = "abccc";
    input = "aabbc";
    input = "ibfdgaeadiaefgbhbdghhhbgdfgeiccbiehhfcggchgghadhdhagfbahhddgghbdehidbibaeaagaeeigffcebfbaieggabcfbiiedcabfihchdfabifahcbhagccbdfifhghcadfiadeeaheeddddiecaicbgigccageicehfdhdgafaddhffadigfhhcaedcedecafeacbdacgfgfeeibgaiffdehigebhhehiaahfidibccdcdagifgaihacihadecgifihbebffebdfbchbgigeccahgihbcbcaggebaaafgfedbfgagfediddghdgbgehhhifhgcedechahidcbchebheihaadbbbiaiccededchdagfhccfdefigfibifabeiaccghcegfbcghaefifbachebaacbhbfgfddeceababbacgffbagidebeadfihaefefegbghgddbbgddeehgfbhafbccidebgehifafgbghafacgfdccgifdcbbbidfifhdaibgigebigaedeaaiadegfefbhacgddhchgcbgcaeaieiegiffchbgbebgbehbbfcebciiagacaiechdigbgbghefcahgbhfibhedaeeiffebdiabcifgccdefabccdghehfibfiifdaicfedagahhdcbhbicdgibgcedieihcichadgchgbdcdagaihebbabhibcihicadgadfcihdheefbhffiageddhgahaidfdhhdbgciiaciegchiiebfbcbhaeagccfhbfhaddagnfieihghfbaggiffbbfbecgaiiidccdceadbbdfgigibgcgchafccdchgifdeieicbaididhfcfdedbhaadedfageigfdehgcdaecaebebebfcieaecfagfdieaefdiedbcadchabhebgehiidfcgahcdhcdhgchhiiheffiifeegcfdgbdeffhgeghdfhbfbifgidcafbfcd";
    System.out.println(isValid(input));
  }
}
