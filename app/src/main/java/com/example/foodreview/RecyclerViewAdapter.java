package com.example.foodreview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Food> mFoodList;
    private OnItemClickListener mListener;
    private String username;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onReviewClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mPrice, mAvgGrade;
        ImageView mReview;


        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mName = itemView.findViewById(R.id.main_name);
            mPrice = itemView.findViewById(R.id.main_price);
            mAvgGrade = itemView.findViewById(R.id.main_avgGrade);
            mReview = itemView.findViewById(R.id.main_grade);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onReviewClick(position);
                        }
                    }
                }
            });
        }
    }

    RecyclerViewAdapter(ArrayList<Food> foodList, String newUsername) {
        mFoodList = foodList;
        username = newUsername;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_row, viewGroup, false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Food currentItem = mFoodList.get(i);

        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);

        String time = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
        if (currentItem.getDate().equals(time)) {
            viewHolder.mReview.setVisibility(View.VISIBLE);
            //TODO: Get this method working
            ArrayList<Review> reviews = currentItem.getReviews();
            for (int x = 0; x < reviews.size(); x++) {
                if (reviews.get(x).getUserId().equals(username)){
                    viewHolder.mReview.setEnabled(false);
                    viewHolder.mReview.setAlpha(0.3f);
                }
            }
        }
        else {
            viewHolder.mReview.setVisibility(View.INVISIBLE);
        }
        viewHolder.mName.setText(currentItem.getFoodName());
        String price = " " + df.format(currentItem.getFoodPrice()) + "€";
        viewHolder.mPrice.setText(price);
        viewHolder.mAvgGrade.setText("4.2/5.0");
        //TODO: Average grade => viewHolder.mAvgGrade.setText();

    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }
}


    // data is passed into the constructor
//    RecyclerViewAdapter(Context context, ArrayList<String> dataNames, ArrayList<Float> dataPrices) {
//        this.mInflater = LayoutInflater.from(context);
//        this.mDataNames = dataNames;
//        this.mDataPrices = dataPrices;
//    }
//    // inflates the row layout from xml when needed
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
//        return new ViewHolder(view);
//    }
//
//    // binds the data to the TextView in each row
//    @SuppressLint("SetTextI18n")
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        DecimalFormat df = new DecimalFormat("0.00");
//        df.setMaximumFractionDigits(2);
//
//        String food = mDataNames.get(position);
//        holder.foodName.setText(food);
//        float price = mDataPrices.get(position);
//        String priceToString = df.format(price);
//
//        holder.foodPrice.setText(priceToString + " €");
//    }
//
//    // total number of rows
//    @Override
//    public int getItemCount() {
//        return mDataNames.size();
//    }
//
//
//    // stores and recycles views as they are scrolled off screen
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView foodName;
//        TextView foodPrice;
//        TextView review;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            foodName = itemView.findViewById(R.id.foodName);
//            foodPrice = itemView.findViewById(R.id.foodPrice);
//            review = itemView.findViewById(R.id.TextView);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }
//
//    // convenience method for getting data at click position
//    String getName(int id) {
//        return mDataNames.get(id);
//    }
//
//    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
//
//}
