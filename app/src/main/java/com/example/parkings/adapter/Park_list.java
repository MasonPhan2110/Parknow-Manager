package com.example.parkings.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkings.coreActivity.ParkDetailActivity;
import com.example.parkings.R;
import com.example.parkings.model.Parking;

import java.util.List;

import static android.content.Context.WINDOW_SERVICE;

public class Park_list extends RecyclerView.Adapter<Park_list.ViewHolder> {
    Context mcontext;
    List<Parking> parks;
    View view;
    public Park_list(Context mcontext, List<Parking> parks){
        this.mcontext = mcontext;
        this.parks = parks;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.item_park_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Parking park ;
        park = parks.get(position);
        holder.name.setText(park.getName());
        holder.loc.setText(park.getAddress());
        holder.maxslot.setText("Số lương tối đa: "+park.getMaxslot());
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickitem(park.getID());
            }
        });
    }

    private void clickitem(String id) {
        Intent intent = new Intent(mcontext, ParkDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ID",id);
        mcontext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return parks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, loc, maxslot;
        RelativeLayout relative;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            loc = itemView.findViewById(R.id.loc);
            maxslot = itemView.findViewById(R.id.maxslot);
            relative = itemView.findViewById(R.id.relative);
        }
    }
}
