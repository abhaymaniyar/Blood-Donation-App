package me.example.abhay.blooddonationapp;

import android.content.Context;
import android.content.Intent;
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
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    NavigationView navigationView;
    int i = 0;
    private Fragment fragmentOther;
    private FragmentTransaction fragmentTransaction;

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String TAG = ">>>>";
        if (id == R.id.nav_search) {
            fragmentOther = new MainFragment();
            setTitle("Search Donors");
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
            Intent i = new Intent();
            i.setType("text/plain");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setAction(Intent.ACTION_SEND);
//            i.putExtra(Intent.EXTRA_SUBJECT, Uri.parse("https://play.google.com/store/apps/details?id=me.example.abhay.blooddonationapp"));
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "\n\"Blood Donors United\": \n";
            sAux = sAux + "Search for blood donors in your city or register as a donor to save a life.\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=me.example.abhay.blooddonationapp \n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            this.startActivity(i);
            fragmentOther = new MainFragment();
            setTitle("Search Donors");
        } else if (id == R.id.about_us) {
            fragmentOther = new AboutUsFragment();
            setTitle("About Me");
        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame, fragmentOther, "Other Fragments").commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
