package com.example.foodreview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.foodreview.UserIdContract.*;

public class LoginDatabaseHelper extends SQLiteOpenHelper {
    // private static final String TAG = "DatabaseManager";
    private static final String DATABASE_NAME = "DatabaseFood";
    private static final int DATABASE_VERSION = 1;

    // Constructor makes a new database with the arguments given above.
    LoginDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This method is called when the database has first been created. Creates the Table and attributes.
    // Every final string, is an sql "create Table" -clause.
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USERID_DATABASE = "CREATE TABLE " +
                tableUserIds.TABLE_NAME + " (" +
                tableUserIds.COLUMN_USERNAME + " TEXT PRIMARY KEY NOT NULL, " +
                tableUserIds.COLUMN_PASSWORD + " TEXT NOT NULL, " +
                tableUserIds.COLUMN_SALT + " BLOB NOT NULL, " +
                tableUserIds.COLUMN_ADMIN + " INTEGER NOT NULL CHECK (" +
                tableUserIds.COLUMN_ADMIN + " = 1 or (" +
                tableUserIds.COLUMN_ADMIN +
                " = 0)));";
        db.execSQL(SQL_CREATE_USERID_DATABASE);

        final String SQL_CREATE_UNIVERSITY_TABLE = "CREATE TABLE " + tableUniversity.TABLE_NAME +" (" +
                tableUniversity.COLUMN_UNIID + " INTEGER NOT NULL PRIMARY KEY, " +
                tableUniversity.COLUMN_UNINAME + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_UNIVERSITY_TABLE);

        final String SQL_CREATE_ADDRESS_TABLE = "CREATE TABLE "+tableAddresses.TABLE_NAME+" (" +
                tableAddresses.COLUMN_ADDRESSID + " INTEGER NOT NULL PRIMARY KEY," +
                tableAddresses.COLUMN_ADDRESS + " TEXT NOT NULL," +
                tableAddresses.COLUMN_POSTALCODE + " INTEGER NOT NULL," +
                tableAddresses.COLUMN_CITY + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_ADDRESS_TABLE);

        final String SQL_CREATE_RESTAURANT_TABLE = "CREATE TABLE " +tableRestaurant.TABLE_NAME+" (" +
                tableRestaurant.COLUMN_RESTAURANTID	+ " INTEGER NOT NULL PRIMARY KEY," +
                tableRestaurant.COLUMN_RESTAURANTNAME +" TEXT NOT NULL," +
                tableRestaurant.COLUMN_ADDRESSID + " INTEGER,"+
                tableRestaurant.COLUMN_UNIID + " INTEGER,"+
                "FOREIGN KEY("+tableRestaurant.COLUMN_UNIID+") REFERENCES "+tableUniversity.TABLE_NAME+"("+tableUniversity.COLUMN_UNIID+") ON UPDATE CASCADE,"+
                "FOREIGN KEY("+tableRestaurant.COLUMN_ADDRESSID+") REFERENCES "+tableAddresses.TABLE_NAME+"("+tableAddresses.COLUMN_ADDRESSID+") ON UPDATE CASCADE);";

        db.execSQL(SQL_CREATE_RESTAURANT_TABLE);

        final String SQL_CREATE_CHEF_TABLE = "CREATE TABLE "+tableChef.TABLE_NAME +" ("+
                tableChef.COLUMN_CHEFID+ " INTEGER NOT NULL PRIMARY KEY,"+
                tableChef.COLUMN_FIRSTNAME+ " TEXT NOT NULL,"+
                tableChef.COLUMN_LASTNAME+ " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_CHEF_TABLE);
        final String SQL_CREATE_FOOD_TABLE = "CREATE TABLE "+tableFood.TABLE_NAME+" (" +
                tableFood.COLUMN_FOODID +" INTEGER NOT NULL PRIMARY KEY,"+
                tableFood.COLUMN_FOODPRICE+" REAL,"+
                tableFood.COLUMN_FOODNAME + " TEXT NOT NULL,"+
                tableFood.COLUMN_DATE + " TEXT NOT NULL,"+
                tableFood.COLUMN_RESTAURANTID+ " INTEGER,"+
                tableFood.COLUMN_CHEFID+" INTEGER,"+
                "FOREIGN KEY("+tableFood.COLUMN_CHEFID+") REFERENCES "+ tableChef.TABLE_NAME+"("+tableChef.COLUMN_CHEFID+"),"+
                "FOREIGN KEY("+tableFood.COLUMN_RESTAURANTID+") REFERENCES "+tableRestaurant.TABLE_NAME+"("+tableRestaurant.COLUMN_RESTAURANTID+") ON UPDATE CASCADE);";

        db.execSQL(SQL_CREATE_FOOD_TABLE);

        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE "+tableReview.TABLE_NAME+" (" +
                tableReview.COLUMN_REVIEWID+" INTEGER NOT NULL PRIMARY KEY," +
                tableReview.COLUMN_REVIEW+" TEXT NOT NULL," +
                tableReview.COLUMN_STARS+" REAL NOT NULL,"+
                tableReview.COLUMN_USERNAME+" INTEGER," +
                tableReview.COLUMN_FOODID+" INTEGER," +
                "FOREIGN KEY("+tableReview.COLUMN_FOODID+") REFERENCES "+tableFood.TABLE_NAME+"("+tableFood.COLUMN_FOODID+") ON UPDATE CASCADE,"+
                "FOREIGN KEY("+tableReview.COLUMN_USERNAME+") REFERENCES "+tableUserIds.COLUMN_USERNAME+"("+tableUserIds.COLUMN_USERNAME+"));";

        db.execSQL(SQL_CREATE_REVIEW_TABLE);

    }

    //This method is called only when the database version or database schema is changed.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableUserIds.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tableUniversity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tableAddresses.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tableChef.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tableRestaurant.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tableFood.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + tableReview.TABLE_NAME);
        onCreate(db);
    }
}
