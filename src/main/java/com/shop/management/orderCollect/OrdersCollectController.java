package com.shop.management.orderCollect;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrdersCollectController {

    private final OrdersCollectService collectService;

    @GetMapping("/organize")
    public String organize() {
        return "orders/organize";
    }

    @PostMapping("/readOrders")
    public String readOrders(@RequestParam("excelFile") MultipartFile excelFile, ModelMap model) {
        List<OrdersCollectDTO> orders = collectService.makeOrdersForTable(excelFile);
        collectService.saveOrders(orders);

        model.addAttribute("orders", orders);

        return "orders/organize";
    }

    @PostMapping("/excelDownload")
    public void download(HttpServletResponse response) throws IOException {
        collectService.downloadExcel(response);
    }
}
