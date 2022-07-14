package com.effectivehibernate.sections.datafetching.assessment.service;

import com.effectivehibernate.sections.datafetching.assessment.domain.ProductDataFetching;
import com.effectivehibernate.sections.datafetching.assessment.domain.ProductReview;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class ProductDataFetchingService {

    @PersistenceContext
    private EntityManager entityManager;

    // Initial method that wont work because the session is finished at the end of the method,
    // and the lazy load wont work
//    @Transactional
//    public Collection<ProductReview> getReviewsForProduct(Integer productId) {
//        var product = entityManager.find(ProductDataFetching.class, productId);
//        return product.getReviews();
//    }

    @Transactional
    public Collection<ProductReview> getReviewsForProduct(Integer productId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var criteriaQuery = criteriaBuilder.createQuery(ProductDataFetching.class);
        var root = criteriaQuery.from(ProductDataFetching.class);
        root.fetch("reviews");

        var productIdParameter = criteriaBuilder.parameter(Integer.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("id"), productIdParameter));

        var query = entityManager.createQuery(criteriaQuery);
        query.setParameter(productIdParameter, productId);

        var product = query.getSingleResult();

        return product.getReviews();
    }

    @Transactional
    public Integer getAverageRatingForProduct(Integer productId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var criteriaQuery = criteriaBuilder.createQuery(ProductDataFetching.class);
        var root = criteriaQuery.from(ProductDataFetching.class);
        root.fetch("reviews");

        var productIdParameter = criteriaBuilder.parameter(Integer.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("id"), productIdParameter));

        var query = entityManager.createQuery(criteriaQuery);
        query.setParameter(productIdParameter, productId);

        var product = query.getSingleResult();

        var reviews = product.getReviews();
        var sum = reviews.stream().mapToInt(ProductReview::getRating).sum();

        return (int) ((double) sum / (double) reviews.size());
    }

    @Transactional
    public Integer getOverallAverageRating() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var criteriaQuery = criteriaBuilder.createQuery(ProductDataFetching.class);
        var root = criteriaQuery.from(ProductDataFetching.class);
        root.fetch("reviews");

        var query = entityManager.createQuery(criteriaQuery);

        var products = query.getResultList();

        var sum = 0;
        var countOfReviews = 0;

        for (ProductDataFetching p : products) {
            var reviews = p.getReviews();
            countOfReviews += reviews.size();

            for (ProductReview review : reviews) {
                sum += review.getRating();
            }
        }
        return (int) ((double) sum / (double) countOfReviews);
    }

    @Transactional
    public void saveProduct(ProductDataFetching product) {
        entityManager.persist(product);
    }

    @Transactional
    public void saveProductReview(ProductReview productReview) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var criteriaQuery = criteriaBuilder.createQuery(ProductDataFetching.class);
        var root = criteriaQuery.from(ProductDataFetching.class);
        var productIdParameter = criteriaBuilder.parameter(Integer.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), productIdParameter));

        var query = entityManager.createQuery(criteriaQuery);
        query.setParameter(productIdParameter, productReview.getProduct().getId());

        var product = query.getSingleResult();

        productReview.setProduct(product);

        entityManager.persist(productReview);
    }

}
