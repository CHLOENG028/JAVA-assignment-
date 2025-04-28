
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {

    String bookingId;
    String customerId;
    String roomType;
    int days;
    int totalPrice; // Assuming this is the total room revenue for the stay
    String status;
    LocalDate checkInDate;
    LocalDate checkOutDate;

    public Booking(String bookingId, String customerId, String roomType, int days, int totalPrice, String status, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomType = roomType;
        this.days = days;
        this.totalPrice = totalPrice;
        this.status = status;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate; // Check-out date is usually the morning *after* the last night
    }

    // Getters...
    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getDays() {
        return days;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public boolean overlapsWith(LocalDate periodStart, LocalDate periodEnd) {
        return !checkInDate.isAfter(periodEnd) && !checkOutDate.isBefore(periodStart.plusDays(1)); // Include stays ending on start date
        //Check in did not happend after period end 
        //Check out did not happend at the second day of the stay
    }

    public int getDaysInPeriod(LocalDate periodStart, LocalDate periodEnd) {
        if (!overlapsWith(periodStart, periodEnd)) {
            return 0;
        }
        LocalDate actualStart = checkInDate.isBefore(periodStart) ? periodStart : checkInDate;
        LocalDate actualEnd = checkOutDate.isAfter(periodEnd.plusDays(1)) ? periodEnd.plusDays(1) : checkOutDate;

        return (int) ChronoUnit.DAYS.between(actualStart, actualEnd);
    }   //is a long, include first day and exclude last day

    public boolean isActiveOnDate(LocalDate date) {
        return !checkInDate.isAfter(date) && checkOutDate.isAfter(date);
    }

    @Override
    public String toString() {
        return "Booking{bookingId='" + bookingId + "', customerId='" + customerId + "', roomType='" + roomType + "', days=" + days + ", totalPrice=" + totalPrice + ", status='" + status + "', checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + "}";
    }
}

