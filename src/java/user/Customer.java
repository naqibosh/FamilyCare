package user;

/**
 *
 * @author hazik
 */
public class Customer {
    //data members
    private int custId;
    private String custUsername;
    private String custPass;
    private String custFName;
    private String custLName;
    private String custPhone;
    private String custEmail;
    private String custIC;
    private String banDate;
    private int statusId;
    private String status;
    private String is_active;
    
    //constructors
    public Customer(){}

    public Customer(int custId, String custUsername, String custFName, String custLName, String custPhone, String custEmail, String custIC, String banDate, int statusId, String status, String is_active) {
        this.custId = custId;
        this.custUsername = custUsername;
        this.custFName = custFName;
        this.custLName = custLName;
        this.custPhone = custPhone;
        this.custEmail = custEmail;
        this.custIC = custIC;
        this.banDate = banDate;
        this.statusId = statusId;
        this.status = status;
        this.is_active = is_active;
    }

    public Customer(String custUsername, String custPass, String custFName, String custLName, String custPhone, String custEmail, String custIC) {
        this.custUsername = custUsername;
        this.custPass = custPass;
        this.custFName = custFName;
        this.custLName = custLName;
        this.custPhone = custPhone;
        this.custEmail = custEmail;
        this.custIC = custIC;
    }
    
    public Customer(String custUsername, String custFName, String custLName, String custPhone, String custEmail, String custIC) {
        this.custUsername = custUsername;
        this.custFName = custFName;
        this.custLName = custLName;
        this.custPhone = custPhone;
        this.custEmail = custEmail;
        this.custIC = custIC;
    }
    
    //setter
    public void setCustId(int custId) {
        this.custId = custId;
    }

    public void setCustUsername(String custUsername) {
        this.custUsername = custUsername;
    }

    public void setCustPass(String custPass) {
        this.custPass = custPass;
    }

    public void setCustFName(String custFName) {
        this.custFName = custFName;
    }

    public void setCustLName(String custLName) {
        this.custLName = custLName;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public void setCustIC(String custIC) {
        this.custIC = custIC;
    }

    public void setBanDate(String banDate) {
        this.banDate = banDate;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
    
        
    //getter
    public int getCustId() {
        return custId;
    }

    public String getCustUsername() {
        return custUsername;
    }

    public String getCustPass() {
        return custPass;
    }

    public String getCustFName() {
        return custFName;
    }

    public String getCustLName() {
        return custLName;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public String getCustIC() {
        return custIC;
    }

    public String getBanDate() {
        return banDate;
    }

    public int getStatusId() {
        return statusId;
    }
    
    public String getStatus() {
        return status;
    }

    public String getIs_active() {
        return is_active;
    }

}