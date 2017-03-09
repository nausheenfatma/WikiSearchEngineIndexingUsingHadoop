package build.index;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import utils.Utils;



public class IndexerJob {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
		Configuration conf=new Configuration();
		//Utils.addJarToDistributedCache(IndexerMapper.class, conf);
		
		if(args.length!=3)
		{
			System.err.println("Index Job needs 3 args");
			System.exit(2);
		}
		Job job=new Job(conf,"Word Count Job");
		int numReducers = 1 /*number of reducers you want*/;
		job.setNumReduceTasks(numReducers);
		job.setJarByClass(IndexerJob.class);
		job.setMapperClass(IndexerMapper.class);
		job.setCombinerClass(IndexerReducer.class);
		job.setReducerClass(IndexerReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job,new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		DistributedCache.addCacheFile(new Path(args[2]).toUri(), job.getConfiguration());
		System.exit(job.waitForCompletion(true)?0:1);

	}

}
