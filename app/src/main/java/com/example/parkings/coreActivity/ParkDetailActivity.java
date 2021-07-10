package com.example.parkings.coreActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.parkings.R;
import com.example.parkings.model.Parking;
import com.example.parkings.model.Parks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ParkDetailActivity extends AppCompatActivity {
    TextView cancel,Edit,name;
    EditText loc,maxslot,count,parks_name;
    DatabaseReference reference;
    Intent intent;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_detail);
        cancel = findViewById(R.id.cancel);
        Edit = findViewById(R.id.Edit);
        loc = findViewById(R.id.loc);
        maxslot = findViewById(R.id.maxslot);
        count = findViewById(R.id.count);
        parks_name = findViewById(R.id.parks_name);
        name = findViewById(R.id.name);
        setEnabled(false);
        intent = getIntent();
        ID = intent.getStringExtra("ID");

        reference = FirebaseDatabase.getInstance().getReference("Parking lot").child("Hanoi").child(ID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Parking park = snapshot.getValue(Parking.class);
                name.setText(park.getName());
                loc.setText(park.getAddress());
                maxslot.setText(String.valueOf(park.getMaxslot()));
                parks_name.setText(park.getName());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParkDetailActivity.super.onBackPressed();
                finish();
            }
        });
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnabled(!parks_name.isEnabled());
                if (parks_name.isEnabled()) {
                    Edit.setText("Lưu");
                } else {
                    reference = FirebaseDatabase.getInstance().getReference("Parking lot").child("Hanoi").child(ID);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Name", parks_name.getText().toString());
                    hashMap.put("Maxslot", maxslot.getText().toString());
                    //reference.updateChildren(hashMap);
                    finish();
                    startActivity(getIntent());

                    //Edit.setText("Sửa");
                }
            }
        });
    }
    private void setEnabled(boolean clickable) {
        maxslot.setEnabled(clickable);
        parks_name.setEnabled(clickable);
    }
}