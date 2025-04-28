
import java.io.*;
import java.util.*;

public class ManageRoom {

    public static ArrayList<Room> rooms = new ArrayList<>();

    public static ArrayList<String> descriptions = new ArrayList<>(Arrays.asList(
            "A simple room with essential amenities for solo travelers or couples.",
            "A more spacious and upgraded room with additional amenities.",
            "A larger room with multiple beds, ideal for families or small groups.",
            "A luxurious room with separate living and sleeping areas.",
            "Designed for business travelers, featuring a workspace and premium amenities."
    ));

    public static void mainPage() {
        rooms = readRoomFile();
        Scanner sc = new Scanner(System.in);
        int action = 0;

        while (action != 5) {
            while (true) {
                clear();
                System.out.println("\n\t\t============================================");
                System.out.println("\t\t|\tHOTEL ROOM MANAGEMENT SYSTEM\t   |");
                System.out.println("\t\t============================================");
                System.out.println("\n\t\tPlease select an option: ");
                System.out.println("\n\t\t[1] Add New Room\n\t\t[2] Edit Room\n\t\t[3] Delete Room\n\t\t[4] Search Room\n\t\t[5] Exit");
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
                    while (true) {
                        System.out.print("\n\t\tConfirm to add new room? (yes/no): ");
                        String addConfirm = sc.nextLine();
                        if (addConfirm.equalsIgnoreCase("yes")) {
                            addNewRoom(rooms);
                            break;
                        } else if (addConfirm.equalsIgnoreCase("no")) {
                            action = 0;
                            break;
                        } else {
                            errorMessageWord();
                        }
                    }
                    break;
                case 2:
                    while (true) {
                        System.out.print("\n\t\tConfirm to edit room? (yes/no): ");
                        String editConfirm = sc.nextLine();
                        if (editConfirm.equalsIgnoreCase("yes")) {
                            editRoom(rooms);
                            break;
                        } else if (editConfirm.equalsIgnoreCase("no")) {
                            action = 0;
                            break;
                        } else {
                            errorMessageWord();
                        }
                    }
                    break;
                case 3:
                    while (true) {
                        System.out.print("\n\t\tConfirm to delete room? (yes/no): ");
                        String delConfirm = sc.nextLine();
                        if (delConfirm.equalsIgnoreCase("yes")) {
                            deleteRoom(rooms);
                            break;
                        } else if (delConfirm.equalsIgnoreCase("no")) {
                            action = 0;
                            break;
                        } else {
                            errorMessageWord();
                        }
                    }
                    break;
                case 4:
                    while (true) {
                        System.out.print("\n\t\tConfirm to show room? (yes/no): ");
                        String showConfirm = sc.nextLine();
                        if (showConfirm.equalsIgnoreCase("yes")) {
                            showRoom(rooms);
                            break;
                        } else if (showConfirm.equalsIgnoreCase("no")) {
                            action = 0;
                            break;
                        } else {
                            errorMessageWord();
                        }
                    }
                    break;
                case 5:
                    while (true) {
                        System.out.print("\n\t\tConfirm to exit? (yes/no): ");
                        String exitConfirm = sc.nextLine();
                        if (exitConfirm.equalsIgnoreCase("yes")) {
                            Main.showMainMenu();
                            break;
                        } else if (exitConfirm.equalsIgnoreCase("no")) {
                            action = 0;
                            break;
                        } else {
                            errorMessageWord();;
                        }
                    }
                    break;
            }
        }
    }

    public static void showRoom(ArrayList<Room> rooms) {
        clear();
        Scanner sc = new Scanner(System.in);
        int search = 0;
        String type = "";

        Map<Integer, String> choicesString = new HashMap<>();
        choicesString.put(1, "Room ID");
        choicesString.put(2, "Room Floor");
        choicesString.put(3, "Room Type");
        choicesString.put(4, "Room Capacity");
        choicesString.put(5, "Room Description");
        choicesString.put(6, "Room Price");
        choicesString.put(7, "Room Status");
        choicesString.put(8, "Exit");

        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tSEARCH ROOM SYSTEM\t\t\t |");
        System.out.println("\t\t==================================================================");
        System.out.println("\n\t\tWhat attribute do you want to search by?");

        while (true) {
            System.out.println("\n\t\t[1] Room ID\n\t\t[2] Room Floor\n\t\t[3] Room Type\n\t\t[4] Room Capacity\n\t\t[5] Room Description\n\t\t[6] Room Price\n\t\t[7] Room Status\n\t\t[8] Exit to Main Menu");
            System.out.print("\n\t\tEnter your choice (1-8): ");

            if (sc.hasNextInt()) {
                search = sc.nextInt();
                sc.nextLine();

                if (search < 1 || search > 8) {
                    errorMessageNumber();
                } else {
                    if (search == 8) {
                        mainPage();
                        return;
                    }

                    String stringChosen = choicesString.getOrDefault(search, "Invalid choice");
                    System.out.print("\t\tYou chose to search by '" + stringChosen + "'. Confirm? (yes/no): ");
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

        ArrayList<Room> searchResult = null;
        boolean searchAgain;

        switch (search) {
            case 1:
                while (true) {
                    System.out.print("\t\tEnter the exact Room ID: ");
                    String id = sc.nextLine().trim();
                     if (id.isEmpty()){
                         System.out.println("\t\tRoom ID cannot be empty. Please try again.");
                         continue;
                     }
                    searchResult = SearchRoomByAttribute(rooms, search, id);
                    if (searchResult != null && !searchResult.isEmpty()) {
                        displayRoomDetails(searchResult.get(0));
                        break;
                    } else {
                        System.out.println("\n\t\tRoom with ID '" + id + "' not found.");
                        System.out.print("\t\tSearch again by Room ID? (yes/no): ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                }
                break;

            case 2:
                while (true) {
                    System.out.print("\t\tEnter the Room Floor: ");
                    String floor = sc.nextLine().trim();
                     if (floor.isEmpty()){
                         System.out.println("\t\tRoom Floor cannot be empty. Please try again.");
                         continue;
                     }
                    searchResult = SearchRoomByAttribute(rooms, search, floor);
                    if (searchResult != null && !searchResult.isEmpty()) {
                        filterRoomAttributes(searchResult, sc);
                        break;
                    } else {
                        System.out.println("\n\t\tNo rooms found on floor '" + floor + "'.");
                        System.out.print("\t\tSearch again by Room Floor? (yes/no): ");
                         if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                }
                break;

            case 3:
                System.out.println("\t\tSelect the Room Type:");
                int typeRoomChoice = 0;
                while (true) {
                    System.out.println("\n\t\t[1] Standard Room\n\t\t[2] Deluxe Room\n\t\t[3] Family Room\n\t\t[4] Suite\n\t\t[5] Executive Room");
                    System.out.print("\t\tEnter the room type choice (1-5): ");
                    if (sc.hasNextInt()) {
                        typeRoomChoice = sc.nextInt();
                        sc.nextLine();
                        if (typeRoomChoice < 1 || typeRoomChoice > 5) {
                            errorMessageNumber();
                        } else {
                            break;
                        }
                    } else {
                        errorMessageNumber();
                        sc.next();
                    }
                }
                switch (typeRoomChoice) {
                    case 1: type = "Standard Room"; break;
                    case 2: type = "Deluxe Room"; break;
                    case 3: type = "Family Room"; break;
                    case 4: type = "Suite"; break;
                    case 5: type = "Executive Room"; break;
                }
                searchResult = SearchRoomByAttribute(rooms, search, type);
                if (searchResult != null && !searchResult.isEmpty()) {
                    filterRoomAttributes(searchResult, sc);
                } else {
                    System.out.println("\n\t\tNo rooms found matching type '" + type + "'.");
                }
                break;

            case 4:
                 while (true) {
                    System.out.print("\t\tEnter the desired Room Capacity: ");
                    String capacityStr = sc.nextLine().trim();
                    int targetCapacity;

                    try {
                        targetCapacity = Integer.parseInt(capacityStr);
                        if (targetCapacity <= 0) {
                             System.out.println("\t\tCapacity must be a positive number. Please try again.");
                             continue;
                        }

                        searchResult = SearchRoomByAttribute(rooms, search, String.valueOf(targetCapacity));

                        if (searchResult != null && !searchResult.isEmpty()) {
                            filterRoomAttributes(searchResult, sc);
                            break;
                        } else {
                            System.out.println("\n\t\tNo rooms found with capacity '" + targetCapacity + "'.");
                             System.out.print("\t\tSearch again by Room Capacity? (yes/no): ");
                            if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                         System.out.println("\t\tInvalid capacity format. Please enter a whole number.");
                    }
                }
                break;

            case 5:
                while (true) {
                    System.out.print("\t\tEnter a keyword for Room Description (e.g., simple, business, spacious): ");
                    String description = sc.nextLine().trim();
                     if (description.isEmpty()){
                         System.out.println("\t\tDescription keyword cannot be empty. Please try again.");
                         continue;
                     }
                    searchResult = SearchRoomByAttribute(rooms, search, description);
                    if (searchResult != null && !searchResult.isEmpty()) {
                        filterRoomAttributes(searchResult, sc);
                        break;
                    } else {
                        System.out.println("\n\t\tNo rooms found matching description keyword '" + description + "'.");
                        System.out.print("\t\tSearch again by Room Description? (yes/no): ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                }
                break;

            case 6:
                 while (true) {
                    System.out.print("\t\tEnter the Room Price (e.g., 150 or 150.00): RM ");
                    String priceString = sc.nextLine().trim();
                    double targetPrice;

                    try {
                        targetPrice = Double.parseDouble(priceString);
                         if (targetPrice < 0) {
                             System.out.println("\n\t\tPrice cannot be negative. Please enter a valid price.");
                             continue;
                         }

                        searchResult = SearchRoomByAttribute(rooms, search, String.valueOf(targetPrice));

                        if (searchResult != null && !searchResult.isEmpty()) {
                            filterRoomAttributes(searchResult, sc);
                            break;
                        } else {
                            System.out.println("\n\t\tNo rooms found matching price " + String.format("RM %.2f", targetPrice) + ".");
                            System.out.print("\t\tSearch again by Room Price? (yes/no): ");
                             if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                break;
                             }
                        }
                    } catch (NumberFormatException e) {
                         System.out.println("\n\t\tInvalid price format entered. Please enter a number (e.g., 150 or 150.00).");
                    }
                }
                break;

            case 7:
                while (true) {
                    System.out.print("\t\tEnter the Room Status (e.g., Available, Occupied, Maintenance): ");
                    String status = sc.nextLine().trim();
                     if (status.isEmpty()){
                         System.out.println("\t\tRoom Status cannot be empty. Please try again.");
                         continue;
                     }
                    searchResult = SearchRoomByAttribute(rooms, search, status);
                    if (searchResult != null && !searchResult.isEmpty()) {
                        filterRoomAttributes(searchResult, sc);
                        break;
                    } else {
                        System.out.println("\n\t\tNo rooms found with status '" + status + "'.");
                        System.out.print("\t\tSearch again by Room Status? (yes/no): ");
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
                    editRoom(rooms);
                    break;
                case 2:
                    deleteRoom(rooms);
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

    public static ArrayList<Room> SearchRoomByAttribute(ArrayList<Room> rooms, int option, String value) {
        ArrayList<Room> matchedRooms = new ArrayList<>();
        for (Room room : rooms) {
            boolean match = false;

            switch (option) {//If equals then true
                case 1:
                    match = room.getId().equalsIgnoreCase(value);
                    break;
                case 2:
                    match = room.getFloor().equalsIgnoreCase(value);
                    break;
                case 3:
                    match = room.getType().equalsIgnoreCase(value);
                    break;
                case 4:
                    match = String.valueOf(room.getCapacity()).equals(value);
                    break;
                case 5:
                    match = room.getDescription().toLowerCase().contains(value.toLowerCase());
                    break;
                case 6:
                    match = String.valueOf(room.getPrice()).equals(value);
                    break;
                case 7:
                    match = room.getStatus().equalsIgnoreCase(value);
                    break;
            }

            if (match) {
                matchedRooms.add(room);
            }
        }
        if (matchedRooms.isEmpty()) {
            System.out.println("\n\t\tNo matching room found.");
        }
        return matchedRooms;
    }

    public static void filterRoomAttributes(ArrayList<Room> rooms, Scanner sc) {
        if (rooms == null || rooms.isEmpty()) {
            System.out.println("\n\t\tNo rooms found based on previous criteria to display details for.");
            return;
        }

        Set<Integer> attributeChoices = new HashSet<>();

        while (true) {
             attributeChoices.clear();
            boolean validInputFound = true;

            System.out.println("\n\t\tDisplay details for the " + rooms.size() + " found room(s).");
            System.out.println("\t\tSelect which attributes to view:");
            System.out.println("\t\t[1] ID  [2] Floor  [3] Type  [4] Capacity  [5] Description  [6] Price  [7] Status");
            System.out.print("\t\tEnter attribute numbers separated by commas (e.g., 1,3,6): ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("\t\tInput cannot be empty. Please try again.");
                continue;
            }

            String[] options = input.split(",");
             if (options.length == 0 && input.length() > 0) {
                 System.out.println("\t\tNo valid options entered. Please try again.");
                 continue;
            }


            for (String option : options) {
                String trimmedOption = option.trim();
                 if (trimmedOption.isEmpty()) {
                    continue;
                }
                try {
                    int choice = Integer.parseInt(trimmedOption);
                    if (choice >= 1 && choice <= 7) {
                        attributeChoices.add(choice);
                    } else {
                        System.out.println("\t\tError: Option '" + choice + "' is out of range (1-7). Input invalid.");
                        validInputFound = false;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\t\tError: Input '" + trimmedOption + "' is not a valid number. Input invalid.");
                    validInputFound = false;
                    break;
                }
            }

             if (validInputFound && !attributeChoices.isEmpty()) {
                 break;
            } else if (validInputFound && attributeChoices.isEmpty()) {
                 System.out.println("\t\tNo valid attribute numbers selected. Please try again.");
            }
        }

        System.out.println("\n\t\t===================== Displaying Selected Room Details =====================");
        int roomCounter = 0;
        for (Room room : rooms) {
            roomCounter++;
             System.out.println("\n\t\t------------------------ Room " + roomCounter + " ------------------------"); 
            displayRoomDetails(room, attributeChoices); 
        }
        System.out.println("\n\t\t===============================================================");
    }

    public static void displayRoomDetails(Room room) {
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\tRoom ID: " + room.getId());
        System.out.println("\t\tRoom Floor: " + room.getFloor());
        System.out.println("\t\tType: " + room.getType());
        System.out.println("\t\tCapacity: " + room.getCapacity());
        System.out.println("\t\tPrice: " + room.getPrice());
        System.out.println("\t\tDescription: " + room.getDescription());
        System.out.println("\t\tStatus: " + room.getStatus());
        System.out.println("\t\t==================================================================");
    }

    public static void displayRoomDetails(Room room, Set<Integer> attributeChoices) {
        if (room == null) {
             System.out.println("\t\tError: Cannot display details for a null room.");
             return;
        }
        if (attributeChoices == null || attributeChoices.isEmpty()){
            System.out.println("\t\tError: No attributes selected to display.");
             return; 
        }

        if (attributeChoices.contains(1)) System.out.println("\t\t  Room ID: " + room.getId());
        if (attributeChoices.contains(2)) System.out.println("\t\t  Room Floor: " + room.getFloor());
        if (attributeChoices.contains(3)) System.out.println("\t\t  Room Type: " + room.getType());
        if (attributeChoices.contains(4)) System.out.println("\t\t  Room Capacity: " + room.getCapacity());
        if (attributeChoices.contains(5)) System.out.println("\t\t  Room Description: " + room.getDescription());
        if (attributeChoices.contains(6)) System.out.println("\t\t  Room Price: RM " + String.format("%.2f", room.getPrice()));
        if (attributeChoices.contains(7)) System.out.println("\t\t  Room Status: " + room.getStatus());
    }

    public static void addNewRoom(ArrayList<Room> rooms) {
        clear();
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tADD ROOM SYSTEM\t\t\t\t  |");
        System.out.println("\t\t==================================================================");
        Scanner sc = new Scanner(System.in);
        boolean detailsConfirm = false;

        String floor = "";
        String capacityInput = "";
        int capacity = 0;
        String type = "";
        String description = "";
        double price = 0.0;
        String status = "";
        String newRoomId = "";
        System.out.println("\n\t\tPlease fill in the details below: ");
        System.out.print("\t\t==================================================================");
        do {
            while (true) {
                System.out.print("\n\t\tEnter floor(Room number will be automated generated): ");
                floor = sc.nextLine();
                if (floor.isEmpty()) {
                    System.out.println("\n\t\tFloor cannot be empty. Please try again.");
                } else {
                    if (floor.length() == 1) {
                        char inputFloor = floor.charAt(0);
                        if (!Character.isDigit(inputFloor)) {
                            System.out.println("\n\t\tOnly digits are allowed");
                        } else {
                            break;
                        }
                    } else {
                        System.out.println("\n\t\tOnly input one digit. Please try again.\n");
                    }
                }
            }
            while (true) {
                System.out.print("\n\t\tEnter capacity: ");
                capacityInput = sc.nextLine().trim();
                if (capacityInput.isEmpty()) {
                    System.out.println("\n\t\tCapacity cannot be empty");
                } else {
                    if (!isDigitsOnly(capacityInput)) {
                        System.out.println("\n\t\tInvalid number, only integer is allowed.");
                    } else {
                        capacity = Integer.parseInt(capacityInput);
                        break;
                    }
                }
            }
            while (true) {
                System.out.print("\n\t\tEnter room type: ");
                int typeRoom = 0;
                while (true) {
                    System.out.println("\n\t\t[1] Standard\n\t\t[2] Deluxe\n\t\t[3] Family\n\t\t[4] Suite\n\t\t[5] Executive");
                    System.out.print("\n\t\tEnter the room type (1-5): ");
                    if (sc.hasNextInt()) {
                        typeRoom = sc.nextInt();
                        sc.nextLine();
                        if (typeRoom < 1 || typeRoom > 5) {
                            errorMessageNumber();
                        } else {
                            break;
                        }
                    } else {
                        errorMessageNumber();
                        sc.next();
                    }
                }
                switch (typeRoom) {
                    case 1:
                        type = "Standard Room";
                        break;
                    case 2:
                        type = "Deluxe Room";
                        break;
                    case 3:
                        type = "Family Room";
                        break;
                    case 4:
                        type = "Suite";
                        break;
                    case 5:
                        type = "Executive Room";
                        break;
                }
                break;
            }
            while (true) {
                System.out.println("\n\t\tChoose a description");
                for (int i = 0; i < descriptions.size(); i++) {
                    System.out.println("\t\t[" + (i + 1) + "] " + descriptions.get(i));
                }
                System.out.println("\t\t[" + (descriptions.size() + 1) + "] Write a custom description");
                System.out.print("\n\t\tEnter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice >= 1 && choice <= descriptions.size()) {
                    description = descriptions.get(choice - 1);
                    break;
                } else if (choice == descriptions.size() + 1) {
                    System.out.print("\n\t\tEnter your custom description: ");
                    description = sc.nextLine();
                    break;
                } else {
                    System.out.print("\n\t\tInvalid choice. Description not set.");
                }
            }

            while (true) {
                System.out.print("\n\t\tEnter room price: RM ");
                if (sc.hasNextDouble()) {
                    price = sc.nextDouble();
                    sc.nextLine();
                    break;
                } else {
                    System.out.println("\n\t\tPlease input a correct price.");
                }
            }

            while (true) {
                System.out.print("\n\t\tEnter the status of the room: ");
                status = askAvailableOrOccupied(sc);
                if (status.isEmpty()) {
                    System.out.println("\n\t\tStatus cannot be empty");
                } else {
                    break;
                }
            }
            int highestRoomNum = 0;
            for (Room room : rooms) {
                if (room.getFloor().equals(floor)) {
                    String latestRoomId = room.getId();
                    if (latestRoomId.startsWith(floor)) {
                        try {
                            String newStartId = latestRoomId.substring(floor.length());//get behind 2 number

                            if (!newStartId.isEmpty()) {
                                int id = Integer.parseInt(newStartId);
                                if (id > highestRoomNum) {
                                    highestRoomNum = id;
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\n\t\tInvalid");
                        }
                    }
                }
            }
            int nextRoomId = highestRoomNum + 1;
            String padString;
            if (nextRoomId < 10) {
                padString = "0" + nextRoomId;
            } else {
                padString = String.valueOf(nextRoomId);
            }
            newRoomId = floor + padString;

            clear();
            System.out.println("\n\t\t==================================================================");
            System.out.println("\t\t|\t\tPlease confirm the details below:\t\t |");
            System.out.println("\t\t==================================================================");
            System.out.println("\t\tRoom ID: " + newRoomId);
            System.out.println("\t\tFloor " + floor);
            System.out.println("\t\tCapacity: " + capacityInput);
            System.out.println("\t\tType: " + type);
            System.out.println("\t\tDescription: " + description);
            System.out.printf("\t\tPrice: RM %.2f%n", price);
            System.out.println("\n\t\tStatus: " + status);
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
                        System.out.println("\n\t\tAdd new room cancelled.");
                        return;
                    } else {
                        clear();
                        System.out.println("\n\t\t==================================================================");
                        System.out.println("\t\t|\t\t\tRE-ENTER ROOM DETAILS\t\t\t |");
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

        Room room = new Room(newRoomId, floor, capacity, type, description, price, status);
        rooms.add(room);//add this object to rooms array
        writeRoomFile(rooms, "room.txt");
        System.out.println("\n\t\tRoom is added successfully!");
        System.out.println("\n\t\tPress [ENTER] to continue..");
        sc.nextLine();
        mainPage();
    }

    public static void editRoom(ArrayList<Room> rooms) {
        clear();
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tEDIT ROOM SYSTEM\t\t\t |");
        System.out.println("\t\t==================================================================");
        Scanner sc = new Scanner(System.in);
        boolean found = false;
        boolean continueEditing = true;
        while (continueEditing) {
            String typeName = "";
            System.out.print("\n\t\tEnter the room ID to edit: ");
            String targetRoom = sc.nextLine();
            for (Room room : rooms) {
                if (room.getId().equalsIgnoreCase(targetRoom)) {
                    found = true;
                    System.out.print("\n\t\tRoom found: ");
                    displayRoomDetails(room);

                    System.out.println("\n\t\tWhat do you want to edit? (Can enter multiple choice using ','[eg: 1,2,3] )");
                    System.out.println("\t\t[1] Room Capacity\n\t\t[2] Room Type\n\t\t[3] Room Description\n\t\t[4] Room Price\n\t\t[5] Room Status\n\t\t[6] Exit");
                    System.out.print("\n\t\tEnter the choices you want to edit: ");
                    String editDetails = sc.nextLine().trim();

                    String[] options = editDetails.split(",");
                    for (String option : options) {
                        switch (option.trim()) {
                            case "1":
                                System.out.println("\n\t\t------------------ Edit Capacity ------------------");
                                int newCapacity = 0;
                                while (true) {
                                    System.out.print("\n\t\tEnter the new capacity: ");
                                    if (sc.hasNextInt()) {
                                        newCapacity = sc.nextInt();
                                        sc.nextLine();
                                        if (newCapacity > 0) {
                                            break;
                                        } else {
                                            System.out.println("\n\t\tCapacity must be positive.");
                                        }
                                    } else {
                                        System.out.print("\n\t\tInvalid input. Please enter an integer.");
                                        sc.next();
                                        sc.nextLine();
                                    }
                                }
                                if (confirmAction(sc, "\n\t\tConfirm change capacity to " + newCapacity + "?")) {
                                    room.setCapacity(newCapacity);
                                    updateRoomFile(room.getId(), 1, String.valueOf(newCapacity)); // Update file
                                    System.out.println("\n\t\tCapacity updated successfully!");
                                } else {
                                    System.out.println("\n\t\tCapacity change cancelled.");
                                }
                                break;
                            case "2":
                                System.out.println("\n\t\t----------------- Edit Room Type -----------------");
                                int newType = 0;
                                while (true) {
                                    System.out.println("\n\t\t[1] Standard\n\t\t[2] Deluxe\n\t\t[3] Family\n\t\t[4] Suite\n\t\t[5] Executive");
                                    System.out.print("\n\t\tEnter the type to change(1-5): ");
                                    if (sc.hasNextInt()) {
                                        newType = sc.nextInt();
                                        sc.nextLine();
                                        if (newType < 1 || newType > 5) {
                                            errorMessageNumber();
                                        } else {
                                            break;
                                        }
                                    } else {
                                        errorMessageNumber();
                                        sc.next();
                                        sc.nextLine();
                                    }
                                }
                                switch (newType) {
                                    case 1:
                                        room.setType("Standard Room");
                                        typeName = "Standard Room";
                                        break;
                                    case 2:
                                        room.setType("Deluxe Room");
                                        typeName = "Deluxe Room";
                                        break;
                                    case 3:
                                        room.setType("Family Room");
                                        typeName = "Family Room";
                                        break;
                                    case 4:
                                        room.setType("Suite");
                                        typeName = "Suite";
                                        break;
                                    case 5:
                                        room.setType("Executive Room");
                                        typeName = "Executive Room";
                                        break;
                                }
                                if (confirmAction(sc, "\n\t\tConfirm change type to '" + typeName + "'?")) {
                                    room.setType(typeName); // Update object
                                    updateRoomFile(room.getId(), 2, typeName); // Update file
                                    System.out.println("\n\t\tType updated successfully!");
                                } else {
                                    System.out.println("\n\t\tType change cancelled.");
                                }
                                break;
                            case "3":
                                System.out.println("\n\t\t------------------ Edit Price ------------------");
                                double newPrice = 0.0;
                                while (true) {
                                    System.out.print("\n\t\tEnter new room price: RM ");
                                    if (sc.hasNextDouble()) {
                                        newPrice = sc.nextDouble();
                                        sc.nextLine();
                                        if (newPrice >= 0) {
                                            break;
                                        } else {
                                            System.out.println("\n\t\tPrice cannot be negative.");
                                        }
                                    } else {
                                        System.out.println("\n\t\tInvalid input. Please enter a valid price.");
                                        sc.next();
                                        sc.nextLine();
                                    }
                                }
                                String priceCheck = String.format("RM %.2f", newPrice);
                                if (confirmAction(sc, "\n\t\tConfirm change price to " + priceCheck + "?")) {
                                    room.setPrice(newPrice);
                                    updateRoomFile(room.getId(), 3, String.valueOf(newPrice));
                                    System.out.println("\n\t\tPrice updated successfully!");
                                } else {
                                    System.out.println("\n\t\tPrice change cancelled.");
                                }
                                break;

                            case "4":
                                System.out.println("\n\t\t----------------- Edit Description -----------------");
                                String newDescription;
                                while (true) {
                                    System.out.println("\n\t\tChoose a description");
                                    for (int i = 0; i < descriptions.size(); i++) {
                                        System.out.println("\t\t[" + (i + 1) + "] " + descriptions.get(i));
                                    }
                                    System.out.println("\t\t[" + (descriptions.size() + 1) + "] Write a custom description");
                                    System.out.print("\n\t\tEnter your choice: ");
                                    if (sc.hasNextInt()) {
                                        int choice = sc.nextInt();
                                        sc.nextLine();
                                        if (choice >= 1 && choice <= descriptions.size()) {
                                            newDescription = descriptions.get(choice - 1);
                                            break;
                                        } else if (choice == descriptions.size() + 1) {
                                            System.out.print("\n\t\tEnter your custom description: ");
                                            newDescription = sc.nextLine();
                                            if (newDescription.isEmpty()) {
                                                System.out.println("\n\t\tCustom description cannot be empty.");
                                                continue;
                                            }
                                            break;
                                        } else {
                                            System.out.print("\n\t\tInvalid choice. Description not set.");
                                        }
                                    } else {
                                        System.out.println("\n\t\tInvalid input. Please enter a number.");
                                        sc.next();
                                        sc.nextLine();
                                    }
                                }
                                if (confirmAction(sc, "\n\t\tConfirm change description to '" + newDescription + "'?")) {
                                    room.setDescription(newDescription);
                                    updateRoomFile(room.getId(), 4, newDescription);
                                    System.out.println("\n\t\tDescription updated successfully!");
                                } else {
                                    System.out.println("\n\t\tDescription change cancelled.");
                                }
                                break;

                            case "5":
                                System.out.println("\n\t\t------------------ Edit Status ------------------");
                                String newStatus = askAvailableOrOccupied(sc);
                                if (confirmAction(sc, "\n\t\tConfirm change status to '" + newStatus + "'?")) {
                                    room.setStatus(newStatus);
                                    updateRoomFile(room.getId(), 5, newStatus);
                                    System.out.println("\n\t\tStatus updated successfully!");
                                } else {
                                    System.out.println("\n\t\tStatus change cancelled.");
                                }
                                break;

                            case "6":
                                System.out.println("\n\t\tFinished editing Room " + room.getId() + ".");
                                continueEditing = false;
                                break;

                            default:
                                System.out.println("\n\t\tInvalid choice. Please enter a number between 1 and 6.");
                                break;
                        }

                        if (continueEditing) {
                            System.out.print("\n\t\tPress [ENTER] to continue editing this room...");
                            sc.nextLine();
                            clear();
                            System.out.println("\n\t\t==================================================================");
                            System.out.println("\t\t|\t\t\tEDIT ROOM SYSTEM\t\t\t |");
                            System.out.println("\t\t==================================================================");
                            System.out.println("\n\t\tCurrent Details for Room " + room.getId() + ":");
                            displayRoomDetails(room);
                        }
                    }
                    System.out.println("\n\t\tPress [ENTER] to return to the main menu...");
                    sc.nextLine();
                    mainPage();
                }
            }

            if (!found) {
                System.out.println("\n\t\tRoom not found. Please try again.");
                continue;
            }
            break;
        }
        System.out.println("\n\t\tPress [ENTER] to continue..");
        sc.nextLine();
        mainPage();
    }

    private static boolean confirmAction(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (yes/no): ");
            String confirmation = scanner.nextLine().trim();
            if (confirmation.equalsIgnoreCase("yes")) {
                return true;
            }
            if (confirmation.equalsIgnoreCase("no")) {
                return false;
            }
            errorMessageWord();
        }
    }

    public static void deleteRoom(ArrayList<Room> currentRooms) {
        clear();
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\t\t==================================================================");
        System.out.println("\t\t|\t\t\tDELETE ROOM SYSTEM\t\t\t |");
        System.out.println("\t\t==================================================================");
        String filename = "room.txt";

        if (currentRooms.isEmpty()) {
            System.out.println("\n\t\tThe room list is currently empty. No rooms to delete.");
            System.out.println("\n\t\tPress [ENTER] to continue...");
            sc.nextLine();
            return;
        }

        System.out.print("\n\t\tPlease enter the room ID that you want to delete: ");
        String idToDelete = sc.nextLine().trim();

        Room roomToDelete = null;
        int roomIndex = -1;

        for (int i = 0; i < currentRooms.size(); i++) {
            Room currentRoom = currentRooms.get(i);
            if (currentRoom != null && idToDelete.equalsIgnoreCase(currentRoom.getId())) {
                roomToDelete = currentRoom;
                roomIndex = i;
                break;
            }
        }

        if (roomToDelete != null) {
            System.out.println("\n\t\tRoom Found:");
            displayRoomDetails(roomToDelete);
            while (true) {
                System.out.print("\n\t\tConfirm to delete this room? (yes/no): ");
                String deleteConfirm = sc.nextLine().trim();
                if (deleteConfirm.equalsIgnoreCase("yes")) {
                    currentRooms.remove(roomIndex);
                    System.out.println("\n\t\tRoom with ID '" + idToDelete + "' has been deleted.");
                    writeRoomFile(currentRooms, filename);
                    break;
                } else if (deleteConfirm.equalsIgnoreCase("no")) {
                    System.out.println("\n\t\tDeletion cancelled for Room ID '" + idToDelete + "'.");
                    break;
                } else {
                    errorMessageWord();
                }
            }
        } else {
            System.out.println("\n\t\tRoom with ID '" + idToDelete + "' not found in the list. No changes made.");
        }
        System.out.println("\n\t\tPress [ENTER] to continue...");
        sc.nextLine();
    }

    public static String askAvailableOrOccupied(Scanner sc) {
        while (true) {
            System.out.print("\n\t\tEnter new room status (Available/Occupied): ");
            String newStatus = sc.nextLine().trim();
            String confirm = "";

            if (newStatus.equalsIgnoreCase("Available")) {
                return "Available";
            } else if (newStatus.equalsIgnoreCase("Occupied")) {
                return "Occupied";
            }

            if (newStatus.toLowerCase().contains("ava")) {
                System.out.print("\n\t\tDid you mean 'Available'? (yes/no): ");
                confirm = sc.nextLine().trim();
                if (confirm.equalsIgnoreCase("yes")) {
                    return "Available";
                } else if (confirm.equalsIgnoreCase("no")) {
                    continue;
                } else {
                    System.out.println("\n\t\tPlease input the correct word");
                }
            } else if (newStatus.toLowerCase().contains("occ")) {
                System.out.print("\n\t\tDid you mean 'Occupied' ? (yes/no): ");
                if (confirm.equalsIgnoreCase("yes")) {
                    return "Occupied";
                } else if (confirm.equalsIgnoreCase("no")) {
                    continue;
                } else {
                    errorMessageWord();
                }
            } else {
                System.out.println("\n\t\tUnrecognized input. Please enter 'Available' or 'Occupied'");
            }
        }
    }

    public static boolean isDigitsOnly(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Room> readRoomFile() {
        File roomFile = new File("room.txt");
        if (!roomFile.exists()) {
            try {
                if (roomFile.createNewFile()) {
                    System.out.println("File created successfully. Ready to store room data.");
                } else {
                    System.out.println("Failed to create file.");
                    return new ArrayList<>(); // Return an empty list
                }
            } catch (IOException ioEx) {
                System.err.println("Error: Couldn't create file 'room.txt': " + ioEx.getMessage());
                return new ArrayList<>();
            }
        }

        ArrayList<Room> rooms = new ArrayList<>(); // Initialize rooms here
        try (Scanner readRoom = new Scanner(roomFile)) {
            while (readRoom.hasNextLine()) {
                String line = readRoom.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String id = parts[0].trim();
                    String floor = id.substring(0, 1);
                    try {
                        int capacity = Integer.parseInt(parts[1].trim());
                        String type = parts[2].trim();
                        double price = Double.parseDouble(parts[3].trim());
                        String description = parts[4];
                        String status = parts[5].trim();
                        Room r = new Room(id, floor, capacity, type, description, price, status);
                        rooms.add(r);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing data in file: " + e.getMessage() + " for line: " + line);
                        // Consider logging the error or skipping the invalid line
                    }
                } else {
                    System.err.println("Skipping invalid line: " + line); // Handle invalid lines
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File 'room.txt' not found: " + e.getMessage());
            return new ArrayList<>();
        }
        return rooms;
    }

    public static void writeRoomFile(ArrayList<Room> roomList, String filename) {
        File roomFile = new File(filename);

        if (roomList == null) {
            System.err.println("Error: Cannot write a null room list to file.");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(roomFile, false)) {
            for (Room room : roomList) {
                if (room == null) {
                    continue; //Skip 
                }

                String id = room.getId() != null ? room.getId() : "";
                String capacityStr = String.valueOf(room.getCapacity());
                String type = room.getType() != null ? room.getType() : "";
                String priceStr = String.format("%.2f", room.getPrice());
                String description = room.getDescription() != null ? room.getDescription() : "";
                String status = room.getStatus() != null ? room.getStatus() : "";

                String line = String.join("|",
                        id,
                        capacityStr,
                        type,
                        priceStr,
                        description,
                        status
                );

                fileWriter.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file '" + filename + "': " + e.getMessage());
        }
    }

    public static void updateRoomFile(String roomId, int update, String newValue) {
        File roomFile = new File("room.txt");
        if (!roomFile.exists()) {
            try {
                if (roomFile.createNewFile()) {
                    System.out.println("File not found, creating a new empty file.");
                    return;
                } else {
                    System.out.println("Failed to create a new file.");
                    return;
                }

            } catch (IOException e) {
                System.out.println("Error creating file");
                return;

            }
        }

        List<String> updatedLines = new ArrayList<>();
        try (Scanner updateRoom = new Scanner(roomFile)) {
            while (updateRoom.hasNextLine()) {
                String line = updateRoom.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length > 0 && roomId.equals(parts[0].trim())) {
                    switch (update) {
                        case 1: // capacity
                            parts[1] = newValue;
                            break;
                        case 2: // type
                            parts[2] = newValue;
                            break;
                        case 3: // price
                            parts[3] = newValue;
                            break;
                        case 4: // desc
                            parts[4] = newValue;
                            break;
                        case 5: // Status
                            parts[5] = newValue;
                            break;
                        default:
                            // Handle unexpected 'update' values
                            System.err.println("Warning: Invalid update code: " + update + " for room ID: " + roomId);
                            break;
                    }
                }
                updatedLines.add(String.join("|", parts));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        try (FileWriter writer = new FileWriter("room.txt", false)) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void errorMessageNumber() {
        System.out.print("\n\t\t===================================\n");
        System.out.println("\n\t\tError. Invalid input. Please input the correct number.");
    }

    public static void errorMessageWord() {
        System.out.print("\n\t\t===================================\n");
        System.out.println("\n\t\tError. Invalid input. Please input the correct word.");
    }

    public static void clear() {
        System.out.println("\033[H\033[2J");
    }
}
