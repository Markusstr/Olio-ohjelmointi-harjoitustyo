package com.example.foodreview;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private RecyclerView mRecyclerView;
    private AdminRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView mFoodRecyclerView;
    private AdminFoodRecyclerViewAdapter mFoodAdapter;
    private RecyclerView.LayoutManager mFoodLayoutManager;

    private ArrayList<Restaurant> mRestaurantList;
    private ArrayList<Food> mFoodList;
    private University currentUniversity;
    private UniversityManager universityManager;

    private Spinner admin_unispinner;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbaradmin);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.admin_title));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backAction();
            }
        });

        mRestaurantList = new ArrayList<>();
        mFoodList = new ArrayList<>();

        admin_unispinner = findViewById(R.id.admin_unispinner);

        universityManager = UniversityManager.getInstance();

        ArrayList<String> uniNames;
        uniNames = universityManager.getUniNames();
        currentUniversity = universityManager.getUniversity(universityManager.getUniNames().get(0));

        ArrayAdapter<String> adapterUni = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, uniNames);
        adapterUni.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        admin_unispinner.setAdapter(adapterUni);
        admin_unispinner.setOnItemSelectedListener(this);

        //Both recycler views are on onCreate and they are not created again after this. Food recycler view will be set to invisible
        //and its content is empty until user presses a restaurant from the first recycler view.
        createRestaurantList();
        buildRecyclerView();
        buildFoodRecyclerView();
        mFoodRecyclerView.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_admin_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.admin_action_users:
                Intent intent = new Intent(this, AdminUserManager.class);
                startActivity(intent);
                break;
            case R.id.admin_action_newrestaurant:
                Toast.makeText(this, "New restaurant", Toast.LENGTH_SHORT).show();
                //TODO: New restaurant fragment
                break;
            case R.id.admin_action_newfood:
                Toast.makeText(this, "New food", Toast.LENGTH_SHORT).show();
                //TODO: New food fragment
                break;
        }
        return true;
    }

    public void removeItem(int position) {
        mRestaurantList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void removeFoodItem(int position) {
        mFoodList.remove(position);
        mFoodAdapter.notifyItemRemoved(position);
    }

    public void createRestaurantList() {
        //currentUniversity = universityManager.getUniversity("LUT-University");
        mRestaurantList.addAll(currentUniversity.getRestaurants());
    }

    //Same food list is used so that the food recycler view can be updated with mFoodAdapter.notifyDataSetChanged()
    //Because of this, no new adapters or recycler views need to be created
    public void createFoodList(String name) {
        Restaurant currentRestaurant = currentUniversity.getRestaurant(name);
        mFoodList.addAll(currentRestaurant.getFoods());

    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.adminRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdminRecyclerViewAdapter(mRestaurantList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdminRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Get a new food list from the restaurant that user pressed. Set first recycler view to invisible and second to visible
                //Update the food recycler view adapter and change title to restaurant name
                mFoodList.clear();
                createFoodList(mRestaurantList.get(position).getRestaurantName());
                mFoodAdapter.notifyDataSetChanged();
                mRecyclerView.setVisibility(View.INVISIBLE);
                mFoodRecyclerView.setVisibility(View.VISIBLE);
                Objects.requireNonNull(getSupportActionBar()).setTitle(mRestaurantList.get(position).getRestaurantName());
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onEditClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("restaurantName", mRestaurantList.get(position).getRestaurantName());
                bundle.putString("restaurantAddress", mRestaurantList.get(position).getRestaurantAddress());
                Fragment adminEditFragment = new AdminEditFragment();
                adminEditFragment.setArguments(bundle);
                frame = findViewById(R.id.adminEditFragmentWindow);
                frame.setVisibility(View.VISIBLE);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.adminEditFragmentWindow, adminEditFragment);
                transaction.commit();
            }
        });
    }

    public void buildFoodRecyclerView() {
        mFoodRecyclerView = findViewById(R.id.adminFoodRecyclerView);
        mFoodRecyclerView.setHasFixedSize(true);
        mFoodLayoutManager = new LinearLayoutManager(this);
        mFoodAdapter = new AdminFoodRecyclerViewAdapter(mFoodList);

        mFoodRecyclerView.setLayoutManager(mFoodLayoutManager);
        mFoodRecyclerView.setAdapter(mFoodAdapter);

        mFoodAdapter.setOnItemClickListener(new AdminFoodRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public void onDeleteClick(int position) {
                removeFoodItem(position);
            }

            @Override
            public void onEditClick(int position) {
                Bundle bundle = new Bundle();
                String foodPrice = "" + mFoodList.get(position).getFoodPrice();
                bundle.putString("foodName", mFoodList.get(position).getFoodName());
                bundle.putString("foodPrice", foodPrice);
                bundle.putString("foodDate", mFoodList.get(position).getDate());
                Fragment adminFoodEditFragment = new adminFoodEditFragment();
                adminFoodEditFragment.setArguments(bundle);
                frame = findViewById(R.id.adminEditFragmentWindow);
                frame.setVisibility(View.VISIBLE);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.adminEditFragmentWindow, adminFoodEditFragment);
                transaction.commit();
            }
        });
    }

    public void continueClick(View view) {
        frame.setVisibility(View.INVISIBLE);
        //TODO: Save new settings
    }

    @Override
    public void onBackPressed() {
        backAction();
    }


    //If user clicks back arrow or back button following actions will happen. If there is food recycler view open,
    //that will close and if user is in the admin activity main page, admin activity will close
    private void backAction() {
        if (mFoodRecyclerView.getVisibility() == View.VISIBLE) {
            mFoodRecyclerView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.admin_title));
        }
        else {
            finish();
        }
    }


    //University spinner listeners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mRestaurantList.clear();
        String uniName = parent.getItemAtPosition(position).toString();
        currentUniversity = universityManager.getUniversity(uniName);
        createRestaurantList();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

        //Creating the recyclerView and customizing it
//        RecyclerView recyclerView = findViewById(R.id.adminRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        radapter = new AdminRecyclerViewAdapter(this, names, addresses);
//        radapter.setClickListener(this);
//        recyclerView.setAdapter(radapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));
//    }
//
//    @Override
//    public void onItemClick(View view, int position) {
//        final String nimi = radapter.getName(position);
//        admin_delete = findViewById(R.id.admin_delete);
//        admin_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(AdminActivity.this, "Klikkasit: " + nimi, Toast.LENGTH_SHORT).show();
//            }
//        });
