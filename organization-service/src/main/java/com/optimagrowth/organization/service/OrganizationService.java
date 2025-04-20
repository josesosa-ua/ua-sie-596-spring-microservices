package com.optimagrowth.organization.service;

import java.util.Optional;
import java.util.UUID;

import com.optimagrowth.organization.event.source.SimpleSourceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;

    private final SimpleSourceBean sourceBean;

    public Organization findById(String organizationId) {
        Optional<Organization> opt = repository.findById(organizationId);
        sourceBean.publishOrganizationChange("GET", organizationId);
        return opt.orElse(null);
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        sourceBean.publishOrganizationChange("SAVE", organization.getId());
        return organization;
    }

    public void update(Organization organization) {
        repository.save(organization);
        sourceBean.publishOrganizationChange("UPDATE", organization.getId());
    }

    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
        sourceBean.publishOrganizationChange("DELETE", organization.getId());
    }

}