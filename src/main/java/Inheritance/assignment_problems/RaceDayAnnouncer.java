package main.java.Inheritance.assignment_problems;
public class RaceDayAnnouncer {
    static class RaceEntry {
        protected String bibNumber;
        protected double balanceDue;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid bib number");
            }
            this.bibNumber = bibNumber;
            this.balanceDue = entryFee;
        }

        public void announce(StringBuilder report) {
            report.append("Race Entry | Bib: ").append(bibNumber).append(" | Balance: ").append(balanceDue).append(" | ");
        }
    }

    static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        public void announce(StringBuilder report) {
            report.append("Runner Entry | Bib: ").append(bibNumber).append(" | Category: ").append(category).append(" | Balance: ").append(balanceDue).append(" | ");
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
        public void announce(StringBuilder report) {
            report.append("Relay Team | Bib: ").append(bibNumber).append(" | Team Size: ").append(teamSize).append(" | Balance: ").append(balanceDue).append(" | ");
        }
    }

    static String announceAll(RaceEntry[] entries) {
        StringBuilder report = new StringBuilder();
        for (RaceEntry entry : entries) {
            entry.announce(report);
            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                report.append("[Team size via downcast: ").append(relay.getTeamSize()).append("] | ");
            }
        }
        return report.toString();
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 90, "Open 10K");
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);
        RaceEntry[] entries = {runnerEntry, relayEntry};
        System.out.println(announceAll(entries));
    }
}
