package com.shop.management.products.dto;

public record ProductsCollectDTO(String productName,
        String option,
        Integer quantity,
        boolean isDuplicated
) {

    public static ProductsCollectDTO of(String productName, String option, Integer quantity) {
        return new ProductsCollectDTO(productName, option, quantity, false);
    }

    public static ProductsCollectDTO of(String productName, String option, Integer quantity, Boolean isDuplicated) {
        return new ProductsCollectDTO(productName, option, quantity, isDuplicated);
    }
}
