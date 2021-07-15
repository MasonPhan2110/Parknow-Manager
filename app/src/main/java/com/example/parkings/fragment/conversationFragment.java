package com.example.parkings.fragment;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class conversationFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<Users> mUser;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private List<Chatlist> mchatList;
    Intent intent;
    String parkid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        parkid = intent.getStringExtra("parkid");
        mchatList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(parkid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mchatList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Chatlist chatlist = dataSnapshot.getValue(Chatlist.class);
                    mchatList.add(chatlist);
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
    private void chatList() {
        mUser = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUser.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    for (Chatlist chatlist : mchatList){
                        if (users.getId().equals(chatlist.getId())){
                            mUser.add(users);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(),mUser, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}