package main.java.AccessModifier.class_problems;

public class PatientVitals {

    private double[] readings;
    private int count;

    public PatientVitals(double[] initialReadings) {

        readings = new double[500];
        count = 0;

        if (initialReadings != null) {

            for (double reading : initialReadings) {
                recordReading(reading);
            }
        }
    }

    public void recordReading(double reading) {

        if (reading <= 0 || reading > 45) {
            return;
        }

        if (count < readings.length) {
            readings[count] = reading;
            count++;
        }
    }

    public double getAverage() {

        if (count == 0) {
            return 0.0;
        }

        double sum = 0;

        for (int i = 0; i < count; i++) {
            sum += readings[i];
        }

        return sum / count;
    }

    public double[] getAllReadings() {

        double[] copy = new double[count];

        for (int i = 0; i < count; i++) {
            copy[i] = readings[i];
        }

        return copy;
    }

    public static void main(String[] args) {

        PatientVitals v = new PatientVitals(
            new double[]{36.5, -2, 37.1}
        );

        double[] readings = v.getAllReadings();

        System.out.println("Readings:");

        for (double reading : readings) {
            System.out.println(reading);
        }

        System.out.println("Average: " + v.getAverage());

        readings[0] = 999;

        System.out.println("After modifying copy:");

        double[] newReadings = v.getAllReadings();

        for (double reading : newReadings) {
            System.out.println(reading);
        }

        v.recordReading(40.0);
        v.recordReading(-5.0);
        v.recordReading(50.0);

        System.out.println("After adding readings:");

        double[] finalReadings = v.getAllReadings();

        for (double reading : finalReadings) {
            System.out.println(reading);
        }
    }
}  

