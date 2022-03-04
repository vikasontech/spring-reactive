package hackerrank;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
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
public class SpecialStringAgain2 {


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
        final String substring2 = it.substring(i + 1, it.length());
        return substring1.equals(substring2);
    };

    //mnonopoo
    //aaaaaaa
    //aadaa

    /* did not worked */
//  static long substrCount(int n, String s) {
//    int k = 1;
//    int count = 0;
//    for (int i = 0; i < n; i++) {
//      k = i + 1;
//      for (int j = i; j < n; j++) {
//        final String substring = s.substring(i, k );
//        final int length = substring.length();
//        if ((length % 2 == 0) && (isSameChar.apply(substring))) {
//          count ++;
//        } else {
//          if (isPalindromNumber.apply(substring)) {
//            count ++;
//          }
//        }
//        k++;
//      }
//    }
//    return count;
//  }


    static class Point {
        public char key;
        public long count;

        public Point(char x, long y) {
            key = x;
            count = y;
        }
    }
    //mnonopoo
    static long substrCount(int n, String s) {
        s = s + " ";
        ArrayList<Point> l = new ArrayList<Point>();
        long count = 1;
        char ch = s.charAt(0);
        for (int i = 1; i <= n; i++) {
            if (ch == s.charAt(i))
                count++;
            else {
                l.add(new Point(ch, count));
                count = 1;
                ch = s.charAt(i);
            }
        }
        count = 0;
        if (l.size() >= 3) {
            Iterator<Point> itr = l.iterator();
            Point prev, curr, next;
            curr = (Point) itr.next();
            next = (Point) itr.next();
            count = (curr.count * (curr.count + 1)) / 2;
            for (int i = 1; i < l.size() - 1; i++) {
                prev = curr;
                curr = next;
                next = itr.next();
                count += (curr.count * (curr.count + 1)) / 2;
                if (prev.key == next.key && curr.count == 1)
                    count += Math.min(prev.count, next.count);
            }
            count += (next.count * (next.count + 1)) / 2;
        } else {
            for (Point curr : l) {
                count += (curr.count * (curr.count + 1)) / 2;
            }
        }
        return count;
    }


    @Test
    void sample() {
        String input = "mnonopoo";
//        input = "asasd";
//        input = "abcbaba";
        System.out.println(substrCount(input.length(), input));
    }
}
