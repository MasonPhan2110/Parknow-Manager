package com.example.parkings.coreActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkings.R;
import com.example.parkings.adapter.CustomExpandableListAdapter;
import com.example.parkings.controller.ListDataPump;
import com.example.parkings.controller.auth;
import com.example.parkings.model.Parking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    ExpandableListView list_parking;
    List<String> listTitle;
    HashMap<String, List<String>> listDetail;
    ExpandableListAdapter listAdapter;
    EditText email, username, phone, pass, count;
    TextView signup;
    int Parkposition = 0;
    DatabaseReference reference;
    List<Parking> parkings;
    List<String> Parkings;
    HashMap<String, List<String>> expandListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Window window = SignupActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(SignupActivity.this.getResources().getColor(R.color.green));
        window.setNavigationBarColor(SignupActivity.this.getResources().getColor(R.color.green));

        //Edit Text
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        count = findViewById(R.id.count);
        //TextView
        signup = findViewById(R.id.signup);
        list_parking = findViewById(R.id.list_parking);

        expandListDetail = new HashMap<String, List<String>>();
        parkings = new ArrayList<>();
        Parkings = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Parking lot").child("Hanoi");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    parkings.add(dataSnapshot.getValue(Parking.class));
                }

                for (int i = 0; i < parkings.size(); i++) {
                    Parkings.add(parkings.get(i).getName() + " - " + parkings.get(i).getAddress());
                }
                Parkings.add("Khác...");
                expandListDetail.put("Chọn bãi đỗ xe", Parkings);
                listDetail = expandListDetail;
                listTitle = new ArrayList<>(listDetail.keySet());
                listAdapter = new CustomExpandableListAdapter(getApplicationContext(), listTitle, listDetail);
                list_parking.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        list_parking.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int dp = 300;
                int px = (int) (dp * getResources().getDisplayMetrics().density);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        px
                );
                layoutParams.setMargins(0, (int) (15 * getResources().getDisplayMetrics().density), 0, 0);
                list_parking.setLayoutParams(layoutParams);
            }
        });
        list_parking.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                int dp = 50;
                int px = (int) (dp * getResources().getDisplayMetrics().density);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        px
                );
                layoutParams.setMargins(0, (int) (15 * getResources().getDisplayMetrics().density), 0, 0);
                list_parking.setLayoutParams(layoutParams);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth fauth = new auth(email.getText().toString(), pass.getText().toString(), SignupActivity.this);
                fauth.signup(username.getText().toString(), phone.getText().toString(), parkings.get(Parkposition).getID(),Integer.valueOf(count.getText().toString()));
            }
        });
        list_parking.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Parkposition = childPosition;
                list_parking.collapseGroup(groupPosition);
                expandListDetail.clear();
                expandListDetail.put(parkings.get(childPosition).getName() + " - "+parkings.get(childPosition).getAddress(),Parkings);
                listDetail = expandListDetail;
                listTitle = new ArrayList<>(listDetail.keySet());
                listAdapter = new CustomExpandableListAdapter(getApplicationContext(), listTitle, listDetail);
                list_parking.setAdapter(listAdapter);
                return false;
            }
        });
    }
}