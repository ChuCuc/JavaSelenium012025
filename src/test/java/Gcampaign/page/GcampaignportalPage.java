package Gcampaign.page;

import TH.helpers.ValidateUIHelpers;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Locale;

public class GcampaignportalPage {
    private WebDriver driver;
    private ValidateUIHelpers validateUIHelpers;
    private By usernameInput = By.id("Username");
    private By passwordInput = By.id("Password");
    private By signinBtn = By.xpath("//button[normalize-space()='Sign In']");

    public GcampaignportalPage(WebDriver driver){
        this.driver = driver;
        validateUIHelpers = new ValidateUIHelpers(driver);
    }

    public void Login(String username, String password) throws InterruptedException {
        validateUIHelpers.setText(usernameInput, username);
        validateUIHelpers.setText(passwordInput, password);
        validateUIHelpers.clickElement(signinBtn);
        Thread.sleep(2000);
    }


}
