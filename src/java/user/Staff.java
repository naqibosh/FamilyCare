/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

/**
 *
 * @author hazik
 */
public class Staff {

    private int id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String role;
    private int supervisorId;
    private String supervisorName;
    private String isActive;

    // Default Constructor
    public Staff() {}

    //for update
    public Staff(int id, String name, String password, String email, String phoneNumber, String role, int supervisorId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.supervisorId = supervisorId;
    }

    //for register
    public Staff(String name, String password, String email, String phoneNumber, String role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    //for get staff with sv name

    public Staff(int id, String name, String email, String phoneNumber, String role, int supervisorId, String supervisorName, String isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.supervisorId = supervisorId;
        this.supervisorName = supervisorName;
        this.isActive = isActive;
    }

    //setter
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    //getter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public String getIsActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "Staff{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + ", phoneNumber='" + phoneNumber + '\''
                + ", role='" + role + '\''
                + ", supervisorId=" + supervisorId
                + ", supervisorName='" + supervisorName + '\''
                + '}';
    }

}
