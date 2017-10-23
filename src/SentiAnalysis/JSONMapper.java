package SentiAnalysis;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JSONMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		
		JsonObject productData = new JsonObject();
		String []values = value.toString().split("\t");
		productData.addProperty("name", values[0]);
		productData.addProperty("features", values[1]);
		productData.addProperty("price", values[2]);
		productData.addProperty("avg_amazon_rating", values[3]);
		productData.addProperty("avg_sentiment_twitter", values[4]);
		productData.addProperty("avg_total_sentiment", values[5]);
		
		context.write(new Text("ONE"), new Text(productData.toString()));

	}

}
