package com.mozammal;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

  @Override
  protected void reduce(Text key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {
    int frequency = 0;

    for (IntWritable value : values) {
      frequency += value.get();
    }
    IntWritable result = new IntWritable();
    result.set(frequency);
    context.write(key, result);
  }
}
