import java.io.*;
import java.util.*;

public class StaffManagement {

    public static ArrayList<Staff> staffs = new ArrayList<>();

    public static void mainPageStaff() {
        staffs = readStaffFile();
        Scanner sc = new Scanner(System.in);
        System.out.println("Manage Staff System");
        System.out.println("=====================");

        while (true) {
            System.out.println("Which action you preferred to do?\n[1] Show All Staff\n[2] Add Staff\n[3] Edit Staff\n[4] Remove Staff\n[5] Exit");
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
                    confirmationPrompt = "Confirm to show all staff? (yes/no): ";
                    break;
                case 2:
                    confirmationPrompt = "Confirm to add new staff? (yes/no): ";
                    break;
                case 3:
                    confirmationPrompt = "Confirm to edit an existing staff? (yes/no): ";
                    break;
                case 4:
                    confirmationPrompt = "Confirm to delete an existing staff? (yes/no): ";
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
                        showStaff();
                        break;
                    case 2:
                        addNewStaff(staffs);
                        break;
                    case 3:
                        editStaff(staffs);
                        break;
                    case 4:
                        deleteStaff(staffs);
                        break;
                }
                System.out.println("\nAction completed. Press [ENTER] to return to staff menu...");
                sc.nextLine();
            }
        }
    }

    public static void addNewStaff(ArrayList<Staff> currentStaffList) {
        Scanner sc = new Scanner(System.in);
        boolean detailsConfirm = false;

        String id, name, phoneNo, icNo = "";
        char gender;
        double salary = 0;

        do {
            System.out.println("Staff ID will auto generated.");
            System.out.println("Enter the details below:");
            while (true) {
                System.out.print("Staff Name: ");
                name = sc.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty. Please try again.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("Phone Number(E.g. 015-463 1254): ");
                phoneNo = sc.nextLine();
                if (phoneNo.isEmpty()) {
                    System.out.println("Phone number cannot be empty. Please try again.");
                } else {
                    if (phoneNo.length() <= 3) {
                        System.out.println("Invalid format. Phone number is too short. Expected format: 01x-xxxx xxxx");
                    } else if (phoneNo.charAt(3) == '-') {
                        break;
                    } else {
                        errorMessage();
                    }
                }
            }

            while (true) {
                System.out.print("Enter IC Number: (E.g. 010131-12-19234): ");
                icNo = sc.nextLine();
                if (icNo.isEmpty()) {
                    System.out.println("IC Number cannot be blank. Please try agian.");
                } else {
                    if (icNo.length() <= 7) {
                        System.out.println("Invalid format. IC number is too short. Expected format: xxxxxx-xx-xxxx");
                    } else if (icNo.charAt(6) == '-' && icNo.charAt(9) == '-') {
                        break;
                    } else {
                        errorMessage();
                    }
                }
            }

            while (true) {
                System.out.print("Enter gender (M/F): ");
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Gender cannot be empty. Please try again.");
                } else {
                    if (input.length() != 1) {
                        System.out.println("Invalid format. Only one character is allowed for gender. Expected input: F (OR) M");
                    } else if (input.charAt(0) == 'F') {
                        gender = 'F';
                        break;
                    } else if (input.charAt(0) == 'M') {
                        gender = 'M';
                        break;
                    } else {
                        errorMessage();
                    }
                }
            }

            while (true) {
                System.out.print("Enter Salary: RM ");
                salary = sc.nextDouble();
                sc.nextLine();
                if (salary < 0) {
                    System.out.println("Salary must be positive number.");
                } else {
                    break;
                }
            }
            Set<Integer> existingIds = new HashSet<>();
            int highestStaffNum = 0;
            String prefix = "S";
            
            for (Staff staff : currentStaffList){
                if (staff.getId() != null && staff.getId().startsWith(prefix)){
                    try {
                        String numberPart = staff.getId().substring(prefix.length());
                        int currentIdNum = Integer.parseInt(numberPart);
                        existingIds.add(currentIdNum);
                        if ( currentIdNum > highestStaffNum){
                            highestStaffNum =  currentIdNum;
                        }
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.err.println("Warning: Skipping staff with unexpected IF format: " + staff.getId());
                    }
                }
            }

            int nextStaffId = -1;
            for (int i = 1; i <= highestStaffNum; i++){
                if (!existingIds.contains(i)){
                    nextStaffId = i;
                    break;
                }
            }

            if (nextStaffId == -1){
                nextStaffId = highestStaffNum + 1;
            }
            id = prefix + String.format("%03d", nextStaffId);

            System.out.println("=================================");
            System.out.println("Please confirm the details below:");
            System.out.println("=================================");
            System.out.println("Staff ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Phone Number: " + phoneNo);
            System.out.println("Gender: " + gender);
            System.out.printf("Salary: RM %.2f%n", salary);
            System.out.println("==============================");

            while (true) {
                System.out.print("Do you confirm the details above are correct? (yes/no):");
                String confirmation = sc.nextLine().trim();

                if (confirmation.equalsIgnoreCase("yes")) {
                    detailsConfirm = true;
                    break;
                } else if (confirmation.equalsIgnoreCase("no")) {
                    System.out.print("\nDetails not confirmed. Please re-enter the staff information by pressing [ENTER] or press [Q] to exit");
                    String continueAdd = sc.nextLine();
                    if (continueAdd.equalsIgnoreCase("q")) {
                        break;
                    }
                } else {
                    errorMessageWord();
                }
            }

        } while (!detailsConfirm);

        Staff staff = new Staff(id, name, phoneNo, gender, icNo, salary);
        staffs.add(staff);
        try {
            FileWriter myWriter = new FileWriter("staff.txt", true);
            myWriter.write(id + "|" + name + "|" + phoneNo + "|" + gender + "|" + icNo + "|" + salary + System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("The staff details is failed to add. Please try again.");
        }
        System.out.println("Staff is added successfully!");
        System.out.println("Press [ENTER] to continue..");
        sc.nextLine();
        mainPageStaff();
    }

    public static void showStaff() {
        Scanner sc = new Scanner(System.in);
        int search = 0;

            Map<Integer, String> choicesString = new HashMap<>();
            choicesString.put(1, "Staff ID");
            choicesString.put(2, "Staff Name");
            choicesString.put(3, "Phone Number");
            choicesString.put(4, "Gender");
            choicesString.put(5,"Exit");

            while (true) {
                System.out.println("What kind of information you want to type in order to find the staff(s) info?\n[1] Staff ID\n[2] Staff Name\n[3] Phone Number\n[4] Gender\n[5] Exit");
                System.out.print("Please choose your action: ");
                if (sc.hasNextInt()) {
                    int choice = sc.nextInt();
                    sc.nextLine();
                    if (choice < 1 || choice > 5) {
                        System.out.println("Only number between (1-5) is allowed. Please retry.");
                    }
                    else {
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
                    System.out.print("Enter the Staff ID: ");
                    String staffId = sc.nextLine();
                    ArrayList<Staff> byId = searchStaffAttributes(staffs, search, staffId);
                    if (!byId.isEmpty()) {
                        showStaff(byId);
                        askUserContinueView(byId);
                    }
                    break;
                case 2:
                    System.out.print("Enter the Staff Name: ");
                    String staffName = sc.nextLine();
                    ArrayList<Staff> byName = searchStaffAttributes(staffs, search, staffName);
                    if (!byName.isEmpty()) {
                        showStaff(byName);
                        askUserContinueView(byName);
                    }
                    break;
                case 3:
                    System.out.print("Enter the Staff Phone Number: ");
                    String staffPhoneNo = sc.nextLine();
                    ArrayList<Staff> byPhone = searchStaffAttributes(staffs, search, staffPhoneNo);
                    if (!byPhone.isEmpty()) {
                        showStaff(byPhone);
                        askUserContinueView(byPhone);
                    }
                    break;
                case 4:
                    System.out.print("Enter the Staff Gender: ");
                    String staffGender = sc.next();
                    ArrayList<Staff> byGender = searchStaffAttributes(staffs, search, staffGender);
                    if (!byGender.isEmpty()) {
                        showStaff(byGender);
                        askUserContinueView(byGender);
                    }
                    break;
                case 5:
                    mainPageStaff();
                    break;
            }
        }

    public static void editStaff(ArrayList<Staff> staffs){
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        while (true) { 
            int update;
            String id = "";
            System.out.print("Enter ID/Name to edit: ");
            String input = sc.nextLine();
            for (Staff staff: staffs){
                if (staff.getId().equalsIgnoreCase(input)){
                    found = true;
                    System.out.println("Staff found: ");
                    displayStaffDetails(staff);
                    editStaffDetails(staff,input);
                    break;

                }
                else if (staff.getName().equalsIgnoreCase(input)){
                    found = true;
                    System.out.println("Staff found: ");
                    displayStaffDetails(staff);
                    input = staff.getId();
                    editStaffDetails(staff,input);
                    break;
                }
            }
            break;
        }
        System.out.println("Press [ENTER] to continue...");
        sc.nextLine();
        mainPageStaff();
    }

    public static void editStaffDetails(Staff staff, String input){
        Scanner sc = new Scanner(System.in);
        boolean continueEditing = true;
        while(continueEditing) {

            System.out.println("What do you want to edit? (Enter numbers separated by commas, e.g., 1,5)");
            System.out.println("[1] Name");
            System.out.println("[2] Phone Number");
            System.out.println("[3] Gender");
            System.out.println("[4] IC Number");
            System.out.println("[5] Salary");
            System.out.println("[6] Finish Editing This Staff");
            System.out.print("Enter your choice(s): ");
            String editChoicesInput = sc.nextLine().trim();

            if (editChoicesInput.isEmpty()) {
                System.out.println("No choice entered. Please select fields to edit or choose 6 to finish.");
                continue;
            }

             if (editChoicesInput.contains("6")) {
                 System.out.println("Finished editing Staff ID: " + staff.getId());
                 continueEditing = false;
                 break;
             }


            String[] options = editChoicesInput.split(",");
            boolean changesMadeThisRound = false;

            for (String option : options) {
                String trimmedOption = option.trim();
                switch (trimmedOption) {
                    case "1":
                        System.out.print("Enter the new Name: ");
                        String newName = sc.nextLine().trim();
                        if (!newName.isEmpty()) {
                            staff.setName(newName);
                            updateStaffFile(staff.getId(), 1, newName);
                            System.out.println("-> Name updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("Name cannot be empty. Update skipped.");
                        }
                        break;

                    case "2":
                        System.out.print("Enter the new Phone Number: ");
                        String newPhone = sc.nextLine().trim();
                        if (!newPhone.isEmpty()) {
                            staff.setPhoneNo(newPhone);
                            updateStaffFile(staff.getId(), 2, newPhone);
                            System.out.println("-> Phone Number updated successfully!");
                            changesMadeThisRound = true;
                        } else {
                            System.out.println("Phone Number cannot be empty. Update skipped.");
                        }
                        break;

                    case "3":
                        char newGender = ' ';
                        while (newGender == ' ') {
                            System.out.print("Enter the new Gender (M/F): ");
                            String genderInput = sc.nextLine().trim().toUpperCase();
                            if (genderInput.length() == 1 && (genderInput.charAt(0) == 'M' || genderInput.charAt(0) == 'F')) {
                                newGender = genderInput.charAt(0);
                                staff.setGender(newGender);
                                updateStaffFile(staff.getId(), 3, String.valueOf(newGender));
                                System.out.println("-> Gender updated successfully!");
                                changesMadeThisRound = true;
                            } else {
                                System.out.println("Invalid input. Please enter 'M' or 'F'.");
                            }
                        }
                        break;

                    case "4":
                        System.out.print("Enter the new IC Number: ");
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
                            System.out.print("Enter the new Salary (e.g., 2500.50): RM ");
                            try {
                                newSalary = sc.nextDouble();
                                sc.nextLine();
                                if (newSalary < 0) {
                                    System.out.println("Salary cannot be negative.");
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
                         continueEditing = false;
                         break;

                    default:
                        System.out.println("Invalid choice '" + trimmedOption + "'. Skipping.");
                        break;
                }

                if (!continueEditing) break;

            }

            if (changesMadeThisRound) {
                 System.out.println("\nCurrent details for Staff ID: " + staff.getId());
                 displayStaffDetails(staff);
             }

             if (!continueEditing) {
                 break;
             }

             System.out.print("\nEdit more details for this staff member? (yes/no): ");
             String more = sc.nextLine().trim();
             if (!more.equalsIgnoreCase("yes")) {
                 continueEditing = false;
                 System.out.println("Finished editing Staff ID: " + staff.getId());
             }

        }
    }

    public static void deleteStaff(ArrayList<Staff> currentStaff) {
        String filename = "staff.txt";

        if (currentStaff == null) {
            System.err.println("Error: Cannot delete from a null list.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the staff ID that you want to delete: ");
        String idToDelete = sc.nextLine().trim();

        boolean staffExists = false;
        for (Staff staff : currentStaff) {
            if (staff != null && staff.getId() != null && staff.getId().equalsIgnoreCase(idToDelete)) {
                staffExists = true;
                displayStaffDetails(staff);
                break;
            }
        }

        if (!staffExists) {
            System.out.println("\nStaff with ID '" + idToDelete + "' not found in the list. No deletion performed.");
            return;
        }

        while (true) {
            System.out.print("Staff found. Confirm to delete staff with ID '" + idToDelete + "'? (yes/no): ");
            String deleteConfirm = sc.nextLine().trim();

            if (deleteConfirm.equalsIgnoreCase("yes")) {
                boolean removed = currentStaff.removeIf(staff ->
                    staff != null && staff.getId() != null && staff.getId().equalsIgnoreCase(idToDelete)
                );//check one by one if it is null

                if (removed) {
                    System.out.println("\nStaff with ID '" + idToDelete + "' has been removed from the list.");
                    writeStaffFile(currentStaff, filename);
                } else {
                    System.out.println("\nError: Staff with ID '" + idToDelete + "' was found initially but could not be removed now.");
                }
                break;

            } else if (deleteConfirm.equalsIgnoreCase("no")) {
                System.out.println("\nDeletion cancelled for Staff ID '" + idToDelete + "'.");
                break;  

            } else {
                errorMessageWord();
            }
        }
    }  
            
    public static void askUserContinueView(ArrayList<Staff> staff) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to continue to view details?\n[1] View\n[2] Exit");
        System.out.print("Enter your action: ");
        String input = sc.nextLine().trim();
        while (true) {
            if (Integer.parseInt(input) < 1 || Integer.parseInt(input) > 2) {
                System.out.println("Incorrect input. Please try again.");
            } else {
                if (input.equalsIgnoreCase("1")) {
                    chooseStaff(staff);
                    break;
                } else {
                    return;
                }
            }
        }
    }

    public static ArrayList<Staff> searchStaffAttributes(ArrayList<Staff> staffs, int option, String value) {
        staffs = readStaffFile();
        ArrayList<Staff> matchedStaff = new ArrayList<>();
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
            }
           
            if (match) {
                matchedStaff.add(staff);
            }
        }
        if (matchedStaff.isEmpty()) {
            System.out.println("No matching staff found.");
        }
        else {
            System.out.println("Matched staff found.");

        }
        return matchedStaff;
    }

    public static void displayStaffDetails(Staff staff) {
        System.out.println("===========================");
        System.out.println("Staff ID: " + staff.getId());
        System.out.println("Name: " + staff.getName());
        System.out.println("Phone Number: " + staff.getPhoneNo());
        System.out.println("Gender: " + staff.getGender());
        System.out.println("IC Number: " + staff.getIcNo());
        System.out.println("Salary: RM " + staff.getSalary());
        System.out.println("===========================");
    }

    public static void showStaff(ArrayList<Staff> staffs) {
        for (int i = 1; i < staffs.size(); i++) {
            Staff currentStaff = staffs.get(i);
            System.out.println((i) + "." + currentStaff.getName());
        }
    }

    public static ArrayList<Staff> readStaffFile() {
        File staff = new File("staff.txt");
        try {
            Scanner staffRead = new Scanner(staff);
            while (staffRead.hasNextLine()) {
                String line = staffRead.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String id = parts[0].trim();
                    String name = parts[1];
                    String phoneNo = parts[2];
                    String genderString = parts[3];
                    char gender = Character.toUpperCase(genderString.trim().charAt(0));
                    String ic = parts[4];
                    double salary = Double.parseDouble(parts[5].trim());

                    Staff readStaff = new Staff(id, name, phoneNo, gender, ic, salary);
                    staffs.add(readStaff);

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found");
        }
        return staffs;
    }

    public static void chooseStaff(ArrayList<Staff> staff) {
        Scanner sc = new Scanner(System.in);
        String choose;
        int staffSelected;
        do {
            showStaff(staff);
            System.out.print("Choose the staff you want(e.g. 1): ");
            choose = sc.nextLine().trim();
            if (Integer.parseInt(choose) > 0 && Integer.parseInt(choose) < staff.size()) {
                staffSelected = (Integer.parseInt(choose) - 1);
                displayStaffDetails(staff.get(staffSelected));
                break;
            }
        } while (Integer.parseInt(choose) < 1 || Integer.parseInt(choose) > staff.size());
    }

    public static void errorMessage() {
        System.out.print("===================================\n");
        System.out.println("Error. Invalid input. Please try again.");
    }

    public static void errorMessageNumber() {
        System.out.print("===================================\n");
        System.out.println("Error. Invalid input. Please input the correct number.");
    }

    public static void errorMessageWord() {
        System.out.print("===================================\n");
        System.out.println("Error. Invalid input. Please input the correct word.");
    }

    public static void errorMessageBlank() {
        System.out.print("===================================\n");
        System.out.println("Error. Input cannot be empty. Please try again");
    }

    public static void updateStaffFile(String staffId, int update, String newValue){
        File staff = new File("staff.txt");
        try {
            Scanner updateStaff = new Scanner(staff);
            List<String> updatedLines = new ArrayList<>();

            while (updateStaff.hasNextLine()) {
                String line = updateStaff.nextLine();
                String[] parts = line.split("\\|");

                if (parts.length > 0 && staffId.equals(parts[0].trim())) {
                    switch (update) {
                        case 1: //Name
                            parts[1] = newValue;
                            break;
                        case 2: //Phone Number
                            parts[2] = newValue;
                            break;
                        case 3: //Gender
                            parts[3] = newValue;
                            break;
                        case 4: //IC Number
                            parts[4] = newValue;
                            break;
                        case 5: //Salary
                            parts[5] = newValue;
                            break;
                    }
                }
                updatedLines.add(String.join("|", parts)); //become back the format in file and add into updatedLines array
            }

            try (FileWriter writer = new FileWriter("staff.txt", false)) { // false to overwrite file
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }
    
    public static void writeStaffFile(ArrayList<Staff> staffs, String filename) {
        File staffFile = new File(filename);

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
}
