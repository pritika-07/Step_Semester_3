package main.java.OOP.practice_problems;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
enum RoomCategory {
STANDARD,
DELUXE,
SUITE
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
class Room {
private String roomNumber;
private RoomCategory category;
private double pricePerDay;
public Room(String roomNumber, RoomCategory category, double pricePerDay) {
this.roomNumber = roomNumber;
this.category = category;
this.pricePerDay = pricePerDay;
}
public String getRoomNumber() {
return roomNumber;
}
public RoomCategory getCategory() {
return category;
}
public double calculatePrice(long days) {
return pricePerDay * days;
}
}
class Reservation {
private Room room;
private Customer customer;
private LocalDate startDate;
private LocalDate endDate;
private boolean active;
private LocalDate cancellationDeadline;
public Reservation(Room room, Customer customer, LocalDate startDate, LocalDate endDate, LocalDate cancellationDeadline) {
this.room = room;
this.customer = customer;
this.startDate = startDate;
this.endDate = endDate;
this.cancellationDeadline = cancellationDeadline;
this.active = true;
}
public boolean overlaps(LocalDate start, LocalDate end) {
return active && startDate.isBefore(end) && endDate.isAfter(start);
}
public double getPrice() {
long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
return room.calculatePrice(days);
}
public Room getRoom() {
return room;
}
public void cancel(LocalDate currentDate) {
if (!active) {
System.out.println("Reservation is already cancelled.");
return;
}
if (currentDate.isBefore(cancellationDeadline)) {
active = false;
System.out.println("Reservation for " + room.getCategory() + " Room " + room.getRoomNumber() + " cancelled successfully.");
} else {
System.out.println("Cancellation deadline has passed.");
}
}
}
class Hotel {
private List<Room> rooms;
private List<Reservation> reservations;
public Hotel() {
rooms = new ArrayList<>();
reservations = new ArrayList<>();
}
public void addRoom(Room room) {
rooms.add(room);
}
public boolean isAvailable(Room room, LocalDate start, LocalDate end) {
for (Reservation reservation : reservations) {
if (reservation.getRoom() == room && reservation.overlaps(start, end)) {
return false;
}
}
return true;
}
public Reservation bookRoom(Room room, Customer customer, LocalDate start, LocalDate end, LocalDate cancellationDeadline) {
if (!isAvailable(room, start, end)) {
System.out.println("Booking failed: " + room.getCategory() + " Room " + room.getRoomNumber() + " is not available for " + start + " to " + end + ".");
return null;
}
Reservation reservation = new Reservation(room, customer, start, end, cancellationDeadline);
reservations.add(reservation);
System.out.println(room.getCategory() + " Room " + room.getRoomNumber() + " booked from " + start + " to " + end + ".");
System.out.printf("Total price: $%.2f%n", reservation.getPrice());
return reservation;
}
}
public class HotelBookingSystem {
public static void main(String[] args) {
Hotel hotel = new Hotel();
Customer customer = new Customer("John");
Room deluxe = new Room("101", RoomCategory.DELUXE, 200);
Room standard = new Room("205", RoomCategory.STANDARD, 150);
hotel.addRoom(deluxe);
hotel.addRoom(standard);
LocalDate start1 = LocalDate.of(2024, 12, 1);
LocalDate end1 = LocalDate.of(2024, 12, 5);
Reservation r1 = hotel.bookRoom(deluxe, customer, start1, end1, LocalDate.of(2024, 11, 30));
LocalDate start2 = LocalDate.of(2024, 12, 3);
LocalDate end2 = LocalDate.of(2024, 12, 7);
Reservation r2 = hotel.bookRoom(standard, customer, start2, end2, LocalDate.of(2024, 12, 1));
Reservation r3 = hotel.bookRoom(deluxe, customer, start2, end2, LocalDate.of(2024, 12, 1));
if (r1 != null) {
r1.cancel(LocalDate.of(2024, 11, 25));
}
}
}