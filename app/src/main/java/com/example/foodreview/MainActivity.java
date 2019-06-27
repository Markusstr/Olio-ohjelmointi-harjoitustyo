package com.example.foodreview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner universities;
    Spinner restaurants;
    University university;
    Restaurant restaurant;
    ArrayAdapter<String> adapterRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Starts the log in activity first
        Intent intent = new Intent(this, LogInActivity.class);
        startActivityForResult(intent, 1);

        setContentView(R.layout.activity_main);
        university = University.getInstance();
        restaurant = Restaurant.getInstance();
        universities = findViewById(R.id.universitySpinner);
        restaurants = findViewById(R.id.restaurantSpinner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "gerbiili", Snackbar.LENGTH_LONG) //memes
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        //Creates the list of universities
        university.newUni("LUT", "LUT"); //TODO TEMPORARY
        ArrayList<String> uniNames = new ArrayList<>();
        for (int i = 0; i < university.uniList.size(); i++) {
            if (!uniNames.contains(university.uniList.get(i).getUniName())) {
                uniNames.add(university.uniList.get(i).getUniName());
            }
        }

        ArrayAdapter<String> adapterUni = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, uniNames);
        adapterUni.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        universities.setAdapter(adapterUni);

        restaurant.newRestaurant("Aalef", "AALEF");
        ArrayList<String> restaurantNames = new ArrayList<>();
        for (int i = 0; i < restaurant.restaurantList.size(); i++) {
            if (!restaurantNames.contains(restaurant.restaurantList.get(i).getRestaurantName())) {
                restaurantNames.add(restaurant.restaurantList.get(i).getRestaurantName());
            }
        }

        adapterRestaurant = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, restaurantNames);
        adapterRestaurant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurants.setAdapter(adapterRestaurant);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //TODO Handle the main menu action
        } else if (id == R.id.nav_profile) {
            //TODO Handle the profile action
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_log_out) {
            //TODO Handle logging out properly
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
