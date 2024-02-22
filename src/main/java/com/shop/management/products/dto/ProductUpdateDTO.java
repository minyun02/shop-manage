package com.shop.management.products.dto;

public record ProductUpdateDTO(
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