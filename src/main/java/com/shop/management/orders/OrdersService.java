package com.shop.management.orders;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService {
    private final String JOIN_KEY = "!@#";
    private final String ZIGZAG = "zigzag";
    private final String SMART_STORE = "smartStore";

    private final OrdersRepository ordersRepository;

    @Transactional
    public void saveExcelData(String platform, MultipartFile excelFile) {

        Integer[] fields = setOrderExcelFieldsIndex(platform);
        if (fields.length == 0) return;

        Map<String, Integer> orderMap = readExcel(platform, fields, excelFile);
        List<OrdersDTO> ordersDTO = convertToDTO(platform, orderMap);

        ordersRepository.removeByPlatform(platform);

        ordersRepository.saveAll(dtoToEntityList(ordersDTO));
    }

    private Iterable<Orders> dtoToEntityList(List<OrdersDTO> ordersDTO) {
        List<Orders> result = new ArrayList<>();

        for (OrdersDTO dto : ordersDTO) {
            result.add(Orders.fromDTO(dto));
        }

        return result;
    }

    private List<OrdersDTO> convertToDTO(String platform, Map<String, Integer> orderMap) {
        List<OrdersDTO> result = new ArrayList<>();

        for (Map.Entry<String, Integer> order : orderMap.entrySet()) {
            String name = order.getKey().split(JOIN_KEY)[0];
            String option = order.getKey().split(JOIN_KEY)[1];

            OrdersDTO dto = new OrdersDTO();
            dto.setOrderKey(order.getKey());
            dto.setProductName(name);
            dto.setOption(option);
            dto.setPlatform(platform);
            dto.setTotalQuantity(order.getValue());

            result.add(dto);
        }

        return result;
    }

    private Map<String, Integer> readExcel(String platform, Integer[] fields, MultipartFile excelFile) {
        Map<String, Integer> orders = new HashMap<>();

        try {
            Workbook wb = null;
            if (ZIGZAG.equals(platform)) {
                wb = new XSSFWorkbook(excelFile.getInputStream());
            }

            if (SMART_STORE.equals(platform)) {
                wb = createWorkbookForSmartStoreWithPassword(excelFile);
            }
            Sheet sheet = wb.getSheetAt(0);

            int startRow = "zigzag".equals(platform) ? 1 : 2;
            for (Row cell : sheet) {

                if (cell.getRowNum() >= startRow) {
                    String productName = cell.getCell(fields[0], Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
                    String option = cell.getCell(fields[1], Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();

                    String key = productName + JOIN_KEY + option.toLowerCase();

                    Integer quantity = 0;
                    if (ZIGZAG.equals(platform)) {
                        quantity = Integer.parseInt(cell.getCell(fields[2], Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue());
                    }

                    if (SMART_STORE.equals(platform)) {
                        quantity = (int) cell.getCell(fields[2], Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getNumericCellValue();
                    }

                    orders.put(key, quantity + orders.getOrDefault(key, 0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    private static Workbook createWorkbookForSmartStoreWithPassword(MultipartFile excelFile) {
        Workbook wk = null;
        try {
            wk = WorkbookFactory.create(excelFile.getInputStream(), "1111");
        } catch (Exception e) {
            log.error("Exception thrown while creating workbookFactory for smartstore {}", e);
        }
        return wk;
    }

    private Integer[] setOrderExcelFieldsIndex(String platform) {

        //상품명, 옵션, 수량, 가격
        if (ZIGZAG.equals(platform)) {
            return new Integer[]{17, 20, 24, 23};
        }

        if (SMART_STORE.equals(platform)) {
            return new Integer[]{18, 21, 23, 28};
        }

        return new Integer[]{};
    }

    public List<OrdersDTO> getAllOrders(String search) {
        if (search == null) search = "default";
        return switch (search) {
            case "all" -> ordersRepository.findAllGroupByOrderKey();
            case ZIGZAG, SMART_STORE -> ordersRepository.findByPlatform(search).stream().map(OrdersDTO::fromEntity).toList();
            default -> ordersRepository.findAll(Sort.by(Sort.Direction.DESC, "platform")).stream().map(OrdersDTO::fromEntity).toList();
        };
    }
}
