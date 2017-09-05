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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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

    final String[] name = new String[1];
    final String[] email = new String[1];
    final String[] contact = new String[1];
    final String[] city = new String[1];
    final String[] isAvailable = new String[1];
    final String[] isFrequentDonor = new String[1];
    final String[] bloodGroup = new String[1];
    String savedName;
    String savedEmail;
    String savedContact;
    String savedBGroup;
    String savedCity;
    String savedAvailable;
    String savedFrequentDonor;

    TextView nameTextView;
    TextView emailTextView;
    TextView contactTextView;
    TextView bGroupTextView;
    TextView cityTextView;
    TextView availableTextView;
    TextView frequentTextView;
    EditText nameEditText;
    EditText emailEditText;
    EditText contactEditText;
    AutoCompleteTextView cityEditText;
    TextView isAvailableText;
    TextView isFrequentDonorText;
    TextView bloodGroupText;

    ActionBar customToobar;
    View view1;
    ImageView editImageView;
    ImageView finishEditingImageView;

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
        nameTextView = (TextView) getView().findViewById(R.id.donor_name);
        emailTextView = (TextView) getView().findViewById(R.id.donor_email);
        contactTextView = (TextView) getView().findViewById(R.id.donor_contact_number);
        bGroupTextView = (TextView) getView().findViewById(R.id.donor_blood_group);
        cityTextView = (TextView) getView().findViewById(R.id.donor_city);
        availableTextView = (TextView) getView().findViewById(R.id.is_available);
        frequentTextView = (TextView) getView().findViewById(R.id.is_frequent_donor);
        nameEditText = (EditText) getView().findViewById(R.id.donor_name_edittext);
        emailEditText = (EditText) getView().findViewById(R.id.donor_email_edittext);
        contactEditText = (EditText) getView().findViewById(R.id.donor_contact_number_edittext);
        cityEditText = (AutoCompleteTextView) getView().findViewById(R.id.donor_city_edittext);
        isAvailableText = (TextView) getView().findViewById(R.id.is_available);
        isFrequentDonorText = (TextView) getView().findViewById(R.id.is_frequent_donor);
        bloodGroupText = (TextView) getView().findViewById(R.id.donor_blood_group);

        cityEditText.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));
        cityEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String description = (String) adapterView.getItemAtPosition(i);
                cityEditText.setText(description);
            }
        });

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
        savedName = sharedPreferences.getString("donor_name_details", "Not yet Registered");
        savedEmail = sharedPreferences.getString("donor_email_details", "Not yet Registered");
        savedContact = sharedPreferences.getString("donor_contact_details", "Not yet Registered");
        savedBGroup = sharedPreferences.getString("donor_blood_group_details", "Not yet Registered");
        savedCity = sharedPreferences.getString("donor_city_details", "Not yet Registered");
        savedAvailable = sharedPreferences.getString("donor_availablity_details", "Not yet Registered");
        savedFrequentDonor = sharedPreferences.getString("donor_frequency_details", "Not yet Registered");
        previous_email = savedEmail;
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        nameTextView.setText(savedName);
        emailTextView.setText(savedEmail);
        contactTextView.setText(savedContact);
        bGroupTextView.setText(savedBGroup);
        cityTextView.setText(savedCity);
        availableTextView.setText(savedAvailable);
        frequentTextView.setText(savedFrequentDonor);

        customToobar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        view1 = customToobar.getCustomView();
        editImageView = view1.findViewById(R.id.edit_image_view);
        finishEditingImageView = (ImageView) view1.findViewById(R.id.finish_editing_image_view);

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

                name[0] = nameEditText.getText().toString();
                email[0] = emailEditText.getText().toString();
                contact[0] = contactEditText.getText().toString();
                city[0] = cityEditText.getText().toString();
                isAvailable[0] = isAvailableText.getText().toString();
                isFrequentDonor[0] = isFrequentDonorText.getText().toString();
                bloodGroup[0] = bloodGroupText.getText().toString();

                new AsyncRegister().execute(previous_email, bloodGroup[0], name[0], contact[0], email[0], city[0], isAvailable[0], isFrequentDonor[0]);
                //                                save registration status in SharedPreferences
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

        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("registrationStatus", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

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
                        editor.putBoolean("isRegistered", true);
                        editor.putString("donor_name_details", name[0]);
                        editor.putString("donor_email_details", email[0]);
                        editor.putString("donor_contact_details", contact[0]);
                        editor.putString("donor_blood_group_details", bloodGroup[0]);
                        editor.putString("donor_city_details", city[0]);
                        editor.putString("donor_frequency_details", isFrequentDonor[0]);
                        editor.putString("donor_availablity_details", isAvailable[0]);
                        editor.commit();

                        nameTextView.setText(name[0]);
                        emailTextView.setText(email[0]);
                        contactTextView.setText(contact[0]);
                        cityTextView.setText(city[0]);


                        nameTextView.setVisibility(View.VISIBLE);
                        emailTextView.setVisibility(View.VISIBLE);
                        contactTextView.setVisibility(View.VISIBLE);
                        cityTextView.setVisibility(View.VISIBLE);

                        TextView t = getActivity().findViewById(R.id.custom_toolbar_title);
                        t.setText("Donor Details");

                        editImageView.setVisibility(View.VISIBLE);
                        finishEditingImageView.setVisibility(View.GONE);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, new DonorDetailsFragment(), "Other Fragment").commit();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else if (s.equals("No internet")) {
                editor.putBoolean("isRegistered", false);
                editor.commit();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("");
                builder.setMessage("No Internet Connectivity.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        editor.putBoolean("isRegistered", true);
                        editor.putString("donor_name_details", name[0]);
                        editor.putString("donor_email_details", email[0]);
                        editor.putString("donor_contact_details", contact[0]);
                        editor.putString("donor_blood_group_details", bloodGroup[0]);
                        editor.putString("donor_city_details", city[0]);
                        editor.putString("donor_frequency_details", isFrequentDonor[0]);
                        editor.putString("donor_availablity_details", isAvailable[0]);
                        registerProgressDialog.setCanceledOnTouchOutside(false);
                        registerProgressDialog.hide();
//                        Fragment f = new DonorDetailsFragment();
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, f, "Main Fragment").commit();
//                        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
//                        navigationView.setCheckedItem(R.id.nav_search);
                        nameTextView.setText(savedName);
                        emailTextView.setText(savedEmail);
                        contactTextView.setText(savedContact);
                        cityTextView.setText(savedCity);


                        nameTextView.setVisibility(View.VISIBLE);
                        emailTextView.setVisibility(View.VISIBLE);
                        contactTextView.setVisibility(View.VISIBLE);
                        cityTextView.setVisibility(View.VISIBLE);

                        TextView t = getActivity().findViewById(R.id.custom_toolbar_title);
                        t.setText("Donor Details");

                        editImageView.setVisibility(View.VISIBLE);
                        finishEditingImageView.setVisibility(View.GONE);
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
                Log.d(">>>>", "doInBackground: " + uri);
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

