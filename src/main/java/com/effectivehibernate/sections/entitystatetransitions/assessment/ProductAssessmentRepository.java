package com.effectivehibernate.sections.entitystatetransitions.assessment;

import com.effectivehibernate.sections.entitystatetransitions.ProductEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ProductAssessmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ProductEntity find(Integer id) {
        return entityManager.find(ProductEntity.class, id);
    }

    @Transactional
    public void save(ProductEntity product) {
        entityManager.merge(product);
    }

    @Transactional
    public void persist(ProductEntity product) {
        entityManager.persist(product);
    }
}
