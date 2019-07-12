package com.example.foodreview;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

//TODO doge is wow
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Spinner.OnItemSelectedListener {

    Spinner universities;
    Spinner restaurants;
    UniversityManager universityManager;
    University currentUniversity;
    Restaurant currentRestaurant;
    Food reviewedFood;
    ArrayAdapter<String> adapterRestaurant;
    RecyclerView mRecyclerView;
    RecyclerViewAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String> mFoodNames;
    ArrayList<Float> mFoodPrices;
    ArrayList<Food> mFoodList;
    FrameLayout frame;
    Fragment reviewFragment;
    String thisDate;
    DatabaseManager dbms;
    Button datePicker;
    TextView date;
    ArrayList<String> restaurantStrings;
    TextView nav_header_username;
    private int year, month, day;
    final Calendar c = Calendar.getInstance();

    private String username;
    String nickname;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        universities = findViewById(R.id.universitySpinner);
        restaurants = findViewById(R.id.restaurantSpinner);
        navigationView = findViewById(R.id.nav_view);
        datePicker = findViewById(R.id.datePicker);
        date = findViewById(R.id.dateText);

        //Creates today's date, createDate() method is for getting a new date
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        String temp;
        if ((month + 1) < 10) {
            if (day < 10) {
                temp = "0" + day + ".0" + (month + 1) + "." + year;
                date.setText(temp);
                thisDate = "0" + day + ".0" + (month + 1) + "." + year;
            } else {
                temp =day + ".0" + (month + 1) + "." + year;
                date.setText(temp);
                thisDate = day + ".0" + (month + 1) + "." + year;
            }
        } else {
            if (day < 10) {
                temp = "0" + day + "." + (month + 1) + "." + year;
                date.setText(temp);
                thisDate = "0" + day + "." + (month + 1) + "." + year;
            } else {
                temp = day + "." + (month + 1) + "." + year;
                date.setText(temp);
                thisDate = day + "." + (month + 1) + "." + year;
            }
        }

        dbms = DatabaseManager.getInstance(this);
        universityManager = UniversityManager.getInstance();
        dbms.updateUniversities();

        username = getIntent().getStringExtra("username");
        nickname = dbms.getOwnUser(username).getNickname();
        View headerView = navigationView.getHeaderView(0);
        nav_header_username = headerView.findViewById(R.id.nav_header_username);
        nav_header_username.setText(nickname);


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
                Snackbar.make(view, "Doge is wow", Snackbar.LENGTH_LONG) //memes
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


        final ArrayList<String> uniNames;
        uniNames = universityManager.getUniNames();

        ArrayAdapter<String> adapterUni = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, uniNames);
        adapterUni.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        universities.setAdapter(adapterUni);
        universities.setOnItemSelectedListener(this);

        mFoodNames = new ArrayList<>();
        mFoodPrices = new ArrayList<>();
        mFoodList = new ArrayList<>();


        mRecyclerView = findViewById(R.id.foodListView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapter(mFoodList, username);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "ItemClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReviewClick(int position) {
                reviewFragment = new ReviewFragment();

                Bundle bundle = new Bundle();
                bundle.putString("foodName", mFoodList.get(position).getFoodName());
                reviewedFood = universityManager.getUniversity(uniNames.get
                        (universities.getSelectedItemPosition())).getRestaurant
                        (restaurantStrings.get(restaurants.getSelectedItemPosition())).getFoods().get(position);
                reviewFragment.setArguments(bundle);
                frame = findViewById(R.id.reviewFragmentWindow);
                frame.setVisibility(View.VISIBLE);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.reviewFragmentWindow, reviewFragment);
                transaction.commit();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String currentUniversityName = universities.getItemAtPosition(universities.getSelectedItemPosition()).toString();
                University currentUniversity = universityManager.getUniversity(currentUniversityName);
                dbms.updateCascade(currentUniversity);
                makeRestaurantSpinner(currentUniversityName);

            }
        }
        else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                nickname = dbms.getOwnUser(username).getNickname();
                nav_header_username.setText(nickname);
            }
        }
        //TODO: This code catches resultCode of review screen
        else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                dbms.updateCascade(currentUniversity);
                mAdapter.notifyDataSetChanged();
            }
        }

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
            intent.putExtra("username", username);
            startActivityForResult(intent, 1);
        } else if (id == R.id.nav_review) {
            Intent intent = new Intent(this, ReviewActivity.class);
            intent.putExtra("username", username);
            startActivityForResult(intent, 3);
            //TODO: Intent.putExtra(username)
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("username", username);
            startActivityForResult(intent, 2);

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

    public void reviewCancel(View view) {
        frame.setVisibility(View.INVISIBLE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(reviewFragment);
        transaction.commit();

        Toast.makeText(this, "" + getString(R.string.ratingCancelled), Toast.LENGTH_SHORT).show();
    }

    public void reviewSave(View view) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        ReviewFragment reviewFragment = (ReviewFragment) manager.findFragmentById(R.id.reviewFragmentWindow);
        String newReviewString = reviewFragment.getReviewString();
        float newReviewGrade = reviewFragment.getReviewGrade();
        String newReviewFood = reviewFragment.getReviewFoodName();

        if (!newReviewString.equals("")) {
            frame.setVisibility(View.INVISIBLE);
            transaction.detach(reviewFragment);
            transaction.commit();
            Toast.makeText(this, "reviewed " + newReviewFood, Toast.LENGTH_SHORT).show();
            dbms.setNewReview(newReviewString, newReviewGrade, username, reviewedFood.getFoodId());
            dbms.updateReviews(reviewedFood);

            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Come on, write a few words as well!", Toast.LENGTH_SHORT).show();
        }
    }

    public void makeRestaurantSpinner(String uniName) {
        currentUniversity = universityManager.getUniversity(uniName);
        ArrayList<Restaurant> restaurantObjects = currentUniversity.getRestaurants();
        restaurantStrings = new ArrayList<>();
        for (int x = 0; x < restaurantObjects.size(); x++) {
            if (restaurantObjects.get(x).getIsEnabled()) {
                restaurantStrings.add(restaurantObjects.get(x).getRestaurantName());
            }
        }
        adapterRestaurant = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, restaurantStrings);
        adapterRestaurant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurants.setAdapter(adapterRestaurant);
        restaurants.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        System.out.println("Going to item selected." + parent.getId());

        switch (parent.getId()) {
            case R.id.universitySpinner:
                String uniName = parent.getItemAtPosition(position).toString();
                System.out.println(uniName);
                dbms.updateUniversities();
                currentUniversity = universityManager.getUniversity(uniName);
                if (currentUniversity == null) {
                    return;
                }
                dbms.updateCascade(currentUniversity);
                makeRestaurantSpinner(uniName);
                break;

            case R.id.restaurantSpinner:
                String restaurantName = parent.getItemAtPosition(position).toString();
                System.out.println(restaurantName);
                currentRestaurant = currentUniversity.getRestaurant(restaurantName);
                if (currentRestaurant == null) {
                    return;
                }
//                mFoodPrices.clear();
//                mFoodNames.clear();
                mFoodList.clear();
//                mFoodNames.addAll(currentRestaurant.getRestaurantFoodStrings(thisDate));
//                mFoodPrices.addAll(currentRestaurant.getRestaurantFoodFloats(thisDate));
                mFoodList.addAll(currentRestaurant.getRestaurantFoods(thisDate));
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Creates a date for thisDate
    public void createDate(View view) {
        if (view == datePicker) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String temp;
                    if ((month + 1) < 10) {
                        if (dayOfMonth < 10) {
                            temp = "0" + dayOfMonth + ".0" + (month + 1) + "." + year;
                            date.setText(temp);
                            thisDate = "0" + dayOfMonth + ".0" + (month + 1) + "." + year;
                        } else {
                            temp = dayOfMonth + ".0" + (month + 1) + "." + year;
                            date.setText(temp);
                            thisDate = dayOfMonth + ".0" + (month + 1) + "." + year;
                        }
                    } else {
                        if (dayOfMonth < 10) {
                            temp = "0" + dayOfMonth + "." + (month + 1) + "." + year;
                            date.setText(temp);
                            thisDate = "0" + dayOfMonth + "." + (month + 1) + "." + year;
                        } else {
                            temp = dayOfMonth + "." + (month + 1) + "." + year;
                            date.setText(temp);
                            thisDate = dayOfMonth + "." + (month + 1) + "." + year;
                        }
                    }
//                    mFoodPrices.clear();
//                    mFoodNames.clear();
                    mFoodList.clear();
//                    mFoodNames.addAll(currentRestaurant.getRestaurantFoodStrings(thisDate));
//                    mFoodPrices.addAll(currentRestaurant.getRestaurantFoodFloats(thisDate));
                    mFoodList.addAll(currentRestaurant.getRestaurantFoods(thisDate));
                    mAdapter.notifyDataSetChanged();
                }
            }, year, month, day);
            datePickerDialog.show();
        }
    }
}
