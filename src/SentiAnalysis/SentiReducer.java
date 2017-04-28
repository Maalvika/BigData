package SentiAnalysis;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class SentiReducer extends Reducer<IntWritable, Text, NullWritable, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		
		Text senti = values.iterator().next();
		context.write(NullWritable.get(), senti);

	}
}