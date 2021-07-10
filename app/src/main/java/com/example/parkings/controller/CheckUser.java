package com.example.parkings.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CheckUser {
    FirebaseUser fuser;
    public CheckUser(){}
    public boolean check(){
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(fuser !=null){
            return true;
        }else{
            return false;
        }
    }
}
