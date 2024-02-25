package com.shop.management.retailProducts;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RetailService {

    private final RetailProductsRepository retailRepository;

    private ArrayList<RetailExcelReadDTO> retailProducts = new ArrayList<>();

    public List<RetailExcelReadDTO> readAndMakeExcelData(MultipartFile excelFile) {

        fillOrdersForTable(excelFile);

        return retailProducts;
    }

    public void saveRetailProducts() {

        for (RetailExcelReadDTO retailProduct : retailProducts) {
            retailRepository.save(RetailProducts.fromExcelDTO(retailProduct));
        }

    }

    private void fillOrdersForTable(MultipartFile excelFile) {
        retailProducts.clear();

        try {
            Workbook workbook = new XSSFWorkbook(excelFile.getInputStream());
            Sheet sheet = workbook.getSheet("신상");

            int skipFirstRow = 0;
            for (Row cells : sheet) {
                if (skipFirstRow++ > 0) {
                    String sellerName = "-";
                    if (cells.getCell(2) != null) {
                        sellerName = cells.getCell(2).getCellType().name().equals("BLANK") ? "-" : cells.getCell(2).getStringCellValue();
                    }

                    String productName = "-";
                    if (cells.getCell(4) != null) {
                        String cellType = cells.getCell(4).getCellType().name();
                        if (cellType.equals("STRING")) {
                            productName = cells.getCell(4).getStringCellValue();
                        }

                        if (cellType.equals("NUMERIC")) {
                            productName = String.valueOf(cells.getCell(4).getNumericCellValue());
                        }

                    }

                    String size = "-";
                    if (cells.getCell(7) != null) {

                        if (cells.getCell(7).getCellType().name().equals("STRING")) {
                            size = cells.getCell(7).getStringCellValue();
                        }

                        if (cells.getCell(7).getCellType().name().equals("NUMERIC")) {
                            size = String.valueOf(cells.getCell(7).getNumericCellValue());
                        }

                    }

                    String retailPrice = String.valueOf(cells.getCell(11).getNumericCellValue());
                    String sellingPrice = String.valueOf(cells.getCell(15).getNumericCellValue());

                    String orderedAt = "-";
                    if (cells.getCell(1).getCellType().name().equals("STRING")) {
                        orderedAt = cells.getCell(1).getStringCellValue();
                    }

                    String[] optionArray = cells.getCell(6).getStringCellValue().split("/");
                    for (String option : optionArray) {
                        retailProducts.add(new RetailExcelReadDTO(
                                sellerName
                                , productName
                                , option
                                , size
                                , retailPrice
                                , sellingPrice
                                , orderedAt
                        ));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * private method
    */
}
