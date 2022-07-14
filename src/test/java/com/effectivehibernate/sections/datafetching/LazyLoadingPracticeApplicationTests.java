package com.effectivehibernate.sections.datafetching;

import com.effectivehibernate.sections.datafetching.assessment.domain.ProductDataFetching;
import com.effectivehibernate.sections.datafetching.assessment.domain.ProductReview;
import com.effectivehibernate.sections.datafetching.assessment.service.ProductDataFetchingService;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class LazyLoadingPracticeApplicationTests {

    @Autowired
    private ProductDataFetchingService productService;

    @BeforeAll
    void setupDataForTests() {
        this.insertProductsAndReviewsForTests();
    }

    @Test
    void testLoadingReviewsForProductWorks() {
        // dataset is initialized in main/resources/data.sql
        int productId = 1;
        // Since there are 3 reviews saved to the database for product id 1 in data.sql
        int expectedReviewCount = 3;

        // load the actual reviews through the service
        Collection<ProductReview> reviews = productService.getReviewsForProduct(productId);
        int reviewCount = reviews.size();

        // verify the review count available
        assertEquals(expectedReviewCount, reviewCount);
    }

    @Test
    void testLoadingAverageRatingForProductIsEfficient() {
        // clear up query counts for verification
        QueryCountHolder.clear();
        // dataset is initialized in main/resources/data.sql
        int expectedAverage = 4;
        int productId = 1;

        // calculate the average for the product
        int average = productService.getAverageRatingForProduct(productId);

        // verify that the average lines up with the expected one
        assertEquals(expectedAverage, average);
        // verify that only a single select query is executed
        assertEquals(2, QueryCountHolder.getGrandTotal().getSelect());
    }

    @Test
    void testLoadingOverallAverageRatingIsEfficient() {
        // clear up query counts for verification
        QueryCountHolder.clear();
        // dataset is initialized in main/resources/data.sql
        int expectedAverage = 4;

        // calculate the overall average
        int average = productService.getOverallAverageRating();

        // verify that the average lines up with the expected one
        assertEquals(expectedAverage, average);
        // verify that only a single select query is executed
        assertEquals(2, QueryCountHolder.getGrandTotal().getSelect());
    }

    private void insertProductsAndReviewsForTests() {
        var product1 = new ProductDataFetching(1, "T-Shirt", 10);
        var product2 = new ProductDataFetching(2, "Red Shirt", 5);

        productService.saveProduct(product1);
        productService.saveProduct(product2);

        var productReview1 = new ProductReview(1, product1, 5);
        var productReview2 = new ProductReview(2, product1, 4);
        var productReview3 = new ProductReview(3, product1, 3);

        var productReview4 = new ProductReview(4, product2, 5);
        var productReview5 = new ProductReview(5, product2, 5);

        productService.saveProductReview(productReview1);
        productService.saveProductReview(productReview2);
        productService.saveProductReview(productReview3);
        productService.saveProductReview(productReview4);
        productService.saveProductReview(productReview5);
    }
}


