package com.minibackoffice.backend.domain.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductCreateRequest {

    @NotBlank(message = "상품명은 필수입니다.")
    public String name;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    public Integer price;

    @NotNull(message = "재고 수량은 필수입니다.")
    @Min(value = 0, message = "재고 수량은 0 이상이어야 합니다.")
    public Integer stockQuantity;

    @NotBlank(message = "상태(status)는 필수입니다.")
    public String status;

    public String thumbnailUrl;
}
