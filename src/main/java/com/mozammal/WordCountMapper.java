package com.mozammal;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class WordCountMapper extends Mapper<Object, Text, Text, Text> {

  private static HashMap<String, String> ccachedCustomers = new HashMap<>();
  // private BufferedReader brReader;

  enum MYCOUNTER {
    RECORD_COUNT,
    FILE_EXISTS,
    FILE_NOT_FOUND,
    SOME_OTHER_ERROR
  }

  @Override
  protected void setup(Context context) {

    Path[] distibutedCacheFile = new Path[0];
    try {
      distibutedCacheFile = DistributedCache.getLocalCacheFiles(context.getConfiguration());

      for (Path file : distibutedCacheFile) {
        // if (file.getName().startsWith("customer")) {
        loadCustomerIntoCache(file, context);
        // }
      }
    } catch (Exception ex) {
      System.err.println("Exception in mapper setup: " + ex.getMessage());
    }
  }

  private void loadCustomerIntoCache(Path file, Context context) throws FileNotFoundException {

    String strLineRead = "";

    try {
      try (BufferedReader brReader = new BufferedReader(new FileReader(file.toString()))) {
        while ((strLineRead = brReader.readLine()) != null) {
          String customers[] = strLineRead.split("\\|");

          String values = customers[0];

          for (int i = 2; i < customers.length; i++) {
            values += "|";
            values += customers[i];
          }

          ccachedCustomers.put(customers[0], values + "|");
        }
      }
    } catch (IOException ex) {
      System.err.println("Exception in mapper setup: " + ex.getMessage());
    }
  }

  @Override
  protected void map(Object key, Text value, Context context)
      throws IOException, InterruptedException {

    String line = value.toString();
    String[] values = line.split("\\|");

    if (ccachedCustomers.containsKey(values[1])) {
      Text outputKey = new Text();
      outputKey.set(values[1]);
      Text outputValue = new Text();
      String str = values[1];
      for (int i = 0; i < values.length; i++) {
        if (i == 1) {
          continue;
        }
        str = str + "|" + values[i];
      }
      outputValue.set(ccachedCustomers.get(values[1]) + str);
      context.write(outputKey, outputValue);
    }
  }
}
