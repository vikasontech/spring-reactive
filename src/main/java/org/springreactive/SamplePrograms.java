package org.springreactive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Service
public class SamplePrograms {

  final List<Person> peoples =
      Arrays.asList(
          Person.builder().age(20).name("sam").build(),
          Person.builder().age(35).build(),
          Person.builder().age(25).name("ved").build(),
          Person.builder().age(18).name("vik").build(),
          Person.builder().age(45).name("ong").build());
  /* functional way using streams */

  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class Person {
    private String name;
    private int age;

    public int getAge() {
      System.out.println("Evaluating  age for : " + name);
      return age;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setAge(int age) {
      this.age = age;
    }
  }



}
