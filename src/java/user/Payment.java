
package user;

/**
 *
 * @author hazik
 */
public class Payment {
    //data members
    private int paymentId;
    private String date;
    private String status;
    private double amount;
    private String method;
    private byte[] receipt;
    private String bookingType;
    private String custName;
    private String staffName;
    private int bookingId;
    private int custId;
    private int staffId;
    
    
    //constructors
    public Payment() {}

    public Payment(int paymentId, String date, String status, double amount, String method, byte[] receipt, String bookingType, String custName, String staffName, int bookingId, int custId, int staffId) {
        this.paymentId = paymentId;
        this.date = date;
        this.status = status;
        this.amount = amount;
        this.method = method;
        this.receipt = receipt;
        this.bookingType = bookingType;
        this.custName = custName;
        this.staffName = staffName;
        this.bookingId = bookingId;
        this.custId = custId;
        this.staffId = staffId;
    }

    //setter
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
    
    //getter
    public int getPaymentId() {
        return paymentId;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getCustId() {
        return custId;
    }

    public int getStaffId() {
        return staffId;
    }

    public String getBookingType() {
        return bookingType;
    }

    public String getCustName() {
        return custName;
    }

    public String getStaffName() {
        return staffName;
    }

    @Override
    public String toString() {
        return "Payment{" + "paymentId=" + paymentId + ", date=" + date + ", status=" + status + ", amount=" + amount + ", method=" + method + ", receipt=" + receipt + ", bookingId=" + bookingId + ", custId=" + custId + ", staffId=" + staffId + '}';
    }

    void setBlobData(String blobData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
}
