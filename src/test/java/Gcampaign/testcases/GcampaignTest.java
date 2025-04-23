package Gcampaign.testcases;

import Gcampaign.common.GcampaignSetup;
import Gcampaign.page.GcampaignportalPage;
import TH.helpers.ExcelHelpers;
import TH.helpers.ValidateUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class GcampaignTest {
    WebDriver driver;
    private ValidateUIHelpers validateHelper;
    private GcampaignportalPage gcampaignportalPage;
    private GcampaignSetup gcampaignSetup;
    private ExcelHelpers excel;


    @BeforeClass
    public void GcampaignSetup(){

        driver = new GcampaignSetup().setupDriver("chrome");
        driver.get(new GcampaignSetup().getURL());

        validateHelper = new ValidateUIHelpers(driver);
        gcampaignportalPage = new GcampaignportalPage(driver);
        excel = new ExcelHelpers();
    }

    //KIỂM TRA VALIDATE TRƯỜNG [USERNAME]

    @Test //Kiểm tra trạng thái mặc định trường Username
    public void AT_GCamp_Login_VLD_User_Default() throws InterruptedException {
        WebElement element = driver.findElement(By.id("Username"));
        Assert.assertEquals(element.getAttribute("placeholder"), "Username"); //getAtrribute dùng khi kiểm tra ví dụ: id, class, placeholder, value, v.v.).
        System.out.println("Hiển thị thành công trạng thái mặc định tại trường [Username]!");
    }

    @Test //Kiểm tra khi không nhập
    public void AT_GCamp_Login_VLD_User_Blank() throws InterruptedException {
        gcampaignportalPage.Login("", "Abc@123");
        WebElement element = driver.findElement(By.xpath("//li[contains(text(),'Vui lòng nhập Username!')]"));
        Assert.assertEquals(element.getText(), "Vui lòng nhập Username!"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi không nhập dữ liệu tại trường [Username]!");
    }

    @Test //Kiểm tra khi nhập space
    public void AT_GCamp_Login_VLD_User_Space() throws InterruptedException {
        gcampaignportalPage.Login("           ", "Abc@123");
        WebElement element = driver.findElement(By.xpath("//li[contains(text(),'Vui lòng nhập Username!')]"));
        Assert.assertEquals(element.getText(), "Vui lòng nhập Username!"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi chỉ nhập space tại trường [Name]!!!!");
    }

    //KIỂM TRA VALIDATE TRƯỜNG [PASSWORD]

    @Test //Kiểm tra trạng thái mặc định trường Password
    public void AT_GCamp_Login_VLD_Password_Default() throws InterruptedException {
        WebElement element = driver.findElement(By.id("Password"));
        Assert.assertEquals(element.getAttribute("placeholder"), "Password"); //getAtrribute dùng khi kiểm tra ví dụ: id, class, placeholder, value, v.v.).
        System.out.println("Hiển thị thành công trạng thái mặc định tại trường [Password]!");
    }

    @Test //Kiểm tra khi không nhập
    public void AT_GCamp_Login_VLD_Password_Blank() throws InterruptedException {
        gcampaignportalPage.Login("super_admin", "");
        WebElement element = driver.findElement(By.xpath("//li[contains(text(),'Vui lòng nhập Password!')]"));
        Assert.assertEquals(element.getText(), "Vui lòng nhập Password!"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi không nhập dữ liệu tại trường [Password]!");
    }

    @Test //Kiểm tra khi nhập space
    public void AT_GCamp_Login_VLD_Password_Space() throws InterruptedException {
        gcampaignportalPage.Login("super_admin", "               ");
        WebElement element = driver.findElement(By.xpath("//li[contains(text(),'Vui lòng nhập Password!')]"));
        Assert.assertEquals(element.getText(), "Vui lòng nhập Password!"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi chỉ nhập space tại trường [Password]!!!!");
    }

    //KIỂM TRA LOGIC LOGIN

    @Test //Đăng nhập sai thông tin
    public void AT_GCamp_Login_Failed_Wrong_UserPass() throws Exception {
        excel.setExcelFile("src/test/resouces/data.xlsx","Wrong_UserPass");//khai báo excel
        for (int i=1; i<5; i++)
        {
            gcampaignportalPage.Login(excel.getCellData("username", i), excel.getCellData("password", i));
            WebElement element = driver.findElement(By.xpath("//li[normalize-space()='Invalid login attempt']"));
            Assert.assertEquals(element.getText(), "Invalid login attempt");
            System.out.println("Hiển thị thành công thông báo lỗi khi nhập sai user và password!!!! " +i );
        }
    }

    @Test //Không nhập user và password
    public void AT_GCamp_Login_Failed_Blank_UserPass() throws InterruptedException {
        gcampaignportalPage.Login("", "");
        WebElement element = driver.findElement(By.xpath("//li[contains(text(),'Vui lòng nhập Username!')]"));
        Assert.assertEquals(element.getText(), "Vui lòng nhập Username!");

        element = driver.findElement(By.xpath("//li[contains(text(),'Vui lòng nhập Password!')]"));
        Assert.assertEquals(element.getText(), "Vui lòng nhập Password!");

        System.out.println("Hiển thị thành công thông báo lỗi khi không nhập user và password!!!! ");
    }

    @Test //Đăng nhập thành công
    public void AT_GCamp_Login_Success() throws InterruptedException {
        gcampaignportalPage.Login("super_admin", "Abc@123");
        WebElement element = driver.findElement(By.xpath("//span[@class='brand-text font-weight-light']"));
        Assert.assertEquals(element.getText(), "G-Campaign Portal"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Đăng nhập thành công!");
    }

    @Test
    public void AT_GCamp_Report() throws Exception {
        Thread.sleep(2000);
        //Tìm element Báo cáo
        WebElement baocao = driver.findElement((By.xpath("//p[normalize-space()='Báo cáo']")));
        System.out.println("Tìm thấy menu báo cáo thành công!");
        baocao.click();

        //Tìm element Tổng hợp trong Báo cáo
        WebElement activereport = driver.findElement(By.xpath("//a[@href='/report/TemplateReport']"));
        System.out.println("Đã tìm thấy trang báo cáo tổng hợp");
        activereport.click();
        WebElement campaign = driver.findElement(By.xpath("//label[normalize-space()='Campaign']"));
        Assert.assertEquals(campaign.getText(), "Campaign");
        System.out.println("Truy cập vào trang báo cáo tổng hợp thành công");


        //Chọn Campaign: TH Top Kid 2024
        WebElement campaignName = driver.findElement(By.xpath("//select[@id='selectCampaign']"));
        Select select1 = new Select(campaignName);
        select1.selectByVisibleText("TH TOP KID 2024");
        WebElement selectedOptioncampaignName = select1.getFirstSelectedOption();

        String text = validateHelper.getText(selectedOptioncampaignName);

        Assert.assertEquals(text, "TH TOP KID 2024");
        System.out.println("Chọn campaign thành công");

        //Chọn report: Báo cáo chi tiết
        Thread.sleep(2000);
        WebElement report = driver.findElement(By.xpath("//select[@name='selectReport']"));
        Select dropdownReport = new Select(report);
        dropdownReport.selectByVisibleText("Báo cáo chi tiết");
        System.out.println("Chọn Báo cáo chi tiết thành công");
    }

    public void checkwiner( int column, String value)  {
        validateHelper.Scrollreport();
        WebElement elementCheck = driver.findElement(By.xpath("//tbody/tr[1]/td["+column+"]"));
        Assert.assertEquals(elementCheck.getText().toUpperCase(), value.toUpperCase());
    }

    @AfterClass
    public void closeBrower()
    {
        driver.quit();
    }


}
