package com.example.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class AdminFoodEditFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_adminfoodeditfragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            assert getArguments() != null;
            String arg = getArguments().getString("foodName");//Sets the admin edit fragment's textviews to correspond the correct information
            String arg2 = getArguments().getString("foodPrice");
            String arg3 = getArguments().getString("foodDate");
            EditText fieldFoodName = this.view.findViewById(R.id.fieldFoodName);
            EditText fieldFoodPrice = this.view.findViewById(R.id.fieldFoodPrice);
            EditText fieldFoodDate = this.view.findViewById(R.id.fieldFoodDate);
            fieldFoodName.setText(arg);
            fieldFoodPrice.setText(arg2);
            fieldFoodDate.setText(arg3);
        } catch (Exception e) {
        }
    }
}
