package main.java.Abstraction.assignment_problems;
abstract class Shape {
private static int counter = 1000;
private final String shapeId;
Shape() {
counter++;
shapeId = "SH-" + counter;
}
public abstract double calculateArea();
void scale(double factor) {
}
void scale(double xFactor, double yFactor) {
scale((xFactor + yFactor) / 2);
}
String getShapeId() {
return shapeId;
}
}
class CircleShape extends Shape {
private double radius;
public CircleShape(double radius) {
this.radius = radius;
}
public double calculateArea() {
return Math.PI * radius * radius;
}
@Override
void scale(double factor) {
radius = radius * factor;
}
@Override
void scale(double xFactor, double yFactor) {
radius = radius * ((xFactor + yFactor) / 2);
}
}
class SquareShape extends Shape {
private double side;
public SquareShape(double side) {
this.side = side;
}
public double calculateArea() {
return side * side;
}
@Override
void scale(double factor) {
side = side * factor;
}
@Override
void scale(double xFactor, double yFactor) {
side = side * ((xFactor + yFactor) / 2);
}
}
public class BasicDrawingCanvas {
static void printArea(Shape s) {
System.out.println(s.calculateArea());
}
public static void main(String[] args) {
CircleShape c = new CircleShape(5.0);
SquareShape sq = new SquareShape(4.0);
System.out.println(c.calculateArea());
System.out.println(sq.calculateArea());
sq.scale(2.0);
System.out.println(sq.calculateArea());
printArea(c);
}
}
