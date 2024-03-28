package com.selenium.engine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.os.WindowsUtils;

import com.selenium.elements.ElementDictionary;
import com.selenium.utilities.Excel;
import com.selenium.utilities.Reports;
import com.selenium.utilities.Step;

public class TestRunner {
	public static ElementDictionary actionKeywords;
	public static int chkData;
	public static int dataCol;
	public static int dataIdentifier;
	public static final String PROJ_DIR = System.getProperty("user.dir");
	public static final String TEST_DATA_DIR = PROJ_DIR + "\\test\\data\\";
	public static final String OBJECT_REPOSITORY_DIR = PROJ_DIR + "\\test\\objects\\";
	public static String currentTC;
	public static String currentPage;
	public static String test_root_dir;
	public static String test_logs_dir;
	public static String test_reports_dir;
	public static String TestStepName;
	public static String data;
	public static String dataElement;
	public static Excel xl;
	public static Method method[];
	public static Step stepTE;
	public static Step stepTC;
	public static Step stepTS;
	public static Map<Integer, Step> mapTE;
	public static Map<Integer, Step> mapTC;
	public static Map<Integer, Step> mapTS;
	public static Map<String, String> mapStore;
	public static int status;

	public static void main(String[] args) throws Exception {
		try {
			status = 1;
			Excel xlConfig = new Excel();
			xl = new Excel();
			xlConfig.openWorkbook(PROJ_DIR + "\\config\\config.xlsx");
			for (int i = 0; i < xlConfig.getColumnCount("config"); i++) {
				switch (xlConfig.getCellValue("config", 0, i).toLowerCase()) {
				case "testrootdirectory":
					test_root_dir = reformatPath(xlConfig.getCellValue("config", 1, i));
					test_logs_dir = test_root_dir + "logs\\";
					test_reports_dir = test_root_dir + "report\\";
					break;
				default:
					break;
				}
			}
			xlConfig.closeWorkbook();
			createDirectories();

			if (args.length == 1) {
				try {
					xl.openWorkbook(TEST_DATA_DIR + args[0] + ".xlsx");
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			} else {
				// load default test script
				try {
					xl.openWorkbook(TEST_DATA_DIR + "\\default.xlsx");
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}

			excelMapping(xl);
			xl.closeWorkbook();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			
			System.out.println("Test Component: " + currentTC);
			System.out.println("Test Page: " + currentPage);
			System.out.println("Locator: " + stepTS.action);
			if (stepTS.element != ("[BLANK]")) {
				//System.out.println("Element : " + ElementDictionary.getElementProperty(sTS.element));
			}
			System.out.println("\n\nStatus : Failed");
			//Reports.terminateReport();
			System.exit(1);
		}
	}

	private static void excelMapping(Excel xl) throws Exception {
		String wsTE = xl.getSheetByName("TestEnvironment").getSheetName();
		String wsTC = xl.getSheetByName("TestCase").getSheetName();
		String wsTS = xl.getSheetByName("TestSteps").getSheetName();

		// Store all data from Test Environment
		mapTE = new HashMap<Integer, Step>();
		Iterator<Row> rowTE = xl.getSheetByName("TestEnvironment").iterator();
		int stepNumber = 0;
		while (rowTE.hasNext()) {
			stepNumber++;
			rowTE.next();
			if (!"[BLANK]".equals(xl.getCellValue(wsTE, stepNumber, 0))) {
				stepTE = new Step(xl.getCellValue(wsTE, stepNumber, 0), // Application Name
						xl.getCellValue(wsTE, stepNumber, 1), // URL
						xl.getCellValue(wsTE, stepNumber, 2), // Execute
						xl.getCellValue(wsTE, stepNumber, 3), // Browser
						xl.getCellValue(wsTE, stepNumber, 4), // Object Repository Location
						xl.getCellValue(wsTE, stepNumber, 5), // Platform
						xl.getCellValue(wsTE, stepNumber, 6));  // Build Version
				mapTE.put(stepNumber, stepTE);
			}
		}

		// Store all date from Test Case
		mapTC = new HashMap<Integer, Step>();
		Iterator<Row> rowTC = xl.getSheetByName("TestCase").iterator();
		stepNumber = 0;
		while (rowTC.hasNext()) {
			stepNumber++;
			rowTC.next();
			if (!"[BLANK]".equals(xl.getCellValue(wsTC, stepNumber, 0))) {
				stepTC = new Step(xl.getCellValue(wsTC, stepNumber, 0), // Test Case Id
						xl.getCellValue(wsTC, stepNumber, 1), // Test Case Name
						xl.getCellValue(wsTC, stepNumber, 2), // Execute Test Case
						xl.getCellValue(wsTC, stepNumber, 3), // Data Set
						xl.getCellValue(wsTC, stepNumber, 4)); // Data Set Details
				mapTC.put(stepNumber, stepTC);
			}
		}

		// Store all date from Test Step
		mapTS = new HashMap<Integer, Step>();
		Iterator<Row> row = xl.getSheetByName("TestSteps").iterator();
		stepNumber = 0;
		while (row.hasNext()) {
			stepNumber++;
			row.next();
			if (!"[BLANK]".equals(xl.getCellValue(wsTS, stepNumber, 3))) {
				stepTS = new Step(String.valueOf(stepNumber), // Step
						xl.getCellValue(wsTS, stepNumber, 0), // Test Component
						xl.getCellValue(wsTS, stepNumber, 1), // Page Name
						xl.getCellValue(wsTS, stepNumber, 2), // Element
						xl.getCellValue(wsTS, stepNumber, 3), // Action
						// xl.getCellValue(wsTS, stepNumber, 4), // Default Parameter
						xl.getCellValue(wsTS, stepNumber, 4), // Param1
						xl.getCellValue(wsTS, stepNumber, 5), // Param2
						xl.getCellValue(wsTS, stepNumber, 6), // Param3
						xl.getCellValue(wsTS, stepNumber, 7)); // Param4
				mapTS.put(stepNumber, stepTS);
			}
		}
		
		mapStore = new HashMap<String, String>();
		runTestEnvironment(xl);
	}

	private static void runTestEnvironment(Excel xl) throws Exception {
		for (Map.Entry<Integer, Step> entry : mapTE.entrySet()) {
			stepTE = entry.getValue();
			if (stepTE.execute.equalsIgnoreCase("Y")) {
				killProcess(stepTE.browser);
				Reports.initReport(stepTE.applicationName, stepTE.applicationName, stepTE.appVersion);
				ElementDictionary.setObjectRepository(stepTE);
				runTestCase(xl);
			}
		}
	}

	private static void runTestCase(Excel xl) throws Exception {
		dataIdentifier = 0;
		for (Map.Entry<Integer, Step> entry : mapTC.entrySet()) {
			stepTC = entry.getValue();
			if (stepTC.executeTestCase.equalsIgnoreCase("Y")) {
				Reports.createTest(stepTC.testCaseName);
				System.out.println("Test Case: " + stepTC.testCaseName);
				ElementDictionary.launchApplication(stepTE);
				if (stepTC.dataSet!="[BLANK]") {
					dataIdentifier = 1;
				}
				runTestStepMapping(xl);
				ElementDictionary.closeApplication();
			}
		}
		Reports.terminateReport();
		if (status!=0) {
			System.out.println("Overall Status : PASSED");
		} else {
			System.out.println("Overall Status : FAILED");
		}
	}

	private static void runTestStepMapping(Excel xl) throws Exception {
		String wsTS = xl.getSheetByName("TestStepMapping").getSheetName();

		for (int i = 0; i < xl.getColumnCount(wsTS); i++) {
			if (stepTC.testCaseID.equalsIgnoreCase(xl.getCellValue(wsTS, 0, i))) {
				for (int j = 2; j < xl.getSheetByName(wsTS).getLastRowNum(); j++) {
					if (!"[BLANK]".equalsIgnoreCase(xl.getCellValue(wsTS, j, i))) {
						TestStepName = xl.getCellValue(wsTS, j, i);
						runTestStep(xl);
					} else if ("[BLANK]".equalsIgnoreCase(xl.getCellValue(wsTS, j, i))) {
						break;
					}
				}
				break;
			}
		}
	}

	private static void runTestStep(Excel xl) throws Exception {
		for (Map.Entry<Integer, Step> entry : mapTS.entrySet()) {
			
			actionKeywords = new ElementDictionary();
			method = actionKeywords.getClass().getMethods();
			
			stepTS = entry.getValue();
			Boolean methodFound = false;

			if (!"[BLANK]".equalsIgnoreCase(stepTS.testComponent)) {
				ElementDictionary.setCurrentTestComponent(stepTS);
				currentTC = ElementDictionary.setCurrentTestComponent(stepTS);
			}

			if (!"[BLANK]".equalsIgnoreCase(stepTS.page)) {
				ElementDictionary.setCurrentPage(stepTS);
				currentPage = ElementDictionary.setCurrentPage(stepTS);
			}

			if (TestStepName.equalsIgnoreCase(ElementDictionary.testComponent)) {
				for (int i = 1; i <= method.length; i++) {
					if (method[i].getName().equalsIgnoreCase(stepTS.action.replace(" ", ""))) {
						if (dataIdentifier!=0) {
							dataElement = currentPage + "." + stepTS.element;
							String testData = ElementDictionary.getTestData(xl, "Data", dataElement, stepTC.dataSet);
							if (testData != null && testData != "[BLANK]") {
								stepTS.param1 = testData;
							}
						}
						methodFound = true;
						method[i].invoke(method[i].getName(), stepTS);
						break;
					} else {
						methodFound = false;
					}
				}

				if (methodFound == false) {
					System.out.println(stepTS.action + " keyword is not recognized");
					System.out.println("\n\nStatus : Failed");

					Reports.testFail("Test Step " + stepTS.step + " " + stepTS.action + "keyword is not recognized");
					ElementDictionary.driver.close();
				}
			}
		}
		// System.exit(0);
	}
	private static void killProcess(String browser) throws IOException, InterruptedException {
		switch (browser) {
		case "Chrome":
			WindowsUtils.killByName("chromedriver.exe");
			break;
		case "Firefox":
			WindowsUtils.killByName("gecko.exe");
			break;
		case "IE":
			WindowsUtils.killByName("IEDriverServer.exe");
			break;
		default:
			WindowsUtils.killByName("chromedriver.exe");
			break;
		}
	}

	public static String reformatPath(String path) {
		if (!path.substring(path.length() - 1).equals("\\")) {
			path = path + "\\";
		}
		return path;
	}

	public static void createDirectories() {
		// create default directories
		createDirectory(test_root_dir);
		createDirectory(TEST_DATA_DIR);
		createDirectory(test_reports_dir);
		createDirectory(OBJECT_REPOSITORY_DIR);
	}
	
	// Create Directory
	public static void createDirectory(String directoryName) {
		File directory = new File(directoryName);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}
}
