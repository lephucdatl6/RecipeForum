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
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.checkUserPass(user, pass)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                // Get the user's email from DB
                String email = db.getEmail(user);
                if (email == null) {
                    Toast.makeText(this, "Email not found for user", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Send 2FA Code
                EmailSender.send2FACode(email, code -> runOnUiThread(() -> {
                    TwoFactorDialog.show(LoginActivity.this, enteredCode -> {
                        if (enteredCode.equalsIgnoreCase(code)) {
                            Intent intent = new Intent(LoginActivity.this, ForumActivity.class);
                            intent.putExtra("username", user);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Invalid 2FA code", Toast.LENGTH_SHORT).show();
                        }
                    });
                }));

            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        signupRedirect.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));
    }
}

