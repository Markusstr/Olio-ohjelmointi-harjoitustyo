package com.example.foodreview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class DatabaseManager {


    private static DatabaseManager instance = null;
    private SQLiteDatabase db;
    private PasswordEncryptor encryptor;
    private Cursor databaseCursor;
    private int index;


    private DatabaseManager(Context context) {

        LoginDatabaseHelper fDBHelp = new LoginDatabaseHelper(context);
        db = fDBHelp.getWritableDatabase();
        databaseCursor = getCursor();
        encryptor = PasswordEncryptor.getInstance();



    }

    // This class functions as a singleton.
    static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        else {
            System.out.println("Instance already exists");
        }
        return instance;
    }


    // This method takes username, password and a database hook to insert one new value to the database.
    void addItem(String username, String password) {

        byte[] salt = encryptor.getSalt(username);
        String hash = encryptor.encryptor(password, salt);

        ContentValues cv = new ContentValues();
        cv.put(UserIdContract.newUserId.COLUMN_USERNAME, username);
        cv.put(UserIdContract.newUserId.COLUMN_PASSWORD, hash);
        cv.put(UserIdContract.newUserId.COLUMN_SALT, salt);

        db.insert(UserIdContract.newUserId.TABLE_NAME, null, cv);
    }

    boolean searchDatabase (String username, String password) {

        databaseCursor = getCursor();
        if (checkExistance(username)) {
            //uses class variable to move cursor to the position, where the username was located.
            // Removes the need for another for -loop.
            databaseCursor.moveToPosition(index);
            String dbPassWord = databaseCursor.getString(databaseCursor.getColumnIndex(UserIdContract.newUserId.COLUMN_PASSWORD));
            byte[] thisSalt = databaseCursor.getBlob(databaseCursor.getColumnIndex(UserIdContract.newUserId.COLUMN_SALT));
            String newHash = encryptor.encryptor(password, thisSalt);

            return newHash.equals(dbPassWord);
        }
        else {
            return false;
        }
    }

    // Looks through the arrayList and looks for an existing username. Returns true if a username exists in the ArrayList.
    boolean checkExistance (String username) {
        databaseCursor = getCursor();
        int count = databaseCursor.getCount();
        databaseCursor.moveToFirst();

        for (int x = 0; x < count; x++) {
            String dbUserName = databaseCursor.getString(databaseCursor.getColumnIndex(UserIdContract.newUserId.COLUMN_USERNAME));
            if (dbUserName.equals(username)) {
                index = x;
                return true;
            }
        }
        return false;

    }

    //This method makes a query to get the data from the database. Returns cursor.
    private Cursor getCursor() {
        return db.query(UserIdContract.newUserId.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
