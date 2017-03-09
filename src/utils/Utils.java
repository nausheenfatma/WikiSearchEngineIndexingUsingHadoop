package utils;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Utils {
	public static void addJarToDistributedCache(
	        Class classToAdd, Configuration conf)
	    throws IOException {
	 
	    // Retrieve jar file for class2Add
	    String jar = classToAdd.getProtectionDomain().
	            getCodeSource().getLocation().
	            getPath();
	    File jarFile = new File(jar);
	 
	    // Declare new HDFS location
	    Path hdfsJar = new Path("/home/cluster/workspace/WikiSearchEngineUsingHadoop/json-simple-1.1.jar");
	 
	    // Mount HDFS
	    FileSystem hdfs = FileSystem.get(conf);
	 
	    // Copy (override) jar file to HDFS
	    hdfs.copyFromLocalFile(false, true,
	        new Path(jar), hdfsJar);
	 
	    // Add jar to distributed classPath
	    DistributedCache.addFileToClassPath(hdfsJar, conf);
	}
}
