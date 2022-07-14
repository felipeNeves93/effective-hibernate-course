package com.effectivehibernate.sections.entitystatetransitions.assessment;

import com.effectivehibernate.sections.entitystatetransitions.ProductEntity;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EntityTransitionAssessmentTests {

    @Autowired
    private ProductAssessmentService productAssessmentService;

    /**
     * This test case emulates that a product has been bought by a customer and the necessary state
     * modifications have been made in the database.
     */
    @Test
    void testBuyingProductByIdWorks() {
        var id = this.insertTestProduct();

        // clear up query counts for verification
        QueryCountHolder.clear();
        // calculate the expected amount in advance
        ProductEntity tShirtProduct = productAssessmentService.find(id);
        int stockToBuy = 5;
        int expectedStock = tShirtProduct.getStock() - stockToBuy;

        // buying 5 of product
        productAssessmentService.buy(id, stockToBuy);

        // verify that the stock equals to the expected amount
        int stock = productAssessmentService.find(id).getStock();
        assertEquals(expectedStock, stock);
        // verify that there were 3 select and 1 update statement issued
        assertEquals(3, QueryCountHolder.getGrandTotal().getSelect());
        assertEquals(1, QueryCountHolder.getGrandTotal().getUpdate());
    }

    /**
     * The test case verifies that a new {@link ProductEntity} entity is properly saved to the database
     * with the least amount of queries
     */
    @Test
    void testCreateProductWorks() {
        // clear up query counts for verification
        QueryCountHolder.clear();
        String productName = "Red Shirt";
        int initialStock = 4;

        // creating a new product
        int productId = productAssessmentService.create(productName, initialStock);

        // verify that product is saved
        ProductEntity newProduct = productAssessmentService.find(productId);
        assertEquals(productName, newProduct.getName());
        assertEquals(initialStock, newProduct.getStock());
        // verify that there were 1 select and 1 insert statement issued
        assertEquals(1, QueryCountHolder.getGrandTotal().getSelect());
        assertEquals(1, QueryCountHolder.getGrandTotal().getInsert());
    }

    private Integer insertTestProduct() {
        var id = new Random().nextInt();
        var product = new ProductEntity(id, 10, "T-Shirt");
        productAssessmentService.saveInitialProduct(product);

        return id;
    }

}
