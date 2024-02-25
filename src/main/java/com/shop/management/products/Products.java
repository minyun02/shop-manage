package com.shop.management.products;

import com.shop.management.products.dto.ProductsCollectDTO;
import com.shop.management.products.dto.ProductSaveDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Entity
public class Products {

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

    private Products(String productName, String option) {
        this.productName = productName;
        this.option = option;
    }

    private Products(Long id, String productName, String option, String sellerCode, Integer stocks, Integer returnStocks, Integer reservedStocks, String sellingChannel, String status) {
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

    public static Products fromCollectDTO(ProductsCollectDTO dto) {
        return new Products(dto.productName(), dto.option());
    }

    public static Products fromUpdateDto(ProductSaveDTO dto) {
        return new Products(dto.productId(), dto.productName(), dto.option(), dto.sellerCode(), dto.stocks(), dto.returnStocks(), dto.reservedStocks(), dto.sellingChannel(), dto.status());
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
