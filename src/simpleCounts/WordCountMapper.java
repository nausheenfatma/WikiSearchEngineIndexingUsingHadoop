package simpleCounts;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>
{
private final static IntWritable one=new IntWritable(1);
private Text word = new Text();
public void map(LongWritable key,Text value,Context context) throws IOException,InterruptedException
{
  StringTokenizer str =new StringTokenizer(value.toString()); 
  //to tokenize input line into array of strring tokens
  // 'quick fox on rat where fox ate rat'  --->   ['quick','fox','on','rat','where','fox','ate','rat']
	while(str.hasMoreTokens())
	{
		word.set(str.nextToken());
		context.write(word, one);  
		//<quick 1><fox 1,1><on 1><rat 1,1><where 1><ate 1>
	}
}
	
}

