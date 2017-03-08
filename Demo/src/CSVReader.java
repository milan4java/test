

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CSVReader {

	  public static Object getKeyFromValue(Map<String, String> fortigateMap, String value) 
	  {
	    for (Object o : fortigateMap.keySet()) {
	      if (fortigateMap.get(o).equalsIgnoreCase(value)) {
	        return o;
	      }
	    }
	    return null;
	  }
    public static void main(String[] args) throws FileNotFoundException {
	try
	{
        String csvApplicationFile = "E:/fortigate parser/production_database_configuration.csv";
        String csvFortigateFile = "E:/fortigate parser/fortigate_application_configuration.csv";
        String csvOtherParamFile = "E:/fortigate parser/other_parameters.csv";
        String filename ="E:/fortigate parser/result.csv";
        FileChannel fw = new FileOutputStream(filename).getChannel();
        ByteBuffer bytebuffer = ByteBuffer.allocate(100*1024);
        
        bytebuffer.put("ID".getBytes());
        bytebuffer.put((",").getBytes());
        
        bytebuffer.put("ApplicationName".getBytes());
        bytebuffer.put((",").getBytes());
        
        bytebuffer.put("FortigateName".getBytes());
        bytebuffer.put((",").getBytes());
        bytebuffer.put(("\n").getBytes());
        
        bytebuffer.flip();
        fw.write(bytebuffer);
	    bytebuffer.clear();
        
        
        BufferedReader applicationBr = null,fortigateBr=null,otherParametersBr=null;
        String line = "";
        String cvsSplitBy = "\\s+";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.102.13:3306/hhmefep", "root", "admin");
        
        Map<String, String> categoriesMap = new LinkedHashMap<String,String>();
        String query = "select * from fg_app_category order by 1 asc";    
		Statement stmt = conn.createStatement();            		
		stmt.setFetchSize(100);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
        	categoriesMap.put(String.valueOf(rs.getInt(1)),rs.getString(2));
        }
        
        Map<String,String> vendorMap = new LinkedHashMap<String,String>();
        String vendorQuery = "select * from fg_vendor order by 1 asc";    
		Statement vendorStmt = conn.createStatement();            		
		vendorStmt.setFetchSize(100);
        ResultSet vendorRs = vendorStmt.executeQuery(vendorQuery);
        while (vendorRs.next()) {
        	vendorMap.put(String.valueOf(vendorRs.getInt(1)),vendorRs.getString(2));
        }
        
/*        Map<Integer,String> techMap = new HashMap<Integer,String>();
        String techQuery = "select * from fg_technology order by 1 asc";    
		Statement techStmt = conn.createStatement();            		
		techStmt.setFetchSize(100);
        ResultSet techRs = techStmt.executeQuery(techQuery);
        while (techRs.next()) {
        	techMap.put(techRs.getInt(1),techRs.getString(2));
        }

      Iterator<Entry<Integer, String>> entries = techMap.entrySet().iterator();
      while (entries.hasNext()) {
          Entry<Integer, String> entry = entries.next();
          System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
      }*/
        
        FileWriter resultDesc =null;
        BufferedWriter resultFileBufWrtr=null;
       try {
	        	Map<String,String> applicationMap = new LinkedHashMap<String,String>();
//	        	applicationBr = new BufferedReader(new FileReader(csvApplicationFile));
	        	applicationBr = new BufferedReader(new InputStreamReader(new FileInputStream(csvApplicationFile), "UTF-8"));
	            while ((line = applicationBr.readLine()) != null) {
	                // use comma as separator
	                String[] values = line.split(",");
	                if(values.length>=2)
	                {
	                	values[0] = values[0].replaceFirst("^\uFEFF", "");
	                	applicationMap.put(values[0].trim(), values[1].trim());
	                }
	            }
	            
	        	Map<String,String> fortigateMap = new LinkedHashMap<String,String>();
	        	fortigateBr = new BufferedReader(new InputStreamReader(new FileInputStream(csvFortigateFile), "UTF-8"));
	            while ((line = fortigateBr.readLine()) != null) {
	                // use comma as separator
	                String[] values = line.split(",");
	                if(values.length>=2) 
	                {
	                	values[0] = values[0].replaceFirst("^\uFEFF", "");
	                	fortigateMap.put(values[0].trim(), values[1].trim());
	                }
	            }
	            
	        	Map<String,FWApplication> OtherParameterMap = new LinkedHashMap<String,FWApplication>();
	        	otherParametersBr = new BufferedReader(new FileReader(csvOtherParamFile));
	            while ((line = otherParametersBr.readLine()) != null) {
	                // use comma as separator
	                String[] values = line.split(cvsSplitBy);
	                if(values.length>=3) 
	                {
	                	String id = (String)getKeyFromValue(fortigateMap,values[0]);
//	                	fortigateMap.put(values[0], new FWApplication(id,values[0],values[0], values[1],null, values[2],1,3,0));
	                	Integer categories = Integer.parseInt(getKeyFromValue(categoriesMap,values[1]).toString());
	                	Integer vendors = Integer.parseInt(getKeyFromValue(vendorMap,values[2]).toString());
	                	OtherParameterMap.put(id,new FWApplication(id, values[0],values[0], categories/*category*/,null,vendors/*vendor*/, 1,3,0));

	                }
	            }
	            
	            List<String> updateScript=new ArrayList<String>();
	            List<String> deleteScript=new ArrayList<String>();
	            List<String> insertScript=new ArrayList<String>();
	            
	            Iterator<Entry<String, String>> entries = applicationMap.entrySet().iterator();
	            while (entries.hasNext()) {
	                Entry<String, String> entry = entries.next();
	                if (fortigateMap.containsKey(entry.getKey())) {
	                    if(!(fortigateMap.get(entry.getKey()).equals(entry.getValue())))
	                    {
	                    	updateScript.add("update fg_application set name = '"+fortigateMap.get(entry.getKey())+"',alias='"+fortigateMap.get(entry.getKey())+"' where id="+entry.getKey()+";");
		                    bytebuffer.put(entry.getKey().getBytes());
		                    bytebuffer.put((",").getBytes());
		                    bytebuffer.put(entry.getValue().getBytes());
		                    bytebuffer.put((",").getBytes());
		                    bytebuffer.put(fortigateMap.get(entry.getKey()).getBytes());
		                    bytebuffer.put(("\n").getBytes());
	                    }
	                    	

	                }
	                else
	                {
	                	deleteScript.add("delete from fg_application where id="+entry.getKey()+";");
	                	bytebuffer.put(entry.getKey().getBytes());
	                    bytebuffer.put((",").getBytes());
	                    bytebuffer.put(entry.getValue().getBytes());
	                    bytebuffer.put((",").getBytes());
	                    bytebuffer.put(("N.A").getBytes());
	                    bytebuffer.put(("\n").getBytes());	                	
	                }
	                
	            }
	            
	            bytebuffer.flip();
                fw.write(bytebuffer);
 		       	bytebuffer.clear();
 		       	
	            Iterator<Entry<String, String>> entriesForFortigate = fortigateMap.entrySet().iterator();
	            while (entriesForFortigate.hasNext()) {
	                Entry<String, String> entry = entriesForFortigate.next();
	                if (!applicationMap.containsKey(entry.getKey())) {
	                	
	                	FWApplication fwApp = OtherParameterMap.get(entry.getKey());
	                	if(fwApp!=null)
	                	{
	                		insertScript.add("insert into fg_application (id,name,alias,category,fortinet_web_link,vendor,popularity,risk,shaping) values ("+fwApp.getId()+",'"+fwApp.getName()+"','"+fwApp.getAlias()+"',"+fwApp.getCategory()+","+fwApp.getFortinetWebLink()+","+fwApp.getVendor()+","+fwApp.getPopularity()+","+fwApp.getRisk()+","+fwApp.getShaping()+");");	
	                	}
	                	
	                    bytebuffer.put(entry.getKey().getBytes());
	                    bytebuffer.put((",").getBytes());
	                    bytebuffer.put(("N.A").getBytes());
	                    bytebuffer.put((",").getBytes());
	                    bytebuffer.put(entry.getValue().getBytes());
	                    bytebuffer.put(("\n").getBytes());
	                }
	            }
            
	            bytebuffer.flip();
                fw.write(bytebuffer);
 		       	bytebuffer.clear();
 		       	
 		       	
                String descptnFile  = "E:\\fortigate parser\\result.sql";
                resultDesc = new FileWriter(descptnFile);
                resultFileBufWrtr = new BufferedWriter(resultDesc);
                resultFileBufWrtr.write("use hhmefep; \n");
                for(int i=0;i<deleteScript.size();i++)
		       	{
 		       		resultFileBufWrtr.write(deleteScript.get(i)+"\n");
		       	}

                for(int i=0;i<insertScript.size();i++)
 		       	{
 		       		resultFileBufWrtr.write(insertScript.get(i)+"\n");
 		       		
 		       	}

                for(int i=0;i<updateScript.size();i++)
		       	{
 		       		resultFileBufWrtr.write(updateScript.get(i)+"\n");
		       	}
 		       	
 		       		resultFileBufWrtr.flush();
 		       		resultFileBufWrtr.close();
 		       	System.out.println("Finished writing the script to file");
 		       	
//            Iterator<Entry<String, String>> entries = applicationMap.entrySet().iterator();
//            while (entries.hasNext()) {
//                Entry<String, String> entry = entries.next();
//                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (applicationBr != null) {
                try {
                	applicationBr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fortigateBr != null) {
                try {
                	fortigateBr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(resultDesc!=null)
            {
            	resultDesc.close();
            }
        }
	}
	catch(Exception e)
	{
			e.printStackTrace();
	}
   }

}