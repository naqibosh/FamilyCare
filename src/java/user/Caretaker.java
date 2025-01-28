
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
    private String availabilityStatus;
    private String profileDescription;
    private String IC;
    private String banDate;
    private int staffId;
    private int statusId;
    
    //constructors
    public Caretaker() {}

    public Caretaker(int caretakerId, String name, String phone, String availabilityStatus, String profileDescription, String IC, String banDate, int staffId, int statusId) {
        this.caretakerId = caretakerId;
        this.name = name;
        this.phone = phone;
        this.availabilityStatus = availabilityStatus;
        this.profileDescription = profileDescription;
        this.IC = IC;
        this.banDate = banDate;
        this.staffId = staffId;
        this.statusId = statusId;
    }
    //for new caretaker
    public Caretaker(String name, String phone, String profileDescription, String IC, int staffId) {
        this.name = name;
        this.phone = phone;
        this.profileDescription = profileDescription;
        this.IC = IC;
        this.staffId = staffId;
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
    
    //additional method
    @Override
    public String toString() {
        return "Caretaker{" + "caretakerId=" + caretakerId + ", name=" + name + ", phone=" + phone + ", availabilityStatus=" + availabilityStatus + ", profileDescription=" + profileDescription + ", IC=" + IC + ", banDate=" + banDate + ", staffId=" + staffId + ", statusId=" + statusId + '}';
    }
   
}
