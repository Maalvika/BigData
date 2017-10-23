package SentiAnalysis;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TotalScoreReducer extends Reducer<IntWritable, Text, NullWritable, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		
		Double twitter_score = null;
		double amazon_score = 0.0d;
		String productData = null;
		
		for(Text value: values) {
			String[] data = value.toString().split("\t");
			if(data.length == 1) {
				twitter_score = Double.parseDouble(data[0]);
			} else if(data.length == 4){
				productData = value.toString();
				if(data[4]!= "NaN") {
					amazon_score = Double.parseDouble(data[4]);
				} 
			}
		}
		
		if(twitter_score !=null && productData!=null){
			context.write(NullWritable.get(), new Text(productData+"\t"+twitter_score+"\t"+(amazon_score*twitter_score)));
		}

	}

}
