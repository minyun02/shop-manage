package com.shop.management.products;

import com.shop.management.products.dto.ProductsCollectDTO;
import com.shop.management.products.dto.ProductSaveDTO;
import com.shop.management.products.dto.ProductsDTO;
import com.shop.management.utils.ExcelExport;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    private ArrayList<ProductsCollectDTO> ordersForTable;

    public List<ProductsCollectDTO> readAndMakeExcelData(MultipartFile excelFile) {

        fillOrdersForTable(readOrders(excelFile));

        return ordersForTable;
    }

    public void downloadExcel(HttpServletResponse response) throws IOException {
        ExcelExport excelExport = new ExcelExport(ordersForTable);
        excelExport.write(response, "주문합치기"+ "_" + LocalDate.now().getYear() + "_" + LocalDate.now().getMonth());
    }

    public void saveProducts() {

        for (ProductsCollectDTO productCollectDTO : ordersForTable) {
//            Optional<Products> order = productsRepository.findByProductNameAndOption(productCollectDTO.productName(), productCollectDTO.option());

            if (!productCollectDTO.isDuplicated()) productsRepository.save(Products.fromCollectDTO(productCollectDTO));
        }

    }

    public List<ProductsDTO> getProductsList() {

        List<ProductsDTO> list = productsRepository.findAll().stream().map(ProductsDTO::fromEntity).toList();

        return list;
    }

    public void updateProduct(ProductSaveDTO product) {
        productsRepository.save(Products.fromUpdateDto(product));
    }

    /*
     * private method
     */
    private void fillOrdersForTable(Map<String, Integer> readExcel) {
        ordersForTable.clear();

        List<Products> products = productsRepository.findAll();
        for (Map.Entry<String, Integer> entry : readExcel.entrySet()) {

            boolean isDuplicated = false;
            for (Products product : products) {
                System.out.println("product = " + product.getProductName() + "+" + product.getOption());
                if (entry.getKey().equals(product.getProductName() + "+" + product.getOption())) {
                    isDuplicated = true;
                }
            }

            ordersForTable.add(ProductsCollectDTO.of(entry.getKey().split("\\+")[0], entry.getKey().split("\\+")[1], entry.getValue(), isDuplicated));
        }

    }

    private Map<String, Integer> readOrders(MultipartFile excelFile) {
        Map<String, Integer> orders = new HashMap<>();

        try {
            Workbook workbook = new XSSFWorkbook(excelFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            int skipFirstRow = 0;
            for (Row cells : sheet) {
                if (skipFirstRow++ > 0) {
                    String productName = cells.getCell(17).getStringCellValue();
                    String option = cells.getCell(20).getStringCellValue();
                    orders.put(productName + "+" + option, 1 + orders.getOrDefault(productName + "+" + option, 0));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
