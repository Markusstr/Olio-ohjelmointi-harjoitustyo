package com.example.foodreview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;

import com.example.foodreview.UserIdContract.*;

import java.util.ArrayList;

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
        databaseCursor = getCursor(tableUserIds.TABLE_NAME);
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
        cv.put(tableUserIds.COLUMN_USERNAME, username);
        cv.put(tableUserIds.COLUMN_PASSWORD, hash);
        cv.put(tableUserIds.COLUMN_SALT, salt);
        cv.put(tableUserIds.COLUMN_ADMIN, 0);

        db.insert(tableUserIds.TABLE_NAME, null, cv);

        databaseCursor = getCursor(tableUserIds.TABLE_NAME);
    }

    // Takes three strings. Uses username and oldPassword to confirm the user.
    // Updates the password hash using the primary key username as a Where clause.
    boolean changePassword(String username, String newPassword) {

        databaseCursor.moveToPosition(index);
        byte[] thisSalt = databaseCursor.getBlob(databaseCursor.getColumnIndex(tableUserIds.COLUMN_SALT));
        String newHash = encryptor.encryptor(newPassword, thisSalt);
        ContentValues cv = new ContentValues();
        cv.put(tableUserIds.COLUMN_PASSWORD,newHash);

        String whereClause = tableUserIds.COLUMN_USERNAME.concat("=?");
        String[] whereArgs = {username};

        if (db.update(tableUserIds.TABLE_NAME, cv, whereClause,whereArgs) > 0) {
            return true;
        }
        else {
            System.out.println("Database update failed!");
            return false;
        }
    }

    // Method takes given username and password. Uses checkExistance -method to find the index.
    // Returns true if values match.
    boolean searchDatabase (String username, String password) {

        databaseCursor = getCursor(tableUserIds.TABLE_NAME);
        if (checkExistance(username)) {
            //uses class variable to move cursor to the position, where the username was located.
            // Removes the need for another for -loop.
            databaseCursor.moveToPosition(index);
            //System.out.println(index);
            String dbPassWord = databaseCursor.getString(databaseCursor.getColumnIndex(tableUserIds.COLUMN_PASSWORD));
            byte[] thisSalt = databaseCursor.getBlob(databaseCursor.getColumnIndex(tableUserIds.COLUMN_SALT));
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
        databaseCursor = getCursor(tableUserIds.TABLE_NAME);
        databaseCursor.moveToFirst();
        int count = databaseCursor.getCount();

        for (int x = 0; x < count; x++) {

            databaseCursor.moveToPosition(x);
            String dbUserName = databaseCursor.getString(databaseCursor.getColumnIndex(tableUserIds.COLUMN_USERNAME));
            //System.out.println(x);
            if (dbUserName.equals(username)) {
                System.out.println(x);
                index = x;
                return true;
            }
        }
        return false;
    }

    //A simple method to check an admin property from the database.
    boolean isAdmin (String username){
        checkExistance(username);
        databaseCursor.moveToPosition(index);
        int data = databaseCursor.getInt(databaseCursor.getColumnIndex(tableUserIds.COLUMN_ADMIN));
        return (data == 1);
    }

    // Moving on to use other tables in the database after this line.
    boolean addUniversity (String name) {
        ContentValues cv = new ContentValues();
        cv.put(tableUniversity.COLUMN_UNINAME, name);
        return db.insert(tableUniversity.TABLE_NAME, null, cv) >= 0;
    }

    // Method is called, when a university has been changed.
    // Updates the UniversityManager object to reflect the data in the database.
    void updateUniversities() {
        UniversityManager uniMan = UniversityManager.getInstance();
        Cursor newCursor = getCursor(tableUniversity.TABLE_NAME);

        int numberOfItems = newCursor.getCount();

        String uniName;
        int uniId;
        ArrayList<University> universities = new ArrayList<>();

        for (int x = 0; x < numberOfItems; x++) {
            newCursor.moveToPosition(x);
            uniId = newCursor.getInt(newCursor.getColumnIndex(tableUniversity.COLUMN_UNIID));
            uniName = newCursor.getString(newCursor.getColumnIndex(tableUniversity.COLUMN_UNINAME));
            universities.add(new University(uniId, uniName));
        }
        uniMan.setUniversities(universities);
    }

    //Is called with a list of Address ids to get data to restaurants.
    void updateRestaurants (University thisUniversity) {

        //Creates an SQL query clause. Uses class variables as table names and attributes
        String restaurantQuery = "SELECT * FROM "+tableRestaurant.TABLE_NAME+
                " INNER JOIN "+ tableAddresses.TABLE_NAME+
                " ON "+tableRestaurant.TABLE_NAME+"."+tableRestaurant.COLUMN_ADDRESSID+
                " = "+tableAddresses.TABLE_NAME+"."+tableAddresses.COLUMN_ADDRESSID+
                " WHERE "+tableRestaurant.COLUMN_UNIID+" = ?;";

        //Creates the argument string array to be appended in where -clause.
        String[] argument = {String.valueOf(thisUniversity.getUniId())};

        //Executes the sql query.
        Cursor newCursor = getRawCursor(restaurantQuery, argument);
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        int count = newCursor.getCount();
        for (int x = 0; x < count; x++) {
            newCursor.moveToPosition(x);

            String[] newAddressArray = new String[]{newCursor.getString(newCursor.getColumnIndex(tableAddresses.COLUMN_ADDRESS)),
                    Integer.toString(newCursor.getInt(newCursor.getColumnIndex(tableAddresses.COLUMN_POSTALCODE))),
                    newCursor.getString(newCursor.getColumnIndex(tableAddresses.COLUMN_CITY))};

            int newId = newCursor.getInt(newCursor.getColumnIndex(tableRestaurant.COLUMN_RESTAURANTID));
            String newName = newCursor.getString(newCursor.getColumnIndex(tableRestaurant.COLUMN_RESTAURANTNAME));
            Restaurant newRestaurant = new Restaurant(newId, newName, newAddressArray);
            restaurants.add(newRestaurant);
        }
        thisUniversity.setRestaurants(restaurants);
    }

    void fetchFoodFromRestaurant (Restaurant restaurant) {

        //Creates an SQL query clause. Uses class variables as table names and attributes
        String foodQuery = "SELECT * FROM "+tableFood.TABLE_NAME+
                " INNER JOIN "+ tableRestaurant.TABLE_NAME+
                " ON "+tableRestaurant.TABLE_NAME+"."+tableRestaurant.COLUMN_RESTAURANTID+
                " = "+tableFood.TABLE_NAME+"."+tableFood.COLUMN_RESTAURANTID+
                " WHERE "+tableFood.COLUMN_RESTAURANTID+" = ?;";

        //Creates the argument string array to be appended in where -clause.
        String[] arguments = {Integer.toString(restaurant.getRestaurantId())};

        //Executes the sql query.
        Cursor newCursor = getRawCursor(foodQuery, arguments);
        ArrayList<Food> foods = new ArrayList<>();
        Food foodTemp;

        //For -loop to go through every column in the current sql query.
        for (int x = 0; x < newCursor.getCount(); x++) {
            String newFoodName = newCursor.getString(newCursor.getColumnIndex(tableFood.COLUMN_FOODNAME));
            float newFoodPrice = newCursor.getFloat(newCursor.getColumnIndex(tableFood.COLUMN_FOODPRICE));
            int newFoodId = newCursor.getInt(newCursor.getColumnIndex(tableFood.COLUMN_FOODID));
            String newDate = newCursor.getString(newCursor.getColumnIndex(tableFood.COLUMN_DATE));

            foodTemp = new Food(newFoodName,newFoodId,newFoodPrice,newDate);
            foods.add(foodTemp);
        }
        restaurant.setRestaurantFoods(foods);

    }

    void setNewUniversity (String newUniName) {

        ContentValues cv = new ContentValues();

        cv.put(tableUniversity.COLUMN_UNINAME, newUniName);
        db.insert(tableUniversity.TABLE_NAME, null, cv);
        updateUniversities();

    }

    void setNewRestaurant (String[] newAddress, String newRestaurantName, int whichUni) {

        ContentValues cvAddress = new ContentValues();
        cvAddress.put(tableAddresses.COLUMN_ADDRESS,newAddress[0]);
        cvAddress.put(tableAddresses.COLUMN_POSTALCODE, Integer.parseInt(newAddress[1]));
        cvAddress.put(tableAddresses.COLUMN_CITY, newAddress[2]);
        long newAddressId = db.insert(tableAddresses.TABLE_NAME, null, cvAddress);

        ContentValues cvRestaurant = new ContentValues();
        cvRestaurant.put(tableRestaurant.COLUMN_ADDRESSID, newAddressId);
        cvRestaurant.put(tableRestaurant.COLUMN_RESTAURANTNAME, newRestaurantName);
        cvRestaurant.put(tableRestaurant.COLUMN_UNIID,whichUni);
        db.insert(tableRestaurant.TABLE_NAME,null, cvRestaurant);

    }

    void setNewFood(String newFoodName, float newFoodPrice, int newRestaurantId) {
        ContentValues cv = new ContentValues();
        cv.put(tableFood.COLUMN_RESTAURANTID, newRestaurantId);
        cv.put(tableFood.COLUMN_FOODNAME, newFoodName);
        cv.put(tableFood.COLUMN_FOODPRICE, newFoodPrice);
        db.insert(tableFood.TABLE_NAME,null, cv);
    }

    //This method makes a query to get the data from the database. Returns cursor.
    private Cursor getCursor(String whatTable) {
        return db.query(whatTable,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    // This method fetches a cursor using a raw SQL select -clause.
    // Used instead of plain fetch (above) when joining tables or requiring specific data.
    private Cursor getRawCursor (String query, String[] arguments) {
        return db.rawQuery(query, arguments);

    }

    // Hardcode admin in to the database.
    private void createAdmin() {
        encryptor = PasswordEncryptor.getInstance();
        byte[] salt = encryptor.getSalt("admin");
        ContentValues cv = new ContentValues();
        String password = encryptor.encryptor("admin", salt);
        cv.put(tableUserIds.COLUMN_USERNAME, "admin");
        cv.put(tableUserIds.COLUMN_PASSWORD, password);
        cv.put(tableUserIds.COLUMN_SALT, salt);
        cv.put(tableUserIds.COLUMN_ADMIN, 1);
        db.insert(tableUserIds.TABLE_NAME, null, cv);
    }
}
