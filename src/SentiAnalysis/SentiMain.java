package SentiAnalysis;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import AmazonCategories.ProductInfoMapper;
import AmazonCategories.ProductInfoReducer;

public class SentiMain {
	 
	public static SWN3 analyzer= new SWN3();
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		if (args.length != 2) {

			System.out.printf("Usage:<jarfile>	<inputdir> <outputdir>\n");

			System.exit(-1);

		}

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(SentiMain.class);
		job.setMapperClass(SentiMapper.class);
		job.setReducerClass(SentiReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

}
