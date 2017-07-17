package com.example.abhay.blooddonationapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by abhay on 13/7/17.
 */

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DonorViewHolder> {
    private List<Donor> donorList;
    Intent i = new Intent(Intent.ACTION_DIAL);
    public DonorAdapter(List<Donor> donorList) {
        this.donorList = donorList;
    }

    @Override
    public DonorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_details_row, parent, false);
        return new DonorViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final DonorViewHolder holder, int position) {
        final Donor donor = donorList.get(position);
        holder.nameOfDonor.setText(donor.getName());
        holder.emailOfDonor.setText(donor.getEmail());
        holder.contactOfDonor.setText(donor.getContact());
        holder.cityOfDonor.setText(donor.getCity());
        holder.frequencyStatus.setText(donor.getIsFDonor());
        holder.availabilityStatus.setText(donor.getIsAvailable());
        holder.callImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+donor.getContact()));
                view.getContext().startActivity(i);
            }
        });
        holder.mailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"+donor.getEmail()));
                view.getContext().startActivity(i);
            }
        });
        holder.shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File vcfFile = new File(view.getContext().getExternalFilesDir(null), "generated.vcf");
                try {
                    FileOutputStream fo = new FileOutputStream(vcfFile);
                    String str = "BEGIN:VCARD\n"+
                                 "VERSION:3.0\n"+
                                 "FN:"+donor.getName()+"\n"+
                                 "TITLE:Donor\n"+
                                 "WORK:"+donor.getContact()+"\n"+
                                 "EMAIL;TYPE=PREF,INTERNET:"+donor.getEmail()+"\n"+
                                 "END:VCARD\n";
                    Log.d(">>>>", "onClick: "+str);
                    fo.write(str.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_SEND);
                    i.setDataAndType(Uri.fromFile(vcfFile), "text/x-vcard");
                    view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public class DonorViewHolder extends RecyclerView.ViewHolder {
        public TextView nameOfDonor, cityOfDonor, emailOfDonor, contactOfDonor, availabilityStatus, frequencyStatus;
        public ImageView callImageView, mailImageView, shareImageView;

        public DonorViewHolder(View view) {
            super(view);
            nameOfDonor = (TextView) view.findViewById(R.id.search_result_name_details);
            cityOfDonor = (TextView) view.findViewById(R.id.search_result_city_details);
            contactOfDonor = (TextView) view.findViewById(R.id.search_result_contact_details);
            emailOfDonor = (TextView) view.findViewById(R.id.search_result_email_details);
            availabilityStatus = (TextView) view.findViewById(R.id.is_available);
            frequencyStatus = (TextView) view.findViewById(R.id.is_frequent_donor);
            callImageView = (ImageView) view.findViewById(R.id.call_donor_imageview);
            mailImageView = (ImageView) view.findViewById(R.id.email_donor_imageview);
            shareImageView = (ImageView) view.findViewById(R.id.share_contact_imageview);
        }

        public String getContact(){
            return contactOfDonor.getText().toString();
        }
    }

    public class CustomOnClickListener implements View.OnClickListener {
        private String data;

        public CustomOnClickListener(String data){
            this.data = data;
        }

        @Override
        public void onClick(View view) {
            i.setData(Uri.parse(data));
            view.getContext().startActivity(i);
        }
    }
}

