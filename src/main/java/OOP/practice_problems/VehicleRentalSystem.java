package main.java.OOP.practice_problems;
abstract class Vehicle {
private String name;
private boolean available;
public Vehicle(String name) {
this.name = name;
this.available = true;
}
public String getName() {
return name;
}
public boolean isAvailable() {
return available;
}
public void setAvailable(boolean available) {
this.available = available;
}
public abstract double calculateCharge(int days);
}
class StandardCar extends Vehicle {
public StandardCar(String name) {
super(name);
}
public double calculateCharge(int days) {
return 50.0 * days;
}
}
class LuxuryCar extends Vehicle {
public LuxuryCar(String name) {
super(name);
}
public double calculateCharge(int days) {
return 100.0 * days;
}
}
class Customer {
private String name;
public Customer(String name) {
this.name = name;
}
public String getName() {
return name;
}
}
class Rental {
private Customer customer;
private Vehicle vehicle;
private int days;
private double totalCharge;
public Rental(Customer customer, Vehicle vehicle, int days) {
this.customer = customer;
this.vehicle = vehicle;
this.days = days;
this.totalCharge = vehicle.calculateCharge(days);
}
public double getTotalCharge() {
return totalCharge;
}
public Vehicle getVehicle() {
return vehicle;
}
public void returnVehicle() {
vehicle.setAvailable(true);
}
}
class RentalService {
public Rental rentVehicle(Customer customer, Vehicle vehicle, int days) {
if (!vehicle.isAvailable()) {
System.out.println(vehicle.getName() + " is not available.");
return null;
}
vehicle.setAvailable(false);
Rental rental = new Rental(customer, vehicle, days);
System.out.println(vehicle.getName() + " rented for " + days + " days.");
System.out.printf("Total charge: $%.2f%n", rental.getTotalCharge());
return rental;
}
public void returnVehicle(Rental rental) {
if (rental != null) {
rental.returnVehicle();
System.out.println(rental.getVehicle().getName() + " returned. Now available.");
}
}
}
public class VehicleRentalSystem {
public static void main(String[] args) {
Customer customer = new Customer("John");
Vehicle luxury = new LuxuryCar("Luxury Car A");
Vehicle standard = new StandardCar("Standard Car B");
RentalService service = new RentalService();
Rental rental1 = service.rentVehicle(customer, luxury, 3);
Rental rental2 = service.rentVehicle(customer, standard, 5);
service.returnVehicle(rental1);
}
} 
