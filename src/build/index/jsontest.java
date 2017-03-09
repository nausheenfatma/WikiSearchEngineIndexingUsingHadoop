package build.index;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class jsontest {
    public static void main(String[] args){
    	readJsonLine("hell");
    }
	public static void readJsonLine(String text){
		 JSONParser parser = new JSONParser();
		 
	        try {
	        	//String test="{'id':10,'text':[u'#redirect [[computer accessibility]] {{r from camelcase}}\n'],'title':[u'AccessibleComputing']}";
	        	//System.out.println(test.replace("u'", "'"));
	            //Object obj = parser.parse("{\"id\":10,\"text\":[\"#redirect [[computer accessibility]] {{r from camelcase}}\"],\"title\":[\"AccessibleComputing\"]}");
	            Object obj = parser.parse("{\"id\":10,\"text\":[\"#redirect [[computer accessibility]] {{r from camelcase}}\"],\"title\":[\"AccessibleComputing\"]}");
	            JSONObject jsonObject = (JSONObject) obj;
	 
	            Long name = (Long) jsonObject.get("id");
	            ArrayList<JSONArray> author = (ArrayList<JSONArray>) jsonObject.get("text");
	            JSONArray companyList = (JSONArray) jsonObject.get("title");
	 
	            System.out.println("Name: " + name);
	            //System.out.println("Author: " + author);
	            Iterator<JSONArray> iterator1 = author.iterator();
	            while (iterator1.hasNext()) {
	                System.out.println(iterator1.next());
	            }	            
	            System.out.println("\nCompany List:");
	            Iterator<String> iterator = companyList.iterator();
	            while (iterator.hasNext()) {
	                System.out.println(iterator.next());
	            }
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		
		
	}
}
