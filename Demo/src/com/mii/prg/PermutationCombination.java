package com.mii.prg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PermutationCombination {
	List<String> result = new ArrayList<String>();
	public static void main(String[] args) throws IOException {
		BufferedWriter resultFileBufWrtr=null;
		FileWriter resultDesc=null;
		try
		{
			String firstLevel [] = {"SAS Selection","SAC Selection","Spot Beam Selection","Satellite Selection","Global"};
			String secondLevel [] = {"G1","DS","DPaaS Tenant"};
			String thirdLevel [] = {"London","Houston","Sydney"};
			String fourthLevel [] = {"Mobile To Fixed","Fixed To Mobile","Mobile To Mobile"};
			String fifthLevel [] = {"Static","Dynamic"};
			String sixthLevel [] = {"Internet","Enterprise","Hybrid"}; 
			String seventhLevel [] = {"Filtering","Optiimization","Shaping"};
			String eighthLevel [] = {"IP Traffic Statistic","URL Statistics","Application Status","Packet Filter Statistics"};
			String ninthLevel [] = {"Regional Disaster Recovery","No Disaster Recovery"};
			
			List<String[]> list = new ArrayList<String[]>();
			list.add(firstLevel);
			list.add(secondLevel);
			list.add(thirdLevel);
			list.add(fourthLevel);
			list.add(fifthLevel);
			list.add(sixthLevel);
			list.add(seventhLevel);
			list.add(eighthLevel);
			list.add(ninthLevel);
			Set<String> value=generatePermutations(list,list.size()-1);
			Iterator it = value.iterator();
			String filename ="E:\\java created files\\outputusingnio.csv";
			resultDesc= new FileWriter(filename);
			resultFileBufWrtr = new BufferedWriter(resultDesc);
			while(it.hasNext())
			{
	            resultFileBufWrtr.write(it.next().toString());;
	            resultFileBufWrtr.newLine();
			}
			resultFileBufWrtr.write("--Successfully Completed Writing--");
			resultFileBufWrtr.flush();

		}
		catch(Exception e)
		{
			
		}
		finally
		{
            if(resultDesc!=null)
            {
            	resultDesc.close();
            }
		}
		
	}
	
	
	static Set generatePermutations(List<String[]> list,int depth)
	{
		Set<String> set= new LinkedHashSet<String>();
		System.out.println("Printed");

			for(int i=0;i<list.get(0).length;i++)
			{
				for(int j=0;j<list.get(1).length;j++)
				{
					for(int k=0;k<list.get(2).length;k++)
					{
						for(int l=0;l<list.get(3).length;l++)
						{
							for(int m=0;m<list.get(4).length;m++)
							{
								for(int n=0;n<list.get(5).length;n++)
								{
									for(int o=0;o<list.get(6).length;o++)
									{
										for(int p=0;p<list.get(7).length;p++)
										{
											for(int q=0;q<list.get(8).length;q++)
											{
												String value = list.get(0)[i]+"->"+list.get(1)[j]+"->"+list.get(2)[k]+"->"+list.get(3)[l]+"->"+list.get(4)[m]+"->"+list.get(5)[n]+"->"+list.get(6)[o]+"->"+list.get(7)[p]+"->"+list.get(8)[q];
												set.add(value);
											}
										}
									}
								}
							}
						}
					}	
				}
			}
			

		return set;
	}
}
