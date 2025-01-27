
package user;

/**
 *
 * @author hazik
 */
public class Booking {
    private int bookingId;
    private String bookingType;
    private String bookingTime;
    private String bookingDuration;
    private double bookingPrice;
    private int caretakerId;
    private int custId;
    private int staffId;
    
    //constructors
    public Booking(String bookingType, String bookingTime, String bookingDuration, double bookingPrice, int caretakerId) {
        this.bookingType = bookingType;
        this.bookingTime = bookingTime;
        this.bookingDuration = bookingDuration;
        this.bookingPrice = bookingPrice;
        this.caretakerId = caretakerId;
    }
    
    //setter
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setBookingDuration(String bookingDuration) {
        this.bookingDuration = bookingDuration;
    }

    public void setBookingPrice(double bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public void setCaretakerId(int caretakerId) {
        this.caretakerId = caretakerId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
    
    //getter
    public int getBookingId() {
        return bookingId;
    }

    public String getBookingType() {
        return bookingType;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public String getBookingDuration() {
        return bookingDuration;
    }

    public double getBookingPrice() {
        return bookingPrice;
    }

    public int getCaretakerId() {
        return caretakerId;
    }

    public int getCustId() {
        return custId;
    }

    public int getStaffId() {
        return staffId;
    }

    @Override
    public String toString() {
        return "Booking{" + "bookingId=" + bookingId + ", bookingType=" + bookingType + ", bookingTime=" + bookingTime + ", bookingDuration=" + bookingDuration + ", bookingPrice=" + bookingPrice + ", caretakerId=" + caretakerId + ", custId=" + custId + ", staffId=" + staffId + '}';
    }
    
}
