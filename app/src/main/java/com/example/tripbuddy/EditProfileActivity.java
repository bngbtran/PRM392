package com.example.tripbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Set default values or get data from intent/ database
        TextView doneButton = findViewById(R.id.doneButton);
        ImageView backButton = findViewById(R.id.backButton);

        doneButton.setOnClickListener(v -> {
            // Save profile changes logic here
            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Add logic to change profile picture
        backButton.setOnClickListener(v -> {
            // Change profile picture logic here
            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
            startActivity(intent);
        });

        TextView changeProfilePicture = findViewById(R.id.changeProfilePicture);

        changeProfilePicture.setOnClickListener(v -> openFileChooser());
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            ImageView profileImage = findViewById(R.id.profileImage);
            profileImage.setImageURI(imageUri);
        }
    }
}
