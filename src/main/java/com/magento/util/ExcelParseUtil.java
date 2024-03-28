package com.magento.util;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExcelParseUtil {

    public static Object[][] geClientData(int rowNumber) {
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(Files.newInputStream
                    (Paths.get("C:\\Users\\REX\\Desktop\\arifvai2\\src\\main\\java\\com\\magento\\data\\NEW_ACCOUNT_TEST_DATA.xlsx")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFRow row = sheet.getRow(rowNumber);

        String firstName = getValue(row, 0);
        String lastName = getValue(row, 1);
        String email = getValue(row, 2);
        String password = getValue(row, 3);
        String confirmPass = getValue(row, 4);

        Object[][] data = new Object[1][5];

        data[0][0] = firstName;
        data[0][1] = lastName;
        data[0][2] = email;
        data[0][3] = password;
        data[0][4] = confirmPass;

        return data;


    }

    private static String getValue(XSSFRow row, int position) {
        return row.getCell(position).getStringCellValue().trim();
    }

    public static Object[][] getRegisterClientData(int rowNumber) {
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(Files.newInputStream
                    (Paths.get("C:\\Users\\REX\\Desktop\\arifvai2\\src\\main\\java\\com\\magento\\data\\Registered_client.xlsx")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFRow row = sheet.getRow(rowNumber);

        String firstName = getValue(row, 0);
        String lastName = getValue(row, 1);
        String email = getValue(row, 2);
        String password = getValue(row, 3);
        String confirmPass = getValue(row, 4);

        Object[][] data = new Object[1][5];

        data[0][0] = firstName;
        data[0][1] = lastName;
        data[0][2] = email;
        data[0][3] = password;
        data[0][4] = confirmPass;

        return data;
    }

    public static Object[][] getSearchData(int rowNumber) {
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(Files.newInputStream
                    (Paths.get("C:\\Users\\REX\\Desktop\\arifvai2\\src\\main\\java\\com\\magento\\data\\search_criteria.xlsx")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFRow row = sheet.getRow(rowNumber);

        String firstName = getValue(row, 0);

        Object[][] data = new Object[1][1];

        data[0][0] = firstName;

        return data;
    }

    public static Object[][] getQuantity(int rowNumber) {
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(Files.newInputStream
                    (Paths.get("C:\\Users\\REX\\Desktop\\arifvai2\\src\\main\\java\\com\\magento\\data\\product_quantity.xlsx")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFRow row = sheet.getRow(rowNumber);

        String firstName = getValue(row, 0);

        Object[][] data = new Object[1][1];

        data[0][0] = firstName;

        return data;
    }

    public static Object[][] getUpdateQuantity(int rowNumber) {
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(Files.newInputStream
                    (Paths.get("C:\\Users\\REX\\Desktop\\arifvai2\\src\\main\\java\\com\\magento\\data\\quantity_update.xlsx")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFRow row = sheet.getRow(rowNumber);

        String firstProd = getValue(row, 0);
        String secondProd = getValue(row, 1);

        Object[][] data = new Object[1][2];

        data[0][0] = firstProd;
        data[0][1] = secondProd;

        return data;
    }
}
