package main.java.OOP.assignment_problems;
import java.util.*;

interface Capability {
String getName();
boolean setValue(int value);
String getValue();
}
class PowerCapability implements Capability {
boolean on=false;
public String getName() {
return "Power";
}
public boolean setValue(int value) {
if(value!=0&&value!=1) return false;
on=value==1;
return true;
}
public String getValue() {
return on?"ON":"OFF";
}
}
class BrightnessCapability implements Capability {
int brightness=0;
public String getName() {
return "Brightness";
}
public boolean setValue(int value) {
if(value<0||value>100) return false;
brightness=value;
return true;
}
public String getValue() {
return brightness+"%";
}
}
class TemperatureCapability implements Capability {
int temperature=16;
public String getName() {
return "Temperature";
}
public boolean setValue(int value) {
if(value<16||value>30) return false;
temperature=value;
return true;
}
public String getValue() {
return temperature+"°C";
}
}
class Device {
String name;
Map<String,Capability> capabilities=new HashMap<>();
Device(String name) {
this.name=name;
}
void addCapability(Capability capability) {
capabilities.put(capability.getName(),capability);
System.out.println(name+": "+capability.getName()+" capability added.");
}
Capability getCapability(String name) {
return capabilities.get(name);
}
}
class SceneStep {
String capability;
int value;
SceneStep(String capability,int value) {
this.capability=capability;
this.value=value;
}
}
class Scene {
String name;
List<SceneStep> steps=new ArrayList<>();
Scene(String name) {
this.name=name;
}
void addStep(SceneStep step) {
steps.add(step);
}
void execute(List<Device> devices) {
System.out.println("Scene '"+name+"' started.");
int count=0;
for(SceneStep step:steps) {
for(Device device:devices) {
Capability capability=device.getCapability(step.capability);
if(capability!=null&&capability.setValue(step.value)) {
count++;
if(step.capability.equals("Power"))
System.out.println(device.name+": "+(step.value==1?"ON":"OFF")+".");
else if(step.capability.equals("Brightness"))
System.out.println(device.name+": brightness set to "+step.value+"%.");
else if(step.capability.equals("Temperature"))
System.out.println(device.name+": temperature set to "+step.value+"°C.");
}
}
}
System.out.println("Scene '"+name+"' completed: "+count+" actions applied.");
}
}
public class SmartLabControlPanel {
public static void main(String[] args) {
Device ac=new Device("Lab AC");
ac.addCapability(new PowerCapability());
ac.addCapability(new TemperatureCapability());
Device lights=new Device("Ceiling Lights");
lights.addCapability(new PowerCapability());
lights.addCapability(new BrightnessCapability());
Device projector=new Device("Projector");
projector.addCapability(new PowerCapability());
List<Device> devices=Arrays.asList(ac,lights,projector);
Scene lectureMode=new Scene("Lecture Mode");
lectureMode.addStep(new SceneStep("Power",1));
lectureMode.addStep(new SceneStep("Brightness",40));
lectureMode.addStep(new SceneStep("Temperature",24));
lectureMode.execute(devices);
Capability temperature=ac.getCapability("Temperature");
if(!temperature.setValue(12))
System.out.println("Rejected: Lab AC temperature must be between 16°C and 30°C.");
projector.addCapability(new BrightnessCapability());
projector.getCapability("Brightness").setValue(70);
System.out.println("Projector: brightness set to 70%.");
}
}
