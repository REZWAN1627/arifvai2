package com.selenium.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
	private Workbook wb = null;

	public void openWorkbook(String fileName) throws IOException {
		FileInputStream inputStream = new FileInputStream(fileName);
		String fileExtensionName = fileName.substring(fileName.indexOf(".x"));
		if (fileExtensionName.equals(".xlsx")) {
			wb = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			wb = new HSSFWorkbook(inputStream);
		} else {
			invalidFile();
		}
	}

	public Workbook getWorkbook(String fileName) {
		return wb;
	}

	public int getNumberOfSheets() {
		return wb.getNumberOfSheets();
	}

	public String getSheetName(int x) {
		return wb.getSheetName(x);
	}

	public Sheet getSheetByName(String xlSheetName) {
		return wb.getSheet(xlSheetName);
	}

	public Sheet getSheetByIndex(int x) {
		return wb.getSheetAt(x);
	}

	public int getRowCount(String xlSheetName) {
		return wb.getSheet(xlSheetName).getLastRowNum();
	}

	public int getColumnCount(String xlSheetName) {
		return wb.getSheet(xlSheetName).getRow(0).getLastCellNum();
	}

	public void closeWorkbook() throws IOException {
		wb.close();
	}

	public String getCellValue(String xlSheetName, int row, int col) {
		String xlCellValue = null;

		try {
			xlCellValue = wb.getSheet(xlSheetName).getRow(row).getCell(col).getStringCellValue();
			if (xlCellValue.length() == 0) {
				xlCellValue = "[BLANK]";
			}
		} catch (Exception e) {
			xlCellValue = "[BLANK]";
		}
		return xlCellValue;
	}

	public static String getExcelDataByReference(String fileName, String xlSheetName, String reference,
			int referenceColumn, int targetColumn) throws IOException {
		String data = null;
		Excel xl = new Excel();
		xl.openWorkbook(fileName);
		Iterator<Row> row = xl.getSheetByName(xlSheetName).iterator();
		int i = 0;
		while (row.hasNext()) {
			row.next();
			i++;
			if (xl.getCellValue(xlSheetName, i, referenceColumn).equals(reference)) {
				data = xl.getCellValue(xlSheetName, i, targetColumn);
				break;
			}
		}

		xl.closeWorkbook();
		return data;
	}

	public String getExcelData(Excel xl, String xlSheetName, String reference, String TargetColumn) throws IOException {
		String xlCellValue = null;
		int checkSheet = 0;
		int totalSheets = wb.getNumberOfSheets();

		for (int i = 0; i < totalSheets; i++) {
			String curSheetName = wb.getSheetName(i);
			if (curSheetName.equalsIgnoreCase(xlSheetName)) {
				checkSheet = 1;
				break;
			} else {
				checkSheet = 0;
			}
		}

		if (checkSheet == 1) {
			outerloop: 
			for (int i = 0; i < xl.getColumnCount(xlSheetName); i++) {
				if (TargetColumn.equalsIgnoreCase(xl.getCellValue(xlSheetName, 0, i))) {
					for (int j = 0; j < xl.getRowCount(xlSheetName); j++) {
						String xlRefValue = xl.getCellValue(xlSheetName, j, 0);
						if (reference.equalsIgnoreCase(xlRefValue)) {
							xlCellValue = xl.getCellValue(xlSheetName, j, i);
							if (xlCellValue.length() == 0) {
								xlCellValue = null;
							}
							break outerloop;
						}
					}
				}
			}
		}
		return xlCellValue;
	}

	public void invalidFile() {
		throw new IllegalArgumentException("The specified file is not an Excel file");
	}

}
