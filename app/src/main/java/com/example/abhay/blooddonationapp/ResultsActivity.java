package com.example.abhay.blooddonationapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhay on 15/7/17.
 */

public class ResultsActivity extends AppCompatActivity {

    private DonorAdapter donorAdapter;
    private List<Donor> donorsList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);
        Bundle b = getIntent().getExtras();
        String city = b.getString("city");
        String bGroup = b.getString("bGroup");
        Log.d(">>>>", "onCreate: "+city);
        Log.d(">>>>", "onCreate: "+bGroup);
        recyclerView = (RecyclerView) findViewById(R.id.donors_recycler_view);
        donorAdapter = new DonorAdapter(donorsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(donorAdapter);
        donorsList.clear();
        donorAdapter.notifyDataSetChanged();
        new SearchAsyncTask().execute(bGroup, city);
    }

    private class SearchAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            prepareDonorsList(s);
            Log.d(">>>>", "onPostExecute: " + s);
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            String result = null;
            Log.d(">>>>URL ", "doInBackground: " + strings[1]);
            try {
//              Creating a http connection
                String uri = Uri.parse("http://ngoindex.info/search_donor.php").buildUpon().appendQueryParameter("bGroup", "'" + strings[0] + "'")
                        .appendQueryParameter("city", "'" + strings[1] + "'").build().toString();
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
                Toast.makeText(ResultsActivity.this, "Connection timed out", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
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
//                Log.d(">>>>", "prepareDonorsList: "+name+" "+city+" "+contact+" "+email);
                Donor d = new Donor(name, city, contact, email);
                donorsList.add(d);
            }
            donorAdapter.notifyDataSetChanged();
//            jsonObject.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
