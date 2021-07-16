package com.example.parkings.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkings.R;
import com.example.parkings.adapter.UserAdapter;
import com.example.parkings.model.Chatlist;
import com.example.parkings.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class conversationFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<Users> mUser;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference;
    private List<Chatlist> mchatList;
    final List<String> parkid = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mchatList = new ArrayList<>();
        getparkid();
        for (int i = 0; i < parkid.size(); i++) {
            Log.d("parkid", parkid.get(i));
        }
//        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(parkid);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mchatList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Chatlist chatlist = dataSnapshot.getValue(Chatlist.class);
//                    mchatList.add(chatlist);
//                }
//                chatList();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        return view;
    }

    private void getparkid() {
        reference = FirebaseDatabase.getInstance().getReference("Parks's").child(firebaseUser.getUid()).child("Park");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                parkid.clear();
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                List<String> key = new ArrayList<>(hashMap.keySet());
                for (int i = 0; i < key.size(); i++) {
                    parkid.add(key.get(i));
                }
                getchatList(parkid);
                Log.d("parkid", String.valueOf(parkid.size()));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    private void getchatList(List<String> parkid){
        for( String id : parkid){
            reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(id);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mchatList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Chatlist chatlist = dataSnapshot.getValue(Chatlist.class);
                        mchatList.add(chatlist);
                    }
                    chatList(mchatList,id);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    private void chatList(List<Chatlist> mchatList, String parkid) {
        mUser = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUser.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    for (Chatlist chatlist : mchatList) {
                        if (users.getId().equals(chatlist.getId())) {
                            mUser.add(users);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUser, true, parkid);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}