package com.example.parkings.coreActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parkings.R;
import com.example.parkings.model.Parks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {
    TextView log_out, profile_name, edit,parkList;
    EditText name, phone, email, vehicles, plates;
    FirebaseUser fuser;
    DatabaseReference reference;
    View back;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Window window = EditProfile.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(EditProfile.this.getResources().getColor(R.color.green));
        window.setNavigationBarColor(EditProfile.this.getResources().getColor(android.R.color.white));

        //Text View
        log_out = findViewById(R.id.log_out);
        edit = findViewById(R.id.edit);
        profile_name = findViewById(R.id.profile_name);
        parkList= findViewById(R.id.parkList);
        //Edit Text
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        //view
        back = findViewById(R.id.back);
        setEnabled(false);
        //Firebase
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Parks's").child(fuser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Parks user = snapshot.getValue(Parks.class);
                profile_name.setText(user.getUsername());
                name.setText(user.getUsername());
                phone.setText(user.getPhone());
                email.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //set click
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnabled(!name.isEnabled());
                if (name.isEnabled()) {
                    edit.setText("Lưu");
                } else {

                    reference = FirebaseDatabase.getInstance().getReference("Parks's").child(fuser.getUid());
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("email", email.getText().toString());
                    hashMap.put("phone", phone.getText().toString());
                    hashMap.put("username", name.getText().toString());
                    reference.updateChildren(hashMap);
                    edit.setText("Sửa");
                }
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(EditProfile.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile.super.onBackPressed();
            }
        });
    }

    private void setEnabled(boolean clickable) {
        name.setEnabled(clickable);
        phone.setEnabled(clickable);
        email.setEnabled(clickable);
    }
}