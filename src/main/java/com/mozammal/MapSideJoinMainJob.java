package com.mozammal;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class MapSideJoinMainJob {

  public static void main(String... args)
      throws IOException, ClassNotFoundException, InterruptedException {

    Configuration conf = new Configuration();

    final Job job = Job.getInstance(conf, "wordCountJob");

    final Path outputPath = new Path(args[2]);
    FileInputFormat.addInputPath(job, new Path(args[1]));
    FileOutputFormat.setOutputPath(job, outputPath);
    outputPath.getFileSystem(conf).delete(outputPath, true);
    DistributedCache.addCacheFile(new Path(args[0]).toUri(), job.getConfiguration());
    job.setJarByClass(MapSideJoinMainJob.class);
    job.setMapperClass(MapSideJoin.class);
    job.setOutputValueClass(Text.class);
    job.setNumReduceTasks(0);
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
