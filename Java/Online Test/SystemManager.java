package onlineTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SystemManager implements Manager, Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Exam> examDatabase = new ArrayList<Exam>();
	private ArrayList<Student> studentDatabase = new ArrayList<Student>();
	private String[] letterGrades;
	private double[] cutoffs;

	/**
	 * Adds the specified exam to the database.
	 * 
	 * @param examId
	 * @param title
	 * @return false if exam already exists.
	 */
	public boolean addExam(int examId, String title) {
		Exam examToAdd = new Exam(examId, title);

		if (findExam(examId) == true) {// exam already exists
			return false;
		} else {
			examDatabase.add(examToAdd);
			return true;
		}
	}// addExam

	private boolean findExam(int examId) {
		for (int i = 0; i < examDatabase.size(); i++) {
			if (examDatabase.get(i).getId() == examId) {
				return true;
			}
		}
		return false;
	}// auxiliary method to check if exam exists in database

	private boolean findStudent(String name) {
		for (int i = 0; i < studentDatabase.size(); i++) {
			if (studentDatabase.get(i).getName() == name) {
				return true;
			}
		}
		return false;
	}// auxiliary method to check if student exists in database

	private Exam returnExam(int examId) {
		for (int i = 0; i < examDatabase.size(); i++) {
			if (examDatabase.get(i).getId() == examId) {
				return examDatabase.get(i);
			}
		}
		return null;
	}// auxiliary method to retrieve the specified exam based on examId

	private Student returnStudent(String name) {// make private
		for (int i = 0; i < studentDatabase.size(); i++) {
			if (studentDatabase.get(i).getName().equals(name)) {
				return studentDatabase.get(i);
			}
		}
		return null;
	}// auxiliary method to retrieve the specified student based on name

	/**
	 * Adds a true and false question to the specified exam. If the question already
	 * exists it is overwritten.
	 * 
	 * @param examId
	 * @param questionNumber
	 * @param text           Question text
	 * @param points         total points
	 * @param answer         expected answer
	 */
	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		trueFalse questionToAdd = new trueFalse("trueFalse", questionNumber, text, answer, points);

		for (int i = 0; i < examDatabase.size(); i++) {
			if (examDatabase.get(i).getId() == examId) {
				examDatabase.get(i).addQuestion(questionToAdd);
			}
		}
	}// addTrueFalse

	/**
	 * Adds a multiple choice question to the specified exam. If the question
	 * already exists it is overwritten.
	 * 
	 * @param examId
	 * @param questionNumber
	 * @param text           Question text
	 * @param points         total points
	 * @param answer         expected answer
	 */
	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		multipleChoice questionToAdd = new multipleChoice("multipleChoice", questionNumber, text, answer, points);

		for (int i = 0; i < examDatabase.size(); i++) {
			if (examDatabase.get(i).getId() == examId) {
				examDatabase.get(i).addQuestion(questionToAdd);
			}
		}
	}// addMultipleChoice

	/**
	 * Adds a fill-in-the-blanks question to the specified exam. If the question
	 * already exits it is overwritten. Each correct response is worth
	 * points/entries in the answer.
	 * 
	 * @param examId
	 * @param questionNumber
	 * @param text           Question text
	 * @param points         total points
	 * @param answer         expected answer
	 */
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		fillBlanks questionToAdd = new fillBlanks("fillBlanks", questionNumber, text, answer, points);

		for (int i = 0; i < examDatabase.size(); i++) {
			if (examDatabase.get(i).getId() == examId) {
				examDatabase.get(i).addQuestion(questionToAdd);
			}
		}
	}// addFillInTheBlanksQuestion

	/**
	 * Returns a string with the following information per question:<br />
	 * "Question Text: " followed by the question's text<br />
	 * "Points: " followed by the points for the question<br />
	 * "Correct Answer: " followed by the correct answer. <br />
	 * The format for the correct answer will be: <br />
	 * a. True or false question: "True" or "False"<br />
	 * b. Multiple choice question: [ ] enclosing the answer (each entry separated
	 * by commas) and in sorted order. <br />
	 * c. Fill in the blanks question: [ ] enclosing the answer (each entry
	 * separated by commas) and in sorted order. <br />
	 * 
	 * @param examId
	 * @return "Exam not found" if exam not found, otherwise the key
	 */
	public String getKey(int examId) {
		for (int i = 0; i < examDatabase.size(); i++) {
			if (examDatabase.get(i).getId() == examId) {
				return examDatabase.get(i).getQuestions();
			}
		}
		return "Exam not found";
	}// getKey

	/**
	 * Adds the specified student to the database. Names are specified in the format
	 * LastName,FirstName
	 * 
	 * @param name
	 * @return false if student already exists.
	 */
	public boolean addStudent(String name) {
		Student studentToAdd = new Student(name);

		if (findStudent(name)) {// student already exists
			return false;
		} else {
			studentDatabase.add(studentToAdd);
			return true;
		}
	}// addStudent

	/**
	 * Enters the question's answer into the database.
	 * 
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		returnStudent(studentName).addExamToDatabase(returnExam(examId));// adds the exam to the examsTaken list for
																			// student
		returnExam(examId).addStudent(returnStudent(studentName));// adds student to examTakers list for exam

		for (int i = 0; i < studentDatabase.size(); i++) {
			if (studentDatabase.get(i).getName() == studentName) {
				studentDatabase.get(i).addAnswerTrueFalse(answer, questionNumber, examId);
				return;
			}
		}
	}// adds student answer to specified question in specified exam

	/**
	 * Enters the question's answer into the database.
	 * 
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		returnStudent(studentName).addExamToDatabase(returnExam(examId));// adds the exam to the examsTaken list for
																			// student
		returnExam(examId).addStudent(returnStudent(studentName));// adds student to examTakers list for exam

		for (int i = 0; i < studentDatabase.size(); i++) {
			if (studentDatabase.get(i).getName() == studentName) {
				studentDatabase.get(i).addAnswer(answer, questionNumber, examId);
			}
		}
	}// adds student answer to specified question in specified exam

	/**
	 * Enters the question's answer into the database.
	 * 
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		returnStudent(studentName).addExamToDatabase(returnExam(examId));// adds the exam to the examsTaken list for
																			// student
		returnExam(examId).addStudent(returnStudent(studentName));// adds student to examTakers list for exam

		for (int i = 0; i < studentDatabase.size(); i++) {
			if (studentDatabase.get(i).getName() == studentName) {
				studentDatabase.get(i).addAnswer(answer, questionNumber, examId);
			}
		}
	}// adds student answer to specified question in specified exam

	/**
	 * Returns the score the student got for the specified exam.
	 * 
	 * @param studentName
	 * @param examId
	 * @return score
	 */
	public double getExamScore(String studentName, int examId) {
		return returnStudent(studentName).getExamScore(studentName, examId);
	}// getExamScore

	/**
	 * Generates a grading report for the specified exam. The report will include
	 * the following information for each exam question:<br />
	 * "Question #" {questionNumber} {questionScore} " points out of "
	 * {totalQuestionPoints}<br />
	 * The report will end with the following information:<br />
	 * "Final Score: " {score} " out of " {totalExamPoints};
	 * 
	 * @param studentName
	 * @param examId
	 * @return report
	 */
	public String getGradingReport(String studentName, int examId) {
		for (int i = 0; i < examDatabase.size(); i++) {
			if (examDatabase.get(i).getId() == examId) {
				return examDatabase.get(i).returnIndividualGradingReport(studentName);
			}
		}
		return null;
	}// getGradingReport

	/**
	 * Sets the cutoffs for letter grades. For example, a typical curve we will have
	 * new String[]{"A","B","C","D","F"}, new double[] {90,80,70,60,0}. Anyone with
	 * a 90 or above gets an A, anyone with an 80 or above gets a B, etc. Notice we
	 * can have different letter grades and cutoffs (not just the typical curve).
	 * 
	 * @param letterGrades
	 * @param cutoffs
	 */
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		this.letterGrades = letterGrades;
		this.cutoffs = cutoffs;
	}// setLetterGradeCutoffs

	/**
	 * Computes a numeric grade (value between 0 and a 100) for the student taking
	 * into consideration all the exams. All exams have the same weight.
	 * 
	 * @param studentName
	 * @return grade
	 */
	public double getCourseNumericGrade(String studentName) {
		for (int i = 0; i < studentDatabase.size(); i++) {
			if (studentDatabase.get(i).getName() == studentName) {
				return studentDatabase.get(i).returnTotalGrade();
			}
		}
		return 0.0;
	}// getCourseNumericGrade

	private double getCourseNumericGradePercent(String studentName) {
		for (int i = 0; i < studentDatabase.size(); i++) {
			if (studentDatabase.get(i).getName().equals(studentName)) {
				return studentDatabase.get(i).returnTotalGradePercentage();
			}
		}
		return 0.0;
	}// getCourseNumericGrade

	/**
	 * Computes a letter grade based on cutoffs provided. It is assumed the cutoffs
	 * have been set before the method is called.
	 * 
	 * @param studentName
	 * @return letter grade
	 */
	public String getCourseLetterGrade(String studentName) {
		double studentNumGrade = getCourseNumericGradePercent(studentName);

		for (int i = 0; i < cutoffs.length; i++) {
			if (studentNumGrade > cutoffs[0]) {// student's grade is greater than first value in cutoffs
				returnStudent(studentName).setLetterGrade(letterGrades[0]);
				return letterGrades[0];
			} else if (studentNumGrade == cutoffs[i]) {// student's grade is exactly at a cutoff
				returnStudent(studentName).setLetterGrade(letterGrades[i]);
				return letterGrades[i];
			} else if (studentNumGrade > cutoffs[i]) {// student's grade is in between 2 cutoffs
				returnStudent(studentName).setLetterGrade(letterGrades[i]);
				return letterGrades[i];
			}
		}
		return null;
	}// getCourseLetterGrade

	private void setIndividualLetterGrade(Student student) {
		for (int i = 0; i < cutoffs.length; i++) {
			if (student.getNumGrade() > cutoffs[0]) {// student's grade is greater than first value in cutoffs
				student.setLetterGrade(letterGrades[0]);
			} else if (student.getNumGrade() == cutoffs[i]) {// student's grade is exactly at a cutoff
				student.setLetterGrade(letterGrades[i]);
			} else if (student.getNumGrade() < cutoffs[i] && student.getNumGrade() > cutoffs[i + 1]) {// student's grade
																										// is in between
																										// 2 cutoffs
				student.setLetterGrade(letterGrades[i + 1]);
			}
		}
	}// auxiliary method to set the letter grade for one student

	/**
	 * Returns a listing with the grades for each student. For each student the
	 * report will include the following information: <br />
	 * {studentName} {courseNumericGrade} {courseLetterGrade}<br />
	 * The names will appear in sorted order.
	 * 
	 * @return grades
	 */
	public String getCourseGrades() {
		String returnVal = "";

		Collections.sort(studentDatabase);

		for (int i = 0; i < studentDatabase.size(); i++) {
			setIndividualLetterGrade(studentDatabase.get(i));
			returnVal = returnVal + studentDatabase.get(i).getName() + " " + studentDatabase.get(i).returnStudentData()
					+ " " + studentDatabase.get(i).getLetterGrade() + "\n";
		}
		return returnVal;
	}// getCourseGrades

	/**
	 * Returns the maximum score (among all the students) for the specified exam.
	 * 
	 * @param examId
	 * @return maximum
	 */
	public double getMaxScore(int examId) {
		for (int i = 0; i < examDatabase.size(); i++) {
			if (examDatabase.get(i).getId() == examId) {
				return examDatabase.get(i).getMaxScore();
			}
		}
		return 0.0;
	}// getMaxScore

	/**
	 * Returns the minimum score (among all the students) for the specified exam.
	 * 
	 * @param examId
	 * @return minimum
	 */
	public double getMinScore(int examId) {
		for (int i = 0; i < examDatabase.size(); i++) {
			if (examDatabase.get(i).getId() == examId) {
				return examDatabase.get(i).getMinScore();
			}
		}
		return 0.0;
	}// getMinScore

	/**
	 * Returns the average score for the specified exam.
	 * 
	 * @param examId
	 * @return average
	 */
	public double getAverageScore(int examId) {
		for (int i = 0; i < examDatabase.size(); i++) {
			if (examDatabase.get(i).getId() == examId) {
				return examDatabase.get(i).getAverage();
			}
		}
		return 0.0;
	}// getverageScore

	/**
	 * It will serialize the Manager object and store it in the specified file.
	 */
	public void saveManager(Manager manager, String fileName) {
		File file = new File(fileName);

		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(manager);
			output.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}// saveManager

	/**
	 * It will return a Manager object based on the serialized data found in the
	 * specified file.
	 */
	public Manager restoreManager(String fileName) {
		File file = new File(fileName);

		if (!file.exists()) {
			return new SystemManager();
		} else {
			try {
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
				SystemManager systemManager = (SystemManager) input.readObject();
				input.close();

				return systemManager;
			} catch (IOException | ClassNotFoundException e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}// restoreManager
}
