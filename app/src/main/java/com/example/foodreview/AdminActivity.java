package com.example.foodreview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity implements AdminRecyclerViewAdapter.ItemClickListener{

    private ImageView admin_delete;
    private AdminRecyclerViewAdapter radapter;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> addresses = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbaradmin);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.admin_title));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        names.add("Laseri");
        addresses.add("Testikatu 25 A");

        names.add("LUT Buffet");
        addresses.add("Yliopistonkatu 34");

        //Creating the recyclerView and customizing it
        RecyclerView recyclerView = findViewById(R.id.adminRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        radapter = new AdminRecyclerViewAdapter(this, names, addresses);
        radapter.setClickListener(this);
        recyclerView.setAdapter(radapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onItemClick(View view, int position) {
        final String nimi = radapter.getName(position);
        admin_delete = findViewById(R.id.admin_delete);
        admin_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Klikkasit: " + nimi, Toast.LENGTH_SHORT).show();
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("restaurantName", radapter.getName(position));
        bundle.putString("restaurantAddress", radapter.getAddress(position));
        Fragment adminEditFragment = new AdminEditFragment();
        adminEditFragment.setArguments(bundle);
        frame = findViewById(R.id.adminEditFragmentWindow);
        frame.setVisibility(View.VISIBLE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.adminEditFragmentWindow, adminEditFragment);
        transaction.commit();

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
