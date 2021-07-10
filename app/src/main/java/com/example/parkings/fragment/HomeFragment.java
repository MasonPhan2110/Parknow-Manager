package com.example.parkings.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.parkings.R;
import com.example.parkings.controller.ChangeActivity;
import com.example.parkings.coreActivity.AnalystActivity;
import com.example.parkings.coreActivity.CarActivity;
import com.example.parkings.coreActivity.ParkActivity;
import com.example.parkings.coreActivity.ReveneuActivity;
import com.example.parkings.coreActivity.SaleActivity;

public class HomeFragment extends Fragment {
    RelativeLayout park_list,car_list,sale_list,reveneu_list,analyst_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        park_list = view.findViewById(R.id.park_list);
        car_list = view.findViewById(R.id.car_list);
        sale_list = view.findViewById(R.id.sale_list);
        reveneu_list = view.findViewById(R.id.reveneu_list);
        analyst_list = view.findViewById(R.id.analyst_list);
        int dp = 10;
        int px = (int) (dp*getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(
                (width-3*px)/2,
                height/7
        );
        layoutParams.setMargins(px,px,0,0);
        park_list.setLayoutParams(layoutParams);
        car_list.setLayoutParams(layoutParams);
        sale_list.setLayoutParams(layoutParams);
        reveneu_list.setLayoutParams(layoutParams);
        analyst_list.setLayoutParams(layoutParams);
        ChangeActivity changeActivity = new ChangeActivity();
        park_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity.change(getActivity(), ParkActivity.class,0);
            }
        });
        car_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity.change(getActivity(), CarActivity.class,0);
            }
        });
        sale_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity.change(getActivity(), SaleActivity.class,0);
            }
        });
        reveneu_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity.change(getActivity(), ReveneuActivity.class,0);
            }
        });
        analyst_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity.change(getActivity(), AnalystActivity.class,0);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}