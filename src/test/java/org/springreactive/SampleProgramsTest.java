package org.springreactive;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springreactive.SamplePrograms.Person;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SampleProgramsTest {


  final List<SamplePrograms.Person> peoples =
      Arrays.asList(
          SamplePrograms.Person.builder().age(20).name("sam").build(),
          SamplePrograms.Person.builder().age(35).build(),
          SamplePrograms.Person.builder().age(25).name("ved").build(),
          SamplePrograms.Person.builder().age(18).name("vik").build(),
          SamplePrograms.Person.builder().age(45).name("ong").build());

  /* functional way using streams */
  public void ageGreaterThanTake3(int age) {

    final Flux<String> map = Flux.fromIterable(peoples)
        .filter(it -> it.getAge() >= age)
        .checkpoint("got the data")
        .map(SamplePrograms.Person::getName)
        .checkpoint("got the name")
        .map(String::toUpperCase)
        .map(String::toLowerCase);

    Consumer<Throwable> errorHandler = e -> System.err.println(
        "ERROR: " + e.getMessage());

    final Runnable completer = () -> System.out.println("Done");

    map.subscribe(System.out::println, errorHandler, completer);

  }

  /* doesn't want max age */
  private void ageGreaterThan(int age) {
    if (peoples.isEmpty()) {
      System.out.println("no data found");
    }

    String name = null;
    for (Person p : peoples) {
      if (p.getAge() >= age) {
        name = p.getName();
        break;
      }
    }

    if (name != null) {
      System.out.println("Name of the Person is:  " + name);
    }
  }

  /* doesn't want max age */
  /* declarative way using streams */
  private void ageGreaterThanTake2(int age) {

   final String name = peoples.stream()
        .filter(it -> it.getAge() >= age)
        .map(Person::getName)
        .findFirst()
        .orElse("No data found");

    System.out.println(name);
  }


  @Test
  @Order(1)
  void testOlderFinder_Imperative() {
    ageGreaterThanTake2(32);
  }
  @Test
  @Order(2)
  void testOlderFinder_ImperativeWithStrams() {
    ageGreaterThanTake2(32);
  }

  @Test
  @Order(3)
  void testOlderFinder_Reactive() {
      ageGreaterThanTake3(32);
  }
}