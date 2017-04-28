package SentiAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class SWN3 {

	private String pathToSWN = "/s/chopin/a/grad/mbachani/TermProject"
			+ "/TwitterStreamAnalysis-BigData-master/sentiNet/SentiWordNet_3.0.0_20130122.txt";
	private Map<String, Double> dictionary;

	public SWN3() {

		dictionary = new HashMap<String, Double>();

		// From String to list of doubles.
		HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

		BufferedReader csv = null;
		try {
			csv = new BufferedReader(new FileReader(pathToSWN));

			String line;
			while ((line = csv.readLine()) != null) {

				// If it's a comment, skip this line.
				if (!line.trim().startsWith("#")) {
					// We use tab separation
					String[] data = line.split("\t");
					String wordTypeMarker = data[0];

					if (data.length != 6) {
						throw new IllegalArgumentException(
								"Incorrect tabulation format in file, line");
					}

					
					Double synsetScore = Double.parseDouble(data[2])
							- Double.parseDouble(data[3]);

				
					String[] synTermsSplit = data[4].split(" ");

					
					for (String synTermSplit : synTermsSplit) {
						
						String[] synTermAndRank = synTermSplit.split("#");
						String synTerm = synTermAndRank[0] + "#"
								+ wordTypeMarker;

						int synTermRank = Integer.parseInt(synTermAndRank[1]);
					

						// Add map to term if it doesn't have one
						if (!tempDictionary.containsKey(synTerm)) {
							tempDictionary.put(synTerm,
									new HashMap<Integer, Double>());
						}

						
						tempDictionary.get(synTerm).put(synTermRank,
								synsetScore);
					}
				}
			}

			// Go through all the terms.
			for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary.entrySet()) {
				String word = entry.getKey();
				Map<Integer, Double> synSetScoreMap = entry.getValue();

				// Calculate weighted average. Weigh the synsets according to
				// their rank.
				// Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
				// Sum = 1/1 + 1/2 + 1/3 ...
				double score = 0.0;
				double sum = 0.0;
				for (Map.Entry<Integer, Double> setScore : synSetScoreMap
						.entrySet()) {
					score += setScore.getValue() / (double) setScore.getKey();
					sum += 1.0 / (double) setScore.getKey();
				}
				score /= sum;

				dictionary.put(word, score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public Double extract(String word)
    {
        Double total = new Double(0);
        
        if(dictionary.get(word+"#n") != null)
             total = dictionary.get(word+"#n");
        if(dictionary.get(word+"#a") != null)
            total = dictionary.get(word+"#a");
        if(dictionary.get(word+"#r") != null)
            total = dictionary.get(word+"#r");
        if(dictionary.get(word+"#v") != null)
            total = dictionary.get(word+"#v");
        return total;
    }


	public String classifytweet(String tweet) {
		String[] words = tweet.split("\\s+");
		double totalScore = 0.0d;
		for (String word : words) {
			word = word.replaceAll("([^a-zA-Z\\s])", "");
			if (this.extract(word) == null)
				continue;
			totalScore += this.extract(word);
		}
		double averageScore = totalScore;
		System.out.println("avg score:"+averageScore);
		if (averageScore >= 0.75)
			return Sentiment.VERY_POSITIVE.name();
		else if (averageScore >= 0.25 && averageScore < 0.5)
			return Sentiment.LESS_POSITIVE.name();
		else if (averageScore >= 0.5 && averageScore < 0.75)
			return Sentiment.POSITIVE.name();
		else if (averageScore < 0.25 && averageScore >= -0.25)
			return Sentiment.NEUTRAL.name();
		else if (averageScore <= -0.5 && averageScore >= -0.75)
			return Sentiment.NEGATIVE.name();
		else if (averageScore < -0.25 && averageScore >= -0.5)
			return Sentiment.LESS_NEGATIVE.name();
		else 
			return Sentiment.VERY_NEGATIVE.name();
		
	}
//	public static void main(String args[]) {
//		SWN3 s = new SWN3();
//		String sentiment = s.classifytweet("This shoe is not bad at all.");
//		System.out.println("Sentiment:"+sentiment);
//		
//	}
}