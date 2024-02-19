package com.shop.management.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shop.management.utils.ExcelExport;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    private ArrayList<OrderCollect> ordersForTable = new ArrayList<>();

    public Page<OrdersDTO> getOrders(Pageable pageable) {
        return ordersRepository.findAll(pageable).map(OrdersDTO::fromEntity);
    }
    public void saveOrders(ObjectNode orders) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> zigzag = mapper.treeToValue(orders.get("zigzag"), Map.class);
        Map<String, String> smartStore = mapper.treeToValue(orders.get("smartstore"), Map.class);

        if (!zigzag.isEmpty()) {
            try {
                OrdersDTO zigzagDto = OrdersDTO.fromMap(zigzag);
                ordersRepository.save(Orders.of(zigzagDto));
            } catch (Exception e) {

            }
        }
        if (!smartStore.isEmpty()) {
            try {
                OrdersDTO smartStoreDto = OrdersDTO.fromMap(smartStore);
                System.out.println(smartStoreDto);
                ordersRepository.save(Orders.of(smartStoreDto));
            } catch (Exception e) {

            }
        }
    }

    public HashMap<String, Integer> readOrders(MultipartFile excelFile) {
        HashMap<String, Integer> orders = new HashMap<>();

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

    public ArrayList<OrderCollect> makeOrdersForTable(HashMap<String, Integer> collectedOrders) {
        ordersForTable.clear();

        for (String key : collectedOrders.keySet()) {
            ordersForTable.add(OrderCollect.builder()
                            .productName(key.split("\\+")[0])
                            .option(key.split("\\+")[1])
                            .quantity(collectedOrders.get(key))
                            .build()
            );

        }
        return ordersForTable;
    }
    public void downloadExcel(HttpServletResponse response) throws IOException {
        ExcelExport<T> excelExport = new ExcelExport<>(ordersForTable);
        excelExport.write(response, "주문합치기"+ "_" + LocalDate.now().getYear() + "_" + LocalDate.now().getMonth());
    }
}
