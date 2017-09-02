package me.abhaymaniyar.blooddonorsunited;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by abhay on 20/7/17.
 */

public class DonorDetailsFragment extends Fragment {
    ProgressDialog registerProgressDialog;
    boolean isConnected = false;
    String previous_email = null;
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
        TextView textView = (TextView) v.findViewById(R.id.custom_toolbar_title);
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
        final String frequentDonor = sharedPreferences.getString("donor_frequency_details", "Not yet Registered");
        previous_email = email;
        final SharedPreferences.Editor editor = sharedPreferences.edit();

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
        final TextView isAvailableText = (TextView) getView().findViewById(R.id.is_available);
        final TextView isFrequentDonorText = (TextView) getView().findViewById(R.id.is_frequent_donor);
        final TextView bloodGroupText = (TextView) getView().findViewById(R.id.donor_blood_group);

        nameTextView.setText(name);
        emailTextView.setText(email);
        contactTextView.setText(contact);
        bGroupTextView.setText(bGroup);
        cityTextView.setText(city);
        availableTextView.setText(available);
        frequentTextView.setText(frequentDonor);

        ActionBar customToobar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        final View view1 = customToobar.getCustomView();
        final ImageView editImageView = view1.findViewById(R.id.edit_image_view);
        final ImageView finishEditingImageView = (ImageView) view1.findViewById(R.id.finish_editing_image_view);

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (i==0) {
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

                TextView t = view1.findViewById(R.id.custom_toolbar_title);
                t.setText("Update Details");
                editImageView.setVisibility(View.GONE);
                finishEditingImageView.setVisibility(View.VISIBLE);
            }
        });

        finishEditingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEditText.setVisibility(View.GONE);
                emailEditText.setVisibility(View.GONE);
                contactEditText.setVisibility(View.GONE);
                cityEditText.setVisibility(View.GONE);

                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String contact = contactEditText.getText().toString();
                String city = cityEditText.getText().toString();
                String isAvailable = isAvailableText.getText().toString();
                String isFrequentDonor = isFrequentDonorText.getText().toString();
                String bloodGroup = bloodGroupText.getText().toString();

                new AsyncRegister().execute(previous_email, bloodGroup, name, contact, email, city, isAvailable, isFrequentDonor);
                //                                save registration status in SharedPreferences
                editor.putBoolean("isRegistered", true);
                editor.putString("donor_name_details", name);
                editor.putString("donor_email_details", email);
                editor.putString("donor_contact_details", contact);
                editor.putString("donor_blood_group_details", bloodGroup);
                editor.putString("donor_city_details", city);
                editor.putString("donor_frequency_details", isFrequentDonor);
                editor.putString("donor_availablity_details", isAvailable);
                editor.commit();

                nameTextView.setText(name);
                emailTextView.setText(email);
                contactTextView.setText(contact);
                cityTextView.setText(city);


                nameTextView.setVisibility(View.VISIBLE);
                emailTextView.setVisibility(View.VISIBLE);
                contactTextView.setVisibility(View.VISIBLE);
                cityTextView.setVisibility(View.VISIBLE);

                TextView t = view1.findViewById(R.id.custom_toolbar_title);
                t.setText("Donor Details");

                editImageView.setVisibility(View.VISIBLE);
                finishEditingImageView.setVisibility(View.GONE);
            }
        });

        registerProgressDialog = new ProgressDialog(getContext());
        registerProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        registerProgressDialog.setIndeterminate(true);
        registerProgressDialog.setCanceledOnTouchOutside(false);
        registerProgressDialog.setMessage("Updating Details...");
    }

    private class AsyncRegister extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            registerProgressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("true")) {
//                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Success!");
                builder.setMessage("Donor Details Updated");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, new DonorDetailsFragment(), "Other Fragment").commit();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else if (s.equals("No internet")) {
                final SharedPreferences sharedPreferences = getContext().getSharedPreferences("registrationStatus", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isRegistered", false);
                editor.commit();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("");
                builder.setMessage("No Internet Connectivity.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        registerProgressDialog.setCanceledOnTouchOutside(false);
                        registerProgressDialog.hide();
//                        Fragment f = new DonorDetailsFragment();
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, f, "Main Fragment").commit();
//                        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
//                        navigationView.setCheckedItem(R.id.nav_search);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            super.onPostExecute(s);
            registerProgressDialog.hide();
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                InetAddress inetAddress = InetAddress.getByName("google.com");
                isConnected = !inetAddress.equals("");
            } catch (UnknownHostException e) {
                return "No internet";
            }
            try {
                HttpURLConnection conn = null;
                String uri = Uri.parse("http://ngoindex.info/updateInfo.php").buildUpon().appendQueryParameter("previous_email", params[0])
                        .appendQueryParameter("bGroup", params[1])
                        .appendQueryParameter("name", params[2])
                        .appendQueryParameter("contact", params[3])
                        .appendQueryParameter("email", params[4])
                        .appendQueryParameter("city", params[5])
                        .appendQueryParameter("isavailable", params[6])
                        .appendQueryParameter("frequentdonor", params[7]).build().toString();
                Log.d(">>>>", "doInBackground: "+uri);
                url = new URL(uri);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(0);


                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                writer.write(String.valueOf(url));
                writer.flush();
                writer.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception";
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception";
            }
            return "true";
        }
    }
}

