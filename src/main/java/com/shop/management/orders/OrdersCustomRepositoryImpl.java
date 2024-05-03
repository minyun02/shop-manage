package com.shop.management.orders;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrdersCustomRepositoryImpl implements OrdersCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrdersDTO> findAllGroupByOrderKey() {
        return jpaQueryFactory
                .select(Projections.constructor(OrdersDTO.class,
                        QOrders.orders.orderKey
                        , QOrders.orders.quantity.sum())
                )
                .from(QOrders.orders)
                .groupBy(QOrders.orders.orderKey)
                .fetch();
    }
}
