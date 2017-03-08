package com.mii.prg;

import java.util.ArrayList;
import java.util.List;

public class Trial {
 
/**
* @param args the command line arguments
*/
		void printArray(String []a) {
			for (int i = 0; i < a.length; i++) {
				System.out.print(a[i]+",");
			}
				System.out.println("");
		}
		static void generatePermutations(List<String[]> Lists, List<String> result, int depth, String current)
		{
		    if(depth == Lists.size())
		    {
		       result.add(current);
		       return;
		     }

		    for(int i = 0; i < Lists.get(depth).length; ++i)
		    {
		        generatePermutations(Lists, result, depth + 1, current + Lists.get(depth)[i]);
		    }
		}
		
		public static void main(String[] args) 
		{
			Trial t=new Trial();
			String firstLevel [] = {"SAS Selection","SAC Selection","Spot Beam Selection","Satellite Selection","Global"};
			String secondLevel [] = {"G1","DS","DPaaS Tenant"};
			List<String[]> list = new ArrayList<String[]>();
			List<String> result=new ArrayList<String>();
			generatePermutations(list, result, 0, "");
			for(int i=0;i<result.size();i++)
			{
				System.out.println(result.get(i));
			}
		}
 
}