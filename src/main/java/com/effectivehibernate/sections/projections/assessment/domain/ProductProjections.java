package com.effectivehibernate.sections.projections.assessment.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "products_projetions")
public class ProductProjections {

    @Id
    private Integer id;
    private String name;
    private Integer stock;

    @OneToMany(mappedBy = "product")
    private List<ProductProjectionsReview> reviews;

    public ProductProjections() {

    }

    public ProductProjections(Integer id, String name, Integer stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<ProductProjectionsReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<ProductProjectionsReview> reviews) {
        this.reviews = reviews;
    }
}
