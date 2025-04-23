package Demo;


public class Fuction {
    static void InputData (int arr[] , int number_value)
    {
        for (int i = 0 ; i< arr.length; i++)
            {
                arr[i] = number_value;
            }

    }

    static void PrintData (int arr[], int number)
    {
        for (int i = 0 ; i< arr.length; i++)
        {
            //Cộng thêm một giá trị là 5 vào từng phần tử mảng khi in
            System.out.println("Phan tu thu " + (i+1) + " la " + (arr[i] + number));
        }

    }

    // Không trả về (void)
    static void min (int arr[])
    {
        int min = arr[0];
        for (int i=1; i < arr.length; i++)
            if (min > arr[i])
            {
                min = arr[i];
            }
        System.out.println(min);
    }

    //Hàm trả về (int)
    static int min2 (int arr[])
    {
        int min = arr[0];
        for (int i=1; i < arr.length; i++)
            if (min > arr[i])
            {
                min = arr[i];
            }
        return min;
    }

    public static void main (String[] args)
    {
        int mang[] = new int[50];
//        int mang1[] = new int[100];
//
//        InputData(mang, 10);
//        PrintData(mang, 5);
//
//        InputData(mang1, 9);
//        PrintData(mang1, 8);
//        int bien1 = min(mang);
        int bien2 = min2(mang);
    }
}
