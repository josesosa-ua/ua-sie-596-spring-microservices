package com.optimagrowth.licence.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LicenseResponse {
  private String message;
  private License license;

  private LicenseResponse(String message, License license) {
    this.message = message;
    this.license = license;
  }

  public static LicenseResponse of(String message, License license) {
    return new LicenseResponse(message, license);
  }
}
