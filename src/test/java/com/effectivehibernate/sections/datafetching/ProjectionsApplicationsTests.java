package com.effectivehibernate.sections.datafetching;

import com.effectivehibernate.sections.projections.assessment.domain.ProductProjections;
import com.effectivehibernate.sections.projections.assessment.service.ProductProjectionsService;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ProjectionsApplicationsTests {

    @Autowired
    private ProductProjectionsService productService;

    @BeforeAll
    void setupDataForTests() {
        this.insertProductsAndReviewsForTests();
    }

    @Test
    void testGetProductNamesIsOnlyLoadingNecessaryData() {
        // clear up query counts for verification
        QueryCountHolder.clear();
        // dataset is initialized in main/resources/data.sql
        Collection<String> expectedProductNames = Arrays.asList("Red Shirt", "T-Shirt");

        // calculate the overall average
        Collection<String> productNames = productService.getProductNames();

        // verify that the product names are equal with the expected one
        assertThat(productNames).containsExactlyInAnyOrderElementsOf(expectedProductNames);
    }

    private void insertProductsAndReviewsForTests() {
        var product1 = new ProductProjections(1, "T-Shirt", 10);
        var product2 = new ProductProjections(2, "Red Shirt", 5);

        productService.saveProduct(product1);
        productService.saveProduct(product2);
    }
}


