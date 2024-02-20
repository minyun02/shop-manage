package com.shop.management.orderCollect;

import com.shop.management.orders.Orders;
import com.shop.management.orders.OrdersRepository;
import com.shop.management.utils.ExcelExport;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
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
@RequiredArgsConstructor
public class OrdersCollectService {

    private final OrdersRepository ordersRepository;

    private ArrayList<OrdersCollectDTO> ordersForTable = new ArrayList<>();

    public List<OrdersCollectDTO> makeOrdersForTable(MultipartFile excelFile) {

        fillOrdersForTable(readOrders(excelFile));

        return ordersForTable;
    }

    public void downloadExcel(HttpServletResponse response) throws IOException {
        ExcelExport<T> excelExport = new ExcelExport<>(ordersForTable);
        excelExport.write(response, "주문합치기"+ "_" + LocalDate.now().getYear() + "_" + LocalDate.now().getMonth());
    }

    public void saveOrders(List<OrdersCollectDTO> orders) {

        for (OrdersCollectDTO orderCollectDTO : orders) {
            Optional<Orders> order = ordersRepository.findByProductNameAndOption(orderCollectDTO.getProductName(), orderCollectDTO.getOption());

            if (order.isEmpty()) ordersRepository.save(Orders.fromCollectDTO(orderCollectDTO));
        }

    }


    /*
    * private method
    */
    private void fillOrdersForTable(Map<String, Integer> readExcel) {
        ordersForTable.clear();

        for (Map.Entry<String, Integer> entry : readExcel.entrySet()) {
            ordersForTable.add(OrdersCollectDTO.builder()
                    .productName(entry.getKey().split("\\+")[0])
                    .option(entry.getKey().split("\\+")[1])
                    .quantity(entry.getValue())
                    .build()
            );
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
