package LuckyDraw.page;

import TH.helpers.ValidateUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LuckyDrawPage {

    private WebDriver driver;
    private WebElement element;
    private ValidateUIHelpers validateHelper;

    //Element at Lucky Draw Page
    private By nameInput = By.id("main-name");
    private By phoneInput = By.id("main-phone");
    private By province = By.id("main-province");
    private By codeInput = By.id("main-code");
    private By checkmark = By.xpath("//div[@class='checkmark']");
    private By submitBtn = By.xpath("//input[contains(@value,'Tham gia ngay')]");

    //Confirm Infor
//    WebElement By confirmname = driver.findElement(By.xpath("//td[@id='confirm-name']"));
//    private By confirmphone = By.xpath("//td[@id='confirm-phone']");
//    private By confirmprovince = By.xpath("//td[@id='confirm-province']");
//    private By confirmcode = By.xpath("//td[@id='confirm-code']");

//    WebElement confirmname = driver.findElement(By.xpath("//span[@class='brand-text font-weight-light']"));

    private By confirmBtn = By.id("confirm-btn");
    private By updateBtn = By.xpath("//input[@class='btn btn-chang-info close']");
    private By closePopup = By.xpath("(//span[@class='close-popup'][normalize-space()='×'])[2]");

    //Thông tin nhận quà giải 2: Xe đạp thể thao
    private By winnerNamePrizeType2 = By.xpath("//input[@id='winner-name-giai2']");
    private By winnerCodePrizeType2 = By.xpath("//input[@id='winner-code-giai2']");
    private By winnerPhonePrizeType2 = By.xpath("//input[@id='winner-phone-giai2']");
    private By winnerCCCDPrizeType2 = By.xpath("//input[@id='winner-national-id-giai2']");
    private By winnerProvincesPrizeType2 = By.xpath("//input[@id='win-province-giai2_text']");
    private By winnerDistrictPrizeType2 = By.xpath("//select[@id='win-district-giai2']");
    private By winnerStorePrizeType2 = By.xpath("//select[@id='store-option-giai2']");
    private By submitBtnPrizeType2 = By.xpath("//button[@id='update-info-btn-giai2']");

    private By BtnThayDoiThongTin = By.xpath("//input[@id='thayDoiThongTin']");



     public LuckyDrawPage(WebDriver driver) {
        this.driver = driver;
        validateHelper = new ValidateUIHelpers(driver);
    }

    public void LuckyDraw(String name, String phone, String diachi, String code) throws InterruptedException {
         driver.navigate().refresh();
         validateHelper.Scroll();
         validateHelper.waitForPageLoaded();

         validateHelper.setText(nameInput, name);
         validateHelper.setText(phoneInput, phone);
         validateHelper.selectOptionByText(province, diachi);
         validateHelper.setText(codeInput, code);
         validateHelper.clickElement(checkmark);
         Thread.sleep(2000);
         validateHelper.clickElement(submitBtn);
         Thread.sleep(2000);
    }

    public void LuckyDrawUnBtn(String name, String phone, String diachi, String code) throws InterruptedException {
        driver.navigate().refresh();
        validateHelper.Scroll();
        validateHelper.waitForPageLoaded();

        validateHelper.setText(nameInput, name);
        validateHelper.setText(phoneInput, phone);
        validateHelper.selectOptionByText(province, diachi);
        validateHelper.setText(codeInput, code);
        validateHelper.clickElement(checkmark);
        Thread.sleep(2000);
    }

    public void LuckyDrawUnTick(String name, String phone, String diachi, String code) throws InterruptedException {
        driver.navigate().refresh();
        validateHelper.Scroll();
        validateHelper.waitForPageLoaded();

        validateHelper.setText(nameInput, name);

        validateHelper.setText(phoneInput, phone);
        validateHelper.selectOptionByText(province, diachi);
        validateHelper.setText(codeInput, code);
        Thread.sleep(2000);
        validateHelper.clickElement(submitBtn);
        Thread.sleep(2000);
    }


    public void ConfirmInfor (String name, String phone, String diachi, String code) throws InterruptedException{
         validateHelper.waitForPageLoaded();
        WebElement confirmname = driver.findElement(By.xpath("//td[@id='confirm-name']"));
        WebElement confirmphone = driver.findElement(By.xpath("//td[@id='confirm-phone']"));
        WebElement confirmprovince = driver.findElement(By.xpath("//td[@id='confirm-province']"));
        WebElement confirmcode = driver.findElement(By.xpath("//td[@id='confirm-code']"));

        Assert.assertEquals(confirmname.getText(), name);
        Assert.assertEquals(confirmphone.getText(), phone);
        Assert.assertEquals(confirmprovince.getText(), diachi);
        Assert.assertEquals(confirmcode.getText().toUpperCase(), code.toUpperCase());
        validateHelper.clickElement(confirmBtn);
        Thread.sleep(2000);
    }

    public void BtnThayDoiThongTin () throws InterruptedException{
        validateHelper.waitForPageLoaded();
        validateHelper.clickElement(BtnThayDoiThongTin);
        Thread.sleep(1000);
    }



    public void CloseButton () throws InterruptedException {
         validateHelper.waitForPageLoaded();
         validateHelper.clickElement(closePopup);
         Thread.sleep(3000);
    }

    public void UpdateDataInfor (String name, String phone, String diachi, String code) throws InterruptedException{
        validateHelper.waitForPageLoaded();
        validateHelper.clickElement(updateBtn);
        Thread.sleep(1000);
        //Update lại thông tin
        validateHelper.setText(nameInput, name);
        validateHelper.setText(phoneInput, phone);
        validateHelper.selectOptionByText(province, diachi);
        validateHelper.setText(codeInput, code);
        validateHelper.clickElement(submitBtn);
        Thread.sleep(5000);
    }

    public void DataWinnerPrizeType2 (String cancuoc, String district, String store ) throws InterruptedException{
//        validateHelper.setText(winnerNamePrizeType2, name);
//        validateHelper.setText(winnerCodePrizeType2, code);
//        validateHelper.setText(winnerPhonePrizeType2, phone);
        validateHelper.setText(winnerCCCDPrizeType2, cancuoc);
//        validateHelper.setText(winnerProvincesPrizeType2, province);
        validateHelper.selectOptionByText(winnerDistrictPrizeType2, district);
        validateHelper.selectOptionByText(winnerStorePrizeType2, store);
        validateHelper.clickElement(submitBtnPrizeType2);
    }

    public void DataWinnerPrizeType2NoBtn (String cancuoc, String district ) throws InterruptedException{
        validateHelper.Scrollwin2();
//        validateHelper.setText(winnerNamePrizeType2, name);
//        validateHelper.setText(winnerCodePrizeType2, code);
//        validateHelper.setText(winnerPhonePrizeType2, phone);
        validateHelper.setText(winnerCCCDPrizeType2, cancuoc);
//        validateHelper.setText(winnerProvincesPrizeType2, province);
        validateHelper.selectOptionByText(winnerDistrictPrizeType2, district);
//        validateHelper.selectOptionByText(winnerStorePrizeType2, store);
//        validateHelper.clickElement(submitBtnPrizeType2);
    }






}
