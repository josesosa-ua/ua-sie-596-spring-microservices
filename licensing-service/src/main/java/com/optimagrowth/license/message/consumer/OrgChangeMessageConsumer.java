package com.optimagrowth.license.message.consumer;

import com.optimagrowth.license.message.model.OrgChangeModel;
import com.optimagrowth.license.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class OrgChangeMessageConsumer {

    private final OrganizationService organizationService;

    @Bean
    public Consumer<OrgChangeModel> orgChange() {
        return orgChangeModel -> {
            log.debug("Received message: {}", orgChangeModel);
            if (orgChangeModel.getAction().equalsIgnoreCase("READ")) {
                log.debug("Ignoring message of type: {} on action: {}", orgChangeModel.getType(),
                        orgChangeModel.getAction());
            }
            else {
                log.debug("Invalidating cache for Organization ID: {} on action: {}",
                        orgChangeModel.getOrganizationId(), orgChangeModel.getAction());
                organizationService.evictOrganizationCache(orgChangeModel.getOrganizationId());
            }
        };
    }

}
