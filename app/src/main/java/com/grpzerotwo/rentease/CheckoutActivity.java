package com.grpzerotwo.rentease;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class CheckoutActivity extends AppCompatActivity {

    private TextView checkoutHouseTitle, checkoutHouseLocation, checkoutHousePrice, checkoutNights, checkoutTotalPrice;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        checkoutHouseTitle = findViewById(R.id.checkout_house_title);
        checkoutHouseLocation = findViewById(R.id.checkout_house_location);
        checkoutHousePrice = findViewById(R.id.checkout_house_price);
        checkoutNights = findViewById(R.id.checkout_nights);
        checkoutTotalPrice = findViewById(R.id.checkout_total_price);
        checkoutButton = findViewById(R.id.checkout_button);

        String title = getIntent().getStringExtra("title");
        String location = getIntent().getStringExtra("location");
        int price = getIntent().getIntExtra("price", 0);
        int nights = getIntent().getIntExtra("nights", 0);
        int totalPrice = price * nights;

        checkoutHouseTitle.setText(title);
        checkoutHouseLocation.setText(location);
        checkoutHousePrice.setText("$" + price);
        checkoutNights.setText(String.valueOf(nights));
        checkoutTotalPrice.setText("$" + totalPrice);
    }

}
