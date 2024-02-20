package com.shop.management.orders;

import com.shop.management.orderCollect.OrdersCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findByProductNameAndOption(String productName, String option);
}
