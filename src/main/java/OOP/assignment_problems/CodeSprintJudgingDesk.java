package main.java.OOP.assignment_problems;
import java.util.*;

interface ScoringRule {
double calculateScore(double idea,double execution,double presentation);
}
class InnovationScoring implements ScoringRule {
public double calculateScore(double idea,double execution,double presentation) {
return idea*0.5+execution*0.3+presentation*0.2;
}
}
class OpenScoring implements ScoringRule {
public double calculateScore(double idea,double execution,double presentation) {
return (idea+execution+presentation)/3;
}
}
class Student {
String name;
Student(String name) {
this.name=name;
}
}
class Project {
String name;
Project(String name) {
this.name=name;
}
}
class Judge {
String name;
Judge(String name) {
this.name=name;
}
}
class Score {
double idea,execution,presentation;
Score(double idea,double execution,double presentation) {
this.idea=idea;
this.execution=execution;
this.presentation=presentation;
}
}
class Team {
String name;
List<Student> members=new ArrayList<>();
String track;
Project project;
Score score;
Team(String name,String track) {
this.name=name;
this.track=track;
}
boolean addMember(Student student) {
if(members.size()>=4) return false;
members.add(student);
return true;
}
}
class Hackathon {
String name;
Map<Student,Team> studentTeams=new HashMap<>();
List<Team> teams=new ArrayList<>();
boolean resultsPublished=false;
Hackathon(String name) {
this.name=name;
}
boolean registerTeam(Team team) {
if(team.members.size()<2||team.members.size()>4) {
System.out.println("Registration failed: A team must have 2 to 4 members.");
return false;
}
for(Student student:team.members) {
if(studentTeams.containsKey(student)) {
System.out.println("Registration failed: Student already belongs to a team.");
return false;
}
}
for(Student student:team.members) studentTeams.put(student,team);
teams.add(team);
System.out.println("Team "+team.name+" registered ("+team.members.size()+" members, "+team.track+" track).");
return true;
}
void submitProject(Team team,Project project) {
if(team.project!=null) {
System.out.println("Submission failed: Team can submit only one project.");
return;
}
team.project=project;
System.out.println("Project '"+project.name+"' submitted by "+team.name+".");
}
void recordScore(Team team,Score score) {
if(resultsPublished) {
System.out.println("Rescore rejected: Results have already been published.");
return;
}
team.score=score;
System.out.println("Score recorded for '"+team.project.name+"'.");
}
void publishResults() {
resultsPublished=true;
for(Team team:teams) {
if(team.score!=null) {
ScoringRule rule=team.track.equalsIgnoreCase("Innovation")?new InnovationScoring():new OpenScoring();
double finalScore=rule.calculateScore(team.score.idea,team.score.execution,team.score.presentation);
System.out.printf("Final score: %.2f%n",finalScore);
}
}
System.out.println("Results published.");
}
}
public class CodeSprintJudgingDesk {
public static void main(String[] args) {
Hackathon hackathon=new Hackathon("Code Sprint");
Team byteBusters=new Team("ByteBusters","Innovation");
byteBusters.addMember(new Student("Asha"));
byteBusters.addMember(new Student("Ravi"));
byteBusters.addMember(new Student("Neha"));
hackathon.registerTeam(byteBusters);
Team soloCoder=new Team("SoloCoder","Open");
soloCoder.addMember(new Student("Kiran"));
hackathon.registerTeam(soloCoder);
hackathon.submitProject(byteBusters,new Project("SmartAttend"));
hackathon.recordScore(byteBusters,new Score(8,7,9));
hackathon.publishResults();
hackathon.recordScore(byteBusters,new Score(10,7,9));
}
}