package com.optimagrowth.license.model.exception;

public class OrganizationValidationException extends IllegalStateException {

  public OrganizationValidationException(String message) {
    super(message);
  }

  public OrganizationValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public static OrganizationValidationException missingMembers() {
    return new OrganizationValidationException("An organization must have at least one member.");
  }
}
