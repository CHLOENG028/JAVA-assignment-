import java.time.LocalDate;
import java.util.*;


// Renamed to avoid conflict with original example if kept
public class HotelReportGenerator{

    private final ReportDataAggregator.ReportSummary summary;
    private final LocalDate startDate; // Store period for context if needed
    private final LocalDate endDate;

    public HotelReportGenerator(ReportDataAggregator.ReportSummary summary, LocalDate startDate, LocalDate endDate) {
        this.summary = Objects.requireNonNull(summary);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void generateRevenueReport() {
        // --- Use Calculated Metrics ---
        // ADR = Total Room Revenue / Room Days Sold
        double averageDailyRate = (summary.totalRoomDaysSold > 0)
                                  ? summary.estimatedRoomRevenue / summary.totalRoomDaysSold
                                  : 0;

        System.out.println("=========================================");
        System.out.println("           HOTEL REVENUE REPORT          ");
        System.out.println(" Period: " + startDate + " to " + endDate + " (" + summary.durationInDays + " days)");
        System.out.println("=========================================");
        System.out.printf("Total Revenue (Payments): RM %,.2f\n", summary.totalRevenue); // Actual payments
        System.out.printf("Est. Room Revenue       : RM %,.2f\n", summary.estimatedRoomRevenue); // Based on bookings
        System.out.printf("Average Daily Rate (ADR): RM %,.2f\n", averageDailyRate);
        System.out.println("-----------------------------------------");
        System.out.println("Est. Room Revenue by Type:");
        if (summary.revenueByRoomType.isEmpty()) {
            System.out.println("  No room revenue data available for breakdown.");
        } else {
            // Sort room types for consistent order if needed
            summary.revenueByRoomType.entrySet().stream()
                 .sorted(Map.Entry.comparingByKey()) // Sort alphabetically
                 .forEach(entry ->
                     System.out.printf(" - %-18s   : RM %,.2f\n", entry.getKey(), entry.getValue())
                 );
        }
        System.out.println("=========================================\n");
    }

    public void generateOccupancyReport() {
        // Occupancy Rate = (Room Days Sold / Total Available Room Days) * 100
        double occupancyRate = (summary.totalAvailableRoomDays > 0)
                               ? ((double) summary.totalRoomDaysSold / summary.totalAvailableRoomDays) * 100
                               : 0;
        // ALOS = Total Room Days Sold / Number of Bookings
        double averageStayLength = (summary.numberOfBookingsInPeriod > 0)
                                   ? (double) summary.totalRoomDaysSold / summary.numberOfBookingsInPeriod
                                   : 0;

        int vacantRoomDays = summary.totalAvailableRoomDays - summary.totalAvailableRoomDays;

        System.out.println("===========================================");
        System.out.println("          HOTEL OCCUPANCY REPORT         ");
        System.out.println(" Period: " + startDate + " to " + endDate + " (" + summary.durationInDays + " days)");
        System.out.println("===========================================");
        System.out.printf("Total Physical Rooms      : %d\n", summary.totalPhysicalRooms);
        System.out.printf("Total Available Rm Days   : %d\n", summary.totalAvailableRoomDays);
        System.out.printf("Total Room Days Sold      : %d\n", summary.totalRoomDaysSold);
        System.out.printf("Total Vacant Room Days    : %d\n", vacantRoomDays);
        System.out.printf("Occupancy Rate            : %.2f%%\n", occupancyRate);
        System.out.printf("Avg. Length of Stay (ALOS): %.2f Days\n", averageStayLength);
        // Removed guest count as data is not available
        System.out.println("===========================================\n");
    }

    public void generatePerformanceReport() {
        // RevPAR = Total Room Revenue / Total Available Room Days
        double revenuePerAvailableRoom = (summary.totalAvailableRoomDays > 0)
                                         ? summary.estimatedRoomRevenue / summary.totalAvailableRoomDays
                                         : 0;
        // Re-calculate occupancy rate or get from summary if stored/passed differently
         double occupancyRate = (summary.totalAvailableRoomDays > 0)
                               ? ((double) summary.totalRoomDaysSold / summary.totalAvailableRoomDays) * 100
                               : 0;
        // ADR calculation needed again or passed if structure changes
        double averageDailyRate = (summary.totalRoomDaysSold > 0)
                                  ? summary.estimatedRoomRevenue / summary.totalRoomDaysSold
                                  : 0;

        System.out.println("============================================================");
        System.out.println("                  HOTEL PERFORMANCE REPORT                  ");
        System.out.println("          Period: " + startDate + " to " + endDate + " (" + summary.durationInDays + " days)");
        System.out.println("============================================================");
        System.out.printf("Occupancy Rate                      : %.2f%%\n", occupancyRate);
        System.out.printf("Average Daily Rate (ADR)            : RM %,.2f\n", averageDailyRate);
        System.out.printf("Revenue per Avail. Rm Day(RevPAR)   : RM %,.2f\n", revenuePerAvailableRoom); // Clarified metric name
        System.out.println("------------------------------------------------------------");
        System.out.println("Performance Overview:");
        if (occupancyRate > 75) {
            System.out.println(" - Excellent occupancy! Business is strong.");
        } else if (occupancyRate > 50) {
            System.out.println(" - Moderate occupancy. Room for improvement.");
        } else {
            System.out.println(" - Low occupancy. Consider promotions.");
        }
        // Add comparisons to budget or history here if that data becomes available
        System.out.println("============================================================");
    }
}
