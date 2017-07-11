package com.example.abhay.blooddonationapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

        spinner.setPrompt("Select Blood Group");
        List<String> bloodGroups = new ArrayList<String>();
        bloodGroups.add("A+");
        bloodGroups.add("B+");
        bloodGroups.add("O+");
        bloodGroups.add("B-");
        bloodGroups.add("AB-");
        bloodGroups.add("O-");

//        set Adapter to Spinner items
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroups);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(dataAdapter);

//        select a bloodGroup and show it on spinner
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

        SharedPreferences sharedPreferences = this.getSharedPreferences("registrationStatus", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameTextView = (EditText) findViewById(R.id.donorName);
                final String donorName = nameTextView.getText().toString();
                contactTextView = (EditText) findViewById(R.id.donorContact);
                final String donorContact = contactTextView.getText().toString();
                emailTextView = (EditText) findViewById(R.id.donorEmail);
                final String donorEmail = emailTextView.getText().toString();
                bloodGroupSpinner = (Spinner) findViewById(R.id.spinner);
                final String bloodGroup = bloodGroupSpinner.getPrompt().toString();
                boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(donorEmail).matches();
                boolean isContactValid = Patterns.PHONE.matcher(donorContact).matches();

//               Check for User details validation and integrity
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                if(!(donorName.equals("") || donorEmail.equals("") || donorContact.equals(""))){
                    if (!isEmailValid){
                        validateInput((EditText) emailTextView, "Email Address");
                    }else if (!isContactValid){
                        validateInput((EditText) contactTextView, "Contact Number");
                    }else{
                        builder.setTitle("");
                        builder.setMessage("Are you sure the details are correct?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new AsyncRegister().execute(bloodGroup, donorName, donorContact, donorEmail);
//                                save registration status in SharedPreferences
                                editor.putBoolean("isRegistered", true);
                                editor.putString("donor_name_details", donorName);
                                editor.putString("donor_email_details", donorEmail);
                                editor.putString("donor_contact_details", donorContact);
                                editor.putString("donor_blood_group_details", bloodGroup);
                                editor.commit();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }else{
                    builder.setTitle("");
                    builder.setMessage("Please fill all the details.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

//    Async task for network call and saving registration status
    private class AsyncRegister extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("true")) {
                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
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

    // Dialog to validate contact and email entries by the user
    private void validateInput(final EditText view, String str){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("");
        builder.setMessage("Please Enter Valid "+str+".");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                view.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

