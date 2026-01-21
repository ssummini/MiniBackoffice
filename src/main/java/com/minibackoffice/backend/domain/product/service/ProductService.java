package com.minibackoffice.backend.domain.product.service;

import com.minibackoffice.backend.domain.product.Product;
import com.minibackoffice.backend.domain.product.dto.ProductUpdateRequest;
import com.minibackoffice.backend.domain.product.repository.ProductRepository;

import com.minibackoffice.backend.domain.product.dto.ProductResponse;

import java.util.List;

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
    public ProductResponse save(Product product) {
        Product saved = productRepository.save(product);
        return ProductResponse.from(saved);
    }

    // 전체 조회
    public List<ProductResponse> findAll(){
        return productRepository.findAll().stream()
                .map(ProductResponse::from)
                .toList();
    }

    // 단건 조회 (없으면 404)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."
                ));
        return ProductResponse.from(product);
    }

    // 수정 (없으면 404)
    public ProductResponse update(Long id, ProductUpdateRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."
                ));

        product.setName(request.name);
        product.setPrice(request.price);
        product.setStockQuantity(request.stockQuantity);
        product.setStatus(request.status);
        product.setThumbnailUrl(request.thumbnailUrl);

        Product saved = productRepository.save(product);
        return ProductResponse.from(saved);
    }

    // 삭제 (없으면 404, 성공하면 void)
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다.");
        }
        productRepository.deleteById(id);
    }
}
