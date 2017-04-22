package AmazonCategories;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class ProductInfoMapper extends Mapper<LongWritable, Text, Text, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		Gson gson = new Gson();
		JsonObject productJson = gson.fromJson(value.toString(), JsonObject.class).getAsJsonObject();
		//System.out.println("JSON:"+productJson);
		JsonObject product = productJson.get("ProductInfo").getAsJsonObject();
		//System.out.println("prod:"+product);
		String productName = product.get("Name").toString();
		//System.out.println("pName:"+productName);
		JsonElement reviewElement = productJson.get("Reviews");
		
		double total_rating = 0.0;
		int rating_count =0;
		if(reviewElement!=null) {
			JsonArray reviewArray = reviewElement.getAsJsonArray();
			for(JsonElement je: reviewArray) {
				JsonObject jo= je.getAsJsonObject();
				String rating  = jo.get("Overall").getAsString();
				double temp = (!rating.equalsIgnoreCase("None"))? Double.parseDouble(rating): 0.0d;
				total_rating += temp;
				rating_count++;
			}
		}
		context.write(new Text(productName), new Text(total_rating/(double)rating_count+""));

	}

}