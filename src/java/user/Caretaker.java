
package user;

/**
 *
 * @author hazik
 */
public class Caretaker {
    //data members
    private int caretakerId;
    private String name;
    private String phone;
    private String password;
    private String availabilityStatus;
    private String profileDescription;
    private String IC;
    private String banDate;
    private String status;
    private String is_active;
    private int staffId;
    private int statusId;
    
    //constructors
    public Caretaker() {}
    //for insert
    public Caretaker(String name, String phone, String password, String profileDescription, String IC, int staffId) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.profileDescription = profileDescription;
        this.IC = IC;
        this.staffId = staffId;
    }

    //for list
    public Caretaker(int caretakerId, String name, String phone, String availabilityStatus, String IC, String banDate, String status, int staffId, int statusId, String is_active) {
        this.caretakerId = caretakerId;
        this.name = name;
        this.phone = phone;
        this.availabilityStatus = availabilityStatus;
        this.IC = IC;
        this.banDate = banDate;
        this.status = status;
        this.staffId = staffId;
        this.statusId = statusId;
        this.is_active = is_active;
    }
    
    //for update caretaker
    public Caretaker(int caretakerId, String name, String phone, String password, String availabilityStatus, String profileDescription, String IC, String status) {
        this.caretakerId = caretakerId;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.availabilityStatus = availabilityStatus;
        this.profileDescription = profileDescription;
        this.IC = IC;
        this.status = status;
    }
    
    
    //setter
    public void setCaretakerId(int caretakerId) {
        this.caretakerId = caretakerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setIC(String IC) {
        this.IC = IC;
    }

    public void setBanDate(String banDate) {
        this.banDate = banDate;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
    
    //getter
    public int getCaretakerId() {
        return caretakerId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public String getIC() {
        return IC;
    }

    public String getBanDate() {
        return banDate;
    }

    public int getStaffId() {
        return staffId;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getIs_active() {
        return is_active;
    }
    
    //additional method
    @Override
    public String toString() {
        return "Caretaker{" + "caretakerId=" + caretakerId + ", name=" + name + ", phone=" + phone + ", availabilityStatus=" + availabilityStatus + ", profileDescription=" + profileDescription + ", IC=" + IC + ", banDate=" + banDate + ", staffId=" + staffId + ", statusId=" + statusId + '}';
    }
   
}
