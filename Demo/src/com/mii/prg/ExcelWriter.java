package com.mii.prg;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter 
{
	public static void main(String args[])
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Java Books");
		
		Object[][] bookData = {{"Head First Java", "Kathy Serria", 79},{"Effective Java", "Joshua Bloch", 36},{"Clean Code", "Robert martin", 42},{"Thinking in Java", "Bruce Eckel", 35}};
		int rowCount = 0;
		
			for (Object[] book : bookData) {
				Row row = sheet.createRow(++rowCount);
				int columnCount = 0;

				for (Object field : book) 
				{
					Cell cell = row.createCell(++columnCount);
					if (field instanceof String) 
					{
						cell.setCellValue((String) field);
					} 
					else if (field instanceof Integer) 
					{
						cell.setCellValue((Integer) field);
					}
				}
			}
			
			try
			{
				FileOutputStream outputStream = new FileOutputStream("E:\\JavaBooks.xlsx");
				workbook.write(outputStream);
				System.out.println("created the class");
			}
			catch(Exception e)
			{
			
			}
			finally
			{
				
			}
	}
}