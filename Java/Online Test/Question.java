package onlineTest;

import java.io.Serializable;
import java.util.Arrays;

public class Question implements Serializable{
	private static final long serialVersionUID = 4L;
	private int questionNum;
	private String questionType;
	private double points;
	private String text;
	private String[] studentAnswer;
	private boolean trueFalseStudentAnswer;

	public Question(int questionNum, String questionType, double points, String text) {
		this.questionNum = questionNum;
		this.questionType = questionType;
		this.points = points;
		this.text = text;
	}// Constructor

	public void addStudentAnswer(String[] studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public void addStudentAnswerTrueFalse(boolean studentAnswer) {
		this.trueFalseStudentAnswer = studentAnswer;
	}

	public boolean getStudentAnswerTrueFalse() {
		return this.trueFalseStudentAnswer;
	}

	public String[] getStudentAnswer() {
		Arrays.sort(this.studentAnswer);
		return this.studentAnswer;
	}// getStudentAnswer

	public String getAnswerTrueFalseString() {
		return null;
	}

	public int getQuestionNum() {
		return this.questionNum;
	}// getQuestionNum

	public String getType() {
		return this.questionType;
	}// getType

	public double getPoints() {
		return this.points;
	}// getPoints

	public String getText() {
		return this.text;
	}// getText

	public String toString() {
		return this.questionNum + " " + this.text + " " + this.points;
	}

	public String[] getAnswer() {
		return null;
	}

	public boolean getAnswerTrueFalse() {
		return false;
	}
}
