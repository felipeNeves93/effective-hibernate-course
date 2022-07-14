package com.effectivehibernate.sections.concurrencycontrol.assessment.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "optimistic_distinct_products")
public class OptimisticDistinctProduct {

    @Id
    private Integer id;
    private String name;
    private Integer stock;

    public OptimisticDistinctProduct() {

    }

    public OptimisticDistinctProduct(Integer id, String name, Integer stock) {
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
}
