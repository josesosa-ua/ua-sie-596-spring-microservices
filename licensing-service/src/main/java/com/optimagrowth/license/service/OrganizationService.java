package com.optimagrowth.license.service;

import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.service.client.OrganizationFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {

    private final OrganizationFeignClient client;

    private final CacheManager cacheManager;

    @Cacheable(value = "organizationCache", key = "#organizationId", condition = "#organizationId != null")
    public Organization getOrganization(String organizationId) {
        log.debug("Fetching organization from remote service with ID: {}", organizationId);
        return client.getOrganization(organizationId);
    }

    public void evictOrganizationCache(String organizationId) {
        var cache = Optional.ofNullable(cacheManager.getCache("organizationCache"));
        if (cache.isPresent()) {
            log.info("Evicting organization cache for ID: {}", organizationId);
            cache.get().evict(organizationId);
        }
        else {
            log.warn("Cache not found");
        }
    }

}
