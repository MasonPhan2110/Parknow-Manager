package com.example.parkings.controller;

import androidx.annotation.NonNull;

import com.example.parkings.model.Parking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListDataPump {
    public static HashMap<String, List<String>> getData(){
        List<Parking> parkings = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Parking lot").child("Hanoi");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                parkings.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    parkings.add(dataSnapshot.getValue(Parking.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        HashMap<String, List<String>> expandListDetail = new HashMap<String, List<String>>();
        List<String> Parkinglot = new ArrayList<>();
//        for(int i = 0; i < parkings.size();i++ ){
//            Parkinglot.add(parkings.get(i).getName()+": "+parkings.get(i).getAddress());
//        }

        Parkinglot.add("Khác...");
        Parkinglot.add("Bãi 2");
        Parkinglot.add("Bãi 3");
        Parkinglot.add("Bãi 4");
        Parkinglot.add("Bãi 5");
        expandListDetail.put("Chọn bãi đỗ xe",Parkinglot);
        return expandListDetail;
    }
}
