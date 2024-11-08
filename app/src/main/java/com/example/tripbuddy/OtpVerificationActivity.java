package com.example.tripbuddy;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OtpVerificationActivity extends AppCompatActivity {

    Button verifyButton;
    ImageView backButton;
    String email;
    TextView emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        email = getIntent().getStringExtra("email");

        verifyButton = findViewById(R.id.verifyButton);
        backButton = findViewById(R.id.backButton);
        emailView = findViewById(R.id.otpSubText);
        emailView.setText(String.format("Please check %s for the OTP code.", email));
        // Handle Verify Button click
        verifyButton.setOnClickListener(view -> {
            // Add logic to verify the OTP code
            Toast.makeText(OtpVerificationActivity.this, "OTP Verified!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OtpVerificationActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(OtpVerificationActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
}
