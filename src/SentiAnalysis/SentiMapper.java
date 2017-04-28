package SentiAnalysis;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SentiMapper extends Mapper<LongWritable, Text, Text, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] data = value.toString().split("\t");
		if (data.length >= 3) {
			String tweet = data[2];
			//System.out.println("5th element:" + tweet);
			//tweet = tweet.replaceAll("[^A-Za-z0-9\\s+]", "");
			String sentiment = SentiMain.analyzer.classifytweet(tweet);
			System.out.println("SENTI:" + sentiment);
			context.write(new Text("ONE"), new Text(value + "\t Sentiment:" + sentiment));
		}

	}

}
