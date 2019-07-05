package com.example.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReviewFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reviewfragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            assert getArguments() != null;
            String arg = getArguments().getString("foodName"); //Sets the review fragment's title to correspond the correct food
            TextView foodTitleName = this.view.findViewById(R.id.foodTitleName);
            foodTitleName.setText(arg);
        } catch (Exception e) {
            TextView foodTitleName = this.view.findViewById(R.id.foodTitleName);
            foodTitleName.setText(R.string.fragment_foodTitleNameNull);
        }
    }

}
