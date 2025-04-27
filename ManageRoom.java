
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

    //Import from file into ArrayList 
    public static void mainPageRoom() {
        rooms = readRoomFile();
        Scanner sc = new Scanner(System.in);
        int action = 0;
        System.out.println("\n\t\t********************************************");
        System.out.println("\t\t\tHOTEL ROOM MANAGEMENT SYSTEM");
        System.out.println("\t\t********************************************");

        // System.out.println("\n\t\t============================================");
        // System.out.println("\t\t|\tHOTEL ROOM MANAGEMENT SYSTEM\t   |");
        // System.out.println("\t\t============================================");
        while (action != 5) {
            while (true) {
                System.out.println("\nPlease select an option: ");
                System.out.println("\n[1] Add New Room\n[2] Edit Room\n[3] Delete Room\n[4] Search Room\n[5] Exit");
                System.out.print("Please choose your action (1-5): ");

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
                        System.out.print("Confirm to add new room? (yes/no): ");
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
                        System.out.print("Confirm to edit room? (yes/no): ");
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
                    deleteRoom(rooms);
                    break;
                case 4:
                    while (true) {
                        System.out.print("Confirm to show room? (yes/no): ");
                        String showConfirm = sc.nextLine();
                        if (showConfirm.equalsIgnoreCase("yes")) {
                            showRoom();
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
                        System.out.print("Confirm to exit? (yes/no): ");
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

    public static void showRoom() {
        Scanner sc = new Scanner(System.in);
        int search = 0;
        String type = "";

        System.out.println("What kind of room you want to search for?");

        while (true) {
            System.out.println("[1] Room ID\n[2] Room Floor\n[3] Room Type\n[4] Room Capacity\n[5] Room Description\n[6] Room Price\n[7] Room Status\n[8] Exit");
            System.out.print("Enter the kind you want to input for searching? (1-9): ");
            //not searc num
            Map<Integer, String> choicesString = new HashMap<>();
            choicesString.put(1, "Room ID");
            choicesString.put(2, "Room Floor");
            choicesString.put(3, "Room Type");
            choicesString.put(4, "Room Capacity");
            choicesString.put(5, "Room Description");
            choicesString.put(6, "Room Price");
            choicesString.put(7, "Room Status");

            if (sc.hasNextInt()) {
                search = sc.nextInt();
                sc.nextLine();

                if (search < 1 || search > 8) {
                    errorMessageNumber();
                } else {
                    String stringChosen = choicesString.getOrDefault(search, "Invalid input");
                    System.out.print("Are you confirm to choose '" + stringChosen + "' as your search? (yes/no) :");
                    String confirmation = sc.nextLine();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        break;
                    } else {
                        errorMessageNumber();
                    }
                }
            } else {
                errorMessageNumber();
                sc.next();
            }
        }
        switch (search) {
            case 1:
                System.out.print("Enter the room ID: ");
                String id = sc.nextLine();
                ArrayList<Room> byId = SearchRoomByAttribute(rooms, search, id);
                if (!byId.isEmpty()) {
                    displayRoomDetails(byId.get(0));
                }
                break;
            case 2:
                System.out.print("Enter the room floor");
                String floor = sc.nextLine();
                ArrayList<Room> byFloor = SearchRoomByAttribute(rooms, search, floor);
                if (!byFloor.isEmpty()) {
                    filterRoomAttributes(rooms, sc);
                }
                break;
            case 3:
                System.out.print("Enter room type: ");
                int typeRoom = 0;
                while (true) {
                    System.out.println("\n[1] Standard\n[2] Deluxe\n[3] Family\n[4] Suite\n[5] Executive");
                    System.out.print("Enter the room type (1-5): ");
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
                ArrayList<Room> byType = SearchRoomByAttribute(rooms, search, type);
                if (!byType.isEmpty()) {
                    filterRoomAttributes(byType, sc);
                }
                break;
            case 4:
                System.out.print("Enter the room capacity: ");
                String capacity = sc.nextLine();
                ArrayList<Room> byCapacity = SearchRoomByAttribute(rooms, search, capacity);
                if (!byCapacity.isEmpty()) {
                    filterRoomAttributes(byCapacity, sc);
                }
                break;
            case 5:
                System.out.print("Enter the room description (any word related: simple): ");
                String description = sc.nextLine();
                ArrayList<Room> byDescription = SearchRoomByAttribute(rooms, search, description);
                if (!byDescription.isEmpty()) {
                    filterRoomAttributes(byDescription, sc);
                }
                break;
            case 6:
                System.out.print("Enter the room price: RM ");
                String price = sc.nextLine();
                ArrayList<Room> byPrice = SearchRoomByAttribute(rooms, search, price);
                if (!byPrice.isEmpty()) {
                    filterRoomAttributes(byPrice, sc);
                }
                break;
            case 7:
                System.out.print("Enter the room satus: ");
                String status = sc.nextLine();
                ArrayList<Room> byStatus = SearchRoomByAttribute(rooms, search, status);
                if (!byStatus.isEmpty()) {
                    filterRoomAttributes(byStatus, sc);
                }
                break;
            case 8:
                mainPageRoom();
                break;
        }

        System.out.println("What action you want to do next?");
        int choice = 0;
        while (true) {
            System.out.println("[1] Edit Room\n[2] Delete Room\n[3] Exit");
            System.out.print("Enter the action you want next: ");
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
                mainPageRoom();
                break;
            default:
                System.out.println("Invalid action.");
                break;
        }
        System.out.println("Press [ENTER] to continue");
        sc.nextLine();
        mainPageRoom();
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
                    match = room.getType().equalsIgnoreCase(value);
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
            System.out.println("No matching room found.");
        }
        return matchedRooms;
    }

    public static void filterRoomAttributes(ArrayList<Room> rooms, Scanner sc) {
        while (true) {
            System.out.println("Filter attributes for viewing the rooms in what details? :\n[1] Room ID\n[2] Room Floor\n[3] Room Type\n[4] Room Capcity\n[5] Room Description\n[6] Room Price\n[7] Room Status\n");
            System.out.print("Enter the attributes you want to view (e.g. 1,2,3): ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Try again.");
                continue;
            }
            String[] options = input.split(",");
            for (Room room : rooms) {
                for (String option : options) {
                    switch (option.trim()) {
                        //verification? suggest
                        case "1":
                            System.out.println("Room ID: " + room.getId());
                            break;
                        case "2":
                            System.out.println("Room Floor: " + room.getFloor());
                            break;
                        case "3":
                            System.out.println("Room Type: " + room.getType());
                            break;
                        case "4":
                            System.out.println("Room Capacity: " + room.getCapacity());
                            break;
                        case "5":
                            System.out.println("Room Description: " + room.getDescription());
                            break;
                        case "6":
                            System.out.println("Room Price: RM " + room.getPrice());
                            break;
                        case "7":
                            System.out.println("Room Status: " + room.getStatus());
                            break;
                    }
                }
                break;
            }
        }
    }

    public static void displayRoomDetails(Room room) {
        System.out.println("===========================");
        System.out.println("Room ID: " + room.getId());
        System.out.println("Room Floor: " + room.getFloor());
        System.out.println("Type: " + room.getType());
        System.out.println("Capacity: " + room.getCapacity());
        System.out.println("Price: " + room.getPrice());
        System.out.println("Description: " + room.getDescription());
        System.out.println("Status: " + room.getStatus());
        System.out.println("===========================");
    }

    public static void addNewRoom(ArrayList<Room> rooms) {
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

        do {
            while (true) {
                System.out.print("Enter floor(Room number will be automated generated): ");
                floor = sc.nextLine();
                if (floor.isEmpty()) {
                    System.out.println("Floor cannot be empty. Please try again.");
                } else {
                    if (floor.length() == 1) {
                        char inputFloor = floor.charAt(0);
                        if (!Character.isDigit(inputFloor)) {
                            System.out.println("Only digits are allowed");
                        } else {
                            break;
                        }
                    } else {
                        System.out.println("Only input one digit. Please try again.\n");
                    }
                }
            }
            while (true) {
                System.out.print("Enter capacity: ");
                capacityInput = sc.nextLine().trim();
                if (capacityInput.isEmpty()) {
                    System.out.println("Capacity cannot be empty");
                } else {
                    if (!isDigitsOnly(capacityInput)) {
                        System.out.println("Invalid number, only integer is allowed.");
                    } else {
                        break;
                    }
                }
            }
            while (true) {
                System.out.print("Enter room type: ");
                int typeRoom = 0;
                while (true) {
                    System.out.println("\n[1] Standard\n[2] Deluxe\n[3] Family\n[4] Suite\n[5] Executive");
                    System.out.print("Enter the room type (1-5): ");
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
                System.out.println("Choose a description");
                for (int i = 0; i < descriptions.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + descriptions.get(i));
                }
                System.out.println("[" + (descriptions.size() + 1) + "] Write a custom description");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice >= 1 && choice <= descriptions.size()) {
                    description = descriptions.get(choice - 1);
                    break;
                } else if (choice == descriptions.size() + 1) {
                    System.out.print("Enter your custom description: ");
                    description = sc.nextLine();
                    break;
                } else {
                    System.out.print("Invalid choice. Description not set.");
                }
            }

            while (true) {
                System.out.print("Enter room price: RM ");
                if (sc.hasNextDouble()) {
                    price = sc.nextDouble();
                    sc.nextLine();
                    break;
                } else {
                    System.out.println("Please input a correct price.");
                }
            }

            while (true) {
                System.out.print("Enter the status of the room: ");
                status = askAvailableOrOccupied(sc);
                if (status.isEmpty()) {
                    System.out.println("Status cannot be empty");
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
                            System.out.println("Invalid");
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

            System.out.println("=================================");
            System.out.println("Please confirm the details below:");
            System.out.println("=================================");
            System.out.println("Room ID: " + newRoomId);
            System.out.println("Floor " + floor);
            System.out.println("Capacity: " + capacity);
            System.out.println("Type: " + type);
            System.out.println("Description: " + description);
            System.out.printf("Price: RM %.2f%n", price);
            System.out.println("Status: " + status);
            System.out.println("==============================");

            while (true) {
                System.out.print("Do you confirm the details above are correct? (yes/no)");
                String confirmation = sc.nextLine().trim();

                if (confirmation.equalsIgnoreCase("yes")) {
                    detailsConfirm = true;
                } else if (confirmation.equalsIgnoreCase("no")) {
                    System.out.print("\nDetails not confirmed. Please re-enter the room information by pressing [ENTER] or press [Q] to exit");
                    String continueAdd = sc.nextLine();
                    if (continueAdd.equalsIgnoreCase("q")) {
                        break;
                    }
                } else {
                    errorMessageWord();
                }
            }
        } while (!detailsConfirm);
        Room room = new Room(newRoomId, floor, capacity, type, description, price, status);
        rooms.add(room);//add this object to rooms array
        try {
            FileWriter myWriter = new FileWriter("room.txt", true);
            myWriter.write(newRoomId + "|" + capacity + "|" + type + "|" + description + "|" + price + "|" + status + System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("The room is failed to add. Please try again.");
        }
        System.out.println("Room is added successfully!");
        System.out.println("Press [ENTER] to continue..");
        sc.nextLine();
        mainPageRoom();
    }

    public static void editRoom(ArrayList<Room> rooms) {
        Scanner sc = new Scanner(System.in);
        boolean found = false;
        while (true) {
            int update;
            String typeName = "";
            System.out.print("Enter the room ID to edit: ");
            String targetRoom = sc.nextLine();
            for (Room room : rooms) {
                if (room.getId().equalsIgnoreCase(targetRoom)) {
                    found = true;
                    System.out.println("Room found: ");
                    displayRoomDetails(room);

                    System.out.println("What do you want to edit? (Can enter multiple choice using ','[eg: 1,2,3] )");
                    System.out.println("[1] Room Capacity\n[2] Room Type\n[3] Room Description\n[4] Room Price\n[5] Room Status");
                    System.out.print("Enter the choices you want to edit: ");
                    String editDetails = sc.nextLine().trim();

                    String[] options = editDetails.split(",");
                    for (String option : options) {
                        switch (option.trim()) {
                            case "1":
                                int newCapacity = 0;
                                while (true) {
                                    System.out.print("Enter the new capacity: ");
                                    if (sc.hasNextInt()) {
                                        newCapacity = sc.nextInt();
                                        sc.nextLine();
                                        break;
                                    } else {
                                        System.out.print("Invalid input. Please enter an integer.");
                                        sc.next();
                                    }
                                }
                                update = 1;
                                updateRoomFile(targetRoom, update, String.valueOf(newCapacity));
                                System.out.println("Capacity of Room " + room.getId() + " updated successfully!");
                                break;
                            case "2":
                                int newType = 0;
                                while (true) {
                                    System.out.println("\n[1] Standard\n[2] Deluxe\n[3] Family\n[4] Suite\n[5] Executive");
                                    System.out.print("Enter the type to change(1-5)");
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
                                update = 2;
                                updateRoomFile(targetRoom, update, typeName);
                                System.out.println("Type of Room " + room.getId() + " updated successfully!");
                                sc.nextLine();
                                break;
                            case "3":
                                while (true) {
                                    System.out.println("Choose a description");
                                    for (int i = 0; i < descriptions.size(); i++) {
                                        System.out.println("[" + (i + 1) + "] " + descriptions.get(i));
                                    }
                                    System.out.println("[" + (descriptions.size() + 1) + "] Write a custom description");
                                    System.out.print("Enter your choice: ");
                                    int choice = sc.nextInt();
                                    sc.nextLine();
                                    String newDescription;

                                    if (choice >= 1 && choice <= descriptions.size()) {
                                        newDescription = descriptions.get(choice - 1);
                                        room.setDescription(newDescription);
                                        update = 3;
                                        updateRoomFile(targetRoom, update, newDescription);
                                        System.out.println("Description of Room " + room.getId() + " updated successfully!");
                                        break;
                                    } else if (choice == descriptions.size() + 1) {
                                        System.out.print("Enter your custom description: ");
                                        newDescription = sc.nextLine();
                                        room.setDescription(newDescription);
                                        update = 3;
                                        updateRoomFile(targetRoom, update, newDescription);
                                        System.out.println("Description of Room " + room.getId() + " updated successfully!");
                                    } else {
                                        System.out.print("Invalid choice. Description not set.");
                                        continue;
                                    }
                                    break;
                                }
                            case "4":
                                while (true) {
                                    System.out.print("Enter room price: RM ");
                                    double newPrice = sc.nextDouble();
                                    sc.nextLine();
                                    System.out.print("Are you sure the room price is changed to RM " + newPrice + " ? (yes/no)");
                                    String priceConfirm = sc.nextLine();
                                    if (priceConfirm.equalsIgnoreCase("yes")) {
                                        room.setPrice(newPrice);
                                        update = 4;
                                        updateRoomFile(targetRoom, update, String.valueOf(newPrice));
                                        System.out.println("Price of Room " + room.getId() + " updated successfully!");
                                        sc.nextLine();
                                    } else {
                                        break;
                                    }
                                }
                                break;
                            case "5":
                                String newStatus = askAvailableOrOccupied(sc);
                                room.setStatus(newStatus);
                                update = 5;
                                updateRoomFile(targetRoom, update, newStatus);
                                System.out.println("Status of Room " + room.getId() + " update successfully!");
                                sc.nextLine();
                                break;
                        }
                    }
                    break;

                }
            }

            if (!found) {
                System.out.println("\nRoom not found. Please try again.");
                continue;
            }
            break;
        }
        System.out.println("Press [ENTER] to continue..");
        sc.nextLine();
        mainPageRoom();
    }

    public static void deleteRoom(ArrayList<Room> currentRooms) {
        String filename = "room.txt";
        if (currentRooms == null) {
            System.err.println("Error: Cannot delete from a null list.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the room ID that you want to delete: ");
        String idToDelete = sc.nextLine().trim();

        while (true) {
            System.out.print("Confirm to delete room? (yes/no): ");
            String deleteConfirm = sc.nextLine();
            if (deleteConfirm.equalsIgnoreCase("yes")) {
                boolean removed = currentRooms.removeIf(room -> room != null && idToDelete.equals(room.getId()));

                if (removed) {
                    System.out.println("Room with ID '" + idToDelete + "' found and removed from the list.");
                    writeRoomFile(currentRooms, filename);
                } else {
                    System.out.println("Room with ID '" + idToDelete + "' not found in the list. No changes made.");
                }

                break;
            } else if (deleteConfirm.equalsIgnoreCase("no")) {
                System.out.println("Deletion cancelled for Room ID '" + idToDelete + "'.");
                break;
            } else {
                errorMessageWord();
            }
        }

        System.out.println("Press [ENTER] to continue...");
        sc.nextLine();
    }

    public static String askAvailableOrOccupied(Scanner sc) {
        while (true) {
            System.out.print("Enter new room status (Available/Occupied): ");
            String newStatus = sc.nextLine().trim();
            String confirm = "";

            if (newStatus.equalsIgnoreCase("Available")) {
                return "Available";
            } else if (newStatus.equalsIgnoreCase("Occupied")) {
                return "Occupied";
            }

            if (newStatus.toLowerCase().contains("ava")) {
                System.out.print("Did you mean 'Available'? (yes/no): ");
                confirm = sc.nextLine().trim();
                if (confirm.equalsIgnoreCase("yes")) {
                    return "Available";
                } else if (confirm.equalsIgnoreCase("no")) {
                    continue;
                } else {
                    System.out.println("Please input the correct word");
                }
            } else if (newStatus.toLowerCase().contains("occ")) {
                System.out.print("Did you mean 'Occupied' ? (yes/no)");
                if (confirm.equalsIgnoreCase("yes")) {
                    return "Occupied";
                } else if (confirm.equalsIgnoreCase("no")) {
                    continue;
                } else {
                    errorMessageWord();
                }
            } else {
                System.out.println("Unrecognized input. Please enter 'Available' or 'Occupied'");
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

        File room = new File("room.txt");

        try {
            if (!room.exists()){
                System.out.println("File not found, creating a new empty file.");
            }else{
                try {
                    if (!room.createNewFile()) {
                        if (room.createNewFile()) {
                            System.out.println("File created successfully. Please add some rooms details first via 'Add Room'");
                        } else {
                            System.out.println("File already existed.");
                        }
                    }
                } catch (IOException ioEx) {
                    System.out.println("Error: Couldnt crete file 'room.txt'");
                    return rooms;
                }
            }
            Scanner readRoom = new Scanner(room);

            while (readRoom.hasNextLine()) {
                String line = readRoom.nextLine();
                String[] parts = line.split("\\|");

                if (parts.length == 6) {
                    String id = parts[0].trim(); //clear spaces
                    String floor = id.substring(0, 1);
                    int capacity = Integer.parseInt(parts[1].trim());
                    String type = parts[2].trim();
                    String description = parts[3].trim();
                    double price = Double.parseDouble(parts[4].trim());
                    String status = parts[5].trim();

                    Room r = new Room(id, floor, capacity, type, description, price, status);
                    rooms.add(r);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found");
        }
        return rooms;
    }

    public static void writeRoomFile(ArrayList<Room> roomList, String filename) {
        File roomFile = new File(filename);

        try (FileWriter fileWriter = new FileWriter(roomFile, false)) {
            for (Room room : roomList) {

                String capacity = String.valueOf(room.getCapacity());
                String price = String.valueOf(room.getPrice());

                String line = String.join("|",
                        room.getId(),
                        capacity,
                        room.getType(),
                        room.getDescription(),
                        price,
                        room.getStatus()
                );
                fileWriter.write(line + System.lineSeparator());
            }

        } catch (IOException e) {
            System.err.println("Error writing to file '" + filename + "': " + e.getMessage());
        }
    }

    public static void updateRoomFile(String roomId, int update, String newValue) {
        File room = new File("room.txt");
        try {
            Scanner updateRoom = new Scanner(room);
            List<String> updatedLines = new ArrayList<>();

            while (updateRoom.hasNextLine()) {
                String line = updateRoom.nextLine();
                String[] parts = line.split("\\|");

                if (parts.length > 0 && roomId.equals(parts[0].trim())) {
                    switch (update) {
                        case 1: //capacity
                            parts[1] = newValue;
                            break;
                        case 2: //type
                            parts[2] = newValue;
                            break;
                        case 3: //Desc
                            parts[3] = newValue;
                            break;
                        case 4: //Price
                            parts[4] = newValue;
                            break;
                        case 5: //Status
                            parts[5] = newValue;
                            break;
                    }
                }
                updatedLines.add(String.join("|", parts)); //become back the format in file and add into updatedLines array
            }

            try (FileWriter writer = new FileWriter("room.txt", false)) { // false to overwrite file
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
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
