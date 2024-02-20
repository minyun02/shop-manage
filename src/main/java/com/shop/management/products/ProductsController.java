package com.shop.management.products;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping()
    public String productsList(ModelMap model) {

        model.addAttribute("products", productsService.getProductsList());

        return "products/list";
    }
}
