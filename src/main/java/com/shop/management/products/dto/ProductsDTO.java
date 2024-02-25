package com.shop.management.products.dto;

import com.shop.management.products.Products;

import java.sql.Timestamp;

public record ProductsDTO(Long id,
                        String productName,
                        String option,
                        String sellerCode,
                        Integer stocks,
                        Integer returnStocks,
                        Integer reservedStocks,
                        String sellingChannel,
                        String status,
                        Timestamp createdAt,
                        Timestamp updatedAt
) {

    public static ProductsDTO fromEntity(Products entity) {
        return new ProductsDTO(
                entity.getId(),
                entity.getProductName(),
                entity.getOption(),
                entity.getSellerCode(),
                entity.getStocks(),
                entity.getReturnStocks(),
                entity.getReservedStocks(),
                entity.getSellingChannel(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
