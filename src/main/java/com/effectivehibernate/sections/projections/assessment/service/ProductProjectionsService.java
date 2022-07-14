package com.effectivehibernate.sections.projections.assessment.service;

import com.effectivehibernate.sections.projections.assessment.domain.ProductProjectionDTO;
import com.effectivehibernate.sections.projections.assessment.domain.ProductProjections;
import com.effectivehibernate.sections.projections.assessment.repository.ProductProjectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class ProductProjectionsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductProjectionsRepository productProjectionsRepository;

    // Wrong way of loading the names, because of unecessary data loaded
//    @Transactional
//    public Collection<String> getProductNames() {
//        var products = entityManager.createQuery("From ProductProjections", ProductProjections.class).getResultList();
//        return products.stream()
//                .map(ProductProjections::getName)
//                .collect(Collectors.toList());
//    }


    // The correct way using projections
    @Transactional
    public Collection<String> getProductNames() {
        var products = productProjectionsRepository.findAll();
        return products.stream()
                .map(ProductProjectionDTO::getName)
                .toList();
    }

    @Transactional
    public void saveProduct(ProductProjections product) {
        entityManager.persist(product);
    }
}
