package com.mii.prg;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class CSVUtilExample {
    public static void main(String[] args) {
        String filename ="E:\\java created files\\outputusingnio.csv";
        try {
        	int x=1,y=50000;
            FileChannel fw = new FileOutputStream(filename).getChannel();
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.102.13:3306/hhmefep", "root", "admin");
            long startTime = System.currentTimeMillis();
            ByteBuffer bytebuffer = ByteBuffer.allocate(1000*1024);
            try
            {
//            	for(int i=0;i<3500000;i+=10000)
            	
            	for(int i=0;i<3500000;i+=1000)
                {
                    String query = "select * from performancemeasure order by 1 asc limit "+i+",1000";                    
//            		String query = "select * from performancemeasure order by 1 asc limit "+i+",50";
            		Statement stmt = conn.createStatement();            		
            		stmt.setFetchSize(100);
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                    	
                        bytebuffer.put(rs.getString(1).getBytes());
                        bytebuffer.put((",").getBytes());
                        
                        bytebuffer.put(rs.getString(2).getBytes());
                        bytebuffer.put((",").getBytes());
                        
                        bytebuffer.put(rs.getString(3).getBytes());
                        bytebuffer.put((",").getBytes());
                        
                        bytebuffer.put(rs.getString(4).getBytes());
                        bytebuffer.put((",").getBytes());
                        
                        bytebuffer.put(rs.getString(5).getBytes());
                        bytebuffer.put((",").getBytes());
                        
                        bytebuffer.put(rs.getString(6).getBytes());
                        bytebuffer.put((",").getBytes());
                        
                        bytebuffer.put(rs.getString(7).getBytes());
                        bytebuffer.put((",").getBytes());
                        
                        bytebuffer.put(rs.getString(8).getBytes());
                        bytebuffer.put((",").getBytes());
                        
                        bytebuffer.put(rs.getString(9).getBytes());
                        bytebuffer.put((",").getBytes());
                        
                        bytebuffer.put(rs.getString(10).getBytes());
                        bytebuffer.put(("\n").getBytes());
                    }
	                    bytebuffer.flip();
	                    fw.write(bytebuffer);
	     		       	bytebuffer.clear();
                }
            }
            finally
            {
            	fw.close();
                conn.close();    	
            }
            
            System.out.println("Finished creating files in "+(System.currentTimeMillis()-startTime)+" msec");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}