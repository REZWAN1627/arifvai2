package com.selenium.utilities;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.selenium.elements.ElementDictionary;
import com.selenium.engine.TestRunner;

public class Reports {
	public static TestRunner test_reports_dir; 
	public static String htmlReportName;
	public static String scrPath;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static DateFormat dateFormat;
	public static Date date;

	public static void initReport(String projectName, String testCaseName, String appVersion) {

		dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
		date = new Date();
		
		if (appVersion.equalsIgnoreCase("[BLANK]")) {
			scrPath = TestRunner.test_reports_dir + projectName + "\\" + testCaseName + "-" + dateFormat.format(date) + "\\";
			TestRunner.createDirectory(scrPath);
			htmlReportName = TestRunner.test_reports_dir + projectName + "\\" + testCaseName + "-" + dateFormat.format(date) + ".html";
		} else {
			scrPath = TestRunner.test_reports_dir + projectName + "\\" + appVersion + "\\" + testCaseName + "-" + dateFormat.format(date) + "\\";
			TestRunner.createDirectory(scrPath);
			htmlReportName = TestRunner.test_reports_dir + projectName + "\\" + appVersion + "\\" + testCaseName + "-" + dateFormat.format(date) + ".html";
		}
		
		
		htmlReporter = new ExtentHtmlReporter(htmlReportName);
		htmlReporter.config().setReportName("Selenium Automation");
		htmlReporter.config().setDocumentTitle(testCaseName);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	}

	public static void createTest(String TestCase) {
		System.out.println("===============================================================================\n");
		test = extent.createTest(TestCase);
		System.out.println("Test Case report initialize for " + TestCase);
	}

	public static void testPass(String desc) {
		test.pass(desc);
		System.out.println(desc + " - Pass");
	}

	public static void testFail(String desc) throws Exception {
		test.fail(desc);
		ElementDictionary.captureScreenshot(TestRunner.stepTS);
		System.out.println(desc + " - Fail");
	}

	public static void terminateReport() {
		extent.flush();
		System.out.println("Report generated - " + htmlReportName.replace("//", "\\"));
	}
	
	public static void testInfo(String desc) {
		test.info(desc);
		System.out.println("Test Component: " + desc);
	}

	public static void testSCR(String desc) throws IOException {
		test.addScreenCaptureFromPath(desc);
		System.out.println("Capture Screenshot:  " + desc);
	}
}
