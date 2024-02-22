package com.shop.management.orders;

import com.shop.management.orderCollect.OrdersCollectDTO;
import com.shop.management.products.dto.ProductUpdateDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false,
            name = "productOption")
    private String option;

    @Column(unique = true)
    private String sellerCode;

    private Integer stocks;

    private Integer returnStocks;

    private Integer reservedStocks;

    private String sellingChannel;

    private String status;

    @Column(updatable = false)
    private Timestamp createdAt;

    private Timestamp updatedAt;

    @PrePersist
    void createdAt() { this.createdAt = Timestamp.from(Instant.now()); }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    private Orders(String productName, String option) {
        this.productName = productName;
        this.option = option;
    }

    private Orders(Long id, String productName, String option, String sellerCode, Integer stocks, Integer returnStocks, Integer reservedStocks, String sellingChannel, String status) {
        this.id = id;
        this.productName = productName;
        this.option = option;
        this.sellerCode = sellerCode;
        this.stocks = stocks;
        this.returnStocks = returnStocks;
        this.reservedStocks = reservedStocks;
        this.sellingChannel = sellingChannel;
        this.status = status;
    }

    public static Orders fromCollectDTO(OrdersCollectDTO dto) {
        return new Orders(dto.getProductName(), dto.getOption());
    }

    public static Orders fromUpdateDto(ProductUpdateDTO dto) {
        return new Orders(dto.productId(), dto.productName(), dto.option(), dto.sellerCode(), dto.stocks(), dto.returnStocks(), dto.reservedStocks(), dto.sellingChannel(), dto.status());
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", option='" + option + '\'' +
                ", sellerCode='" + sellerCode + '\'' +
                ", stocks=" + stocks +
                ", returnStocks=" + returnStocks +
                ", reservedStocks=" + reservedStocks +
                ", sellingChannel='" + sellingChannel + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
