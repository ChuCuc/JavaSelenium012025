package LuckyDraw.page;

import TH.helpers.ValidateUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class LuckyDrawWinLogPage {
    private WebDriver driver;
    private ValidateUIHelpers validateHelper;
    private static By searchInput = By.id("searchBox");
    private By searchBtn = By.xpath("//button[contains(text(),'Tra Cứu')]");

//    public static void enterSearchValue(String value)
//    {
//        validateHelper.setText(searchInput,value);
//    }
    public LuckyDrawWinLogPage(WebDriver driver) {
        this.driver = driver;
        validateHelper = new ValidateUIHelpers(driver);
}

    public void enterSearchValue(String search) throws InterruptedException
    {
//        ValidateUIHelpers validateHelper = null;
        validateHelper.waitForPageLoaded();
        validateHelper.setText(searchInput, search);
    }

    public static void checkSearchTableByColum(int colum, String value)
    {
        WebDriver driver1 = null;
        //Xác định số dòng của table sau khi search
        List<WebElement> row = driver1.findElements(By.xpath("//table//tbody//tr"));
        //Lấy ra số dòng
        int rowTotal = row.size();

        //Duyệt từng dòng
        for (int i=1; i <= rowTotal; i++)
        {
            WebElement elementCheck = driver1.findElement(By.xpath("//table//tbody//tr["+ i +"]//td["+ colum +"]"));
            System.out.println(value + " - ");
            System.out.println(elementCheck.getText());
            Assert.assertTrue(elementCheck.getText().toUpperCase().contains(value.toUpperCase()),"Không chứa giá trị tìm kiếm"); //Lấy so sánh với giá trị search (Kểm tra chứa)

        }
    }
}
