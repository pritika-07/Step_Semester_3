package main.java.Inheritance.practice_problems;

public class NightlyTicketAnnouncer {

    static class EventTicket {
        protected double balanceDue;

        public EventTicket(double basePrice) {
            balanceDue = basePrice;
        }

        public void printTicket(StringBuilder report) {
            report.append("Standard | Balance: ")
                  .append(balanceDue)
                  .append(" | ");
        }
    }

    static class WorkshopTicket extends EventTicket {
        private String track;

        public WorkshopTicket(double basePrice, String track) {
            super(basePrice);
            this.track = track;
        }

        public String getTrack() {
            return track;
        }

        @Override
        public void printTicket(StringBuilder report) {
            report.append("Workshop | Track: ")
                  .append(track)
                  .append(" | Balance: ")
                  .append(balanceDue)
                  .append(" | ");
        }
    }

    static String batchPrint(EventTicket[] tickets) {
        StringBuilder report = new StringBuilder();

        for (EventTicket ticket : tickets) {
            ticket.printTicket(report);

            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket workshop =
                    (WorkshopTicket) ticket;

                report.append("[Track via downcast: ")
                      .append(workshop.getTrack())
                      .append("] | ");
            }
        }

        return report.toString();
    }

    public static void main(String[] args) {
        EventTicket standard =
            new EventTicket(500);

        WorkshopTicket workshop =
            new WorkshopTicket(1200, "AI/ML");

        EventTicket[] tickets = {
            standard,
            workshop
        };

        System.out.println(batchPrint(tickets));

        EventTicket plain = new EventTicket(500);

        if (plain instanceof WorkshopTicket) {
            WorkshopTicket bad =
                (WorkshopTicket) plain;

            System.out.println(bad.getTrack());
        }
    }
}
