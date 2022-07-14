package com.effectivehibernate.sections.projections.assessment.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_projections_review")
public class ProductProjectionsReview {

    @Id
    private Integer id;
    private Integer rating;

    @ManyToOne
    private ProductProjections product;

    public ProductProjectionsReview() {

    }

    public ProductProjectionsReview(Integer id, ProductProjections product, Integer rating) {
        this.id = id;
        this.rating = rating;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public ProductProjections getProduct() {
        return product;
    }

    public void setProduct(ProductProjections product) {
        this.product = product;
    }
}
