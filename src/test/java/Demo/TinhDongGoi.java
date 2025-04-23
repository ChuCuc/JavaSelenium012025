package Demo;

public class TinhDongGoi {
    private String name;
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public static void main(String[] args)
    {
        TinhDongGoi tinhDongGoi = new TinhDongGoi();
        tinhDongGoi.setName("");
    }
}
