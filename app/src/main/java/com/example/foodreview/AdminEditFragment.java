package com.example.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdminEditFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admineditfragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            assert getArguments() != null;
            String arg = getArguments().getString("restaurantName");//Sets the admin edit fragment's textviews to correspond the correct information
            String arg2 = getArguments().getString("restaurantAddress");
            TextView restaurantName = this.view.findViewById(R.id.restaurantName);
            TextView restaurantAddress = this.view.findViewById(R.id.restaurantAddress);
            restaurantName.setText(arg);
            restaurantAddress.setText(arg2);
        } catch (Exception e) {
            TextView restaurantName = this.view.findViewById(R.id.restaurantName);
            restaurantName.setText(R.string.fragment_foodTitleNameNull);
        }
    }
}
