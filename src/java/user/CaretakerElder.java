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
public class CaretakerElder extends Caretaker{
    //data members
    private int experienceYears;
    private String certification;
    private double hourlyRate = 0.00;
    
    //constructors
    public CaretakerElder() {}
    
//    public CaretakerElder(int experienceYears, String certification, double hourlyRate, String name, String phone, String profileDescription, String IC, int staffId) {
//        super(name, phone, profileDescription, IC, staffId);
//        this.experienceYears = experienceYears;
//        this.certification = certification;
//        this.hourlyRate = hourlyRate;
//    }
    
    //setter
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    
    //getter
    public int getExperienceYears() {
        return experienceYears;
    }

    public String getCertification() {
        return certification;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }   

    @Override
    public String toString() {
        return "CaretakerElder{" + "experienceYears=" + experienceYears + ", certification=" + certification + ", hourlyRate=" + hourlyRate + '}';
    }
    
}
