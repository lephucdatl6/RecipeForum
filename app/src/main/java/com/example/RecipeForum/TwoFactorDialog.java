package com.example.RecipeForum;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.widget.EditText;

public class TwoFactorDialog {

    public interface OnCodeEnteredListener {
        void onCodeEntered(String code);
    }

    public static void show(Context context, OnCodeEnteredListener listener) {
        EditText input = new EditText(context);
        input.setHint("Enter 4-digit code");
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(context)
                .setTitle("2FA Verification")
                .setMessage("We’ve sent a 4-digit code to your email.")
                .setView(input)
                .setCancelable(false)
                .setPositiveButton("Verify", (dialog, which) -> {
                    String code = input.getText().toString().trim();
                    listener.onCodeEntered(code);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
