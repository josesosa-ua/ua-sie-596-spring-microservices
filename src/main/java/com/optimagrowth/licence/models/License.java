package com.optimagrowth.licence.models;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class License extends RepresentationModel<License> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(
      description = "Auto-generated ID of the license",
      accessMode = Schema.AccessMode.READ_ONLY)
  private int id;

  private String licenseId;
  private String description;
  private String organizationId;
  private String productName;
  private String licenseType;
}
