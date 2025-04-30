
import java.io.*;
import java.util.*;

public class StaffManagement {

    public static ArrayList<Staff> staffs = new ArrayList<>();

    public static void mainPage() {
        staffs = readStaffFile();
        Scanner sc = new Scanner(System.in);
        int action = -1;

        while (action != 0) {
            while (true){
            System.out.println("\n=================================================================");
            System.out.println("||               HOTEL STAFF MANAGEMENT SYSTEM                 ||");
            System.out.println("=================================================================");
            System.out.println("|| [1] Add New Staff                                           ||");
            System.out.println("|| [2] Edit Staff                                              ||");
            System.out.println("|| [3] Delete Staff                                            ||");
            System.out.println("|| [4] Search Staff                                            ||");
            System.out.println("|| [0] Exit                                                    ||");
            System.out.println("=================================================================");
            System.out.print("Please choose an action: ");

            if (sc.hasNextInt()) {
                action = sc.nextInt();
                sc.nextLine();

                switch(action) {
                    case 1:
                        addNewStaff(staffs);
                        break;
                    case 2:
                        editStaff(staffs);
                        break;
                    case 3:
                        deleteStaff(staffs);
                        break;
                    case 4:
                        showStaff();
                        break;
                    case 0:
                        clear();
                        return;
                }

                if (action < 0 || action > 5) {
                    errorMessageNumber();
                    System.out.println("Press [ENTER] to continue..");
                    sc.nextLine();
                } 
            } else {
                errorMessageNumber();
                sc.next();
            }
        }

    }
}

    public static void addNewStaff(ArrayList<Staff> currentStaffList) {
        clear();
        clear();
        System.out.println("\n==================================================================");
        System.out.println("||                       ADD STAFF SYSTEM                       ||");
        System.out.println("==================================================================");
        Scanner sc = new Scanner(System.in);
        boolean detailsConfirm = false;

        String id, name, phoneNo, icNo, role = "";
        char gender;
        double salary = 0;
        System.out.println("\nStaff ID will auto generated.");
        System.out.println("\nPlease fill in the details below: ");
        System.out.print("==================================================================");

        do {
            while (true) {
                System.out.println("\nSelect Role:");
                System.out.println("[1] Normal Staff");
                System.out.println("[2] Housekeeper");
                System.out.println("[3] Receptionist");
                System.out.println("[4] Hotel Manager");
                System.out.print("Enter your choice (1-4): ");
                String roleChoice = sc.nextLine().trim();
                switch (roleChoice) {
                    case "1":
                        role = "Normal Staff";
                    break;
                    case "2":
                        role = "Housekeeper";
                        break;
                    case "3":
                        role = "Receptionist";
                        break;
                    case "4":
                        role = "Hotel Manager";
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please select a valid role.");
                        continue;
                }
                break;
            }

            while (true) {
                System.out.print("\nStaff Name: ");
                name = sc.nextLine();
                if (name.isEmpty()) {
                    System.out.println("\nName cannot be empty. Please try again.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("\nPhone number (e.g. 0121234567): ");
                phoneNo = sc.nextLine().trim();
                if (phoneNo.length() == 10 && phoneNo.matches("\\d+")) {
                    break;
                } else {
                    System.out.println("\n[Invalid phone number format. Please enter exactly 10 digits]\n");
                }
            }

            while (true) {
                System.out.print("\nEnter IC Number: (E.g. 010131-12-19234): ");
                icNo = sc.nextLine();
                if (icNo.isEmpty()) {
                    System.out.println("\nIC Number cannot be blank. Please try agian.");
                } else {
                    if (icNo.length() <= 7) {
                        System.out.println("\nInvalid format. IC number is too short. Expected format: xxxxxx-xx-xxxx");
                    } else if (icNo.charAt(6) == '-' && icNo.charAt(9) == '-') {
                        break;
                    } else {
                        errorMessage();
                    }
                }
            }

            while (true) {
                System.out.print("\nEnter gender (M/F): ");
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("\nGender cannot be empty. Please try again.");
                } else {
                    if (input.length() != 1) {
                        System.out.println("\nInvalid format. Only one character is allowed for gender. Expected input: F (OR) M");
                    } else if (input.charAt(0) == 'F' || input.charAt(0) == 'f') {
                        gender = 'F';
                        break;
                    } else if (input.charAt(0) == 'M' || input.charAt(0) == 'm') {
                        gender = 'M';
                        break;
                    } else {
                        errorMessage();
                    }
                }
            }

            while (true) {
                System.out.print("\nEnter Salary: RM ");
                salary = sc.nextDouble();
                sc.nextLine();
                if (salary < 0 || salary == 0) {
                    System.out.println("\nSalary must be positive number.");
                } else {
                    break;
                }
            }



            Set<Integer> existingIds = new HashSet<>();
            int highestStaffNum = 0;
            String prefix = role.substring(0, 1).toUpperCase(); 

            for (Staff staff : currentStaffList) {
                if (staff.getId() != null && staff.getId().startsWith(prefix)) {
                    try {
                        String numberPart = staff.getId().substring(prefix.length());
                        int currentIdNum = Integer.parseInt(numberPart);
                        existingIds.add(currentIdNum);
                        if (currentIdNum > highestStaffNum) {
                            highestStaffNum = currentIdNum;
                        }
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.err.println("\nWarning: Skipping staff with unexpected ID format: " + staff.getId());
                    }
                }
            }
    
            int nextStaffId = -1;
            for (int i = 1; i <= highestStaffNum; i++) {
                if (!existingIds.contains(i)) {
                    nextStaffId = i;
                    break;
                }
            }
    
            if (nextStaffId == -1) {
                nextStaffId = highestStaffNum + 1;
            }
            id = prefix + String.format("%03d", nextStaffId);

            clear();
            System.out.println("\n==================================================================");
            System.out.println("|| Please confirm the details below:                            ||");
            System.out.println("==================================================================");
            System.out.println("\nRole : " + role);
            System.out.println("Staff ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Gender: " + gender);
            System.out.println("IC Number: " + icNo);
            System.out.printf("Salary: RM %.2f%n", salary);
            System.out.println("\n==================================================================");

            while (true) {
                System.out.print("\nDo you confirm the details above are correct? (yes/no): ");
                String confirmation = sc.nextLine().trim();

                if (confirmation.equalsIgnoreCase("yes")) {
                    detailsConfirm = true;
                    break;
                } else if (confirmation.equalsIgnoreCase("no")) {
                    System.out.print("\nDetails not confirmed. Please re-enter the staff information by pressing [ENTER] or press [Q] to exit: ");
                    String continueAdd = sc.nextLine().trim();
                    if (continueAdd.equalsIgnoreCase("q")) {
                        System.out.println("\nAdd new staff cancelled.");
                        return;
                    }
                    else{
                        clear();
                        System.out.println("\n==============================================================");
                        System.out.println("|| RE-ENTER CUSTOMER DETAILS                                ||");
                        System.out.println("==============================================================");
                        System.out.println("\nPlease fill in the details below: ");
                        System.out.print("================================================================");
                        break;
                    }
                } else {
                    errorMessageWord();
                }
            }

        } while (!detailsConfirm);


        Staff staff = new Staff(role, id, name, phoneNo, gender, icNo, salary);
        staffs.add(staff);
        writeStaffFile(staffs, "staff.txt");
        System.out.println("\nStaff is added successfully!");
        System.out.println("\nPress [ENTER] to continue..");
        sc.nextLine();
        mainPage();
    }

    public static void showStaff() {
        clear();
        Scanner sc = new Scanner(System.in);
        int search = 0;
        String type = "";

        Map<Integer, String> choicesString = new HashMap<>();
        choicesString.put(1, "Staff ID");
        choicesString.put(2, "Staff Role");
        choicesString.put(3, "Staff Name");
        choicesString.put(4, "Staff Phone Number");
        choicesString.put(5, "Staff Gender");
        choicesString.put(6, "Staff IC Number");
        choicesString.put(7, "Staff Salary");

        System.out.println("==================================================================");
        System.out.println("||                      SEARCH STAFF SYSTEM                     ||");
        System.out.println("==================================================================");

        while (true) {
            System.out.println("|| [1] Staff ID                                                ||");
            System.out.println("|| [2] Staff Role                                                ||");
            System.out.println("|| [3] Staff Name                                              ||");
            System.out.println("|| [4] Staff Phone Number                                      ||");
            System.out.println("|| [5] Staff Gender                                            ||");
            System.out.println("|| [6] Staff IC Number                                         ||");
            System.out.println("|| [7] Staff Salary                                            ||");
            System.out.println("|| [0] Exit to Main Menu                                       ||");
            System.out.println("=================================================================");
            System.out.print("Please choose an option: ");

            if (sc.hasNextInt()) {
                search = sc.nextInt();
                sc.nextLine();
                switch (search) {

                    case 1:
                        while (true) {
                            System.out.print("Enter the exact staff ID: ");
                            String id = sc.nextLine().trim();
                            if (id.isEmpty()) {
                                System.out.println("Staff ID cannot be empty. Please try again.");
                                continue;
                            }
                            Staff byId = searchStaffByAttributes(staffs, search, id);
                            if (byId != null) {
                                displayStaffDetails(byId);
                                break;
                            } else {
                                System.out.println("\nStaff with ID '" + id + "' not found.");
                                System.out.print("Search again by Staff ID? (yes/no): ");
                                if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                    break;
                                }
                            }
                        }
                        break;
                    case 2:
                        while (true) {
                            System.out.print("Enter the exact staff Role: ");
                            String role = sc.nextLine().trim();
                            if (role.isEmpty()) {
                                System.out.println("Staff Role cannot be empty. Please try again.");
                                continue;
                            }
                            Staff byRole = searchStaffByAttributes(staffs, search, role);
                            if (byRole != null) {
                                displayStaffDetails(byRole);
                                break;
                            } else {
                                System.out.println("\nStaff with role '" + role + "' not found.");
                                System.out.print("Search again by Staff role? (yes/no): ");
                                if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                    break;
                                }
                            }
                        }
                        break;
        
                    case 3:
                        while (true) {
                            System.out.print("Enter the Staff Name: ");
                            String name = sc.nextLine().trim();
                            if (name.isEmpty()) {
                                System.out.println("Staff name cannot be empty. Please try again.");
                                continue;
                            }
                            Staff byName = searchStaffByAttributes(staffs, search, name);
                            if (byName != null) {
                                displayStaffDetails(byName);
                                break;
                            } else {
                                System.out.println("\nNo staff with name '" + name + "'.");
                                System.out.print("Search again by Staff name? (yes/no): ");
                                if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                    break;
                                }
                            }
                        }
                        break;
        
                    case 4:
                    while (true) {
                        System.out.print("Enter the Staff Phone Number: ");
                        String phoneNo = sc.nextLine().trim();
                        if (phoneNo.isEmpty()) {
                            System.out.println("Staff Phone Number cannot be empty. Please try again.");
                            continue;
                        }
                        if (phoneNo.length() == 10 && phoneNo.matches("\\d+")) {
                            Staff byPhone = searchStaffByAttributes(staffs, search, phoneNo);
                            if (byPhone != null) {
                                break;
                            } else {
                                System.out.println("\nStaff with phone number'" + phoneNo + "' not found.");
                                System.out.print("Search again by staff phone number? (yes/no): ");
                                if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                    break;
                                }
                            }
                            break;
                        } else {
                            System.out.println("\n[Invalid phone number format. Please enter exactly 10 digits]\n");
                        }
                    }
                    break;
        
                    case 5:
                    while (true) {
                        System.out.print("Enter the Staff Gender (e.g., M or F): ");
                        String gender = sc.nextLine().trim();
                        if (gender.isEmpty()) {
                            System.out.println("Staff gender cannot be empty. Please try again.");
                            continue;
                        }
                
                        List<Staff> foundStaff = findAllStaffByGender(staffs, gender);
                
                        if (foundStaff.isEmpty()) {
                            System.out.println("\nNo staff found with gender '" + gender + "'.");
                        } else if (foundStaff.size() == 1) {
                            System.out.println("\nFound one staff member:");
                            displayStaffDetails(foundStaff.get(0));
                            break;
                        } else {
                            System.out.println("\nMultiple staff members found with gender '" + gender + "'. Please select one by Staff ID:");
                            for (int i = 0; i < foundStaff.size(); i++) {
                                System.out.println("[" + (i + 1) + "] " + foundStaff.get(i).getId() + " - " + foundStaff.get(i).getName());
                            }
                
                            Staff selectedStaff = null;
                            while (selectedStaff == null) {
                                System.out.print("Enter the Staff ID to view details: ");
                                String selectedId = sc.nextLine().trim();
                                for (Staff staff : foundStaff) {
                                    if (staff.getId().equalsIgnoreCase(selectedId)) {
                                        selectedStaff = staff;
                                        break;
                                    }
                                }
                                if (selectedStaff == null) {
                                    System.out.println("Invalid Staff ID from the list. Please try again.");
                                }
                            }
                            System.out.println("\nShowing details for selected staff:");
                            displayStaffDetails(selectedStaff);
                            break;
                        }
                
                        if (foundStaff.isEmpty()) {
                             System.out.print("Search again by Staff gender? (yes/no): ");
                             if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                 break;
                             }
                        } else {
                             break;
                        }
                    }
                    break;
        
                    case 6:
                    while (true) {
                        System.out.print("Enter the Staff IC Number: ");
                        String icNo = sc.nextLine().trim();
                        if (icNo.isEmpty()) {
                            System.out.println("Staff IC number cannot be empty. Please try again.");
                            continue;
                        }
                        Staff byIc = searchStaffByAttributes(staffs, search, icNo);
                        if (byIc != null) {
                            displayStaffDetails(byIc);
                            break;
                        } else {
                            System.out.println("\nNo staff with ic number '" + icNo + "'.");
                            System.out.print("Search again by Staff ic number? (yes/no): ");
                            if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                break;
                            }
                        }
                    }
                    break;
        
                    case 7:
                    while (true) {
                        System.out.print("Enter the Staff salary: RM ");
                        String salary = sc.nextLine().trim();
                        if (salary.isEmpty()) {
                            System.out.println("Staff salary cannot be empty. Please try again.");
                            continue;
                        }
                        Staff bySalary = searchStaffByAttributes(staffs, search, salary);
                        if (bySalary != null) {
                            displayStaffDetails(bySalary);
                            break;
                        } else {
                            System.out.println("\nNo staff with salary RM '" + salary + "'.");
                            System.out.print("Search again by Staff salary? (yes/no): ");
                            if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                break;
                            }
                        }
                    }
                    break;
        
                    default:
                        System.out.println("\nUnexpected error in search type selection.");
                        break;
                }
        

                if (search < 1 || search > 8) {
                    errorMessageNumber();
                } else {
                    if (search == 0) {
                        return;
                    }

                    String stringChosen = choicesString.getOrDefault(search, "Invalid choice");
                    System.out.print("Confirm to choose to search by '" + stringChosen + "'?(yes/no): ");
                    String confirmation = sc.nextLine().trim();

                    if (confirmation.equalsIgnoreCase("yes")) {
                        break;
                    } else if (confirmation.equalsIgnoreCase("no")) {
                        System.out.println("Okay, please choose the search attribute again.");
                    } else {
                        System.out.println("Invalid confirmation. Please enter 'yes' or 'no'.");
                        System.out.println("Please choose the search attribute again.");
                    }
                }
            } else {
                errorMessageNumber();
                sc.next();
            }
        }


        System.out.println("\n------------------------------------------------------------------");
        if (search != 8) {
            System.out.println("Search finished. What action would you like to perform next?");
            int choice = 0;
            while (true) {
                System.out.println("\n[1] Edit Room\n[2] Delete Room\n[3] Return to Main Menu");
                System.out.print("Enter your choice (1-3): ");
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice < 1 || choice > 3) {
                        errorMessageNumber();
                    } else {
                        break;
                    }
                } else {
                    errorMessageNumber();
                    sc.next();
                }
            }

            switch (choice) {
                case 1:
                    editStaff(staffs);
                    break;
                case 2:
                    deleteStaff(staffs);
                    break;
                case 3:
                    mainPage();
                    break;
                default:
                    System.out.println("Invalid action choice.");
                    break;
            }

            System.out.println("\nPress [ENTER] to continue...");
            sc.nextLine();
            mainPage();
        }
    }
    
    public static List<Staff> findAllStaffByGender(List<Staff> allStaff, String gender) {
        List<Staff> matches = new ArrayList<>();
        for (Staff staff : allStaff) {
            if (String.valueOf(staff.getGender()).equalsIgnoreCase(gender)) {
                matches.add(staff);
            }
        }
        return matches;
    }

    public static void editStaff(ArrayList<Staff> staffs) {
        clear();
        System.out.println("\n=================================================================");
        System.out.println("||                      EDIT STAFF SYSTEM                      ||");
        System.out.println("=================================================================");
        Scanner sc = new Scanner(System.in);
        boolean searchAgain = true;
    
        while (searchAgain) {
            System.out.print("\nEnter Staff ID or Name to edit (or type 'exit' to return): ");
            String input = sc.nextLine().trim();
    
            if (input.equalsIgnoreCase("exit")) {
                searchAgain = false;
                break;
            }
            if (input.isEmpty()) {
                 System.out.println("Input cannot be empty.");
                 continue;
            }
    
            Staff staffToEdit = null;
            List<Staff> nameMatches = new ArrayList<>();
    
            for (Staff staff : staffs) {
                if (staff.getId().equalsIgnoreCase(input)) {
                    staffToEdit = staff;
                    break;
                } else if (staff.getName().equalsIgnoreCase(input)) {
                    nameMatches.add(staff);
                }
            }
    
            if (staffToEdit != null) {
                System.out.println("\nStaff found by ID:");
                displayStaffDetails(staffToEdit);
                editStaffDetails(staffToEdit); 
    
            } else if (nameMatches.isEmpty()) {
                System.out.println("\nNo staff found with ID or Name '" + input + "'.");
    
            } else if (nameMatches.size() == 1) {
                staffToEdit = nameMatches.get(0);
                System.out.println("\nStaff found by Name:");
                displayStaffDetails(staffToEdit);
                editStaffDetails(staffToEdit); 
    
            } else {
                System.out.println("\nMultiple staff found with the name '" + input + "'. Please specify by Staff ID:");
                for (int i = 0; i < nameMatches.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + nameMatches.get(i).getId() + " - " + nameMatches.get(i).getName());
                }
    
                Staff selectedStaff = null;
                while (selectedStaff == null) {
                    System.out.print("Enter the Staff ID to edit: ");
                    String selectedId = sc.nextLine().trim();
                    for (Staff staff : nameMatches) {
                        if (staff.getId().equalsIgnoreCase(selectedId)) {
                            selectedStaff = staff;
                            break;
                        }
                    }
                    if (selectedStaff == null) {
                        System.out.println("Invalid Staff ID from the list. Please try again.");
                    }
                }
                staffToEdit = selectedStaff;
                System.out.println("\nSelected staff for editing:");
                displayStaffDetails(staffToEdit);
                editStaffDetails(staffToEdit); 
            }
    
            if (searchAgain) {
                 System.out.print("\nSearch for another staff member to edit? (yes/no): ");
                 if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                      searchAgain = false;
                 }
            }
        }
    
        System.out.println("\nReturning to main page...");
        mainPage();
    }

    public static void editStaffDetails(Staff staff) {
        Scanner sc = new Scanner(System.in);
        boolean continueEditing = true;
        while (continueEditing) {
    
            System.out.println("\nWhat do you want to edit? (Enter numbers separated by commas, e.g., 1,5)");
            System.out.println("[1] Name");
            System.out.println("[2] Phone Number");
            System.out.println("[3] Gender");
            System.out.println("[4] IC Number");
            System.out.println("[5] Salary");
            System.out.println("[6] Role (and update Staff ID)");
            System.out.println("[7] Finish Editing This Staff");
            System.out.print("Enter your choice(s): ");
            String editChoicesInput = sc.nextLine().trim();
    
            if (editChoicesInput.isEmpty()) {
                System.out.println("\nNo choice entered. Please select fields to edit or choose 7 to finish.");
                continue;
            }
    
            if (editChoicesInput.contains("7")) {
                System.out.println("\nFinished editing Staff ID: " + staff.getId());
                continueEditing = false;
                break;
            }
    
            String[] options = editChoicesInput.split(",");
            boolean changesMadeThisRound = false;
    
            for (String option : options) {
                String trimmedOption = option.trim();
                switch (trimmedOption) {
                    case "1":
                        System.out.print("\nEnter the new Name: ");
                        String newName = sc.nextLine().trim();
                        if (!newName.isEmpty()) {
                            staff.setName(newName);
                            updateStaffFile(staff.getId(), 1, newName);
                            System.out.println("-> Name updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("\nName cannot be empty. Update skipped.");
                        }
                        break;
    
                    case "2":
                        System.out.print("\nEnter the new Phone Number: ");
                        String newPhone = sc.nextLine().trim();
                        if (!newPhone.isEmpty()) {
                            staff.setPhoneNo(newPhone);
                            updateStaffFile(staff.getId(), 2, newPhone);
                            System.out.println("-> Phone Number updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("\nPhone Number cannot be empty. Update skipped.");
                        }
                        break;
    
                    case "3":
                        char newGender = ' ';
                        while (newGender == ' ') {
                            System.out.print("\nEnter the new Gender (M/F): ");
                            String genderInput = sc.nextLine().trim().toUpperCase();
                            if (genderInput.length() == 1 && (genderInput.charAt(0) == 'M' || genderInput.charAt(0) == 'F')) {
                                newGender = genderInput.charAt(0);
                                staff.setGender(newGender);
                                updateStaffFile(staff.getId(), 3, String.valueOf(newGender));
                                System.out.println("-> Gender updated successfully!");
                                changesMadeThisRound = true;
                            } else {
                                System.out.println("\nInvalid input. Please enter 'M' or 'F'.");
                            }
                        }
                        break;
    
                    case "4":
                        System.out.print("\nEnter the new IC Number: ");
                        String newIc = sc.nextLine().trim();
                        if (!newIc.isEmpty()) {
                            staff.setIcNo(newIc);
                            updateStaffFile(staff.getId(), 4, newIc);
                            System.out.println("-> IC Number updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("IC Number cannot be empty. Update skipped.");
                        }
                        break;
    
                    case "5":
                        double newSalary = -1.0;
                        while (newSalary < 0) {
                            System.out.print("\nEnter the new Salary (e.g., 2500.50): RM ");
                            try {
                                newSalary = sc.nextDouble();
                                sc.nextLine();
                                if (newSalary < 0) {
                                    System.out.println("\nSalary cannot be negative.");
                                    newSalary = -1.0;
                                } else {
                                    staff.setSalary(newSalary);
                                    updateStaffFile(staff.getId(), 5, String.valueOf(newSalary));
                                    System.out.println("-> Salary updated successfully!");
                                    changesMadeThisRound = true;
                                }
                            } catch (InputMismatchException e) {
                                errorMessageNumber();
                                sc.nextLine();
                                newSalary = -1.0;
                            }
                        }
                        break;
    
                    case "6":
                        System.out.println("\nSelect the new role:");
                        System.out.println("[1] Housekeeper");
                        System.out.println("[2] Receptionist");
                        System.out.println("[3] Manager");
                        System.out.println("[4] Normal Staff");
                        System.out.print("Enter your choice (1-4): ");
                        String roleChoice = sc.nextLine().trim();
                        String newRole = "";
                        String prefix = "";
                        switch (roleChoice) {
                            case "1":
                                newRole = "Housekeeper";
                                prefix = "H";
                                break;
                            case "2":
                                newRole = "Receptionist";
                                prefix = "R";
                                break;
                            case "3":
                                newRole = "Manager";
                                prefix = "M";
                                break;
                            case "4":
                                newRole = "Normal Staff";
                                prefix = "S";
                                break;
                            default:
                                System.out.println("\nInvalid choice. Role update skipped.");
                                continue;
                        }
    
                        int highestStaffNum = 0;
                        for (Staff s : staffs) {
                            if (s.getId().startsWith(prefix)) {
                                try {
                                    int currentIdNum = Integer.parseInt(s.getId().substring(1));
                                    if (currentIdNum > highestStaffNum) {
                                        highestStaffNum = currentIdNum;
                                    }
                                } catch (NumberFormatException e) {
                                    System.err.println("Warning: Skipping invalid ID format for staff: " + s.getId());
                                }
                            }
                        }
                        String newId = prefix + String.format("%03d", highestStaffNum + 1);
    
                        staff.setRole(newRole);
                        staff.setId(newId);
                        System.out.println("-> Role updated successfully to: " + newRole);
                        System.out.println("-> Staff ID updated successfully to: " + newId);
                        changesMadeThisRound = true;
                        break;
    
                    default:
                        System.out.println("\nInvalid choice '" + trimmedOption + "'. Skipping.");
                        break;
                }
    
                if (!continueEditing) {
                    break;
                }
            }
    
            if (changesMadeThisRound) {
                System.out.println("\nCurrent details for Staff ID: " + staff.getId());
                displayStaffDetails(staff);
                writeStaffFile(staffs, "staff.txt"); 
            }
    
            if (!continueEditing) {
                break;
            }
    
            System.out.print("\nEdit more details for this staff member? (yes/no): ");
            String more = sc.nextLine().trim();
            if (!more.equalsIgnoreCase("yes")) {
                continueEditing = false;
                System.out.println("\nFinished editing Staff ID: " + staff.getId());
            }
        }
    }
    public static void deleteStaff(ArrayList<Staff> currentStaffs) {
        clear();
        Scanner sc = new Scanner(System.in);
        System.out.println("\n==================================================================");
        System.out.println("||                      DELETE STAFF SYSTEM                     ||");
        System.out.println("==================================================================");
        String filename = "staff.txt";

        if (currentStaffs == null) {
            System.err.println("\nError: Cannot delete from a null list.");
            return;
        }

        System.out.print("\nPlease enter the staff ID that you want to delete: ");
        String idToDelete = sc.nextLine().trim();

        Staff staffToDel = null;
        int staffIndex = -1;

        for (int i = 0; i < currentStaffs.size(); i++) {
            Staff currenStaff = currentStaffs.get(i);
            if (currenStaff != null && idToDelete.equalsIgnoreCase(currenStaff.getId())) {
                staffToDel = currenStaff;
                staffIndex = i;
                break;
            }
        }

        if (staffToDel != null) {
            System.out.println("\nStaff Found:");
            displayStaffDetails(staffToDel);
            while (true) {
                System.out.print("\nConfirm to delete this staff? (yes/no): ");
                String deleteConfirm = sc.nextLine().trim();
                if (deleteConfirm.equalsIgnoreCase("yes")) {
                    currentStaffs.remove(staffIndex);
                    System.out.println("\nStaff with ID '" + idToDelete + "'("+ staffToDel.getName() +") has been deleted.");
                    writeStaffFile(currentStaffs, filename);
                    break;
                } else if (deleteConfirm.equalsIgnoreCase("no")) {
                    System.out.println("\nDeletion cancelled for Staff ID '" + idToDelete + "'("+ staffToDel.getName() + "').");
                    break;
                } else {
                    errorMessageWord();
                }
            }
        } else {
            System.out.println("\nStaff with ID '"+ idToDelete + "'("+ staffToDel.getName() + ")' not found in the list. No changes made.");
        }
        System.out.println("\nPress [ENTER] to continue...");
        sc.nextLine();
    }

    public static Staff searchStaffByAttributes(ArrayList<Staff> staffs, int option, String value) {
        staffs = readStaffFile();
        Staff matchedStaff = new Staff();
        for (Staff staff : staffs) {
            boolean match = false;

            switch (option) {
                case 1:
                    match = staff.getId().equalsIgnoreCase(value);
                    break;
                case 2:
                    match = staff.getRole().equalsIgnoreCase(value);
                    break;
                case 3:
                    match = staff.getName().equalsIgnoreCase(value);
                    break;
                case 4:
                    match = staff.getPhoneNo().equalsIgnoreCase(value);
                    break;
                case 5:
                    char staffGender = staff.getGender();
                    char valueChar = value.charAt(0);
                    match = (Character.toUpperCase(staffGender) == Character.toUpperCase(valueChar));
                    break;
                case 6:
                    match = staff.getIcNo().equalsIgnoreCase(value);
                    break;
                case 7:
                    double staffSalary = staff.getSalary();
                    match = (staffSalary == (Double.parseDouble(value)));
                    break;

            }

            if (match) {
                matchedStaff = staff;
            }
        }
        if (matchedStaff == null) {
            System.out.println("\nNo matching staff found.");
        } else {
            System.out.println("\nMatched staff(s) found.");
        }
        return matchedStaff;
    }

    public static void displayStaffDetails(Staff staff) {
        System.out.println("\n==================================================================");
        System.out.println("Staff Role: " + staff.getRole());
        System.out.println("Staff ID: " + staff.getId());
        System.out.println("Name: " + staff.getName());
        System.out.println("Phone Number: " + staff.getPhoneNo());
        System.out.println("Gender: " + staff.getGender());
        System.out.println("IC Number: " + staff.getIcNo());
        System.out.printf("Salary: RM %.2f ", staff.getSalary());
        System.out.println("\n==================================================================");
    }

    public static ArrayList<Staff> readStaffFile() {
        File staffFile = new File("staff.txt");
        if (!staffFile.exists()) {
            try {
                if (staffFile.createNewFile()) {
                    System.out.println("File created successfully. Please add staff details via 'Add Staff'");
                } else {
                    System.err.println("Failed to create file.");
                    return new ArrayList<>();
                }
            } catch (IOException ioEx) {
                System.err.println("Error: Couldn't create file 'staff.txt': " + ioEx.getMessage());
                return new ArrayList<>();
            }
        }

        ArrayList<Staff> staffs = new ArrayList<>();
        try (Scanner staffRead = new Scanner(staffFile)) {
            int lineNumber = 0;
            while (staffRead.hasNextLine()) {
                lineNumber++;
                String line = staffRead.nextLine();
                String[] parts = line.split("\\|");

                if (parts.length == 6) {
                    try { 
                        String id = parts[0].trim();
                        String name = parts[1].trim();
                        String phoneNo = parts[2].trim();
                        String genderString = parts[3].trim();
                        char gender = genderString.isEmpty() ? 'U' : Character.toUpperCase(genderString.charAt(0));
                        String ic = parts[4].trim();
                        double salary = Double.parseDouble(parts[5].trim()); 

                        String role;
                        if (id.isEmpty()) {
                            System.err.println("Warning: Empty Staff ID on line " + lineNumber + ". Skipping line.");
                            continue; 
                        }
                        char rolePrefix = id.charAt(0);
                        switch (rolePrefix) {
                            case 'H': role = "Housekeeper"; break;
                            case 'R': role = "Receptionist"; break;
                            case 'M': role = "Manager"; break;
                            case 'S': role = "Normal Staff"; break;
                            default:
                                role = "Unknown";
                                System.err.println("Warning: Unknown role prefix '" + rolePrefix + "' for Staff ID: " + id + " on line " + lineNumber);
                                break;
                        }
                        Staff readStaff = new Staff(role, id, name, phoneNo, gender, ic, salary);
                        staffs.add(readStaff);

                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing salary on line " + lineNumber + ": '" + parts[5].trim() + "'. Skipping line.");
                    } catch (Exception e) { 
                         System.err.println("Error processing line " + lineNumber + ": " + e.getMessage() + ". Skipping line.");
                    }
                } else {
                     if (!line.trim().isEmpty()) { 
                        System.err.println("Warning: Incorrect data format on line " + lineNumber + " (Expected 7 fields separated by '|'). Skipping line.");
                        System.err.println("   Line content: " + line);
                     }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File 'staff.txt' not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file 'staff.txt': " + e.getMessage());
        }
        return staffs;
    }
    public static void updateStaffFile(String staffId, int update, String newValue) {
        File staffFile = new File("staff.txt");
    
        if (!staffFile.exists()) {
            try {
                if (staffFile.createNewFile()) {
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
        try (Scanner updateStaff = new Scanner(staffFile)) {
            while (updateStaff.hasNextLine()) {
                String line = updateStaff.nextLine();
                String[] parts = line.split("\\|");
    
                if (parts.length > 0 && staffId.equals(parts[0].trim())) {
                    switch (update) {
                        case 1: // Name
                            parts[1] = newValue;
                            break;
                        case 2: // Phone Number
                            parts[2] = newValue;
                            break;
                        case 3: // Gender
                            parts[3] = newValue;
                            break;
                        case 4: // IC Number
                            parts[4] = newValue;
                            break;
                        case 5: // Salary
                            parts[5] = newValue;
                            break;
                        default:
                            System.err.println("Warning: Invalid update code: " + update + " for staff ID: " + staffId);
                            break;
                    }
                }
                updatedLines.add(String.join("|", parts));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }
    
        try (FileWriter writer = new FileWriter("staff.txt", false)) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void writeStaffFile(ArrayList<Staff> staffs, String filename) {
        File staffFile = new File(filename);
    
        if (!staffFile.exists()) {
            try {
                if (staffFile.createNewFile()) {
                    System.out.println("File created successfully. Ready to store staff data.");
                } else {
                    System.err.println("Failed to create file: " + filename);
                    return; 
                }
            } catch (IOException ioEx) {
                System.err.println("Error: Couldn't create file '" + filename + "': " + ioEx.getMessage());
                return; 
            }
        }
    
        try (FileWriter fileWriter = new FileWriter(staffFile, false)) {
            for (Staff staff : staffs) {
                String prefix = "";
                switch (staff.getRole().toLowerCase()) {
                    case "housekeeper":
                        prefix = "H";
                        break;
                    case "receptionist":
                        prefix = "R";
                        break;
                    case "manager":
                        prefix = "M";
                        break;
                    case "normal staff":
                        prefix = "S";
                        break;
                    default:
                        prefix = "U"; 
                        break;
                }
    
                String numericPart = staff.getId().replaceAll("\\D", ""); // Remove non-numeric characters
                String newId = prefix + String.format("%03d", Integer.parseInt(numericPart));
                staff.setId(newId); 
    
                
                String gender = String.valueOf(staff.getGender());
                String salary = String.valueOf(staff.getSalary());
    
                String line = String.join("|",
                        staff.getId(),
                        staff.getName(),
                        staff.getPhoneNo(),
                        gender,
                        staff.getIcNo(),
                        salary,
                        staff.getRole()
                );
                fileWriter.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file '" + filename + "': " + e.getMessage());
        }
    }
    
    public static void errorMessage() {
        System.out.print("\n===================================\n");
        System.out.println("\nError. Invalid input. Please try again.");
    }

    public static void errorMessageNumber() {
        System.out.print("\n===================================\n");
        System.out.println("\nError. Invalid input. Please input the correct number.");
    }

    public static void errorMessageWord() {
        System.out.print("\n===================================\n");
        System.out.println("\nError. Invalid input. Please input the correct word.");
    }

    public static void errorMessageBlank() {
        System.out.print("\n===================================\n");
        System.out.println("\nError. Input cannot be empty. Please try again");
    }

    public static void clear() {
        System.out.println("\033[H\033[2J");
    }

}
