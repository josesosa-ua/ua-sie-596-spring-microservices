package com.optimagrowth.organization.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationChangeModel {

    private String type;

    private String action;

    private String organizationId;

    private String correlationId;

}
