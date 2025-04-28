
import java.io.*;
import java.util.*;

public class CustomerManagement {

    public static ArrayList<Customer> customers = new ArrayList<>();

    public static void mainPage() {
        customers = readCustomerFile();
        Scanner sc = new Scanner(System.in);
        int action = 0;

        while (action != 5) {
            while (true) {
                clear();
                System.out.println("\n\t\t=====================================================");
                System.out.println("\t\t|\t   HOTEL CUSTOMER MANAGEMENT SYSTEM\t    |");
                System.out.println("\t\t=====================================================");
                System.out.println("\n\t\tPlease select an option:\n\t\t[1] Add New Customer\n\t\t[2] Edit Customer\n\t\t[3] Delete Customer\n\t\t[4] Search Customer\n\t\t[5] Exit");
                System.out.print("\t\tPlease choose your action (1-5): ");

                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    sc.nextLine();

                    if (action < 1 || action > 5) {
                        errorMessageNumber();
                    } else {
                        switch (action) {
                            case 1:
                                addNewCustomer(customers);
                                break;
                            case 2:
                                editCustomer(customers);
                                break;
                            case 3:
                                deleteCustomer(customers);
                                break;
                            case 4:
                                showCustomer();
                                break;
                            case 5:
                                break;
                        }
                        break;
                    }
                } else {
                    errorMessageNumber();
                    sc.next();
                }

            }
        }
    }

    public static void addNewCustomer(ArrayList<Customer> currentCustomerList) {
        clear();
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tADD CUSTOMER SYSTEM\t\t\t |");
        System.out.println("\t\t==================================================================");
        Scanner sc = new Scanner(System.in);
        boolean detailsConfirm = false;
        String customerId = "";
        String username, fullName, password, phoneNo, email = "";
        System.out.println("\n\t\tPlease fill in the details below: ");
        System.out.print("\t\t==================================================================");

        do {
            while (true) {
                System.out.println("\n\t\tCustomer ID will be auto-generated.");
                System.out.print("\n\t\tCustomer Username: ");
                username = sc.nextLine();
                if (username.isEmpty()) {
                    System.out.println("\n\t\tUsername cannot be empty. Please try again.");
                } else {
                    boolean exists = false;
                    for (Customer cust : currentCustomerList) {
                        if (cust.getUsername().equalsIgnoreCase(username)) {
                            exists = true;
                            break;
                        }
                    }
                    if (exists) {
                        System.out.println("\n\t\tUsername '" + username + "' already exists. Please choose another.");
                    } else {
                        break;
                    }
                }
            }

            while (true) {
                System.out.print("\n\t\tCustomer Full Name: ");
                fullName = sc.nextLine();
                if (fullName.isEmpty()) {
                    System.out.println("\n\t\tFull Name cannot be empty. Please try again.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("\n\t\tPassword: ");
                password = sc.nextLine();
                if (password.isEmpty()) {
                    System.out.println("\n\t\tPassword cannot be empty. Please try again.");
                } else if (password.length() < 6) {
                    System.out.println("\n\t\tPassword must be at least 6 characters long.");
                } else {
                    break;
                }
            }
            while (true) {
                System.out.print("\n\t\tEnter phone number (e.g. 0121234567): ");
                phoneNo = sc.nextLine().trim();

                if (phoneNo.length() == 10 && phoneNo.matches("\\d+")) {
                    break;
                } else {
                    System.out.println("\n\t\t[Invalid phone number format. Please enter exactly 10 digits]\n");
                }
            }

            while (true) {
                System.out.print("\n\t\tEnter email: ");
                email = sc.nextLine().trim();

                if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    break;
                } else {
                    System.out.println("\n\t\t[Invalid email format. Please enter again]");
                    System.out.println("\n\t\t[e.g. user@example.com]\n");
                }
            }

            int highestCustNum = 0;
            String prefix = "CUST";

            for (Customer customer : currentCustomerList) {
                if (customer.getId().startsWith(prefix)) {
                    try {
                        String numberPart = customer.getId().substring(prefix.length());
                        int currentIdNum = Integer.parseInt(numberPart);
                        if (currentIdNum > highestCustNum) {
                            highestCustNum = currentIdNum;
                        }
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.err.println("\n\t\tWarning: Skipping customer with unexpected ID format: " + customer.getId());
                    }
                }
            }
            int nextCustIdNum = highestCustNum + 1;
            customerId = prefix + nextCustIdNum;

            clear();
            System.out.println("\n\t\t==================================================================");
            System.out.println("\t\t|\t\tPlease confirm the details below:\t\t |");
            System.out.println("\t\t==================================================================");
            System.out.println("\t\tCustomer ID: " + customerId);
            System.out.println("\t\tUsername: " + username);
            System.out.println("\t\tFull Name: " + fullName);
            System.out.println("\t\tPassword: [Set]");
            System.out.println("\t\tPhone Number: " + phoneNo);
            System.out.println("\t\tEmail: " + email);
            System.out.println("\t\t==================================================================");

            while (true) {
                System.out.print("\n\t\tDo you confirm the details above are correct? (yes/no): ");
                String confirmation = sc.nextLine().trim();

                if (confirmation.equalsIgnoreCase("yes")) {
                    detailsConfirm = true;
                    break;
                } else if (confirmation.equalsIgnoreCase("no")) {
                    System.out.print("\n\t\tDetails not confirmed. Please re-enter the room information by pressing [ENTER] or press [Q] to exit: ");
                    String continueAdd = sc.nextLine().trim();
                    if (continueAdd.equalsIgnoreCase("q")) {
                        System.out.println("\n\t\tAdd new customer cancelled.");
                        return;
                    } else {
                        clear();
                        System.out.println("\n\t\t==================================================================");
                        System.out.println("\t\t|\t\t    RE-ENTER CUSTOMER DETAILS\t\t\t |");
                        System.out.println("\t\t==================================================================");
                        System.out.println("\n\t\tPlease fill in the details below: ");
                        System.out.print("\t\t==================================================================");
                        break;
                    }
                } else {
                    errorMessageWord();
                }
            }

        } while (!detailsConfirm);

        Customer newCustomer = new Customer(customerId, username, fullName, password, phoneNo, email);
        currentCustomerList.add(newCustomer);
        writeCustomerFile(customers, "customer.txt");
        System.out.println("\n\t\tCustomer [" + fullName + "] added successfully with ID: " + customerId);
        System.out.println("\n\t\tPress [ENTER] to continue...");
        sc.nextLine();
        mainPage();
    }

    public static void showCustomer() {
        clear();
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

        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tSEARCH CUSTOMER SYSTEM\t\t\t |");
        System.out.println("\t\t==================================================================");
        System.out.println("\n\t\tWhat attribute do you want to search by?");
        while (true) {
            System.out.println("\n\t\tWhat kind of information you want to type in order to find the customer(s) info?\n\t\t[1] Customer ID\n\t\t[2] Customer Username \n\t\t[3] Customer Fullname\n\t\t[4] Customer Phone Number\n\t\t[5] Customer Email\n\t\t[6] Exit");
            System.out.print("\n\t\tEnter your choice (1-7): ");
            if (sc.hasNextInt()) {
                search = sc.nextInt();
                sc.nextLine();
                if (search < 1 || search > 7) {
                    errorMessageNumber();
                } else {
                    if (search == 7) {
                        return;
                    }
                    String stringChosen = choicesString.getOrDefault(search, "Invalid");
                    System.out.print("\t\tConfirm to choose to search by '" + stringChosen + "'?(yes/no): ");
                    String confirmation = sc.nextLine().trim();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        break;
                    } else if (confirmation.equalsIgnoreCase("no")) {
                        System.out.println("\t\tOkay, please choose the search attribute again.");
                    } else {
                        System.out.println("\t\tInvalid confirmation. Please enter 'yes' or 'no'.");
                        System.out.println("\t\tPlease choose the search attribute again.");
                    }
                }
            } else {
                errorMessageNumber();
                sc.next();
            }
        }

        switch (search) {
            case 1:
                while (true) {
                    System.out.print("\t\tEnter the exact Customer ID [E.g CUST1]: ");
                    String customerID = sc.nextLine();
                    if (customerID.isEmpty()) {
                        System.out.println("\t\tCustomer ID cannot be empty. Please try again.");
                        continue;
                    }
                    Customer byUserID = searchCustomerByAttributes(customers, search, customerID);
                    if (byUserID != null) {
                        displayCustomerDetails(byUserID);
                        break;
                    } else {
                        System.out.println("\n\t\tCustomer with ID '" + customerID + "' not found.");
                        System.out.print("\t\tSearch again by tCustomer ID? (yes/no): ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                    break;
                }
                break;
            case 2:
                while (true) {
                    System.out.print("\t\tEnter the exact Customer Username: ");
                    String customerUsername = sc.nextLine();
                    if (customerUsername.isEmpty()) {
                        System.out.println("\t\tCustomer Username cannot be empty. Please try again.");
                        continue;
                    }
                    Customer byUsername = searchCustomerByAttributes(customers, search, customerUsername);
                    if (byUsername != null) {
                        displayCustomerDetails(byUsername);
                        break;
                    } else {
                        System.out.println("\n\t\tCustomer with username'" + customerUsername + "' not found.");
                        System.out.print("\t\tSearch again by Customer username? (yes/no): ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                    break;
                }
                break;
            case 3:
                while (true) {
                    System.out.print("\t\tEnter the exact Customer Fullname: ");
                    String customerFullname = sc.nextLine();
                    if (customerFullname.isEmpty()) {
                        System.out.println("\t\tCustomer Fullname cannot be empty. Please try again.");
                        continue;
                    }
                    Customer byFullname = searchCustomerByAttributes(customers, search, customerFullname);
                    if (byFullname != null) {
                        displayCustomerDetails(byFullname);
                        break;
                    } else {
                        System.out.println("\n\t\tCustomer with fullname'" + customerFullname + "' not found.");
                        System.out.print("\t\tSearch again by Customer fullname? (yes/no): ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                    break;
                }
                break;
            case 4:
                while (true) {
                    System.out.print("\t\tEnter the exact Customer Phone Number: ");
                    String customerPhone = sc.nextLine().trim();
                    if (customerPhone.isEmpty()) {
                        System.out.println("\t\tCustomer Phone Number cannot be empty. Please try again.");
                        continue;
                    }
                    if (customerPhone.length() == 10 && customerPhone.matches("\\d+")) {
                        Customer byPhone = searchCustomerByAttributes(customers, search, customerPhone);
                        if (byPhone != null) {
                            break;
                        } else {
                            System.out.println("\n\t\tCustomer with phone number'" + customerPhone + "' not found.");
                            System.out.print("\t\tSearch again by Customer phone number? (yes/no): ");
                            if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                break;
                            }
                        }
                        break;
                    } else {
                        System.out.println("\n\t\t[Invalid phone number format. Please enter exactly 10 digits]\n");
                    }
                }
                break;
            case 5:
                while (true) {
                    System.out.print("\t\tEnter the exact Customer Email: ");
                    String email = sc.nextLine();
                    if (email.isEmpty()) {
                        System.out.println("\t\tCustomer Phone Number cannot be empty. Please try again.");
                        continue;
                    }
                    if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                        break;
                    } else {
                        System.out.println("\n\t\t[Invalid email format. Please enter again]");
                        System.out.println("\n\t\t[e.g. user@example.com]\n");
                    }
                    Customer byEmail = searchCustomerByAttributes(customers, search, email);
                    if (byEmail != null) {
                        break;
                    } else {
                        System.out.println("\n\t\tCustomer with email'" + email + "' not found.");
                        System.out.print("\t\tSearch again by Customer email? (yes/no): ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                    break;
                }
                break;
            case 6:
                break;
        }
    }

    public static void deleteCustomer(ArrayList<Customer> currentCustomers) {
        clear();
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tDELETE CUSTOMER SYSTEM\t\t\t |");
        System.out.println("\t\t==================================================================");
        String filename = "customer.txt";

        if (currentCustomers == null) {
            System.err.println("\n\t\tError: Cannot delete from a null list.");
            return;
        }

        System.out.print("\n\t\tPlease enter the customer phone number that you want to delete: ");
        String custPhone = sc.nextLine().trim();


        Customer custToDelete = null;
        int custIndex = -1;

        for (int i = 0; i < currentCustomers.size(); i++) {
            Customer currentCust = currentCustomers.get(i);
            if (currentCust != null && custPhone.equalsIgnoreCase(currentCust.getPhoneNo())) {
                custToDelete = currentCust;
                custIndex = i;
                break;
            }
        }

        if (custToDelete != null) {
            System.out.println("\n\t\tCustomer Found:");
            displayCustomerDetails(custToDelete);
            while (true) {
                System.out.print("\n\t\tConfirm to delete this customer? (yes/no): ");
                String deleteConfirm = sc.nextLine().trim();
                if (deleteConfirm.equalsIgnoreCase("yes")) {
                    currentCustomers.remove(custIndex);
                    System.out.println("\n\t\tCustomer with ID '" + custToDelete.getId() + "(" + custToDelete.getPhoneNo() + ")' has been deleted.");
                    writeCustomerFile(currentCustomers, filename);
                    break;
                } else if (deleteConfirm.equalsIgnoreCase("no")) {
                    System.out.println("\n\t\tDeletion cancelled for Customer ID '" + custPhone + "(" + custToDelete.getPhoneNo() + ")'.");
                    break;
                } else {
                    errorMessageWord();
                }
            }
        } else {
            System.out.println("\n\t\tCustomer with ID '" + custPhone + "(" + custToDelete.getPhoneNo() + ")' not found in the list. No changes made.");
        }
        System.out.println("\n\t\tPress [ENTER] to continue...");
        sc.nextLine();
    }


    public static void editCustomer(ArrayList<Customer> customers) {
        clear();
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tEDIT CUSTOMER SYSTEM\t\t\t |");
        System.out.println("\t\t==================================================================");
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        while (true) {
            int update;
            String id = "";
            System.out.print("\n\t\tEnter Phone Number/Email to edit(Final will display in phone number): ");
            String input = sc.nextLine();
            for (Customer customer : customers) {
                if (customer.getEmail().equalsIgnoreCase(input)) {
                    found = true;
                    System.out.println("\n\t\tCustomer found: ");
                    displayCustomerDetails(customer);
                    editCustomerDetails(customer, input);
                    break;

                } else if (customer.getPhoneNo().equalsIgnoreCase(input)) {
                    found = true;
                    System.out.println("\n\t\tCustomer found: ");
                    displayCustomerDetails(customer);
                    input = customer.getPhoneNo();
                    editCustomerDetails(customer, input);
                    break;
                }
            }
            break;
        }
        System.out.println("\n\t\tPress [ENTER] to continue...");
        sc.nextLine();
        mainPage();
    }

    public static void editCustomerDetails(Customer customer, String input) {
        Scanner sc = new Scanner(System.in);
        boolean continueEditing = true;
        while (continueEditing) {

            System.out.println("\n\t\tWhat do you want to edit? (Enter numbers separated by commas, e.g., 1,5)");
            System.out.println("\t\t[1] User Name");
            System.out.println("\t\t[2] Full Name");
            System.out.println("\t\t[3] Password");
            System.out.println("\t\t[4] Phone Number");
            System.out.println("\t\t[5] Phone Number");
            System.out.println("\t\t[6] Finish Editing This Staff");
            System.out.print("\n\t\tEnter your choice(s): ");
            String editChoicesInput = sc.nextLine().trim();

            if (editChoicesInput.isEmpty()) {
                System.out.println("\n\t\tNo choice entered. Please select fields to edit or choose 6 to finish.");
                continue;
            }

            if (editChoicesInput.contains("6")) {
                System.out.println("\n\t\tFinished editing Staff ID: " + customer.getId());
                continueEditing = false;
                break;
            }

            String[] options = editChoicesInput.split(",");
            boolean changesMadeThisRound = false;

            for (String option : options) {
                String trimmedOption = option.trim();
                switch (trimmedOption) {
                    case "1":
                        System.out.print("\n\t\tEnter the new Username: ");
                        String newUsername = sc.nextLine().trim();
                        if (!newUsername.isEmpty()) {
                            customer.setUsername(newUsername);
                            updateCustomerFile(customer.getUsername(), 1, newUsername);
                            System.out.println("\t\t-> Username updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("\t\tUsername cannot be empty. Update skipped.");
                        }
                        break;
                    case "2":
                        System.out.print("\n\t\tEnter the new Fullname: ");
                        String newFullname = sc.nextLine().trim();
                        if (!newFullname.isEmpty()) {
                            customer.setFullName(newFullname);
                            updateCustomerFile(customer.getFullName(), 2, newFullname);
                            System.out.println("\t\t-> Fullname updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("\t\tFullname cannot be empty. Update skipped.");
                        }
                        break;
                    case "3":
                        System.out.print("\n\t\tEnter the new Password: ");
                        String newPassword = sc.nextLine().trim();
                        if (!newPassword.isEmpty()) {
                            customer.setUsername(newPassword);
                            updateCustomerFile(customer.getId(), 3, newPassword);
                            System.out.println("\t\t-> Password updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("\t\tPassword cannot be empty. Update skipped.");
                        }
                        break;

                    case "4":
                        System.out.print("\n\t\tEnter the new Phone Number: ");
                        String newPhone = sc.nextLine().trim();
                        if (!newPhone.isEmpty()) {
                            customer.setPhoneNo(newPhone);
                            updateCustomerFile(customer.getId(), 4, newPhone);
                            System.out.println("\t\t-> Phone Number updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("\t\tPhone Number cannot be empty. Update skipped.");
                        }
                        break;

                    case "5":
                        System.out.print("\n\t\tEnter the new Email: ");
                        String newEmail = sc.nextLine().trim();
                        if (!newEmail.isEmpty()) {
                            customer.setEmail(newEmail);
                            updateCustomerFile(customer.getId(), 5, newEmail);
                            System.out.println("\t\t-> Email updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("\t\tEmail cannot be empty. Update skipped.");
                        }
                        break;

                    case "6":
                        continueEditing = false;
                        break;

                    default:
                        System.out.println("\n\t\tInvalid choice '" + trimmedOption + "'. Skipping.");
                        break;
                }

                if (!continueEditing) {
                    break;
                }

            }

            if (changesMadeThisRound) {
                System.out.println("\n\t\tCurrent details for Customer Phone Number: " + customer.getPhoneNo());
                displayCustomerDetails(customer);
            }

            if (!continueEditing) {
                break;
            }

            System.out.print("\n\t\tEdit more details for this customer? (yes/no): ");
            String more = sc.nextLine().trim();
            if (!more.equalsIgnoreCase("yes")) {
                continueEditing = false;
                System.out.println("\n\t\tFinished editing Customer Phone Number: " + customer.getPhoneNo());
            }

        }
    }

    public static Customer searchCustomerByAttributes(ArrayList<Customer> customers, int option, String value) {
        Customer matchedCustomers = new Customer();
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
                    match = customer.getPhoneNo().equalsIgnoreCase(value);
                    break;
                case 5:
                    match = customer.getEmail().equalsIgnoreCase(value);
                    break;
            }

            if (match) {
                matchedCustomers = customer;
            }
        }
        if (matchedCustomers == null) {
            System.out.println("\n\t\tNo matching customers found.");
        } else {
            System.out.println("\n\t\tMatched customers found.");
        }
        return matchedCustomers;
    }

    public static void displayCustomerDetails(Customer customer) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\tCustomer ID: " + customer.getId());
        System.out.println("\t\tUsername: " + customer.getUsername());
        System.out.println("\t\tFullname: " + customer.getFullName());
        System.out.println("\t\tPassword: [Set]");
        System.out.println("\t\tPhone Number: " + customer.getPhoneNo());
        System.out.println("\t\tEmail: " + customer.getEmail());
        System.out.println("\n\t\t==================================================================");

        System.out.println("\n\t\tPress [ENTER] to continue...");
        sc.nextLine();
    }

    public static ArrayList<Customer> readCustomerFile() {
        File customerFile = new File("customer.txt");
        if (!customerFile.exists()) {
            try {
                if (customerFile.createNewFile()) {
                    System.out.println("\n\t\tFile created successfully. Please add customer details via 'Add Customer'");
                } else {
                    System.out.println("\n\t\tFailed to create file.");
                    return new ArrayList<>(); // Return an empty list
                }
            } catch (IOException ioEx) {
                System.err.println("\n\t\tError: Couldn't create file 'customer.txt': " + ioEx.getMessage());
                return customers; // Return empty list on error
            }
        }
        ArrayList<Customer> customers = new ArrayList<>(); // Initialize rooms here
        try (Scanner customerRead = new Scanner(customerFile)) {
            while (customerRead.hasNextLine()) {
                String line = customerRead.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String custId = parts[0].trim();
                    String username = parts[1];
                    String fullname = parts[2];
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

    public static void writeCustomerFile(ArrayList<Customer> customerList, String filename) {
        File customerFile = new File(filename);

        if (customerList == null) {
            System.err.println("Error: Cannot write a null customer list to file.");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(customerFile, false)) {
            for (Customer customer : customerList) {
                if (customer == null) {
                    continue; //Skip 
                }

                String id = customer.getId();
                String userName = customer.getUsername();
                String fullName = customer.getFullName();
                String password = customer.getPassword();
                String phoneNo = customer.getPhoneNo();
                String email = customer.getEmail();

                String line = String.join("|",
                        id,
                        userName,
                        fullName,
                        password,
                        phoneNo,
                        email
                );

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
                            parts[0] = newValue;
                            break;
                        case 2: // username
                            parts[1] = newValue;
                            break;
                        case 3: // fullname
                            parts[2] = newValue;
                            break;
                        case 4: // password
                            parts[3] = newValue;
                            break;
                        case 5: // Phone number
                            parts[4] = newValue;
                            break;
                        case 6: // email
                            parts[5] = newValue;
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
        System.out.print("\n\t\t===================================\n");
        System.out.println("\t\tError. Invalid input. Please input the correct number.");
    }

    public static void errorMessageWord() {
        System.out.print("\n\t\t===================================\n");
        System.out.println("\t\tError. Invalid input. Please input the correct word.");
    }

    public static void clear() {
        System.out.println("\033[H\033[2J");
    }
}
