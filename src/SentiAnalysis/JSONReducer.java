package SentiAnalysis;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JSONReducer extends Reducer<IntWritable, Text, NullWritable, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		Gson gson = new Gson();
		Map<Double, String> sortedProducts = new TreeMap<>(Collections.reverseOrder());
		for(Text value: values) {
			JsonObject temp = gson.fromJson(value.toString(), JsonObject.class);
			sortedProducts.put(Double.parseDouble(temp.get("avg_total_sentiment").toString()), value.toString());
		}
		int count = 1;
		for(String json: sortedProducts.values()) {
			if(count>20) {
				break;
			}
			context.write(NullWritable.get(), new Text(json));
			count++;
		}

	}
	
}
