package build.index;

import java.util.HashMap;
import java.util.Map;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String newstr = "Word#$#$% Word 1234".replaceAll("[^A-Za-z]+", "");
		newstr="redirect [[computer accessibility]] {{r from camelcase 2006}}".replaceAll("[^A-Za-z]+", " ");;
		System.out.println(newstr);
		*/
		  Map m1 = new HashMap(); 
		  m1.put("Zara", "8");
		  m1.put("Mahnaz", "31");
		  m1.put("Ayan", "12");
		  m1.put("Daisy", "14");
		  System.out.println();
		  System.out.println(" Map Elements");
		  System.out.print("\t" + m1);
		  
		  System.out.println(m1.get("Zara"));
		  System.out.println((int)m1.get("bssds")+1);
		  
		  
		  
	}

}
