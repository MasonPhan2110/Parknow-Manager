package com.example.parkings.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkings.R;
import com.example.parkings.coreActivity.MessageActivity;
import com.example.parkings.model.Chat;
import com.example.parkings.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<Users> mUser;
    private boolean ischat;
    String thelastmsg;

    public UserAdapter(Context context, List<Users> mUser, boolean ischat) {
        this.context = context;
        this.mUser = mUser;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Users users = mUser.get(position);
        holder.user_name.setText(users.getUsername());
        if (ischat) {
            lastMessage(users.getId(), holder.last_msg);
        } else {
            holder.last_msg.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", users.getId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name;
        public TextView last_msg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            last_msg = itemView.findViewById(R.id.lastmessage);
        }
    }

    private void lastMessage(final String userid, final TextView last_msg) {
        thelastmsg = "";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid)
                            || chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                        thelastmsg = chat.getMessage();
                    }
                    if (!chat.isIsseen() && chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid)) {
                        last_msg.setTypeface(null, Typeface.BOLD);
                    } else {
                        last_msg.setTypeface(null, Typeface.NORMAL);
                    }
                }
                switch (thelastmsg) {
                    case "":
                        last_msg.setText("No Message");
                        break;
                    default:
                        last_msg.setText(thelastmsg);
                        break;
                }
                thelastmsg = "";

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
