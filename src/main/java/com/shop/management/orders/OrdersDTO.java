package com.shop.management.orders;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrdersDTO {
    private String orderKey;
    private String productName;
    private String option;
    private String platform;
    private Integer totalQuantity;

    private OrdersDTO(String productName, String option, String platform, Integer totalQuantity) {
        this.productName = productName;
        this.option = option;
        this.platform = platform;
        this.totalQuantity = totalQuantity;
    }

    public OrdersDTO(String orderKey, Integer totalQuantity) {
        this.orderKey = orderKey;
        this.productName = orderKey.split("!@#")[0];
        this.option = orderKey.split("!@#")[1];
        this.totalQuantity = totalQuantity;
    }

    public static OrdersDTO fromEntity(Orders entity) {
        return new OrdersDTO(
                entity.getProductName(),
                entity.getOptionName(),
                entity.getPlatform(),
                entity.getQuantity()
        );
    }

}

