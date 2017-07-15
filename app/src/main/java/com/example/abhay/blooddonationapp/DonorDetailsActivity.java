package com.example.abhay.blooddonationapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by abhay on 11/7/17.
 */

public class DonorDetailsActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_details);

        SharedPreferences sharedPreferences = this.getSharedPreferences("registrationStatus", MODE_PRIVATE);
        String name = sharedPreferences.getString("donor_name_details", "Not yet Registered");
        String email = sharedPreferences.getString("donor_email_details", "Not yet Registered");
        String contact = sharedPreferences.getString("donor_contact_details", "Not yet Registered");
        String bGroup = sharedPreferences.getString("donor_blood_group_details", "Not yet Registered");
        String city = sharedPreferences.getString("donor_city_details", "Not yet Registered");
        String available = sharedPreferences.getString("donor_availablity_details", "Not yet Registered");
        String frequentDonor = sharedPreferences.getString("donor_frequency_details", "Not yet Registered");

        TextView nameTextView = (TextView) findViewById(R.id.donor_name);
        TextView emailTextView = (TextView) findViewById(R.id.donor_email);
        TextView contactTextView = (TextView) findViewById(R.id.donor_contact_number);
        TextView bGroupTextView = (TextView) findViewById(R.id.donor_blood_group);
        TextView cityTextView = (TextView) findViewById(R.id.donor_city);
        TextView availableTextView = (TextView) findViewById(R.id.is_available);
        TextView frequentTextView = (TextView) findViewById(R.id.is_frequent_donor);

        nameTextView.setText(name);
        emailTextView.setText(email);
        contactTextView.setText(contact);
        bGroupTextView.setText(bGroup);
        cityTextView.setText(city);
        availableTextView.setText(available);
        frequentTextView.setText(frequentDonor);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(i);
    }
}
