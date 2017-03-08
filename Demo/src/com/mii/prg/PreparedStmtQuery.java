package com.mii.prg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class PreparedStmtQuery {

		private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
		private static final String DB_CONNECTION = "jdbc:mysql://192.168.102.13:3306/hhmefep?useServerPrepStmts=true&prepStmtCacheSize=250&cachePrepStmts=true&prepStmtCacheSize=250&prepStmtCacheSqlLimit=1024&cacheServerConfiguration=true";
		private static final String DB_USER = "root";
		private static final String DB_PASSWORD = "admin";
		 private static final Logger LOGGER = Logger.getLogger(PreparedStmtQuery.class.getName());
		
			 
		 static XSSFSheet  sheet;
		 static XSSFWorkbook workbook;
		 static int counter=1;
		 static FileOutputStream outputStream = null;
		 
		public static void main(String[] argv) throws SecurityException, IOException {
//			FileInputStream file = new FileInputStream(new File("E:\\output.xlsx"));	
				  workbook = new XSSFWorkbook();
	              sheet = workbook.createSheet("output");
	              outputStream = new FileOutputStream("E:\\output.xlsx");

/*			FileHandler  fh = new FileHandler("E:/MyLogFile.log");  	
			LOGGER.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	        LOGGER.setLevel(Level.FINEST);
*/	        
			try 
			{	
					for(counter=1;counter<=100;counter++)
					{
						Row row = sheet.createRow(counter);
						updateRecordToTable();
						updateRecordToTableUsingHikari();
						updateRecordToTableUsingC3p0();
					}
					workbook.write(outputStream);
					
			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
//				fh.close();
			}

		}

		private static void updateRecordToTable() throws SQLException, ClassNotFoundException, IOException {

			Connection dbConnectionForMysql = null;
			PreparedStatement preparedStatementForMysql = null;

			String updateTableSQL = "UPDATE bgan_ip_addresses set usage_counter = usage_counter + 1 where ip_address = ?";

			try {
					Class.forName(DB_DRIVER);
					dbConnectionForMysql = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
					dbConnectionForMysql.setAutoCommit(false);
					long startTime = System.currentTimeMillis();
					for(int i =0;i<100;i++){
						preparedStatementForMysql = dbConnectionForMysql.prepareStatement(updateTableSQL);
						preparedStatementForMysql.setString(1, "81.1.8.137");
						preparedStatementForMysql.addBatch();
						preparedStatementForMysql.executeBatch();
					}
					dbConnectionForMysql.commit();
				    
					Row row = sheet.getRow(counter);
			        Cell column = row.createCell(1);
			        column.setCellValue((System.currentTimeMillis()-startTime));
			        
			        

			} catch (SQLException e) { 

				System.out.println(e.getMessage());

			} finally {

				if (preparedStatementForMysql != null) {
					preparedStatementForMysql.close();
				}

				if (dbConnectionForMysql != null) {
					dbConnectionForMysql.close();
				}

			}

		}
		
		
		
	private static void updateRecordToTableUsingHikari() throws SQLException, ClassNotFoundException, IOException 
	{
        HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://192.168.102.13:3306/hhmefep");
		config.setUsername("root");
		config.setPassword("admin");
		config.setMaximumPoolSize(10);
		config.setAutoCommit(false);
		config.addDataSourceProperty("cachePrepStmts", "true");
	    config.addDataSourceProperty("prepStmtCacheSize", "250");
	    config.addDataSourceProperty("prepStmtCacheSqlLimit", "1024");
	    config.addDataSourceProperty("useServerPrepStmts", "true");
	    config.addDataSourceProperty("cacheServerConfiguration", "true");

		DataSource   dataSource = new HikariDataSource(config);
		Connection dbConnectionForHikari = dataSource.getConnection();
        PreparedStatement preparedStatementForHikari = null;
        String updateTableSQL = "UPDATE bgan_ip_addresses set usage_counter = usage_counter + 1 where ip_address = ?";
    	try {
    		dbConnectionForHikari.setAutoCommit(false);
				long startTime = System.currentTimeMillis();
				for(int i =0;i<100;i++){
					preparedStatementForHikari = dbConnectionForHikari.prepareStatement(updateTableSQL);
					preparedStatementForHikari.setString(1, "81.1.8.137");
					// System.out.println("Waiting for response from server...");
					preparedStatementForHikari.addBatch();
				}
	
				/*preparedStatement = dbConnection.prepareStatement(updateTableSQL);
				preparedStatement.setString(1, "81.1.8.138");
				preparedStatement.addBatch();*/
				
				preparedStatementForHikari.executeBatch();
			
//			preparedStatement.executeUpdate();
			
				dbConnectionForHikari.commit();
				Row row = sheet.getRow(counter);
		        Cell column = row.createCell(2);
				column.setCellValue((System.currentTimeMillis()-startTime));
		        
			} finally {

				if (preparedStatementForHikari != null) {
					preparedStatementForHikari.close();
				}

				if (dbConnectionForHikari != null) {
					dbConnectionForHikari.close();
				}

			}
	}
	
	private static void updateRecordToTableUsingC3p0() throws SQLException, ClassNotFoundException 
	{
		 ComboPooledDataSource cpds = new ComboPooledDataSource();
		 Connection dbConnectionForc3p0=null;
		 PreparedStatement preparedStatementForc3p0 = null;
         try 
         {
             cpds=new ComboPooledDataSource();
             cpds.setDriverClass("com.mysql.jdbc.Driver");
             cpds.setJdbcUrl("jdbc:mysql://192.168.102.13:3306/hhmefep");
             cpds.setUser("root");
             cpds.setPassword("admin");
             
             dbConnectionForc3p0 = cpds.getConnection();
             dbConnectionForc3p0 = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
             dbConnectionForc3p0.setAutoCommit(false);
				
				long startTime = System.currentTimeMillis();
				String updateTableSQL = "UPDATE bgan_ip_addresses set usage_counter = usage_counter + 1 where ip_address = ?";
				for(int i =0;i<100;i++){
					preparedStatementForc3p0 = dbConnectionForc3p0.prepareStatement(updateTableSQL);
					preparedStatementForc3p0.setString(1, "81.1.8.137");
					preparedStatementForc3p0.addBatch();
					preparedStatementForc3p0.executeBatch();
				}
				dbConnectionForc3p0.commit();
				Row row = sheet.getRow(counter);
		        Cell column = row.createCell(3);
				column.setCellValue((System.currentTimeMillis()-startTime));
         } 
         catch (Exception ex) 
         {
             ex.getMessage();
         }
         finally
         {
        	 if (preparedStatementForc3p0 != null) {
        		 preparedStatementForc3p0.close();
				}

				if (dbConnectionForc3p0 != null) {
					dbConnectionForc3p0.close();
				}
         }
	}
}
