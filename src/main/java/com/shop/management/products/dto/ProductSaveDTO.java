package com.shop.management.products.dto;

public record ProductSaveDTO(
        Long productId
        , String productName
        , String option
        , String sellerCode
        , Integer stocks
        , Integer returnStocks
        , Integer reservedStocks
        , String sellingChannel
        , String status
) {}