package build.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.fs.Path;
import utils.Stemmer;

public class IndexerMapper extends Mapper<LongWritable, Text, Text, Text> {
	private final static IntWritable one=new IntWritable(1);
	private Text word = new Text();
	private Set stopWords = new HashSet();
	
	
	protected void setup(Context context) throws IOException, InterruptedException {
		try{
			Path[] stopWordsFiles =DistributedCache.getLocalCacheFiles(context.getConfiguration());
			if(stopWordsFiles != null && stopWordsFiles.length > 0) {
				for(Path stopWordFile : stopWordsFiles) {
					readFile(stopWordFile);
				}
			}
			
		}catch(Exception e)
		{
			 System.err.println("Exception in mapper setup: " + e.getMessage());
		}
	}
	
	private void readFile(Path filePath) {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath.toString()));
            String stopWord = null;
            while((stopWord = bufferedReader.readLine()) != null) {
                stopWords.add(stopWord.toLowerCase());
            }
        } catch(IOException ex) {
            System.err.println("Exception while reading stop words file: " + ex.getMessage());
        }
       }
			
	
	
	public void map(LongWritable key,Text value,Context context) throws IOException,InterruptedException
	{
		HashMap<String,HashMap<String,Integer>> local_dict=new HashMap<String,HashMap<String,Integer>>();
		//HashMap<String,Integer> local_title_dict=new HashMap<String,Integer>();
		String[] splitted_tokens=value.toString().split("#####");
		//word.set("ss");
		//context.write(word,new IntWritable(splitted_tokens.length));
		String docid=splitted_tokens[0];
		String title=splitted_tokens[2];
		
		String text=splitted_tokens[3].replaceAll("[^A-Za-z]+", " ");
		text=text.toLowerCase();
		File stop_file=new File("/nltkstop");
		
		
		
		
		String curr_token="";
		int curr_value=0;
		HashMap<String,Integer> curr_map=null;
		StringTokenizer str=new StringTokenizer(title);
		Stemmer stemming = new Stemmer();
		while(str.hasMoreTokens())
		{
			curr_token=str.nextToken();
			stemming.add(curr_token);
			curr_token=stemming.stem();
			stemming.clear();
			if(!stopWords.contains(curr_token.toLowerCase()))
			{
			try{
				curr_value=local_dict.get(curr_token).get("t");
				local_dict.get(curr_token).put("t", (curr_value+1));
				
			}catch(Exception e){
				HashMap<String,Integer> new_map=new HashMap<String,Integer>();
				new_map.put("t",1);
				new_map.put("b",0);
				local_dict.put(curr_token,new_map);
			}
			}
		}		
		
		str=new StringTokenizer(text);
		while(str.hasMoreTokens())
		{
			curr_token=str.nextToken();
			stemming.add(curr_token);
			curr_token=stemming.stem();
			stemming.clear();
			if(!stopWords.contains(curr_token.toLowerCase()) && curr_token.length()>=2)
			{
			try{
				
				curr_value=local_dict.get(curr_token).get("b");
				local_dict.get(curr_token).put("b", (curr_value+1));
				
			}catch(Exception e){
				HashMap<String,Integer> new_map=new HashMap<String,Integer>();
				new_map.put("t",0);
				new_map.put("b",1);
				local_dict.put(curr_token,new_map);
			}
			}
		}		

		
		int title_count=0;
		int title_in_body=0;
		String writable_value="";
		String index_entry="";
		for (String curr_key : local_dict.keySet()){
			 index_entry="d"+docid;
			Integer titlecount=local_dict.get(curr_key).get("t");
			Integer bodyCount=local_dict.get(curr_key).get("b");
			if(titlecount!=0){
				index_entry=index_entry+"t"+titlecount;
			}
			if(bodyCount!=0){
				index_entry=index_entry+"b"+bodyCount;
			}				
		  
		 context.write(new Text(curr_key), new Text(index_entry));
		 index_entry="";
		 }
		
        	
        	           
 
	}

}
