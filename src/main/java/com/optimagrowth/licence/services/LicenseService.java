package com.optimagrowth.licence.services;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.licence.models.License;
import com.optimagrowth.licence.models.LicenseResponse;
import com.optimagrowth.licence.repositories.LicenseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LicenseService {

  @Autowired private LicenseRepository licenseRepository;
  @Autowired private MessageSource messages;

  public Optional<LicenseResponse> getLicense(
      String organizationId, String licenseId, Locale locale) {
    return licenseRepository
        .findByOrganizationIdAndLicenseId(organizationId, licenseId)
        .map(
            license -> {
              String message =
                  String.format(
                      messages.getMessage("license.get.message", null, locale), license.toString());
              return LicenseResponse.of(message, license);
            });
  }

  public LicenseResponse createLicense(License license, String organizationId, Locale locale) {
    license.setOrganizationId(organizationId);
    License savedLicense = licenseRepository.save(license);
    String message =
        String.format(
            messages.getMessage("license.create.message", null, locale), savedLicense.toString());
    return LicenseResponse.of(message, savedLicense);
  }

  public LicenseResponse updateLicense(License license, String organizationId, Locale locale) {
    license.setOrganizationId(organizationId);
    License updatedLicense = licenseRepository.save(license);
    String message =
        String.format(
            messages.getMessage("license.update.message", null, locale), updatedLicense.toString());
    return LicenseResponse.of(message, updatedLicense);
  }

  public String deleteLicense(String organizationId, String licenseId, Locale locale) {
    licenseRepository.deleteByLicenseId(licenseId);
    return String.format(
        messages.getMessage("license.delete.message", null, locale), licenseId, organizationId);
  }

  public boolean licenseExists(String organizationId, String licenseId) {
    return licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId).isPresent();
  }
}
