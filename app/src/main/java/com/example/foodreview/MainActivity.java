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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewAdapter.ItemClickListener, Spinner.OnItemSelectedListener {

    Spinner universities;
    Spinner restaurants;
    UniversityManager universityManager;
    University currentUniversity;
    Restaurant currentRestaurant;
    ArrayList<String> foodNames;
    ArrayList<Float> foodPrices;
    RecyclerView recyclerView;
    ArrayAdapter<String> adapterRestaurant;
    RecyclerViewAdapter radapter;
    FrameLayout frame;
    Fragment reviewFragment;
    Bundle bundle;
    String thisDate = "08.07.2019";
    DatabaseManager dbms;

    private String username;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Starts the log in activity first
//        Intent intent = new Intent(this, LogInActivity.class);
//        startActivityForResult(intent, 1);

        setContentView(R.layout.activity_main);

        universities = findViewById(R.id.universitySpinner);
        restaurants = findViewById(R.id.restaurantSpinner);
        recyclerView = findViewById(R.id.foodListView);

        navigationView = findViewById(R.id.nav_view);

        username = getIntent().getStringExtra("username");
        View headerView = navigationView.getHeaderView(0);
        TextView nav_header_username = headerView.findViewById(R.id.nav_header_username);
        nav_header_username.setText(username);

        dbms = DatabaseManager.getInstance(this);
        universityManager = UniversityManager.getInstance();
        dbms.updateUniversities();

        if (dbms.isAdmin(username)) {
            navigationView.getMenu().setGroupVisible(R.id.menu_admingroup, true);
//            navigationView.getMenu().setGroupEnabled(R.id.menu_admingroup, false);
        }
        else {
            navigationView.getMenu().setGroupVisible(R.id.menu_admingroup, false);
        }

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
//        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        ArrayList<String> uniNames;
        uniNames = universityManager.getUniNames();

        ArrayAdapter<String> adapterUni = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, uniNames);
        adapterUni.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        universities.setAdapter(adapterUni);
        universities.setOnItemSelectedListener(this);

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
        // Handles sidebar item clicks
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //TODO Handle the main menu action
        } else if (id == R.id.nav_admin){
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            //TODO: Intent.putExtra(username)
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);

        } else if (id == R.id.nav_log_out) {
            //TODO Handle logging out properly
            Intent intent = new Intent(this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        bundle = new Bundle();
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
        frame.setVisibility(View.INVISIBLE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(reviewFragment);
        transaction.commit();

        Toast.makeText(this, "" + getString(R.string.ratingCancelled), Toast.LENGTH_SHORT).show();
    }

    public void reviewSave(View view) {
        frame.setVisibility(View.INVISIBLE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(reviewFragment);
        transaction.commit();
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        String toast = bundle.getString("foodName") + " " + getString(R.string.ratingRated) + " " + ratingBar.getRating();
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    public void makeRestaurantSpinner(String uniName) {
        currentUniversity = universityManager.getUniversity(uniName);
        ArrayList<String> restaurantStrings = currentUniversity.getRestaurantStrings();
        adapterRestaurant = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, restaurantStrings);
        adapterRestaurant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurants.setAdapter(adapterRestaurant);
        restaurants.setOnItemSelectedListener(this);
    }

    public void makeFoodsRecycler () {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        radapter = new RecyclerViewAdapter(this, foodNames, foodPrices);
        radapter.setClickListener(this);
        recyclerView.setAdapter(radapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        System.out.println("Going to item selected." + parent.getId());

        switch (parent.getId()) {
            case R.id.universitySpinner:
                String uniName = parent.getItemAtPosition(position).toString();
                System.out.println(uniName);
                currentUniversity = universityManager.getUniversity(uniName);
                dbms.updateRestaurants(currentUniversity);
                makeRestaurantSpinner(uniName);

            case R.id.restaurantSpinner:
                String restaurantName = parent.getItemAtPosition(position).toString();
                System.out.println(restaurantName);
                currentRestaurant = currentUniversity.getRestaurant(restaurantName);
                if (currentRestaurant == null) {
                    return;
                }
                dbms.updateFoods(currentRestaurant);
                foodNames = currentRestaurant.getRestaurantFoodStrings(thisDate);
                foodPrices = currentRestaurant.getRestaurantFoodFloats(thisDate);
                makeFoodsRecycler();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                assert data != null;
//                username = data.getStringExtra("username");
//                View headerView = navigationView.getHeaderView(0);
//                TextView nav_header_username = headerView.findViewById(R.id.nav_header_username);
//                nav_header_username.setText(username);
//            }
//        }
//    }
}
