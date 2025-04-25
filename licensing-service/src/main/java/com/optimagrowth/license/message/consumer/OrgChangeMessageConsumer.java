package com.optimagrowth.license.message.consumer;

import com.optimagrowth.license.message.model.OrgChangeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class OrgChangeMessageConsumer {

    @Bean
    public Consumer<OrgChangeModel> orgChange() {
        return orgChangeModel -> {
            log.debug("Received message: {}", orgChangeModel);
            // Here you can add logic to handle the received message
            // For example, you might want to update the license information based on the
            // organization change
        };
    }

}
