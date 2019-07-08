package com.example.foodreview;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity{

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
    private ImageView admin_closefood;
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
                finish();
            }
        });

        mRestaurantList = new ArrayList<>();
        mFoodList = new ArrayList<>();

        admin_closefood = findViewById(R.id.admin_closefood);
        admin_closefood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFoodList.clear();
                mFoodRecyclerView.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                admin_closefood.setVisibility(View.INVISIBLE);
            }
        });

        universityManager = UniversityManager.getInstance();
        createRestaurantList();
        buildRecyclerView();
        buildFoodRecyclerView();
        mFoodRecyclerView.setVisibility(View.INVISIBLE);
        admin_closefood.setVisibility(View.INVISIBLE);

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
            case R.id.action_add:
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
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
        currentUniversity = universityManager.getUniversity("LUT-University");
        mRestaurantList = currentUniversity.getRestaurants();
    }

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
                createFoodList(mRestaurantList.get(position).getRestaurantName());
                mRecyclerView.setVisibility(View.INVISIBLE);
                mFoodRecyclerView.setVisibility(View.VISIBLE);
                mFoodAdapter.notifyDataSetChanged();
                admin_closefood.setVisibility(View.VISIBLE);
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
//                Bundle bundle = new Bundle();
//                bundle.putString("restaurantName", mRestaurantList.get(position).getRestaurantName());
//                bundle.putString("restaurantAddress", mRestaurantList.get(position).getRestaurantAddress());
//                Fragment adminEditFragment = new AdminEditFragment();
//                adminEditFragment.setArguments(bundle);
//                frame = findViewById(R.id.adminEditFragmentWindow);
//                frame.setVisibility(View.VISIBLE);
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.adminEditFragmentWindow, adminEditFragment);
//                transaction.commit();
//                TODO: Other fragment
            }
        });
    }

    public void continueClick(View view) {
        frame.setVisibility(View.INVISIBLE);
        Fragment adminEditFragment = new AdminEditFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(adminEditFragment);
        transaction.commit();
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
