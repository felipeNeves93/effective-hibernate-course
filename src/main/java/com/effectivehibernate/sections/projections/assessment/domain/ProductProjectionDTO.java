package com.effectivehibernate.sections.projections.assessment.domain;

public class ProductProjectionDTO {
    private String name;

    public ProductProjectionDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
