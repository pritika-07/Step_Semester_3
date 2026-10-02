package main.java.OOP.assignment_problems;
import java.util.*;

interface ShippingType {
double calculateCharge(double weight);
String getName();
}
class StandardShipping implements ShippingType {
public double calculateCharge(double weight) {
return 40+10*weight;
}
public String getName() {
return "Standard";
}
}
class ExpressShipping implements ShippingType {
public double calculateCharge(double weight) {
return 80+15*weight;
}
public String getName() {
return "Express";
}
}
class FragileShipping implements ShippingType {
public double calculateCharge(double weight) {
return 40+10*weight+50;
}
public String getName() {
return "Fragile";
}
}
interface NotificationChannel {
void notify(String parcelId,String status);
}
class SmsChannel implements NotificationChannel {
public void notify(String parcelId,String status) {
System.out.println("[SMS] "+parcelId+" is now "+status+".");
}
}
class EmailChannel implements NotificationChannel {
public void notify(String parcelId,String status) {
System.out.println("[Email] "+parcelId+" is now "+status+".");
}
}
enum ParcelStatus {
BOOKED,PICKED_UP,IN_TRANSIT,OUT_FOR_DELIVERY,DELIVERED,CANCELLED
}
class Customer {
String name;
List<NotificationChannel> channels=new ArrayList<>();
Customer(String name) {
this.name=name;
}
void subscribe(NotificationChannel channel) {
channels.add(channel);
}
}
class Parcel {
String id;
double weight;
ShippingType shippingType;
ParcelStatus status;
Customer customer;
Parcel(String id,double weight,ShippingType shippingType,Customer customer) {
this.id=id;
this.weight=weight;
this.shippingType=shippingType;
this.customer=customer;
this.status=ParcelStatus.BOOKED;
}
double getCharge() {
return shippingType.calculateCharge(weight);
}
void notifyChannels() {
for(NotificationChannel channel:customer.channels) {
channel.notify(id,status.toString());
}
}
void changeStatus(ParcelStatus newStatus) {
boolean valid=false;
if(status==ParcelStatus.BOOKED&&newStatus==ParcelStatus.PICKED_UP) valid=true;
if(status==ParcelStatus.PICKED_UP&&newStatus==ParcelStatus.IN_TRANSIT) valid=true;
if(status==ParcelStatus.IN_TRANSIT&&newStatus==ParcelStatus.OUT_FOR_DELIVERY) valid=true;
if(status==ParcelStatus.OUT_FOR_DELIVERY&&newStatus==ParcelStatus.DELIVERED) valid=true;
if(valid) {
status=newStatus;
notifyChannels();
} else {
System.out.println("Invalid transition: "+status+" → "+newStatus+" is not allowed.");
}
}
void cancel() {
if(status!=ParcelStatus.BOOKED) {
System.out.println("Cancellation failed: "+id+" can be cancelled only while BOOKED.");
return;
}
status=ParcelStatus.CANCELLED;
notifyChannels();
}
}
class ParcelService {
void book(Parcel parcel) {
System.out.printf("Parcel %s booked (%s, %.0f kg).%n",parcel.id,parcel.shippingType.getName(),parcel.weight);
System.out.printf("Charge: ₹%.2f%n",parcel.getCharge());
parcel.notifyChannels();
}
}
public class SwiftShipParcelTracker {
public static void main(String[] args) {
Customer customer=new Customer("Asha");
customer.subscribe(new SmsChannel());
customer.subscribe(new EmailChannel());
Parcel parcel=new Parcel("P101",2,new ExpressShipping(),customer);
ParcelService service=new ParcelService();
service.book(parcel);
parcel.changeStatus(ParcelStatus.PICKED_UP);
parcel.cancel();
parcel.changeStatus(ParcelStatus.IN_TRANSIT);
parcel.changeStatus(ParcelStatus.DELIVERED);
}
}
