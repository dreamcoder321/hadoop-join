package com.mozammal;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MapSideJoin extends Mapper<Object, Text, NullWritable, Text> {

  private static HashMap<String, String> cachedCustomers = new HashMap<>();

  @Override
  protected void setup(Context context) {

    Path[] distibutedCacheFile;
    try {
      distibutedCacheFile = DistributedCache.getLocalCacheFiles(context.getConfiguration());

      for (Path file : distibutedCacheFile) {
        if (file.getName().startsWith("customer")) {
          loadCustomerIntoCache(file, context);
        }
      }
    } catch (Exception ex) {
      System.err.println("Exception in mapper setup: " + ex.getMessage());
    }
  }

  private void loadCustomerIntoCache(Path file, Context context) throws FileNotFoundException {

    String strLine = "";

    try {
      try (BufferedReader brReader = new BufferedReader(new FileReader(file.toString()))) {
        while ((strLine = brReader.readLine()) != null) {
          String customers[] = strLine.split("\\|");
          String values = customers[0];
          cachedCustomers.put(customers[0], strLine + "|");
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

    if (cachedCustomers.containsKey(values[1])) {
      Text mappedKey = new Text();
      mappedKey.set(values[1]);
      Text outputValue = new Text();
      outputValue.set(cachedCustomers.get(values[1]) + line);
      context.write(NullWritable.get(), outputValue);
    }
  }
}
