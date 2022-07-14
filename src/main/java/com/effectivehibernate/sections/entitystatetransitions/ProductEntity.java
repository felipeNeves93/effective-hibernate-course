package com.effectivehibernate.sections.entitystatetransitions;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    private Integer id;
    private Integer stock;
    private String name;

    public ProductEntity() {
    }

    public ProductEntity(Integer id, Integer stock) {
        this.id = id;
        this.stock = stock;
    }

    public ProductEntity(Integer id, Integer stock, String name) {
        this.id = id;
        this.stock = stock;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
