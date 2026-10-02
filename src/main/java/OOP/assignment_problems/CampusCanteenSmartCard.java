package main.java.OOP.assignment_problems;
import java.util.*;

interface PricingPlan {
double getPrice(double originalPrice);
String getName();
}
class DayScholarPlan implements PricingPlan {
public double getPrice(double originalPrice) {
return originalPrice;
}
public String getName() {
return "Day Scholar";
}
}
class HostellerPlan implements PricingPlan {
public double getPrice(double originalPrice) {
return originalPrice*0.90;
}
public String getName() {
return "Hosteller";
}
}
class StaffPlan implements PricingPlan {
public double getPrice(double originalPrice) {
return originalPrice*0.80;
}
public String getName() {
return "Staff";
}
}
class Transaction {
double amount;
String description;
Transaction(double amount,String description) {
this.amount=amount;
this.description=description;
}
}
class FoodItem {
String name;
double price;
FoodItem(String name,double price) {
this.name=name;
this.price=price;
}
}
class Purchase {
FoodItem item;
double chargedAmount;
boolean refunded=false;
Purchase(FoodItem item,double chargedAmount) {
this.item=item;
this.chargedAmount=chargedAmount;
}
}
class SmartCard {
String cardNumber;
PricingPlan plan;
double balance=0;
List<Transaction> transactions=new ArrayList<>();
boolean blocked=false;
SmartCard(String cardNumber,PricingPlan plan) {
this.cardNumber=cardNumber;
this.plan=plan;
}
void topUp(double amount) {
if(blocked) {
System.out.println("Top-up rejected: Card is blocked.");
return;
}
if(amount<100) {
System.out.println("Top-up rejected: Minimum top-up is ₹100.");
return;
}
if(balance+amount>5000) {
System.out.println("Top-up rejected: Maximum balance is ₹5000.");
return;
}
balance+=amount;
transactions.add(new Transaction(amount,"Top-up"));
System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f.%n",cardNumber,amount,balance);
}
Purchase purchase(FoodItem item) {
if(blocked) {
System.out.println("Purchase failed: Card is blocked.");
return null;
}
double price=plan.getPrice(item.price);
if(price>balance) {
System.out.printf("Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",price,balance);
return null;
}
balance-=price;
transactions.add(new Transaction(-price,"Purchase"));
Purchase purchase=new Purchase(item,price);
System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f.%n",item.name,price,balance);
return purchase;
}
void refund(Purchase purchase) {
if(purchase==null) return;
if(purchase.refunded) {
System.out.println("Refund rejected: "+purchase.item.name+" has already been refunded.");
return;
}
balance+=purchase.chargedAmount;
transactions.add(new Transaction(purchase.chargedAmount,"Refund"));
purchase.refunded=true;
System.out.printf("Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",purchase.chargedAmount,purchase.item.name,balance);
}
void block() {
blocked=true;
}
void unblock() {
blocked=false;
}
void miniStatement() {
System.out.print("Mini-statement for "+cardNumber+": ");
double sum=0;
for(Transaction transaction:transactions) {
System.out.printf("%s%.2f",transaction.amount>=0?"+":"",transaction.amount);
sum+=transaction.amount;
if(transaction!=transactions.get(transactions.size()-1))
System.out.print(", ");
}
System.out.printf(" = ₹%.2f.%n",sum);
}
}
public class CampusCanteenSmartCard {
public static void main(String[] args) {
SmartCard card=new SmartCard("C-2045",new HostellerPlan());
card.topUp(500);
FoodItem thali=new FoodItem("Veg Thali",120);
FoodItem coffee=new FoodItem("Cold Coffee",60);
FoodItem expensive=new FoodItem("Food Combo",400);
Purchase p1=card.purchase(thali);
card.purchase(coffee);
card.purchase(expensive);
card.refund(p1);
card.refund(p1);
card.miniStatement();
}
}