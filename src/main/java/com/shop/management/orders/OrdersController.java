package com.shop.management.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shop.management.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping
    public Response<Page<OrdersResponse>> getOrders(Pageable pageable) {
        return Response.success(ordersService.getOrders(pageable).map(OrdersResponse::fromDto));
    }
    @PostMapping("/save")
    public Response<Void> saveOrders(@RequestBody ObjectNode orders) throws JsonProcessingException {
        ordersService.saveOrders(orders);
        return Response.success();
    }

    @GetMapping("/organize")
    public String organize() {
        return "orders/organize";
    }

    @PostMapping("/readOrders")
    public String readOrders(@RequestParam("excelFile") MultipartFile excelFile, ModelMap model) {
        Map<String, Integer> collectedOrders = ordersService.readOrders(excelFile);
        List<OrderCollect> orders = ordersService.makeOrdersForTable(collectedOrders);

        model.addAttribute("orders", orders);

        return "orders/organize";
    }

    @PostMapping("/excelDownload")
    public void download(HttpServletResponse response) throws IOException {
        ordersService.downloadExcel(response);
    }
}
