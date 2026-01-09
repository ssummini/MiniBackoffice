package com.minibackoffice.backend.domain.product.controller;

import com.minibackoffice.backend.domain.product.Product;
import com.minibackoffice.backend.domain.product.dto.ProductCreateRequest;
import com.minibackoffice.backend.domain.product.dto.ProductResponse;
import com.minibackoffice.backend.domain.product.dto.ProductUpdateRequest;
import com.minibackoffice.backend.domain.product.repository.ProductRepository;
import com.minibackoffice.backend.domain.product.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 등록 (ADMIN만)
    @PostMapping
    public ResponseEntity<ProductResponse> create(
            HttpServletRequest request,
            @Valid @RequestBody ProductCreateRequest req
    ) {
        requireAdmin(request);

        Product product = new Product(
                req.name,
                req.price,
                req.stockQuantity,
                req.status,
                req.thumbnailUrl
        );

        Product saved = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(saved)); // 201
    }

    // 전체 조회 (누구나)
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {

        List<Product> products = productService.findAll();

        List<ProductResponse> result = new ArrayList<>();
        for (Product p : products) {
            result.add(new ProductResponse(p));
        }

        return ResponseEntity.ok(result); // 200
    }
    
    // 단건 조회 (누구나)
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {

        Product product = productService.findById(id);
        return ResponseEntity.ok(new ProductResponse(product)); // 200
    }

    // 수정 (ADMIN만)
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            HttpServletRequest request,
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest req
    ) {
        requireAdmin(request);

        Product updated = productService.update(id, req);
        return ResponseEntity.ok(new ProductResponse(updated)); // 200
    }

    // 삭제 (ADMIN만)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            HttpServletRequest request,
            @PathVariable Long id
    ) {
        requireAdmin(request);

        productService.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }

    // 관리자 체크
    private void requireAdmin(HttpServletRequest request) {
        Object role = request.getAttribute("role");

        if(role == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        if(!"ADMIN".equals(role.toString())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
    }
}
