package com.example.foodreview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminRecyclerViewAdapter extends RecyclerView.Adapter<AdminRecyclerViewAdapter.AdminViewHolder> {
    private ArrayList<Restaurant> mRestaurantList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mAddress;
        ImageView mEdit;
        ImageView mDelete;

        AdminViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mName = itemView.findViewById(R.id.admin_name);
            mAddress = itemView.findViewById(R.id.admin_address);
            mDelete = itemView.findViewById(R.id.admin_delete);
            mEdit = itemView.findViewById(R.id.admin_edit);

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

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }

    AdminRecyclerViewAdapter(ArrayList<Restaurant> restaurantList) {
        mRestaurantList = restaurantList;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adminrecyclerview_row, viewGroup, false);
        return new AdminViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder adminViewHolder, int i) {
        Restaurant currentItem = mRestaurantList.get(i);

        adminViewHolder.mName.setText(currentItem.getRestaurantName());
        adminViewHolder.mAddress.setText(currentItem.getRestaurantAddress() /*TODO: getAddress()*/);

    }

    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }

}
//    private ArrayList<String> mDataNames;
//    private ArrayList<String> mDataAddresses;
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
//
//    // data is passed into the constructor
//    AdminRecyclerViewAdapter(Context context, ArrayList<String> dataNames, ArrayList<String> dataAddresses) {
//        this.mInflater = LayoutInflater.from(context);
//        this.mDataNames = dataNames;
//        this.mDataAddresses = dataAddresses;
//    }
//
//    // inflates the row layout from xml when needed
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.adminrecyclerview_row, parent, false);
//        return new ViewHolder(view);
//    }
//
//    // binds the data to the TextView in each row
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        String food = mDataNames.get(position);
//        holder.name.setText(food);
//        String address = mDataAddresses.get(position);
//        holder.address.setText(address);
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
//        TextView name;
//        TextView address;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.name);
//            address = itemView.findViewById(R.id.address);
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
//    String getPrice(int id) {return mDataAddresses.get(id); }
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

