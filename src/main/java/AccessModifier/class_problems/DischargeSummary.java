package main.java.AccessModifier.class_problems;
public class DischargeSummary {
    private final String patientId;
    private final String[] medicationCodes;

    private static String systemName;

    static {
        systemName = "MediTrack";
    }
    public DischargeSummary(String patientId,
                             String[] medicationCodes) {

        if (medicationCodes == null) {
            throw new IllegalArgumentException(
                "Medication codes cannot be null"
            );
        }

        String[] copy = new String[medicationCodes.length];

        for (int i = 0; i < medicationCodes.length; i++) {

            String code = medicationCodes[i];

            if (code == null || !code.matches("MED-[A-Z]")) {
                throw new IllegalArgumentException(
                    "Invalid medication code"
                );
            }

            copy[i] = code;
        }

        this.patientId = patientId;
        this.medicationCodes = copy;
    }

    public String[] getMedicationCodes() {

        String[] copy = new String[medicationCodes.length];

        for (int i = 0; i < medicationCodes.length; i++) {
            copy[i] = medicationCodes[i];
        }

        return copy;
    }

    public DischargeSummary withCorrectedMedication(
            int index,
            String newCode) {

        if (index < 0 || index >= medicationCodes.length) {
            throw new IndexOutOfBoundsException();
        }

        if (newCode == null ||
            !newCode.matches("MED-[A-Z]")) {

            throw new IllegalArgumentException(
                "Invalid medication code"
            );
        }

        String[] newCodes = medicationCodes.clone();

        newCodes[index] = newCode;

        return new DischargeSummary(
            patientId,
            newCodes
        );
    }

    public static String processNightlyBatch(
            DischargeSummary[] summaries) {

        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        if (summaries != null) {

            for (DischargeSummary summary : summaries) {

                if (summary == null) {
                    nullSkipped++;
                    continue;
                }

                processed++;

                if (summary instanceof
                    CriticalCareDischargeSummary) {

                    criticalCare++;

                } else {

                    routine++;
                }
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + criticalCare + " critical-care | "
                + routine + " routine";
    }

    public static void main(String[] args) {

        DischargeSummary d =
            new DischargeSummary(
                "MT2026-0142",
                new String[]{"MED-A", "MED-B"}
            );

        String[] codes = d.getMedicationCodes();

        codes[0] = "TAMPERED";

        System.out.println(d.getMedicationCodes()[0]);

        DischargeSummary corrected =
            d.withCorrectedMedication(
                0,
                "MED-C"
            );

        System.out.println(
            corrected.getMedicationCodes()[0]
        );

        DischargeSummary[] batch = {

            new CriticalCareDischargeSummary(
                "MT001",
                new String[]{"MED-X"},
                4
            ),

            null,

            new DischargeSummary(
                "MT002",
                new String[]{"MED-Y"}
            )
        };

        System.out.println(
            processNightlyBatch(batch)
        );

        try {

            new DischargeSummary(
                "MT2026-0142",
                new String[]{"MED-A", "bad"}
            );

        } catch (IllegalArgumentException e) {

            System.out.println("construction rejected");
        }
    }
}


final class CriticalCareDischargeSummary
        extends DischargeSummary {

    private final int icuDays;

    public CriticalCareDischargeSummary(
            String patientId,
            String[] medicationCodes,
            int icuDays) {

        super(patientId, medicationCodes);

        this.icuDays = icuDays;
    }
} 
