package com.shop.management.orders;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrdersDTO {
    private String orderKey;
    private String productName;
    private String option;
    private String platformMap;
    private Integer totalQuantity;

    private OrdersDTO(String productName, String option, String platformMap, Integer totalQuantity) {
        this.productName = productName;
        this.option = option;
        this.platformMap = platformMap;
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

