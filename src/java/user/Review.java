
package user;

/**
 *
 * @author hazik
 */
public class Review {
    private int reviewId;
    private double rating = 0.00;
    private String comments;
    private String reply;
    private String date;
    private int bookingId;
    private int custId;
    private int staffId;
    
    //constructors
    public Review() {}

    public Review(String comments, String reply, String date, int bookingId, int custId) {
        this.comments = comments;
        this.reply = reply;
        this.date = date;
        this.bookingId = bookingId;
        this.custId = custId;
    }
    
    //setter
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public void setDate(String date) {
        this.date = date;
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
    public int getReviewId() {
        return reviewId;
    }

    public double getRating() {
        return rating;
    }

    public String getComments() {
        return comments;
    }

    public String getReply() {
        return reply;
    }

    public String getDate() {
        return date;
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
        return "Review{" + "reviewId=" + reviewId + ", rating=" + rating + ", comments=" + comments + ", reply=" + reply + ", date=" + date + ", bookingId=" + bookingId + ", custId=" + custId + ", staffId=" + staffId + '}';
    }
    
    
}
