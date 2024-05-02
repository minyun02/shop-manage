package com.shop.management.orders;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderKey;

    private String productName;

    private String optionName;

    private Integer quantity;

    private String platform;

    private Orders(String orderKey, String productName, String optionName, Integer quantity, String platform) {
        this.orderKey = orderKey;
        this.productName = productName;
        this.optionName = optionName;
        this.quantity = quantity;
        this.platform = platform;
    }

    public static Orders fromDTO(OrdersDTO dto) {
        return new Orders(dto.getOrderKey(), dto.getProductName(), dto.getOption(), dto.getTotalQuantity(), dto.getPlatformMap());
    }
}
