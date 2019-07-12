package com.example.foodreview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReviewRecyclerViewAdapter mAdapter;
    private ArrayList<Review> mReviewList;

    private String username;
    String nickname;
    private DatabaseManager dbms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        dbms = DatabaseManager.getInstance(this);

        username = getIntent().getStringExtra("username");
        nickname = dbms.getOwnUser(username).getNickname();

        TextView usernameText = findViewById(R.id.profileUsernameText);
        usernameText.setText(nickname);

        mReviewList = new ArrayList<>();

        TextView reviewText = findViewById(R.id.profileReviewText);



        dbms = DatabaseManager.getInstance(this);
        if (dbms.isAdmin(username)) {
            mReviewList = dbms.getAllReviews();
            reviewText.setText(getResources().getString(R.string.review_reviewadmintext));
        }
        else {
            mReviewList = dbms.getReviewsForUser(username);
            reviewText.setText(getResources().getString(R.string.review_reviewtext));
        }

        TextView ownReviews = findViewById(R.id.ownReviews);
        String reviews = " " + mReviewList.size();
        ownReviews.setText(reviews);

        Toolbar toolbar = findViewById(R.id.toolbarreview);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.review_activitytitle));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });

        buildRecyclerView();


    }
    public void deleteItem(int position, Review review) {
        mReviewList.remove(position);
        mAdapter.notifyItemRemoved(position);
        dbms.deleteReview(review);
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.review_recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ReviewRecyclerViewAdapter(mReviewList, dbms.isAdmin(username));

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ReviewRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onEditClick(int position) {
                //TODO: Edit fragment
            }

            @Override
            public void onDeleteClick(int position) {
                Review review = mReviewList.get(position);
                deleteItem(position, review);
            }
        });
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    private void closeActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }
}
