package com.grpzerotwo.rentease;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder> {

    private List<House> houseList;
    private Context context;

    public HouseAdapter(List<House> houseList, Context context) {
        this.houseList = houseList;
        this.context = context;
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_house, parent, false);
        return new HouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        House house = houseList.get(position);
        holder.title.setText(house.getTitle());
        holder.description.setText(house.getDescription());
        holder.location.setText(house.getLocation());
        holder.price.setText("$" + house.getPrice());
        holder.rooms.setText(String.valueOf(house.getRooms()));
        Glide.with(context).load(house.getImageUrl()).into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HouseDetailsActivity.class);
            intent.putExtra("houseId", house.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public static class HouseViewHolder extends RecyclerView.ViewHolder {

        public TextView title, description, location, price, rooms;
        public ImageView image;

        public HouseViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            rooms = itemView.findViewById(R.id.rooms);
            image = itemView.findViewById(R.id.image);
        }
    }
}
