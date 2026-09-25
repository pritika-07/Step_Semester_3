package main.java.Inheritance.assignment_problems;
public class RaceFamilyClassifier {
    static class RaceEntry {
        protected String bibNumber;
        protected double entryFee;
        protected double balanceDue;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid bib number");
            }
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

        public void announce() {
            System.out.println("Race Entry | Bib: " + bibNumber + " | Balance: " + balanceDue);
        }
    }

    static class RunnerEntry extends RaceEntry {
        protected String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        public void announce() {
            System.out.println("Runner Entry | Bib: " + bibNumber + " | Category: " + category + " | Balance: " + balanceDue);
        }
    }

    static class EliteRunnerEntry extends RunnerEntry {
        private double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }

        @Override
        public void announce() {
            System.out.println("Elite Runner | Bib: " + bibNumber + " | Category: " + category + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + balanceDue);
        }
    }

    static class RelayTeamEntry extends RaceEntry {
        private int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        public int getTeamSize() {
            return teamSize;
        }

        @Override
        public void announce() {
            System.out.println("Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize + " | Balance: " + balanceDue);
        }
    }

    static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        }
        if (entry instanceof RunnerEntry) {
            return "Runner descendant";
        }
        return "Base entry";
    }

    static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0;
        for (RaceEntry entry : entries) {
            total += entry.getBalanceDue();
        }
        return total;
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);
        runnerEntry.announce();
        eliteEntry.announce();
        relayEntry.announce();
        System.out.println(classifyGeneration(eliteEntry));
        System.out.println(classifyGeneration(relayEntry));
        RaceEntry[] entries = {runnerEntry, eliteEntry, relayEntry};
        System.out.println(getTotalBalanceDue(entries));
    }
} 