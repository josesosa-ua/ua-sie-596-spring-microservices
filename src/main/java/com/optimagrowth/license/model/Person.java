package com.optimagrowth.license.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(
      description = "Auto-generated ID. Do not include in POST request.",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @OneToMany(mappedBy = "president", fetch = FetchType.EAGER)
  private List<Organization> presidedOrganizations;

  @ManyToMany(
      mappedBy = "members",
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Organization> memberships;

  private String name;
  private String major;
  private String dept;
  private LocalDate dateOfBirth;
  private String phone;
  private String email;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getDept() {
    return dept;
  }

  public void setDept(String dept) {
    this.dept = dept;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public List<Organization> getPresidedOrganizations() {
    return presidedOrganizations;
  }

  public void setPresidedOrganizations(List<Organization> presidedOrganizations) {
    this.presidedOrganizations = presidedOrganizations;
  }

  public List<Organization> getMemberships() {
    return memberships;
  }

  public void setMemberships(List<Organization> memberships) {
    this.memberships = memberships;
  }

  @Override
  public String toString() {
    return "Person [id="
        + id
        + ", name="
        + name
        + ", major="
        + major
        + ", dept="
        + dept
        + ", dateOfBirth="
        + dateOfBirth
        + ", phone="
        + phone
        + ", email="
        + email
        + ", presidedOrganizations="
        + getPresidedOrganizationsNames()
        + ", memberships="
        + getMembershipsNames()
        + "]";
  }

  private String getPresidedOrganizationsNames() {
    return getOrganizationsNames(presidedOrganizations);
  }

  private String getMembershipsNames() {
    return getOrganizationsNames(memberships);
  }

  private String getOrganizationsNames(List<Organization> organizations) {
    return Optional.ofNullable(organizations)
        .map(m -> m.stream().map(Organization::getName).collect(Collectors.joining(", ")))
        .filter(s -> !s.isEmpty())
        .orElse("None");
  }
}
