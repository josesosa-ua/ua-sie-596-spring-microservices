package com.optimagrowth.license.service;

import java.util.*;
import java.util.concurrent.TimeoutException;

import com.optimagrowth.license.util.UserContextHolder;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.repository.LicenseRepository;
import com.optimagrowth.license.service.client.OrganizationFeignClient;
import org.springframework.web.client.ResourceAccessException;

@Service
@Slf4j
public class LicenseService {

    private final MessageSource messages;

    private final LicenseRepository licenseRepository;

    private final ServiceConfig config;

    private final OrganizationFeignClient organizationFeignClient;

    public LicenseService(LicenseRepository licenseRepository, OrganizationFeignClient organizationFeignClient,
            ServiceConfig serviceConfig, @Qualifier("ostockMessageSource") MessageSource messages) {
        this.licenseRepository = licenseRepository;
        this.organizationFeignClient = organizationFeignClient;
        this.config = serviceConfig;
        this.messages = messages;
    }

    public License getLicense(String licenseId, String organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (null == license) {
            throw new IllegalArgumentException(String
                .format(messages.getMessage("license.search.error.message", null, null), licenseId, organizationId));
        }

        Organization organization = retrieveOrganizationInfo(organizationId);
        if (null != organization) {
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactEmail());
            license.setContactPhone(organization.getContactPhone());
        }

        return license.withComment(config.getProperty());
    }

    private Organization retrieveOrganizationInfo(String organizationId) {
        return organizationFeignClient.getOrganization(organizationId);
    }

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);

        return license.withComment(config.getProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);

        return license.withComment(config.getProperty());
    }

    public String deleteLicense(String licenseId) {
        String responseMessage = null;
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        responseMessage = String.format(messages.getMessage("license.delete.message", null, null), licenseId);
        return responseMessage;

    }

    @CircuitBreaker(name = "licenseServiceBreaker", fallbackMethod = "customFallbackLicenseList")
    @RateLimiter(name = "licenseServiceRateLimiter", fallbackMethod = "customFallbackLicenseList")
    @Retry(name = "licenseServiceRetry", fallbackMethod = "customFallbackLicenseList")
    @Bulkhead(name = "licenseServiceBulkhead", fallbackMethod = "customFallbackLicenseList")
    public List<License> getLicensesByOrganization(String organizationId)
            throws TimeoutException, ConcurrentModificationException, ResourceAccessException {
        log.debug("getLicensesByOrganization Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    private List<License> customFallbackLicenseList(String organizationId, Throwable t) {
        log.debug("Fallback triggered by: {}", t.getClass().getSimpleName());
        var fallbackList = new ArrayList<License>();
        var license = new License();
        license.setLicenseId("0000000-00-0000");
        license.setOrganizationId(organizationId);
        license.setProductName("Fallback: " + t.getMessage());
        fallbackList.add(license);
        return fallbackList;
    }

    private static final Random RANDOM = new Random();

    private void randomlyRunLong() throws TimeoutException {
        int randomNum = RANDOM.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3) {
            sleep();
        }
    }

    private void sleep() throws TimeoutException {
        try {
            log.debug("Sleep");
            Thread.sleep(5000);
            throw new TimeoutException();
        }
        catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
