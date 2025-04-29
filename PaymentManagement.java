
import java.util.*;

public class PaymentManagement {

    private static RefundTypeManager refundManager = new RefundTypeManager();
    private static final String REFUND_DATA_FILE = "refund.txt"; // Define constant

    public static void mainPage() {
        Scanner sc = new Scanner(System.in);
        int action = -1;

        while (action != 0) {
            clear();
            System.out.println("\n\t\t=====================================================");
            System.out.println("\t\t|\t    HOTEL PAYMENT MANAGEMENT SYSTEM           |");
            System.out.println("\t\t=====================================================");
            System.out.println("\n\t\tPlease select an option:");
            System.out.println("\t\t[1] Payment (Staff)");
            System.out.println("\t\t[2] Refund Management (Admin)");
            System.out.println("\t\t[0] Exit");
            System.out.print("\t\tPlease choose your action (0-2): ");

            try {
                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    sc.nextLine();

                    if (action < 0 || action > 2) {
                        errorMessageNumber();
                    } else {
                        switch (action) {
                            case 1:
                                System.out.println("\n\t\tPayment function not yet implemented.");
                                action = -1;
                                break;
                            case 2:
                                refundManagementPage(sc);
                                action = -1;
                                break;
                            case 0:
                                System.out.println("\nExiting Payment Management System. Goodbye!");
                                break;
                        }
                    }
                } else {
                    errorMessageNumber();
                    sc.next();
                    sc.nextLine();

                }
            } catch (InputMismatchException e) {
                errorMessageNumber();
                sc.nextLine();

            }
        }
        sc.close();
    }

    public static void refundManagementPage(Scanner sc) {
        int choice = -1;

        while (choice != 0) {
            clear();
            System.out.println("\n============================================================");
            System.out.println("||              REFUND MANAGEMENT (Admin)               ||");
            System.out.println("============================================================");

            displayRefundTypesWithCustomersDetailed();

            System.out.println("\n======================= Options ==========================");
            System.out.println("|| [1] Add New Customer Refund Assignment               ||");
            System.out.println("|| [2] Modify Existing Customer Refund Assignment       ||");
            System.out.println("|| [3] Remove Customer Refund Assignment                ||");
            System.out.println("|| [0] Back to Main Menu                                ||");
            System.out.println("============================================================");
            System.out.print("Choose an action (0-3): ");

            try {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {
                        case 1:
                            addRefundAssignment(sc);

                            break;
                        case 2:
                            modifyRefundAssignment(sc);

                            break;
                        case 3:
                            removeRefundAssignment(sc);
                            break;
                        case 0:
                            System.out.println("\nReturning to main menu...");
                            break;
                        default:
                            System.out.println("\nInvalid choice. Please enter a number between 0 and 3.");
                            break;
                    }
                } else {
                    System.out.println("\nInvalid input. Please enter a number.");
                    sc.next();
                    sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input format. Please enter an integer number.");
                sc.nextLine();
            }
        }
    }

    public static void displayRefundTypesWithCustomersDetailed() {
        System.out.println("\n--------------- Current Refund Assignments ---------------");

        List<String> predefinedTypes = refundManager.getPredefinedTypeNames();
        Map<String, Map<String, String>> refundData = refundManager.getRefundData();

        if (predefinedTypes == null || predefinedTypes.isEmpty()) {
            System.out.println("\nStatus: No refund types have been defined or loaded.");
            System.out.println("----------------------------------------------------------");
            return;
        }
        if (refundData == null) {
            System.out.println("\nStatus: Refund data structure not initialized.");
            System.out.println("----------------------------------------------------------");
            return;
        }

        boolean dataFound = false;
        for (int i = 0; i < predefinedTypes.size(); i++) {
            String typeName = predefinedTypes.get(i);
            Map<String, String> customerMap = refundData.getOrDefault(typeName, new HashMap<>());
            System.out.println("\n==========================================================");
            System.out.printf("[%d] %s\n", i + 1, typeName);
            System.out.println("----------------------------------------------------------");

            if (customerMap.isEmpty()) {
                System.out.println("    (No customers assigned to this type)");
            } else {
                dataFound = true;
                List<Map.Entry<String, String>> customerEntries = new ArrayList<>(customerMap.entrySet());

                for (int a = 0; a < customerEntries.size(); a++) {
                    Map.Entry<String, String> entry = customerEntries.get(a);
                    String customerId = entry.getKey();
                    String roomId = entry.getValue();
                    int customerIndex = a + 1;

                    System.out.printf("    %d. %s [Room ID: %s]\n",
                            customerIndex,
                            customerId,
                            roomId);
                }
            }
        }
        System.out.println("\n==========================================================");

        if (!dataFound && !predefinedTypes.isEmpty()) {
            System.out.println("\nStatus: No customers assigned to any refund type yet.");
            System.out.println("----------------------------------------------------------");
        }
    }

    private static void addRefundAssignment(Scanner sc) {
        clear();
        System.out.println("\n============================================================");
        System.out.println("||             Add New Refund Assignment                ||");
        System.out.println("============================================================");

        List<String> types = displayNumberedTypes();
        if (types.isEmpty()) {
            return;
        }

        int typeChoice = selectTypeIndex(sc, types.size(), "Select the number for the refund type to assign");
        if (typeChoice == -1) {
            return;
        }
        String selectedTypeName = types.get(typeChoice);

        String customerId = promptForInput(sc, "Enter Customer ID:");
        if (customerId == null) {
            return;
        }

        String roomId = promptForInput(sc, "Enter Room ID for customer '" + customerId + "':");
        if (roomId == null) {
            return;
        }

        System.out.println("\n--- Please Confirm Details ---");
        System.out.println("Refund Type: " + selectedTypeName);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Room ID    : " + roomId);
        System.out.println("------------------------------");

        if (confirmAction(sc, "Add this refund assignment? (y/n):")) {
            boolean success = refundManager.addCustomerToRefundType(selectedTypeName, customerId, roomId);
            if (success) {
                System.out.println("\nSuccessfully added assignment and saved data.");
            } else {
                System.out.println("\nFailed to add assignment. An error occurred.");
            }
        } else {
            System.out.println("\nAdd operation cancelled.");
        }
    }

    private static void modifyRefundAssignment(Scanner sc) {
        clear();
        System.out.println("\n============================================================");
        System.out.println("||           Modify Existing Refund Assignment          ||");
        System.out.println("============================================================");

        List<String> types = displayNumberedTypes();
        if (types.isEmpty()) {
            return;
        }

        int typeChoice = selectTypeIndex(sc, types.size(), "Select the number for the refund type containing the customer");
        if (typeChoice == -1) {
            return;
        }
        String selectedTypeName = types.get(typeChoice);

        Map<String, String> customerMap = refundManager.getCustomersForType(selectedTypeName);
        if (customerMap == null || customerMap.isEmpty()) {
            System.out.println("\nNo customers are currently assigned to type '" + selectedTypeName + "'.");
            return;
        }

        System.out.println("\nCustomers assigned to '" + selectedTypeName + "':");
        List<String> customerIdList = new ArrayList<>(customerMap.keySet());
        for (int i = 0; i < customerIdList.size(); i++) {
            String custId = customerIdList.get(i);
            System.out.printf("  [%d] %s [Current Room ID: %s]\n", i + 1, custId, customerMap.get(custId));
        }
        System.out.println("----------------------------------------------------------");

        int customerChoice = -1;
        while (customerChoice < 1 || customerChoice > customerIdList.size()) {
            System.out.print("Select the number of the customer entry to modify (1-" + customerIdList.size() + "): ");
            try {
                if (sc.hasNextInt()) {
                    customerChoice = sc.nextInt();
                    sc.nextLine();
                    if (customerChoice < 1 || customerChoice > customerIdList.size()) {
                        System.out.println("Invalid selection.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.next();
                    sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input format. Please enter a number.");
                sc.nextLine();
            }
        }

        String customerIdToModify = customerIdList.get(customerChoice - 1);
        String currentRoomId = customerMap.get(customerIdToModify);

        System.out.println("\nModifying entry for Customer ID: " + customerIdToModify);
        String newRoomId = promptForInput(sc, "Enter the NEW Room ID (current: " + currentRoomId + "):");
        if (newRoomId == null) {
            System.out.println("\nModification cancelled (empty Room ID entered).");
            return;
        }

        System.out.println("\n=============== Please Confirm Modification ===============");
        System.out.println("Refund Type : " + selectedTypeName);
        System.out.println("Customer ID : " + customerIdToModify);
        System.out.println("Current Room: " + currentRoomId);
        System.out.println("NEW Room ID : " + newRoomId);
        System.out.println("============================================================");

        if (confirmAction(sc, "Update Room ID for this assignment? (y/n):")) {
            boolean success = refundManager.addCustomerToRefundType(selectedTypeName, customerIdToModify, newRoomId);
            if (success) {
                System.out.println("\nSuccessfully updated assignment and saved data."); // Updated message
            } else {
                System.out.println("\nFailed to update assignment. An error occurred.");
            }
        } else {
            System.out.println("\nModify operation cancelled.");
        }
    }

    private static void removeRefundAssignment(Scanner sc) {
        clear();
        System.out.println("\n============================================================");
        System.out.println("||            Remove Customer Refund Assignment           ||");
        System.out.println("============================================================");

        List<String> types = displayNumberedTypes();
        if (types.isEmpty()) {
            return;
        }

        int typeChoice = selectTypeIndex(sc, types.size(), "Select the number for the refund type containing the customer");
        if (typeChoice == -1) {
            return;
        }
        String selectedTypeName = types.get(typeChoice);

        Map<String, String> customerMap = refundManager.getCustomersForType(selectedTypeName);
        if (customerMap == null || customerMap.isEmpty()) {
            System.out.println("\nNo customers are currently assigned to type '" + selectedTypeName + "'.");
            return;
        }

        System.out.println("\nCustomers assigned to '" + selectedTypeName + "':");
        List<String> customerIdList = new ArrayList<>(customerMap.keySet());
        for (int i = 0; i < customerIdList.size(); i++) {
            String custId = customerIdList.get(i);
            System.out.printf("  [%d] %s [Room ID: %s]\n", i + 1, custId, customerMap.get(custId));
        }
        System.out.println("----------------------------------------------------------");

        int customerChoice = -1;
        while (customerChoice < 1 || customerChoice > customerIdList.size()) {
            System.out.print("Select the number of the customer entry to REMOVE (1-" + customerIdList.size() + "): ");
            try {
                if (sc.hasNextInt()) {
                    customerChoice = sc.nextInt();
                    sc.nextLine();
                    if (customerChoice < 1 || customerChoice > customerIdList.size()) {
                        System.out.println("Invalid selection.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.next();
                    sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input format. Please enter a number.");
                sc.nextLine();
            }
        }

        String customerIdToRemove = customerIdList.get(customerChoice - 1);
        String roomId = customerMap.get(customerIdToRemove);

        System.out.println("\n--- Please Confirm Removal ---");
        System.out.println("Refund Type : " + selectedTypeName);
        System.out.println("Customer ID : " + customerIdToRemove);
        System.out.println("Room ID     : " + roomId);
        System.out.println("------------------------------");

        if (confirmAction(sc, "REMOVE this refund assignment permanently? (y/n):")) {
            boolean success = refundManager.removeCustomerFromRefundType(selectedTypeName, customerIdToRemove);
            if (success) {
                System.out.println("\nSuccessfully removed assignment and saved data.");
            } else {
                System.out.println("\nFailed to remove assignment. Customer might not exist in this type.");
            }
        } else {
            System.out.println("\nRemove operation cancelled.");
        }
    }

    private static List<String> displayNumberedTypes() {
        System.out.println("Available Refund Types:");
        List<String> types = refundManager.getPredefinedTypeNames();
        if (types == null || types.isEmpty()) {
            System.out.println("\nError: No refund types have been defined or loaded.");
            System.out.println("Please configure refund types first.");
            System.out.println("------------------------------------------------------------");
            return new ArrayList<>();
        }

        for (int i = 0; i < types.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, types.get(i));
        }
        System.out.println("------------------------------------------------------------");
        return types;
    }

    private static int selectTypeIndex(Scanner sc, int maxIndex, String prompt) {
        int choice = -1;
        while (choice < 1 || choice > maxIndex) {
            System.out.print(prompt + " (1-" + maxIndex + "): ");
            try {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice < 1 || choice > maxIndex) {
                        System.out.println("Invalid selection.");
                    } else {
                        return choice - 1;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.next();
                    sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input format. Please enter a number.");
                sc.nextLine();
            }
        }
        return -1;
    }

    private static String promptForInput(Scanner sc, String prompt) {
        String input = "";
        while (input.trim().isEmpty()) {
            System.out.print(prompt + " ");
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
        return input;
    }

    private static boolean confirmAction(Scanner sc, String prompt) {
        String confirm = "";
        while (!confirm.equals("y") && !confirm.equals("n")) {
            System.out.print(prompt);
            confirm = sc.nextLine().trim().toLowerCase();
            if (!confirm.equals("y") && !confirm.equals("n")) {
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            }
        }
        return confirm.equals("y");
    }

    public static void errorMessageNumber() {
        System.out.print("\n===================================");
        System.out.println("\nError. Invalid input. Please input a valid number.");
        System.out.print("===================================\n");
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
    }
}
