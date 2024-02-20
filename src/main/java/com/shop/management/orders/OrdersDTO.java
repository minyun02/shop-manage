package com.shop.management.orders;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

public record OrdersDTO(
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
    public static OrdersDTO fromMap(Map<String, String> map) {
        return new OrdersDTO(
                map.get("판매채널"),
                map.get("상품주문번호"),
                map.get("주문번호"),
                map.get("구매자명"),
                map.get("수취인명"),
                map.get("주문상태"),
                convertToTimeStamp(map.get("주문일시"), map.get("판매채널")),
                convertToTimeStamp(map.get("결제일"), map.get("판매채널")),
                map.get("상품번호"),
                map.get("상품명"),
                map.get("옵션정보"),
                String.valueOf(map.get("수량")),
                map.get("옵션가격") == null ? "-" : String.valueOf(map.get("옵션가격")),
                String.valueOf(map.get("상품가격")),
                String.valueOf(map.get("상품별 할인액")),
                String.valueOf(map.get("판매자 부담 할인액")),
                String.valueOf(map.get("상품별 총 주문금액")),
                map.get("판매자 상품코드") == null ? "-" : map.get("판매자 상품코드"),
                map.get("통합배송지"),
                map.get("우편번호"),
                map.get("출고지"),
                Timestamp.from(Instant.now()),
                "admin"
        );
    }

//    public static OrdersDTO fromEntity(Orders entity) {
//        return new OrdersDTO(
//                entity.getSalesChannel(),
//                entity.getProductOrderNumber(),
//                entity.getOrderNumber(),
//                entity.getBuyerName(),
//                entity.getRecipientName(),
//                entity.getOrderStatus(),
//                entity.getOrderDate(),
//                entity.getPaymentDate(),
//                entity.getProductNumber(),
//                entity.getProductName(),
//                entity.getOptionInfo(),
//                entity.getQuantity(),
//                entity.getOptionPrice(),
//                entity.getProductPrice(),
//                entity.getProductDiscountAmount(),
//                entity.getSellerDiscountAmount(),
//                entity.getTotalOrderAmountPerProduct(),
//                entity.getSellerProductCode(),
//                entity.getShippingAddress(),
//                entity.getPostalCode(),
//                entity.getShippingOrigin(),
//                entity.getRegisteredDate(),
//                entity.getRegisteredBy()
//        );
//    }

    public static Timestamp convertToTimeStamp(String date, String salesChannel) {
        String year = "";
        String month = "";
        String day = "";
        String hour = "";
        String minute = "";
        String hhmm = "";

        if (salesChannel.equals("지그재그")) {
            // 2023/11/13 05:45:45
            String[] ymd = date.substring(0, date.indexOf(" ")).split("/");
            year = ymd[0];
            month = ymd[1];
            day = ymd[2];
            hhmm = date.substring(date.indexOf(" ") + 1);
            return Timestamp.valueOf(year + "-" + month + "-" + day + " " + hhmm);
        }
        else if (salesChannel.equals("스마트스토어")) {
            // 2023-11-12T14:28:17.000Z
            String ymd = date.substring(0, date.indexOf("T"));
            hhmm = date.substring(date.indexOf("T") + 1, date.indexOf("."));
            return Timestamp.valueOf(ymd + " " + hhmm);
        }
//        yyyy-mm-dd hh:mm:ss

        return null;
    }
}
