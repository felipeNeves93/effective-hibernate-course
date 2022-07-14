package com.effectivehibernate.sections.concurrencycontrol.assessment.support;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class TransactionalRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void doInTransaction(final Consumer<EntityManager> consumer) {
        consumer.accept(entityManager);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T doInTransaction(final Function<EntityManager, T> function) {
        return function.apply(entityManager);
    }
}
