package com.shop.management.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shop.management.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

//    @GetMapping
//    public Response<Page<OrdersResponse>> getOrders(Pageable pageable) {
//        return Response.success(ordersService.getOrders(pageable).map(OrdersResponse::fromDto));
//    }
//    @PostMapping("/save")
//    public Response<Void> saveOrders(@RequestBody ObjectNode orders) throws JsonProcessingException {
//        ordersService.saveOrdersOld(orders);
//        return Response.success();
//    }
}
