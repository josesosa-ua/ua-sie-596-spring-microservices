package com.optimagrowth.organization.event.source;

import com.optimagrowth.organization.event.model.OrganizationChangeModel;
import com.optimagrowth.organization.util.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleSourceBean {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    private final StreamBridge streamBridge;

    public SimpleSourceBean(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishOrganizationChange(String action, String organizationId) {
        logger.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);
        OrganizationChangeModel change = new OrganizationChangeModel(OrganizationChangeModel.class.getTypeName(),
                action, organizationId, UserContext.getCorrelationId());

        streamBridge.send("organizationChanges-output", MessageBuilder.withPayload(change).build());
    }

}