package com.shop.management.orders;

import java.sql.Timestamp;

public record OrdersResponse(
        String salesChannel, //판매채널
        String productOrderNumber, //상품주문번호
        String orderNumber,//주문번호
        String buyerName,//구매자명
        String recipientName,//수취인명
        String orderStatus,//주문상태
        Timestamp orderDate,//주문일시
        Timestamp paymentDate,//결제일
        String productNumber,//상품번호
        String productName,//상품명
        String optionInfo,//옵션정보
        String quantity,//수량
        String optionPrice,//옵션가격
        String productPrice,//상품가격
        String productDiscountAmount,//상품별 할인액
        String sellerDiscountAmount,//판매자 부담 할인액
        String totalOrderAmountPerProduct,//상품별 총 주문금액
        String sellerProductCode,//판매자 상품코드 FK
        String shippingAddress,//통합배송지
        String postalCode,//우편번호
        String shippingOrigin,//출고지
        Timestamp registeredDate,
        String registeredBy
) {

    public static OrdersResponse fromDto(OrdersDTO dto) {
        return new OrdersResponse(
                dto.salesChannel(),
                dto.productOrderNumber(),
                dto.orderNumber(),
                dto.buyerName(),
                dto.recipientName(),
                dto.orderStatus(),
                dto.orderDate(),
                dto.paymentDate(),
                dto.productNumber(),
                dto.productName(),
                dto.optionInfo(),
                dto.quantity(),
                dto.optionPrice(),
                dto.productPrice(),
                dto.productDiscountAmount(),
                dto.sellerDiscountAmount(),
                dto.totalOrderAmountPerProduct(),
                dto.sellerProductCode(),
                dto.shippingAddress(),
                dto.postalCode(),
                dto.shippingOrigin(),
                dto.registeredDate(),
                dto.registeredBy()
        );
    }
}
