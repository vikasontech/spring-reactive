package org.springreactive;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UtilityTests {

  @Test
  void StepVerifierTest() {

    final List<String> strings = Arrays.asList("foo", "bar", "draco" );
    final Flux<String> map = Flux.fromIterable(strings)
        .map(String::toUpperCase)
//        .map(Integer::parseInt)
        ;

//    StepVerifier.create(map)
//        .expectSubscription()
//        .verifyErrorMessage("For input string: \"FOO\"");

    StepVerifier.create(map)
        .expectSubscription()
//        .expectNext("FOO")
//        .expectNext("BAR")
        .expectNextCount(3)
        .expectComplete()
        .verify();
  }

  @Test
  void hotSubscriber() {
    Sinks.Many<String> hotSource = Sinks.unsafe().many().multicast().directBestEffort();

    Flux<String> hotFlux = hotSource.asFlux().map(String::toUpperCase);

    hotFlux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: " + d));

    hotSource.emitNext("blue", FAIL_FAST);
    hotSource.tryEmitNext("green").orThrow();

    hotFlux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: " + d));

    hotSource.emitNext("orange", FAIL_FAST);
    hotSource.emitNext("purple", FAIL_FAST);
    hotSource.emitComplete(FAIL_FAST);
  }

  @Test
  void coldSubscriber() {

    Flux<String> source = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
        .map(String::toUpperCase);

    source.subscribe(d -> System.out.println("Subscriber 1: " + d));
    source.subscribe(d -> System.out.println("Subscriber 2: " + d));

  }

  @Test
  void transformExample() {
    Function<Flux<String>, Flux<String>> filterAndMap =
        f -> f.filter(color -> !color.equals("orange"))
            .map(String::toUpperCase);

    Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
        .doOnNext(it -> System.out.println("on next " + it))
        .transform(filterAndMap)
        .subscribe(d -> System.out.println("Subscriber to Transformed MapAndFilter: " + d));

  }

  final List<SamplePrograms.Person> peoples =
      Arrays.asList(
          SamplePrograms.Person.builder().age(20).name("sam").build(),
          SamplePrograms.Person.builder().age(35).build(),
          SamplePrograms.Person.builder().age(25).name("ved").build(),
          SamplePrograms.Person.builder().age(18).name("vik").build(),
          SamplePrograms.Person.builder().age(45).name("ong").build());

}