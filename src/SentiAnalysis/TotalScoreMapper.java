package SentiAnalysis;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TotalScoreMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] data = value.toString().split("\t");
		if(data.length == 2)
			context.write(new Text(data[1].replace(" ", "")), new Text(data[0]));
		else if(data.length == 4) {
			String temp_name = data[0];
			temp_name.replace("camera", "NA");
			String [] temp_split = temp_name.split(" ");
			context.write(new Text(temp_split[0]+temp_split[1]+temp_split[2]+temp_split[3]+temp_split[4]), value);
		}
	
	}

}
