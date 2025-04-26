package com.optimagrowth.license.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class Organization extends RepresentationModel<Organization> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String contactName;

    private String contactEmail;

    private String contactPhone;

}
