import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;


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
        // ADR = Total Room Revenue / Room Nights Sold
        double averageDailyRate = (summary.totalRoomNightsSold > 0)
                                  ? summary.estimatedRoomRevenue / summary.totalRoomNightsSold
                                  : 0;

        System.out.println("\n\t\t============================================================");
        System.out.println("\t\t||                  HOTEL REVENUE REPORT                  ||");
        System.out.println("\t\t||--------------------------------------------------------||");
        System.out.printf("\t\t|| Period: %-10s to %-10s (%2d nights)          ||\n", startDate, endDate, summary.durationInNights);
        System.out.println("\t\t============================================================");
        System.out.printf("\t\t|| Total Revenue (Payments): RM %,-20.2f ||\n", summary.totalRevenue);
        System.out.printf("\t\t|| Est. Room Revenue       : RM %,-20.2f ||\n", summary.estimatedRoomRevenue);
        System.out.printf("\t\t|| Average Daily Rate (ADR): RM %,-20.2f ||\n", averageDailyRate);
        System.out.println("\t\t------------------------------------------------------------");
        System.out.println("\t\t| Est. Room Revenue by Type:                              |");
        if (summary.revenueByRoomType.isEmpty()) {
            System.out.println("  \t\t|No room revenue data available for breakdown. \t\t   |");
        } else {
            // Sort room types for consistent order if needed
            summary.revenueByRoomType.entrySet().stream()
                 .sorted(Map.Entry.comparingByKey()) // Sort alphabetically
                 .forEach(entry ->
                     System.out.printf("\n\t\t| - %-18s : RM %,.2f\n", entry.getKey(), entry.getValue())
                 );
        }
        System.out.println("\t\t============================================================");
    }

    public void generateOccupancyReport() {
        // Occupancy Rate = (Room Nights Sold / Total Available Room Nights) * 100
        double occupancyRate = (summary.totalAvailableRoomNights > 0)
                               ? ((double) summary.totalRoomNightsSold / summary.totalAvailableRoomNights) * 100
                               : 0;
        // ALOS = Total Room Nights Sold / Number of Bookings
        double averageStayLength = (summary.numberOfBookingsInPeriod > 0)
                                   ? (double) summary.totalRoomNightsSold / summary.numberOfBookingsInPeriod
                                   : 0;

        int vacantRoomNights = summary.totalAvailableRoomNights - summary.totalRoomNightsSold;

        System.out.println("\n\t\t============================================================");
        System.out.println("\t\t||                  HOTEL OCCUPANCY REPORT                ||");
        System.out.println("\t\t||--------------------------------------------------------||");
        System.out.printf("\t\t|| Period: %-10s to %-10s (%2d nights)          ||\n", startDate, endDate, summary.durationInNights);
        System.out.println("\t\t============================================================");
        System.out.printf("\t\t|| Total Physical Rooms      : %-25d ||\n", summary.totalPhysicalRooms);
        System.out.printf("\t\t|| Total Available Room Nights : %-22d ||\n", summary.totalAvailableRoomNights);
        System.out.printf("\t\t|| Total Room Nights Sold    : %-25d ||\n", summary.totalRoomNightsSold);
        System.out.printf("\t\t|| Total Vacant Room Nights  : %-25d ||\n", vacantRoomNights);
        System.out.printf("\t\t|| Occupancy Rate            : %-24.2f%% ||\n", occupancyRate);
        System.out.printf("\t\t|| Avg. Length of Stay (ALOS): %-24.2f nights ||\n", averageStayLength);
        System.out.println("\t\t============================================================");
    }

    public void generatePerformanceReport() {
        // RevPAR = Total Room Revenue / Total Available Room Nights
        double revenuePerAvailableRoom = (summary.totalAvailableRoomNights > 0)
                                         ? summary.estimatedRoomRevenue / summary.totalAvailableRoomNights
                                         : 0;
        // Re-calculate occupancy rate or get from summary if stored/passed differently
         double occupancyRate = (summary.totalAvailableRoomNights > 0)
                               ? ((double) summary.totalRoomNightsSold / summary.totalAvailableRoomNights) * 100
                               : 0;
         // ADR calculation needed again or passed if structure changes
          double averageDailyRate = (summary.totalRoomNightsSold > 0)
                                  ? summary.estimatedRoomRevenue / summary.totalRoomNightsSold
                                  : 0;

            System.out.println("\n\t\t============================================================");
            System.out.println("\t\t||                  HOTEL PERFORMANCE REPORT              ||");
            System.out.println("\t\t||--------------------------------------------------------||");
            System.out.printf("\t\t|| Period: %-10s to %-10s (%2d nights)          ||\n", startDate, endDate, summary.durationInNights);
            System.out.println("\t\t============================================================");
            System.out.printf("\t\t|| Occupancy Rate            : %-24.2f%% ||\n", occupancyRate);
            System.out.printf("\t\t|| Average Daily Rate (ADR)  : RM %,-20.2f ||\n", averageDailyRate);
            System.out.printf("\t\t|| Revenue per Avail. Rm Night (RevPAR): RM %,-20.2f ||\n", revenuePerAvailableRoom);
            System.out.println("\t\t------------------------------------------------------------");
            System.out.println("\t\t| Performance Overview:                                    |");
                                  
            if (occupancyRate > 75) {
                System.out.println("\t\t|   - Excellent occupancy! Business is strong.            |");
            } else if (occupancyRate > 50) {
                System.out.println("\t\t|   - Moderate occupancy. Room for improvement.           |");
            } else {
                System.out.println("\t\t|   - Low occupancy. Consider promotions.                 |");
            }
                                  
            System.out.println("\t\t============================================================");
    }
}
