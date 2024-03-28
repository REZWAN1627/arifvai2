package com.magento.base;

import com.magento.util.TestUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;
    public static Properties prop;

    public TestBase() {

        try {
            prop = new Properties();
//            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") +
//                    "src/main/java/com/magento/config/config.properties");
            FileInputStream ip = new FileInputStream("C:\\Users\\rezwankabir\\Desktop\\ESSPIAutomationJava\\src\\main\\java\\com\\magento\\config\\config.properties");
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    public static void initialization() {
        String browser = prop.getProperty("browser");
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIME_OUT, TimeUnit.SECONDS);

        driver.get(prop.getProperty("url"));
    }
}
