package onlineTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Exam implements Serializable {
	private static final long serialVersionUID = 1L;
	private int examId;
	private String title;
	private String gradeReport;
	private ArrayList<String> gradeReports = new ArrayList<String>();
	private ArrayList<Question> questions = new ArrayList<Question>();
	private ArrayList<Student> examTakers = new ArrayList<Student>();
	private ArrayList<Double> examScores = new ArrayList<Double>();

	public Exam(int examId, String title) {
		this.examId = examId;
		this.title = title;
	}// Constructor

	public int getId() {
		return this.examId;
	}// getId

	public String getTitle() {
		return this.title;
	}// getTItle

	public String printStudents() {
		String returnVal = "";

		for (int i = 0; i < examTakers.size(); i++) {
			returnVal = returnVal + examTakers.get(i).getName() + "\n";
		}
		return returnVal;
	}

	public void setGradeReport(String studentName) {
		for (int i = 0; i < examTakers.size(); i++) {
			if (examTakers.get(i).getName() == studentName) {
				this.gradeReport = examTakers.get(i).getGradingReport(this.examId);
			}
		}
	}// sets grade report for one exam

	public String printGradeReports() {
		String returnVal = "";

		for (int i = 0; i < gradeReports.size(); i++) {
			returnVal = returnVal + gradeReports.get(i) + "\n\n";
		}
		return returnVal;
	}

	private int returnIndex(String studentName) {

		for (int j = 0; j < examTakers.size(); j++) {
			if (examTakers.get(j).getName().equals(studentName)) {
				return j;
			} else {
			}
		}
		return 0;
	}

	public String getGradeReport(String studentName) {
		for (int i = 0; i < gradeReports.size(); i++) {
			if (i == returnIndex(studentName)) {
				return gradeReports.get(i);
			}
		}
		return null;
	}

	public void addGradeReport(String report) {
		this.gradeReports.add(report);
	}

	public String getGradeReport() {
		return this.gradeReport;
	}

	public void addScoreToDatabase(double score) {
		this.examScores.add(score);
	}

	public String returnExamScoreDatabase() {
		String returnVal = "";

		for (int i = 0; i < examScores.size(); i++) {
			returnVal = returnVal + examScores.get(i) + "\n";
		}
		return returnVal;
	}

	public ArrayList<Double> returnExamScores() {
		return this.examScores;
	}

	public ArrayList<Question> getQuestionList() {
		return this.questions;
	}

	public ArrayList<Student> getExamTakers() {
		return this.examTakers;
	}

	public int getNumQuestions() {
		return this.questions.size();
	}

	private boolean findStudent(String studentName) {
		for (int i = 0; i < examTakers.size(); i++) {
			if (examTakers.get(i).getName() == studentName) {
				return true;
			}
		}
		return false;
	}

	public void addStudent(Student student) {
		if (examTakers.size() == 0) {
			examTakers.add(student);
		}

		for (int i = 0; i < examTakers.size(); i++) {
			if (findStudent(student.getName())) {// checks if student is already added
				return;
			} else {
				examTakers.add(student);
			}
		}

	}// adds a student to the examTakers arrayList

	public void addQuestion(Question question) {
		if (findQuestion(question.getText())) {// question already exists
			for (int i = 0; i < questions.size(); i++) {
				if (questions.get(i).getText() == question.getText()) {
					questions.set(i, question);
				}
			} // replaces the pre-existing question with the new question
		} else {
			this.questions.add(question);
		}
	}// addQuestion

	public boolean findQuestion(String questionText) {
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getText() == questionText) {
				return true;
			}
		}
		return false;
	}// finds a question

	public Question returnFullQuestion(int questionNum) {
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getQuestionNum() == questionNum) {
				return questions.get(i);
			}
		}
		return null;
	}// returns the requested question based on questionNum

	private double totalScore(Exam exam) {
		double total = 0;

		for (int i = 0; i < questions.size(); i++) {
			total += questions.get(i).getPoints();
		}
		return total;
	}// Auxiliary method to compute the total amount of points for an exam

	private boolean studentAnswerContains(Question question, String elem) {
		for (int i = 0; i < question.getStudentAnswer().length; i++) {
			if (question.getStudentAnswer()[i] == elem) {
				return true;
			}
		}
		return false;
	}// auxiliary method for calculating partial credit in regards to fill in the
		// blank questions

	private double getFillBlankScore(Question question) {
		double numCorrect = 0;

		for (int j = 0; j < question.getAnswer().length; j++) {
			if (studentAnswerContains(question, question.getAnswer()[j])) {
				numCorrect += (question.getPoints() / question.getAnswer().length);
			}
		}
		return numCorrect;
	}// auxiliary method to get the total points for fill in the blank questions

	public double studentScore() {
		double numCorrect = 0;

		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i) instanceof trueFalse) {// if the question is true/false
				if (questions.get(i).getAnswerTrueFalse() == questions.get(i).getStudentAnswerTrueFalse()) {
					numCorrect += questions.get(i).getPoints();
				}
			} else if (questions.get(i) instanceof multipleChoice) {// question is multiple choice
				if (Arrays.equals(questions.get(i).getAnswer(), questions.get(i).getStudentAnswer())) {
					numCorrect += questions.get(i).getPoints();
				}
			} else if (questions.get(i) instanceof fillBlanks) {
				for (int j = 0; j < questions.get(i).getAnswer().length; j++) {// question is FiB
					if (studentAnswerContains(questions.get(i), questions.get(i).getAnswer()[j])) {
						numCorrect += (questions.get(i).getPoints() / questions.get(i).getAnswer().length);
					}
				}
			}
		}
		return numCorrect;
	}// computes and returns the score associated with the exam

	public double studentScorePercentage() {
		double numCorrect = 0;

		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i) instanceof trueFalse) {// if the question is true/false
				if (questions.get(i).getAnswerTrueFalse() == questions.get(i).getStudentAnswerTrueFalse()) {
					numCorrect += questions.get(i).getPoints();
				}
			} else if (questions.get(i) instanceof multipleChoice) {// question is multiple choice
				if (Arrays.equals(questions.get(i).getAnswer(), questions.get(i).getStudentAnswer())) {
					numCorrect += questions.get(i).getPoints();
				}
			} else if (questions.get(i) instanceof fillBlanks) {
				for (int j = 0; j < questions.get(i).getAnswer().length; j++) {// question is FiB
					if (studentAnswerContains(questions.get(i), questions.get(i).getAnswer()[j])) {
						numCorrect += (questions.get(i).getPoints() / questions.get(i).getAnswer().length);
					}
				}
			}
		}
		return (numCorrect / totalScore(this)) * 100;
	}// computes and returns the score associated with the exam

	private String arrayToString(String[] myArray) {
		String returnVal = "[";

		for (int i = 0; i < myArray.length; i++) {
			if (i < myArray.length - 1) {
				returnVal = returnVal + myArray[i] + ", ";
			} else {
				returnVal = returnVal + myArray[i] + "]";
			}
		}
		return returnVal;
	}// Auxiliary method to return an array as a string

	public String getQuestions() {
		String returnVal = "";

		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i) instanceof trueFalse) {
				returnVal = returnVal + "Question Text: " + questions.get(i).getText() + "\n";
				returnVal = returnVal + "Points: " + questions.get(i).getPoints() + "\n";
				returnVal = returnVal + "Correct Answer: " + questions.get(i).getAnswerTrueFalseString() + "\n";

				// return returnVal;
			} else if (questions.get(i) instanceof multipleChoice) {
				returnVal = returnVal + "Question Text: " + questions.get(i).getText() + "\n";
				returnVal = returnVal + "Points: " + questions.get(i).getPoints() + "\n";
				returnVal = returnVal + "Correct Answer: " + arrayToString(questions.get(i).getAnswer()) + "\n";

				// return returnVal;
			} else if (questions.get(i) instanceof fillBlanks) {
				returnVal = returnVal + "Question Text: " + questions.get(i).getText() + "\n";
				returnVal = returnVal + "Points: " + questions.get(i).getPoints() + "\n";
				returnVal = returnVal + "Correct Answer: " + arrayToString(questions.get(i).getAnswer()) + "\n";

				// return returnVal;
			}
		}
		return returnVal;
	}// getQuestions -> returns all questions w/in the exam in specified format

	public String returnIndividualGradingReport(String studentName) {
		int index = 0;

		for (int j = 0; j < examTakers.size(); j++) {
			if (examTakers.get(j).getName().equals(studentName)) {
				index = j;
			}
		}

		for (int i = 0; i < gradeReports.size(); i++) {
			if (i == index) {
				return gradeReports.get(i);
			}
		}

		return null;
	}// returns a grade report for specified student

	public String getGradingReport(int examId) {
		String returnVal = "";

		for (int i = 0; i < this.questions.size(); i++) {
			if (this.questions.get(i) instanceof trueFalse) {
				if (this.questions.get(i).getStudentAnswerTrueFalse() == this.questions.get(i).getAnswerTrueFalse()) {
					returnVal = returnVal + "Question #" + this.questions.get(i).getQuestionNum() + " "
							+ this.questions.get(i).getPoints() + " points out of " + this.questions.get(i).getPoints()
							+ "\n";
				} else {
					returnVal = returnVal + "Question #" + this.questions.get(i).getQuestionNum()
							+ " 0.0 points out of " + this.questions.get(i).getPoints() + "\n";
				}
			} else if (this.questions.get(i) instanceof multipleChoice) {
				if (Arrays.equals(questions.get(i).getStudentAnswer(), this.questions.get(i).getAnswer())) {
					returnVal = returnVal + "Question #" + this.questions.get(i).getQuestionNum() + " "
							+ this.questions.get(i).getPoints() + " points out of " + questions.get(i).getPoints()
							+ "\n";
				} else {
					returnVal = returnVal + "Question #" + questions.get(i).getQuestionNum() + " 0.0 points out of "
							+ questions.get(i).getPoints() + "\n";
				}
			} else if (this.questions.get(i) instanceof fillBlanks) {
				if (Arrays.equals(this.questions.get(i).getStudentAnswer(), this.questions.get(i).getAnswer())) {
					returnVal = returnVal + "Question #" + this.questions.get(i).getQuestionNum() + " "
							+ this.questions.get(i).getPoints() + " points out of " + questions.get(i).getPoints()
							+ "\n";
				} else {
					returnVal = returnVal + "Question #" + this.questions.get(i).getQuestionNum() + " "
							+ getFillBlankScore(this.questions.get(i)) + " points out of "
							+ questions.get(i).getPoints() + "\n";
				}
			}
		}
		returnVal = returnVal + "Final Score: " + this.studentScore() + " out of " + totalScore(this);

		return returnVal;
	}// getGradingReport

	public double returnNumGrade() {
		return (this.studentScore() / totalScore(this)) * 100;
	}// gets the number grade for an exam

	public double getMaxScore() {
		double max = examScores.get(0);

		for (int i = 1; i < examScores.size(); i++) {
			if (examScores.get(i) > max) {
				max = examScores.get(i);
			}
		}
		return max;
	}// returns the maximum score scored by a student in the examTakers arrayList

	public double getMinScore() {
		double min = examScores.get(0);

		for (int i = 1; i < examScores.size(); i++) {
			if (examScores.get(i) < min) {
				min = examScores.get(i);
			}
		}
		return min;
	}// returns the minimum score

	public double getAverage() {
		double total = 0;

		for (int i = 0; i < examScores.size(); i++) {
			total += examScores.get(i);
		}

		return total / examScores.size();
	}// returns the average of student scores

}
