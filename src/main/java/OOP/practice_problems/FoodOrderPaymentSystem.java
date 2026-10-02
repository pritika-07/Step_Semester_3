package main.java.OOP.practice_problems;
import java.util.ArrayList;
import java.util.List;
interface IPaymentMethod {
boolean pay(double amount);
String getPaymentName();
}
class CreditCardPayment implements IPaymentMethod {
public boolean pay(double amount) {
return true;
}
public String getPaymentName() {
return "Credit Card";
}
}
class DigitalWalletPayment implements IPaymentMethod {
public boolean pay(double amount) {
return false;
}
public String getPaymentName() {
return "Digital Wallet";
}
}
class CashOnDeliveryPayment implements IPaymentMethod {
public boolean pay(double amount) {
return true;
}
public String getPaymentName() {
return "Cash on Delivery";
}
}
interface OrderObserver {
void update(String message);
}
class Customer implements OrderObserver {
private String name;
public Customer(String name) {
this.name = name;
}
public String getName() {
return name;
}
public void update(String message) {
System.out.println("Notification: " + message);
}
}
class Restaurant {
private String name;
public Restaurant(String name) {
this.name = name;
}
public String getName() {
return name;
}
}
class FoodItem {
private String name;
private double price;
public FoodItem(String name, double price) {
this.name = name;
this.price = price;
}
public String getName() {
return name;
}
public double getPrice() {
return price;
}
}
class LineItem {
private FoodItem foodItem;
private int quantity;
public LineItem(FoodItem foodItem, int quantity) {
this.foodItem = foodItem;
this.quantity = quantity;
}
public double getTotal() {
return foodItem.getPrice() * quantity;
}
public String getName() {
return foodItem.getName();
}
public int getQuantity() {
return quantity;
}
}
class Order {
private static int counter = 122;
private int orderId;
private Customer customer;
private Restaurant restaurant;
private List<LineItem> items;
private String status;
private List<OrderObserver> observers;
public Order(Customer customer, Restaurant restaurant) {
counter++;
orderId = counter;
this.customer = customer;
this.restaurant = restaurant;
items = new ArrayList<>();
observers = new ArrayList<>();
status = "Created";
System.out.println("Order created.");
}
public int getOrderId() {
return orderId;
}
public void addObserver(OrderObserver observer) {
observers.add(observer);
}
private void notifyObservers(String message) {
for (OrderObserver observer : observers) {
observer.update(message);
}
}
public void addItem(FoodItem item, int quantity) {
items.add(new LineItem(item, quantity));
System.out.println("Added " + item.getName() + " (Qty " + quantity + ").");
}
private double calculateTotal() {
double total = 0;
for (LineItem item : items) {
total += item.getTotal();
}
return total;
}
public boolean placeOrder(IPaymentMethod paymentMethod) {
if (items.isEmpty()) {
System.out.println("Cannot place order: Order must contain at least one item.");
return false;
}
status = "Placed";
System.out.println("Order placed successfully.");
notifyObservers("Order #" + orderId + " placed.");
boolean paymentSuccess = paymentMethod.pay(calculateTotal());
if (paymentSuccess) {
status = "Paid";
System.out.println("Payment via " + paymentMethod.getPaymentName() + " successful.");
System.out.println("Order status: " + status + ".");
notifyObservers("Order #" + orderId + " placed and paid.");
} else {
status = "Pending Payment";
System.out.println("Payment via " + paymentMethod.getPaymentName() + " failed.");
System.out.println("Order status: " + status + ".");
notifyObservers("Order #" + orderId + " placed, awaiting payment.");
}
return paymentSuccess;
}
}
public class FoodOrderPaymentSystem {
public static void main(String[] args) {
Customer customer = new Customer("John");
Restaurant restaurant = new Restaurant("Food Palace");
FoodItem pizza = new FoodItem("Pizza", 200);
FoodItem soda = new FoodItem("Soda", 50);
FoodItem burger = new FoodItem("Burger", 150);
Order emptyOrder = new Order(customer, restaurant);
emptyOrder.addObserver(customer);
emptyOrder.placeOrder(new CreditCardPayment());
Order order1 = new Order(customer, restaurant);
order1.addObserver(customer);
order1.addItem(pizza, 2);
order1.addItem(soda, 1);
order1.placeOrder(new CreditCardPayment());
Order order2 = new Order(customer, restaurant);
order2.addObserver(customer);
order2.addItem(burger, 1);
order2.placeOrder(new DigitalWalletPayment());
}
}
