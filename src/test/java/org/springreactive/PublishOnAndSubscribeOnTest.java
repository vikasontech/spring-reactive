package org.springreactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.sql.Timestamp;

public class PublishOnAndSubscribeOnTest {

  @Test
  void test_publish_on() {

    final Scheduler scheduler = Schedulers.newParallel("parallel-scheduler", 4);
    final Flux<Sample> myFlux = Flux.range(1, 10)
        .map(i -> { final Sample sample = new Sample();
          sample.setI(i);
          return sample;
        })
        .publishOn(scheduler)
        .map(sample -> {
          sample.setI(sample.getI() * 2);
          return sample;
        });

    myFlux
        .subscribeOn(scheduler)
        .subscribe(it -> print(it.toString()));

    final Flux<Sample> map = Flux.range(1, 2)
        .map(i -> {
          final Sample sample = new Sample();
          sample.setI(i);
          return sample;
        });
    try {
      Thread.sleep(1000*2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    print("I am in the main");
  }

  private void print(String message) {
       System.out.println(
        Thread.currentThread().getName()
        + " " +
        new Timestamp(System.currentTimeMillis()) +
        "=> " +
        message);
  }
}


class Sample {
  int i;

  @Override
  public String toString() {
    return "Sample{" +
        "i=" + i +
        '}';
  }

  public int getI() {
    return i;
  }

  public void setI(int i) {
    this.i = i;
  }
}
