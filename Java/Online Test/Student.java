package onlineTest;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Comparable<Object>, Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private double numGrade;
	private String letterGrade;
	private String gradeReport;
	private String[] studentAnswer;
	private ArrayList<Exam> examsTaken = new ArrayList<Exam>();
	private ArrayList<Double> scores = new ArrayList<Double>();
	private ArrayList<Double> scoresPercentage = new ArrayList<Double>();

	public Student(String name) {
		this.name = name;
	}// Constructor

	public String getName() {
		return this.name;
	}// getName

	public double getNumGrade() {
		return this.numGrade;
	}// getNumGrade

	public String getLetterGrade() {
		return this.letterGrade;
	}// getLetterGrade

	public String[] getStudentAnswer() {
		return this.studentAnswer;
	}

	public String getGradingReport(int examId) {
		return this.gradeReport;
	}

	public void setGradingReport(int examId) {
		for (int i = 0; i < examsTaken.size(); i++) {
			if (examsTaken.get(i).getId() == examId) {
				this.gradeReport = examsTaken.get(i).getGradingReport(examId);
				// return this.gradeReport;
			}
		}
		// return this.gradeReport;
	}

	public void setNumGrade(double grade) {
		this.numGrade = grade;
	}

	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}

	public void addScore(double score) {
		this.scores.add(score);
	}

	public void addScorePercentage(double score) {
		this.scoresPercentage.add(score);
	}

	public void addExamToDatabase(Exam examToAdd) {
		if (examsTaken.size() == 0) {
			examsTaken.add(examToAdd);
			return;
		} // examsTaken has no exams in it yet

		for (int i = 0; i < examsTaken.size(); i++) {
			if (findExam(examToAdd.getId())) {// exam already exists
				return;
			} else {
				examsTaken.add(examToAdd);
			}
		}
	}// adds exam to exams taken database

	public Exam getExamTaken(int examId) {
		for (int i = 0; i < examsTaken.size(); i++) {
			if (examsTaken.get(i).getId() == examId) {
				return examsTaken.get(i);
			}
		}
		return null;
	}// returns an exam according to the examId

	public boolean findExam(int examId) {
		for (int i = 0; i < examsTaken.size(); i++) {
			if (examsTaken.get(i).getId() == examId) {
				return true;
			}
		}
		return false;
	}

	public double computeGradeForOneExam(Exam exam) {
		for (int i = 0; i < examsTaken.size(); i++) {
			if (examsTaken.get(i).getId() == exam.getId()) {
				return examsTaken.get(i).studentScore();
			}
		}
		return 0.0;
	}// gets the grade for one exam

	private double computeGradeForOneExamPercentage(Exam exam) {
		for (int i = 0; i < examsTaken.size(); i++) {
			if (examsTaken.get(i).getId() == exam.getId()) {
				return examsTaken.get(i).studentScorePercentage();
			}
		}
		return 0.0;
	}// gets the percentage grade for one exam

	public double returnTotalGrade() {
		double result = 0;

		for (int i = 0; i < scores.size(); i++) {
			result = result + scores.get(i);
		}
		return result / scores.size();
	}// gets the total grade of the student taking into account all exams

	public double returnTotalGradePercentage() {
		double result = 0;

		for (int i = 0; i < scoresPercentage.size(); i++) {
			result = result + scoresPercentage.get(i);
		}
		return result / scoresPercentage.size();
	}// gets the overall percentage grade of the student taking into account all
		// exams

	public void addAnswerTrueFalse(boolean answer, int questionNum, int examId) {
		for (int i = 0; i < examsTaken.size(); i++) {
			if (examsTaken.get(i).getId() == examId) {// finds specified exam
				examsTaken.get(i).returnFullQuestion(questionNum).addStudentAnswerTrueFalse(answer);
			}
		}
		if (questionNum == getExamTaken(examId).getNumQuestions()) {// if question is last question in exam
			this.numGrade = this.computeGradeForOneExam(getExamTaken(examId));
			setGradingReport(examId);
			getExamTaken(examId).addGradeReport(getGradingReport(examId));
			getExamTaken(examId).addScoreToDatabase(this.numGrade);
			addScore(this.numGrade);
			addScorePercentage(this.computeGradeForOneExamPercentage(getExamTaken(examId)));
		}
	}// addAnswerTrueFalse

	public void addAnswer(String[] answer, int questionNum, int examId) {
		for (int i = 0; i < examsTaken.size(); i++) {
			if (examsTaken.get(i).getId() == examId) {
				examsTaken.get(i).returnFullQuestion(questionNum).addStudentAnswer(answer);
				this.studentAnswer = answer;
			}
		}
		if (questionNum == getExamTaken(examId).getNumQuestions()) {// if question is last question in exam
			this.numGrade = this.computeGradeForOneExam(getExamTaken(examId));
			setGradingReport(examId);
			getExamTaken(examId).addGradeReport(getGradingReport(examId));
			getExamTaken(examId).addScoreToDatabase(this.numGrade);
			addScore(this.numGrade);
			addScorePercentage(this.computeGradeForOneExamPercentage(getExamTaken(examId)));
		}
	}// addAnswer

	public double getExamScore(String studentName, int examId) {
		for (int i = 0; i < examsTaken.size(); i++) {// first finds specified exam
			if (examsTaken.get(i).getId() == examId) {
				return examsTaken.get(i).studentScore();
			}
		}
		return 0.0;
	}// getExamScore

	public String returnStudentData() {
		String returnVal = "";

		returnVal = returnVal + (this.numGrade);

		return returnVal;
	}// returnStudentData

	public int compareTo(Object o) {
		return this.getName().compareTo(((Student) o).getName());
	}// Compares the names of students

}
