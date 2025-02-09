package com.optimagrowth.licence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.optimagrowth.licence.models.License;

public interface LicenseRepository extends JpaRepository<License, Integer> {
  Optional<License> findByOrganizationIdAndLicenseId(String organizationId, String licenseId);

  void deleteByLicenseId(String licenseId);
}
