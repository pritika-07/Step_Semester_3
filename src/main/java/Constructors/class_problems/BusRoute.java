package Constructors.class_problems;
import java.util.Scanner;
public class BusRoute {
    private String routeCode;
    private String routeName;
    private int priority;
    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }
    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 0);
    }
    int compareTo(BusRoute other) {
        if (priority != other.priority)
            return other.priority - priority;
        int code = routeCode.compareToIgnoreCase(other.routeCode);
        if (code != 0)
            return code;
        return routeName.compareToIgnoreCase(other.routeName);
    }
    static BusRoute[] rankRoutes(BusRoute[] routes) {
        BusRoute[] result = routes.clone();
        for (int i = 0; i < result.length - 1; i++) {
            for (int j = i + 1; j < result.length; j++) {
                if (result[i].compareTo(result[j]) > 0) {
                    BusRoute temp = result[i];
                    result[i] = result[j];
                    result[j] = temp;
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of routes: ");
        int n = sc.nextInt();
        sc.nextLine();
        BusRoute[] routes = new BusRoute[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter route code: ");
            String code = sc.nextLine();
            System.out.print("Enter route name: ");
            String name = sc.nextLine();
            System.out.print("Enter priority or -1 for default: ");
            int priority = sc.nextInt();
            sc.nextLine();
            if (priority == -1)
                routes[i] = new BusRoute(code, name);
            else
                routes[i] = new BusRoute(code, name, priority);
        }
        BusRoute[] ranked = rankRoutes(routes);
        System.out.println("Ranked Routes:");
        for (int i = 0; i < ranked.length; i++)
            System.out.println(ranked[i].routeCode + " - " + ranked[i].routeName + " - " + ranked[i].priority);
        sc.close();
    }
}
