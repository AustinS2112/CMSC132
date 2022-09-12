package onlineTest;

import java.util.ArrayList;

public class trueFalse extends Question{
	private int questionNum;
	private String text;
	private boolean answer;
	private double points;
	private ArrayList<Student> studentsWhoAnswered = new ArrayList<Student>();
	
	public trueFalse(String type, int questionNum, String text, boolean answer, double points) {
		super(questionNum, "trueFalse", points, text);

		this.text = text;
		this.points = points;
		this.questionNum = questionNum;
		this.answer = answer;
		//System.out.println("answer for questionToAdd: " + this.getAnswerTrueFalse());
	}//Constructor
	
	public void addStudent(Student student) {
		if(this.studentsWhoAnswered.size() == 0) {
			this.studentsWhoAnswered.add(student);
		}else {
			for(int i = 0; i < studentsWhoAnswered.size(); i++) {
				if(this.studentsWhoAnswered.get(i).getName() == student.getName()) {
					return;
				}else {
					this.studentsWhoAnswered.add(student);
				}
			}
		}
	}
	
	public boolean getAnswerTrueFalse() {
		if(answer == false) {
			return false;
		}
		return true;
	}
	
	public String getAnswerTrueFalseString() {
		if(answer == false) {
			return "False";
		}
		return "True";
	}
}
