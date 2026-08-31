package OOPFundamental.asssignment_problems;
class Employee {
    private String empId;
    private String empName;
    private double salary;
    Employee(String empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }
    double getSalary() {
        return salary;
    }
}
class ManagerEmployee extends Employee {
    private double teamBonus;
    ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }
    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}
class ParkingSlot {
    String slotNo;
    int capacity;
    int occupiedCount;
    ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }
    void allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
        }
    }
}
class CompanyEmployeeRecord {
    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;
    static int totalRecords = 0;
    CompanyEmployeeRecord(String name, String empId, Employee employee) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        totalRecords++;
    }
    String fullProfile() {
        double pay;
        if (employee instanceof ManagerEmployee) {
            ManagerEmployee manager = (ManagerEmployee) employee;
            pay = manager.effectiveSalary();
        } else {
            pay = employee.getSalary();
        }
        if (slot == null) {
            return name + " | Pay: Rs " + pay + " | Slot: no parking assigned";
        }
        return name + " | Pay: Rs " + pay + " | Slot: " + slot.slotNo;
    }
}
public class HRParkingSystem {
    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }
        return null;
    }
    static void safeAllot(ParkingSlot[] slots, CompanyEmployeeRecord record) {
        ParkingSlot slot = findAvailableSlot(slots);
        if (slot != null) {
            slot.allot(record.empId);
            record.slot = slot;
        }
    }
    public static void main(String[] args) {
        ManagerEmployee divyaEmployee = new ManagerEmployee("E101", "Divya", 70000, 8000);
        Employee karanEmployee = new Employee("E102", "Karan", 40000);
        Employee meeraEmployee = new Employee("E103", "Meera", 10000);
        CompanyEmployeeRecord divya = new CompanyEmployeeRecord("Divya", "E101", divyaEmployee);
        CompanyEmployeeRecord karan = new CompanyEmployeeRecord("Karan", "E102", karanEmployee);
        CompanyEmployeeRecord meera = new CompanyEmployeeRecord("Meera", "E103", meeraEmployee);
        ParkingSlot[] slots = {
            new ParkingSlot("A1", 1, 0),
            new ParkingSlot("A2", 1, 0)
        };
        safeAllot(slots, divya);
        safeAllot(slots, karan);
        System.out.println(divya.fullProfile());
        System.out.println(karan.fullProfile());
        System.out.println(meera.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
} 
