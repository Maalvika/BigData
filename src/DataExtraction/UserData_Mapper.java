package DataExtraction;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class UserData_Mapper extends Mapper<LongWritable, Text, IntWritable, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		Gson gson = new Gson();
		JsonObject twitterJSON = gson.fromJson(value.toString(), JsonObject.class).getAsJsonObject();
		if (twitterJSON.has("created_at")) {
			String userJSON = twitterJSON.get("user").toString();
			//System.out.println("user:"+userJSON);
			TwitterUser user = gson.fromJson(userJSON, TwitterUser.class);
			//System.out.println("user:"+user.toString());
			if ( user.getLanguage().equalsIgnoreCase("en")) {
				context.write(new IntWritable(user.getId()), new Text(user.toString()));

			}
		}

	}

}
