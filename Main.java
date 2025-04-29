

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        showMainMenu();
    }

    public static void showMainMenu() {
        Scanner sc = new Scanner(System.in);
        int action = 0;
        
            while (true) {
                System.out.println("\n=============================================");
                System.out.println("||           HOTEL MANAGEMENT SYSTEM       ||");
                System.out.println("=============================================");
                System.out.println("|| [1] Manage Room                         ||");
                System.out.println("|| [2] Manage Staff                        ||");
                System.out.println("|| [3] Manage Customer                     ||");
                System.out.println("|| [4] Generate Reports                    ||");
                System.out.println("|| [5] Payment                             ||");
                System.out.println("|| [0] Exit                                ||");
                System.out.println("=============================================");
                System.out.print("Please choose an action: ");

                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    sc.nextLine();
                    if (action < 0 || action > 5) {
                        System.out.println("\n============================================");
                        System.out.println("Please input a correct number.\n");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("\n============================================");
                    System.out.println("Please input a correct number.\n");
                    sc.next();
                }
            }

            switch (action) {
                case 1:
                    clear();
                    ManageRoom.mainPage();
                    break;
                case 2:
                    clear();
                    StaffManagement.mainPage();
                    break;
                case 3:
                    clear();
                    CustomerManagement.mainPage();
                    break;
                case 4:
                    clear();
                    Reports.generateReports();
                    break;
                case 5:
                    clear();
                    System.out.println("Payment");
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                    break;
            }

    }

    public static void clear() {
        System.out.println("\033c");
    }
}
