package com.example.RecipeForum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class SignupActivity extends AppCompatActivity {

    Spinner monthSpinner, daySpinner, yearSpinner;
    EditText username, password, phone, email;
    Button signup, returnLogin;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        username = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);
        phone = findViewById(R.id.phone__input);
        email = findViewById(R.id.email__input);
        signup = findViewById(R.id.signup_btn);
        returnLogin = findViewById(R.id.btn_return_login);

        monthSpinner = findViewById(R.id.month_spinner);
        daySpinner = findViewById(R.id.day_spinner);
        yearSpinner = findViewById(R.id.year_spinner);
        db = new DBHelper(this);

        setupSpinners();

        // Signup Button Logic
        signup.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String ph = phone.getText().toString().trim();
            String em = email.getText().toString().trim();
            String month = monthSpinner.getSelectedItem().toString();
            String day = daySpinner.getSelectedItem().toString();
            String year = yearSpinner.getSelectedItem().toString();

            if (user.isEmpty() || pass.isEmpty() || ph.isEmpty() || em.isEmpty() ||
                    month.equals("Select") || day.equals("Select") || year.equals("Select")) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                String dob = day + "/" + month + "/" + year;

                if (db.checkUsername(user)) {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                } else if (db.checkPhone(ph)) {
                    Toast.makeText(this, "Phone number already used", Toast.LENGTH_SHORT).show();
                } else if (db.checkEmail(em)) {
                    Toast.makeText(this, "Email already used", Toast.LENGTH_SHORT).show();
                } else {
                    boolean insert = db.insertUser(user, pass, dob, ph, em);
                    if (insert) {
                        Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                    } else {
                        Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Back to Login Button Logic
        returnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setupSpinners() {
        // Year Spinner
        List<String> years = new ArrayList<>();
        years.add("Select");
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 1900; i--) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        // Month Spinner
        List<String> months = Arrays.asList("Select", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);


        // Day Spinner
        List<String> days = new ArrayList<>();
        days.add("Select");
        for (int i = 1; i <= 31; i++) {
            days.add(String.valueOf(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
    }
}
