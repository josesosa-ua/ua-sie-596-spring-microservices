package com.optimagrowth.license.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonRespositoryTests {

  @Autowired private PersonRepository personRepository;

  @Test
  public void testPrintAllPeople() {
    var people = personRepository.findAll();
    System.out.println(
        "================================================================================");
    System.out.println("People:");
    people.forEach(System.out::println);
    System.out.println(
        "================================================================================");
  }
}
