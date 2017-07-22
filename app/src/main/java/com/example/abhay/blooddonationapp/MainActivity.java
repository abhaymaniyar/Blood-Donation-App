package com.example.abhay.blooddonationapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Fragment fragmentOther;
    private FragmentTransaction fragmentTransaction;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = this.getSharedPreferences("registrationStatus", Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame, fragment, "Main Fragment").commit();
        setTitle("Search Donors");

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    int i = 0;

    @Override
    public void onBackPressed() {
        String str = getSupportFragmentManager().findFragmentById(R.id.fragment_frame).getTag();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (str.equals("Main Fragment")) {
            i++;
            if (i == 2) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, new MainFragment(), "Main Fragment").commit();
            setTitle("Search Donors");
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String TAG = ">>>>";
        if (id == R.id.nav_search) {
            fragmentOther = new MainFragment();
            setTitle("Search Donor");
        } else if (id == R.id.nav_register) {
            SharedPreferences sharedPreferences = getSharedPreferences("registrationStatus", Context.MODE_PRIVATE);
            boolean isRegistered = sharedPreferences.getBoolean("isRegistered", false);
            if (isRegistered) {
                fragmentOther = new DonorDetailsFragment();
                setTitle("Donor Details");
            } else {
                fragmentOther = new RegisterFragment();
                setTitle("Register as a Donor");
            }
        } else if (id == R.id.nav_faq) {
            fragmentOther = new FaqFragment();
            setTitle("Blood Donation FAQ");
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.about_us) {
            fragmentOther = new AboutUsFragment();
            setTitle("About Me");
        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame, fragmentOther, "Other Fragments").commit();
//        fragmentTransaction.addToBackStack(null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
