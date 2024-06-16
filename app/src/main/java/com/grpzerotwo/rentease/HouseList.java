package com.grpzerotwo.rentease;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HouseList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    HouseAdapter adapter;
    ArrayList<House> houses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list);

        recyclerView = findViewById(R.id.houseList);
        database = FirebaseDatabase.getInstance().getReference("houses");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        houses = new ArrayList<>();
        adapter = new HouseAdapter(this, houses);
        recyclerView.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                houses.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        House house = dataSnapshot.getValue(House.class);
                        if (house != null) {
                            houses.add(house);
                        }
                    } catch (DatabaseException e) {
                        Log.e("FirebaseError", "Error converting data: " + e.getMessage());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error: " + error.getMessage());
            }

        });
    }
}
