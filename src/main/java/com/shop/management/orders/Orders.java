package com.shop.management.orders;

import com.shop.management.orderCollect.OrdersCollectDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

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

    private Timestamp createdAt;

    @PrePersist
    void createdAt() { this.createdAt = Timestamp.from(Instant.now()); }

    private Orders(String productName, String option) {
        this.productName = productName;
        this.option = option;
    }

    public static Orders fromCollectDTO(OrdersCollectDTO dto) {
        return new Orders(dto.getProductName(), dto.getOption());
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
