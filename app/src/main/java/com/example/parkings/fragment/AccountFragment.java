package com.example.parkings.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.parkings.R;
import com.example.parkings.coreActivity.EditProfile;

public class AccountFragment extends Fragment {
    RelativeLayout setting;
    TextView edit_profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        setting = view.findViewById(R.id.settting);
        edit_profile = view.findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SettingActivity.class);
//                startActivity(intent);
            }
        });
        return view;
    }
}