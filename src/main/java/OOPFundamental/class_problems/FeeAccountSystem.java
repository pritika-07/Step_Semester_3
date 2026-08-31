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
class ScholarshipFeeAccount extends FeeAccount {
    private double scholarshipPercent;
    ScholarshipFeeAccount(String regNo, double totalFee, double scholarshipPercent) {
        super(regNo, totalFee);
        this.scholarshipPercent = scholarshipPercent;
    }
    double effectiveDue() {
        return getDue() - (getDue() * scholarshipPercent / 100);
    }
}
public class FeeAccountSystem {
    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("101", 150000);
        plain.pay(150000);
        HostelFeeAccount hostel = new HostelFeeAccount("102", 200000);
        hostel.payInTwoInstallments(30000);
        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("103", 180000, 20);
        FeeAccount[] accounts = {plain, hostel, scholarship};
        for (FeeAccount account : accounts) {
            if (account instanceof HostelFeeAccount) {
                HostelFeeAccount hostelAccount = (HostelFeeAccount) account;
                System.out.println("Hostel account due: Rs " + hostelAccount.getDue());
            } else if (account instanceof ScholarshipFeeAccount) {
                ScholarshipFeeAccount scholarshipAccount = (ScholarshipFeeAccount) account;
                System.out.println("Scholarship account effective due: Rs " + scholarshipAccount.effectiveDue());
            } else {
                System.out.println("Plain account due: Rs " + account.getDue());
            }
        }
    }
} 