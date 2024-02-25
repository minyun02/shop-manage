package com.shop.management.retailProducts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailProductsRepository extends JpaRepository<RetailProducts, Long> {
}
