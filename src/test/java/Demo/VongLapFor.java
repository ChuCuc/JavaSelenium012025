package Demo;

public class VongLapFor {
    public static void main (String[] args)
    {
        //int i = 1 để khởi tạo giá trị bến chạy từ i=1 đến điều kiện
        //i<=10 giới hạn biến chạy i lại đến khi = 10
        //i++ để tăng i sau mooxi lần làm việc lên 1 giá trị đến khi thoả điều kiện

        String nameString = "Chu Minh Cuc";
        for (int i=1; i<=10; i++)
        {
            System.out.println("Số thứ tự:" +i);
            System.out.println("Hello team");
            System.out.println(nameString);
        }
    }
}
