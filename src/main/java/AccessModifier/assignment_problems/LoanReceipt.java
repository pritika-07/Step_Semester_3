package main.java.AccessModifier.assignment_problems;

public class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    private static String systemName;

    static {
        systemName = "PageTurner Library";
    }

    public LoanReceipt(String memberId,
                       String[] bookIds) {

        if (bookIds == null) {
            throw new IllegalArgumentException(
                "Book IDs cannot be null"
            );
        }

        String[] copy = new String[bookIds.length];

        for (int i = 0; i < bookIds.length; i++) {

            String id = bookIds[i];

            if (id == null || !id.matches("BK-\\d{3}")) {
                throw new IllegalArgumentException(
                    "Invalid book ID"
                );
            }

            copy[i] = id;
        }

        this.memberId = memberId;
        this.bookIds = copy;
    }

    public String[] getBookIds() {

        String[] copy = new String[bookIds.length];

        for (int i = 0; i < bookIds.length; i++) {
            copy[i] = bookIds[i];
        }

        return copy;
    }

    public LoanReceipt withCorrectedBookId(
            int index,
            String newId) {

        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException();
        }

        if (newId == null ||
            !newId.matches("BK-\\d{3}")) {
            throw new IllegalArgumentException(
                "Invalid book ID"
            );
        }

        String[] newBookIds = bookIds.clone();
        newBookIds[index] = newId;

        return new LoanReceipt(
            memberId,
            newBookIds
        );
    }

    public static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts != null) {

            for (LoanReceipt receipt : receipts) {

                if (receipt == null) {
                    nullSkipped++;
                    continue;
                }

                processed++;

                if (receipt instanceof ReferenceOnlyLoanReceipt) {
                    referenceOnly++;
                } else {
                    regular++;
                }
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }

    public static void main(String[] args) {

        LoanReceipt r =
            new LoanReceipt(
                "LIB-8841",
                new String[]{"BK-100", "BK-101"}
            );

        String[] ids = r.getBookIds();

        ids[0] = "HACKED";

        System.out.println(
            r.getBookIds()[0]
        );

        LoanReceipt corrected =
            r.withCorrectedBookId(
                0,
                "BK-999"
            );

        System.out.println(
            corrected.getBookIds()[0]
        );

        LoanReceipt[] receipts = {

            new ReferenceOnlyLoanReceipt(
                "LIB-001",
                new String[]{"BK-200"},
                "Reading Room 3"
            ),

            null,

            new LoanReceipt(
                "LIB-002",
                new String[]{"BK-201"}
            )
        };

        System.out.println(
            processNightlyCirculation(receipts)
        );

        try {

            new LoanReceipt(
                "LIB-8841",
                new String[]{"BK-100", "bad"}
            );

        } catch (IllegalArgumentException e) {

            System.out.println("construction rejected");
        }
    }
}


final class ReferenceOnlyLoanReceipt
        extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);

        this.roomNumber = roomNumber;
    }
}