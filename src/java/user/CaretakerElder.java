
package user;

import java.io.InputStream;

/**
 *
 * @author hazik
 */
public class CaretakerElder extends Caretaker{
    //data members
    private int experienceYears;
    private String certification;
    private double hourlyRate = 0.00;
    private InputStream certificationStream;
    
    //constructors
    public CaretakerElder() {}
    
    public CaretakerElder(int experienceYears, String certification) {
        this.experienceYears = experienceYears;
        this.certification = certification;
    }
    
    //setter
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setCertificationStream(InputStream certificationStream) {
        this.certificationStream = certificationStream;
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

    public InputStream getCertificationStream() {
        return certificationStream;
    }

    @Override
    public String toString() {
        return "CaretakerElder{" + "experienceYears=" + experienceYears + ", certification=" + certification + ", hourlyRate=" + hourlyRate + '}';
    }
    
}
