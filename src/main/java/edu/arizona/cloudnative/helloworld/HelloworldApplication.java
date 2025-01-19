package edu.arizona.cloudnative.helloworld;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("hello")
public class HelloworldApplication {

  public static void main(String[] args) {
    SpringApplication.run(HelloworldApplication.class, args);
  }

  @GetMapping("{firstName}")
  public String getHello(
      @PathVariable String firstName,
      @RequestParam String lastName) {
    return sayHelloTo(firstName, lastName);
  }

  @PostMapping
  public String postHello(@RequestBody HelloRequest request) {
      return sayHelloTo(request.getFirstName(), request.getLastName());
  }

  @GetMapping("ping")
  public String getPing(@RequestParam Optional<String> name) {
      return name.isPresent() ? String.format("Pong %s", name.get()) : "Pong";
  }


  private String sayHelloTo(String firstName, String lastName) {
    return String.format("\"message\":\"Hello %s %s\"", firstName, lastName);
  }

}