package com.grpzerotwo.rentease;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HouseDetailsActivity extends AppCompatActivity {

    private TextView houseTitle, houseDescription, houseLocation, housePrice, houseRooms;
    private ImageView houseImage;
    private EditText nightsInput;
    private Button rentButton;
    private DatabaseReference databaseReference;
    private String houseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);

        houseTitle = findViewById(R.id.house_title);
        houseDescription = findViewById(R.id.house_description);
        houseLocation = findViewById(R.id.house_location);
        housePrice = findViewById(R.id.house_price);
        houseRooms = findViewById(R.id.house_rooms);
        houseImage = findViewById(R.id.house_image);
        nightsInput = findViewById(R.id.nights_input);
        rentButton = findViewById(R.id.rent_button);

        houseId = getIntent().getStringExtra("houseId");
        databaseReference = FirebaseDatabase.getInstance().getReference("houses").child(houseId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                House house = snapshot.getValue(House.class);
                if (house != null) {
                    houseTitle.setText(house.getTitle());
                    houseDescription.setText(house.getDescription());
                    houseLocation.setText(house.getLocation());
                    housePrice.setText("$" + house.getPrice());
                    houseRooms.setText(String.valueOf(house.getRooms()));
                    Glide.with(HouseDetailsActivity.this).load(house.getImageUrl()).into(houseImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        rentButton.setOnClickListener(v -> {
            // Handle rent logic here
        });
    }
}
