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

import com.squareup.picasso.Picasso;

import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder> {
    private Context mContext;
    private List<House> mHouses;

    public HouseAdapter(Context context, List<House> houses) {
        mContext = context;
        mHouses = houses;
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_house, parent, false);
        return new HouseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        House currentHouse = mHouses.get(position);
        holder.title.setText(currentHouse.getTitle());
        if (currentHouse.getImageUrl() != null && !currentHouse.getImageUrl().isEmpty()) {
            Picasso.get().load(currentHouse.getImageUrl()).fit().centerCrop().into(holder.image);
        }
        holder.description.setText(currentHouse.getDescription());
        holder.location.setText(currentHouse.getLocation());
        holder.price.setText(String.valueOf(currentHouse.getPrice()));
        holder.rooms.setText(String.valueOf(currentHouse.getRooms()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HouseDetailsActivity.class);
                intent.putExtra("house", currentHouse);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHouses.size();
    }

    public class HouseViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public TextView description;
        public TextView location;
        public TextView price;
        public TextView rooms;

        public HouseViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            description = itemView.findViewById(R.id.description);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            rooms = itemView.findViewById(R.id.rooms);
        }
    }
}
