package com.optimagrowth.license.model.listener;

import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.model.exception.OrganizationValidationException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class OrganizationListener {
  @PrePersist
  public void onSave(Organization organization) {
    if (hasNoMembers(organization)) {
      throw OrganizationValidationException.missingMembers();
    }
  }

  @PreUpdate
  public void onUpdate(Organization organization) {
    if (hasNoMembers(organization)) {
      throw new IllegalStateException("An organization must have at least one member");
    }
  }

  private boolean hasNoMembers(Organization organization) {
    return organization.getMembers() == null || organization.getMembers().isEmpty();
  }
}
