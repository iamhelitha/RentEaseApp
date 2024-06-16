package com.grpzerotwo.rentease;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false); // Remove default title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24); // Set custom back icon
        }

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
                    House house = dataSnapshot.getValue(House.class);
                    if (house != null) {
                        houses.add(house);
                        Log.d("FirebaseData", "House: " + house.getLocation());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Handle back button click
            finish();
            return true;
        } else if (id == R.id.action_profile) {
            // Handle profile button click
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_add) {
            // Handle add button click un comment when they are made lol
            // Intent intent = new Intent(this, AddHouseActivity.class);
            // startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
