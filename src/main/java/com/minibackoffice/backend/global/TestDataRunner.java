package com.minibackoffice.backend.global;

import com.minibackoffice.backend.domain.product.Product;
import com.minibackoffice.backend.domain.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// @Component
public class TestDataRunner implements CommandLineRunner {

    private final ProductRepository productRepository;

    public TestDataRunner(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        Product p = new Product("테스트상품", 10000, 10, "SALE", null);
        productRepository.save(p);

        System.out.println("저장 완료! productId=" + p.getId());
        System.out.println("전체 상품 수=" + productRepository.count());
    }
}
