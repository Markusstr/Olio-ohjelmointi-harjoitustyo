package com.example.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminEditFragment extends Fragment {
    View view;
    EditText fieldRestaurantName;
    EditText fieldRestaurantAddress;
    EditText fieldRestaurantPC;
    EditText fieldRestaurantCity;
    CheckBox restaurantEnabled;
    Spinner restaurantEditUniSpinner;
    UniversityManager universityManager;
    ArrayList<String> uniNames;
    String arg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admineditfragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        assert getArguments() != null;
        arg = getArguments().getString("restaurantName");//Sets the admin edit fragment's textviews to correspond the correct information
        String arg2 = getArguments().getString("restaurantAddress");
        String arg3 = getArguments().getString("restaurantPC");
        String arg4 = getArguments().getString("restaurantCity");
        boolean arg5 = getArguments().getBoolean("restaurantEnabled");
        int arg6 = getArguments().getInt("restaurantUni");
        fieldRestaurantName = this.view.findViewById(R.id.fieldRestaurantName);
        fieldRestaurantAddress = this.view.findViewById(R.id.fieldRestaurantAddress);
        fieldRestaurantPC = this.view.findViewById(R.id.fieldRestaurantPC);
        fieldRestaurantCity = this.view.findViewById(R.id.fieldRestaurantCity);
        restaurantEnabled = this.view.findViewById(R.id.restaurantEnabled);
        restaurantEditUniSpinner = this.view.findViewById(R.id.restaurantEditUniSpinner);
        universityManager = UniversityManager.getInstance();
        uniNames = universityManager.getUniNames();

        fieldRestaurantName.setText(arg);
        fieldRestaurantAddress.setText(arg2);
        fieldRestaurantPC.setText(arg3);
        fieldRestaurantCity.setText(arg4);
        restaurantEnabled.setChecked(arg5);

        ArrayAdapter<String> adapterUni = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, uniNames);
        adapterUni.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurantEditUniSpinner.setAdapter(adapterUni);

        restaurantEditUniSpinner.setSelection(arg6);
    }

    public String getEditRestaurantName() {
        String editRestaurantNameString = fieldRestaurantName.getText().toString();
        return editRestaurantNameString;
    }

    public String getEditRestaurantAddress() {
        String editRestaurantAddressString = fieldRestaurantAddress.getText().toString();
        return editRestaurantAddressString;
    }

    public String getEditRestaurantPC() {
        String editRestaurantPCString = fieldRestaurantPC.getText().toString();
        return editRestaurantPCString;
    }

    public String getEditRestaurantCity() {
        String editRestaurantCityString = fieldRestaurantCity.getText().toString();
        return editRestaurantCityString;
    }

    public String getEditRestaurantUni() {
        String editRestaurantUni = uniNames.get(restaurantEditUniSpinner.getSelectedItemPosition());
        return editRestaurantUni;
    }

    public boolean getEditRestaurantIsEnabled() {
        boolean editRestaurantIsEnabled = restaurantEnabled.isChecked();
        return editRestaurantIsEnabled;
    }

    public String getEditRestaurantOldName() {
        String oldName = arg;
        return oldName;
    }

}
