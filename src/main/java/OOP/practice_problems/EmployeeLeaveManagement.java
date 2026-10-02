package main.java.OOP.practice_problems;
import java.time.LocalDate;
enum LeaveStatus {
PENDING,
APPROVED,
REJECTED
}
abstract class Employee {
private String name;
public Employee(String name) {
this.name = name;
}
public String getName() {
return name;
}
public abstract boolean isLeaveAllowed(LocalDate start, LocalDate end);
}
class FullTimeEmployee extends Employee {
public FullTimeEmployee(String name) {
super(name);
}
public boolean isLeaveAllowed(LocalDate start, LocalDate end) {
return true;
}
}
class PartTimeEmployee extends Employee {
public PartTimeEmployee(String name) {
super(name);
}
public boolean isLeaveAllowed(LocalDate start, LocalDate end) {
long days = java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1;
return days <= 5;
}
}
class ContractEmployee extends Employee {
public ContractEmployee(String name) {
super(name);
}
public boolean isLeaveAllowed(LocalDate start, LocalDate end) {
long days = java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1;
return days <= 3;
}
}
class LeaveRequest {
private Employee employee;
private LocalDate startDate;
private LocalDate endDate;
private LeaveStatus status;
public LeaveRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
this.employee = employee;
this.startDate = startDate;
this.endDate = endDate;
this.status = LeaveStatus.PENDING;
}
public Employee getEmployee() {
return employee;
}
public LeaveStatus getStatus() {
return status;
}
public boolean approve() {
if (status != LeaveStatus.PENDING) {
return false;
}
if (!employee.isLeaveAllowed(startDate, endDate)) {
return false;
}
status = LeaveStatus.APPROVED;
return true;
}
public boolean reject() {
if (status != LeaveStatus.PENDING) {
return false;
}
status = LeaveStatus.REJECTED;
return true;
}
public void setStatus(LeaveStatus newStatus) {
if (status != LeaveStatus.PENDING && newStatus == LeaveStatus.PENDING) {
System.out.println("Cannot change status: " + status + " request cannot revert to Pending.");
return;
}
status = newStatus;
}
}
class LeaveManager {
public LeaveRequest submitRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
LeaveRequest request = new LeaveRequest(employee, startDate, endDate);
System.out.println("Leave request submitted by " + employee.getName() + " for " + startDate + " to " + endDate + ". Status: " + request.getStatus() + ".");
return request;
}
public void approveRequest(LeaveRequest request) {
if (request.approve()) {
System.out.println("Leave request for " + request.getEmployee().getName() + " approved. Status: " + request.getStatus() + ".");
} else {
System.out.println("Leave request could not be approved.");
}
}
public void rejectRequest(LeaveRequest request) {
if (request.reject()) {
System.out.println("Leave request for " + request.getEmployee().getName() + " rejected. Status: " + request.getStatus() + ".");
}
}
}
public class EmployeeLeaveManagement {
public static void main(String[] args) {
LeaveManager manager = new LeaveManager();
Employee john = new FullTimeEmployee("John Doe");
Employee jane = new PartTimeEmployee("Jane Smith");
LeaveRequest johnRequest = manager.submitRequest(john, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
manager.approveRequest(johnRequest);
LeaveRequest janeRequest = manager.submitRequest(jane, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));
johnRequest.setStatus(LeaveStatus.PENDING);
}
}
