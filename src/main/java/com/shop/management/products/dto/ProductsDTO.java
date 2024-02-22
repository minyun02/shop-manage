package com.shop.management.products.dto;

import com.shop.management.orders.Orders;

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
                          Timestamp createdAt
) {

    public static ProductsDTO fromEntity(Orders entity) {
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
                entity.getCreatedAt()
        );
    }

    @Override
    public String toString() {
        return "ProductsDTO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", option='" + option + '\'' +
                ", sellerCode='" + sellerCode + '\'' +
                ", stocks=" + stocks +
                ", returnStocks=" + returnStocks +
                ", reservedStocks=" + reservedStocks +
                ", sellingChannel='" + sellingChannel + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
