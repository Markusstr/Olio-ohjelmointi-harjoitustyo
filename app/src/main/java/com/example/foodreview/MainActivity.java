package com.example.foodreview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewAdapter.ItemClickListener {

    Spinner universities;
    Spinner restaurants;
    University university;
    Restaurant restaurant;
    Food food;
    ArrayAdapter<String> adapterRestaurant;
    RecyclerViewAdapter radapter;
    FrameLayout frame;
    Fragment reviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Starts the log in activity first
        Intent intent = new Intent(this, LogInActivity.class);
        startActivityForResult(intent, 1);

        setContentView(R.layout.activity_main);
        university = University.getInstance();
        restaurant = Restaurant.getInstance();
        food = Food.getInstance();
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
        university.newUni("LUT", "LUT"); //TODO TEMPORARY, IMPLEMENT DATABASE HERE PLS

        ArrayList<String> uniNames = new ArrayList<>();
        for (int i = 0; i < university.uniList.size(); i++) {
            if (!uniNames.contains(university.uniList.get(i).getUniName())) {
                uniNames.add(university.uniList.get(i).getUniName());
            }
        }

        ArrayAdapter<String> adapterUni = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, uniNames);
        adapterUni.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        universities.setAdapter(adapterUni);

        //Creates the list of restaurants
        restaurant.newRestaurant("Aalef", "AALEF"); //TODO TEMPORARY, IMPLEMENT DATABASE HERE PLS

        ArrayList<String> restaurantNames = new ArrayList<>();
        for (int i = 0; i < restaurant.restaurantList.size(); i++) {
            if (!restaurantNames.contains(restaurant.restaurantList.get(i).getRestaurantName())) {
                restaurantNames.add(restaurant.restaurantList.get(i).getRestaurantName());
            }
        }

        adapterRestaurant = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, restaurantNames);
        adapterRestaurant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurants.setAdapter(adapterRestaurant);

        //Creates the list of foods for the recyclerView
        food.newFood("Spaghetti", "SPAGHETTI", 1); //TODO TEMPORAARY, IMPLEMENT DATABASE HERE PLS
        food.newFood("Lasagna", "LASAGNA", 2);

        ArrayList<String> foodNames = new ArrayList<>();
        for (int i = 0; i < food.foodList.size(); i++) {
            if (!foodNames.contains(food.foodList.get(i).getFoodName())) {
                foodNames.add(food.foodList.get(i).getFoodName());
            }
        }

        ArrayList<Double> foodPrices = new ArrayList<>();
        for (int i = 0; i < food.foodList.size(); i++) {
            if (!foodPrices.contains(food.foodList.get(i).getFoodPrice())) {
                foodPrices.add(food.foodList.get(i).getFoodPrice());
            }
        }

        //Creating the recyclerView and customizing it
        RecyclerView recyclerView = findViewById(R.id.foodListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        radapter = new RecyclerViewAdapter(this, foodNames, foodPrices);
        radapter.setClickListener(this);
        recyclerView.setAdapter(radapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handles navigation view item clicks
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //TODO Handle the main menu action
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            //TODO: Intent.putExtra(username)
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

    @Override
    public void onItemClick(View view, int position) {

        //When the user clicks on a food, it starts a review fragment

        reviewFragment = new ReviewFragment();

        Bundle bundle = new Bundle();
        bundle.putString("foodName", radapter.getName(position));
        reviewFragment.setArguments(bundle);
        frame = findViewById(R.id.reviewFragmentWindow);
        frame.setVisibility(View.VISIBLE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.reviewFragmentWindow, reviewFragment);
        transaction.commit();
    }

    public void reviewCancel(View view) {

        //TODO Do not save the review (currently exactly the same as reviewSave)

        frame.setVisibility(View.INVISIBLE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(reviewFragment);
        transaction.commit();
    }

    public void reviewSave(View view) {
        frame.setVisibility(View.INVISIBLE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(reviewFragment);
        transaction.commit();
    }
}
