package org.springreactive;


import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;

import static java.time.Duration.ofSeconds;

public class BackpressureTest {
  private static final Logger log = LoggerFactory.getLogger(BackpressureTest.class);


  void backPressureExample_usingBaseSubscriber() throws IOException, InterruptedException {

    final BaseSubscriber<Integer> baseSubscriber = new BaseSubscriber<Integer>() {
      @Override
      protected void hookOnSubscribe(Subscription subscription) {
        request(5);
      }

      @Override
      protected void hookOnNext(Integer value) {
        log.info("Next Value ===> {}", value);
        if(value >= 5) {
          log.error("cancelling the subscription after receiving: => {} ",value);
          cancel();
        }
      }
    };

    Flux.range(1, 10)
        .log()
        .flatMap(it -> Mono.just(it).delayElement(ofSeconds(2)),  100, 2)
        .log()
        .subscribe(baseSubscriber);

    log.info("hello");

  }

  @Test
  void backPressureExample() throws IOException, InterruptedException {

    final Disposable subscribe = Flux.range(1, 10)
        .log()
        .flatMap(it -> Mono.just(it).delayElement(ofSeconds(2)),  4, 2)
        .log()
        .subscribe(it -> log.info(it.toString()));


    log.info("hello");

    waitUntilDisposed(subscribe);

  }

  private void waitUntilDisposed(Disposable subscribe) throws InterruptedException {
    while(!subscribe.isDisposed()) {
      Thread.sleep(ofSeconds(3).toMillis() );
    }
  }
}
