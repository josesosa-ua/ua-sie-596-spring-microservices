package com.optimagrowth.license.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
public class Organization {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(
      description = "Auto-generated ID. Do not include in POST request.",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Category category;

  @ManyToOne
  @JoinColumn(name = "president_id")
  private Person president;

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "person_organization",
      joinColumns = @JoinColumn(name = "organization_id"),
      inverseJoinColumns = @JoinColumn(name = "person_id"))
  private List<Person> members;

  private String name;
  private LocalDate establishedDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public LocalDate getEstablishedDate() {
    return establishedDate;
  }

  public void setEstablishedDate(LocalDate establishedDate) {
    this.establishedDate = establishedDate;
  }

  public Person getPresident() {
    return president;
  }

  public void setPresident(Person president) {
    this.president = president;
  }

  public List<Person> getMembers() {
    return members;
  }

  public void setMembers(List<Person> members) {
    this.members = members;
  }

  @Override
  public String toString() {
    return "Organization["
        + "id="
        + id
        + ", name='"
        + name
        + ", category="
        + category
        + ", president="
        + president.getName()
        + ", number of members="
        + getMembersCount()
        + ", members="
        + getMembersNames()
        + ", establishedDate="
        + establishedDate
        + "]";
  }

  private int getMembersCount() {
    return Optional.ofNullable(members).map(List::size).orElse(0);
  }

  private String getMembersNames() {
    return Optional.ofNullable(members)
        .map(m -> m.stream().map(Person::getName).collect(Collectors.joining(", ")))
        .filter(s -> !s.isEmpty())
        .orElse("None");
  }
}
