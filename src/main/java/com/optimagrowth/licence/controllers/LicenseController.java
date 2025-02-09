package com.optimagrowth.licence.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
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

import com.optimagrowth.licence.models.License;
import com.optimagrowth.licence.models.LicenseResponse;
import com.optimagrowth.licence.services.LicenseService;

@RestController
@RequestMapping(value = "/v1/organization/{organizationId}/license")
public class LicenseController {

  @Autowired private LicenseService licenseService;

  @GetMapping("/{licenseId}")
  public ResponseEntity<LicenseResponse> getLicense(
      @PathVariable String organizationId,
      @PathVariable String licenseId,
      @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
    return licenseService
        .getLicense(organizationId, licenseId, locale)
        .map(
            licenseResponse -> {
              addHateoasTo(licenseResponse.getLicense());
              return ResponseEntity.ok(licenseResponse);
            })
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<LicenseResponse> createLicense(
      @PathVariable String organizationId,
      @RequestBody License licenseRequest,
      @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
    if (licenseService
        .getLicense(organizationId, licenseRequest.getLicenseId(), locale)
        .isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    var licenseResponse = licenseService.createLicense(licenseRequest, organizationId, locale);
    addHateoasTo(licenseResponse.getLicense());
    return ResponseEntity.ok(licenseResponse);
  }

  @PutMapping
  public ResponseEntity<LicenseResponse> updateLicense(
      @PathVariable String organizationId,
      @RequestBody License licenseRequest,
      @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
    var licenseResponse = licenseService.updateLicense(licenseRequest, organizationId, locale);
    addHateoasTo(licenseResponse.getLicense());
    return ResponseEntity.ok(licenseResponse);
  }

  @DeleteMapping("/{licenseId}")
  public ResponseEntity<String> deleteLicense(
      @PathVariable String organizationId,
      @PathVariable String licenseId,
      @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
    var licenseResponse = licenseService.deleteLicense(organizationId, licenseId, locale);
    return ResponseEntity.ok(licenseResponse);
  }

  private void addHateoasTo(License licence) {
    licence.add(
        linkTo(
                methodOn(LicenseController.class)
                    .getLicense(licence.getOrganizationId(), licence.getLicenseId(), null))
            .withSelfRel(),
        linkTo(
                methodOn(LicenseController.class)
                    .createLicense(licence.getOrganizationId(), licence, null))
            .withRel("createLicense"),
        linkTo(
                methodOn(LicenseController.class)
                    .updateLicense(licence.getOrganizationId(), licence, null))
            .withRel("updateLicense"),
        linkTo(
                methodOn(LicenseController.class)
                    .deleteLicense(licence.getOrganizationId(), licence.getLicenseId(), null))
            .withRel("deleteLicense"));
  }
}
