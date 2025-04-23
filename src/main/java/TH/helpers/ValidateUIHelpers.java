package TH.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.List;

public class ValidateUIHelpers {
    //Thư viện chứa nhiều hàm nhỏ bên trong
    private WebDriver driver;
    private final int timeoutWaitForPageLoaded = 30;
    private WebDriverWait wait;

    public ValidateUIHelpers(WebDriver driver) //Hàm trùng với tên class ==> Hàm xây dựng
    {
        this.driver = driver;
        wait = new WebDriverWait(driver,5);

    }

    //Hàm chung
    public void setText(By element, String value)
    {
        //Sendkey một giá trị là Value cho element truyền vào
//        wait.until(ExpectedConditions.elementToBeClickable(element));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(value);
    }

    public void clickElement(By element)
    {
        //Click vào một phần tử Element truyền vào
//        wait.until(ExpectedConditions.elementToBeClickable(element));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).click();
    }

    public void setCheckboxState (WebElement element, boolean isChecked){
        boolean isActualChecked = element.isSelected();
        if (isChecked != isActualChecked)
        {
            element.click();
        }
    }

    public boolean verifyUrl (String url)
    {
        System.out.println(driver.getCurrentUrl());
        System.out.println(url);

        return driver.getCurrentUrl().contains(url); //True False
    }

    public boolean verifyElementText (By element, String textValue)
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        return driver.findElement(element).getText().equals(textValue); //True False
    }

    public boolean verifyElementExist (By element)
    {
        //Tạo list lưu tất cả đối tượng WebElement
        List<WebElement> listElement = driver.findElements(element);

        int total = listElement.size();
        if (total > 0)
        {
            return true;
        }
        return false;
    }

    public void selectOptionByText(By element, String text)
    {
        //Chuyển đối tượng By sang WebElement thi thêm driver.findElement
        Select select = new Select(driver.findElement(element));
        select.selectByVisibleText(text);
    }

    public void waitForPageLoaded()
    {
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>()
        {
            @Override
            public Boolean apply(WebDriver driver)
            {
                try
                {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                }
                catch (Exception e)
                {
                    return true;
                }
            }
        };

        //wait for Javascript to loaded
        ExpectedCondition<Boolean> jsload = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver)
            {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
    }

    public String getText (WebElement element){
        return element.getText();
    }

    @Test
    public void Scroll(){
        //Cuộn trang đến đúng phần tử
        //Tìm phần tử trên trang
        WebElement element = driver.findElement(By.xpath("//form[@class='form-quay-thuong']"));
        // Cuộn đến phần tử
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @Test
    public void Scrollwin2(){
        //Cuộn trang đến đúng phần tử
        //Tìm phần tử trên trang
        WebElement element = driver.findElement(By.xpath("//form[@id='update-info-form-giai2']"));
        // Cuộn đến phần tử
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @Test
    public void Scrollreport(){
        //Cuộn trang đến đúng phần tử
        //Tìm phần tử trên trang
        WebElement element = driver.findElement(By.xpath("//tbody/tr[1]"));
        // Cuộn đến phần tử
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }


//    public void waitForPageLoaded()
//    {
//        //wait for jQuery to loaded
//        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>()
//        {
//            public Boolean apply (WebDriver driver)
//            {
//                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
//                        .equals("complete");
//            }
//        };
//        try
//        {
//            WebDriverWait wait = new WebDriverWait(driver, timeoutWaitForPageLoaded);
//            wait.until(expectation);
//        }
//        catch (Throwable error)
//        {
//            Assert.fail("Timeout waiting for Page Load request");
//        }
//    }
}
