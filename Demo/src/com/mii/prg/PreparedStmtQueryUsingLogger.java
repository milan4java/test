package com.mii.prg;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class PreparedStmtQueryUsingLogger {

		private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
		private static final String DB_CONNECTION = "jdbc:mysql://192.168.102.13:3306/hhmefep?useServerPrepStmts=true&prepStmtCacheSize=250&cachePrepStmts=true&prepStmtCacheSize=250&prepStmtCacheSqlLimit=1024&cacheServerConfiguration=true";
		private static final String DB_USER = "root";
		private static final String DB_PASSWORD = "admin";
		 private static final Logger LOGGER = Logger.getLogger(PreparedStmtQuery.class.getName());

		public static void main(String[] argv) throws SecurityException, IOException, ClassNotFoundException, SQLException {

			FileHandler  fh = new FileHandler("E:/MyLogFile.log");  	
			LOGGER.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	        LOGGER.setLevel(Level.FINEST);
	        
			try 
			{	LOGGER.info("--START--");
//					for(int i=0;i<100;i++)
//					{
//						LOGGER.info("Record No. "+i);
						crudUsingMySQL();
//						updateRecordToTableUsingHikari();
//						updateRecordToTableUsingC3p0();
//					}
				LOGGER.info("--END--");		
			}
			finally
			{
//				fh.close();
			}

		}

		public static void crudUsingMySQL() throws SQLException, ClassNotFoundException, IOException
		{
			String updateTableSQL = "UPDATE bgan_ip_addresses set usage_counter = usage_counter + 1 where ip_address = ?";
			
			String insertTableSQL = "insert into performancemeasure (name,address) values(?,?)";
			String deleteTableSQL = "delete from performancemeasure where id=?";
			String selectTableSQL = "select * from bgan_ip_addresses where ip_address=?";


			Connection dbConnectionForMysql = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);;
			PreparedStatement preparedStatementForMysqlUpdate =  dbConnectionForMysql.prepareStatement(updateTableSQL);
			PreparedStatement preparedStatementForMysqlDelete =  dbConnectionForMysql.prepareStatement(deleteTableSQL);
			PreparedStatement preparedStatementForMysqlCreate =  dbConnectionForMysql.prepareStatement(insertTableSQL);
			PreparedStatement preparedStatementForMysqlSelect =  dbConnectionForMysql.prepareStatement(selectTableSQL);
			Class.forName(DB_DRIVER);
			dbConnectionForMysql.setAutoCommit(false);
			
			updateRecordToTable(dbConnectionForMysql,preparedStatementForMysqlUpdate);
			createRecordToTable(dbConnectionForMysql,preparedStatementForMysqlCreate);
			deleteRecordToTable(dbConnectionForMysql,preparedStatementForMysqlDelete);
			viewRecordToTable(dbConnectionForMysql,preparedStatementForMysqlSelect);
			
		}
		private static void updateRecordToTable(Connection dbConnectionForMysql, PreparedStatement preparedStatementForMysql) throws SQLException, ClassNotFoundException, IOException {
			try {
					
					long startTime = System.currentTimeMillis();
					for(int i =0;i<100;i++){
						
						preparedStatementForMysql.setString(1, "81.1.8.137");
						preparedStatementForMysql.addBatch();
						preparedStatementForMysql.executeBatch();
					}
					dbConnectionForMysql.commit();			        
			        LOGGER.info("Mysql Update="+(System.currentTimeMillis()-startTime));
			} catch (SQLException e) { 

				System.out.println(e.getMessage());

			} finally {

				if (preparedStatementForMysql != null) {
					preparedStatementForMysql.close();
				}

				if (dbConnectionForMysql != null) {
//					dbConnectionForMysql.close();
				}

			}

		}
		
		private static void createRecordToTable(Connection dbConnectionForMysql, PreparedStatement preparedStatementForMysql) throws SQLException, ClassNotFoundException, IOException {
							try {
								
								long startTime = System.currentTimeMillis();
								for(int i =0;i<100;i++){
									preparedStatementForMysql.setString(1, "abc");
									preparedStatementForMysql.setString(2,"defghijkl");
									
									preparedStatementForMysql.addBatch();
									preparedStatementForMysql.executeBatch();
								}
								dbConnectionForMysql.commit();			        
						        LOGGER.info("Mysql Create="+(System.currentTimeMillis()-startTime));
						} catch (SQLException e) { 
				
							System.out.println(e.getMessage());
				
						} finally {
				
							if (preparedStatementForMysql != null) {
								preparedStatementForMysql.close();
							}
				
							if (dbConnectionForMysql != null) {
//								dbConnectionForMysql.close();
							}
				
						}
		}
		
		private static void deleteRecordToTable(Connection dbConnectionForMysql, PreparedStatement preparedStatementForMysql) throws SQLException, ClassNotFoundException, IOException {
						try {
							
							long startTime = System.currentTimeMillis();
							for(int i =0;i<100;i++){
								
								preparedStatementForMysql.setString(1, "81.1.8.137");
								preparedStatementForMysql.addBatch();
								preparedStatementForMysql.executeBatch();
							}
							dbConnectionForMysql.commit();			        
					        LOGGER.info("Mysql Delete="+(System.currentTimeMillis()-startTime));
					} catch (SQLException e) { 
					
						System.out.println(e.getMessage());
					
					} finally {
					
						if (preparedStatementForMysql != null) {
							preparedStatementForMysql.close();
						}
					
						if (dbConnectionForMysql != null) {
//							dbConnectionForMysql.close();
						}
					
					}
		}
		
		private static void viewRecordToTable(Connection dbConnectionForMysql, PreparedStatement preparedStatementForMysql) throws SQLException, ClassNotFoundException, IOException {
						try {
							
							long startTime = System.currentTimeMillis();
							for(int i =0;i<100;i++){
								
								preparedStatementForMysql.setString(1, "81.1.8.137");
								preparedStatementForMysql.addBatch();
								preparedStatementForMysql.executeBatch();
							}
							dbConnectionForMysql.commit();			        
					        LOGGER.info("Mysql view="+(System.currentTimeMillis()-startTime));
					} catch (SQLException e) { 
					
						System.out.println(e.getMessage());
					
					} finally {
					
						if (preparedStatementForMysql != null) {
							preparedStatementForMysql.close();
						}
					
						if (dbConnectionForMysql != null) {
//							dbConnectionForMysql.close();
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
				
				preparedStatementForHikari.executeBatch();			
				dbConnectionForHikari.commit();
				LOGGER.info("hikari="+(System.currentTimeMillis()-startTime));
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
				LOGGER.info("c3p0="+(System.currentTimeMillis()-startTime));
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
