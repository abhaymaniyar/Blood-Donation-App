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
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhay on 20/7/17.
 */

public class RegisterFragment extends android.support.v4.app.Fragment {
    TextView nameTextView;
    TextView contactTextView;
    TextView emailTextView;
    Spinner bloodGroupSpinner;
    ProgressDialog registerProgressDialog;
    Fragment donorDetialsFragment = new DonorDetailsFragment();
    FragmentManager fragmentManager;
    boolean isConnected = false;

    public RegisterFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.donor_registration_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Spinner spinner = (Spinner) getView().findViewById(R.id.spinner);
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("registrationStatus", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        spinner.setPrompt("Select Blood Group");
        List<String> bloodGroups = new ArrayList<String>();
        bloodGroups.add("O+");
        bloodGroups.add("O-");
        bloodGroups.add("A+");
        bloodGroups.add("A-");
        bloodGroups.add("B+");
        bloodGroups.add("B-");
        bloodGroups.add("AB-");
        bloodGroups.add("AB+");

//        set Adapter to Spinner items
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bloodGroups);
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

        final Spinner frequentDonor = (Spinner) getView().findViewById(R.id.frequent_donor_spinner);
        final Spinner emergencyDonor = (Spinner) getView().findViewById(R.id.emergency_spinner);
        ArrayList<String> yesNoSpinnerItems = new ArrayList<>();
        yesNoSpinnerItems.add("Yes");
        yesNoSpinnerItems.add("No");
        ArrayAdapter<String> yesNoAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, yesNoSpinnerItems);
        yesNoAdapter.setDropDownViewResource(R.layout.spinner_item);
        frequentDonor.setAdapter(yesNoAdapter);
        emergencyDonor.setAdapter(yesNoAdapter);
        frequentDonor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                frequentDonor.setPrompt(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        emergencyDonor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                emergencyDonor.setPrompt(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        LinearLayout registerButton = (LinearLayout) getView().findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameTextView = (EditText) getView().findViewById(R.id.donorName);
                final String donorName = nameTextView.getText().toString();
                contactTextView = (EditText) getView().findViewById(R.id.donorContact);
                final String donorContact = contactTextView.getText().toString();
                emailTextView = (EditText) getView().findViewById(R.id.donorEmail);
                final String donorEmail = emailTextView.getText().toString();
                bloodGroupSpinner = (Spinner) getView().findViewById(R.id.spinner);
                final String bloodGroup = bloodGroupSpinner.getPrompt().toString();
                EditText cityView = (EditText) getView().findViewById(R.id.donorCity);
                final String donorCity = cityView.getText().toString();
                boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(donorEmail).matches();
                boolean isContactValid = Patterns.PHONE.matcher(donorContact).matches();
                int contactNumberLength = donorContact.length();
                final String isFrequentDonor = frequentDonor.getPrompt().toString();
                final String isAvaiable = emergencyDonor.getPrompt().toString();

//               Check for User details validation and integrity
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                if (!(donorName.equals("") || donorEmail.equals("") || donorContact.equals("") || donorCity.equals(""))) {
                    if (!isEmailValid) {
                        validateInput((EditText) emailTextView, "Email Address");
                    } else if (!isContactValid || donorContact.length() != 10) {
                        validateInput((EditText) contactTextView, "Contact Number");
                    } else if (contactNumberLength < 10) {
                        validateInput((EditText) contactTextView, "Contact Number");
                    } else {
                        builder.setTitle("");
                        builder.setMessage("Are you sure the details are correct?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

//                                execute background registration thread
                                new AsyncRegister().execute(bloodGroup, donorName, donorContact, donorEmail, donorCity, isAvaiable, isFrequentDonor);

//                                save registration status in SharedPreferences
                                editor.putBoolean("isRegistered", true);
                                editor.putString("donor_name_details", donorName);
                                editor.putString("donor_email_details", donorEmail);
                                editor.putString("donor_contact_details", donorContact);
                                editor.putString("donor_blood_group_details", bloodGroup);
                                editor.putString("donor_city_details", donorCity);
                                editor.putString("donor_frequency_details", isFrequentDonor);
                                editor.putString("donor_availablity_details", isAvaiable);
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
                } else {
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

        registerProgressDialog = new ProgressDialog(getContext());
        registerProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        registerProgressDialog.setIndeterminate(true);
        registerProgressDialog.setCanceledOnTouchOutside(false);
        registerProgressDialog.setMessage("Registering Donor...");
    }

    // Dialog to validate contact and email entries by the user
    private void validateInput(final EditText view, String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("");
        builder.setMessage("Please Enter Valid " + str + ".");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                view.requestFocus();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //    Async task for network call and saving registration status
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
                builder.setMessage("Donor Registration Successful");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, new DonorDetailsFragment(), "Other Fragment").commit();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else if(s.equals("No internet")){
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
                        Fragment f = new MainFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, f, "Main Fragment").commit();
                        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                        navigationView.setCheckedItem(R.id.nav_search);
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
                String uri = Uri.parse("http://ngoindex.info/donor_register.php").buildUpon().appendQueryParameter("bGroup", params[0])
                        .appendQueryParameter("name", params[1])
                        .appendQueryParameter("contact", params[2])
                        .appendQueryParameter("email", params[3])
                        .appendQueryParameter("city", params[4])
                        .appendQueryParameter("isavailable", params[5])
                        .appendQueryParameter("frequentdonor", params[6]).build().toString();
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
