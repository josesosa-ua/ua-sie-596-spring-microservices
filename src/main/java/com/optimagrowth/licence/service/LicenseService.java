package com.optimagrowth.licence.service;

import java.util.Locale;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.licence.model.License;

@Service
public class LicenseService {

  @Autowired private MessageSource messages;

  public License getLicense(String organizationId, String licenseId) {
    var license = new License();
    license.setId(new Random().nextInt(1000));
    license.setLicenseId(licenseId);
    license.setOrganizationId(organizationId);
    license.setProductName("Ostock");
    license.setDescription("Software product");
    license.setLicenseType("Full");
    return license;
  }

  public String createLicense(License license, String organizationId, Locale locale) {
    return Optional.ofNullable(license)
        .map(
            l ->
                String.format(
                    messages.getMessage("license.create.message", null, locale),
                    organizationId,
                    l.toString()))
        .orElse("");
  }

  public String updateLicense(License license, String organizationId, Locale locale) {
    return Optional.ofNullable(license)
        .map(
            l ->
                String.format(
                    messages.getMessage("license.update.message", null, locale),
                    organizationId,
                    l.toString()))
        .orElse("");
  }

  public String deleteLicense(String licenseId, String organizationId, Locale locale) {
    return String.format(
        messages.getMessage("license.delete.message", null, locale), licenseId, organizationId);
  }
}
