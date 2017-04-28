package SentiAnalysis;

public enum Sentiment {
	
	VERY_POSITIVE(0.875),POSITIVE(0.625),LESS_POSITIVE(0.325), NEUTRAL(0), LESS_NEGATIVE(-0.325), 
	NEGATIVE(-0.625), VERY_NEGATIVE(-0.875);
	
	private double score;
	
	Sentiment(double score){
		this.score = score;
	}
	
	public double getScore() {
		return score;
	}

}
