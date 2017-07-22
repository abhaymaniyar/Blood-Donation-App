package com.example.abhay.blooddonationapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by abhay on 20/7/17.
 */

public class ResultsFragment extends Fragment {
    boolean isConnected = false;
    private DonorAdapter donorAdapter;
    private List<Donor> donorsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressDialog searchProgressDialog;

    public ResultsFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.results_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle b = this.getArguments();
        getActivity().setTitle("Search Results");
        String city = b.getString("city");
        String bGroup = b.getString("bGroup");
        Log.d(">>>>", "onCreate: " + city);
        Log.d(">>>>", "onCreate: " + bGroup);
        String connectivity = b.getString("isConnected");
        recyclerView = (RecyclerView) getView().findViewById(R.id.donors_recycler_view);
        donorAdapter = new DonorAdapter(donorsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(donorAdapter);
        donorsList.clear();
        donorAdapter.notifyDataSetChanged();
        searchProgressDialog = new ProgressDialog(getContext());
        searchProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        searchProgressDialog.setMessage("Loading Search Results...");
        searchProgressDialog.setIndeterminate(true);
        searchProgressDialog.setCanceledOnTouchOutside(false);
        new SearchAsyncTask().execute(bGroup, city);
    }

    private void prepareDonorsList(String result) {
        try {
            JSONArray jsonObject = new JSONArray(result);
//            Log.d(">>>>", "prepareDonorsList: "+donorArray.toString());
            for (int i = 0; i < jsonObject.length(); i++) {
                Log.d(">>>>", "prepareDonorsList: " + jsonObject.getJSONObject(i));
                JSONObject donorJsonObject = jsonObject.getJSONObject(i);
                String name = donorJsonObject.getString("name");
                String contact = donorJsonObject.getString("contact_number");
                String email = donorJsonObject.getString("email");
                String city = donorJsonObject.getString("city");
                String isAvailable = donorJsonObject.getString("isavailable");
                String isFDonor = donorJsonObject.getString("frequentdonor");
//                Log.d(">>>>", "prepareDonorsList: "+name+" "+city+" "+contact+" "+email);
                Donor d = new Donor(name, city, contact, email, isFDonor, isAvailable);
                donorsList.add(d);
            }
            donorAdapter.notifyDataSetChanged();
//            jsonObject.getJSONObject(0);
        } catch (JSONException e) {
            Log.d(TAG, "prepareDonorsList: " + e.toString());
            e.printStackTrace();
        }
    }

    private class SearchAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            searchProgressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("");
                builder.setMessage("No Internet Connectivity.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        searchProgressDialog.hide();
                        Fragment f = new MainFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, f, "Main Fragment").commit();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                prepareDonorsList(s);
                searchProgressDialog.hide();
            }
            Log.d(">>>>", "onPostExecute: " + s);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                InetAddress inetAddress = InetAddress.getByName("google.com");
                isConnected = !inetAddress.equals("");
            } catch (UnknownHostException e) {
                return null;
            }
            URL url = null;
            String result = null;
            if (isConnected == true) {

                Log.d(">>>>URL ", "doInBackground: " + strings[1]);
                try {
//              Creating a http connection
                    String uri;
                    if (strings[1].length() == 0) {
                        uri = Uri.parse("http://ngoindex.info/search_donor.php").buildUpon().appendQueryParameter("bGroup", "'" + strings[0] + "'")
                                .build().toString();
                        Log.d(">>>>URL", "doInBackground: " + uri);
                    } else {
                        uri = Uri.parse("http://ngoindex.info/search_donor.php").buildUpon().appendQueryParameter("bGroup", "'" + strings[0] + "'")
                                .appendQueryParameter("city", "'" + strings[1] + "'").build().toString();
                    }
                    url = new URL(uri);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setChunkedStreamingMode(0);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    Log.d(">>>>URL", "doInBackground: " + url);
//              Writing link to the output stream of the http connection
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
                    writer.write(String.valueOf(uri));
                    writer.flush();
                    writer.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder strBuilder = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        strBuilder.append(line);
                    }
                    reader.close();
                    result = strBuilder.toString();
                    Log.d(">>>>json: ", "doInBackground: " + result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ConnectException e) {
                    Toast.makeText(getContext(), "Connection timed out", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("");
                builder.setMessage("No Internet Connectivity.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Fragment f = new MainFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, f, "Main Fragment").commit();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            return result;
        }
    }
}
