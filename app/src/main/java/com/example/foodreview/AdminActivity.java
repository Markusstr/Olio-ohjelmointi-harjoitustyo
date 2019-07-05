package com.example.foodreview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    private ArrayList<Restaurant> mRestaurantList;
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

        createRestaurantList();
        buildRecyclerView();

    }

    public void removeItem(int position) {
        mRestaurantList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void createRestaurantList() {
        mRestaurantList = new ArrayList<>();
        mRestaurantList.add(new Restaurant("Laseri", "Testikatu 25 A"));
        mRestaurantList.add(new Restaurant("LUT Buffet", "Yliopistonkatu 34"));
        mRestaurantList.add(new Restaurant("LUT Buffet", "Yliopistonkatu 34"));
        mRestaurantList.add(new Restaurant("LUT Buffet", "Yliopistonkatu 34"));
        mRestaurantList.add(new Restaurant("LUT Buffet", "Yliopistonkatu 34"));
        mRestaurantList.add(new Restaurant("LUT Buffet", "Yliopistonkatu 34"));
        mRestaurantList.add(new Restaurant("LUT Buffet", "Yliopistonkatu 34"));
        mRestaurantList.add(new Restaurant("LUT Buffet", "Yliopistonkatu 34"));

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
