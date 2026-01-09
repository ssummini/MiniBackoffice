package com.minibackoffice.backend.domain.product.service;

import com.minibackoffice.backend.domain.product.Product;
import com.minibackoffice.backend.domain.product.dto.ProductUpdateRequest;
import com.minibackoffice.backend.domain.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // 생성자 주입 (제일 중요)
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 저장
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // 전체 조회
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    // 단건 조회 (없으면 404)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."
                ));
    }

    // 수정 (없으면 404)
    public Product update(Long id, ProductUpdateRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."
                ));

        product.setName(request.name);
        product.setPrice(request.price);
        product.setStockQuantity(request.stockQuantity);
        product.setStatus(request.status);
        product.setThumbnailUrl(request.thumbnailUrl);

        return productRepository.save(product);
    }

    // 삭제 (없으면 404, 성공하면 void)
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다.");
        }
        productRepository.deleteById(id);
    }
}
