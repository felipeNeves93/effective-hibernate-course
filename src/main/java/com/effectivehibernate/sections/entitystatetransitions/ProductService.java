package com.effectivehibernate.sections.entitystatetransitions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ProductEntity find(Integer id) {
        return entityManager.find(ProductEntity.class, id);
    }

    // Example of a non-performatic method to update the value of an entity where she is
    // in its detached state
//    @Transactional
//    public void decrementStock(ProductEntity product) {
//        product.setStock(product.getStock() - 1);

    // The merge operation tells hibernate that he needs to ensure tha the current
    // state of the entity should be the same in the database
    // To do this, hibernate does something called 'dirty check', where it loads an snapshot
    // of the entity from the database, hence, doing an extra select, to compare the properties
    // of the snapshot entity with the current one that we want to update.
    // After detecting any change, hibernate will execute an UPDATE statement
//        entityManager.merge(product);
//    }

    // Correct example on how to deal with the update of an entity keeping the
    // managed state because the search and update of the product happens within
    // the boundaries of the current transaction
    @Transactional
    public void decrementStock(Integer id) {
        var product = entityManager.find(ProductEntity.class, id);
        product.setStock(product.getStock() - 1);
    }

    // When inserting in the database, if you utilize the merge() method,
    // you are going to do an extra select statement for each insert operation,
    // and thinking about the scenary of many rows, its not an cheap operation.
//    @Transactional
//    public void create(Integer id, Integer stock) {
//        var product = new ProductEntity(id, stock);
//        entityManager.merge(product);
//    }

    // The correct way to insert using entity manager, is to call the persist() method
    @Transactional
    public void create(Integer id, Integer stock, String name) {
        var product = new ProductEntity(id, stock, name);
        entityManager.persist(product);
    }

}
