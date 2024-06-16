package com.grpzerotwo.rentease;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class HouseDetailsActivity extends AppCompatActivity {

    private ImageView houseImage;
    private TextView houseTitle;
    private TextView houseDescription;
    private TextView houseLocation;
    private TextView housePrice;
    private TextView houseRooms;
    private Button rentButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        houseImage = findViewById(R.id.house_image);
        houseTitle = findViewById(R.id.house_title);
        houseDescription = findViewById(R.id.house_description);
        houseLocation = findViewById(R.id.house_location);
        housePrice = findViewById(R.id.house_price);
        houseRooms = findViewById(R.id.house_rooms);
        rentButton = findViewById(R.id.rent_button);

        House house = (House) getIntent().getSerializableExtra("house");
        if (house != null) {
            houseTitle.setText(house.getTitle());
            houseDescription.setText(house.getDescription());
            houseLocation.setText("Location: " + house.getLocation());
            housePrice.setText("Price per night: $" + house.getPrice());
            houseRooms.setText("Rooms: " + house.getRooms());
            Picasso.get().load(house.getImageUrl()).fit().centerCrop().into(houseImage);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
