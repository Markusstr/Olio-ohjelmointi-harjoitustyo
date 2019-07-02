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

    // Singleton has a private constructor
    private DatabaseManager(Context context) {

        LoginDatabaseHelper fDBHelp = new LoginDatabaseHelper(context);
        db = fDBHelp.getWritableDatabase();
        databaseCursor = getCursor();
        encryptor = PasswordEncryptor.getInstance();
        if (!checkExistance("admin")) {
            createAdmin();
        }
    }

    // This class functions as a singleton and always returns the same instance
    static DatabaseManager getInstance(Context newContext) {
        if (instance == null) {
            instance = new DatabaseManager(newContext);
        }
        else {

            System.out.println("Instance already exists");
        }
        return instance;
    }


    // This method takes given username and password to insert one new value to the database.
    // Does not return anything at the moment. //TODO: Maybe check if the database saves the value properly?
    void addItem(String username, String password) {

        byte[] salt = encryptor.getSalt(username);
        String hash = encryptor.encryptor(password, salt);

        ContentValues cv = new ContentValues();
        cv.put(UserIdContract.newUserId.COLUMN_USERNAME, username);
        cv.put(UserIdContract.newUserId.COLUMN_PASSWORD, hash);
        cv.put(UserIdContract.newUserId.COLUMN_SALT, salt);
        cv.put(UserIdContract.newUserId.COLUMN_ADMIN, false);

        db.insert(UserIdContract.newUserId.TABLE_NAME, null, cv);

        databaseCursor = getCursor();
    }

    // Takes three strings. Uses username and oldPassword to confirm the user.
    // Updates the password hash using the primary key username as a Where clause.
    boolean changePassword(String username, String oldPassword, String newPassword) {

        if(searchDatabase(username, oldPassword)) {

            databaseCursor.moveToPosition(index);
            byte[] thisSalt = databaseCursor.getBlob(databaseCursor.getColumnIndex(UserIdContract.newUserId.COLUMN_SALT));
            String newHash = encryptor.encryptor(newPassword, thisSalt);
            ContentValues cv = new ContentValues();
            cv.put(UserIdContract.newUserId.COLUMN_PASSWORD,newHash);

            String whereClause = UserIdContract.newUserId.COLUMN_USERNAME.concat("=?");
            String[] whereArgs = {username};

            if (db.update(UserIdContract.newUserId.TABLE_NAME, cv, whereClause,whereArgs) > 0) {
                return true;
            }
            else {
                System.out.println("Database update failed!");
                return false;
            }
        }
        else {
            System.out.println("Wrong password!");
            return false;
        }
    }

    // Method takes given username and password. Uses checkExistance -method to find the index.
    // Returns true if values match.
    boolean searchDatabase (String username, String password) {

        databaseCursor = getCursor();
        if (checkExistance(username)) {
            //uses class variable to move cursor to the position, where the username was located.
            // Removes the need for another for -loop.
            databaseCursor.moveToPosition(index);
            System.out.println(index);
            String dbPassWord = databaseCursor.getString(databaseCursor.getColumnIndex(UserIdContract.newUserId.COLUMN_PASSWORD));
            byte[] thisSalt = databaseCursor.getBlob(databaseCursor.getColumnIndex(UserIdContract.newUserId.COLUMN_SALT));
            String newHash = encryptor.encryptor(password, thisSalt);

            return newHash.equals(dbPassWord);
        }
        else {
            return false;
        }
    }

    // Looks through the arrayList and looks for an existing username. Returns true if a username exists in the Database.
    // Also sets a class variable index to the position of the located username.
    boolean checkExistance (String username) {
        databaseCursor = getCursor();
        databaseCursor.moveToFirst();
        int count = databaseCursor.getCount();

        for (int x = 0; x < count; x++) {

            databaseCursor.moveToPosition(x);
            String dbUserName = databaseCursor.getString(databaseCursor.getColumnIndex(UserIdContract.newUserId.COLUMN_USERNAME));
            System.out.println(x);
            if (dbUserName.equals(username)) {
                System.out.println(x);
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

    // Hardcode admin in to the database.
    private void createAdmin() {
        encryptor = PasswordEncryptor.getInstance();
        byte[] salt = encryptor.getSalt("admin");
        ContentValues cv = new ContentValues();
        String password = encryptor.encryptor("admin", salt);
        cv.put(UserIdContract.newUserId.COLUMN_USERNAME, "admin");
        cv.put(UserIdContract.newUserId.COLUMN_PASSWORD, password);
        cv.put(UserIdContract.newUserId.COLUMN_SALT, salt);
        cv.put(UserIdContract.newUserId.COLUMN_ADMIN, true);
        db.insert(UserIdContract.newUserId.TABLE_NAME, null, cv);
    }



}
