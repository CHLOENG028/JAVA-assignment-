
import java.io.*;
import java.util.*;

public class StaffManagement {

    public static ArrayList<Staff> staffs = new ArrayList<>();

    public static void mainPage() {
        staffs = readStaffFile();
        Scanner sc = new Scanner(System.in);
        int action = 0;

        while (action != 5) {
            while (true){
            clear();
            System.out.println("\n\t\t============================================");
            System.out.println("\t\t|\tHOTEL STAFF MANAGEMENT SYSTEM\t   |");
            System.out.println("\t\t============================================");
            System.out.println("\n\t\tPlease select an option: ");
            System.out.println("\n\t\t[1] Add New Staff\n\t\t[2] Edit Staff\n\t\t[3] Delete Staff\n\t\t[4] Search Staff\n\t\t[5] Exit");
            System.out.print("\t\tPlease choose your action (1-5): ");

            if (sc.hasNextInt()) {
                action = sc.nextInt();
                sc.nextLine();

                if (action < 1 || action > 5) {
                    errorMessageNumber();
                } else {
                    break;
                }
            } else {
                errorMessageNumber();
                sc.next();
            }
        }
        switch (action) {
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
            case 5:
                break;
        }
    }
}

    public static void addNewStaff(ArrayList<Staff> currentStaffList) {
        clear();
        clear();
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tADD STAFF SYSTEM\t\t\t |");
        System.out.println("\t\t==================================================================");
        Scanner sc = new Scanner(System.in);
        boolean detailsConfirm = false;

        String id, name, phoneNo, icNo = "";
        char gender;
        double salary = 0;
        System.out.println("\n\t\tStaff ID will auto generated.");
        System.out.println("\n\t\tPlease fill in the details below: ");
        System.out.print("\t\t==================================================================");

        do {

            while (true) {
                System.out.print("\n\t\tStaff Name: ");
                name = sc.nextLine();
                if (name.isEmpty()) {
                    System.out.println("\n\t\tName cannot be empty. Please try again.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("\n\t\tPhone number (e.g. 0121234567): ");
                phoneNo = sc.nextLine().trim();
                if (phoneNo.length() == 10 && phoneNo.matches("\\d+")) {
                    break;
                } else {
                    System.out.println("\n\t\t[Invalid phone number format. Please enter exactly 10 digits]\n");
                }
            }

            while (true) {
                System.out.print("\n\t\tEnter IC Number: (E.g. 010131-12-19234): ");
                icNo = sc.nextLine();
                if (icNo.isEmpty()) {
                    System.out.println("\n\t\tIC Number cannot be blank. Please try agian.");
                } else {
                    if (icNo.length() <= 7) {
                        System.out.println("\n\t\tInvalid format. IC number is too short. Expected format: xxxxxx-xx-xxxx");
                    } else if (icNo.charAt(6) == '-' && icNo.charAt(9) == '-') {
                        break;
                    } else {
                        errorMessage();
                    }
                }
            }

            while (true) {
                System.out.print("\n\t\tEnter gender (M/F): ");
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("\n\t\tGender cannot be empty. Please try again.");
                } else {
                    if (input.length() != 1) {
                        System.out.println("\n\t\tInvalid format. Only one character is allowed for gender. Expected input: F (OR) M");
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
                System.out.print("\n\t\tEnter Salary: RM ");
                salary = sc.nextDouble();
                sc.nextLine();
                if (salary < 0 || salary == 0) {
                    System.out.println("\n\t\tSalary must be positive number.");
                } else {
                    break;
                }
            }
            Set<Integer> existingIds = new HashSet<>();
            int highestStaffNum = 0;
            String prefix = "S";

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
                        System.err.println("\n\t\tWarning: Skipping staff with unexpected IF format: " + staff.getId());
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
            System.out.println("\n\t\t==================================================================");
            System.out.println("\t\t|\t\tPlease confirm the details below:\t\t |");
            System.out.println("\t\t==================================================================");
            System.out.println("\t\tStaff ID: " + id);
            System.out.println("\t\tName: " + name);
            System.out.println("\t\tGender: " + gender);
            System.out.println("\t\tIC Number: " + icNo);
            System.out.printf("\t\tSalary: RM %.2f%n", salary);
            System.out.println("\n\t\t==================================================================");

            while (true) {
                System.out.print("\n\t\tDo you confirm the details above are correct? (yes/no): ");
                String confirmation = sc.nextLine().trim();

                if (confirmation.equalsIgnoreCase("yes")) {
                    detailsConfirm = true;
                    break;
                } else if (confirmation.equalsIgnoreCase("no")) {
                    System.out.print("\n\t\tDetails not confirmed. Please re-enter the staff information by pressing [ENTER] or press [Q] to exit: ");
                    String continueAdd = sc.nextLine().trim();
                    if (continueAdd.equalsIgnoreCase("q")) {
                        System.out.println("\n\t\tAdd new staff cancelled.");
                        return;
                    }
                    else{
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


        Staff staff = new Staff(id, name, phoneNo, gender, icNo, salary);
        staffs.add(staff);
        writeStaffFile(staffs, "staff.txt");
        System.out.println("\n\t\tStaff is added successfully!");
        System.out.println("\n\t\tPress [ENTER] to continue..");
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
        choicesString.put(2, "Staff Name");
        choicesString.put(3, "Staff Phone Number");
        choicesString.put(4, "Staff Gender");
        choicesString.put(5, "Staff IC Number");
        choicesString.put(6, "Staff Salary");

        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tSEARCH STAFF SYSTEM\t\t\t |");
        System.out.println("\t\t==================================================================");
        System.out.println("\n\t\tWhat attribute do you want to search by?");

        while (true) {
            System.out.println("\n\t\t[1] Staff ID\n\t\t[2] Staff Name\n\t\t[3] Staff Phone Number\n\t\t[4] Staff Gender\n\t\t[5] Staff IC Number\n\t\t[6] Staff Salary\n\t\t[7] Exit to Main Menu");
            System.out.print("\n\t\tEnter your choice (1-8): ");

            if (sc.hasNextInt()) {
                search = sc.nextInt();
                sc.nextLine();

                if (search < 1 || search > 8) {
                    errorMessageNumber();
                } else {
                    if (search == 8) {
                        return;
                    }

                    String stringChosen = choicesString.getOrDefault(search, "Invalid choice");
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
                    System.out.print("\t\tEnter the exact staff ID: ");
                    String id = sc.nextLine().trim();
                    if (id.isEmpty()) {
                        System.out.println("\t\tStaff ID cannot be empty. Please try again.");
                        continue;
                    }
                    Staff byId = searchStaffByAttributes(staffs, search, id);
                    if (byId != null) {
                        displayStaffDetails(byId);
                        break;
                    } else {
                        System.out.println("\n\t\tStaff with ID '" + id + "' not found.");
                        System.out.print("\t\tSearch again by Staff ID? (yes/no): ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                }
                break;

            case 2:
                while (true) {
                    System.out.print("\t\tEnter the Staff Name: ");
                    String name = sc.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println("\t\tStaff name cannot be empty. Please try again.");
                        continue;
                    }
                    Staff byName = searchStaffByAttributes(staffs, search, name);
                    if (byName != null) {
                        displayStaffDetails(byName);
                        break;
                    } else {
                        System.out.println("\n\t\tNo staff with name '" + name + "'.");
                        System.out.print("\t\tSearch again by Staff name? (yes/no): ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                }
                break;

            case 3:
            while (true) {
                System.out.print("\t\tEnter the Staff Phone Number: ");
                String phoneNo = sc.nextLine().trim();
                if (phoneNo.isEmpty()) {
                    System.out.println("\t\tStaff Phone Number cannot be empty. Please try again.");
                    continue;
                }
                if (phoneNo.length() == 10 && phoneNo.matches("\\d+")) {
                    Staff byPhone = searchStaffByAttributes(staffs, search, phoneNo);
                    if (byPhone != null) {
                        break;
                    } else {
                        System.out.println("\n\t\tStaff with phone number'" + phoneNo + "' not found.");
                        System.out.print("\t\tSearch again by staff phone number? (yes/no): ");
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

            case 4:
            while (true) {
                System.out.print("\t\tEnter the Staff Gender (e.g., M or F): ");
                String gender = sc.nextLine().trim();
                if (gender.isEmpty()) {
                    System.out.println("\t\tStaff gender cannot be empty. Please try again.");
                    continue;
                }
        
                List<Staff> foundStaff = findAllStaffByGender(staffs, gender);
        
                if (foundStaff.isEmpty()) {
                    System.out.println("\n\t\tNo staff found with gender '" + gender + "'.");
                } else if (foundStaff.size() == 1) {
                    System.out.println("\n\t\tFound one staff member:");
                    displayStaffDetails(foundStaff.get(0));
                    break;
                } else {
                    System.out.println("\n\t\tMultiple staff members found with gender '" + gender + "'. Please select one by Staff ID:");
                    for (int i = 0; i < foundStaff.size(); i++) {
                        System.out.println("\t\t[" + (i + 1) + "] " + foundStaff.get(i).getId() + " - " + foundStaff.get(i).getName());
                    }
        
                    Staff selectedStaff = null;
                    while (selectedStaff == null) {
                        System.out.print("\t\tEnter the Staff ID to view details: ");
                        String selectedId = sc.nextLine().trim();
                        for (Staff staff : foundStaff) {
                            if (staff.getId().equalsIgnoreCase(selectedId)) {
                                selectedStaff = staff;
                                break;
                            }
                        }
                        if (selectedStaff == null) {
                            System.out.println("\t\tInvalid Staff ID from the list. Please try again.");
                        }
                    }
                    System.out.println("\n\t\tShowing details for selected staff:");
                    displayStaffDetails(selectedStaff);
                    break;
                }
        
                if (foundStaff.isEmpty()) {
                     System.out.print("\t\tSearch again by Staff gender? (yes/no): ");
                     if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                         break;
                     }
                } else {
                     break;
                }
            }
            break;

            case 5:
            while (true) {
                System.out.print("\t\tEnter the Staff IC Number: ");
                String icNo = sc.nextLine().trim();
                if (icNo.isEmpty()) {
                    System.out.println("\t\tStaff IC number cannot be empty. Please try again.");
                    continue;
                }
                Staff byIc = searchStaffByAttributes(staffs, search, icNo);
                if (byIc != null) {
                    displayStaffDetails(byIc);
                    break;
                } else {
                    System.out.println("\n\t\tNo staff with ic number '" + icNo + "'.");
                    System.out.print("\t\tSearch again by Staff ic number? (yes/no): ");
                    if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                        break;
                    }
                }
            }
            break;

            case 6:
            while (true) {
                System.out.print("\t\tEnter the Staff salary: RM ");
                String salary = sc.nextLine().trim();
                if (salary.isEmpty()) {
                    System.out.println("\t\tStaff salary cannot be empty. Please try again.");
                    continue;
                }
                Staff bySalary = searchStaffByAttributes(staffs, search, salary);
                if (bySalary != null) {
                    displayStaffDetails(bySalary);
                    break;
                } else {
                    System.out.println("\n\t\tNo staff with salary RM '" + salary + "'.");
                    System.out.print("\t\tSearch again by Staff salary? (yes/no): ");
                    if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                        break;
                    }
                }
            }
            break;

            default:
                System.out.println("\n\t\tUnexpected error in search type selection.");
                break;
        }

        System.out.println("\n\t\t------------------------------------------------------------------");
        if (search != 8) {
            System.out.println("\t\tSearch finished. What action would you like to perform next?");
            int choice = 0;
            while (true) {
                System.out.println("\n\t\t[1] Edit Room\n\t\t[2] Delete Room\n\t\t[3] Return to Main Menu");
                System.out.print("\t\tEnter your choice (1-3): ");
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
                    System.out.println("\t\tInvalid action choice.");
                    break;
            }

            System.out.println("\n\t\tPress [ENTER] to continue...");
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
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tEDIT STAFF SYSTEM\t\t\t |");
        System.out.println("\t\t==================================================================");
        Scanner sc = new Scanner(System.in);
        boolean searchAgain = true;
    
        while (searchAgain) {
            System.out.print("\n\t\tEnter Staff ID or Name to edit (or type 'exit' to return): ");
            String input = sc.nextLine().trim();
    
            if (input.equalsIgnoreCase("exit")) {
                searchAgain = false;
                break;
            }
            if (input.isEmpty()) {
                 System.out.println("\t\tInput cannot be empty.");
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
                System.out.println("\n\t\tStaff found by ID:");
                displayStaffDetails(staffToEdit);
                editStaffDetails(staffToEdit); 
    
            } else if (nameMatches.isEmpty()) {
                System.out.println("\n\t\tNo staff found with ID or Name '" + input + "'.");
    
            } else if (nameMatches.size() == 1) {
                staffToEdit = nameMatches.get(0);
                System.out.println("\n\t\tStaff found by Name:");
                displayStaffDetails(staffToEdit);
                editStaffDetails(staffToEdit); 
    
            } else {
                System.out.println("\n\t\tMultiple staff found with the name '" + input + "'. Please specify by Staff ID:");
                for (int i = 0; i < nameMatches.size(); i++) {
                    System.out.println("\t\t[" + (i + 1) + "] " + nameMatches.get(i).getId() + " - " + nameMatches.get(i).getName());
                }
    
                Staff selectedStaff = null;
                while (selectedStaff == null) {
                    System.out.print("\t\tEnter the Staff ID to edit: ");
                    String selectedId = sc.nextLine().trim();
                    for (Staff staff : nameMatches) {
                        if (staff.getId().equalsIgnoreCase(selectedId)) {
                            selectedStaff = staff;
                            break;
                        }
                    }
                    if (selectedStaff == null) {
                        System.out.println("\t\tInvalid Staff ID from the list. Please try again.");
                    }
                }
                staffToEdit = selectedStaff;
                System.out.println("\n\t\tSelected staff for editing:");
                displayStaffDetails(staffToEdit);
                editStaffDetails(staffToEdit); 
            }
    
            if (searchAgain) {
                 System.out.print("\n\t\tSearch for another staff member to edit? (yes/no): ");
                 if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                      searchAgain = false;
                 }
            }
        }
    
        System.out.println("\n\t\tReturning to main page...");
        mainPage();
    }

    public static void editStaffDetails(Staff staff) {
        Scanner sc = new Scanner(System.in);
        boolean continueEditing = true;
        while (continueEditing) {

            System.out.println("\n\t\tWhat do you want to edit? (Enter numbers separated by commas, e.g., 1,5)");
            System.out.println("\t\t[1] Name");
            System.out.println("\t\t[2] Phone Number");
            System.out.println("\t\t[3] Gender");
            System.out.println("\t\t[4] IC Number");
            System.out.println("\t\t[5] Salary");
            System.out.println("\t\t[6] Finish Editing This Staff");
            System.out.print("\t\tEnter your choice(s): ");
            String editChoicesInput = sc.nextLine().trim();

            if (editChoicesInput.isEmpty()) {
                System.out.println("\n\t\tNo choice entered. Please select fields to edit or choose 6 to finish.");
                continue;
            }

            if (editChoicesInput.contains("6")) {
                System.out.println("\n\t\tFinished editing Staff ID: " + staff.getId());
                continueEditing = false;
                break;
            }

            String[] options = editChoicesInput.split(",");
            boolean changesMadeThisRound = false;

            for (String option : options) {
                String trimmedOption = option.trim();
                switch (trimmedOption) {
                    case "1":
                        System.out.print("\n\t\tEnter the new Name: ");
                        String newName = sc.nextLine().trim();
                        if (!newName.isEmpty()) {
                            staff.setName(newName);
                            updateStaffFile(staff.getId(), 1, newName);
                            System.out.println("\t\t-> Name updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("\n\t\tName cannot be empty. Update skipped.");
                        }
                        break;

                    case "2":
                        System.out.print("\n\t\tEnter the new Phone Number: ");
                        String newPhone = sc.nextLine().trim();
                        if (!newPhone.isEmpty()) {
                            staff.setPhoneNo(newPhone);
                            updateStaffFile(staff.getId(), 2, newPhone);
                            System.out.println("\t\t-> Phone Number updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("\n\t\tPhone Number cannot be empty. Update skipped.");
                        }
                        break;

                    case "3":
                        char newGender = ' ';
                        while (newGender == ' ') {
                            System.out.print("\n\t\tEnter the new Gender (M/F): ");
                            String genderInput = sc.nextLine().trim().toUpperCase();
                            if (genderInput.length() == 1 && (genderInput.charAt(0) == 'M' || genderInput.charAt(0) == 'F')) {
                                newGender = genderInput.charAt(0);
                                staff.setGender(newGender);
                                updateStaffFile(staff.getId(), 3, String.valueOf(newGender));
                                System.out.println("\t\t-> Gender updated successfully!");
                                changesMadeThisRound = true;
                            } else {
                                System.out.println("\n\t\tInvalid input. Please enter 'M' or 'F'.");
                            }
                        }
                        break;

                    case "4":
                        System.out.print("\n\t\tEnter the new IC Number: ");
                        String newIc = sc.nextLine().trim();
                        if (!newIc.isEmpty()) {
                            staff.setIcNo(newIc);
                            updateStaffFile(staff.getId(), 4, newIc);
                            System.out.println("\t\t-> IC Number updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("\t\tIC Number cannot be empty. Update skipped.");
                        }
                        break;

                    case "5":
                        double newSalary = -1.0;
                        while (newSalary < 0) {
                            System.out.print("\n\t\tEnter the new Salary (e.g., 2500.50): RM ");
                            try {
                                newSalary = sc.nextDouble();
                                sc.nextLine();
                                if (newSalary < 0) {
                                    System.out.println("\n\t\tSalary cannot be negative.");
                                    newSalary = -1.0;
                                } else {
                                    staff.setSalary(newSalary);
                                    updateStaffFile(staff.getId(), 5, String.valueOf(newSalary));
                                    System.out.println("\t\t-> Salary updated successfully!");
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
                System.out.println("\n\t\tCurrent details for Staff ID: " + staff.getId());
                displayStaffDetails(staff);
            }

            if (!continueEditing) {
                break;
            }

            System.out.print("\n\t\tEdit more details for this staff member? (yes/no): ");
            String more = sc.nextLine().trim();
            if (!more.equalsIgnoreCase("yes")) {
                continueEditing = false;
                System.out.println("\n\t\tFinished editing Staff ID: " + staff.getId());
            }

        }
    }

    public static void deleteStaff(ArrayList<Staff> currentStaffs) {
        clear();
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tDELETE STAFF SYSTEM\t\t\t |");
        System.out.println("\t\t==================================================================");
        String filename = "staff.txt";

        if (currentStaffs == null) {
            System.err.println("\n\t\tError: Cannot delete from a null list.");
            return;
        }

        System.out.print("\n\t\tPlease enter the staff ID that you want to delete: ");
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
            System.out.println("\n\t\tStaff Found:");
            displayStaffDetails(staffToDel);
            while (true) {
                System.out.print("\n\t\tConfirm to delete this staff? (yes/no): ");
                String deleteConfirm = sc.nextLine().trim();
                if (deleteConfirm.equalsIgnoreCase("yes")) {
                    currentStaffs.remove(staffIndex);
                    System.out.println("\n\t\tStaff with ID '" + idToDelete + "'("+ staffToDel.getName() +") has been deleted.");
                    writeStaffFile(currentStaffs, filename);
                    break;
                } else if (deleteConfirm.equalsIgnoreCase("no")) {
                    System.out.println("\n\t\tDeletion cancelled for Staff ID '" + idToDelete + "'("+ staffToDel.getName() + "').");
                    break;
                } else {
                    errorMessageWord();
                }
            }
        } else {
            System.out.println("\n\t\tStaff with ID '"+ idToDelete + "'("+ staffToDel.getName() + ")' not found in the list. No changes made.");
        }
        System.out.println("\n\t\tPress [ENTER] to continue...");
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
                    match = staff.getName().equalsIgnoreCase(value);
                    break;
                case 3:
                    match = staff.getPhoneNo().equalsIgnoreCase(value);
                    break;
                case 4:
                    char staffGender = staff.getGender();
                    char valueChar = value.charAt(0);
                    match = (Character.toUpperCase(staffGender) == Character.toUpperCase(valueChar));
                    break;
                case 5:
                    match = staff.getIcNo().equalsIgnoreCase(value);
                    break;
                case 6:
                    double staffSalary = staff.getSalary();
                    match = (staffSalary == (Double.parseDouble(value)));
                    break;

            }

            if (match) {
                matchedStaff = staff;
            }
        }
        if (matchedStaff == null) {
            System.out.println("\n\t\tNo matching staff found.");
        } else {
            System.out.println("\n\t\tMatched staff(s) found.");
        }
        return matchedStaff;
    }

    public static void displayStaffDetails(Staff staff) {
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\tStaff ID: " + staff.getId());
        System.out.println("\t\tName: " + staff.getName());
        System.out.println("\t\tPhone Number: " + staff.getPhoneNo());
        System.out.println("\t\tGender: " + staff.getGender());
        System.out.println("\t\tIC Number: " + staff.getIcNo());
        System.out.printf("\t\tSalary: RM %.2f ", staff.getSalary());
        System.out.println("\n\t\t==================================================================");
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
            while (staffRead.hasNextLine()) {
                String line = staffRead.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String phoneNo = parts[2].trim();
                    String genderString = parts[3].trim();
                    char gender = genderString.isEmpty() ? 'U' : Character.toUpperCase(genderString.charAt(0)); // Handle empty gender
                    String ic = parts[4].trim();
                    double salary = Double.parseDouble(parts[5].trim());
    
                    Staff readStaff = new Staff(id, name, phoneNo, gender, ic, salary);
                    staffs.add(readStaff);
                } 
            }
        } catch (FileNotFoundException e) {
            System.err.println("File 'staff.txt' not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file 'staff.txt': " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing salary: " + e.getMessage());
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
                    return; // Exit if file creation fails
                }
            } catch (IOException ioEx) {
                System.err.println("Error: Couldn't create file '" + filename + "': " + ioEx.getMessage());
                return; // Exit on error
            }
        }
    
        try (FileWriter fileWriter = new FileWriter(staffFile, false)) {
            for (Staff staff : staffs) {
                String gender = String.valueOf(staff.getGender());
                String salary = String.valueOf(staff.getSalary());
    
                String line = String.join("|",
                        staff.getId(),
                        staff.getName(),
                        staff.getPhoneNo(),
                        gender,
                        staff.getIcNo(),
                        salary
                );
                fileWriter.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file '" + filename + "': " + e.getMessage());
        }
    }

    public static void errorMessage() {
        System.out.print("\n\t\t===================================\n");
        System.out.println("\n\t\tError. Invalid input. Please try again.");
    }

    public static void errorMessageNumber() {
        System.out.print("\n\t\t===================================\n");
        System.out.println("\n\t\tError. Invalid input. Please input the correct number.");
    }

    public static void errorMessageWord() {
        System.out.print("\n\t\t===================================\n");
        System.out.println("\n\t\tError. Invalid input. Please input the correct word.");
    }

    public static void errorMessageBlank() {
        System.out.print("\n\t\t===================================\n");
        System.out.println("\n\t\tError. Input cannot be empty. Please try again");
    }

    public static void clear() {
        System.out.println("\033[H\033[2J");
    }

}
