package SentiAnalysis;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class SentiMain extends Configured implements Tool{
	
	private Job sentiScore, totScore, json;
	private Configuration conf;
	private static String CATEGORY = "Camera"; 
	private static String SENTI_SCORE = "twitterSenti";
	private static String TOTAL_SCORE = "twitterSenti";
	private static String JSON_TOP = "topJSON/"+CATEGORY;
	private static String AMAZON_CLASS = "AmazonCategory/"+CATEGORY;
	public static SWN3 analyzer= new SWN3();
	
	public static void main(String[] args) throws Exception {

		if (args.length != 2) {

			System.out.printf("Usage:<jarfile>	<inputdir> <outputdir>\n");

			System.exit(-1);

		}

		ToolRunner.run(new Configuration(), new SentiMain(), args);

	}

	public int run(String[] arg0) throws Exception {
		
		computeSentiscore(arg0);
		FileInputFormat.addInputPath(sentiScore, new Path(arg0[0]));
		FileOutputFormat.setOutputPath(sentiScore, new Path(arg0[1]+"/"+SENTI_SCORE));
		sentiScore.waitForCompletion(true);
		assignTotalScore();
		FileInputFormat.addInputPath(totScore, new Path(arg0[1]+"/"+SENTI_SCORE));
		FileInputFormat.addInputPath(totScore, new Path(arg0[0]+"/"+AMAZON_CLASS));
		FileOutputFormat.setOutputPath(totScore, new Path(arg0[1]+"/"+TOTAL_SCORE));
		totScore.waitForCompletion(true);
		FileInputFormat.addInputPath(json, new Path(arg0[1]+"/"+TOTAL_SCORE));
		FileOutputFormat.setOutputPath(json, new Path(arg0[1]+"/"+JSON_TOP));
		return sentiScore.waitForCompletion(true)? 0:1;
	}

	public void computeSentiscore(String[] arg0) throws IOException {
		Configuration conf = new Configuration();
		sentiScore = Job.getInstance(conf);
		sentiScore.setJarByClass(SentiMain.class);
		sentiScore.setMapperClass(SentiMapper.class);
		sentiScore.setReducerClass(SentiReducer.class);
		sentiScore.setOutputKeyClass(Text.class);
		sentiScore.setOutputValueClass(Text.class);
		sentiScore.setInputFormatClass(TextInputFormat.class);
		sentiScore.setOutputFormatClass(TextOutputFormat.class);
	}
	
	public void assignTotalScore() throws IOException{
		conf = new Configuration();
		totScore = Job.getInstance(conf);
		totScore.setJarByClass(SentiMain.class);
		totScore.setMapperClass(TotalScoreMapper.class);
		totScore.setReducerClass(TotalScoreReducer.class);
		totScore.setOutputKeyClass(Text.class);
		totScore.setOutputValueClass(Text.class);
		totScore.setInputFormatClass(TextInputFormat.class);
		totScore.setOutputFormatClass(TextOutputFormat.class);
		
	}
	
	public void constructJSON() throws IOException{
		conf = new Configuration();
		json = Job.getInstance(conf);
		json.setJarByClass(SentiMain.class);
		json.setMapperClass(JSONMapper.class);
		json.setReducerClass(JSONReducer.class);
		json.setOutputKeyClass(Text.class);
		json.setOutputValueClass(Text.class);
		json.setInputFormatClass(TextInputFormat.class);
		json.setOutputFormatClass(TextOutputFormat.class);
		
	}
	
	
}