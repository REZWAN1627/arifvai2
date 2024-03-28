package com.selenium.elements;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.engine.TestRunner;
import com.selenium.utilities.Excel;
import com.selenium.utilities.Keyboard;
import com.selenium.utilities.Reports;
import com.selenium.utilities.Step;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ElementDictionary {
	static String ieWebDriverPath = TestRunner.PROJ_DIR + "//driver//IEDriverServer.exe";
	static String geckoWebDriverPath = TestRunner.PROJ_DIR + "//driver//geckodriver.exe";
	static String chromeWebDriverPath = TestRunner.PROJ_DIR + "//driver//chromedriver.exe";
	public static int counterScreenshot = 1;
	private static int step = 1;
	private static String parent = null, newwin = null;
	private static String errorMessage;
	private static String object_repository;
	public static String page;
	public static String testComponent;
	public static String testData;
	public static String storeTestData;
	public static String policyNo;
	public static WebDriver driver;
	public static Keyboard ks;

	// Log pass result
	private static void logPassToConsole(Step s) {
		try {
			System.out.println("\n-------------------------------------------------------------------------------\n");
			String stepString = String.format("%03d", step);
			System.out.println("Test Step " + stepString);
			System.out.println("Test Component: " + TestRunner.currentTC);
			System.out.println("Test Page: " + TestRunner.currentPage);
			System.out.println("Locator: " + s.action);
			System.out.println("Element Name : " + s.element);
			System.out.println("Element Locator : " + getLocatorID(s.element));
			System.out.println("Element ID: " + getElementProperty(s.element));
			if (s.param1 != ("[BLANK]")) {
				System.out.println("Parameter : " + s.param1);
			}
			System.out.println("Status : PASS");

			step++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Error Message
	private static void errorEncountered(Step s) throws Exception {
		String stepString = String.format("%03d", step);
		System.out.println("Test Step " + stepString);
		System.out.println("Test Component: " + TestRunner.currentTC);
		System.out.println("Test Page: " + TestRunner.currentPage);
		System.out.println("Locator: " + s.action);
		if (s.element != ("[BLANK]")) {
			System.out.println("Element : " + getElementProperty(s.element));
		}
		if (s.param1 != ("[BLANK]")) {
			System.out.println("Parameter : " + s.param1);
		}
		System.out.println("Error Message: " + errorMessage);
		System.out.println("Status : FAILED");
		Reports.testFail(errorMessage);
		// captureScreenshot(s);
		TestRunner.status = 0;
		step++;
	}

	// Launch Application
	@SuppressWarnings("deprecation")
	public static void launchApplication(Step s) throws Exception {
		try {
			switch (s.browser) {
			case "Chrome":
				driver = null;
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
				driver.manage().window().maximize();
				driver.get(s.url);
				if (driver.getTitle().contains("Certificate")) {
					driver.navigate().to("javascript:document.getElementById('overridelink').click()");
				}
				Reports.testPass("URL: " + s.url + " is launched using " + s.browser);
				break;
			case "Firefox":
				driver = null;
				System.setProperty("webdriver.gecko.driver", geckoWebDriverPath);
				driver = new FirefoxDriver();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
				driver.manage().window().maximize();
				driver.get(s.url);
				if (driver.getTitle().contains("Certificate")) {
					driver.navigate().to("javascript:document.getElementById('overridelink').click()");
				}
				Reports.testPass("URL: " + s.url + " is launched using " + s.browser);
				break;
			}

		} catch (Exception e) {
			errorMessage = "Webdriver was not found";
			System.out.println(errorMessage + " - " + e.getMessage());
			System.out.println("Status : Failed");
			Reports.testFail(errorMessage);
			Reports.terminateReport();
		}
	}

	private static WebElement UIElement(String s) throws IOException {
		WebElement webElement = null;

		switch (getLocatorID(s)) {
		case "id":
			webElement = driver.findElement(By.id(getElementProperty(s)));
			break;
		case "name":
			webElement = driver.findElement(By.name(getElementProperty(s)));
			break;
		case "xpath":
			webElement = driver.findElement(By.xpath(getElementProperty(s)));
			break;
		case "className":
			webElement = driver.findElement(By.className(getElementProperty(s)));
			break;
		case "cssSelector":
			webElement = driver.findElement(By.cssSelector(getElementProperty(s)));
			break;
		case "linkText":
			webElement = driver.findElement(By.linkText(getElementProperty(s)));
			break;
		case "partialLinkText":
			webElement = driver.findElement(By.partialLinkText(getElementProperty(s)));
			break;
		case "tagName":
			webElement = driver.findElement(By.tagName(getElementProperty(s)));
			break;
		default:
			System.out.println("Selected Locator By: " + getElementProperty(s) + " is invalid.");
			break;
		}
		return webElement;
	}

	private static By UIBy(String s) throws IOException {
		By byElement = null;

		switch (getLocatorID(s)) {
		case "id":
			byElement = By.id(getElementProperty(s));
			break;
		case "name":
			byElement = By.name(getElementProperty(s));
			break;
		case "xpath":
			byElement = By.xpath(getElementProperty(s));
			break;
		case "className":
			byElement = By.className(getElementProperty(s));
			break;
		case "cssSelector":
			byElement = By.cssSelector(getElementProperty(s));
			break;
		case "linkText":
			byElement = By.linkText(getElementProperty(s));
			break;
		case "partialLinkText":
			byElement = By.partialLinkText(getElementProperty(s));
			break;
		case "tagName":
			byElement = By.tagName(getElementProperty(s));
			break;
		default:
			System.out.println("Selected Locator By: " + getElementProperty(s) + " is invalid.");
			break;
		}
		return byElement;
	}

	// Accept Alerts
	public static void acceptAlert(Step s) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			Thread.sleep(3000);
			alert.accept();
			logPassToConsole(s);
			Reports.testPass("Alert is accepted");
		} catch (Exception e) {
			errorMessage = "No Alert was not found";
			errorEncountered(s);
		}
	}

	// CaptureScreenshot
	public static void captureScreenshot(Step s) throws Exception {
		BufferedImage image = new Robot()
				.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		String imageFilename = Reports.scrPath + "Screenshot - " + counterScreenshot + ".png";

		TestRunner.createDirectory(imageFilename);

		File file = new File(imageFilename);
		ImageIO.write(image, "png", file);
		InputStream pic = new FileInputStream(imageFilename);
		pic.close();
		Reports.testSCR(imageFilename);
		counterScreenshot++;
	}

	// Click specific value (xpath only)
	public static void clickText(Step s) throws Exception {
		String xpath = getElementProperty(s.element);
		WebElement element = null;

		try {
			if (s.param1.contains("${")) {
				String revText = s.param1.replace("${", "");
				revText = xpath.replace("}", "");

				for (Entry<String, String> entry : TestRunner.mapStore.entrySet()) {
					String text = entry.getKey();
					if (text.equals(revText)) {
						xpath = xpath.replace("%s", entry.getValue());
					}
				}

				element = driver.findElement(By.xpath(xpath));
			} else {
				element = UIElement(s.element);
			}
			element.click();
			logPassToConsole(s);
			Reports.testPass("Element " + s.element + " is clicked");
		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found";
			errorEncountered(s);
		}
	}

	// Click Specific Link (For xpath only)
	public static void clickSpecificData(Step s) throws Exception {
		String xpath = getElementProperty(s.element);
		WebElement element = null;

		try {
			if (s.param1.contains("${")) {
				String revText = s.param1.replace("${", "");
				revText = xpath.replace("}", "");

				for (Entry<String, String> entry : TestRunner.mapStore.entrySet()) {
					String text = entry.getKey();
					if (text.equals(revText)) {
						xpath = xpath.replace("%s", entry.getValue());
					}
				}
				element = driver.findElement(By.xpath(xpath));
			} else {
				xpath = xpath.replace("%s", s.param1);
				element = driver.findElement(By.xpath(xpath));
			}

			element.click();
			logPassToConsole(s);
			Reports.testPass("Element " + s.element + " is clicked");

		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found";
			errorEncountered(s);
		}
	}

	// Click Button, Checkbox, Link
	public static void clickButton(Step s) throws Exception {
		WebElement element = UIElement(s.element);
		try {
			element.click();
			logPassToConsole(s);
			Reports.testPass("Element " + s.element + " is clicked");
		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found";
			errorEncountered(s);
		}
	}

	// Close Application
	public static void closeApplication() throws Exception {
		try {
			driver.quit();
			System.out.println("\n-------------------------------------------------------------------------------\n");
			Reports.testPass("Closing application");
			System.out.println("\n-------------------------------------------------------------------------------\n");
		} catch (Exception e) {
			System.out.println("\n-------------------------------------------------------------------------------\n");
			errorMessage = "Browser was not found";
			System.out.println("\n-------------------------------------------------------------------------------\n");
			System.out.println(errorMessage + " - " + e.getMessage());
			driver.close();
		}
	}

	// Close Current Window
	public static void closeCurrentWindow(Step s) throws Exception {
		try {
			driver.close();
			Reports.testPass("Current Window is closed");
			System.out.println("Current window is closed");
		} catch (Exception e) {
			errorMessage = "Browser was not found";
			errorEncountered(s);
		}
	}

	// Double Click
	public static void doubleClick(Step s) throws Exception {
		WebElement element = UIElement(s.element);
		Actions action = new Actions(driver);
		try {
			action.doubleClick(element).perform();
			logPassToConsole(s);
			Reports.testPass("Element " + s.element + " is doubled clicked");

		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found";
			errorEncountered(s);
		}

	}

	public static int getDynamicWaitTime() {
		String value = System.getProperty("DynamicWaitTime");
		int waitTime = Integer.parseInt(value);
		return waitTime;
	}

	// Get Text
	public static void getText(Step s) throws Exception {
		String value = null;
		try {
			WebElement element = UIElement(s.element);
			value = element.getText();
			TestRunner.mapStore.put(s.param1, value);
			logPassToConsole(s);
			System.out.println("Stored: " + value);
			Reports.testPass(s.param1 + ": " + value);
		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found to store text";
			errorEncountered(s);
		}
	}

	// Get Text & remove white spaces
	public static void getTextRmSpaces(Step s) throws Exception {
		String value = null;
		try {
			WebElement element = UIElement(s.element);
			value = element.getText();
			value = value.replaceAll("\\s+", "");
			TestRunner.mapStore.put(s.param1, value);
			logPassToConsole(s);
			System.out.println("Stored: " + value);
			Reports.testPass(s.param1 + ": " + value);
		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found to store text";
			errorEncountered(s);
		}
	}

	// Get Value
	public static void getValue(Step s) throws Exception {
		String value = null;
		try {
			WebElement element = UIElement(s.element);
			value = element.getAttribute("value");
			TestRunner.mapStore.put(s.param1, value);
			logPassToConsole(s);
			System.out.println("Stored: " + value);
			Reports.testPass(s.param1 + ": " + value);

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			Object aa = executor.executeScript(
					"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
					element);
			System.out.println(aa.toString());

		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found to store text";
			errorEncountered(s);
		}

	}

	// Get Value & remove white spaces
	public static void getValueRmSpace(Step s) throws Exception {
		String value = null;
		try {
			WebElement element = UIElement(s.element);
			value = element.getAttribute("value");
			value = value.replaceAll("\\s+", "");
			TestRunner.mapStore.put(s.param1, value);
			logPassToConsole(s);
			System.out.println("Stored: " + value);
			Reports.testPass(s.param1 + ": " + value);

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			Object aa = executor.executeScript(
					"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
					element);
			System.out.println(aa.toString());

		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found to store text";
			errorEncountered(s);
		}

	}

	// Generate Random Number
	public static void generateRandomNumber(Step s) throws Exception {
		WebElement element = UIElement(s.element);
		int rMin = Integer.parseInt(s.param2);
		int rMax = Integer.parseInt(s.param3);
		try {
			Random r = new Random();
			String randomNumber = String.format("%01d", r.nextInt((rMax - rMin) + 1) + rMin);

			element.sendKeys(randomNumber);

			TestRunner.mapStore.put(s.param1, randomNumber);
			Reports.testPass("Number is set to " + randomNumber);
			logPassToConsole(s);

		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found";
			errorEncountered(s);
		}
	}

	// Get Element Property
	public static String getElementProperty(String elementName) throws IOException {
		String element = Excel.getExcelDataByReference(TestRunner.OBJECT_REPOSITORY_DIR + object_repository + ".xlsx",
				page, elementName, 0, 2);
		// System.out.println("DEBUG: Element is equal to " + element);
		return element;
	}

	// Get Locator ID
	private static String getLocatorID(String elementName) throws IOException {
		// System.out.println();
		String locator = Excel.getExcelDataByReference(TestRunner.OBJECT_REPOSITORY_DIR + object_repository + ".xlsx",
				page, elementName, 0, 1);
		return locator;
	}

	// Get Test Data
	public static String getTestData(Excel xl, String xlSheetName, String dataElement, String dataSet)
			throws IOException {
		// System.out.println();
		testData = xl.getExcelData(xl, xlSheetName, dataElement, dataSet);
		return testData;
	}

	// Get Window ID
	public static String getWindowID(Step s) throws Exception {
		String current_active_window = null;
		try {
			current_active_window = driver.getWindowHandle();
		} catch (Exception e) {
			errorMessage = "Browser was not found";
			errorEncountered(s);
		}
		return current_active_window;
	}

	// Go To URL
	public static void goToURL(Step s) throws Exception {
		try {
			driver.navigate().to(s.param1);
			logPassToConsole(s);
			Reports.testPass("Navigate to " + s.param1 + " URL is successful");
		} catch (Exception e) {
			errorMessage = "Browser was not found";
			errorEncountered(s);
		}
	}

	// Hover Menu
	public static void hoverMenu(Step s) throws Exception {
		WebElement element = UIElement(s.element);
		try {
			Actions builder = new Actions(driver);
			// builder.moveToElement(menu).build().perform();
			builder.moveToElement(element).perform();
			logPassToConsole(s);
			Reports.testPass("Element " + s.element + " is hovered ");
		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found";
			errorEncountered(s);
		}
	}

	// Override link
	public static void overrideLink(Step s) {
		driver.navigate().to("javascript:document.getElementById('overridelink').click()");
	}

	// Select Check Box using text beside the checkbox that was an input in the
	public static void selectCheckBox(Step s) throws Exception {
		String xpath = getElementProperty(s.element);
		String Text = s.param1;
		String finalXPath = "//td[contains(text(),'" + Text + "')]/preceding-sibling::td/" + xpath;
		try {
			WebElement checkBox = driver.findElement(By.xpath(finalXPath.trim()));
			checkBox.click();
			logPassToConsole(s);
			Reports.testPass("Checkbox " + s.element + " is ticked");
		} catch (Exception e) {
			errorMessage = "Checkbox " + s.element + " was not found";
			errorEncountered(s);
		}

	}

	// Select More Specific Value From drop-down
	public static void selectFromDropdown(Step s) throws Exception {
		try {
			WebElement drop = UIElement(s.element);
			drop.click();
			// drop.click();
			WebElement val = drop.findElement(By.xpath("./*[contains(text(), '" + s.param1 + "')]"));
			val.click();
			logPassToConsole(s);
			Reports.testPass("Dropdown " + s.element + " is selected");
		} catch (Exception e) {
			errorMessage = "Dropdown " + s.element + " was not found";
			errorEncountered(s);
		}

	}

	// Select From List
	public static void selectFromList(Step s) throws Exception {
		String xpath = getElementProperty(s.element);
		try {
			WebElement drop = driver.findElement(By.xpath(xpath));
			// new Select(element).selectByValue(s.param1);
			// drop.click();
			WebElement val = drop.findElement(By.xpath("//*[contains(text(), '" + s.param1 + "')]"));
			val.click();
			logPassToConsole(s);
			Reports.testPass("Listbox " + s.element + " is selected");
		} catch (Exception e) {
			errorMessage = "Listbox " + s.element + " was not found";
			errorEncountered(s);
		}

	}

	// Set Current Page
	public static String setCurrentPage(Step s) {
		return page = s.page;
	}

	// Set Current Test Component
	public static String setCurrentTestComponent(Step s) {
		return testComponent = s.testComponent;
	}

	// Set Object Repository
	public static String setObjectRepository(Step s) {
		return object_repository = s.objectRepository;
	}

	// Set Robot Text
	public static void setRobotText(Step s) throws Exception {
		// String message = null;
		String Text = String.format("%s", s.param1);
		storeTestData = String.format("%s", s.param1);
		// robot = new Robot();
		ks = new Keyboard();

		try {
			// message = s.param1;

			if (Text.contains("${")) {
				Text = Text.replace("${", "");
				Text = Text.replace("}", "");

				for (Entry<String, String> entry : TestRunner.mapStore.entrySet()) {
					String key = entry.getKey();
					if (key.equals(Text)) {
						Text = String.format("%s", entry.getValue());
					}
				}
			}

			ks.type(Text);
			logPassToConsole(s);
			Reports.testPass("Element " + s.element + " is set to " + Text);

		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not cleared";
			errorEncountered(s);
		}

	}

	// Set Text
	public static void setText(Step s) throws Exception {
		WebElement element = UIElement(s.element);
		String Text = String.format("%s", s.param1);
		storeTestData = String.format("%s", s.param1);
		try {
			element.clear();

			if (Text.contains("${")) {
				Text = Text.replace("${", "");
				Text = Text.replace("}", "");

				for (Entry<String, String> entry : TestRunner.mapStore.entrySet()) {
					String key = entry.getKey();
					if (key.equals(Text)) {
						Text = String.format("%s", entry.getValue());
					}
				}
			}

			element.sendKeys(Text);
			logPassToConsole(s);
			Reports.testPass("Textbox " + s.element + " is populated with " + s.param1);
		} catch (Exception e) {
			errorMessage = "Textbox " + s.element + " was not found";
			errorEncountered(s);
		}

	}

	// Set Text w/o Clear
	public static void setTextWOClear(Step s) throws Exception {
		WebElement element = UIElement(s.element);
		String Text = String.format("%s", s.param1);
		storeTestData = String.format("%s", s.param1);
		try {
			if (Text.contains("${")) {
				Text = Text.replace("${", "");
				Text = Text.replace("}", "");

				for (Entry<String, String> entry : TestRunner.mapStore.entrySet()) {
					String key = entry.getKey();
					if (key.equals(Text)) {
						Text = String.format("%s", entry.getValue());
					}
				}
			}

			element.sendKeys(Text);
			logPassToConsole(s);
			Reports.testPass("Textbox " + s.element + " is populated with " + s.param1);
		} catch (Exception e) {
			errorMessage = "Textbox " + s.element + " was not found";
			errorEncountered(s);
		}

	}

	// Store Text from Input
	public static void storeTextFromInput(Step s) throws Exception {
		String value = storeTestData;
		try {
			TestRunner.mapStore.put(s.param1, value);
			logPassToConsole(s);
			System.out.println("Stored: " + value);
			Reports.testPass(s.param1 + ": " + value);
		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found to store text";
			errorEncountered(s);
		}
	}

	// Switch Frame
	public static void switchFrame(Step s) throws Exception {
		try {
			WebElement iframe = driver.findElement(By.name(s.param1));
			driver.switchTo().frame(iframe);
			System.out.println("Switched to Frame - " + s.param1 + "\n");
		} catch (Exception e) {
			errorMessage = "Frame " + s.element + " was not found";
			errorEncountered(s);
		}

	}

	// Switch to Default Frame
	public static void switchDefaultFrame(Step s) throws Exception {
		driver.switchTo().defaultContent();
		System.out.println("Switched to Default Frame\n");
	}

	// Switch to Main Window
	public static void switchToMainWindow(Step s) throws Exception {
		try {
			driver.switchTo().window(parent);
			System.out.println("Switched to Main Window - " + parent + "\n");
		} catch (Exception e) {
			errorMessage = "Main window was not found";
			errorEncountered(s);
		}

		// Reports.testPass("Switched to Main Window");
	}

	// Switch To Specific Window
	public static void switchToLastWindow(Step s) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String hnd : allWindows) {
			driver.switchTo().window(hnd);
		}
	}

	// Switch To New Window
	public static void switchToNewWindow(Step s) throws Exception {
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();

		int x = 0;

		if (handles.size() == 2) {
			parent = it.next();
			newwin = it.next();
		} else {
			for (;;) {
				handles = driver.getWindowHandles();
				it = handles.iterator();
				if (handles.size() == 2) {
					parent = it.next();
					newwin = it.next();
					break;
				}
				Thread.sleep(1000);
				x++;
				if (x == 9) {
					errorMessage = "New window was not found";
					errorEncountered(s);
				}
			}
		}
		Thread.sleep(1000);
		driver.switchTo().window(newwin);
		Thread.sleep(1000);
		if (driver.getTitle().contains("Certificate")) {
			driver.navigate().to("javascript:document.getElementById('overridelink').click()");
			System.out.println("Certificate - Continue");
		}
		System.out.println("Switched to New Window - " + newwin + "\n");
	}

	// Thread Sleep
	public static void threadSleep(Step s) throws Exception {
		int sec = Integer.parseInt(s.param1);
		sec = sec * 1000;
		Thread.sleep(sec);
		// System.out.println("Sleep: " + sec + " sec.");
	}

	// Verify Text
	public static void verifyText(Step s) throws Exception {
		WebElement element = UIElement(s.element);
		String expText = String.format("%s", s.param1);

		try {
			if (expText.contains("${")) {
				expText = expText.replace("${", "");
				expText = expText.replace("}", "");

				for (Entry<String, String> entry : TestRunner.mapStore.entrySet()) {
					String key = entry.getKey();
					if (key.equals(expText)) {
						expText = String.format("%s", entry.getValue());
					}
				}
			}

			String actText = element.getText();

			if (expText.equals(actText)) {
				logPassToConsole(s);
				Reports.testPass("Expected: " + expText + " is equal to Actual: " + actText);
			} else {
				Reports.testFail("Expected: " + expText + " is not equal to Actual: " + actText);
			}

		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found";
			errorEncountered(s);
		}
	}
	
	// Verify Text Contains
	public static void verifyTextContains(Step s) throws Exception {
		WebElement element = UIElement(s.element);
		String expText = String.format("%s", s.param1);

		try {
			if (expText.contains("${")) {
				expText = expText.replace("${", "");
				expText = expText.replace("}", "");

				for (Entry<String, String> entry : TestRunner.mapStore.entrySet()) {
					String key = entry.getKey();
					if (key.equals(expText)) {
						expText = String.format("%s", entry.getValue());
					}
				}
			}

			String actText = element.getText();

			if (expText.contains(actText)) {
				logPassToConsole(s);
				Reports.testPass("Expected: " + expText + " is equal to Actual: " + actText);
			} else {
				Reports.testFail("Expected: " + expText + " is not equal to Actual: " + actText);
			}

		} catch (Exception e) {
			errorMessage = "Element " + s.element + " was not found";
			errorEncountered(s);
		}
	}

	// Wait For Element To Display
	public static void waitForElementDisplayed(Step s) throws Exception {
		By locator = UIBy(s.element);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			logPassToConsole(s);
			System.out.println("\n-------------------------------------------------------------------------------\n");
			System.out.println(s.element + " is displayed");
			
		} catch (Exception e) {
			System.out.println("\n-------------------------------------------------------------------------------\n");
			errorMessage = s.element + " was not found";
			errorEncountered(s);
		}
	}

	// Wait For Element To Be Clickable
	public static void waitForElementToBeClickable(Step s) throws Exception {
		WebElement element = UIElement(s.element);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			logPassToConsole(s);
			System.out.println("\n-------------------------------------------------------------------------------\n");
			System.out.println(s.element + " is now clickable");
		} catch (Exception e) {
			System.out.println("\n-------------------------------------------------------------------------------\n");
			errorMessage = s.element + " was not found";
			errorEncountered(s);
		}
	}

	// Wait For Page to Load
	public static void waitForPageToLoad(Step s) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return js.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver,  60);
			wait.until(expectation);
			System.out.println("\n-------------------------------------------------------------------------------\n");
			System.out.println("Page loaded succesfully");
			return;
		} catch (Throwable error) {
			System.out.println("\n-------------------------------------------------------------------------------\n");
			errorMessage = "Page Timeout!";
			errorEncountered(s);
			return;
		}
	}

}
