package me.example.abhay.blooddonationapp;

/**
 * Created by abhay on 13/7/17.
 */

public class Donor {
    private String name;
    private String contact;
    private String email;
    private String city;
    private String isAvailable;

    private String isFDonor;

    public Donor(String name, String city, String contact, String email, String isAvailable, String isFDonor){
        this.name = name;
        this.city = city;
        this.contact = contact;
        this.email = email;
        this.isAvailable = isAvailable;
        this.isFDonor = isFDonor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getIsFDonor() {
        return isFDonor;
    }

    public void setIsFDonor(String isFDonor) {
        this.isFDonor = isFDonor;
    }
}