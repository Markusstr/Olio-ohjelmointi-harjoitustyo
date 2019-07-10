package com.example.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminFoodEditFragment extends Fragment {
    View view;
    EditText fieldFoodName;
    EditText fieldFoodPrice;
    EditText fieldFoodDate;
    int arg5;
    String arg4;

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
            arg4 = getArguments().getString("foodUni");
            arg5 = getArguments().getInt("selectedFood");
            fieldFoodName = this.view.findViewById(R.id.fieldFoodName);
            fieldFoodPrice = this.view.findViewById(R.id.fieldFoodPrice);
            fieldFoodDate = this.view.findViewById(R.id.fieldFoodDate);

            fieldFoodName.setText(arg);
            fieldFoodPrice.setText(arg2);
            fieldFoodDate.setText(arg3);
        } catch (Exception e) {
        }
    }

    public String getEditFoodName() {
        String editFoodNameText = fieldFoodName.getText().toString();
        return editFoodNameText;
    }

    public float getEditFoodPrice() {
        float editFoodPriceFloat = Float.parseFloat(fieldFoodPrice.getText().toString());
        return editFoodPriceFloat;
    }

    public String getEditFoodDate() {
        String editFoodDateText = fieldFoodDate.getText().toString();
        return editFoodDateText;
    }

    public String getFoodUni() {
        return arg4;
    }

    public int getSelectedFood() {
        return arg5;
    }


}
