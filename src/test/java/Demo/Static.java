package Demo;

class Student
{
    public String name;
    public int age;
    public float height;

    public static String universityName = "Chu Minh Cuc";
    public static int total = 0;

    public Student (String name, int age, float height)
    {
        this.name = name;
        this.age = age;
        this.height = height;
        total += 1;
    }
}

public class Static {
    public static void main (String[] args)
    {
        Student a = new Student("Cuc", 21, 1.7f);
        System.out.println("Uniersity (From class):" + Student.universityName);
        System.out.println("Uniersity (From instance):" + a.universityName);

        System.out.println("Total (From class):" + Student.total);
        Student b = new Student("Minh", 24, 1.7f);
        System.out.println("Total (From instance):" + b.total);
    }
}
