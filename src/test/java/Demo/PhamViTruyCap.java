package Demo;

class A_Private
{
    private int data = 40;

    private void msg()
    {
        System.out.println(data);
    }
}

public class PhamViTruyCap{
    protected void msg()
    {
        System.out.println("This is msg fuction at PhamViTruyCap");
    }

    protected void msgSuccess()
    {
        System.out.println("This is msg Success fuction at PhamViTruyCap");
    }

    protected void msgError()
    {
        System.out.println("This is msg Error fuction at PhamViTruyCap");
    }

    public int cong2so(int a, int b)
    {
        return a+b;
    }

    public static void main (String args[])
    {
        A_Private obj = new A_Private(); //khởi tạo object
//        System.out.println(obj.data); //Không được gọi vì class trên là private
//        obj.msg(); //Không được gọi vì class trên là private
    }
}