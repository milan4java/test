package com.mii.prg;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CSVusingNIO {
	FileOutputStream fout = null;
	FileReader fr = null;
	static BufferedWriter br = null;

	public static void main(String args[]) {

		// new writing_data_csv_IO();
		// new writing_mappedbytebuffer_NIO();
		// new copy();

		// reading from record.csv & writing to the destination.csv file using BYteBuffer(23-1------------- GOOD)
		
		
		 FileChannel in = null; FileChannel out = null; 
		 int bufferSizeKB = 4;
		 int bufferSize = bufferSizeKB * 1024;
		 ByteBuffer bytebuffer;
		 try { 
			 in = new FileInputStream("E:\\java created files\\outputusingnio.csv").getChannel(); 
			 out = new FileOutputStream("E:\\java created files\\output.csv").getChannel();
			 bytebuffer = ByteBuffer.allocate(bufferSize);// 4 KB(4096 bytes) long
			 long starttime = System.currentTimeMillis(); 
		 int bytecount = 0; // read data from record.csv into byte buffer
		  
		 while ((bytecount = in.read(bytebuffer)) > 0) {
		 
		  // flip the buffer which set the limit to current position, andposition to 0. 
			 bytebuffer.flip(); 
			 out.write(bytebuffer); // Write data from ByteBuffer to file
			 bytebuffer.clear(); // For the next read
		  }
		  
		  System.out.println("Total Time: (.csv read/write)(NIO):--> " +(System.currentTimeMillis() - starttime));
		  } catch (Exception e) {
			  e.printStackTrace(); 
		  } finally { if (in != null & out != null) { try
		  { in.close(); out.close(); } catch (IOException e) {
		  
		  }
		  
		  } }
		 

		// reading data from .csv file
		/*
		 * String line; int rcount=0; String words[]=null; XSSFWorkbook book=new
		 * XSSFWorkbook(); XSSFSheet sheet1=book.createSheet("First Sheet");
		 * 
		 * try { System.out.println("Start Time( ExcelBook.xlsx write):--> "
		 * +System.currentTimeMillis()); BufferedReader br=new
		 * BufferedReader(new FileReader("record.csv"));
		 * while((line=br.readLine())!=null){ rcount++; words = line.split(",");
		 * XSSFRow row=sheet1.createRow(rcount); for(int
		 * c=0;c<words.length;c++){ XSSFCell cell=row.createCell(c);
		 * cell.setCellValue(words[c]); } } // System.out.println(rcount);
		 * FileOutputStream fout=new FileOutputStream("ExcelBook.xlsx");
		 * 
		 * book.write(fout); System.out.println(
		 * "End Time(ExcelBook.xlsx write):--> "+System.currentTimeMillis());
		 * fout.close(); System.out.println(
		 * "Data Written successfully in Excelbook.xlsx");
		 * 
		 * } catch (Exception e) {
		 * 
		 * e.printStackTrace(); }
		 */

	}
}
