package com.effectivehibernate.sections.entitystatetransitions;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;


@SpringBootTest
class StateTransitionApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(StateTransitionApplicationTests.class);

    @Autowired
    private ProductService productService;


    @Test
    void testUpdateMerging() {
        // In this commented case, we have a situation that the performance is worse because of an extra select statement,
        // Where we are first searching for a product in the database, then trying to update the stock value and using
        // entity manager merge method to update it
        // At the end of each transaction, there is a commit/rollback, at the end of the transaction,
        // spring closes the entity manager, in the find case, the entity is in the managed state, after the product
        // is loaded, the entity changes its state to 'detached', hence the changes made to the product to decrease stock wont
        // affect the value inside the database, and its going to remain the same (10), to fix this, a merge() call is needed
//        LOGGER.info("Decrementing the stock value");
//        var product = productService.find(1);
//        productService.decrementStock(product);

        // Correct case with the correct transaction boundary, we are passing the id of the
        // product that we want to update, and the product is searched inside the database, and have its
        // stock value updated inside the same transaction, eliminating the extra select call that we had
        // in the previous situation
        var id = new Random().nextInt();
        productService.create(id, 10, "test");
        LOGGER.info("Decrementing the stock value");
        productService.decrementStock(id);
        LOGGER.info("Loading product again");
        var productAgain = productService.find(id);
        LOGGER.info("Product stock is {}", productAgain.getStock());
    }

    @Test
    void testInsertMerging() {
        productService.create(new Random().nextInt(), 10, "test");
    }

}
