package build.index;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class IndexerReducer extends Reducer<Text, Text,Text, Text>{
	public void reduce(Text key,Iterable<Text> values,Context context)
	throws IOException,InterruptedException
	{
	String concatStr="";
	
	for(Text value:values)
	{
		concatStr+=value+"|";
		//sum+=value.get();
	}
	//result.set(sum);
	context.write(new Text(key),new Text(concatStr));
	}
}
