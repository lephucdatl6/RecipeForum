package com.example.RecipeForum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button loginBtn, signupRedirect;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_btn);
        signupRedirect = findViewById(R.id.signup_redirect_btn);
        db = new DBHelper(this);

        loginBtn.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();

            if (db.checkUserPass(user, pass)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                // Redirect to forum page
                Intent intent = new Intent(LoginActivity.this, ForumActivity.class);
                intent.putExtra("username", user);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        signupRedirect.setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
        });
    }
}
