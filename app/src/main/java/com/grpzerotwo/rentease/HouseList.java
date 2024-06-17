package com.grpzerotwo.rentease;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

public class HouseList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HouseAdapter houseAdapter;
    private List<House> houseList;
    private DatabaseReference databaseReference;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.houseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        houseList = new ArrayList<>();
        houseAdapter = new HouseAdapter(houseList, this);
        recyclerView.setAdapter(houseAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("houses");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                houseList.clear();
                for (DataSnapshot houseSnapshot : snapshot.getChildren()) {
                    String houseId = houseSnapshot.getKey();
                    House house = houseSnapshot.getValue(House.class);
                    if (house != null) {
                        house.setId(houseId);
                        houseList.add(house);
                    }
                }
                houseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
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

        if (id == R.id.action_filter) {
            // Handle filter action
            return true;
        } else if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddHouseActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_profile) {
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
