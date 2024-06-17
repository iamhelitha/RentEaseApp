package com.grpzerotwo.rentease;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.UUID;

public class AddHouseActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etLocation, etPrice, etRooms;
    private ImageView ivHouseImage;
    private Button btnChooseImage, btnAddHouse;
    private Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);

        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        etLocation = findViewById(R.id.et_location);
        etPrice = findViewById(R.id.et_price);
        etRooms = findViewById(R.id.et_rooms);
        ivHouseImage = findViewById(R.id.iv_house_image);
        btnChooseImage = findViewById(R.id.btn_choose_image);
        btnAddHouse = findViewById(R.id.btn_add_house);
        progressBar = findViewById(R.id.progressBar);

        storageReference = FirebaseStorage.getInstance().getReference("house_images");
        databaseReference = FirebaseDatabase.getInstance().getReference("houses");

        btnChooseImage.setOnClickListener(v -> chooseImage());

        btnAddHouse.setOnClickListener(v -> addHouse());
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivHouseImage.setImageURI(imageUri);
        }
    }

    private void addHouse() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String rooms = etRooms.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(location) ||
                TextUtils.isEmpty(price) || TextUtils.isEmpty(rooms) || imageUri == null) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        StorageReference fileReference = storageReference.child(UUID.randomUUID().toString());
        fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
            String houseId = databaseReference.push().getKey();
            House house = new House(title, description, location, Integer.parseInt(price), Integer.parseInt(rooms), uri.toString());
            if (houseId != null) {
                house.setId(houseId);
                databaseReference.child(houseId).setValue(house).addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(AddHouseActivity.this, "House added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddHouseActivity.this, "Failed to add house", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        })).addOnFailureListener(e -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AddHouseActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
        });
    }
}
