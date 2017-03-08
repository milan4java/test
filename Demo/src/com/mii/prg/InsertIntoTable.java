package com.mii.prg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertIntoTable {
	
    public static void main(String[] args) {
        try {
        	
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.6.59:3306/fortilogs", "root", "admin");
            Statement statement = conn.createStatement();
            String query = null;

            	for(int i=319298;i<=1000000;i++)
                {
                   query = "insert into tlog VALUES ("+i+",'deny','Skype','Collaboration','10','NOSKYPE','2016-09-01 11:08:13','2016-09-01 11:09:06','2016-09-01 11:10:00',1,'notice','','10.10.1.3','','157.55.130.174','','TCP',53,331,286,'1348878711',51,'MIL_TESTER_AMS_FW_VRRP',0,'MIL1','United States','United States','','block','','','','','',1472708348235,1315,40005,'Mobile','8162439','','','','',0);";
                   if(i%100==0)
                   {
                	   statement.executeBatch();
                   }
                   else
                   {
                	   statement.addBatch(query);
                   }
            	}
            		statement.executeBatch();
            
            System.out.println("Finished writing in database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
