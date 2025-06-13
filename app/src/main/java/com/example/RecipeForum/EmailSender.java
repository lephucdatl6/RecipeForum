package com.example.RecipeForum;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;


public class EmailSender {
    // Welcome Email
    public static void sendWelcomeEmail(String userEmail, String username) {
        new Thread(() -> {
            try {
                Log.d("EmailSender", "Sending Welcome Email to: " + userEmail + " (Username: " + username + ")");

                URL url = new URL("http://192.168.20.108/sendemail/send_email.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String postData = "email=" + URLEncoder.encode(userEmail, "UTF-8")
                        + "&username=" + URLEncoder.encode(username, "UTF-8");

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(postData.getBytes());
                    os.flush();
                }

                // log the server response
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                Log.d("EmailSender", "Welcome Email Response: " + response.toString());

                conn.disconnect();
            } catch (Exception e) {
                Log.e("EmailSender", "Error sending Welcome Email: " + e.getMessage(), e);
            }
        }).start();
    }


    // 2FA Code Email
    public interface OnCodeGeneratedListener {
        void onCodeGenerated(String code);
    }

    public static void send2FACode(String userEmail, OnCodeGeneratedListener listener) {
        new Thread(() -> {
            try {
                String code = generateRandomCode(4);
                Log.d("EmailSender", "Sending 2FA to: " + userEmail + " with code: " + code);

                if (listener != null) {
                    listener.onCodeGenerated(code);
                }

                URL url = new URL("http://192.168.20.108/sendemail/send_2fa.php");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String postData = "email=" + URLEncoder.encode(userEmail, "UTF-8")
                        + "&code=" + URLEncoder.encode(code, "UTF-8");

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(postData.getBytes());
                    os.flush();
                }

                // log the server response
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                Log.d("EmailSender", "2FA Response: " + response.toString());

                conn.disconnect();
            } catch (Exception e) {
                Log.e("EmailSender", "Error sending 2FA: " + e.getMessage(), e);
            }
        }).start();
    }

    private static String generateRandomCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
}

