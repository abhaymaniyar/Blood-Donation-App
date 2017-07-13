package com.example.abhay.blooddonationapp;

/**
 * Created by abhay on 13/7/17.
 */

public class Donor {
    private String name;
    private String contact;
    private String email;
    private String city;

    public Donor(String name, String city, String contact, String email){
        this.name = name;
        this.city = city;
        this.contact = contact;
        this.email = email;
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
}