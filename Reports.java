import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Reports {
        public static void generateReports() {
        System.out.println("\nLoading hotel data...");
        List<Room> hotelRooms = FileLoader.loadRooms();
        List<Booking> allBookings = FileLoader.loadBookings();
        List<Transaction> allTransactions = FileLoader.loadTransactions();
        System.out.println("\nData loaded.");
        System.out.printf("\nRooms: %d, Bookings: %d, Transactions: %d\n",
                hotelRooms.size(), allBookings.size(), allTransactions.size());

        // --- Get Reporting Period from Manager ---
        Scanner scanner = new Scanner(System.in);
        LocalDate startDate = null, endDate = null;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE; // YYYY-MM-DD

        while (startDate == null) {
            System.out.print("\nEnter report start date (YYYY-MM-DD): ");
            try {
                startDate = LocalDate.parse(scanner.nextLine(), inputFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date format. Please use YYYY-MM-DD.");
            }
        }
        while (endDate == null || endDate.isBefore(startDate)) {
            System.out.print("\nEnter report end date (YYYY-MM-DD): ");
            try {
                endDate = LocalDate.parse(scanner.nextLine(), inputFormatter);
                if (endDate.isBefore(startDate)) {
                    System.out.println("\nEnd date cannot be before start date.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date format. Please use YYYY-MM-DD.");
            }
        }

        // --- Calculate Summary Data ---
        System.out.println("\nCalculating report data for " + startDate + " to " + endDate + "...");
        ReportDataAggregator.ReportSummary summary = ReportDataAggregator.calculateDataForPeriod(
                startDate, endDate, allBookings, hotelRooms, allTransactions);
        System.out.println("\nCalculation complete.");

        // --- Generate Reports using Updated Generator ---
        HotelReportGenerator reportGenerator = new HotelReportGenerator(summary, startDate, endDate);

        // --- Show Menu (Example) ---
        int choice = 0;
        while (choice != 4) {
            System.out.println("\n=================================================================");
            System.out.println("||                     GENERATE REPORT MENU                    ||");
            System.out.println("=================================================================");
            System.out.println("||[1] Revenue Report                                           ||");
            System.out.println("||[2] Occupancy Report                                         ||");
            System.out.println("||[3] Performance Report                                       ||");
            System.out.println("||[4] Return to Main Menu                                      ||");
            System.out.println("=================================================================");
            System.out.print("Please choose an option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        reportGenerator.generateRevenueReport();
                        break;
                    case 2:
                        reportGenerator.generateOccupancyReport();
                        break;
                    case 3:
                        reportGenerator.generatePerformanceReport();
                        break;
                    case 4:
                        System.out.println("\nExiting reports...");
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please enter 1-4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a number.");
                choice = 0; // Reset choice to continue loop
            }
            if (choice > 0 && choice < 4) {
                System.out.print("\nPress [ENTER] to return to report menu...");
                scanner.nextLine(); // Pause
            }
        }

        scanner.close();
        System.out.println("\nReport generation finished.");

    }
}
