package me.abhaymaniyar.blooddonorsunited;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
        LayoutInflater l = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = l.inflate(R.layout.tool_bar, null);
        TextView textView = (TextView) v.findViewById(R.id.title);
        textView.setText("Donor Details");
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);
        //        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        //        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("registrationStatus", MODE_PRIVATE);
        String name = sharedPreferences.getString("donor_name_details", "Not yet Registered");
        String email = sharedPreferences.getString("donor_email_details", "Not yet Registered");
        String contact = sharedPreferences.getString("donor_contact_details", "Not yet Registered");
        String bGroup = sharedPreferences.getString("donor_blood_group_details", "Not yet Registered");
        String city = sharedPreferences.getString("donor_city_details", "Not yet Registered");
        String available = sharedPreferences.getString("donor_availablity_details", "Not yet Registered");
        String frequentDonor = sharedPreferences.getString("donor_frequency_details", "Not yet Registered");

        final TextView nameTextView = (TextView) getView().findViewById(R.id.donor_name);
        final TextView emailTextView = (TextView) getView().findViewById(R.id.donor_email);
        final TextView contactTextView = (TextView) getView().findViewById(R.id.donor_contact_number);
        TextView bGroupTextView = (TextView) getView().findViewById(R.id.donor_blood_group);
        final TextView cityTextView = (TextView) getView().findViewById(R.id.donor_city);
        TextView availableTextView = (TextView) getView().findViewById(R.id.is_available);
        TextView frequentTextView = (TextView) getView().findViewById(R.id.is_frequent_donor);
        final EditText nameEditText = (EditText) getView().findViewById(R.id.donor_name_edittext);
        final EditText emailEditText = (EditText) getView().findViewById(R.id.donor_email_edittext);
        final EditText contactEditText = (EditText) getView().findViewById(R.id.donor_contact_number_edittext);
        final EditText cityEditText = (EditText) getView().findViewById(R.id.donor_city_edittext);

        nameTextView.setText(name);
        emailTextView.setText(email);
        contactTextView.setText(contact);
        bGroupTextView.setText(bGroup);
        cityTextView.setText(city);
        availableTextView.setText(available);
        frequentTextView.setText(frequentDonor);

        final Button updateBtn = (Button) getView().findViewById(R.id.update_information);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateBtn.getText().toString().toLowerCase().equals("update information")) {
                    nameEditText.setVisibility(View.VISIBLE);
                    emailEditText.setVisibility(View.VISIBLE);
                    contactEditText.setVisibility(View.VISIBLE);
                    cityEditText.setVisibility(View.VISIBLE);

                    nameEditText.setText(nameTextView.getText().toString());
                    emailEditText.setText(emailTextView.getText().toString());
                    contactEditText.setText(contactTextView.getText().toString());
                    cityEditText.setText(cityTextView.getText().toString());

                    nameTextView.setVisibility(View.GONE);
                    emailTextView.setVisibility(View.GONE);
                    contactTextView.setVisibility(View.GONE);
                    cityTextView.setVisibility(View.GONE);

                    updateBtn.setText("Done");
                    getActivity().setTitle("Update Info");
                } else {
                    nameEditText.setVisibility(View.GONE);
                    emailEditText.setVisibility(View.GONE);
                    contactEditText.setVisibility(View.GONE);
                    cityEditText.setVisibility(View.GONE);

                    String name = nameEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String contact = contactEditText.getText().toString();
                    String city = cityEditText.getText().toString();

                    nameTextView.setText(name);
                    emailTextView.setText(email);
                    contactTextView.setText(contact);
                    cityTextView.setText(city);

                    nameTextView.setVisibility(View.VISIBLE);
                    emailTextView.setVisibility(View.VISIBLE);
                    contactTextView.setVisibility(View.VISIBLE);
                    cityTextView.setVisibility(View.VISIBLE);

                    updateBtn.setText("Update Information");
                    getActivity().setTitle("Donor Details");
                }
            }
        });

    }


}
