package com.optimagrowth.license.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrganizationRepositoryTests {

  @Autowired private OrganizationRepository organizationRepository;

  @Test
  public void testPrintOrganizationsReport() {
    var organizations = organizationRepository.findAll();
    System.out.println(
        "================================================================================");
    System.out.println("Organizations:");
    organizations.forEach(System.out::println);
    System.out.println(
        "================================================================================");
  }
}
