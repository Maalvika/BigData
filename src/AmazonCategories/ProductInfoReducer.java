package AmazonCategories;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ProductInfoReducer extends Reducer<Text, Text, NullWritable, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		
		Text avg_rating = values.iterator().next();
		context.write(NullWritable.get(), new Text(key+"\t"+avg_rating));

	}
}