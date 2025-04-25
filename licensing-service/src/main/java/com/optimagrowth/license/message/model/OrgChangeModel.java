package com.optimagrowth.license.message.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
