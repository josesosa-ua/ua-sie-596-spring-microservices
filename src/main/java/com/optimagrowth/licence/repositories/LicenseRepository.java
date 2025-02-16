package com.optimagrowth.licence.repositories;

import com.optimagrowth.licence.models.License;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LicenseRepository extends MongoRepository<License, ObjectId> {
  Optional<License> findByOrganizationIdAndLicenseId(String organizationId, String licenseId);

  void deleteByLicenseId(String licenseId);
}
