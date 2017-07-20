package com.example.abhay.blooddonationapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhay on 20/7/17.
 */

public class MainFragment extends Fragment {
    public MainFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("registrationStatus", Context.MODE_PRIVATE);

        final Spinner spinner = (Spinner) getView().findViewById(R.id.search_spinner);
        final List<String> bloodGroups = new ArrayList<String>();
        bloodGroups.add("A+");
        bloodGroups.add("A");
        bloodGroups.add("B+");
        bloodGroups.add("O+");
        bloodGroups.add("B-");
        bloodGroups.add("AB-");
        bloodGroups.add("O-");
        bloodGroups.add("B");
        //        set Adapter to Spinner items
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bloodGroups);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(dataAdapter);
        spinner.setPrompt(bloodGroups.get(0));
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

//        onClick behavior of the search button
        Button searchDonorBtn = (Button) getView().findViewById(R.id.search_button);
        searchDonorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText cityEditText = (EditText) getView().findViewById(R.id.search_city);
                String city = cityEditText.getText().toString();
                Spinner spnr = (Spinner) getView().findViewById(R.id.search_spinner);
                String bGroup = spnr.getPrompt().toString();
                Intent resultsActivity = new Intent(getContext(), ResultsActivity.class);
                resultsActivity.putExtra("city", city);
                resultsActivity.putExtra("bGroup", bGroup);
                startActivity(resultsActivity);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
