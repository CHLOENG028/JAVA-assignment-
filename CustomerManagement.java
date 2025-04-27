
import java.io.*;
import java.util.*;

public class CustomerManagement {

    public static ArrayList<Customer> customers = new ArrayList<>();

    public static void mainPageCustomer() {
        customers = readCustomerFile();
        Scanner sc = new Scanner(System.in);
        System.out.println("Manage Customer System");
        System.out.println("=====================");

        while (true) {
            System.out.println("Which action you preferred to do?\n[1] Show All Customer\n[2] Add Customer\n[3] Edit Customer\n[4] Remove Customer\n[5] Exit");
            System.out.print("Enter your action (1-5): ");
            int action = 0;

            if (sc.hasNextInt()) {
                action = sc.nextInt();
                sc.nextLine();

                if (action == 5) {
                    System.out.println("Returning to previous menu...");
                    return;
                }

                if (action < 1 || action > 5) {
                    System.out.println("=================");
                    System.out.println("Please input a correct integer (1-5)");
                    continue;
                }
            } else {
                errorMessageNumber();
                sc.next();
                sc.nextLine();
                continue;
            }

            boolean performAction = false;

            String confirmationPrompt = "";
            switch (action) { // Determine prompt based on action
                case 1:
                    confirmationPrompt = "Confirm to show all customer? (yes/no): ";
                    break;
                case 2:
                    confirmationPrompt = "Confirm to add new customer? (yes/no): ";
                    break;
                case 3:
                    confirmationPrompt = "Confirm to edit an existing customer? (yes/no): ";
                    break;
                case 4:
                    confirmationPrompt = "Confirm to delete an existing customer? (yes/no): ";
                    break;
            }

            while (true) {
                System.out.print(confirmationPrompt);
                String confirmInput = sc.nextLine();

                if (confirmInput.equalsIgnoreCase("yes")) {
                    performAction = true;
                    break;
                } else if (confirmInput.equalsIgnoreCase("no")) {
                    performAction = false;
                    System.out.println("Action cancelled.");
                    break;
                } else {
                    errorMessageWord();
                }
            }

            if (performAction) {
                switch (action) {
                    case 1:
                        showCustomer();
                        break;
                    case 2:
                        addNewCustomer(customers);
                        break;
                    case 3:
                        editCustomer(customers);
                        break;
                    case 4:
                        deleteCustomer(customers);
                        break;
                }
                System.out.println("\nAction completed. Press [ENTER] to return to staff menu...");
                sc.nextLine();
            }
        }
    }

    public static void addNewCustomer(ArrayList<Customer> currentCustomerList) {
        Scanner sc = new Scanner(System.in);
        boolean detailsConfirm = false;
        String customerId = "";
        String username, fullName, password, phoneNo, email = "";

        do {
            System.out.println("\n--- Add New Customer ---");
            System.out.println("Customer ID will be auto-generated.");
            System.out.println("Enter the details below:");

            while (true) {
                System.out.print("Customer Username: ");
                username = sc.nextLine().trim();
                if (username.isEmpty()) {
                    System.out.println("Username cannot be empty. Please try again.");
                } else {
                    boolean exists = false;
                    for(Customer cust : currentCustomerList) {
                        if(cust.getUsername().equalsIgnoreCase(username)) {
                            exists = true;
                            break;
                        }
                    }
                    if (exists) {
                         System.out.println("Username '" + username + "' already exists. Please choose another.");
                    } else {
                        break;
                    }
                }
            }

            while (true) {
                System.out.print("Customer Full Name: ");
                fullName = sc.nextLine().trim();
                if (fullName.isEmpty()) {
                    System.out.println("Full Name cannot be empty. Please try again.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("Password: ");
                password = sc.nextLine();
                if (password.isEmpty()) {
                     System.out.println("Password cannot be empty. Please try again.");
                } else if (password.length() < 6) {
                     System.out.println("Password must be at least 6 characters long.");
                }
                 else {
                    break;
                }
            }

            while (true) {
                System.out.print("Phone Number (e.g., 012-3456789 or 011-12345678): ");
                phoneNo = sc.nextLine().trim();
                if (phoneNo.isEmpty()) {
                    System.out.println("Phone number cannot be empty. Please try again.");
                 } else if (!phoneNo.matches("^\\d{3}-\\d{7,8}$")) {
                     System.out.println("Invalid phone number format. Expected: 01X-XXXXXXX or 01X-XXXXXXXX");
                }
                 else {
                     boolean exists = false;
                    for(Customer cust : currentCustomerList) {
                        if(cust.getPhoneNo().equals(phoneNo)) {
                            exists = true;
                            break;
                        }
                    }
                    if (exists) {
                         System.out.println("Phone number '" + phoneNo + "' is already registered. Please use a different one.");
                    } else {
                       break;
                    }
                }
            }

            while (true) {
                System.out.print("Email (e.g., user@example.com): ");
                email = sc.nextLine().trim();
                if (email.isEmpty()) {
                    System.out.println("Email cannot be empty. Please try again.");
                } else if (!email.contains("@") || !email.contains(".")) {
                    System.out.println("Invalid email format. Please include '@' and '.'");
                }
                else {
                     boolean exists = false;
                    for(Customer cust : currentCustomerList) {
                        if(cust.getEmail().equalsIgnoreCase(email)) {
                            exists = true;
                            break;
                        }
                    }
                     if (exists) {
                         System.out.println("Email '" + email + "' is already registered. Please use a different one.");
                    } else {
                       break;
                    }
                }
            }

            int highestCustNum = 0;
            String prefix = "CUST";

            for (Customer customer : currentCustomerList) {
                if (customer.getId() != null && customer.getId().startsWith(prefix)) {
                    try {
                        String numberPart = customer.getId().substring(prefix.length());
                        int currentIdNum = Integer.parseInt(numberPart);
                        if (currentIdNum > highestCustNum) {
                            highestCustNum = currentIdNum;
                        }
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.err.println("Warning: Skipping customer with unexpected ID format: " + customer.getId());
                    }
                }
            }
            int nextCustIdNum = highestCustNum + 1;
            customerId = prefix + nextCustIdNum;

            System.out.println("\n=================================");
            System.out.println("Please confirm the details below:");
            System.out.println("=================================");
            System.out.println("Customer ID: " + customerId);
            System.out.println("Username: " + username);
            System.out.println("Full Name: " + fullName);
            System.out.println("Password: [Set]");
            System.out.println("Phone Number: " + phoneNo);
            System.out.println("Email: " + email);
            System.out.println("=================================");

            while (true) {
                System.out.print("Do you confirm the details above are correct? (yes/no): ");
                String confirmation = sc.nextLine().trim();

                if (confirmation.equalsIgnoreCase("yes")) {
                    detailsConfirm = true;
                    break;
                } else if (confirmation.equalsIgnoreCase("no")) {
                    System.out.println("\nDetails not confirmed. Restarting customer entry.");
                    detailsConfirm = false;
                    break;
                } else {
                    errorMessageWord();
                }
            }

        } while (!detailsConfirm);

        Customer newCustomer = new Customer(customerId, username, fullName, password, phoneNo, email);
        currentCustomerList.add(newCustomer);

        try (FileWriter myWriter = new FileWriter("customer.txt", true)) {
            myWriter.write(customerId + "|" + username + "|" + fullName + "|" + password + "|" + phoneNo + "|" + email + System.lineSeparator());
             System.out.println("\nCustomer details saved to customer.txt successfully!");
        } catch (IOException e) {
            System.out.println("\nError: Failed to write customer details to file.");
        }

        System.out.println("Customer [" + fullName + "] added successfully with ID: " + customerId);
        System.out.println("Press [ENTER] to continue...");
        sc.nextLine();
        mainPageCustomer();
    }

    public static void showCustomer() {
        Scanner sc = new Scanner(System.in);
        int search = 0;

        Map<Integer, String> choicesString = new HashMap<>();
        choicesString.put(1, "Customer ID");
        choicesString.put(2, "Customer Username");
        choicesString.put(3, "Customer Fullname");
        choicesString.put(4, "Password");
        choicesString.put(5, "Phone Number");
        choicesString.put(6, "Email");
        choicesString.put(7, "Exit");

        while (true) {
            System.out.println("What kind of information you want to type in order to find the customer(s) info?\n[1] Customer ID\n[2] Customer Username \n[3] Customer Fullname\n[4] Customer Password\n[5] Customer Phone Number\n[6] Customer Email\n[7] Exit");
            System.out.print("Please choose your action: ");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();
                if (choice < 1 || choice > 5) {
                    System.out.println("Only number between (1-7) is allowed. Please retry.");
                } else {
                    if (choice == 5) {
                        while (true) {
                            System.out.print("Do you confirm to exit? (yes/no)");
                            String confirmation = sc.nextLine().trim();
                            if (confirmation.equalsIgnoreCase("yes")) {
                                System.out.println("Exiting..");
                                return;
                            } else if (confirmation.equalsIgnoreCase("no")) {
                                System.out.println("Exit cancelled.");
                                break;
                            } else {
                                errorMessageWord();
                            }
                        }

                    } else {
                        String chosenString = choicesString.getOrDefault(choice, "Invalid");
                        System.out.print("Are you confirm to choose [" + choice + "] " + chosenString + " as your action? (yes/no): ");
                        String confirmation = sc.nextLine();
                        if (confirmation.equalsIgnoreCase("yes")) {
                            search = choice;
                            break;
                        } else {
                            System.out.print("===================================\n");
                            System.out.print("Please input a correct number.(1-8)\n");
                        }
                    }
                }
            } else {
                errorMessageNumber();
                sc.next();
                sc.nextLine();
            }
        }

        switch (search) {
            case 1:
                System.out.print("Enter the Customer ID: ");
                String custID = sc.nextLine();
                ArrayList<Customer> byId = searchCustomerAttributes(customers, search, custID);
                if (!byId.isEmpty()) {
                    showCustomer(byId);
                }
                break;
            case 2:
                System.out.print("Enter the Customer Username: ");
                String customerUsername = sc.nextLine();
                ArrayList<Customer> byUsername = searchCustomerAttributes(customers, search, customerUsername);
                if (!byUsername.isEmpty()) {
                    showCustomer(byUsername);
                }
                break;
            case 3:
                System.out.print("Enter the Customer Fullname: ");
                String customerFullname = sc.nextLine();
                ArrayList<Customer> byFullname = searchCustomerAttributes(customers, search, customerFullname);
                if (!byFullname.isEmpty()) {
                    showCustomer(byFullname);
                }
                break;
            case 4:
                System.out.print("Enter the Customer Password: ");
                String customerPassword = sc.nextLine();
                ArrayList<Customer> byPassword = searchCustomerAttributes(customers, search, customerPassword);
                if (!byPassword.isEmpty()) {
                    showStaff(byPassword);
                }
                break;
            case 5:
                System.out.print("Enter the Customer Phone Number: ");
                String customerPhone = sc.nextLine();
                ArrayList<Customer> byPhone = searchCustomerAttributes(customers, search, customerPhone);
                if (!byPhone.isEmpty()) {
                    showStaff(byPhone);
                }
                break;
            case 6:
                System.out.print("Enter the Customer Email: ");
                String customerEmail = sc.nextLine();
                ArrayList<Customer> byEmail = searchCustomerAttributes(customers, search, customerEmail);
                if (!byEmail.isEmpty()) {
                    showStaff(byEmail);
                }
                break;
            case 7:
                mainPageCustomer();
                break;
        }
    }

    public static void deleteCustomer(ArrayList<Customer> currentCustomer) {
        String filename = "customer.txt";

        if (currentCustomer == null) {
            System.err.println("Error: Cannot delete from a null list.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the customer phone number that you want to delete: ");
        String custPhone = sc.nextLine().trim();

        boolean custExists = false;
        for (Customer customer : currentCustomer) {
            if (customer != null && customer.getPhoneNo() != null && customer.getPhoneNo().equalsIgnoreCase(custPhone)) {
                custExists = true;
                displayCustomerDetails(customer);
                break;
            }
        }

        if (!custExists) {
            System.out.println("\nCustomer with Phone Number '" + custPhone + "' not found in the list. No deletion performed.");
            return;
        }

        while (true) {
            System.out.print("Customer found. Confirm to delete Customer with Phone Number '" + custPhone + "'? (yes/no): ");
            String deleteConfirm = sc.nextLine().trim();

            if (deleteConfirm.equalsIgnoreCase("yes")) {
                boolean removed = currentCustomer.removeIf(customer
                        -> customer != null && customer.getPhoneNo() != null && customer.getPhoneNo().equalsIgnoreCase(custPhone)
                );//check one by one if it is null

                if (removed) {
                    System.out.println("\nCustomer with Phone Number '" + custPhone + "' has been removed from the list.");
                    writeCustomerFile(currentCustomer, filename);
                } else {
                    System.out.println("\nError: Customer with Phone Number '" + custPhone + "' was found initially but could not be removed now.");
                }
                break;

            } else if (deleteConfirm.equalsIgnoreCase("no")) {
                System.out.println("\nDeletion cancelled for Customer with Phone Number'" + custPhone + "'.");
                break;

            } else {
                errorMessageWord();
            }
        }
    }

    public static void editCustomer(ArrayList<Customer> customers) {
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        while (true) {
            int update;
            String id = "";
            System.out.print("Enter Phone Number/Email to edit(Final will display in phone number): ");
            String input = sc.nextLine();
            for (Customer customer : customers) {
                if (customer.getEmail().equalsIgnoreCase(input)) {
                    found = true;
                    System.out.println("Customer found: ");
                    displayCustomerDetails(customer);
                    editCustomerDetails(customer, input);
                    break;

                } else if (customer.getPhoneNo().equalsIgnoreCase(input)) {
                    found = true;
                    System.out.println("Customer found: ");
                    displayCustomerDetails(customer);
                    input = customer.getPhoneNo();
                    editCustomerDetails(customer, input);
                    break;
                }
            }
            break;
        }
        System.out.println("Press [ENTER] to continue...");
        sc.nextLine();
        mainPageCustomer();
    }

    public static void editCustomerDetails(Customer customer, String input) {
        Scanner sc = new Scanner(System.in);
        boolean continueEditing = true;
        while (continueEditing) {

            System.out.println("What do you want to edit? (Enter numbers separated by commas, e.g., 1,5)");
            System.out.println("[1] User Name");
            System.out.println("[2] Full Name");
            System.out.println("[3] Password");
            System.out.println("[4] Phone Number");
            System.out.println("[5] Phone Number");
            System.out.println("[6] Finish Editing This Staff");
            System.out.print("Enter your choice(s): ");
            String editChoicesInput = sc.nextLine().trim();

            if (editChoicesInput.isEmpty()) {
                System.out.println("No choice entered. Please select fields to edit or choose 6 to finish.");
                continue;
            }

            if (editChoicesInput.contains("6")) {
                System.out.println("Finished editing Staff ID: " + customer.getId());
                continueEditing = false;
                break;
            }

            String[] options = editChoicesInput.split(",");
            boolean changesMadeThisRound = false;

            for (String option : options) {
                String trimmedOption = option.trim();
                switch (trimmedOption) {
                    case "1":
                        System.out.print("Enter the new Username: ");
                        String newUsername = sc.nextLine().trim();
                        if (!newUsername.isEmpty()) {
                            customer.setUsername(newUsername);
                            updateCustomerFile(customer.getUsername(), 1, newUsername);
                            System.out.println("-> Username updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("Username cannot be empty. Update skipped.");
                        }
                        break;
                    case "2":
                        System.out.print("Enter the new Fullname: ");
                        String newFullname = sc.nextLine().trim();
                        if (!newFullname.isEmpty()) {
                            customer.setFullName(newFullname);
                            updateCustomerFile(customer.getFullName(), 2, newFullname);
                            System.out.println("-> Fullname updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("Fullname cannot be empty. Update skipped.");
                        }
                        break;
                    case "3":
                        System.out.print("Enter the new Password: ");
                        String newPassword = sc.nextLine().trim();
                        if (!newPassword.isEmpty()) {
                            customer.setUsername(newPassword);
                            updateCustomerFile(customer.getId(), 3, newPassword);
                            System.out.println("-> Password updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("Password cannot be empty. Update skipped.");
                        }
                        break;

                    case "4":
                        System.out.print("Enter the new Phone Number: ");
                        String newPhone = sc.nextLine().trim();
                        if (!newPhone.isEmpty()) {
                            customer.setPhoneNo(newPhone);
                            updateCustomerFile(customer.getId(), 4, newPhone);
                            System.out.println("-> Phone Number updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("Phone Number cannot be empty. Update skipped.");
                        }
                        break;

                    case "5":
                        System.out.print("Enter the new Email: ");
                        String newEmail = sc.nextLine().trim();
                        if (!newEmail.isEmpty()) {
                            customer.setEmail(newEmail);
                            updateCustomerFile(customer.getId(), 5, newEmail);
                            System.out.println("-> Email updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("Email cannot be empty. Update skipped.");
                        }
                        break;

                    case "6":
                        continueEditing = false;
                        break;

                    default:
                        System.out.println("Invalid choice '" + trimmedOption + "'. Skipping.");
                        break;
                }

                if (!continueEditing) {
                    break;
                }

            }

            if (changesMadeThisRound) {
                System.out.println("\nCurrent details for Customer Phone Number: " + customer.getPhoneNo());
                displayCustomerDetails(customer);
            }

            if (!continueEditing) {
                break;
            }

            System.out.print("\nEdit more details for this customer? (yes/no): ");
            String more = sc.nextLine().trim();
            if (!more.equalsIgnoreCase("yes")) {
                continueEditing = false;
                System.out.println("Finished editing Customer Phone Number: " + customer.getPhoneNo());
            }

        }
    }

    public static void showCustomer(ArrayList<Customer> customers) {
        for (int i = 1; i < customers.size(); i++) {
            Customer currentCustomer = customers.get(i);
            System.out.println((i) + "." + currentCustomer.getUsername() + "(" + currentCustomer.getFullName() + ")");
        }
        
        for (Customer customer:customers){
            displayCustomerDetails(customer);
        }
    }

    public static void chooseCustomer(ArrayList<Customer> customer) {
        Scanner sc = new Scanner(System.in);
        String choose;
        int customerSelected;
        do {
            showStaff(customer);
            System.out.print("Choose the Customer you want(e.g. 1): ");
            choose = sc.nextLine().trim();
            if (Integer.parseInt(choose) > 0 && Integer.parseInt(choose) < customer.size()) {
                customerSelected = (Integer.parseInt(choose) - 1);
                displayCustomerDetails(customer.get(customerSelected));
                break;
            }
        } while (Integer.parseInt(choose) < 1 || Integer.parseInt(choose) > customer.size());
    }

    public static ArrayList<Customer> searchCustomerAttributes(ArrayList<Customer> customers, int option, String value) {
        ArrayList<Customer> matchedCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            boolean match = false;

            switch (option) {
                case 1:
                    match = customer.getId().equalsIgnoreCase(value);
                    break;
                case 2:
                    match = customer.getUsername().equalsIgnoreCase(value);
                    break;
                case 3:
                    match = customer.getFullName().equalsIgnoreCase(value);
                    break;
                case 4:
                    match = customer.getPassword().equalsIgnoreCase(value);
                    break;
                case 5:
                    match = customer.getPhoneNo().equalsIgnoreCase(value);
                    break;
                case 6:
                    match = customer.getEmail().equalsIgnoreCase(value);
                    break;
            }

            if (match) {
                matchedCustomers.add(customer);
            }
        }
        if (matchedCustomers.isEmpty()) {
            System.out.println("No matching customers found.");
        } else {
            System.out.println("Matched customers found.");

        }
        return matchedCustomers;
    }

    public static void displayCustomerDetails(Customer customer) {
        System.out.println("===========================");
        System.out.println("Customer ID: " + customer.getId());
        System.out.println("Username: " + customer.getUsername());
        System.out.println("Fullname: " + customer.getFullName());
        System.out.println("Password: " + customer.getPassword());
        System.out.println("Phone Number: " + customer.getPhoneNo());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("===========================");
    }

    public static void showStaff(ArrayList<Customer> customers) {
        for (int i = 1; i < customers.size(); i++) {
            Customer currentCustomer = customers.get(i);
            System.out.println((i) + "." + currentCustomer.getUsername() + "(" + currentCustomer.getFullName() + ")");
        }
    }

    public static ArrayList<Customer> readCustomerFile() {
        File customerFile = new File("customer.txt");
        ArrayList<Customer> customers = new ArrayList<>(); // Initialize here
    
        if (!customerFile.exists()) {
            try {
                if (customerFile.createNewFile()) {
                    System.out.println("File created successfully. Please add customer details via 'Add Customer'");
                } else {
                    System.out.println("Failed to create file.");
                    return customers; // Return empty list on failure
                }
            } catch (IOException ioEx) {
                System.err.println("Error: Couldn't create file 'customer.txt': " + ioEx.getMessage());
                return customers; // Return empty list on error
            }
        }
    
        try (Scanner customerRead = new Scanner(customerFile)) {
            while (customerRead.hasNextLine()) {
                String line = customerRead.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String custId = parts[0].trim();
                    String username = parts[1].trim();
                    String fullname = parts[2].trim();
                    String password = parts[3].trim();
                    String phoneNo = parts[4].trim();
                    String email = parts[5].trim();
                    Customer readCustomer = new Customer(custId, username, fullname, password, phoneNo, email);
                    customers.add(readCustomer);
                } else {
                    System.err.println("Skipping invalid customer data line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File 'customer.txt' not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file 'customer.txt': " + e.getMessage());
        }
        return customers;
    }

    public static void writeCustomerFile(ArrayList<Customer> customers, String filename) {
        File customerFile = new File(filename);
    
        if (!customerFile.exists()) {
            try {
                if (customerFile.createNewFile()) {
                    System.out.println("File created successfully. Ready to store customer data.");
                } else {
                    System.err.println("Failed to create file: " + filename);
                    return; // Exit if file creation fails
                }
            } catch (IOException ioEx) {
                System.err.println("Error: Couldn't create file '" + filename + "': " + ioEx.getMessage());
                return; // Exit on error
            }
        }
    
        try (FileWriter fileWriter = new FileWriter(customerFile, false)) {
            for (Customer customer : customers) {
                String line = String.join("|",
                        customer.getId(),
                        customer.getUsername(),
                        customer.getFullName(),
                        customer.getPassword(),
                        customer.getPhoneNo(),
                        customer.getEmail());
                fileWriter.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file '" + filename + "': " + e.getMessage());
        }
    }
    
    public static void updateCustomerFile(String custId, int update, String newValue) {
        File customerFile = new File("customer.txt");
    
        if (!customerFile.exists()) {
            try {
                if (customerFile.createNewFile()) {
                    System.out.println("File not found, creating a new empty file.");
                    return;
                } else {
                    System.err.println("Failed to create file.");
                    return;
                }
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
                return;
            }
        }
    
        List<String> updatedLines = new ArrayList<>();
        try (Scanner updateCustomer = new Scanner(customerFile)) {
            while (updateCustomer.hasNextLine()) {
                String line = updateCustomer.nextLine();
                String[] parts = line.split("\\|");
    
                if (parts.length > 0 && custId.equals(parts[0].trim())) {
                    switch (update) {
                        case 1: // id
                            parts[0] = newValue; // Corrected index
                            break;
                        case 2: // username
                            parts[1] = newValue; // Corrected index
                            break;
                        case 3: // fullname
                            parts[2] = newValue; // Corrected index
                            break;
                        case 4: // password
                            parts[3] = newValue; // Corrected index
                            break;
                        case 5: // Phone number
                            parts[4] = newValue; // Corrected index
                            break;
                        case 6: // email
                            parts[5] = newValue; // Corrected index
                            break;
                        default:
                            System.err.println("Warning: Invalid update code: " + update + " for customer ID: " + custId);
                            break;
                    }
                }
                updatedLines.add(String.join("|", parts));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }
    
        try (FileWriter writer = new FileWriter("customer.txt", false)) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void errorMessageNumber() {
        System.out.print("===================================\n");
        System.out.println("Error. Invalid input. Please input the correct number.");
    }

    public static void errorMessageWord() {
        System.out.print("===================================\n");
        System.out.println("Error. Invalid input. Please input the correct word.");
    }
}
