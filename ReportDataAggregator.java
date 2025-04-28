
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ReportDataAggregator {

    public static class ReportSummary {

        double totalRevenue;          // Sum of transaction amounts in period
        double estimatedRoomRevenue;  // Sum of prorated booking prices in period
        int totalAvailableRoomDays; // totalAvailableRooms * duration
        int totalRoomDaysSold;      // Sum of days occupied within period
        int numberOfBookingsInPeriod;
        Map<String, Double> revenueByRoomType; // Estimated based on booking prices
        int totalPhysicalRooms;       // Total rooms listed
        long durationInDays;          // Length of reporting period
    }

    public static ReportSummary calculateDataForPeriod(LocalDate startDate, LocalDate endDate,
            List<Booking> allBookings,
            List<Room> allRooms,
            List<Transaction> allTransactions) {

        ReportSummary summary = new ReportSummary();
        summary.revenueByRoomType = new HashMap<>();
        summary.durationInDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        // --- Calculate Available Rooms & Days ---
        // Assuming all rooms in the list are potentially available
        // Add logic here to exclude "OutOfOrder" if that status exists and matters
        summary.totalPhysicalRooms = allRooms.size();
        summary.totalAvailableRoomDays = (int) (summary.totalPhysicalRooms * summary.durationInDays);

        // --- Process Bookings ---
        int roomDaysSold = 0;
        int bookingCount = 0;
        double estimatedRoomRevenue = 0;

        for (Booking booking : allBookings) {
            if (booking.overlapsWith(startDate, endDate)) {
                // Check if booking status indicates it actually happened (e.g., not 'CANCELLED')
                // Add status check if necessary: if(!booking.getStatus().equals("CANCELLED")) { ... }
                bookingCount++;
                roomDaysSold += booking.getDays();

                // Estimate room revenue for the period from this booking
                if (booking.getDays() > 0 && booking.getDays() > 0) {
                    double proratedRevenue = ((double) booking.getTotalPrice() - 200); //-200 for deposit
                    estimatedRoomRevenue += proratedRevenue;
                    summary.revenueByRoomType.merge(booking.getRoomType(), proratedRevenue, Double::sum);
                }
            }
        }
        summary.totalRoomDaysSold = roomDaysSold;
        summary.numberOfBookingsInPeriod = bookingCount;
        summary.estimatedRoomRevenue = estimatedRoomRevenue; // Store the estimation

        // --- Process Transactions ---
        double revenueTotal = 0;
        for (Transaction transaction : allTransactions) {

            java.util.Date legacyDate = transaction.getDate(); //Get old format of date 
            if (legacyDate == null) {
                System.err.println("Skipping transaction with null date: ID " + transaction.getTransactionId()); 
                continue;
            }
            LocalDate transactionDate = legacyDate.toInstant()// A fixed point of timeline
                    .atZone(ZoneId.systemDefault()) //Change to KL zone, using system default
                    .toLocalDate(); //From the converted zone time, extract date part (Year,Month,Day)

            // Now compare using the converted LocalDate
            if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                revenueTotal += transaction.getTotalPrice();

            }
        }
        summary.totalRevenue = revenueTotal;

        return summary;
    }
}
