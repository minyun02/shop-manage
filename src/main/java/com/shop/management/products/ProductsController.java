package com.shop.management.products;

import com.shop.management.products.dto.ProductsCollectDTO;
import com.shop.management.products.dto.ProductSaveDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/bulkSave")
    public String bulkSave() {
        return "products/bulkSave";
    }

    @PostMapping("/readExcel")
    public String readExcel(@RequestParam("excelFile") MultipartFile excelFile, ModelMap model) {
        List<ProductsCollectDTO> products = productsService.readAndMakeExcelData(excelFile);

        model.addAttribute("products", products);

        return "products/bulkSave";
    }

    @PostMapping("/saveNewProducts")
    public String saveNewProducts() {

        productsService.saveProducts();

        return "products/save";
    }

    @PostMapping("/excelDownload")
    public void saveProducts(HttpServletResponse response) throws IOException {
        productsService.downloadExcel(response);
    }

    @GetMapping("/save")
    public String productSaveForm() {
        return "products/save";
    }

    @PostMapping("/update")
    public String updateProductList(ProductSaveDTO dto) {

        productsService.updateProduct(dto);

        return "redirect:/api/v1/products";
    }


}
