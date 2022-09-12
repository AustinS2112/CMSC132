package onlineTest;

import java.util.Arrays;

public class multipleChoice extends Question{
	private int questionNum;
	private String text;
	private String[] answer;
	private double points;
	
	public multipleChoice(String type, int questionNum, String text, String[] answer, double points) {
		super(questionNum, "multipleChoice", points, text);

		this.text = text;
		this.points = points;
		this.questionNum = questionNum;
		this.answer = answer;
	}//Constructor
	
	public String[] getAnswer() {
		Arrays.sort(answer);
		
		return this.answer;
		
	}//getAnswer(s)
	
	public double getPoints() {
		return this.points;
	}
}
