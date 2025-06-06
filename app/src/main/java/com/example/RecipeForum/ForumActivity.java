package com.example.RecipeForum;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ForumActivity extends AppCompatActivity {

    TextView usernameText, dobText, phoneText, emailText;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        usernameText = findViewById(R.id.username_text);
        dobText = findViewById(R.id.dob_text);
        phoneText = findViewById(R.id.phone_text);
        emailText = findViewById(R.id.email_text);
        db = new DBHelper(this);

        String username = getIntent().getStringExtra("username");
        String dob = db.getDob(username);
        String phone = db.getPhone(username);
        String email = db.getEmail(username);

        usernameText.setText("Username: " + username);
        dobText.setText("Date of Birth: " + (dob != null ? dob : "Not Found"));
        phoneText.setText("Phone: " + (phone != null ? phone : "Not Found"));
        emailText.setText("Email: " + (email != null ? email : "Not Found"));
    }
}

