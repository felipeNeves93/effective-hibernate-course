package com.effectivehibernate.sections.projections.assessment.repository;

import com.effectivehibernate.sections.projections.assessment.domain.ProductProjectionDTO;
import com.effectivehibernate.sections.projections.assessment.domain.ProductProjections;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;

public interface ProductProjectionsRepository extends Repository<ProductProjections, Integer> {

    @Query("SELECT new com.effectivehibernate.sections.projections.assessment.domain.ProductProjectionDTO(p.name) FROM ProductProjections p")
    Collection<ProductProjectionDTO> findAll();
}
