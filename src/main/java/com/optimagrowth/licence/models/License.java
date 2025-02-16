package com.optimagrowth.licence.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
public class License extends RepresentationModel<License> {

  @Id
  @Schema(
      description = "Auto-generated ID of the license",
      accessMode = Schema.AccessMode.READ_ONLY)
  private ObjectId id;

  private String licenseId;
  private String description;
  private String organizationId;
  private String productName;
  private String licenseType;
}
