/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.bean;

/**
 *
 * @author hazik
 */
public class Staff {
    private int staffID;
    private String staffName;
    private String staffPassword;
    private String staffEmail;
    private String staffPhoneNumber;
    private String staffRole;
    private int supervisorID;

    // Constructor
    public Staff(int staffID, String staffName, String staffPassword, String staffEmail, String staffPhoneNumber, String staffRole, int supervisorID) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.staffPassword = staffPassword;
        this.staffEmail = staffEmail;
        this.staffPhoneNumber = staffPhoneNumber;
        this.staffRole = staffRole;
        this.supervisorID = supervisorID;
    }

    // Getters and Setters
    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getStaffPhoneNumber() {
        return staffPhoneNumber;
    }

    public void setStaffPhoneNumber(String staffPhoneNumber) {
        this.staffPhoneNumber = staffPhoneNumber;
    }

    public String getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    public int getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(int supervisorID) {
        this.supervisorID = supervisorID;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffID=" + staffID +
                ", staffName='" + staffName + '\'' +
                ", staffPassword='" + staffPassword + '\'' +
                ", staffEmail='" + staffEmail + '\'' +
                ", staffPhoneNumber='" + staffPhoneNumber + '\'' +
                ", staffRole='" + staffRole + '\'' +
                ", supervisorID=" + supervisorID +
                '}';
    }
}

