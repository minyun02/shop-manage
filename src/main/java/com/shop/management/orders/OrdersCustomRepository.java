package com.shop.management.orders;


import java.util.List;

public interface OrdersCustomRepository {

    List<OrdersDTO> findAllGroupByOrderKey();

}
