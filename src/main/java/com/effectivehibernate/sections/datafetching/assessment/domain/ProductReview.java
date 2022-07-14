package com.effectivehibernate.sections.datafetching.assessment.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_Reviews")
public class ProductReview {

    @Id
    private Integer id;
    private Integer rating;

    @ManyToOne
    private ProductDataFetching product;

    public ProductReview() {

    }

    public ProductReview(Integer id, ProductDataFetching product, Integer rating) {
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

    public ProductDataFetching getProduct() {
        return product;
    }

    public void setProduct(ProductDataFetching product) {
        this.product = product;
    }
}
