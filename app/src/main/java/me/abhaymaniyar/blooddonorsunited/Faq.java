package me.abhaymaniyar.blooddonorsunited;

/**
 * Created by abhay on 18/7/17.
 */

public class Faq {
    private String heading;
    private String details;

    public Faq(String heading, String details) {
        this.heading = heading;
        this.details = details;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
