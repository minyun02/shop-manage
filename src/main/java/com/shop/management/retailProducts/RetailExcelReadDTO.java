package com.shop.management.retailProducts;

public record RetailExcelReadDTO(
        String sellerName,

        String productName,

        String option,

        String size,

        String retailPrice,

        String sellingPrice,

        String orderedAt
) {
}
