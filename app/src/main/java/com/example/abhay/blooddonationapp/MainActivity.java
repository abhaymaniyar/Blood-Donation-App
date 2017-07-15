package com.example.abhay.blooddonationapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = this.getSharedPreferences("registrationStatus", Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Spinner spinner = (Spinner) findViewById(R.id.search_spinner);
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroups);
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
        Button searchDonorBtn = (Button) findViewById(R.id.search_button);
        searchDonorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText cityEditText = (EditText) findViewById(R.id.search_city);
                String city = cityEditText.getText().toString();
                Spinner spnr = (Spinner) findViewById(R.id.search_spinner);
                String bGroup = spnr.getPrompt().toString();
                Intent resultsActivity = new Intent(MainActivity.this, ResultsActivity.class);
                resultsActivity.putExtra("city", city);
                resultsActivity.putExtra("bGroup", bGroup);
                startActivity(resultsActivity);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String TAG = ">>>>";
        if (id == R.id.nav_search) {
            // Handle the camera action
        } else if (id == R.id.nav_register) {
            SharedPreferences sharedPreferences = getSharedPreferences("registrationStatus", Context.MODE_PRIVATE);
            boolean isRegistered = sharedPreferences.getBoolean("isRegistered", false);
            if (isRegistered) {
                Intent i = new Intent(this, DonorDetailsActivity.class);
                startActivity(i);
            } else {
                Intent i = new Intent(this, RegisterActivity.class);
                startActivity(i);
            }
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
