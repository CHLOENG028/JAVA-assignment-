import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class FileLoader {

    private static final String ROOM_FILE = "room.txt";
    private static final String BOOKING_FILE = "booking.txt";
    private static final String TRANSACTION_FILE = "transaction.txt";
    private static final String DELIMITER = "\\|"; 

    public static List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();
        try (Scanner readRoom = new Scanner(new File(ROOM_FILE))) {
            while (readRoom.hasNextLine()) {
                String line = readRoom.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(DELIMITER);
                if (parts.length == 6) { 
                    try {
                        String id = parts[0].trim();
                        String floor = (id.length() > 0) ? id.substring(0, 1) : "?";
                        int capacity = Integer.parseInt(parts[1].trim());
                        String type = parts[2].trim();
                        double price = Double.parseDouble(parts[3].trim());
                        String description = parts[4].trim();
                        String status = parts[5].trim();
                        rooms.add(new Room(id, floor, capacity, type, description, price, status));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping room line due to number format error: " + line + " -> " + e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                         System.err.println("Skipping room line due to error extracting floor: " + line + " -> " + e.getMessage());
                    }
                } else {
                    System.err.println("Skipping room line due to incorrect format (expected 6 parts): " + line);
                }
            }
        } catch (FileNotFoundException e) {
             System.err.println("Error loading rooms file: File not found at " + ROOM_FILE + " -> " + e.getMessage());
        } catch (IOException e) { 
            System.err.println("Error reading rooms file: " + e.getMessage());
        }
        return rooms;
    }

    public static List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd

        try (Scanner readBookings = new Scanner(new File(BOOKING_FILE))) {
            while (readBookings.hasNextLine()) {
                String line = readBookings.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(DELIMITER);
                if (parts.length == 8) {
                    try {
                        String bookingId = parts[0].trim();
                        String customerId = parts[1].trim();
                        String roomType = parts[2].trim();
                        int days = Integer.parseInt(parts[3].trim());
                        int totalPrice = Integer.parseInt(parts[4].trim());
                        String status = parts[5].trim();
                        LocalDate checkIn = LocalDate.parse(parts[6].trim(), dateFormatter);
                        LocalDate checkOut = LocalDate.parse(parts[7].trim(), dateFormatter);
                        bookings.add(new Booking(bookingId, customerId, roomType, days, totalPrice, status, checkIn, checkOut));
                    } catch (NumberFormatException | DateTimeParseException e) {
                        System.err.println("Skipping booking line due to parse error: " + line + " -> " + e.getMessage());
                    }
                } else {
                    System.err.println("Skipping booking line due to incorrect format (expected 8 parts): " + line);
                }
            }
        } catch (FileNotFoundException e) { 
            System.err.println("Error loading bookings file: File not found at " + BOOKING_FILE + " -> " + e.getMessage());
        } catch (IOException e) { 
            System.err.println("Error reading bookings file: " + e.getMessage());
        }
        return bookings;
    }

    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        SimpleDateFormat transactionDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        try (Scanner readTransactions = new Scanner(new File(TRANSACTION_FILE))) {
            while (readTransactions.hasNextLine()) {
                String line = readTransactions.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(DELIMITER);
                if (parts.length == 5) {
                    try {
                        String id = parts[0].trim();
                        String custId = parts[1].trim();
                        String method = parts[2].trim();
                        double amount = Double.parseDouble(parts[3].trim());
                        Date transactionDate = transactionDateFormat.parse(parts[4].trim());

                        transactions.add(new Transaction(id, custId, method, amount, transactionDate));

                    } catch (NumberFormatException e) {
                        System.err.println("Skipping transaction line due to number format error: " + line + " -> " + e.getMessage());
                    } catch (ParseException e) {
                        System.err.println("Skipping transaction line due to date parse error: " + line + " -> " + e.getMessage());
                    } catch (Exception e) { 
                        System.err.println("Skipping transaction line due to unexpected error: " + line + " -> " + e.getMessage());
                    }
                } else {
                    System.err.println("Skipping transaction line due to incorrect format (expected 5 parts): " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading transactions file: File not found at " + TRANSACTION_FILE + " -> " + e.getMessage());
        } catch (IOException e) { 
            System.err.println("Error reading transactions file: " + e.getMessage());
        }
        return transactions;
    }
}

