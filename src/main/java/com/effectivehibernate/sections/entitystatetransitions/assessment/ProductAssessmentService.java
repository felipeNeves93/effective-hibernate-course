package com.effectivehibernate.sections.entitystatetransitions.assessment;

import com.effectivehibernate.sections.entitystatetransitions.ProductEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
public class ProductAssessmentService {

    private final ProductAssessmentRepository productAssessmentRepository;

    public ProductAssessmentService(ProductAssessmentRepository productAssessmentRepository) {
        this.productAssessmentRepository = productAssessmentRepository;
    }

    public ProductEntity find(Integer id) {
        return productAssessmentRepository.find(id);
    }

    @Transactional
    public void buy(Integer id, Integer amountToBuy) {
        var product = find(id);
        product.setStock(product.getStock() - amountToBuy);
        productAssessmentRepository.save(product);
    }

    @Transactional
    public Integer create(String productName, Integer initialStock) {
        var id = new Random().nextInt();
        var product = new ProductEntity(id, initialStock, productName);
        productAssessmentRepository.persist(product);

        return id;
    }

    public void saveInitialProduct(ProductEntity productEntity) {
        productAssessmentRepository.persist(productEntity);
    }
}
