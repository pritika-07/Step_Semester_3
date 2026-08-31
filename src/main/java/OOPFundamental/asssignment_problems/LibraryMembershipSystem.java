package OOPFundamental.asssignment_problems;
class BrokenLibraryMember {
    static String name;
    static String memberId;
    static int booksIssued;
    BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }
}
class LibraryMember {
    String name;
    String memberId;
    int booksIssued;
    static String libraryName = "SRM Library";
    static int memberCount = 0;
    LibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
        memberCount++;
        this.memberId = "LM-" + (1000 + memberCount);
    }
    void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }
    static void printTotalMembers() {
        System.out.println("Total members: " + memberCount);
    }
}
public class LibraryMembershipSystem {
    public static void main(String[] args) {
        new BrokenLibraryMember("Aditi", "LM-1001", 2);
        new BrokenLibraryMember("Rohan", "LM-1002", 3);
        System.out.println("Broken version:");
        System.out.println(BrokenLibraryMember.name);
        System.out.println(BrokenLibraryMember.name);
        // name, memberId and booksIssued are different for each member, so making them static causes one member's data to overwrite another's data.
        LibraryMember member1 = new LibraryMember("Aditi", 2);
        LibraryMember member2 = new LibraryMember("Rohan", 3);
        System.out.println("Fixed version:");
        member1.printMemberCard();
        member2.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}