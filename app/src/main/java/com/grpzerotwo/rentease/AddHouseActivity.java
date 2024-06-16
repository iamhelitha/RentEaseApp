package com.grpzerotwo.rentease;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddHouseActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etTitle, etDescription, etLocation, etPrice, etRooms;
    private ImageView ivHouseImage;
    private Button btnChooseImage, btnAddHouse;
    private Uri imageUri;

    private DatabaseReference databaseHouses;
    private StorageReference storageReference;

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

        databaseHouses = FirebaseDatabase.getInstance().getReference("houses");
        storageReference = FirebaseStorage.getInstance().getReference("house_images");

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnAddHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHouse();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivHouseImage.setImageURI(imageUri);
        }
    }

    private void addHouse() {
        final String title = etTitle.getText().toString().trim();
        final String description = etDescription.getText().toString().trim();
        final String location = etLocation.getText().toString().trim();
        final String price = etPrice.getText().toString().trim();
        final String rooms = etRooms.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(location) ||
                TextUtils.isEmpty(price) || TextUtils.isEmpty(rooms) || imageUri == null) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

        fileReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();

                        House house = new House(title, downloadUrl.toString(), description, location, Integer.parseInt(price), Integer.parseInt(rooms));
                        String houseId = databaseHouses.push().getKey();
                        databaseHouses.child(houseId).setValue(house);

                        Toast.makeText(AddHouseActivity.this, "House added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddHouseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getFileExtension(Uri uri) {
        return getContentResolver().getType(uri).split("/")[1];
    }
}
