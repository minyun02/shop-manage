package com.shop.management.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping
    public String orders(@RequestParam(required = false) String search, ModelMap model) {
        System.out.println("search = " + search);
        model.addAttribute("orders", ordersService.getAllOrders(search));

        return "orders/orders";
    }

    @PostMapping(value = "readExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void readOrderExcel(
            @RequestParam(name = "platform") String platform
            , @RequestPart(name = "excelFile") MultipartFile excelFile
    ) {

        ordersService.saveExcelData(platform, excelFile);

    }

}
