package simpleCounts;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LineCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable one=new IntWritable(1);
	private Text word = new Text();
	public void map(LongWritable key,Text value,Context context) throws IOException,InterruptedException
	{
		word.set("ss_1");
	  	context.write(word, one);  
			//<quick 1><fox 1,1><on 1><rat 1,1><where 1><ate 1>
		
	}
	
}
