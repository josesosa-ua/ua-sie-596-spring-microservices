package com.optimagrowth.license.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "ostock")
@Component
@Data
public class OstockProperties {

    private String clientName;

}
