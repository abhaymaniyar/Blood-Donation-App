package com.example.abhay.blooddonationapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhay on 13/7/17.
 */

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DonorViewHolder> {
    private List<Donor> donorList;
    public class DonorViewHolder extends RecyclerView.ViewHolder{
        public TextView nameOfDonor, cityOfDonor, emailOfDonor, contactOfDonor, availabilityStatus, frequencyStatus;

        public DonorViewHolder(View view){
            super(view);
            nameOfDonor = (TextView) view.findViewById(R.id.search_result_name_details);
            cityOfDonor = (TextView) view.findViewById(R.id.search_result_city_details);
            contactOfDonor = (TextView) view.findViewById(R.id.search_result_contact_details);
            emailOfDonor = (TextView) view.findViewById(R.id.search_result_email_details);
            availabilityStatus = (TextView) view.findViewById(R.id.is_available);
            frequencyStatus = (TextView) view.findViewById(R.id.is_frequent_donor);
        }
    }

    public DonorAdapter(List<Donor> donorList){
        this.donorList = donorList;
    }

    @Override
    public DonorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_details_row, parent, false);
        return new DonorViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(DonorViewHolder holder, int position) {
        Donor donor = donorList.get(position);
        holder.nameOfDonor.setText(donor.getName());
        holder.emailOfDonor.setText(donor.getEmail());
        holder.contactOfDonor.setText(donor.getContact());
        holder.cityOfDonor.setText(donor.getCity());
        holder.frequencyStatus.setText(donor.getIsFDonor());
        holder.availabilityStatus.setText(donor.getIsAvailable());
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }
}
