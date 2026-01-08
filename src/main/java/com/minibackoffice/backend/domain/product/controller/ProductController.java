package com.minibackoffice.backend.domain.product.controller;

import com.minibackoffice.backend.domain.product.Product;
import com.minibackoffice.backend.domain.product.dto.ProductCreateRequest;
import com.minibackoffice.backend.domain.product.dto.ProductResponse;
import com.minibackoffice.backend.domain.product.dto.ProductUpdateRequest;
import com.minibackoffice.backend.domain.product.repository.ProductRepository;
import com.minibackoffice.backend.domain.product.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    // 등록
    @PostMapping
    public Product create(@RequestBody ProductCreateRequest req) {
        
        Product product = new Product(
            req.name,
            req.price,
            req.stockQuantity,
            req.status,
            req.thumbnailUrl
        );

        return productService.save(product);
    }

    // 전체 조회
    @GetMapping
    public List<ProductResponse> findAll() {

        List<Product> products = productService.findAll();

        List<ProductResponse> result = new ArrayList<>();
        for (Product p : products) {
            result.add(new ProductResponse((p)));
        }
        return result;
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {

        Product product = productService.findById(id);

        if(product == null) {
            return ResponseEntity.notFound().build(); // 404 응답
        }

        return ResponseEntity.ok(new ProductResponse(product)); 
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, 
                                                  @RequestBody ProductUpdateRequest req) {
        Product updated = productService.update(id, req);

        if(updated == null) {
            return ResponseEntity.notFound().build(); // 404
        }

        return ResponseEntity.ok(new ProductResponse(updated)); // 200 + DTO
    }

    // 삭제
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        if(!productRepository.existsById(id)) {
            return false;
        }
        productService.delete(id);
        return true;
    }
}
