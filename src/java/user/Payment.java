
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
    private String receipt;
    private int bookingId;
    private int custId;
    private int staffId;
    
    //constructors
    public Payment(String status, double amount, String method, String receipt, int bookingId, int custId) {
        this.status = status;
        this.amount = amount;
        this.method = method;
        this.receipt = receipt;
        this.bookingId = bookingId;
        this.custId = custId;
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

    public void setReceipt(String receipt) {
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

    public String getReceipt() {
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

    @Override
    public String toString() {
        return "Payment{" + "paymentId=" + paymentId + ", date=" + date + ", status=" + status + ", amount=" + amount + ", method=" + method + ", receipt=" + receipt + ", bookingId=" + bookingId + ", custId=" + custId + ", staffId=" + staffId + '}';
    }
       
}
