package Demo;

class TestClass
{
    public int number1 = 10;
    public int number2 = 20;

    public int Cong(int a, int b)
    {
        return a+b;
    }
    public void Print(int a, int b)
    {
        System.out.println(Cong(a,b));
    }
}
public class ClassAndObject {
    public static void main (String[] args)
    {
        int a = 2;
        int b = 4;

        //Khởi tạo một object (đối tượng) thuộc một class
        TestClass obClass = new TestClass();

        //Cách gọi các thành phần trong 1 class thông qua object
        obClass.Print(a,b);
//        obClass.Cong(a,b);
    }
}
