package com.shop.management.utils;

import com.shop.management.orders.OrderCollect;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExcelExport<T> {
    private static final int ROW_START_INDEX = 0;
    private static final int COLUMN_START_INDEX = 0;

    private SXSSFWorkbook wb;
    private SXSSFSheet sheet;


//    public ExcelExport(List<T> data, Class<T> type) {
    public ExcelExport(ArrayList<OrderCollect> data) {
        this.wb = new SXSSFWorkbook();
        renderExcel(data);
    }

    private void renderExcel(ArrayList<OrderCollect> data) {
        sheet = wb.createSheet();
        List<String> columns = Arrays.stream("상품명,옵션,수량".split(",")).toList();
//        createHeaders(sheet, ROW_START_INDEX, COLUMN_START_INDEX, objectType);
        createHeaders(ROW_START_INDEX, COLUMN_START_INDEX, columns);

        if (data.isEmpty()) return;

        int rowIndex = ROW_START_INDEX + 1;

        for (OrderCollect order : data) {
            createBody(order, rowIndex++, COLUMN_START_INDEX, columns);
        }

    }

    private void createHeaders(int rowStartIndex, int columnStartIndex, List<String> columns) {
        SXSSFRow row = sheet.createRow(rowStartIndex);

        for (String column : columns) {
            SXSSFCell cell = row.createCell(columnStartIndex++);
            cell.setCellValue(column);
        }
    }

    private void createBody(OrderCollect order, int i, int columnStartIndex,  List<String> columns) {
        SXSSFRow row = sheet.createRow(i);

        for (String column : columns) {
            SXSSFCell cell = row.createCell(columnStartIndex++);
            if ("상품명".equals(column)) {
                cell.setCellValue(order.getProductName());
                continue;
            }
            if ("옵션".equals(column)) {
                cell.setCellValue(order.getOption());
                continue;
            }
            if ("수량".equals(column)) {
                cell.setCellValue(order.getQuantity());
            }
        }
    }

    public void write(HttpServletResponse res, String fileName) throws IOException {
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8") + ";");
        wb.write(res.getOutputStream());
        wb.close();
        res.getOutputStream().close();
    }
}
