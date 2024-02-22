package com.shop.management.products;

import com.shop.management.products.dto.ProductUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/save")
    public String productSaveForm() {
        return "products/save";
    }

    @PostMapping("/update")
    public String updateProductList(ProductUpdateDTO dto) {

        productsService.updateProduct(dto);

        return "redirect:/api/v1/products";
    }
}
