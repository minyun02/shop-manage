package com.shop.management.orders;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String salesChannel;
    private String productOrderNumber;
    private String orderNumber;
    private String buyerName;
    private String recipientName;
    private String orderStatus;
    private Timestamp orderDate;
    private Timestamp paymentDate;
    private String productNumber;
    private String productName;
    private String optionInfo;
    private String quantity;
    private String optionPrice;
    private String productPrice;
    private String productDiscountAmount;
    private String sellerDiscountAmount;
    private String totalOrderAmountPerProduct;
    private String sellerProductCode;
    private String shippingAddress;
    private String postalCode;
    private String shippingOrigin;
    private Timestamp registeredDate;
    private String registeredBy;
    private Timestamp removedDate;
    private String removedBy;

    @PrePersist
    void registeredDate() { this.registeredDate = Timestamp.from(Instant.now());}

    private Orders(OrdersDTO dto) {
        this.salesChannel = dto.salesChannel();
        this.productOrderNumber = dto.productOrderNumber();
        this.orderNumber = dto.orderNumber();
        this.buyerName = dto.buyerName();
        this.recipientName = dto.recipientName();
        this.orderStatus = dto.orderStatus();
        this.orderDate = dto.orderDate();
        this.paymentDate = dto.paymentDate();
        this.productNumber = dto.productNumber();
        this.productName = dto.productName();
        this.optionInfo = dto.optionInfo();
        this.quantity = dto.quantity();
        this.optionPrice = dto.optionPrice();
        this.productPrice = dto.productPrice();
        this.productDiscountAmount = dto.productDiscountAmount();
        this.sellerDiscountAmount = dto.sellerDiscountAmount();
        this.totalOrderAmountPerProduct = dto.totalOrderAmountPerProduct();
        this.sellerProductCode = dto.sellerProductCode();
        this.shippingAddress = dto.shippingAddress();
        this.postalCode = dto.postalCode();
        this.shippingOrigin = dto.shippingOrigin();
    }
    public static Orders of(OrdersDTO dto) {
        return new Orders(dto);
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", salesChannel='" + salesChannel + '\'' +
                ", productOrderNumber='" + productOrderNumber + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", buyerName='" + buyerName + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDate=" + orderDate +
                ", paymentDate=" + paymentDate +
                ", productNumber='" + productNumber + '\'' +
                ", productName='" + productName + '\'' +
                ", optionInfo='" + optionInfo + '\'' +
                ", quantity='" + quantity + '\'' +
                ", optionPrice='" + optionPrice + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productDiscountAmount='" + productDiscountAmount + '\'' +
                ", sellerDiscountAmount='" + sellerDiscountAmount + '\'' +
                ", totalOrderAmountPerProduct='" + totalOrderAmountPerProduct + '\'' +
                ", sellerProductCode='" + sellerProductCode + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", shippingOrigin='" + shippingOrigin + '\'' +
                ", registeredDate=" + registeredDate +
                ", registeredBy='" + registeredBy + '\'' +
                ", removedDate=" + removedDate +
                ", removedBy='" + removedBy + '\'' +
                '}';
    }
}
