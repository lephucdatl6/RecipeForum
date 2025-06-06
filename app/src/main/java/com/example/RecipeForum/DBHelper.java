package com.example.RecipeForum;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT, " +
                "dob TEXT, " +
                "phone TEXT, " +
                "email TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS users");
    }

    public boolean insertUser(String username, String password, String dob, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("dob", dob);
        values.put("phone", phone);
        values.put("email", email);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    public String getDob(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT dob FROM users WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            String dob = cursor.getString(0);
            cursor.close();
            return dob;
        }
        cursor.close();
        return null;
    }

    public String getPhone(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT phone FROM users WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            String phone = cursor.getString(0);
            cursor.close();
            return phone;
        }
        cursor.close();
        return null;
    }

    public String getEmail(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT email FROM users WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            String email = cursor.getString(0);
            cursor.close();
            return email;
        }
        cursor.close();
        return null;
    }



    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public boolean checkUserPass(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    public boolean checkPhone(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE phone = ?", new String[]{phone});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


}


