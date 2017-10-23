package SentiAnalysis;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class SentiReducer extends Reducer<IntWritable, Text, NullWritable, Text> {

//	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
//		
//		Text senti = values.iterator().next();
//		context.write(NullWritable.get(), senti);
//
//	}
	
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		double count = 0.0d;
		double total = 0.0d;
		for(Text value: values) {
			String tweet = value.toString().replaceAll("[^A-Za-z0-9\\s+]", "");
			String sentiment = SentiMain.analyzer.classifytweet(tweet);
			count ++;
			total+= Sentiment.valueOf(sentiment).getScore();
		}
		
		double avg = total/count;
		
		 context.write(NullWritable.get(), new Text(key + "\t" + avg));
	}
}