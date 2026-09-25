package main.java.Inheritance.assignment_problems;
public class LateWithdrawalPenalty {
    static class RaceEntry {
        protected double balanceDue;
        private double[] lateFeeHistory = new double[10];
        private int feeCount = 0;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid bib number");
            }
            balanceDue = entryFee;
        }

        public void pay(double amount) {
            balanceDue -= amount;
            if (balanceDue < 0) {
                balanceDue = 0;
            }
        }

        protected void applyLateFee(double amount) {
            balanceDue += amount;
            lateFeeHistory[feeCount++] = amount;
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public double[] getLateFeeHistory() {
            double[] copy = new double[feeCount];
            for (int i = 0; i < feeCount; i++) {
                copy[i] = lateFeeHistory[i];
            }
            return copy;
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

    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);
        System.out.println(r.getBalanceDue());
        double[] history = r.getLateFeeHistory();
        history[0] = 999;
        System.out.println(r.getLateFeeHistory()[0]);
    }
} 
