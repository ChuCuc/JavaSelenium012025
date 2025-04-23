package TH.helpers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelHelpers {
    private FileInputStream fis;
    private FileOutputStream fileOut;
    private Workbook wb;
    private Sheet sh;
    private Cell cell;
    private Row row;
    private CellStyle cellStyle;
    private Color mycolor;
    private String excelFilePath;
    private Map<String, Integer> columns = new HashMap<>();

    public String setExcelFile(String ExeclPath, String SheetName) throws Exception
    {
        try
        {
            File f = new File(ExeclPath); //Đọc file

            if (!f.exists()) //Kiểm tra xem có tồn tại hay không
            {
                f.createNewFile();
                System.out.println("File doesn't exist, so created!");
            }

            fis = new FileInputStream(ExeclPath);
            wb = WorkbookFactory.create(fis);
            sh = wb.getSheet(SheetName);
//            sh = wb.getSheetAt(0); //0: Indexof 1st sheet
            if (sh == null)
            {
                sh = wb.createSheet(SheetName);
            }

            this.excelFilePath =ExeclPath;

            //adding all the column header names to the map 'columns'
            sh.getRow(0).forEach(cell->{
                columns.put(cell.getStringCellValue(), cell.getColumnIndex());
            });
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return ExeclPath;
    }

    public String getCellData (int rownum, int colnum) throws Exception //Get từng ô data
    {
        try
        {
            cell = sh.getRow(rownum).getCell(colnum);
            String CellData = null;
            switch (cell.getCellType())
            {
                case STRING:
                    CellData = cell.getStringCellValue();
                    break;

                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell))
                    {
                        CellData = String.valueOf(cell.getDateCellValue());
                    }
                    else
                    {
                        CellData = String.valueOf((long)cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    CellData = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    CellData = "";
                    break;
            }
            return CellData;
        }
        catch (Exception e)
        {
            return "";
        }
    }

    //Gọi ra hàm
    public String getCellData (String columnName, int rownum) throws Exception
    {
        return getCellData(rownum, columns.get(columnName));
    }

    public void setCellData (String text, int rownum, int colnum) throws Exception
    {
        try
        {
            row = sh.getRow(rownum);
            if (row == null)
            {
                row = sh.createRow(rownum);
            }
            cell = row.getCell(colnum);

            if (cell == null)
            {
                cell = row.createCell(colnum);
            }
            cell.setCellValue(text);

            fileOut = new FileOutputStream(excelFilePath);
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        }
        catch (Exception e)
        {
            throw (e);
        }
    }



}
