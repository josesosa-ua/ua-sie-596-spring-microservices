package com.optimagrowth.organization.event;

import com.optimagrowth.organization.event.model.OrganizationChangeModel;
import com.optimagrowth.organization.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaSource {

    private final StreamBridge streamBridge;

    public KafkaSource(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishOrganizationChange(String action, String organizationId) {
        log.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);
        streamBridge.send("orgChange-out-0", new OrganizationChangeModel(OrganizationChangeModel.class.getTypeName(),
                action, organizationId, UserContext.getCorrelationId()));
    }

}
