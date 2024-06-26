package com.shop.management.orders;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>, OrdersCustomRepository {

    void removeByPlatform(String platform);

    List<Orders> findByPlatform(String search);
}
