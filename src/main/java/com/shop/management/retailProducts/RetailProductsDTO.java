package com.shop.management.retailProducts;

import java.sql.Timestamp;

public record RetailProductsDTO(
        Long id,
        String sellerCode,
        String sellerName,
        String productName,
        Integer retailPrice,
        Integer sellingPrice,
        String option,
        String status,
        Timestamp orderedAt,
        Timestamp createdAt
) {

}
