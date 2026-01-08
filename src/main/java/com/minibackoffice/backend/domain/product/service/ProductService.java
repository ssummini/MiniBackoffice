package com.minibackoffice.backend.domain.product.service;

import com.minibackoffice.backend.domain.product.Product;
import com.minibackoffice.backend.domain.product.dto.ProductUpdateRequest;
import com.minibackoffice.backend.domain.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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

    // 단건 조회 (없으면 null 반환)
    public Product findById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    // 수정
    public Product update(Long id, ProductUpdateRequest request) {
        
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            return null;
        }

        Product product = optionalProduct.get();

        product.setName(request.name);
        product.setPrice(request.price);
        product.setStockQuantity(request.stockQuantity);
        product.setStatus(request.status);
        product.setThumbnailUrl(request.thumbnailUrl);

        return productRepository.save(product);
    }

    // 삭제
    public boolean delete(Long id) {
        if(!productRepository.existsById(id)) {
            return false; // 없으면 삭제 실패
        }
        productRepository.deleteById(id);
        return true; // 삭제 성공
    }
}
