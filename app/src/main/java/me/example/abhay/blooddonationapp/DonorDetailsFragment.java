package me.example.abhay.blooddonationapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by abhay on 20/7/17.
 */

public class DonorDetailsFragment extends Fragment {
    public DonorDetailsFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.donor_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Donor Details");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("registrationStatus", MODE_PRIVATE);
        String name = sharedPreferences.getString("donor_name_details", "Not yet Registered");
        String email = sharedPreferences.getString("donor_email_details", "Not yet Registered");
        String contact = sharedPreferences.getString("donor_contact_details", "Not yet Registered");
        String bGroup = sharedPreferences.getString("donor_blood_group_details", "Not yet Registered");
        String city = sharedPreferences.getString("donor_city_details", "Not yet Registered");
        String available = sharedPreferences.getString("donor_availablity_details", "Not yet Registered");
        String frequentDonor = sharedPreferences.getString("donor_frequency_details", "Not yet Registered");

        TextView nameTextView = (TextView) getView().findViewById(R.id.donor_name);
        TextView emailTextView = (TextView) getView().findViewById(R.id.donor_email);
        TextView contactTextView = (TextView) getView().findViewById(R.id.donor_contact_number);
        TextView bGroupTextView = (TextView) getView().findViewById(R.id.donor_blood_group);
        TextView cityTextView = (TextView) getView().findViewById(R.id.donor_city);
        TextView availableTextView = (TextView) getView().findViewById(R.id.is_available);
        TextView frequentTextView = (TextView) getView().findViewById(R.id.is_frequent_donor);

        nameTextView.setText(name);
        emailTextView.setText(email);
        contactTextView.setText(contact);
        bGroupTextView.setText(bGroup);
        cityTextView.setText(city);
        availableTextView.setText(available);
        frequentTextView.setText(frequentDonor);
    }
}
