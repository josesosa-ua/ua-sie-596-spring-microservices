package com.optimagrowth.license.service;


import com.optimagrowth.license.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource()
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long>, CrudRepository<Organization, Long> {

    List<Organization> findByName(@Param("name") String name);

}
