package OOPFundamental.class_problems;
class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;
    FeeAccount(String regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = 0;
    }
    void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        } else {
            System.out.println("Payment rejected");
        }
    }
    double getDue() {
        return totalFee - amountPaid;
    }
}
class HostelFeeAccount extends FeeAccount {
    HostelFeeAccount(String regNo, double totalFee) {
        super(regNo, totalFee);
    }
    void payInTwoInstallments(double amount) {
        pay(amount);
        pay(amount);
    }
}
class HostelRoom {
    String roomNo;
    int beds;
    int occupied;
    HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }
    void allot(String name) {
        if (occupied < beds) {
            occupied++;
        }
    }
}
class SrmStudent {
    String name;
    String regNo;
    HostelFeeAccount feeAccount;
    HostelRoom room;
    static int totalStudents = 0;
    SrmStudent(String name, String regNo, double totalFee) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = new HostelFeeAccount(regNo, totalFee);
        totalStudents++;
    }
    String fullStatus() {
        if (room == null) {
            return name + " | Due: Rs " + feeAccount.getDue() + " | Room: unallotted";
        }
        return name + " | Due: Rs " + feeAccount.getDue() + " | Room: " + room.roomNo;
    }
}
public class FeeHostelManagementSystem {
    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        for (HostelRoom room : rooms) {
            if (room.occupied < room.beds) {
                return room;
            }
        }
        return null;
    }
    static void safeAllot(HostelRoom[] rooms, SrmStudent student) {
        HostelRoom room = findAvailableRoom(rooms);
        if (room != null) {
            room.allot(student.name);
            student.room = room;
        }
    }
    public static void main(String[] args) {
        SrmStudent ravi = new SrmStudent("Ravi", "101", 150000);
        SrmStudent anitha = new SrmStudent("Anitha", "102", 180000);
        SrmStudent karthik = new SrmStudent("Karthik", "103", 200000);
        HostelRoom[] rooms = {
            new HostelRoom("C-214", 1, 0),
            new HostelRoom("C-507", 1, 0)
        };
        safeAllot(rooms, ravi);
        safeAllot(rooms, anitha);
        safeAllot(rooms, karthik);
        ravi.feeAccount.pay(10000);
        anitha.feeAccount.pay(-5000);
        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());
        System.out.println("Total students: " + SrmStudent.totalStudents);
    }
}