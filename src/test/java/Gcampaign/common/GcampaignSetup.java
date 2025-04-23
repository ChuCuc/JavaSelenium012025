package Gcampaign.common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

public class GcampaignSetup {
    static String drvierPath = "resources\\drivers\\";
    private WebDriver driver;
    private String url = "https://gcampaignportal-dev.gapit.com.vn/";
    public WebDriver getDriver()
    {
        return driver;
    }

    public String getURL()
    {
        return url;
    }

    public WebDriver setupDriver (String browerType)
    {
        switch (browerType.trim().toLowerCase()) //chuyển về chữ thường
        {
            case "chrome":
                driver = initChromeDriver();
                break;
            case "firefox":
                driver = initFirefoxDriver();
                break;
            case "edge":
                driver = initEdgeDriver();
                break;
            default: //Nếu không vào cái nào
                System.out.println("Brower: " + browerType + " is invalid, Launching Chrome as brower of choice...");
                driver = initChromeDriver();
        }
        return driver;
    }

    //Hàm này để tùy chọn Brower. Cho chạy trước khi gọi class này (BeforeClass)
    private void setDriver (String browerType, String appURL)
    {
        switch (browerType)
        {
            case "chrome":
                driver = initChromeDriver();
                driver.navigate().to(appURL);
                break;
            case "firefox":
                driver = initFirefoxDriver();
                driver.navigate().to(appURL);
                break;
            case "edge":
                driver = initEdgeDriver();
                driver.navigate().to(appURL);
                break;
            default:
                System.out.println("Brower: " + browerType + " is invalid, Launching Chrome as brower of choice...");
                driver = initChromeDriver();
        }
    }

    //Khởi tạo cấu hình của các Brower để đưa vào Switch Case
    private WebDriver initChromeDriver()
    {
        System.out.println("Launching Chrome brower...");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

    private WebDriver initFirefoxDriver()
    {
        System.out.println("Launching Firefox brower...");
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

    private WebDriver initEdgeDriver()
    {
        System.out.println("Launching Edge brower...");
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

    //Chạy hàm intializeTestLuckyDrawSetup trước hết khi class này được gọi
    @Parameters({"browerType", "webURL"})
    @BeforeClass
    public void intializeTestLuckyDrawSetup (String browerType, String webURL)
    {
        try
        {
            //Khởi tạo drvier và tùy chọn brower và web url
            setDriver(browerType, webURL);
        }
        catch (Exception e)
        {
            System.out.println("Error..." + e.getStackTrace());
        }
    }

    @AfterClass
    public void tearDown() throws Exception
    {
        Thread.sleep(2000);
        driver.quit();
    }
}
