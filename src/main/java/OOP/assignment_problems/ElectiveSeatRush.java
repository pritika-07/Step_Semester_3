package main.java.OOP.assignment_problems;
import java.util.*;

interface CreditPolicy {
int getCreditLimit();
String getType();
}
class RegularPolicy implements CreditPolicy {
public int getCreditLimit() {
return 24;
}
public String getType() {
return "Regular";
}
}
class HonorsPolicy implements CreditPolicy {
public int getCreditLimit() {
return 28;
}
public String getType() {
return "Honors";
}
}
class ExchangePolicy implements CreditPolicy {
public int getCreditLimit() {
return 20;
}
public String getType() {
return "Exchange";
}
}
class Student {
String name;
int currentCredits;
CreditPolicy policy;
Student(String name,CreditPolicy policy,int currentCredits) {
this.name=name;
this.policy=policy;
this.currentCredits=currentCredits;
}
}
class Elective {
String name;
int credits;
int capacity;
List<Student> enrolled=new ArrayList<>();
Queue<Student> waitlist=new LinkedList<>();
Elective(String name,int credits,int capacity) {
this.name=name;
this.credits=credits;
this.capacity=capacity;
}
boolean isEnrolled(Student student) {
return enrolled.contains(student);
}
boolean isWaitlisted(Student student) {
return waitlist.contains(student);
}
}
class EnrollmentService {
void enroll(Student student,Elective elective) {
if(elective.isEnrolled(student)||elective.isWaitlisted(student)) {
System.out.println("Enrollment failed: Student already enrolled or waitlisted.");
return;
}
if(student.currentCredits+elective.credits>student.policy.getCreditLimit()) {
System.out.println("Enrollment failed: "+student.name+" would exceed the "+student.policy.getType()+" credit limit ("+(student.currentCredits+elective.credits)+"/"+student.policy.getCreditLimit()+").");
return;
}
if(elective.enrolled.size()<elective.capacity) {
elective.enrolled.add(student);
student.currentCredits+=elective.credits;
System.out.println(student.name+" enrolled in "+elective.name+" (credits: "+student.currentCredits+"/"+student.policy.getCreditLimit()+").");
} else {
elective.waitlist.add(student);
System.out.println(elective.name+" is full.");
System.out.println(student.name+" added to waitlist (position "+elective.waitlist.size()+").");
}
}
void drop(Student student,Elective elective) {
if(!elective.enrolled.remove(student)) {
System.out.println("Drop failed: "+student.name+" is not enrolled.");
return;
}
student.currentCredits-=elective.credits;
System.out.println(student.name+" dropped "+elective.name+" (credits: "+student.currentCredits+"/"+student.policy.getCreditLimit()+").");
promote(elective);
}
void promote(Elective elective) {
while(elective.enrolled.size()<elective.capacity&&!elective.waitlist.isEmpty()) {
Student student=elective.waitlist.poll();
if(student.currentCredits+elective.credits<=student.policy.getCreditLimit()) {
elective.enrolled.add(student);
student.currentCredits+=elective.credits;
System.out.println(student.name+" promoted from waitlist and enrolled in "+elective.name+" (credits: "+student.currentCredits+"/"+student.policy.getCreditLimit()+").");
} else {
System.out.println(student.name+" could not be promoted due to credit limit.");
}
}
}
}
public class ElectiveSeatRush {
public static void main(String[] args) {
Elective elective=new Elective("Cloud Computing",4,2);
EnrollmentService service=new EnrollmentService();
Student asha=new Student("Asha",new RegularPolicy(),20);
Student ravi=new Student("Ravi",new HonorsPolicy(),22);
Student neha=new Student("Neha",new ExchangePolicy(),12);
Student kiran=new Student("Kiran",new RegularPolicy(),22);
service.enroll(asha,elective);
service.enroll(ravi,elective);
service.enroll(neha,elective);
service.enroll(kiran,elective);
service.drop(asha,elective);
}
}
