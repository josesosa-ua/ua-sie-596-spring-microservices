package com.optimagrowth.organization.message.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrgChangeModel {

    private String type;

    private String action;

    private String organizationId;

    private String correlationId;

}
