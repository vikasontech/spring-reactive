package hackerrank;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;

/*
You are given an array and you need to find number of tripets of indices  such that the elements at those indices are in geometric progression for a given common ratio  and .


https://www.hackerrank.com/challenges/count-triplets-1/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps

6 3
1 3 9 9 27 81

6

 */
class CountTriplet {

  // Complete the countTriplets function below.
  static long countTriplets(List<Long> arr, long r) {
    Map<Long, Integer> before = new HashMap<>();
    Map<Long, Integer> after = new HashMap<>();
    //initialize after
    for (Long aLong : arr) {
      if (after.containsKey(aLong)) {
        after.compute(aLong,
            (a, b) -> Objects.isNull(b) ? 1 : b + 1);
      } else
        after.put(aLong, 1);
    }

    long pairCount = 0l;
    for (int i = 0; i < arr.size(); i++) {
      long v = arr.get(i);

      //reduce from after map
      after.compute(v, (a, b) -> b - 1 );

      if(before.containsKey(v/r) && v %r ==0  &&
      after.containsKey(v * r)) {
        pairCount += before.get(v/r)
      }

    }


    return 0;
  }


  @Test
  void name() {
    final List<Long> longs = Arrays.asList(1l, 3l, 9l, 27l, 81l);
    countTriplets(longs, 3);
  }


  @Test
  void testMap() {

    final Map<String, String> map = new HashMap<>();
    map.put("key", "hello");
    map.compute("key", (a, b) -> Objects.isNull(b)? "is null": b + " value from map") ;
    System.out.println(map);

  }
}
