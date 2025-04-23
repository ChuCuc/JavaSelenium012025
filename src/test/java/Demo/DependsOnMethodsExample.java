package Demo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DependsOnMethodsExample {

    @Test
    public void loginTest() {
        System.out.println("Đăng nhập thành công.");
        // Nếu kiểm tra thất bại, các test phụ thuộc sẽ không chạy
        Assert.assertTrue(true);
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void viewProfileTest() {
        System.out.println("Xem thông tin cá nhân.");
    }

    @Test(dependsOnMethods = {"loginTest", "viewProfileTest"})
    public void logoutTest() {
        System.out.println("Đăng xuất thành công.");
    }
}
