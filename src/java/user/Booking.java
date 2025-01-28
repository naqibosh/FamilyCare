
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
    private String custName;
    private String staffName;
    private String caretakerName;
    
    //constructors
    public Booking() {}

    public Booking(int bookingId, String bookingType, String bookingTime, String bookingDuration, double bookingPrice, int caretakerId, int custId, int staffId, String custName, String staffName, String caretakerName) {
        this.bookingId = bookingId;
        this.bookingType = bookingType;
        this.bookingTime = bookingTime;
        this.bookingDuration = bookingDuration;
        this.bookingPrice = bookingPrice;
        this.caretakerId = caretakerId;
        this.custId = custId;
        this.staffId = staffId;
        this.custName = custName;
        this.staffName = staffName;
        this.caretakerName = caretakerName;
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

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setCaretakerName(String caretakerName) {
        this.caretakerName = caretakerName;
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

    public String getCustName() {
        return custName;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getCaretakerName() {
        return caretakerName;
    }

    @Override
    public String toString() {
        return "Booking{" + "bookingId=" + bookingId + ", bookingType=" + bookingType + ", bookingTime=" + bookingTime + ", bookingDuration=" + bookingDuration + ", bookingPrice=" + bookingPrice + ", caretakerId=" + caretakerId + ", custId=" + custId + ", staffId=" + staffId + '}';
    }
    
}
