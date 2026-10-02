package main.java.OOP.practice_problems;

import java.util.ArrayList;
import java.util.List;
abstract class Question {
private int questionNumber;
private String questionText;
public Question(int questionNumber, String questionText) {
this.questionNumber = questionNumber;
this.questionText = questionText;
}
public int getQuestionNumber() {
return questionNumber;
}
public String getQuestionText() {
return questionText;
}
public abstract boolean evaluateAnswer(String answer);
}
class MultipleChoiceQuestion extends Question {
private String correctAnswer;
public MultipleChoiceQuestion(int questionNumber, String questionText, String correctAnswer) {
super(questionNumber, questionText);
this.correctAnswer = correctAnswer;
}
public boolean evaluateAnswer(String answer) {
return correctAnswer.equalsIgnoreCase(answer);
}
}
class Student {
private String name;
public Student(String name) {
this.name = name;
}
public String getName() {
return name;
}
}
class Attempt {
private Student student;
private Examination examination;
private List<String> answers;
private boolean submitted;
public Attempt(Student student, Examination examination) {
this.student = student;
this.examination = examination;
this.answers = new ArrayList<>();
this.submitted = false;
}
public void answerQuestion(String answer) {
if (submitted) {
System.out.println("Cannot change answer: Attempt already submitted.");
return;
}
answers.add(answer);
}
public void submit() {
if (submitted) {
return;
}
submitted = true;
System.out.println("Examination '" + examination.getName() + "' submitted successfully.");
}
public boolean isSubmitted() {
return submitted;
}
public List<String> getAnswers() {
return answers;
}
}
class Examination {
private String name;
private List<Question> questions;
private Attempt attempt;
public Examination(String name) {
this.name = name;
questions = new ArrayList<>();
}
public String getName() {
return name;
}
public void addQuestion(Question question) {
questions.add(question);
}
public void start(Student student) {
if (attempt != null && attempt.isSubmitted()) {
System.out.println("An attempt has already been submitted for this examination.");
return;
}
attempt = new Attempt(student, this);
System.out.println("Examination '" + name + "' started by " + student.getName() + ".");
}
public void answerQuestion(int questionNumber, String answer) {
if (attempt == null) {
System.out.println("Start the examination first.");
return;
}
attempt.answerQuestion(answer);
System.out.println("Question " + questionNumber + " answered with '" + answer + "'.");
}
public void submit() {
if (attempt == null) {
System.out.println("No active attempt.");
return;
}
attempt.submit();
evaluate();
}
private void evaluate() {
int correct = 0;
List<String> answers = attempt.getAnswers();
for (int i = 0; i < questions.size() && i < answers.size(); i++) {
if (questions.get(i).evaluateAnswer(answers.get(i))) {
correct++;
}
}
System.out.println("Result for '" + name + "' attempt: " + correct + "/" + questions.size() + " correct");
}
}
public class OnlineExaminationSystem {
public static void main(String[] args) {
Student student = new Student("John");
Examination exam = new Examination("Math Quiz");
exam.addQuestion(new MultipleChoiceQuestion(1, "2 + 2 = ?", "A"));
exam.addQuestion(new MultipleChoiceQuestion(2, "Capital of France?", "B"));
exam.start(student);
exam.answerQuestion(1, "A");
exam.answerQuestion(2, "C");
exam.submit();
}
}
