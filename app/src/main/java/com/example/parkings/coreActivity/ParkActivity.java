package com.example.parkings.coreActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.parkings.R;
import com.example.parkings.adapter.Park_list;
import com.example.parkings.model.Parking;
import com.example.parkings.model.Parks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParkActivity extends AppCompatActivity {
    RecyclerView recycle_park;
    DatabaseReference reference;
    FirebaseUser user;
    List<Parking> list;
    Park_list park_list;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recycle_park = findViewById(R.id.recycle_park);
        user = FirebaseAuth.getInstance().getCurrentUser();
        list = new ArrayList<>();
        recycle_park.setHasFixedSize(true);
        recycle_park.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getData();
    }

    private void getData() {
        reference = FirebaseDatabase.getInstance().getReference("Parks's").child(user.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Parks parks = snapshot.getValue(Parks.class);
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Parking lot").child("Hanoi").child(parks.getId_park());
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Parking parking = snapshot.getValue(Parking.class);
                        assert  parking != null;
                        list.add(parking);
                        park_list = new Park_list(getApplicationContext(),list);
                        recycle_park.setAdapter(park_list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}