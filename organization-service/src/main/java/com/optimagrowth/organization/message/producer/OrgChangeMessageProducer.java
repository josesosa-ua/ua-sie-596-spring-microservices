package com.optimagrowth.organization.message.producer;

import com.optimagrowth.organization.message.model.OrgChangeModel;
import com.optimagrowth.organization.util.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrgChangeMessageProducer {

    private final StreamBridge streamBridge;

    public void sendMessage(String action, String organizationId) {
        var organizationChanged = new OrgChangeModel(OrgChangeModel.class.getTypeName(), action, organizationId,
                UserContext.getCorrelationId());
        log.debug("OrgChangeMessageProducer sending message: {}", organizationChanged);
        streamBridge.send("orgChange-out-0", organizationChanged);
    }

}
