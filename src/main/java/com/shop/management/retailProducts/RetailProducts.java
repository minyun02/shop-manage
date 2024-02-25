package com.shop.management.retailProducts;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class RetailProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false
            , unique = true
            , nullable = false
    )
    private String sellerCode;

    private String sellerName;

    private String productName;

    private String retailPrice;

    private String sellingPrice;

    @Column(name = "retailOption")
    private String option;

    private String status;

    private String orderedAt;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @PrePersist
    void createdAt() { this.createdAt = Timestamp.from(Instant.now()); }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    private RetailProducts(String sellerName, String productName, String retailPrice, String sellingPrice, String option, String orderedAt) {
        this.sellerName = sellerName;
        this.productName = productName;
        this.retailPrice = retailPrice;
        this.sellingPrice = sellingPrice;
        this.option = option;
        this.orderedAt = orderedAt;
    }

    public static RetailProducts fromExcelDTO(RetailExcelReadDTO dto) {
        return new RetailProducts(dto.sellerName()
                , dto.productName()
                , dto.retailPrice()
                , dto.sellingPrice()
                , dto.option()
                , dto.orderedAt()
                );
    }
}
