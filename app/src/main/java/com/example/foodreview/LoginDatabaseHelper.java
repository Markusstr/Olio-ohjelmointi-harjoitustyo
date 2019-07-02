package com.example.foodreview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.foodreview.UserIdContract.*;

public class LoginDatabaseHelper extends SQLiteOpenHelper {
    // private static final String TAG = "DatabaseManager";
    private static final String DATABASE_NAME = "DatabaseFood";
    private static final int DATABASE_VERSION = 1;

    LoginDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USERID_DATABASE = "CREATE TABLE " +
                newUserId.TABLE_NAME + " (" +
                newUserId.COLUMN_USERNAME + " VARCHAR(32) PRIMARY KEY NOT NULL, " +
                newUserId.COLUMN_PASSWORD + " VARCHAR(64) NOT NULL, " +
                newUserId.COLUMN_SALT + " BLOB NOT NULL );";
        db.execSQL(SQL_CREATE_USERID_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + newUserId.TABLE_NAME);
        onCreate(db);
    }
}