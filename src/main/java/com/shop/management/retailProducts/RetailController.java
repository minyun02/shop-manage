package com.shop.management.retailProducts;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/retails")
public class RetailController {

    private final RetailService retailService;

    @GetMapping("/bulkSave")
    public String bulkSave() {
        return "retails/bulkSave";
    }

    @PostMapping("/readExcel")
    public String readExcel(@RequestParam("excelFile") MultipartFile excelFile, ModelMap model) {
        List<RetailExcelReadDTO> retailProducts = retailService.readAndMakeExcelData(excelFile);

        model.addAttribute("retails", retailProducts);

        return "retails/bulkSave";
    }

    @PostMapping("/saveNewRetails")
    public String saveNewRetails() {
        retailService.saveRetailProducts();

        return "retails/bulkSave";
    }
}
