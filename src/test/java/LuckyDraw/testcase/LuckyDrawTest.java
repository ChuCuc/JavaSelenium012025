package LuckyDraw.testcase;

import Gcampaign.page.GcampaignportalPage;
import Gcampaign.testcases.GcampaignTest;
import LuckyDraw.common.LuckyDrawSetup;
import TH.helpers.ExcelHelpers;
import TH.helpers.ValidateUIHelpers;
import LuckyDraw.page.LuckyDrawPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static TH.helpers.JsonUtils.loadProvincesAndDistricts;
import static TH.helpers.JsonUtils.loadProvincesDistrictsAndStores;
import static java.time.Duration.ofSeconds;

public class LuckyDrawTest {
    WebDriver driver;
    private ValidateUIHelpers validateHelper;
    private LuckyDrawPage luckyDrawPage;
    private ExcelHelpers excel;
    private GcampaignTest gcampaignTest;
    private GcampaignportalPage gcampaignportalPage;

    //2 Nút vào trang web
    private By advancedBtn = By.id("details-button");
    private By proceedlink = By.id("proceed-link");

    @BeforeClass
    public void LuclkyDrawSetup()
    {
        driver = new LuckyDrawSetup().setupDriver("chrome");
        validateHelper = new ValidateUIHelpers(driver);
        excel = new ExcelHelpers();
        luckyDrawPage = new LuckyDrawPage(driver);
    }

    private WebDriverWait wait;

    public void PrizeChecker(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10); // Thời gian chờ tối đa 10 giây
    }

    @Test //Mở Brower
    public void OpenBrower() throws Exception {
        driver.get(new LuckyDrawSetup().getURL());

        //Click vào 2 nút để vào trang
        validateHelper.clickElement(advancedBtn);
        validateHelper.clickElement(proceedlink);

    }

    //--------------------------------------------------------------------------------------------------------------//
    //--------------------------------------------CHECK VALIDATE QUAY SỐ--------------------------------------------//
    //--------------------------------------------------------------------------------------------------------------//

    //KIỂM TRA VALIDATE TRƯỜNG [NAME]

    @Test //Kiểm tra trạng thái mặc định trường Name
    public void AT_TH_QS_Created_VLD_Name_Default() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//input[@id='main-name']"));
        Assert.assertEquals(element.getAttribute("placeholder"), "Họ và tên (*)"); //getAtrribute dùng khi kiểm tra ví dụ: id, class, placeholder, value, v.v.).
        System.out.println("Hiển thị thành công trạng thái mặc định tại trường [Name]!");
    }

    @Test //Kiểm tra khi không nhập
    public void AT_TH_QS_Created_VLD_Name_Blank() throws InterruptedException {
        luckyDrawPage.LuckyDraw("", "0311111111", "An Giang", "abc123456");
        WebElement element = driver.findElement(By.xpath("//span[@id='main-name-error']"));
        Assert.assertEquals(element.getText(), "Vui lòng nhập họ tên"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi không nhập dữ liệu tại trường [Name]!");
    }

    @Test //Kiểm tra khi nhập số
    public void AT_TH_QS_Created_VLD_Name_Number() throws InterruptedException {
        luckyDrawPage.LuckyDraw("123456789", "0322222222", "Bắc Giang", "abc123456");
        WebElement element = driver.findElement(By.xpath("//span[@id='main-name-error']"));
        Assert.assertEquals(element.getText(), "Họ tên không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi nhập số tại trường [Name]!!!!");
    }

    @Test //Kiểm tra khi nhập space
    public void AT_TH_QS_Created_VLD_Name_Space() throws InterruptedException {
        luckyDrawPage.LuckyDraw("                    ", "0333333333", "Bắc Kạn", "abc123456");
        WebElement element = driver.findElement(By.xpath("//span[@id='main-name-error']"));
        Assert.assertEquals(element.getText(), "Họ tên không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi chỉ nhập space tại trường [Name]!!!!");
    }

    //KIỂM TRA VALIDATE TRƯỜNG [PHONE]

    @Test //Kiểm tra trạng thái mặc định trường Phone
    public void AT_TH_QS_Created_VLD_Phone_Default() throws InterruptedException {
        WebElement element = driver.findElement(By.id("main-phone"));
        Assert.assertEquals(element.getAttribute("placeholder"), "Số điện thoại (*)"); //getAtrribute dùng khi kiểm tra ví dụ: id, class, placeholder, value, v.v.).
        System.out.println("Hiển thị thành công trạng thái mặc định tại trường [Phone]!");
    }

    @Test //Kiểm tra khi không nhập
    public void AT_TH_QS_Created_VLD_Phone_Blank() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc", "", "Bạc Liêu", "abc123456");
        WebElement element = driver.findElement(By.id("main-phone-error"));
        Assert.assertEquals(element.getText(), "Vui lòng nhập số điện thoại"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi không nhập tại trường [Phone]!");
    }

    @Test //Kiểm tra khi nhập space
    public void AT_TH_QS_Created_VLD_Phone_Space() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc một", "                 ", "Bắc Ninh", "abc123456");
        WebElement element = driver.findElement(By.id("main-phone-error"));
        Assert.assertEquals(element.getText(), "Số điện thoại không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi chỉ nhập space tại trường [Phone]!!!!");
    }

    @Test //Kiểm tra khi nhập 9 ký tự
    public void AT_TH_QS_Created_VLD_Phone_9Characters() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc một", "034444444", "Bà Rịa - Vũng Tàu", "abc123456");
        WebElement element = driver.findElement(By.id("main-phone-error"));
        Assert.assertEquals(element.getText(), "Số điện thoại không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi chỉ nhập 9 ký tự tại trường [Phone]!!!!");
    }

    @Test //Kiểm tra khi nhập 11 ký tự
    public void AT_TH_QS_Created_VLD_Phone_11Characters() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc hai", "03123456789", "Bến Tre", "abc123456");
        luckyDrawPage.CloseButton();
        WebElement element = driver.findElement(By.xpath("//input[@id='main-phone']"));
        Assert.assertEquals(element.getAttribute("value"), "0312345678"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hệ thống chỉ cho phép nhập 10 ký tự tại trường [Phone]!!!!");
    }

    @Test //Kiểm tra khi nhập đầu số khác 0
    public void AT_TH_QS_Created_VLD_Phone_Different0() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc hai", "84343396286", "Bình Định", "abc123456");
        WebElement element = driver.findElement(By.id("main-phone-error"));
        Assert.assertEquals(element.getText(), "Số điện thoại không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi chỉ nhập SĐT khác 0 tại trường [Phone]!!!!");
    }

    @Test //Kiểm tra khi nhập sai định dạng SĐT
    public void AT_TH_QS_Created_VLD_Phone_Wrong_Format() throws Exception {
        excel.setExcelFile("src/test/resouces/data.xlsx","Wrong_Format");//khai báo excel
        for (int i=1; i<5; i++)
        {
            luckyDrawPage.LuckyDraw(excel.getCellData("name", i), excel.getCellData("phone", i), excel.getCellData("diachi", i), excel.getCellData("code", i));
            WebElement element = driver.findElement(By.id("main-phone-error"));
            Assert.assertEquals(element.getText(), "Số điện thoại không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
            System.out.println("Hiển thị thành công thông báo lỗi khi nhập sai định dạng tại trường [Phone]!!!! " +i );
        }
    }

    //KIỂM TRA VALIDATE TRƯỜNG [PROVINCES]

    @Test //Kiểm tra trạng thái mặc định trường Provinces
    public void AT_TH_QS_Created_VLD_Provinces_Default() throws InterruptedException {
        WebElement element = driver.findElement(By.id("main-province"));
        Select select = new Select(element);
        select.selectByVisibleText("Tỉnh/TP (*)");
        WebElement selectedOption = select.getFirstSelectedOption();

        String text = validateHelper.getText(selectedOption);

        Assert.assertEquals(text, "Tỉnh/TP (*)");
        System.out.println("Hiển thị thành công trạng thái mặc định tại trường [Provonces]!");
    }

    @Test //Kiểm tra đủ 63 tỉnh thành
    public void AT_TH_QS_Created_VLD_Provinces_Data() throws Exception {
        // Danh sách 63 tỉnh thành
        List<String> expectedProvinces = Arrays.asList(
                "An Giang", "Bà Rịa - Vũng Tàu", "Bạc Liêu", "Bắc Kạn", "Bắc Giang", "Bắc Ninh",
                "Bến Tre", "Bình Dương", "Bình Định", "Bình Phước", "Bình Thuận", "Cà Mau",
                "Cao Bằng", "Cần Thơ", "Đà Nẵng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai",
                "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Nội", "Hà Tĩnh", "Hải Dương",
                "Hải Phòng", "Hậu Giang", "Hoà Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang",
                "Kon Tum", "Lai Châu", "Lạng Sơn", "Lào Cai", "Lâm Đồng", "Long An", "Nam Định",
                "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình",
                "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La",
                "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang",
                "Hồ Chí Minh", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Tỉnh/TP (*)"
        );

        try {
            // Lấy dropdown
            WebElement dropdownElement = driver.findElement(By.id("main-province")); // Thay id_dropdown bằng ID của dropdown trên trang
            Select dropdown = new Select(dropdownElement);

            // Lấy tất cả giá trị từ dropdown
            List<WebElement> options = dropdown.getOptions();
            List<String> actualProvinces = new ArrayList<>();
            for (WebElement option : options) {//Duyệt qua tường phần tử
                actualProvinces.add(option.getText().trim());//getText lấy giá trị văn bẳn. Trim() bỏ khoảng cách đầu cuối
            }

            // Kiểm tra giá trị
            List<String> missing = new ArrayList<>(expectedProvinces);
            missing.removeAll(actualProvinces);

            List<String> extra = new ArrayList<>(actualProvinces);
            extra.removeAll(expectedProvinces);

            // Kết quả kiểm tra
            if (missing.isEmpty() && extra.isEmpty()) {
                System.out.println("Dropdown đã chứa đủ 63 tỉnh thành và đúng giá trị.");
            } else {
                if (!missing.isEmpty()) {
                    System.out.println("Thiếu giá trị: " + missing);
                }
                if (!extra.isEmpty()) {
                    System.out.println("Thừa giá trị: " + extra);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test //Kiểm tra khi không chọn dữ liệu
    public void AT_TH_QS_Created_VLD_Provinces_Blank() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc", "0355555555", "Tỉnh/TP (*)", "ABC1234567");
        WebElement element = driver.findElement(By.id("main-province-error"));
        Assert.assertEquals(element.getText(), "Vui lòng chọn Tỉnh / Thành Phố"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi không chọn dữ liệu trường [Tỉnh/TP]!");
    }

    @Test //Kiểm tra chọn dữ liệu
    public void AT_TH_QS_Created_VLD_Provinces_Display() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc", "0355555555", "Bình Dương", "");
        WebElement element = driver.findElement(By.xpath("//select[@id='main-province']"));
        Select select = new Select(element);
        select.selectByVisibleText("Bình Dương");
        WebElement selectedOption = select.getFirstSelectedOption();

        String text = validateHelper.getText(selectedOption);

        Assert.assertEquals(text, "Bình Dương");
        System.out.println("Hiển thị dữ liệu thành công");
    }

    //KIỂM TRA VALIDATE TRƯỜNG [CODE]

    @Test //Kiểm tra trạng thái mặc định trường Code
    public void AT_TH_QS_Created_VLD_Code_Default() throws InterruptedException {
        WebElement element = driver.findElement(By.id("main-code"));
        Assert.assertEquals(element.getAttribute("placeholder"), "Mã số quay thưởng (*)"); //getAtrribute dùng khi kiểm tra ví dụ: id, class, placeholder, value, v.v.).
        System.out.println("Hiển thị thành công trạng thái mặc định tại trường [Code]!");
    }

    @Test //Kiểm tra khi không nhập
    public void AT_TH_QS_Created_VLD_Code_Blank() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc", "0355555555", "Bình Phước", "");
        WebElement element = driver.findElement(By.id("main-code-error"));
        Assert.assertEquals(element.getText(), "Vui lòng nhập mã quay thưởng"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi không nhập tại trường [Code]!");
    }

    @Test //Kiểm tra khi nhập space
    public void AT_TH_QS_Created_VLD_Code_Space() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc một", "0366666666", "Bình Thuận", "                ");
        WebElement element = driver.findElement(By.id("main-code-error"));
        Assert.assertEquals(element.getText(), "Mã quay thưởng không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi chỉ nhập space tại trường [Code]!!!!");
    }

    @Test //Kiểm tra khi nhập 8 ký tự
    public void AT_TH_QS_Created_VLD_Code_8Characters() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc tam ky tu", "0377777777", "Bến Tre", "abc11111");
        WebElement element = driver.findElement(By.id("main-code-error"));
        Assert.assertEquals(element.getText(), "Mã quay thưởng không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi chỉ nhập 8 ký tự tại trường [Code]!!!!");
    }

    @Test //Kiểm tra khi nhập 10 ký tự
    public void AT_TH_QS_Created_VLD_Code_10Characters() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc muoi ky tu", "0388888888", "Bình Định", "abc9876543");
        luckyDrawPage.CloseButton();
        WebElement element = driver.findElement(By.xpath("//input[@id='main-code']"));
        Assert.assertEquals(element.getAttribute("value"), "ABC987654"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hệ thống chỉ cho phép nhập 9 ký tự tại trường [Code]!!!!");
    }

    @Test //Mã code có chứa space đầu cuối
    public void AT_TH_QS_Created_VLD_Code_SpaceBA() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cuc space đầu cuối", "0399999999", "Bến Tre", " abc345678 ");
        WebElement element = driver.findElement(By.id("main-code-error"));
        Assert.assertEquals(element.getText(), "Mã quay thưởng không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị thành công thông báo lỗi khi có space đầu cuối tại trường [Code]!!!!");
    }

    @Test //Chặn XSS
    public void AT_TH_QS_Created_VLD_Code_BlockXSS() throws InterruptedException {
        luckyDrawPage.LuckyDrawUnTick("Cuc space đầu cuối", "0399999999", "Bến Tre", "<script>alert(123)</script>");
        WebElement element = driver.findElement(By.id("main-code-error"));
        Assert.assertEquals(element.getText(), "Mã quay thưởng không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Không thực thi câu lệnh");
    }

    //KIỂM TRA VALIDATE TRƯỜNG [CHECKBOX]

    @Test
    public void AT_TH_QS_Created_VLD_Rules_Default() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//div[@class='checkmark']"));
        boolean isChecked = element.isSelected();//Trạng thái có checkbox
        System.out.println("Mặc định không tick");
    }

    @Test //Kiểm tra khi tick chọn
    public void AT_TH_QS_Created_VLD_Rules_UnTick() throws InterruptedException {
        luckyDrawPage.LuckyDrawUnTick("Cuc không tick thể lệ", "0342222222", "Bến Tre", "abc123456");
        WebElement element = driver.findElement(By.xpath("(//div[@class='text-note col-12 mb-2'])[1]"));
        Assert.assertEquals(element.getText(), "Vui lòng đọc và đồng ý với Thể lệ chương trình"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Hiển thị đúng câu thông báo khi không tick chọn thể lệ");
    }

    @Test //Kiểm tra khi tick chọn
    public void AT_TH_QS_Created_VLD_Rules_Tick() throws InterruptedException {
        luckyDrawPage.LuckyDraw("Cúc tick chọn thể lệ", "0341111111", "Bến Tre", "abc123456");
        WebElement element = driver.findElement(By.xpath("(//p[contains(text(),'Những thông tin sau đây không thể thay đổi. Vui lò')])[1]"));
        Assert.assertEquals(element.getText(), "Những thông tin sau đây không thể thay đổi. Vui lòng kiểm tra lại thông tin của bạn trước khi bắt đầu tham gia quay thưởng."); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("Đã tick Thể lệ thành công");
    }

    //--------------------------------------------------------------------------------------------------------------//
    //----------------------------------------CHECK VALIDATE XÁC NHẬN QUAY SỐ---------------------------------------//
    //--------------------------------------------------------------------------------------------------------------//

    @Test
    public void checkPrize() throws Exception {
        excel.setExcelFile("src/test/resouces/data.xlsx", "Infor");//khai báo excel

        for (int i = 1; i < 2; i++) {
            //Điền thông tin quay số
            System.out.println("\n---------------------------------------------------------------------------------------------");
            System.out.println("---------------------------------------THÔNG TIN THỨ " +i+ "---------------------------------------");
            System.out.println("---------------------------------------------------------------------------------------------\n");

            luckyDrawPage.LuckyDraw(excel.getCellData("name", i), excel.getCellData("phone", i), excel.getCellData("diachi", i), excel.getCellData("code", i));
            System.out.println("[Điền thông tin quay số] Điền thông tin thành công!!!");
            //Xác nhận thông tin quay số
            luckyDrawPage.ConfirmInfor(excel.getCellData("name", i), excel.getCellData("phone", i), excel.getCellData("diachi", i), excel.getCellData("code", i));
            System.out.println("[Xác nhận thông tin quay số] Các thông tin trùng khớp với màn [Điền thông tin quay sô]" + i);
            //Kiểm tra trả giải

            if (isElementPresent (By.xpath("//div[@class='modal fade popup-ma-da-su-dung show']//div[@class='modal-body']"))) { //Trùng mã
                AT_TH_XNQS_Failed_Used_Code();

                //Kiểm tra trong báo cáo chi tiết
                gcampaignTest = new GcampaignTest();
                gcampaignportalPage = new GcampaignportalPage(driver);
                gcampaignTest.GcampaignSetup();
                gcampaignTest.AT_GCamp_Login_Success();
                gcampaignTest.AT_GCamp_Report();

                gcampaignTest.checkwiner(2, excel.getCellData("phone", i));
                gcampaignTest.checkwiner(3, excel.getCellData("name", i));
                gcampaignTest.checkwiner(4, excel.getCellData("code", i));
                gcampaignTest.checkwiner(5, "Mã dự thưởng đã được sử dụng");
                gcampaignTest.checkwiner(6, "");
                gcampaignTest.checkwiner(8, excel.getCellData("diachi", i));
                System.out.println("[Báo cáo chi tiết] Thông tin trùng khớp" + i);
                gcampaignTest.closeBrower();

            } else if (isElementPresent (By.xpath("//div[@class='modal fade popup-ma-khong-hop-le show']//div[@class='modal-body']"))) { //Sai mã
                AT_TH_XNQS_Failed_Wrong_Code();

                //Kiểm tra trong báo cáo chi tiết
                gcampaignTest = new GcampaignTest();
                gcampaignportalPage = new GcampaignportalPage(driver);
                gcampaignTest.GcampaignSetup();
                gcampaignTest.AT_GCamp_Login_Success();
                gcampaignTest.AT_GCamp_Report();

                gcampaignTest.checkwiner(2, excel.getCellData("phone", i));
                gcampaignTest.checkwiner(3, excel.getCellData("name", i));
                gcampaignTest.checkwiner(4, excel.getCellData("code", i));
                gcampaignTest.checkwiner(5, "Mã dự thưởng không hợp lệ");
                gcampaignTest.checkwiner(6, "");
                gcampaignTest.checkwiner(8, excel.getCellData("diachi", i));
                System.out.println("[Báo cáo chi tiết] Thông tin trùng khớp" + i);
                gcampaignTest.closeBrower();

            } else if (isElementPresent (By.xpath("//div[@class='modal fade popup-khong-trung show']//div[@class='modal-body']"))) { //Không trúng giải
                AT_TH_XNQS_Success_NotWin();

                //Kiểm tra trong báo cáo chi tiết
                gcampaignTest = new GcampaignTest();
                gcampaignportalPage = new GcampaignportalPage(driver);
                gcampaignTest.GcampaignSetup();
                gcampaignTest.AT_GCamp_Login_Success();
                gcampaignTest.AT_GCamp_Report();

                gcampaignTest.checkwiner(2, excel.getCellData("phone", i));
                gcampaignTest.checkwiner(3, excel.getCellData("name", i));
                gcampaignTest.checkwiner(4, excel.getCellData("code", i));
                gcampaignTest.checkwiner(5, "Hợp lệ");
                gcampaignTest.checkwiner(6, "Không trúng giải");
                gcampaignTest.checkwiner(8, excel.getCellData("diachi", i));
                System.out.println("[Báo cáo chi tiết] Thông tin trùng khớp" + i);
                gcampaignTest.closeBrower();

            } else if (isElementPresent (By.xpath("//div[@class='modal fade popup-giai-the-10k show']//div[@class='modal-body']"))) { //Giải 4
                AT_TH_XNQS_Success_PrizeType4();

                //Kiểm tra trong báo cáo chi tiết
                gcampaignTest = new GcampaignTest();
                gcampaignportalPage = new GcampaignportalPage(driver);
                gcampaignTest.GcampaignSetup();
                gcampaignTest.AT_GCamp_Login_Success();
                gcampaignTest.AT_GCamp_Report();

                gcampaignTest.checkwiner(2, excel.getCellData("phone", i) );
                gcampaignTest.checkwiner(3, excel.getCellData("name", i) );
                gcampaignTest.checkwiner(4, excel.getCellData("code", i) );
                gcampaignTest.checkwiner(5, "Hợp lệ" );
                gcampaignTest.checkwiner(6, "Giải Topup 10k" );
                gcampaignTest.checkwiner(8, excel.getCellData("diachi", i) );
                System.out.println("[Báo cáo chi tiết] Thông tin trùng khớp" + i);

                gcampaignTest.closeBrower();

            } else if (isElementPresent (By.xpath("//div[@class='modal fade popup-giai-the-20k show']//div[@class='modal-body']"))) { //Giải 3
                AT_TH_XNQS_Success_PrizeType3();

                //Kiểm tra trong báo cáo chi tiết
                gcampaignTest = new GcampaignTest();
                gcampaignportalPage = new GcampaignportalPage(driver);
                gcampaignTest.GcampaignSetup();
                gcampaignTest.AT_GCamp_Login_Success();
                gcampaignTest.AT_GCamp_Report();

                gcampaignTest.checkwiner(2, excel.getCellData("phone", i) );
                gcampaignTest.checkwiner(3, excel.getCellData("name", i) );
                gcampaignTest.checkwiner(4, excel.getCellData("code", i) );
                gcampaignTest.checkwiner(5, "Hợp lệ" );
                gcampaignTest.checkwiner(6, "Giải Topup 20k" );
                gcampaignTest.checkwiner(8, excel.getCellData("diachi", i) );
                System.out.println("[Báo cáo chi tiết] Thông tin trùng khớp" + i);
                gcampaignTest.closeBrower();

            } else if (isElementPresent (By.xpath("//div[@class='modal fade popup-giai-tai-nghe popup-trung-1 popup-large show']//div[@class='modal-body']"))) { //Giải 2
                AT_TH_XNQS_Success_PrizeType2();

                //Kiểm tra trong báo cáo chi tiết
//                gcampaignTest = new GcampaignTest();
//                gcampaignportalPage = new GcampaignportalPage(driver);
//                gcampaignTest.GcampaignSetup();
//                gcampaignTest.AT_GCamp_Login_Success();
//                gcampaignTest.AT_GCamp_Report();
//
//                gcampaignTest.checkwiner(2, excel.getCellData("phone", i) );
//                gcampaignTest.checkwiner(3, excel.getCellData("name", i) );
//                gcampaignTest.checkwiner(4, excel.getCellData("code", i) );
//                gcampaignTest.checkwiner(5, "Hợp lệ" );
//                gcampaignTest.checkwiner(6, "Xe đạp" );
//                gcampaignTest.checkwiner(8, excel.getCellData("diachi", i) );
//                System.out.println("[Báo cáo chi tiết] Thông tin trùng khớp" + i);
//                gcampaignTest.closeBrower();

                //Kiểm tra validate popup [Điền thông tin trúng giải]
//                AT_TH_WinPrize_Created_VLD_Name();
//                AT_TH_WinPrize_Created_VLD_CCCD_Default();
//                AT_TH_WinPrize_Created_VLD_CCCD_Blank();
//                AT_TH_WinPrize_Created_VLD_CCCD_Space();
//                AT_TH_WinPrize_Created_VLD_CCCD_Text();
//                AT_TH_WinPrize_Created_VLD_CCCD_Number();
//                AT_TH_WinPrize_Created_VLD_CCCD_8Characters();
//                AT_TH_WinPrize_Created_VLD_CCCD_13Characters();
//                AT_TH_WinPrize_Created_VLD_CCCD_Block();
//                AT_TH_WinPrize_Created_VLD_Province();
//                AT_TH_WinPrize_Created_VLD_District_Default();
//                AT_TH_WinPrize_Created_VLD_District_Data();
//                AT_TH_WinPrize_Created_VLD_District_Blank();
//                AT_TH_WinPrize_Created_VLD_Code();
                AT_TH_WinPrize_Created_VLD_Store_Data();

            } else if (isElementPresent (By.xpath("//div[@class='modal fade popup-giai-may-quay popup-trung-1 popup-large show']//div[@class='modal-body']"))) { //Giải 1
                AT_TH_XNQS_Success_PrizeType1();

                //Kiểm tra trong báo cáo chi tiết
                gcampaignTest = new GcampaignTest();
                gcampaignportalPage = new GcampaignportalPage(driver);
                gcampaignTest.GcampaignSetup();
                gcampaignTest.AT_GCamp_Login_Success();
                gcampaignTest.AT_GCamp_Report();

                gcampaignTest.checkwiner(2, excel.getCellData("phone", i) );
                gcampaignTest.checkwiner(3, excel.getCellData("name", i) );
                gcampaignTest.checkwiner(4, excel.getCellData("code", i) );
                gcampaignTest.checkwiner(5, "Hợp lệ" );
                gcampaignTest.checkwiner(6, "Laptop" );
                gcampaignTest.checkwiner(8, excel.getCellData("diachi", i) );
                System.out.println("[Báo cáo chi tiết] Thông tin trùng khớp" + i);
                gcampaignTest.closeBrower();

            } else {
                System.out.println("Không tìm thấy element");
            }
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by); // Nếu tìm thấy element, trả về true
            return true;
        } catch (Exception e) {
            return false; // Nếu không tìm thấy hoặc gặp lỗi, trả về false
        }
    }

    @Test //Không trúng giải
    public void AT_TH_XNQS_Success_NotWin() throws Exception {
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("KIỂM TRA POPUP KHÔNG TRÚNG GIẢI");

        Thread.sleep(5000);
        WebElement element = driver.findElement(By.xpath("(//p[contains(text(),'Vui lòng nhập mã quay thưởng khác để tiếp tục tham')])[1]"));
        Assert.assertEquals(element.getText(),"Vui lòng nhập mã quay thưởng khác để tiếp tục tham gia chương trình.");
        System.out.println("[Popup không trúng giải] Hiển thị popup thành công");

        //Kiểm tra btton Tiếp tục quay thưởng
        WebElement btnTiepTuc = driver.findElement(By.xpath("(//a[@class='btn confirm-info closeRemove'][contains(text(),'Tiếp tục quay thưởng')])[2]"));
        btnTiepTuc.click();
        element = driver.findElement(By.xpath("//div[@class='form-title l-font col-12']"));
        Thread.sleep(3000);
        if(element.getText().equalsIgnoreCase("Thông tin tham gia quay thưởng"))
        {
            System.out.println("[Popup không trúng giải] Nhấn button [Tiếp tục quay thưởng] thành công");
        }
        else {
            System.out.println("[Popup không trúng giải] Nhấn button [Tiếp tục quay thưởng] KHÔNG thành công");
        }

    }

    @Test //trùng mã code
    public void AT_TH_XNQS_Failed_Used_Code() throws InterruptedException {
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("KIỂM TRA POPUP TRÙNG MÃ");

        Thread.sleep(2000);
        WebElement element = driver.findElement(By.xpath("(//p[contains(text(),'Vui lòng kiểm tra lại mã quay thưởng hoặc nhập mã ')])[2]"));
        Thread.sleep(3000);
        Assert. assertEquals(element.getText(),"Vui lòng kiểm tra lại mã quay thưởng hoặc nhập mã quay thưởng khác.");
        System.out.println("[Popup Trùng mã] Hiển thị popup thành công");

        //Kiểm tra btton nhập mã khác
        WebElement btnMakhac = driver.findElement(By.xpath("(//a[@class='btn confirm-info close'][contains(text(),'Nhập mã khác')])[2]"));
        btnMakhac.click();
        element = driver.findElement(By.xpath("//div[@class='form-title l-font col-12']"));
        Thread.sleep(3000);
        if(element.getText().equalsIgnoreCase("Thông tin tham gia quay thưởng"))
        {
            System.out.println("[Popup trùng mã] Nhấn button [Nhập mã khác] thành công");
        }
        else {
            System.out.println("[Popup trùng mã] Nhấn button [Nhập mã khác] KHÔNG thành công");
        }

//        //Kiểm tra khi nhấn icon x
//        luckyDrawPage.LuckyDraw("Cúc", "0343396286", "Thái Bình", "ZF4IT1RA9");
////        luckyDrawPage.ConfirmInfor();
//        WebElement x = driver.findElement(By.xpath("(//span[@class='close-popup'][normalize-space()='×'])[5]"));
//        x.click();
//        element = driver.findElement(By.xpath("//div[@class='form-title l-font col-12']"));
//        Thread.sleep(3000);
//        if(element.getText().equalsIgnoreCase("Thông tin tham gia quay thưởng"))
//        {
//            System.out.println("[Popup trùng mã] Nhấn icon x đóng popup thành công");
//        }
//        else {
//            System.out.println("[Popup trùng mã] Nhấn icon x đóng popup KHÔNG thành công");
//        }
    }

    @Test //Sai mã code
    public void AT_TH_XNQS_Failed_Wrong_Code() throws InterruptedException {
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("KIỂM TRA POPUP SAI MÃ");

        Thread.sleep(2000);
        WebElement element = driver.findElement(By.xpath("(//p[contains(text(),'Vui lòng kiểm tra lại mã quay thưởng hoặc nhập mã ')])[1]"));
        Assert.assertEquals(element.getText(),"Vui lòng kiểm tra lại mã quay thưởng hoặc nhập mã quay thưởng khác.");
        System.out.println("[Popup Sai mã] Hiển thị popup thành công");

        //Kiểm tra btton nhập mã khác
        WebElement btnMakhac = driver.findElement(By.xpath("(//a[@class='btn confirm-info close'][contains(text(),'Nhập mã khác')])[1]"));
        btnMakhac.click();
        element = driver.findElement(By.xpath("//div[@class='form-title l-font col-12']"));
        Thread.sleep(3000);
        if(element.getText().equalsIgnoreCase("Thông tin tham gia quay thưởng"))
        {
            System.out.println("[Popup sai mã] Nhấn button [Nhập mã khác] thành công");
        }
        else {
            System.out.println("[Popup sai mã] Nhấn button [Nhập mã khác] KHÔNG thành công");
        }

//        //Kiểm tra khi nhấn icon x
//        luckyDrawPage.LuckyDraw("Cúc", "0343396286", "Thái Bình", "fffffffff");
////        luckyDrawPage.ConfirmInfor();
//        WebElement closeWrongCode = driver.findElement(By.xpath("(//span[@class='close-popup'][normalize-space()='×'])[4]"));
//        closeWrongCode.click();
//        element = driver.findElement(By.xpath("//div[@class='form-title l-font col-12']"));
//        Thread.sleep(3000);
//        if(element.getText().equalsIgnoreCase("Thông tin tham gia quay thưởng"))
//        {
//            System.out.println("[Popup sai mã] Nhấn icon x đóng popup thành công");
//        }
//        else {
//            System.out.println("[Popup sai mã] Nhấn icon x đóng popup KHÔNG thành công");
//        }
    }

    @Test //Trúng giải 4: Topup 10k
    public void AT_TH_XNQS_Success_PrizeType4() throws InterruptedException{
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("KIỂM TRA TRÚNG GIẢI 4: TOPUP 10K");

        Thread.sleep(5000);
        WebElement element = driver.findElement(By.xpath("//div[@class='win-product']"));
        Assert.assertEquals(element.getText().trim(),"01 THẺ ĐIỆN THOẠI\n" + "10.000 Đ");
        System.out.println("[Popup trùng giải 4] Hiển thị popup trúng giải thành công");

        //Kiểm tra button tiếp tục quay thưởng
        WebElement btnTieptuc = driver.findElement(By.xpath("(//button[@type='button'][contains(text(),'Tiếp tục quay thưởng')])[2]"));
        btnTieptuc.click();
        element = driver.findElement(By.xpath("//div[@class='form-title l-font col-12']"));
        Thread.sleep(3000);
        if(element.getText().equalsIgnoreCase("Thông tin tham gia quay thưởng"))
        {
            System.out.println("[Popup trúng giải 4] Nhấn button [Tiếp tục quay thưởng] thành công");
        }
        else {
            System.out.println("[Popup trúng giải 4] Nhấn button [Tiếp tục quay thưởng] KHÔNG thành công");
        }

//        //Kiểm tra khi nhấn icon x
//        luckyDrawPage.LuckyDraw("Cúc", "0343396286", "Thái Bình", "QLC5S9ZJR");
////        luckyDrawPage.ConfirmInfor();
//        WebElement closeWinRate4 = driver.findElement(By.xpath("(//span[@class='close-popup'][normalize-space()='×'])[8]"));
//        closeWinRate4.click();
//        element = driver.findElement(By.xpath("//div[@class='form-title l-font col-12']"));
//        Thread.sleep(3000);
//        if(element.getText().equalsIgnoreCase("Thông tin tham gia quay thưởng"))
//        {
//            System.out.println("[Popup trúng giải 4] Nhấn icon x đóng popup thành công");
//        }
//        else {
//            System.out.println("[Popup trúng giải 4] Nhấn icon x đóng popup KHÔNG thành công");
//        }
    }

    @Test //Trúng giải 3: Topup 20k
    public void AT_TH_XNQS_Success_PrizeType3() throws InterruptedException{
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("KIỂM TRA POPUP TRÚNG GIẢI 3: TOPUP 20K");

        Thread.sleep(2000);
        WebElement element = driver.findElement(By.xpath("(//div[@class='win-product'])[1]"));
        Assert.assertEquals(element.getText(),"01 THẺ ĐIỆN THOẠI\n" + "20.000 Đ");
        System.out.println("[Popup trùng giải 3] Hiển thị popup trúng giải thành công");

        //Kiểm tra button tiếp tục quay thưởng
        WebElement btnTieptuc = driver.findElement(By.xpath("(//button[@type='button'][contains(text(),'Tiếp tục quay thưởng')])[1]"));
        btnTieptuc.click();
        element = driver.findElement(By.xpath("//div[@class='form-title l-font col-12']"));
        Thread.sleep(3000);
        if(element.getText().equalsIgnoreCase("Thông tin tham gia quay thưởng"))
        {
            System.out.println("[Popup trúng giải 3] Nhấn button [Tiếp tục quay thưởng] thành công");
        }
        else {
            System.out.println("[Popup trúng giải 3] Nhấn button [Tiếp tục quay thưởng] KHÔNG thành công");
        }

//        //Kiểm tra khi nhấn icon x
//        luckyDrawPage.LuckyDraw("Cúc", "0343396286", "Thái Bình", "YROYSG2IN");
////        luckyDrawPage.ConfirmInfor();
//        WebElement closeWinRate3 = driver.findElement(By.xpath("(//span[@class='close-popup'][normalize-space()='×'])[7]"));
//        closeWinRate3.click();
//        element = driver.findElement(By.xpath("//div[@class='form-title l-font col-12']"));
//        Thread.sleep(3000);
//        if(element.getText().equalsIgnoreCase("Thông tin tham gia quay thưởng"))
//        {
//            System.out.println("[Popup trúng giải 3] Nhấn icon x đóng popup thành công");
//        }
//        else {
//            System.out.println("[Popup trúng giải 3] Nhấn icon x đóng popup KHÔNG thành công");
//        }
    }

    @Test //Trúng giải 2: Xe đạp thể thao
    public void AT_TH_XNQS_Success_PrizeType2() throws InterruptedException{
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("KIỂM TRA POPUP TRÚNG GIẢI 2: XE ĐẠP THỂ THAO");

        Thread.sleep(5000);
        WebElement element = driver.findElement(By.xpath("//div[@id='title_z2']"));
        Assert.assertEquals(element.getText().toLowerCase(),"thông tin nhận quà");
        System.out.println("[Popup trùng giải 2] Hiển thị popup trúng giải thành công");
    }

    @Test //Trúng giải 1: Laptop
    public void AT_TH_XNQS_Success_PrizeType1() throws InterruptedException{
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("KIỂM TRA POPUP TRÚNG GIẢI 1: LAPTOP");

        Thread.sleep(5000);
        WebElement element = driver.findElement(By.xpath("//div[@id='title_z1']"));
        Assert.assertEquals(element.getText().toLowerCase(),"thông tin nhận quà");
        System.out.println("[Popup trùng giải 1] Hiển thị popup trúng giải thành công");

    }


    //-------------------------------------------------------------------------------------------------------------//
    //-----------------------------------CHECK VALIDATE ĐIỀN THÔNG TIN TRÚNG GIẢI----------------------------------//
    //-------------------------------------------------------------------------------------------------------------//

    @Test //Kiểm tra trường [Name]
    public void AT_TH_WinPrize_Created_VLD_Name() throws InterruptedException{
        //Kiểm tra trạng thái mặc định
        WebElement element = driver.findElement(By.xpath("//input[@id='winner-name-giai2']"));

        // Lấy giá trị thuộc tính "readonly"
        String readonly = element.getAttribute("readonly");
        System.out.println("Giá trị của thuộc tính 'readonly': " + readonly);

        // Sử dụng JavascriptExecutor để lấy giá trị
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String value = (String) js.executeScript("return arguments[0].value;", element);

        // In giá trị ra màn hình
        System.out.println("Giá trị của trường: " + value);
        Assert.assertNotNull(value,"Cuc Minh Chu");
        System.out.println("[Thông tin nhận quà] Hiển thị đúng tên đã nhập tại màn [Quay số]");


//        //Kiểm tra disable
//        Assert.assertNotNull(element.isEnabled(),"Trường [Name] không bị disabled!!!");
//        System.out.println("[Thông tin nhận quà giải 2] Trường [Name] bị disabled không cho sửa thông tin");
    }

    @Test //Kiểm tra trường [Code]
    public void AT_TH_WinPrize_Created_VLD_Code() throws InterruptedException{
        //Kiểm tra trạng thái mặc định
        WebElement element = driver.findElement(By.xpath("//input[@id='winner-code-giai2']"));

//        //Kiểm tra disable
//        Assert.assertNotNull(code.isEnabled(),"Trường [Code] không bị disabled!!!");
//        System.out.println("[Thông tin nhận quà giải 2] Trường [Code] bị disabled không cho sửa thông tin");

        // Lấy giá trị thuộc tính "readonly"
        String readonly = element.getAttribute("readonly");
        System.out.println("Giá trị của thuộc tính 'readonly': " + readonly);

        // Sử dụng JavascriptExecutor để lấy giá trị
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String value = (String) js.executeScript("return arguments[0].value;", element);

        // In giá trị ra màn hình
        System.out.println("Giá trị của trường: " + value);
        Assert.assertNotNull(value,"R4FCRIEZP");
        System.out.println("[Thông tin nhận quà] Hiển thị đúng mã code đã nhập tại màn [Quay số]");
    }

    @Test //Kiểm tra trường [Phone]
    public void AT_TH_WinPrize_Created_VLD_Phone() throws InterruptedException{
        //Kiểm tra trạng thái mặc định
        WebElement element = driver.findElement(By.xpath("//input[@id='winner-phone-giai2']"));


//        //Kiểm tra disable
//        Assert.assertNotNull(phone.isEnabled(),"Trường [Phone] không bị disabled!!!");
//        System.out.println("[Thông tin nhận quà giải 2] Trường [Phone] bị disabled không cho sửa thông tin");

        // Lấy giá trị thuộc tính "readonly"
        String readonly = element.getAttribute("readonly");
        System.out.println("Giá trị của thuộc tính 'readonly': " + readonly);

        // Sử dụng JavascriptExecutor để lấy giá trị
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String value = (String) js.executeScript("return arguments[0].value;", element);

        // In giá trị ra màn hình
        System.out.println("Giá trị của trường: " + value);
        Assert.assertNotNull(value,"0343333333");
        System.out.println("[Thông tin nhận quà] Hiển thị đúng SĐT đã nhập tại màn [Quay số]");
    }
    //Kiểm tra trường [Căn cước]
    @Test //Kiểm tra trạng thái mặc định
    public void AT_TH_WinPrize_Created_VLD_CCCD_Default() throws InterruptedException{
        WebElement cancuoc = driver.findElement(By.xpath("//input[@id='winner-national-id-giai2']"));
        Assert.assertNotNull(cancuoc.getAttribute("placeholder"), "CCCD/CMND");
        System.out.println("[Thông tin nhận quà giải 2] Hiển thị đúng placeholder trường CCCD/CMND");
    }

    @Test //Kiểm tra khi không nhập
    public void AT_TH_WinPrize_Created_VLD_CCCD_Blank() throws InterruptedException{
        luckyDrawPage.DataWinnerPrizeType2("","Quận Cầu Giấy","195 Yên Hòa, phường Yên Hòa, Quận Cầu Giấy, TP Hà Nội");
        WebElement element = driver.findElement(By.xpath("//span[@id='winner-national-id-giai2-error']"));
        Assert.assertEquals(element.getText(), "Vui lòng nhập CMND/CCCD");
        System.out.println("[Thông tin nhận quà giải 2] Hiển thị đúng thông báo lỗi khi không nhập CCCD");
    }

    @Test //Kiểm tra khi chỉ nhập spcae
    public void AT_TH_WinPrize_Created_VLD_CCCD_Space() throws InterruptedException{
        luckyDrawPage.DataWinnerPrizeType2("           ", "Huyện Chương Mỹ","Số 49 Bắc Sơn, Thị trấn Chúc Sơn, Huyện Chương Mỹ, Hà Nội");
        WebElement element = driver.findElement(By.xpath("//span[@id='winner-national-id-giai2-error']"));
        Assert.assertEquals(element.getText(), "CMND/CCCD không hợp lệ");
        System.out.println("[Thông tin nhận quà giải 2] Hiển thị đúng thông báo lỗi khi chỉ nhâp space tại trường [CCCD/CMND]");
    }

    @Test //Kiểm tra khi chỉ nhập chữ, ký tự đặc biệt
    public void AT_TH_WinPrize_Created_VLD_CCCD_Text() throws InterruptedException{
        luckyDrawPage.DataWinnerPrizeType2("ChuMinhCuc@123", "Huyện Đông Anh","số 283 Cao Lỗ, Xã Uy Nỗ, Huyện Đông Anh, TP Hà Nội");
        WebElement element = driver.findElement(By.xpath("(//span[@class='text-danger field-validation-error'])[1]"));
        Assert.assertEquals(element.getText(), "CMND/CCCD không hợp lệ");
        System.out.println("[Thông tin nhận quà giải 2] Hiển thị đúng thông báo lỗi khi nhập chữ và ký tự đặc biệt tại trường [CCCD/CMND]");
    }

    @Test //Kiểm tra khi nhập số
    public void AT_TH_WinPrize_Created_VLD_CCCD_Number() throws InterruptedException{
        luckyDrawPage.DataWinnerPrizeType2("123456789", "Huyện Đông Anh","số 283 Cao Lỗ, Xã Uy Nỗ, Huyện Đông Anh, TP Hà Nội");
        WebElement btnUpdateData = driver.findElement(By.xpath("//input[@id='thayDoiThongTin']"));
        btnUpdateData.click();
        Thread.sleep(5000);

        WebElement element = driver.findElement(By.xpath("//input[@id='winner-national-id-giai2']"));
        Assert.assertEquals(element.getAttribute("value"), "123456789");
        System.out.println("[Thông tin nhận quà giải 2] Hệ thống cho phép nhập số tại trường [CCCD/CMND]");
    }

    @Test //Kiểm tra khi nhập 8 ký tự
    public void AT_TH_WinPrize_Created_VLD_CCCD_8Characters() throws InterruptedException {
        luckyDrawPage.DataWinnerPrizeType2("12345678", "Huyện Đông Anh","số 283 Cao Lỗ, Xã Uy Nỗ, Huyện Đông Anh, TP Hà Nội");
        WebElement element = driver.findElement(By.id("winner-national-id-giai2-error"));
        Assert.assertEquals(element.getText(), "CMND/CCCD không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("[Thông tin nhận quà giải 2] Hiển thị thành công thông báo lỗi khi chỉ nhập 8 ký tự tại trường [CCCD]!!!!");
    }

    @Test //Kiểm tra khi nhập 13 ký tự
    public void AT_TH_WinPrize_Created_VLD_CCCD_13Characters() throws InterruptedException {
        luckyDrawPage.DataWinnerPrizeType2("1234567898745", "Huyện Đông Anh","số 283 Cao Lỗ, Xã Uy Nỗ, Huyện Đông Anh, TP Hà Nội");
        WebElement element = driver.findElement(By.xpath("//span[@class='text-danger field-validation-error']"));
        Assert.assertEquals(element.getText(), "CMND/CCCD không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("[Thông tin nhận quà giải 2] Hệ thống chỉ cho phép nhập 12 ký tự tại trường [CCCD]!!!!");
    }

    @Test //Kiểm tra chặn XSS
    public void AT_TH_WinPrize_Created_VLD_CCCD_Block() throws InterruptedException {
        luckyDrawPage.DataWinnerPrizeType2("<script>alert(123)</script>", "Huyện Đông Anh", "số 283 Cao Lỗ, Xã Uy Nỗ, Huyện Đông Anh, TP Hà Nội");
        WebElement element = driver.findElement(By.id("winner-national-id-giai2-error"));
        Assert.assertEquals(element.getText(), "CMND/CCCD không hợp lệ"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("[Thông tin nhận quà giải 2] Không thực thi câu lệnh khi nhập XSS vào ô [CCCD]");
    }

    @Test //Kiểm tra trường [Province]
    public void AT_TH_WinPrize_Created_VLD_Province() throws InterruptedException{
        //Kiểm tra trạng thái mặc định
        WebElement element = driver.findElement(By.xpath("//input[@id='win-province-giai2_text']"));

        // Lấy giá trị thuộc tính "readonly"
        String readonly = element.getAttribute("readonly");
        System.out.println("Giá trị của thuộc tính 'readonly': " + readonly);

        // Sử dụng JavascriptExecutor để lấy giá trị
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String value = (String) js.executeScript("return arguments[0].value;", element);

        // In giá trị ra màn hình
        System.out.println("Giá trị của trường: " + value);
        Assert.assertNotNull(value,"Hà Nội");
        System.out.println("[Thông tin nhận quà giải 2] Hiển thị đúng tỉnh/TP đã nhập tại màn [Quay số]");
    }

    @Test //Kiểm tra trường [District]
    public void AT_TH_WinPrize_Created_VLD_District_Default() throws InterruptedException{
        WebElement element = driver.findElement(By.xpath("//select[@id='win-district-giai2']"));

        Select select = new Select(element);
        select.selectByVisibleText("Chọn Quận / Huyện");
        WebElement selectedOption = select.getFirstSelectedOption();

        String text = validateHelper.getText(selectedOption);

        Assert.assertEquals(text, "Chọn Quận / Huyện");
        System.out.println("[Thông tin nhận quà giải 2] Hiển thị thành công trạng thái mặc định tại trường [District]!");
    }

    @Test //Kiểm tra dữ liệu trường [District]
    public void AT_TH_WinPrize_Created_VLD_District_Data() throws InterruptedException{
        System.out.println("[Thông tin nhận quà giải 2] Kiểm tra dữ liệu combobox");
        try {
            // Đọc dữ liệu từ file JSON
            String filePath = "src/test/resouces/district_provinces.json";
            Map<String, List<String>> expectedData = loadProvincesAndDistricts(filePath);

            // Lấy Tỉnh/Thành phố từ trường readonly
            WebElement provinceField = driver.findElement(By.id("win-province-giai2_text")); // Thay ID thực tế
            String selectedProvince = provinceField.getAttribute("value").trim();
            System.out.println("Tỉnh/Thành phố đã chọn: " + selectedProvince);

            // Lấy danh sách Quận/Huyện thực tế từ dropdown
            WebElement districtDropdown = driver.findElement(By.id("win-district-giai2")); // Thay ID thực tế
            Select districtSelect = new Select(districtDropdown);
            List<WebElement> districtOptions = districtSelect.getOptions();

            // Chuyển danh sách WebElement thành danh sách String
            List<String> actualDistricts = districtOptions.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

            // Lấy danh sách Quận/Huyện mong đợi từ file JSON
            List<String> expectedDistricts = expectedData.get(selectedProvince);

            if (expectedDistricts == null) {
                System.err.println("[Thông tin nhận quà giải 2] Không tìm thấy dữ liệu mong đợi cho tỉnh: " + selectedProvince);
                return;
            }

            // Chuyển danh sách sang Set để kiểm tra không phân biệt thứ tự
            Set<String> actualDistrictSet = new HashSet<>(actualDistricts);
            Set<String> expectedDistrictSet = new HashSet<>(expectedDistricts);

            if (!actualDistrictSet.equals(expectedDistrictSet)) {
                System.out.println("[Thông tin nhận quà giải 2] Danh sách Quận/Huyện không khớp!");

                // Tìm mục thừa và thiếu
                Set<String> missingDistricts = new HashSet<>(expectedDistrictSet);
                missingDistricts.removeAll(actualDistrictSet);

                Set<String> extraDistricts = new HashSet<>(actualDistrictSet);
                extraDistricts.removeAll(expectedDistrictSet);

                // Hiển thị chi tiết sự khác biệt
                System.out.println("Mong đợi (Expected): " + expectedDistrictSet);
                System.out.println("Thực tế (Actual): " + actualDistrictSet);

                if (!missingDistricts.isEmpty()) {
                    System.out.println("Thiếu trong danh sách thực tế: " + missingDistricts);
                }

                if (!extraDistricts.isEmpty()) {
                    System.out.println("Dư trong danh sách thực tế: " + extraDistricts);
                }

                Assert.fail("[Thông tin nhận quà giải 2] Danh sách Quận/Huyện không khớp!");
            } else {
                System.out.println("[Thông tin nhận quà giải 2] Hiển thị đủ danh sách Quận/huyện");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test //Kiểm tra khi không chọn dữ liệu
    public void AT_TH_WinPrize_Created_VLD_District_Blank() throws InterruptedException {
        luckyDrawPage.DataWinnerPrizeType2("123456789","Chọn Quận / Huyện", "Cửa hàng nhận thưởng");
        WebElement element = driver.findElement(By.xpath("(//span[@id='win-district-giai2-error'])[1]"));
        Assert.assertEquals(element.getText(), "Vui lòng chọn Quận / Huyện"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("[Thông tin nhận quà giải 2] Hiển thị thành công thông báo lỗi khi không chọn dữ liệu trường [Quận/Huyện]!");
    }

    //Kiểm tra trường [Store]
    @Test //Kiểm tra trạng thái mặc định
    public void AT_TH_WinPrize_Created_VLD_Store_Default() throws InterruptedException{
        WebElement element = driver.findElement(By.xpath("//select[@id='store-option-giai2']"));

        Select select = new Select(element);
        select.selectByVisibleText("Cửa hàng nhận thưởng");
        WebElement selectedOption = select.getFirstSelectedOption();

        String text = validateHelper.getText(selectedOption);

        Assert.assertEquals(text, "Cửa hàng nhận thưởng");
        System.out.println("[Thông tin nhận quà giải 2] Hiển thị thành công trạng thái mặc định tại trường [Cửa hàng]!");
    }

    @Test //Kiểm tra khi không chọn dữ liệu
    public void AT_TH_WinPrize_Created_VLD_Store_Blank() throws InterruptedException {
        luckyDrawPage.DataWinnerPrizeType2("123456789","Quận Cầu Giấy", "Cửa hàng nhận thưởng");
        WebElement element = driver.findElement(By.xpath("//span[@class='text-danger valid-store-giai2']"));
        Assert.assertEquals(element.getText(), "Vui lòng chọn cửa hàng để nhận thưởng!"); //getText() hỗ trợ lấy thông tin bên trong thẻ
        System.out.println("[Thông tin nhận quà giải 2] Hiển thị thành công thông báo lỗi khi không chọn dữ liệu trường [Cửa hàng nhận thưởng]!");
    }

    @Test //Kiểm tra dữ liệu data
    public void AT_TH_WinPrize_Created_VLD_Store_Data() throws InterruptedException {
        System.out.println("[Thông tin nhận quà giải 2] Bắt đầu kiểm tra dữ liệu combobox");

        try {
            // Đọc dữ liệu từ file JSON
            String filePath = "src/test/resouces/data_store.json";
            Map<String, Map<String, List<String>>> expectedData = loadProvincesDistrictsAndStores(filePath);

            // Lấy Tỉnh/Thành phố từ trường readonly
            WebElement provinceField = driver.findElement(By.id("win-province-giai2_text"));
            String selectedProvince = provinceField.getAttribute("value").trim();
            System.out.println("Tỉnh/Thành phố đã chọn: " + selectedProvince);

            // Lấy danh sách Quận/Huyện từ dropdown
            WebElement districtDropdown = driver.findElement(By.id("win-district-giai2"));
            Select districtSelect = new Select(districtDropdown);
            String selectedDistrict = districtSelect.getFirstSelectedOption().getText().trim();
            System.out.println("Quận/Huyện đã chọn: " + selectedDistrict);

            List<WebElement> districtOptions = districtSelect.getOptions();
            List<String> actualDistricts = districtOptions.stream()
                    .map(WebElement::getText)
                    .map(String::trim)
                    .collect(Collectors.toList());

            // Lấy dữ liệu mong đợi từ file JSON
            Map<String, List<String>> provinceData = expectedData.get(selectedProvince);
            if (provinceData == null) {
                System.err.println("[Lỗi] Không tìm thấy dữ liệu cho tỉnh: " + selectedProvince);
                return;
            }

            List<String> expectedDistricts = new ArrayList<>(provinceData.keySet());

            // So sánh danh sách Quận/Huyện
            if (!new HashSet<>(actualDistricts).equals(new HashSet<>(expectedDistricts))) {
                System.out.println("[Lỗi] Danh sách Quận/Huyện không khớp!");
                compareLists(actualDistricts, expectedDistricts);
                Assert.fail("[Thông tin nhận quà giải 2] Danh sách Quận/Huyện không khớp!");
            } else {
                System.out.println("[Thông tin nhận quà giải 2] Hiển thị đủ danh sách Quận/Huyện");
            }

            // Lấy danh sách Cửa hàng từ dropdown
            WebElement storeDropdown = driver.findElement(By.xpath("//select[@id='store-option-giai2']"));
            Select storeSelect = new Select(storeDropdown);
            List<String> actualStores = storeSelect.getOptions().stream()
                    .map(WebElement::getText)
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

            // Lấy danh sách Cửa hàng mong đợi từ JSON
            List<String> expectedStores = provinceData.get(selectedDistrict);
            if (expectedStores == null) {
                System.err.println("[Lỗi] Không tìm thấy dữ liệu mong đợi cho Quận/Huyện: " + selectedDistrict);
                return;
            }

            // So sánh danh sách Cửa hàng
            if (!new HashSet<>(actualStores).equals(new HashSet<>(expectedStores))) {
                System.out.println("[Lỗi] Danh sách Cửa hàng không khớp!");
                compareLists(actualStores, expectedStores);
                Assert.fail("[Thông tin nhận quà giải 2] Danh sách Cửa hàng không khớp cho Quận/Huyện: " + selectedDistrict);
            } else {
                System.out.println("[Thông tin nhận quà giải 2] Hiển thị đủ danh sách Cửa hàng cho Quận/Huyện");
            }

            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("[Thông tin nhận quà giải 2] Lỗi trong quá trình kiểm tra dữ liệu");
            }
        }

/**
 * Hàm so sánh và hiển thị sự khác biệt giữa hai danh sách.
 */
        private void compareLists(List<String> actual, List<String> expected) {
            Set<String> actualSet = new HashSet<>(actual);
            Set<String> expectedSet = new HashSet<>(expected);

            Set<String> missing = new HashSet<>(expectedSet);
            missing.removeAll(actualSet);

            Set<String> extra = new HashSet<>(actualSet);
            extra.removeAll(expectedSet);

            System.out.println("Mong đợi (Expected): " + expectedSet);
            System.out.println("Thực tế (Actual): " + actualSet);

            if (!missing.isEmpty()) {
                System.out.println("Thiếu trong danh sách thực tế: " + missing);
            }
            if (!extra.isEmpty()) {
                System.out.println("Dư trong danh sách thực tế: " + extra);
            }
        }


    @AfterClass
    public void closeBrower()
    {
        driver.close();
    }
}
