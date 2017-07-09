package com.example.abhay.blooddonationapp;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhay on 6/7/17.
 */

public class RegisterActivity extends Activity {
    TextView nameTextView;
    TextView contactTextView;
    TextView emailTextView;
    Spinner bloodGroupSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_registration);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                spinner.setPrompt(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> bloodGroups = new ArrayList<String>();
        bloodGroups.add("A+");
        bloodGroups.add("B+");
        bloodGroups.add("O+");
        bloodGroups.add("B-");
        bloodGroups.add("AB-");
        bloodGroups.add("O-");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroups);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);


        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameTextView = (TextView) findViewById(R.id.donorName);
                String donorName = nameTextView.getText().toString();
                contactTextView = (TextView) findViewById(R.id.donorContact);
                String donorContact = contactTextView.getText().toString();
                emailTextView = (TextView) findViewById(R.id.donorEmail);
                String donorEmail = emailTextView.getText().toString();
                bloodGroupSpinner = (Spinner) findViewById(R.id.spinner);
                String bloodGroup = bloodGroupSpinner.getPrompt().toString();
                Log.d(">>>>", "onClick: " + bloodGroup + " " + donorName + " " + donorEmail + " " + donorContact);
                if(!(donorName.equals("") || donorEmail.equals("") || donorContact.equals(""))){
                    new AsyncRegister().execute(bloodGroup, donorName, donorContact, donorEmail);
                }else{
                    Toast.makeText(RegisterActivity.this, "Fill the details please.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class AsyncRegister extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("true")) {
                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                HttpURLConnection conn = null;
                String uri = Uri.parse("http://ngoindex.info/donor_register.php").buildUpon().appendQueryParameter("bGroup", params[0])
                        .appendQueryParameter("name", params[1])
                        .appendQueryParameter("contact", params[2])
                        .appendQueryParameter("email", params[3]).build().toString();
                url = new URL(uri);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(0);

                Log.d(">>>>", "doInBackground: " + uri);

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                writer.write(String.valueOf(url));
                writer.flush();
                writer.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception";
            } catch (IOException e) {
                Log.d(">>>>", "doInBackground:2 " + e);
                e.printStackTrace();
                return "Exception";
            } catch (Exception e) {
                e.printStackTrace();
                return "E";
            }
            return "true";
        }
    }
}
