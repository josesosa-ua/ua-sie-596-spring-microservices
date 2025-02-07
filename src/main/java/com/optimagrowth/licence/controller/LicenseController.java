package com.optimagrowth.licence.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.licence.model.License;
import com.optimagrowth.licence.service.LicenseService;

@RestController
@RequestMapping(value = "/v1/organization/{organizationId}/license")
public class LicenseController {

  @Autowired private LicenseService licenseService;

  @GetMapping("/{licenseId}")
  public ResponseEntity<License> getLicense(
      @PathVariable("organizationId") String organizationId,
      @PathVariable("licenseId") String licenseId) {
    return ResponseEntity.ok(licenseService.getLicense(organizationId, licenseId));
  }

  @PostMapping
  public ResponseEntity<String> createLicense(
      @PathVariable String organizationId,
      @RequestBody License licenseRequest,
      @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
    return ResponseEntity.ok(licenseService.createLicense(licenseRequest, organizationId, locale));
  }

  @PutMapping
  public ResponseEntity<String> updateLicense(
      @PathVariable String organizationId,
      @RequestBody License licenseRequest,
      @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
    return ResponseEntity.ok(licenseService.updateLicense(licenseRequest, organizationId, locale));
  }

  @DeleteMapping("/{licenseId}")
  public ResponseEntity<String> deleteLicense(
      @PathVariable String organizationId,
      @PathVariable String licenseId,
      @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
    return ResponseEntity.ok(licenseService.deleteLicense(organizationId, licenseId, locale));
  }
}
