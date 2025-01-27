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
    private String statusId;
    
    //constructors
    public Customer(){}

    public Customer(int custId, String custUsername, String custPass, String custFName, String custLName, String custPhone, String custEmail, String custIC, String banDate, String statusId) {
        this.custId = custId;
        this.custUsername = custUsername;
        this.custPass = custPass;
        this.custFName = custFName;
        this.custLName = custLName;
        this.custPhone = custPhone;
        this.custEmail = custEmail;
        this.custIC = custIC;
        this.banDate = banDate;
        this.statusId = statusId;
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

    public void setStatusId(String statusId) {
        this.statusId = statusId;
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

    public String getStatusId() {
        return statusId;
    }
    
    //additional method

}
