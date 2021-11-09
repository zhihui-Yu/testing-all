package org.example;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExcelData {
    private XSSFWorkbook sheets;

    ExcelData(String filePath) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            sheets = new XSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Customer> readCustomers(String sheetName) {
        XSSFSheet sheet = sheets.getSheet(sheetName);
        List<Customer> customers = new ArrayList<>();
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            try {
                XSSFRow row = sheet.getRow(i);
                Customer customer = new Customer();
                customer.userId = row.getCell(0).toString();
                customer.customerTotalOrderCount = Double.valueOf(row.getCell(1).toString());
                customer.customerOrderCount = Double.valueOf(row.getCell(2).toString());
                customer.firstOrderId = row.getCell(3).toString();
                customers.add(customer);
            } catch (NullPointerException ignored) {

            }
        }
        return customers;
    }

    public List<Customer> readOrderTotals(String sheetName) {
        XSSFSheet sheet = sheets.getSheet(sheetName);
        List<Customer> customers = new ArrayList<>();
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            try {
                XSSFRow row = sheet.getRow(i);
                Customer customer = new Customer();
                customer.userId = row.getCell(0).toString();
                customer.orderTotalCount = Double.valueOf(row.getCell(1).toString());
                customers.add(customer);
            } catch (NullPointerException ignored) {
            }
        }
        return customers;
    }

    public List<Customer> readFirstOrderIds(String sheetName) {
        XSSFSheet sheet = sheets.getSheet(sheetName);
        List<Customer> customers = new ArrayList<>();
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            try {
                XSSFRow row = sheet.getRow(i);
                Customer customer = new Customer();
                customer.userId = row.getCell(0).toString();
                customer.firstOrderIdInOrder = row.getCell(1).toString();
                customers.add(customer);
            } catch (NullPointerException ignored) {
            }
        }
        return customers;
    }

    public List<Customer> readOrderCounts(String sheetName) {
        XSSFSheet sheet = sheets.getSheet(sheetName);
        List<Customer> customers = new ArrayList<>();
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            Customer customer = new Customer();
            customer.userId = row.getCell(0).toString();
            customer.orderCount = Double.valueOf(row.getCell(1).toString());
            customers.add(customer);
        }
        return customers;
    }

    public static void main(String[] args) {
        ExcelData excelData = new ExcelData("C:\\workspaces\\excelTest\\src\\main\\resources\\customer-uat.xlsx");
        List<Customer> customers = excelData.readCustomers("sheet1");
        List<Customer> orderTotals = excelData.readOrderTotals("sheet2");
        List<Customer> orderCounts = excelData.readOrderCounts("sheet3");
        List<Customer> firstOrderIds = excelData.readFirstOrderIds("sheet4");
        customers.forEach(customer -> {
            Optional<Customer> optional = orderTotals.stream().filter(o -> o.userId.equals(customer.userId)).findFirst();
            customer.orderTotalCount = optional.isEmpty() ? 0 : optional.get().orderTotalCount;
        });
        customers.forEach(customer -> {
            Optional<Customer> optional = orderCounts.stream().filter(o -> o.userId.equals(customer.userId)).findFirst();
            customer.orderCount = optional.isEmpty() ? 0 : optional.get().orderCount;
        });
        customers.forEach(customer -> {
            Optional<Customer> optional = firstOrderIds.stream().filter(o -> o.userId.equals(customer.userId)).findFirst();
            customer.firstOrderIdInOrder = optional.isEmpty() ? "" : optional.get().firstOrderIdInOrder;
        });
        customers.forEach(customer -> {
            customer.correct = customer.orderCount.equals(customer.customerOrderCount)
                && customer.orderTotalCount.equals(customer.customerTotalOrderCount)
                && customer.firstOrderId.equals(customer.firstOrderIdInOrder);
        });
        customers.forEach(customer -> {
            customer.firstIdCorrect = customer.firstOrderId.equals(customer.firstOrderIdInOrder);
        });
        System.out.println((int) customers.stream().filter(customer -> !customer.correct).count());
        System.out.println((int) customers.stream().filter(customer -> !customer.firstIdCorrect).count());
        XSSFSheet newSheet = excelData.sheets.createSheet("customer");
        XSSFRow firstRow = newSheet.createRow(0);
        firstRow.createCell(0).setCellValue("user_id");
        firstRow.createCell(1).setCellValue("order_count_in_customer");
        firstRow.createCell(2).setCellValue("total_order_count_in_customer");
        firstRow.createCell(3).setCellValue("order_count_in_order");
        firstRow.createCell(4).setCellValue("order_total_count_in_order");
        firstRow.createCell(5).setCellValue("first_order_id_in_customer");
        firstRow.createCell(6).setCellValue("first_order_id_in_order");
        firstRow.createCell(7).setCellValue("is_correct");
        firstRow.createCell(8).setCellValue("id_is_correct");
        customers = customers.stream().filter(customer -> !customer.correct).collect(Collectors.toList());
        for (int i = 1; i < customers.size() + 1; i++) {
            XSSFRow row = newSheet.createRow(i);
            row.createCell(0).setCellValue(customers.get(i - 1).userId);
            row.createCell(1).setCellValue(customers.get(i - 1).customerOrderCount);
            row.createCell(2).setCellValue(customers.get(i - 1).customerTotalOrderCount);
            row.createCell(3).setCellValue(customers.get(i - 1).orderCount);
            row.createCell(4).setCellValue(customers.get(i - 1).orderTotalCount);
            row.createCell(5).setCellValue(customers.get(i - 1).firstOrderId);
            row.createCell(6).setCellValue(customers.get(i - 1).firstOrderIdInOrder);
            row.createCell(7).setCellValue(customers.get(i - 1).correct);
            row.createCell(8).setCellValue(customers.get(i - 1).firstIdCorrect);
        }
        File file = new File("C:\\workspaces\\excelTest\\src\\main\\resources\\customers-uat.xlsx");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            excelData.sheets.write(fos);
            fos.close();
            excelData.sheets.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Customer {
        public String userId;
        public Double customerTotalOrderCount;
        public Double customerOrderCount;
        public Double orderTotalCount;
        public Double orderCount;
        public String firstOrderId;
        public String firstOrderIdInOrder;
        public Boolean correct;
        public Boolean firstIdCorrect;
    }
}
