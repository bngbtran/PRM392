package com.example.tripbuddy;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tripbuddy.ViewModel.UserViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private ImageView backButton;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.emailEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        backButton = findViewById(R.id.backButton);

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        resetPasswordButton.setOnClickListener(view -> handlePasswordReset());
        backButton.setOnClickListener(view -> navigateToLogin());
    }

    private void handlePasswordReset() {
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
        } else {
            // Observe password retrieval
            userViewModel.getPasswordByEmail(email).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String password) {
                    if (password != null) {
                        sendPasswordEmail(email, password);
                        showPasswordSentDialog();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "No account associated with this email.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendPasswordEmail(String email, String password) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Password Recovery");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your password is: " + password);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPasswordSentDialog() {
        // Inflate and display a custom layout for the dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setView(inflater.inflate(R.layout.dialog_password_reset, null));

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    private void navigateToLogin() {
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
