package LuckyDraw.testcase;

import LuckyDraw.common.LuckyDrawSetup;
import TH.helpers.ValidateUIHelpers;
import LuckyDraw.page.LuckyDrawPage;
import LuckyDraw.page.LuckyDrawWinLogPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LuckyDrawWinLogTest {
    private WebDriver driver;
    private LuckyDrawPage luckyDrawPage;
    private ValidateUIHelpers validateHelper;
    private LuckyDrawSetup luckyDrawSetup;
    private LuckyDrawWinLogPage luckyDrawWinLogPage;
    private By wrapBar = By.xpath("//div[@class='wrap-bar']");
    private By listGiai = By.xpath("//a[@href='/Home/ListGiai']");
    private By advancedBtn = By.id("details-button");
    private By proceedlink = By.id("proceed-link");

    @BeforeClass
    public void LuckyDrawSetup()
    {
//        driver = getDriver();
        driver = new LuckyDrawSetup().setupDriver("chrome");
        validateHelper = new ValidateUIHelpers(driver);

    }

    @Test
    public void searchLuckyDrawWinLog() throws InterruptedException {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(new LuckyDrawSetup().getURL());

        validateHelper = new ValidateUIHelpers(driver);
        luckyDrawPage = new LuckyDrawPage(driver);


//        driver.get("https://192.168.20.44:4445/");

        validateHelper.clickElement(advancedBtn);
        validateHelper.clickElement(proceedlink);

        validateHelper.clickElement(wrapBar);
        validateHelper.clickElement(listGiai);

        luckyDrawWinLogPage = new LuckyDrawWinLogPage(driver);
        luckyDrawWinLogPage.enterSearchValue("0343396286");
        Thread.sleep(2000);

        LuckyDrawWinLogPage.checkSearchTableByColum(4,"0343396286");
    }

    public void searchPhone() throws InterruptedException {
        luckyDrawWinLogPage.enterSearchValue("0343396286");
        luckyDrawWinLogPage.checkSearchTableByColum(4,"0343396286");
    }
}
