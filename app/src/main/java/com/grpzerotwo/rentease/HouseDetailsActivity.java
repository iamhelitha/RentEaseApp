package com.grpzerotwo.rentease;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;

public class HouseDetailsActivity extends AppCompatActivity {

    private TextView houseTitle, houseDescription, houseLocation, housePrice, houseRooms;
    private ImageView houseImage;
    private EditText nightsInput, dateInput;
    private Button rentButton;
    private ImageView btnPickDate;
    private DatabaseReference databaseReference;
    private String houseId;
    private int price;

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
        dateInput = findViewById(R.id.date_input);
        rentButton = findViewById(R.id.rent_button);
        btnPickDate = findViewById(R.id.btn_pick_date);  // Initialize the date picker icon

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
                    price = house.getPrice(); // Get the price for later use
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        btnPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    HouseDetailsActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        // Handle date selection
                        String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        dateInput.setText(selectedDate);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        rentButton.setOnClickListener(v -> {
            String title = houseTitle.getText().toString();
            String location = houseLocation.getText().toString();
            int nights = Integer.parseInt(nightsInput.getText().toString());

            Intent intent = new Intent(HouseDetailsActivity.this, CheckoutActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("location", location);
            intent.putExtra("price", price);
            intent.putExtra("nights", nights);
            startActivity(intent);
        });
    }
}
