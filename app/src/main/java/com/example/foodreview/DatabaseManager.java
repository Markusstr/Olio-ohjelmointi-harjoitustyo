package com.example.foodreview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

class DatabaseManager {

    // This class functions as a singleton.
    private static DatabaseManager instance = null;
    private SQLiteDatabase db;
    private PasswordEncryptor encryptor;
    private Cursor databaseCursor;


    private DatabaseManager(Context context) {

        LoginDatabaseHelper fDBHelp = new LoginDatabaseHelper(context);
        db = fDBHelp.getWritableDatabase();
        databaseCursor = getCursor();
        encryptor = PasswordEncryptor.getInstance();


    }

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

    boolean searchDatabase (String username, String password, Context context) {

        databaseCursor = getCursor();
        databaseCursor.moveToFirst();
        int count = databaseCursor.getCount();

        for (int x = 0; x < count; x++) {

            databaseCursor.moveToPosition(x);
            String dbUserName = databaseCursor.getString(databaseCursor.getColumnIndex(UserIdContract.newUserId.COLUMN_USERNAME));
            String dbPassWord = databaseCursor.getString(databaseCursor.getColumnIndex(UserIdContract.newUserId.COLUMN_PASSWORD));

//            System.out.println(dbUserName+ " " + dbPassWord);
//            System.out.println(username+ " " + hash);

            if (username.equals(dbUserName)) {
                byte[] thisSalt = databaseCursor.getBlob(databaseCursor.getColumnIndex(UserIdContract.newUserId.COLUMN_SALT));
                String newHash = encryptor.encryptor(password, thisSalt);

                if (newHash.equals(dbPassWord)) {
                    Toast toast = (Toast.makeText(context, "Login SUCCESS VITTU SAATANA!!!!", Toast.LENGTH_LONG));
                    toast.show();
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkExistance (String username) {
        databaseCursor = getCursor();
        databaseCursor.moveToFirst();
        int length = databaseCursor.getCount();

        if (databaseCursor.getCount() <= 0) {
            return false;
        }
        else {

            for (int x = 0; x < length; x++) {
                databaseCursor.moveToPosition(x);
                String dbUserName = databaseCursor.getString(databaseCursor.getColumnIndex(UserIdContract.newUserId.COLUMN_USERNAME));
                if (dbUserName.equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

/*    String getData () {
        return "";
    }*/

}
