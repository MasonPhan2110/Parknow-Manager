package com.example.parkings.controller;

import android.app.Activity;
import android.content.Intent;

import com.example.parkings.coreActivity.MainActivity;

public class ChangeActivity {
    public ChangeActivity(){}
    public void change(Activity fromActivity, Class toActivity, int type){
        Intent intent = new Intent(fromActivity.getApplicationContext(),toActivity);
        fromActivity.startActivity(intent);
        if(type == 1){
            fromActivity.finish();
        }
    }
}
