package com.shop.management.orderCollect;

import com.shop.management.orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersCollectRepository extends JpaRepository<OrdersCollect, Long> {

    Optional<OrdersCollect> findByProductNameAndProductOption(String productName, String productOption);
}
