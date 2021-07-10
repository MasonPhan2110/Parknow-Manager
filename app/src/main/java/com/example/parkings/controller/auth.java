package com.example.parkings.controller;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.parkings.coreActivity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class auth {
    private String email, password,username,phone,id_park;
    int count;
    Activity activity;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public auth(String email, String password, Activity activity){
        this.email = email;
        this.password = password;
        this.activity = activity;
    }
    public void signin(){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                check(task.isSuccessful(),"signin");
            }
        });
    }
    public void login_fb(Switch s){
       // AuthCredential authCredential = FacebookAuthProvider.getCredential();

    }
    public void signup(String username,String phone, String id_park, int count){
        this.username = username;
        this.phone = phone;
        this.id_park = id_park;
        this.count = count;
        Log.d("SignupActivity", this.username);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                check(task.isSuccessful(),"signup");
                Log.d("SignupActivity", task.getResult().toString());
            }
        });
    }
    private void check(boolean success, String type){
        if(type == "signin"){
            if(success){
                Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.finish();
            }else{
                Toast.makeText(activity, "Something wrong with Email or Password", Toast.LENGTH_SHORT).show();
            }
        }else{
            if(success){
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String userid = firebaseUser.getUid();
                DatabaseReference reference;
                reference = FirebaseDatabase.getInstance().getReference("Parking lot").child("Hanoi").child(id_park);
                HashMap<String,Object> hashMap1 = new HashMap<>();
                hashMap1.put("Maxslot",count);
                reference.updateChildren(hashMap1);
                reference = FirebaseDatabase.getInstance().getReference("Parks's").child(userid);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id",userid);
                hashMap.put("username",username);
                hashMap.put("email",email);
                hashMap.put("phone",phone);
                hashMap.put("id_park",id_park);
                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    }
                });

            }else {
                Toast.makeText(activity, "Can't not Register with this email", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String safeEmail(String email){
        return email.replace('@', '-').replace('.','-');
    }
}
