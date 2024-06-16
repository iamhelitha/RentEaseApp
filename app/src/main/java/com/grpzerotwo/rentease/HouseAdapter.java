package com.grpzerotwo.rentease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder>{
    Context context;
    ArrayList<House> houseArrayList;

    public HouseAdapter(Context context, ArrayList<House> houseArrayList) {
        this.context = context;
        this.houseArrayList = houseArrayList;
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.house, parent, false);
        return new HouseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        House house = houseArrayList.get(position);
        holder.location.setText(house.getLocation());
        holder.price.setText(String.valueOf(house.getPrice()));
        holder.rooms.setText(String.valueOf(house.getRooms()));
        holder.title.setText(house.getTitle());
        holder.description.setText(house.getDescription());

        Glide.with(context)
             .load(house.getImageUrl())
             .into(holder.houseImage);
    }

    @Override
    public int getItemCount() {
        return houseArrayList.size();
    }

    public static class HouseViewHolder extends RecyclerView.ViewHolder {
        TextView location, price, rooms, title, description;
        ImageView houseImage;

        public HouseViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            rooms = itemView.findViewById(R.id.rooms);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            houseImage = itemView.findViewById(R.id.houseImage);
        }
    }
}