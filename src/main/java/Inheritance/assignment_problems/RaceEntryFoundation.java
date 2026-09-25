package main.java.Inheritance.assignment_problems;

public class RaceEntryFoundation {
    static class RaceEntry {
        protected String bibNumber;
        protected double entryFee;
        protected double balanceDue;
        private double[] lateFeeHistory = new double[10];
        private int feeCount = 0;
        private static int bibCounter = 0;
        private final String entryCode;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid bib number");
            }
            bibCounter++;
            entryCode = "ENT-" + bibCounter;
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
        }

        public void pay(double amount) {
            balanceDue -= amount;
            if (balanceDue < 0) {
                balanceDue = 0;
            }
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        protected void applyLateFee(double amount) {
            balanceDue += amount;
            lateFeeHistory[feeCount++] = amount;
        }

        public double[] getLateFeeHistory() {
            double[] copy = new double[feeCount];
            for (int i = 0; i < feeCount; i++) {
                copy[i] = lateFeeHistory[i];
            }
            return copy;
        }

        public static int getBibCounter() {
            return bibCounter;
        }
    }

    static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;
        for (String bibNumber : bibNumbers) {
            try {
                new RaceEntry(bibNumber, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println(r.getBalanceDue());
        String[] bibNumbers = {"BIB1", "B1", "BIB2"};
        System.out.println(registerBatch(bibNumbers, 80));
    }
} 