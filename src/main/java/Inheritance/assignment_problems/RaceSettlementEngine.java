package main.java.Inheritance.assignment_problems;
public class RaceSettlementEngine {
    static class RaceEntry {
        private static int bibCounter = 0;
        private final String entryCode;
        protected double balanceDue;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid bib number");
            }
            bibCounter++;
            entryCode = "ENT-" + bibCounter;
            balanceDue = entryFee;
        }

        public void pay(double amount) {
            balanceDue -= amount;
            if (balanceDue < 0) {
                balanceDue = 0;
            }
        }

        public void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount);
        }

        public static boolean isValidDiscountCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }
            if (code.charAt(0) != 'M') {
                return false;
            }
            if (!Character.isDigit(code.charAt(1))) {
                return false;
            }
            if (!Character.isDigit(code.charAt(2))) {
                return false;
            }
            if (!Character.isDigit(code.charAt(3))) {
                return false;
            }
            if (!Character.isUpperCase(code.charAt(4))) {
                return false;
            }
            return true;
        }

        public static int getBibCounter() {
            return bibCounter;
        }

        public double getBalanceDue() {
            return balanceDue;
        }
    }

    static class RunnerEntry extends RaceEntry {
        public RunnerEntry(String bibNumber, double entryFee) {
            super(bibNumber, entryFee);
        }
    }

    static class EliteRunnerEntry extends RunnerEntry {
        public EliteRunnerEntry(String bibNumber, double entryFee) {
            super(bibNumber, entryFee);
        }
    }

    static class RelayTeamEntry extends RaceEntry {
        private int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            if (teamSize <= 0) {
                throw new IllegalArgumentException("Invalid team size");
            }
            this.teamSize = teamSize;
        }
    }

    static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (entry instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " + relay + " relay | " + individual + " individual";
    }

    public static void main(String[] args) {
        System.out.println(RaceEntry.isValidDiscountCode("M123A"));
        System.out.println(RaceEntry.isValidDiscountCode("M12A"));
        System.out.println(RaceEntry.isValidDiscountCode("X123A"));
        RunnerEntry runner = new RunnerEntry("BIB2001", 80);
        runner.pay(10, "UPI");
        EliteRunnerEntry elite = new EliteRunnerEntry("BIB3001", 150);
        RelayTeamEntry relay = new RelayTeamEntry("BIB4001", 300, 4);
        RaceEntry[] entries = {elite, null, relay};
        System.out.println(settleNight(entries));
        System.out.println(RaceEntry.getBibCounter());
    }
} 
