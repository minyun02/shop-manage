package com.shop.management.orderCollect;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class OrdersCollect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String productOption;
    private Integer quantity;

    @CreatedDate
    @Column(updatable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP NOT NULL COMMENT '생성일자'")
    private LocalDateTime createdAt;

    private OrdersCollect(String productName, String productOption, Integer quantity) {
        this.productName = productName;
        this.productOption = productOption;
        this.quantity = quantity;
    }

    public static OrdersCollect fromDTO(OrdersCollectDTO dto) {
        return new OrdersCollect(dto.getProductName(), dto.getOption(), dto.getQuantity());
    }

}
