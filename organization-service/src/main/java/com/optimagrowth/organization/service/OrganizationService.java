package com.optimagrowth.organization.service;

import java.util.Optional;
import java.util.UUID;

import com.optimagrowth.organization.message.producer.OrgChangeMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;

    private final OrgChangeMessageProducer orgMessageProducer;

    public Organization findById(String organizationId) {
        Optional<Organization> opt = repository.findById(organizationId);
        orgMessageProducer.sendMessage("READ", organizationId);
        return opt.orElse(null);
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        orgMessageProducer.sendMessage("SAVE", organization.getId());
        return organization;

    }

    public void update(Organization organization) {
        repository.save(organization);
        orgMessageProducer.sendMessage("UPDATE", organization.getId());
    }

    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
        orgMessageProducer.sendMessage("DELETE", organization.getId());
    }

}