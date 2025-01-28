
package user;

/**
 *
 * @author hazik
 */
public class CaretakerBabysitter extends Caretaker{
    private int experienceYears;
    private double rating = 0.00;
    private double hourlyRate = 0.00;
    
    //constructors
    public CaretakerBabysitter() {}

    public CaretakerBabysitter(int experienceYears, String name, String phone, String password, String profileDescription, String IC, int staffId, double hourlyRate, double rating) {
        super(name, phone, password, profileDescription, IC, staffId);
        this.experienceYears = experienceYears;
        this.hourlyRate = hourlyRate;
        this.rating = rating;
    }
    
    //setter
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    
    //getter
    public int getExperienceYears() {
        return experienceYears;
    }

    public double getRating() {
        return rating;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    @Override
    public String toString() {
        return "CaretakerBabysitter{" + "experienceYears=" + experienceYears + ", rating=" + rating + ", hourlyRate=" + hourlyRate + '}';
    }
    
}
