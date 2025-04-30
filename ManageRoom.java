
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
        int action = -1;

        while (action != 0) {
            while (true) {
                clear();
                System.out.println("\n============================================================");
                System.out.println("||           HOTEL ROOM MANAGEMENT SYSTEM                ||");
                System.out.println("============================================================");
                System.out.println("|| [1] Add New Room                                      ||");
                System.out.println("|| [2] Edit Room                                         ||");
                System.out.println("|| [3] Delete Room                                       ||");
                System.out.println("|| [4] Search Room                                       ||");
                System.out.println("|| [0] Exit                                              ||");
                System.out.println("============================================================");
                System.out.print("Please choose an action: ");

                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    sc.nextLine();
                    switch (action) {
                        case 1:
                            addNewRoom(rooms);
                            break;
                        case 2:
                            editRoom(rooms);
                            break;
                        case 3:
                            deleteRoom(rooms);
                            break;
                        case 4:
                            showRoom(rooms);
                            break;
                        case 0:
                            return;
                    }
                    if (action < 1 || action > 5) {
                        errorMessageNumber();
                    } 
                } else {
                    errorMessageNumber();
                    sc.next();
                }
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

        System.out.println("\n=================================================================");
        System.out.println("||                    SEARCH ROOM SYSTEM                       ||");
        System.out.println("=================================================================");
        System.out.println("\nWhat attribute do you want to search by?");

        while (true) {
            System.out.println("\n[1] Room ID\n[2] Room Floor\n[3] Room Type\n[4] Room Capacity\n[5] Room Description\n[6] Room Price\n[7] Room Status\n[8] Exit to Main Menu");
            System.out.print("\nEnter your choice (1-8): ");

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

        ArrayList<Room> searchResult = null;

        switch (search) {
            case 1:
                while (true) {
                    System.out.print("Enter the exact Room ID: ");
                    String id = sc.nextLine().trim();
                    if (id.isEmpty()) {
                        System.out.println("Room ID cannot be empty. Please try again.");
                        continue;
                    }
                    searchResult = SearchRoomByAttribute(rooms, search, id);
                    if (searchResult != null && !searchResult.isEmpty()) {
                        displayRoomDetails(searchResult.get(0));
                        break;
                    } else {
                        System.out.println("\nRoom with ID '" + id + "' not found.");
                        System.out.print("Search again by Room ID? (yes/no): ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                }
                break;

            case 2:
                while (true) {
                    System.out.print("Enter the Room Floor: ");
                    String floor = sc.nextLine().trim();
                    if (floor.isEmpty()) {
                        System.out.println("Room Floor cannot be empty. Please try again.");
                        continue;
                    }
                    searchResult = SearchRoomByAttribute(rooms, search, floor);
                    if (searchResult != null && !searchResult.isEmpty()) {
                        filterRoomAttributes(searchResult, sc);
                        break;
                    } else {
                        System.out.println("\nNo rooms found on floor '" + floor + "'.");
                        System.out.print("Search again by Room Floor? (yes/no): ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                }
                break;

            case 3:
                System.out.println("Select the Room Type:");
                int typeRoomChoice = 0;
                while (true) {
                    System.out.println("\n[1] Standard Room\n[2] Deluxe Room\n[3] Family Room\n[4] Suite\n[5] Executive Room");
                    System.out.print("Enter the room type choice (1-5): ");
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
                searchResult = SearchRoomByAttribute(rooms, search, type);
                if (searchResult != null && !searchResult.isEmpty()) {
                    filterRoomAttributes(searchResult, sc);
                } else {
                    System.out.println("\nNo rooms found matching type '" + type + "'.");
                }
                break;

            case 4:
                while (true) {
                    System.out.print("Enter the desired Room Capacity: ");
                    String capacityStr = sc.nextLine().trim();
                    int targetCapacity;

                    try {
                        targetCapacity = Integer.parseInt(capacityStr);
                        if (targetCapacity <= 0) {
                            System.out.println("Capacity must be a positive number. Please try again.");
                            continue;
                        }

                        searchResult = SearchRoomByAttribute(rooms, search, String.valueOf(targetCapacity));

                        if (searchResult != null && !searchResult.isEmpty()) {
                            filterRoomAttributes(searchResult, sc);
                            break;
                        } else {
                            System.out.println("\nNo rooms found with capacity '" + targetCapacity + "'.");
                            System.out.print("Search again by Room Capacity? (yes/no): ");
                            if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid capacity format. Please enter a whole number.");
                    }
                }
                break;

            case 5:
                while (true) {
                    System.out.print("Enter a keyword for Room Description (e.g., simple, business, spacious): ");
                    String description = sc.nextLine().trim();
                    if (description.isEmpty()) {
                        System.out.println("Description keyword cannot be empty. Please try again.");
                        continue;
                    }
                    searchResult = SearchRoomByAttribute(rooms, search, description);
                    if (searchResult != null && !searchResult.isEmpty()) {
                        filterRoomAttributes(searchResult, sc);
                        break;
                    } else {
                        System.out.println("\nNo rooms found matching description keyword '" + description + "'.");
                        System.out.print("Search again by Room Description? (yes/no): ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                }
                break;

            case 6:
                while (true) {
                    System.out.print("Enter the Room Price (e.g., 150 or 150.00): RM ");
                    String priceString = sc.nextLine().trim();
                    double targetPrice;

                    try {
                        targetPrice = Double.parseDouble(priceString);
                        if (targetPrice < 0) {
                            System.out.println("\nPrice cannot be negative. Please enter a valid price.");
                            continue;
                        }

                        searchResult = SearchRoomByAttribute(rooms, search, String.valueOf(targetPrice));

                        if (searchResult != null && !searchResult.isEmpty()) {
                            filterRoomAttributes(searchResult, sc);
                            break;
                        } else {
                            System.out.println("\nNo rooms found matching price " + String.format("RM %.2f", targetPrice) + ".");
                            System.out.print("Search again by Room Price? (yes/no): ");
                            if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid price format entered. Please enter a number (e.g., 150 or 150.00).");
                    }
                }
                break;

            case 7:
                while (true) {
                    System.out.print("Enter the Room Status (e.g., Available, Occupied, Maintenance): ");
                    String status = sc.nextLine().trim();
                    if (status.isEmpty()) {
                        System.out.println("Room Status cannot be empty. Please try again.");
                        continue;
                    }
                    searchResult = SearchRoomByAttribute(rooms, search, status);
                    if (searchResult != null && !searchResult.isEmpty()) {
                        filterRoomAttributes(searchResult, sc);
                        break;
                    } else {
                        System.out.println("\nNo rooms found with status '" + status + "'.");
                        System.out.print("Search again by Room Status? (yes/no): ");
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
                    editRoom(rooms);
                    break;
                case 2:
                    deleteRoom(rooms);
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
            System.out.println("\nNo matching room found.");
        }
        return matchedRooms;
    }

    public static void filterRoomAttributes(ArrayList<Room> rooms, Scanner sc) {
        if (rooms == null || rooms.isEmpty()) {
            System.out.println("\nNo rooms found based on previous criteria to display details for.");
            return;
        }

        Set<Integer> attributeChoices = new HashSet<>();

        while (true) {
            attributeChoices.clear();
            boolean validInputFound = true;

            System.out.println("\nDisplay details for the " + rooms.size() + " found room(s).");
            System.out.println("Select which attributes to view:");
            System.out.println("[1] ID  [2] Floor  [3] Type  [4] Capacity  [5] Description  [6] Price  [7] Status");
            System.out.print("Enter attribute numbers separated by commas (e.g., 1,3,6): ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }

            String[] options = input.split(",");
            if (options.length == 0 && input.length() > 0) {
                System.out.println("No valid options entered. Please try again.");
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
                        System.out.println("Error: Option '" + choice + "' is out of range (1-7). Input invalid.");
                        validInputFound = false;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Input '" + trimmedOption + "' is not a valid number. Input invalid.");
                    validInputFound = false;
                    break;
                }
            }

            if (validInputFound && !attributeChoices.isEmpty()) {
                break;
            } else if (validInputFound && attributeChoices.isEmpty()) {
                System.out.println("No valid attribute numbers selected. Please try again.");
            }
        }

        System.out.println("\n==================================================================");
        System.out.println("||               DISPLAYING SELECTED ROOM DETAILS              ||");
        System.out.println("==================================================================");
        int roomCounter = 0;
        for (Room room : rooms) {
            roomCounter++;
            System.out.println("\n==================================================================");
            System.out.println("||                        ROOM " + roomCounter + " DETAILS                        ||");
            System.out.println("==================================================================");
            displayRoomDetails(room, attributeChoices);
        }
        System.out.println("\n===============================================================");
    }

    public static void displayRoomDetails(Room room) {
        System.out.println("\n==================================================================");
        System.out.println("Room ID: " + room.getId());
        System.out.println("Room Floor: " + room.getFloor());
        System.out.println("Type: " + room.getType());
        System.out.println("Capacity: " + room.getCapacity());
        System.out.println("Price: " + room.getPrice());
        System.out.println("Description: " + room.getDescription());
        System.out.println("Status: " + room.getStatus());
        System.out.println("==================================================================");
    }

    public static void displayRoomDetails(Room room, Set<Integer> attributeChoices) {
        if (room == null) {
            System.out.println("Error: Cannot display details for a null room.");
            return;
        }
        if (attributeChoices == null || attributeChoices.isEmpty()) {
            System.out.println("Error: No attributes selected to display.");
            return;
        }

        if (attributeChoices.contains(1)) {
            System.out.println("  Room ID: " + room.getId());
        }
        if (attributeChoices.contains(2)) {
            System.out.println("  Room Floor: " + room.getFloor());
        }
        if (attributeChoices.contains(3)) {
            System.out.println("  Room Type: " + room.getType());
        }
        if (attributeChoices.contains(4)) {
            System.out.println("  Room Capacity: " + room.getCapacity());
        }
        if (attributeChoices.contains(5)) {
            System.out.println("  Room Description: " + room.getDescription());
        }
        if (attributeChoices.contains(6)) {
            System.out.println("  Room Price: RM " + String.format("%.2f", room.getPrice()));
        }
        if (attributeChoices.contains(7)) {
            System.out.println("  Room Status: " + room.getStatus());
        }
    }

    public static void addNewRoom(ArrayList<Room> rooms) {
        clear();
        System.out.println("\n==================================================================");
        System.out.println("||                      ADD ROOM SYSTEM                        ||");
        System.out.println("==================================================================");
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
        System.out.println("\nPlease fill in the details below: ");
        System.out.print("==================================================================");
        do {
            while (true) {
                System.out.print("\nEnter floor(Room number will be automated generated): ");
                floor = sc.nextLine();
                if (floor.isEmpty()) {
                    System.out.println("\nFloor cannot be empty. Please try again.");
                } 
                else {
                    if (floor.length() == 1) {
                        char inputFloor = floor.charAt(0);
                        if (!Character.isDigit(inputFloor)) {
                            System.out.println("\nOnly digits are allowed");
                        } else if (Integer.parseInt(floor) == 0){
                            System.out.println("\nFloor cannot be 0. Please try again.");
    
                        } else 
                        {
                        break;
                        }
                    } else {
                        System.out.println("\nOnly input one digit. Please try again.\n");
                    }
                }
            }
            while (true) {
                System.out.print("\nEnter capacity: ");
                capacityInput = sc.nextLine().trim();
                if (capacityInput.isEmpty()) {
                    System.out.println("\nCapacity cannot be empty");
                } else {
                    if (!isDigitsOnly(capacityInput)) {
                        System.out.println("\nInvalid number, only integer is allowed.");
                    } else if (Integer.parseInt(capacityInput) == 0){
                        System.out.println("\nCapacity cannot be 0. Please try again.");
                    }
                    else {
                        capacity = Integer.parseInt(capacityInput);
                        break;
                    }
                }
            }
            while (true) {
                System.out.print("\nEnter room type: ");
                int typeRoom = 0;
                while (true) {
                    System.out.println("\n============================================================");
                    System.out.println("||                     ROOM TYPE SELECTION                ||");
                    System.out.println("============================================================");
                    System.out.println("||[1] Standard Room                                       ||");
                    System.out.println("||[2] Deluxe Room                                         ||");
                    System.out.println("||[3] Family Room                                         ||");                                   
                    System.out.println("||[4] Suite                                               ||");
                    System.out.println("||[5] Executive Room                                      ||");
                    System.out.println("============================================================");
                    System.out.print("Please choose an option: ");
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
                System.out.println("\nChoose a description");
                for (int i = 0; i < descriptions.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + descriptions.get(i));
                }
                System.out.println("[" + (descriptions.size() + 1) + "] Write a custom description");
                System.out.print("\nEnter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice >= 1 && choice <= descriptions.size()) {
                    description = descriptions.get(choice - 1);
                    break;
                } else if (choice == descriptions.size() + 1) {
                    System.out.print("\nEnter your custom description: ");
                    description = sc.nextLine();
                    break;
                } else {
                    System.out.print("\nInvalid choice. Description not set.");
                }
            }

            while (true) {
                System.out.print("\nEnter room price: RM ");
                if (sc.hasNextDouble()) {
                    price = sc.nextDouble();
                    sc.nextLine();
                    break;
                } else {
                    System.out.println("\nPlease input a correct price.");
                }
            }

            while (true) {
                System.out.print("\nEnter the status of the room: ");
                status = askAvailableOrOccupied(sc);
                if (status.isEmpty()) {
                    System.out.println("\nStatus cannot be empty");
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
                            System.out.println("\nInvalid");
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
            System.out.println("\n==================================================================");
            System.out.println("||                  PLEASE CONFIRM THE DETAILS BELOW            ||");
            System.out.println("==================================================================");
            System.out.println("|| Room ID       : " + newRoomId + "                              ||");
            System.out.println("|| Floor         : " + floor + "                                  ||");
            System.out.println("|| Capacity      : " + capacityInput + "                          ||");
            System.out.println("|| Type          : " + type + "                                   ||");
            System.out.println("|| Description   : " + description + "                            ||");
            System.out.printf("|| Price         : RM %.2f                              ||%n", price);
            System.out.println("|| Status        : " + status + "                                 ||");
            System.out.println("==================================================================");

            while (true) {
                System.out.print("\nDo you confirm the details above are correct? (yes/no): ");
                String confirmation = sc.nextLine().trim();

                if (confirmation.equalsIgnoreCase("yes")) {
                    detailsConfirm = true;
                    break;
                } else if (confirmation.equalsIgnoreCase("no")) {
                    System.out.print("\nDetails not confirmed. Please re-enter the room information by pressing [ENTER] or press [Q] to exit: ");
                    String continueAdd = sc.nextLine().trim();
                    if (continueAdd.equalsIgnoreCase("q")) {
                        System.out.println("\nAdd new room cancelled.");
                        return;
                    } else {
                        clear();
                        System.out.println("\n==================================================================");
                        System.out.println("||                  RE-ENTER ROOM DETAILS                      ||");
                        System.out.println("==================================================================");
                        System.out.println("\nPlease fill in the details below:");
                        System.out.println("==================================================================");
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
        System.out.println("\nRoom is added successfully!");
        System.out.println("\nPress [ENTER] to continue..");
        sc.nextLine();
        mainPage();
    }

    public static void editRoom(ArrayList<Room> rooms) {
        clear();
        System.out.println("\n==================================================================");
        System.out.println("||                      EDIT ROOM SYSTEM                        ||");
        System.out.println("==================================================================");
        Scanner sc = new Scanner(System.in);
        boolean found = false;
        boolean continueEditing = true;
        while (continueEditing) {
            String typeName = "";
            System.out.print("\nEnter the room ID to edit: ");
            String targetRoom = sc.nextLine();
            for (Room room : rooms) {
                if (room.getId().equalsIgnoreCase(targetRoom)) {
                    found = true;
                    System.out.print("\nRoom found: ");
                    displayRoomDetails(room);

                    System.out.println("\n=================================================================");
                    System.out.println("||                  WHAT DO YOU WANT TO EDIT?                  ||");
                    System.out.println("=================================================================");
                    System.out.println("|| [1] Room Capacity                                           ||");
                    System.out.println("|| [2] Room Type                                               ||");
                    System.out.println("|| [3] Room Description                                        ||");
                    System.out.println("|| [4] Room Price                                              ||");
                    System.out.println("|| [5] Room Status                                             ||");
                    System.out.println("|| [0] Exit                                                    ||");
                    System.out.println("=================================================================");
                    System.out.print("Please choose an option: ");
                    String editDetails = sc.nextLine().trim();

                    String[] options = editDetails.split(",");
                    for (String option : options) {
                        switch (option.trim()) {
                            case "1":
                                System.out.println("\n------------------ Edit Capacity ------------------");
                                int newCapacity = 0;
                                while (true) {
                                    System.out.print("\nEnter the new capacity: ");
                                    if (sc.hasNextInt()) {
                                        newCapacity = sc.nextInt();
                                        sc.nextLine();
                                        if (newCapacity > 0) {
                                            break;
                                        } else {
                                            System.out.println("\nCapacity must be positive.");
                                        }
                                    } else {
                                        System.out.print("\nInvalid input. Please enter an integer.");
                                        sc.next();
                                        sc.nextLine();
                                    }
                                }
                                if (confirmAction(sc, "\nConfirm change capacity to " + newCapacity + "?")) {
                                    room.setCapacity(newCapacity);
                                    updateRoomFile(room.getId(), 1, String.valueOf(newCapacity)); // Update file
                                    System.out.println("\nCapacity updated successfully!");
                                } else {
                                    System.out.println("\nCapacity change cancelled.");
                                }
                                break;
                            case "2":
                                System.out.println("\n----------------- Edit Room Type -----------------");
                                int newType = 0;
                                while (true) {
                                    System.out.println("\n[1] Standard\n[2] Deluxe\n[3] Family\n[4] Suite\n[5] Executive");
                                    System.out.print("\nPlease choose an option: ");
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
                                if (confirmAction(sc, "\nConfirm change type to '" + typeName + "'?")) {
                                    room.setType(typeName); // Update object
                                    updateRoomFile(room.getId(), 2, typeName); // Update file
                                    System.out.println("\nType updated successfully!");
                                } else {
                                    System.out.println("\nType change cancelled.");
                                }
                                break;
                            case "3":
                                System.out.println("\n------------------ Edit Price ------------------");
                                double newPrice = 0.0;
                                while (true) {
                                    System.out.print("\nEnter new room price: RM ");
                                    if (sc.hasNextDouble()) {
                                        newPrice = sc.nextDouble();
                                        sc.nextLine();
                                        if (newPrice >= 0) {
                                            break;
                                        } else {
                                            System.out.println("\nPrice cannot be negative.");
                                        }
                                    } else {
                                        System.out.println("\nInvalid input. Please enter a valid price.");
                                        sc.next();
                                        sc.nextLine();
                                    }
                                }
                                String priceCheck = String.format("RM %.2f", newPrice);
                                if (confirmAction(sc, "\nConfirm change price to " + priceCheck + "?")) {
                                    room.setPrice(newPrice);
                                    updateRoomFile(room.getId(), 3, String.valueOf(newPrice));
                                    System.out.println("\nPrice updated successfully!");
                                } else {
                                    System.out.println("\nPrice change cancelled.");
                                }
                                break;

                            case "4":
                                System.out.println("\n----------------- Edit Description -----------------");
                                String newDescription;
                                while (true) {
                                    System.out.println("\n\tChoose a description");
                                    for (int i = 0; i < descriptions.size(); i++) {
                                        System.out.println("[" + (i + 1) + "] " + descriptions.get(i));
                                    }
                                    System.out.println("[" + (descriptions.size() + 1) + "] Write a custom description");
                                    System.out.print("\nEnter your choice: ");
                                    if (sc.hasNextInt()) {
                                        int choice = sc.nextInt();
                                        sc.nextLine();
                                        if (choice >= 1 && choice <= descriptions.size()) {
                                            newDescription = descriptions.get(choice - 1);
                                            break;
                                        } else if (choice == descriptions.size() + 1) {
                                            System.out.print("\nEnter your custom description: ");
                                            newDescription = sc.nextLine();
                                            if (newDescription.isEmpty()) {
                                                System.out.println("\nCustom description cannot be empty.");
                                                continue;
                                            }
                                            break;
                                        } else {
                                            System.out.print("\nInvalid choice. Description not set.");
                                        }
                                    } else {
                                        System.out.println("\nInvalid input. Please enter a number.");
                                        sc.next();
                                        sc.nextLine();
                                    }
                                }
                                if (confirmAction(sc, "\nConfirm change description to '" + newDescription + "'?")) {
                                    room.setDescription(newDescription);
                                    updateRoomFile(room.getId(), 4, newDescription);
                                    System.out.println("\nDescription updated successfully!");
                                } else {
                                    System.out.println("\nDescription change cancelled.");
                                }
                                break;

                            case "5":
                                System.out.println("\n------------------ Edit Status ------------------");
                                String newStatus = askAvailableOrOccupied(sc);
                                if (confirmAction(sc, "\nConfirm change status to '" + newStatus + "'?")) {
                                    room.setStatus(newStatus);
                                    updateRoomFile(room.getId(), 5, newStatus);
                                    System.out.println("\nStatus updated successfully!");
                                } else {
                                    System.out.println("\nStatus change cancelled.");
                                }
                                break;

                            case "0":
                                System.out.println("\nFinished editing Room " + room.getId() + ".");
                                continueEditing = false;
                                break;

                            default:
                                System.out.println("\nInvalid choice. Please enter a number between 1 and 6.");
                                break;
                        }

                        if (continueEditing) {
                            System.out.print("\nPress [ENTER] to continue editing this room...");
                            sc.nextLine();
                            clear();
                            System.out.println("\n==================================================================");
                            System.out.println("||                      EDIT ROOM SYSTEM                        ||");
                            System.out.println("==================================================================");
                            System.out.println("\nCurrent Details for Room " + room.getId() + ":");
                            displayRoomDetails(room);
                        }
                    }
                    System.out.println("\nPress [ENTER] to return to the main menu...");
                    sc.nextLine();
                    mainPage();
                }
            }

            if (!found) {
                System.out.println("\nRoom not found. Please try again.");
                continue;
            }
            break;
        }
        System.out.println("\nPress [ENTER] to continue..");
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
        System.out.println("\n==================================================================");
        System.out.println("||                      DELETE ROOM SYSTEM                      ||");
        System.out.println("==================================================================");
        String filename = "room.txt";

        if (currentRooms.isEmpty()) {
            System.out.println("\nThe room list is currently empty. No rooms to delete.");
            System.out.println("\nPress [ENTER] to continue...");
            sc.nextLine();
            return;
        }

        System.out.print("\nPlease enter the room ID that you want to delete: ");
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
            System.out.println("\nRoom Found:");
            displayRoomDetails(roomToDelete);
            while (true) {
                System.out.print("\nConfirm to delete this room? (yes/no): ");
                String deleteConfirm = sc.nextLine().trim();
                if (deleteConfirm.equalsIgnoreCase("yes")) {
                    currentRooms.remove(roomIndex);
                    System.out.println("\nRoom with ID '" + idToDelete + "' has been deleted.");
                    writeRoomFile(currentRooms, filename);
                    break;
                } else if (deleteConfirm.equalsIgnoreCase("no")) {
                    System.out.println("\nDeletion cancelled for Room ID '" + idToDelete + "'.");
                    break;
                } else {
                    errorMessageWord();
                }
            }
        } else {
            System.out.println("\nRoom with ID '" + idToDelete + "' not found in the list. No changes made.");
        }
        System.out.println("\nPress [ENTER] to continue...");
        sc.nextLine();
    }

    public static String askAvailableOrOccupied(Scanner sc) {
        while (true) {
            System.out.print("\nEnter new room status (Available/Occupied): ");
            String newStatus = sc.nextLine().trim();
            String confirm = "";

            if (newStatus.equalsIgnoreCase("Available")) {
                return "Available";
            } else if (newStatus.equalsIgnoreCase("Occupied")) {
                return "Occupied";
            }

            if (newStatus.toLowerCase().contains("ava")) {
                System.out.print("\nDid you mean 'Available'? (yes/no): ");
                confirm = sc.nextLine().trim();
                if (confirm.equalsIgnoreCase("yes")) {
                    return "Available";
                } else if (confirm.equalsIgnoreCase("no")) {
                    continue;
                } else {
                    System.out.println("\nPlease input the correct word");
                }
            } else if (newStatus.toLowerCase().contains("occ")) {
                System.out.print("\nDid you mean 'Occupied' ? (yes/no): ");
                if (confirm.equalsIgnoreCase("yes")) {
                    return "Occupied";
                } else if (confirm.equalsIgnoreCase("no")) {
                    continue;
                } else {
                    errorMessageWord();
                }
            } else {
                System.out.println("\nUnrecognized input. Please enter 'Available' or 'Occupied'");
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

                String id = room.getId();
                String capacityStr = String.valueOf(room.getCapacity());
                String type = room.getType();
                String priceStr = String.format("%.2f", room.getPrice());
                String description = room.getDescription();
                String status = room.getStatus();

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
        System.out.print("\n===================================");
        System.out.println("\nError. Invalid input. Please input the correct number.");
    }

    public static void errorMessageWord() {
        System.out.print("\n===================================");
        System.out.println("\nError. Invalid input. Please input the correct word.");
    }

    public static void clear() {
        System.out.println("\033[H\033[2J");
    }
}
