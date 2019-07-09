package com.example.foodreview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.foodreview.UserIdContract.*;
import java.util.ArrayList;

class DatabaseManager {

    private static DatabaseManager instance = null;
    private SQLiteDatabase db;
    private PasswordEncryptor encryptor;
    private Cursor databaseCursor;
    private int userIndex;

    // Singleton has a private constructor
    private DatabaseManager(Context context) {

        LoginDatabaseHelper fDBHelp = new LoginDatabaseHelper(context);
        db = fDBHelp.getWritableDatabase();
        databaseCursor = getCursor(tableUserIds.TABLE_NAME);
        encryptor = PasswordEncryptor.getInstance();
        hardCodeDatabaseTestData();
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
    // Does not return anything at the moment.
    void addItem(String username, String password) {

        byte[] salt = encryptor.getSalt(username);
        String hash = encryptor.encryptor(password, salt);

        ContentValues cv = new ContentValues();
        cv.put(tableUserIds.COLUMN_USERNAME, username);
        cv.put(tableUserIds.COLUMN_PASSWORD, hash);
        cv.put(tableUserIds.COLUMN_SALT, salt);
        cv.put(tableUserIds.COLUMN_ADMIN, 0);

        db.insert(tableUserIds.TABLE_NAME, null, cv);

    }

    // Takes three strings. Uses username and oldPassword to confirm the user.
    // Updates the password hash using the primary key username as a Where clause.
    boolean changePassword(String username, String newPassword) {

        databaseCursor.moveToPosition(userIndex);
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
        if (checkStringExistance(username, tableUserIds.TABLE_NAME, tableUserIds.COLUMN_USERNAME)) {
            //uses class variable to move cursor to the position, where the username was located.
            // Removes the need for another for -loop.
            databaseCursor.moveToPosition(userIndex);
            //System.out.println(userIndex);
            String dbPassWord = databaseCursor.getString(databaseCursor.getColumnIndex(tableUserIds.COLUMN_PASSWORD));
            byte[] thisSalt = databaseCursor.getBlob(databaseCursor.getColumnIndex(tableUserIds.COLUMN_SALT));
            String newHash = encryptor.encryptor(password, thisSalt);

            return newHash.equals(dbPassWord);
        }
        else {
            return false;
        }
    }

    void updateUsers() {
        databaseCursor = getCursor(tableUserIds.TABLE_NAME);
        ArrayList<User> newUsers = new ArrayList<>();
        UserManager userManager = UserManager.getInstance();

        int count = databaseCursor.getCount();

        for (int x = 0; x < count; x++) {
            databaseCursor.moveToPosition(x);
            String newUsername = databaseCursor.getString(databaseCursor.getColumnIndex(tableUserIds.COLUMN_USERNAME));
            int newIsAdmin = databaseCursor.getInt(databaseCursor.getColumnIndex(tableUserIds.COLUMN_ADMIN));

            User newUser = new User(newUsername, newIsAdmin);
            newUsers.add(newUser);
        }

        userManager.setUsers(newUsers);

    }

    // Looks through the database and looks for an existing username. Returns true if a username exists in the Database.
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
                userIndex = x;
                return true;
            }
        }
        return false;
    }

    // Looks through the database to check if a string exists in the database.
    // Also takes table name and column.
    private boolean checkStringExistance (String search, String tableName, String column) {
        databaseCursor = getCursor(tableName);
        databaseCursor.moveToFirst();
        int count = databaseCursor.getCount();

        for (int x = 0; x < count; x++) {
            databaseCursor.moveToPosition(x);
            String dbResult = databaseCursor.getString(databaseCursor.getColumnIndex(column));

            if (dbResult.equals(search)) {
                return true;
            }
        }
        return false;
    }

    // Looks through the database, checks integer instead of String
    private boolean checkIdExistance (int id, String tableName, String column) {
        databaseCursor = getCursor(tableName);
        databaseCursor.moveToFirst();
        int count = databaseCursor.getCount();

        for (int x = 0; x < count; x++) {
            databaseCursor.moveToPosition(x);
            int dbResult = databaseCursor.getInt(databaseCursor.getColumnIndex(column));

            if (dbResult == id) {
                return true;
            }
        }
        return false;
    }

    //A simple method to check an admin property from the database.
    boolean isAdmin (String username){
        checkStringExistance(username, tableUserIds.TABLE_NAME, tableUserIds.COLUMN_USERNAME);
        databaseCursor.moveToPosition(userIndex);
        int data = databaseCursor.getInt(databaseCursor.getColumnIndex(tableUserIds.COLUMN_ADMIN));
        return (data == 1);
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

    // Update Methods:
    // Update methods use the same principle, and so the principle has only been explained in the first
    // method. Update methods are made to parse database data, and to save the data in the right
    // format to be used by the program.
    void updateRestaurants (University thisUniversity) {

        //System.out.println("Getting data from university: "+ thisUniversity.getUniName());

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

        // For -loop goes through every row of data, and appends it to corresponding class attributes.
        // Saves objects to an ArrayList that is later set to corresponding parent objects.
        for (int x = 0; x < count; x++) {
            newCursor.moveToPosition(x);

            String[] newAddressArray = new String[]{newCursor.getString(newCursor.getColumnIndex(tableAddresses.COLUMN_ADDRESS)),
                    Integer.toString(newCursor.getInt(newCursor.getColumnIndex(tableAddresses.COLUMN_POSTALCODE))),
                    newCursor.getString(newCursor.getColumnIndex(tableAddresses.COLUMN_CITY))};

            int newId = newCursor.getInt(newCursor.getColumnIndex(tableRestaurant.COLUMN_RESTAURANTID));
            String newName = newCursor.getString(newCursor.getColumnIndex(tableRestaurant.COLUMN_RESTAURANTNAME));
            int newAddressId = newCursor.getInt(newCursor.getColumnIndex(tableRestaurant.COLUMN_ADDRESSID));
            Restaurant newRestaurant = new Restaurant(newId, newName, newAddressArray, newAddressId);
            restaurants.add(newRestaurant);
        }
        thisUniversity.setRestaurants(restaurants);
    }

    void updateFoods (Restaurant restaurant) {

        //Creates an SQL query clause. Uses class variables as table names and attributes
        String foodQuery = "SELECT * FROM "+tableFood.TABLE_NAME+
                " INNER JOIN "+ tableRestaurant.TABLE_NAME+
                " ON "+tableRestaurant.TABLE_NAME+"."+tableRestaurant.COLUMN_RESTAURANTID+
                " = "+tableFood.TABLE_NAME+"."+tableFood.COLUMN_RESTAURANTID+
                " WHERE "+tableFood.TABLE_NAME+"."+tableFood.COLUMN_RESTAURANTID+" = ?;";

        String[] arguments = {Integer.toString(restaurant.getRestaurantId())};

        Cursor newCursor = getRawCursor(foodQuery, arguments);
        ArrayList<Food> foods = new ArrayList<>();
        Food foodTemp;

        for (int x = 0; x < newCursor.getCount(); x++) {
            newCursor.moveToPosition(x);
            String newFoodName = newCursor.getString(newCursor.getColumnIndex(tableFood.COLUMN_FOODNAME));
            float newFoodPrice = newCursor.getFloat(newCursor.getColumnIndex(tableFood.COLUMN_FOODPRICE));
            int newFoodId = newCursor.getInt(newCursor.getColumnIndex(tableFood.COLUMN_FOODID));
            String newDate = newCursor.getString(newCursor.getColumnIndex(tableFood.COLUMN_DATE));

            foodTemp = new Food(newFoodName,newFoodId,newFoodPrice,newDate);
            foods.add(foodTemp);
        }
        restaurant.setRestaurantFoods(foods);

    }

    void updateReviews (Food food) {

        ArrayList<Review> reviews = new ArrayList<>();
        Review reviewTemp;

        //Creates the argument string array to be appended in where -clause.
        String whereClause = tableReview.COLUMN_FOODID + " = ?";
        String[] arguments = {Integer.toString(food.getFoodId())};
        Cursor newCursor = getCursorWithWhere(tableReview.TABLE_NAME, whereClause, arguments);

        //For -loop to go through every column in the current sql query.
        for (int x = 0; x < newCursor.getCount(); x++) {
            newCursor.moveToPosition(x);
            int reviewId = newCursor.getInt(newCursor.getColumnIndex(tableReview.COLUMN_REVIEWID));
            String review = newCursor.getString(newCursor.getColumnIndex(tableReview.COLUMN_REVIEW));
            float stars = newCursor.getFloat(newCursor.getColumnIndex(tableReview.COLUMN_STARS));
            String username = newCursor.getString(newCursor.getColumnIndex(tableReview.COLUMN_USERNAME));

            reviewTemp = new Review(reviewId , stars, review, username);
            reviews.add(reviewTemp);
        }
        food.setReviews(reviews);
    }

    // Method to update every item related to selected university.
    // Called every time when some data related to the university has been changed (or university deleted).
    void updateCascade (University university) {
        ArrayList<Restaurant> restaurants;
        ArrayList<Food> foods;

        updateRestaurants(university);
        restaurants = university.getRestaurants();
        Restaurant tempRestaurant;

        // Update all related foods
        for (int x = 0; x < restaurants.size(); x++) {
            tempRestaurant = restaurants.get(x);
            updateFoods(tempRestaurant);
            foods = tempRestaurant.getFoods();

            //Update all related Reviews
            System.out.println(foods.size());
            for (int y = 0; y < foods.size(); y++) {
                updateReviews(foods.get(y));
            }
        }

    }

    // Method sets a new university directly to database.
    void setNewUniversity (String newUniName) {

        ContentValues cv = new ContentValues();

        cv.put(tableUniversity.COLUMN_UNINAME, newUniName);
        long insertedId = db.insert(tableUniversity.TABLE_NAME, null, cv);
        updateUniversities();

        System.out.println("New university id is: "+insertedId);
    }

    // Method sets a new restaurant directly to database.
    void setNewRestaurant (String[] newAddress, String newRestaurantName, int whichUni) {

        ContentValues cvAddress = new ContentValues();
        cvAddress.put(tableAddresses.COLUMN_ADDRESS,newAddress[0]);
        cvAddress.put(tableAddresses.COLUMN_POSTALCODE, Integer.parseInt(newAddress[1]));
        cvAddress.put(tableAddresses.COLUMN_CITY, newAddress[2]);
        long newAddressId = db.insert(tableAddresses.TABLE_NAME, null, cvAddress);
        System.out.println("New address id is: "+newAddressId);

        ContentValues cvRestaurant = new ContentValues();
        cvRestaurant.put(tableRestaurant.COLUMN_ADDRESSID, newAddressId);
        cvRestaurant.put(tableRestaurant.COLUMN_RESTAURANTNAME, newRestaurantName);
        cvRestaurant.put(tableRestaurant.COLUMN_UNIID,whichUni);
        long newRestaurantId = db.insert(tableRestaurant.TABLE_NAME,null, cvRestaurant);
        System.out.println("New restaurant id is: "+newRestaurantId);
    }

    // Method sets a new restaurant directly to database.
    void setNewFood(String newFoodName, float newFoodPrice, int newRestaurantId, String newFoodDate) {
        ContentValues cv = new ContentValues();
        cv.put(tableFood.COLUMN_RESTAURANTID, newRestaurantId);
        cv.put(tableFood.COLUMN_FOODNAME, newFoodName);
        cv.put(tableFood.COLUMN_FOODPRICE, newFoodPrice);
        cv.put(tableFood.COLUMN_DATE, newFoodDate);
        long insertedId = db.insert(tableFood.TABLE_NAME,null, cv);
        System.out.println("New food id is: "+insertedId);
    }

    //Method sets a new review directly to the database.
    void setNewReview (String newReview, float newStars, String newUsername, int newFoodId) {
        ContentValues cv = new ContentValues();
        cv.put(tableReview.COLUMN_FOODID, newFoodId);
        cv.put(tableReview.COLUMN_STARS, newStars);
        cv.put(tableReview.COLUMN_REVIEW, newReview);
        cv.put(tableReview.COLUMN_USERNAME, newUsername);
        long insertedId = db.insert(tableReview.TABLE_NAME, null, cv);
        System.out.println("New review id is: "+ insertedId);
    }

    //Methods to modify database data:

    // Modify restaurant data. Call when needed to change restaurant data. Use null on String values
    // if value is not to be changed. Use -1 on integers if value is not to be changed.
    boolean modifyRestaurantData (Restaurant restaurant, String[] newAddress, String newRestaurantName, int whichUni) {
        ContentValues cvAddress = new ContentValues();
        ContentValues cvRestaurant = new ContentValues();
        boolean modified = false;

        if (newAddress != null) {
            String whereClauseAddress = tableAddresses.COLUMN_ADDRESSID + " = ?";
            String[] whereArgsAddressrestaurant = {Integer.toString(restaurant.getRestaurantAddressId())};
            cvAddress.put(tableAddresses.COLUMN_ADDRESS, newAddress[0]);
            cvAddress.put(tableAddresses.COLUMN_POSTALCODE, Integer.parseInt(newAddress[1]));
            cvAddress.put(tableAddresses.COLUMN_CITY, newAddress[2]);

            if (db.update(tableAddresses.TABLE_NAME, cvAddress, whereClauseAddress, whereArgsAddressrestaurant) <= 0) {
                return false;
            }

        }
        if (newRestaurantName != null) {
            cvRestaurant.put(tableRestaurant.COLUMN_RESTAURANTNAME, newRestaurantName);
            modified = true;
        }
        if (whichUni != -1) {
            cvRestaurant.put(tableRestaurant.COLUMN_UNIID, whichUni);
            modified = true;
        }
        if (modified) {
            String whereClauseRestaurant = tableRestaurant.COLUMN_RESTAURANTID + " = ?";
            String[] whereArgsRestaurant = {Integer.toString(restaurant.getRestaurantId())};

            if (db.update(tableRestaurant.TABLE_NAME, cvRestaurant, whereClauseRestaurant, whereArgsRestaurant) > 0) {
                return true;
            }
        }
        return false;
    }

    // Modify food's data in the database. Again, use unmodified strings as null and other values as -1
    boolean modifyFoodData(Food food, String foodName, float foodPrice, int restaurantId) {
        ContentValues cv = new ContentValues();
        String whereClause = tableFood.COLUMN_FOODID+ " = ?";
        String[] whereArgs = {Integer.toString(food.getFoodId())};

        if (foodName != null) {
            cv.put(tableFood.COLUMN_FOODNAME, foodName);
        }
        if (foodPrice != -1f) {
            cv.put(tableFood.COLUMN_FOODPRICE, foodPrice);
        }
        if (restaurantId != -1) {
            cv.put(tableFood.COLUMN_RESTAURANTID, restaurantId);
        }

        if (db.update(tableFood.TABLE_NAME, cv, whereClause, whereArgs) > 0) {
            return true;
        }
        return false;
    }

    // Methods to remove items from database:
    // All methods take in the corresponding object. Method takes it's id from the object and
    // removes the corresponding row from the database. Runs update -methods afterwards to
    // keep program's object

    void deleteUniversity(University university) {
        String whereClause = tableUniversity.COLUMN_UNIID +" = ?";
        String[] whereArgs = {Integer.toString(university.getUniId())};
        db.delete(tableUniversity.TABLE_NAME, whereClause, whereArgs);

        updateUniversities();
    }

    void deleteRestaurant(Restaurant restaurant, University university) {
        String whereClause = tableRestaurant.COLUMN_RESTAURANTID +" = ?";
        String[] whereArgsRestaurant = {Integer.toString(restaurant.getRestaurantId())};
        db.delete(tableRestaurant.TABLE_NAME, whereClause, whereArgsRestaurant);

        whereClause = tableAddresses.COLUMN_ADDRESSID +" = ?";
        String[] whereArgsAddress = {Integer.toString(restaurant.getRestaurantAddressId())};
        db.delete(tableAddresses.TABLE_NAME, whereClause, whereArgsAddress);
        updateCascade(university);
    }

    void deleteFood(Food food, Restaurant restaurant) {
        String whereClause = tableFood.COLUMN_FOODID +" = ?";
        String[] whereArgs = {Integer.toString(food.getFoodId())};
        db.delete(tableFood.TABLE_NAME, whereClause, whereArgs);

        updateFoods(restaurant);
        ArrayList<Food> tempFoods = restaurant.getFoods();
        for (int x = 0; x < tempFoods.size(); x++) {
            updateReviews(tempFoods.get(x));
        }
    }

    void deleteReview(Review review, Food food) {
        String whereClause = tableReview.COLUMN_REVIEWID +" = ?";
        String[] whereArgs = {Integer.toString(review.getReviewId())};
        db.delete(tableReview.TABLE_NAME, whereClause, whereArgs);

        updateReviews(food);
    }

    // This method makes a query to get the data from the database. Returns cursor.
    private Cursor getCursor(String whatTable) {
        return db.query(whatTable,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    // This method uses a where -clause to get data from the database.
    private Cursor getCursorWithWhere(String whatTable, String whereClause, String[] whereArgs) {
        return db.query(whatTable,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }

    // This method fetches a cursor using a raw SQL select -clause.
    // Used instead of plain fetch (above) when joining tables or requiring very specific data.
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



    // This method puts the Test Data in to the database, while running checks to qualify the existance
    // of the data that is being added.
    private void hardCodeDatabaseTestData() {
        // users
        if (!checkStringExistance("admin", tableUserIds.TABLE_NAME, tableUserIds.COLUMN_USERNAME)) {
            createAdmin();
        }
        // Universities
        if (!checkStringExistance("LUT-University", tableUniversity.TABLE_NAME, tableUniversity.COLUMN_UNINAME)) {
            setNewUniversity("LUT-University");
        }
        if (!checkStringExistance("TTY", tableUniversity.TABLE_NAME, tableUniversity.COLUMN_UNINAME)) {
            setNewUniversity("TTY");
        }
        if (!checkStringExistance("Itä-Suomen Yliopisto", tableUniversity.TABLE_NAME, tableUniversity.COLUMN_UNINAME)) {
            setNewUniversity("Itä-Suomen Yliopisto");
        }
        //TODO: Checks to allow multiple restaurants with the same name!
        if (!checkStringExistance("Aalef - Meidän ravintola", tableRestaurant.TABLE_NAME, tableRestaurant.COLUMN_RESTAURANTNAME)) {
            String[] newAddress = {"Laserkatu 10", "53850", "Lappeenranta"};
            setNewRestaurant(newAddress, "Aalef - Meidän ravintola", 1);
        }
        if (!checkStringExistance("Aalef - Laseri", tableRestaurant.TABLE_NAME, tableRestaurant.COLUMN_RESTAURANTNAME)) {
            String[] newAddress = {"Skinnarilankatu 45", "53850", "Lappeenranta"};
            setNewRestaurant(newAddress, "Aalef - Laseri", 1);
        }
        if (!checkStringExistance("LUT Buffet", tableRestaurant.TABLE_NAME, tableRestaurant.COLUMN_RESTAURANTNAME)) {
            String[] newAddress = {"Yliopistonkatu 38", "53850", "Lappeenranta"};
            setNewRestaurant(newAddress, "LUT Buffet", 1);
        }
        if (!checkStringExistance("Juvenes - Newton", tableRestaurant.TABLE_NAME, tableRestaurant.COLUMN_RESTAURANTNAME)) {
            String[] newAddress = {"Korkeakoulunkatu 6", "33780", "Tampere"};
            setNewRestaurant(newAddress, "Juvenes - Newton", 2);
        }
        if (!checkStringExistance("Opiskelijaravintola Carelia", tableRestaurant.TABLE_NAME, tableRestaurant.COLUMN_RESTAURANTNAME)) {
            String[] newAddress = {"Yliopistonkatu 4", "80100", "Joensuu"};
            setNewRestaurant(newAddress, "Opiskelijaravintola Carelia", 3);
        }
        //Foods from now on:
        if (!checkIdExistance(1,tableFood.TABLE_NAME, tableFood.COLUMN_FOODID)) {
            String newFoodName = "Jauhelihapullat ja muusi";
            float newFoodPrice = 5.40f;
            int newRestaurantId = 1;
            String newFoodDate = "08.07.2019";
            setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
        }
        if (!checkIdExistance(2,tableFood.TABLE_NAME, tableFood.COLUMN_FOODID)) {
            String newFoodName = "Hampurilaisateria";
            float newFoodPrice = 2.60f;
            int newRestaurantId = 1;
            String newFoodDate = "08.07.2019";
            setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
        }
        if (!checkIdExistance(3,tableFood.TABLE_NAME, tableFood.COLUMN_FOODID)) {
            String newFoodName = "Lehtisalaatti ja keitto";
            float newFoodPrice = 2.60f;
            int newRestaurantId = 1;
            String newFoodDate = "08.07.2019";
            setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
        }
        if (!checkIdExistance(4,tableFood.TABLE_NAME, tableFood.COLUMN_FOODID)) {
            String newFoodName = "Broileritortillat";
            float newFoodPrice = 2.60f;
            int newRestaurantId = 2;
            String newFoodDate = "08.07.2019";
            setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
        }
        if (!checkIdExistance(5,tableFood.TABLE_NAME, tableFood.COLUMN_FOODID)) {
            String newFoodName = "Uunilohi ja perunamuusi";
            float newFoodPrice = 5.40f;
            int newRestaurantId = 2;
            String newFoodDate = "08.07.2019";
            setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
        }
        if (!checkIdExistance(6,tableFood.TABLE_NAME, tableFood.COLUMN_FOODID)) {
            String newFoodName = "Jauhelihakastike ja perunat";
            float newFoodPrice = 2.20f;
            int newRestaurantId = 3;
            String newFoodDate = "08.07.2019";
            setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
        }
        if (!checkIdExistance(7,tableFood.TABLE_NAME, tableFood.COLUMN_FOODID)) {
            String newFoodName = "Chili con Carne";
            float newFoodPrice = 2.60f;
            int newRestaurantId = 3;
            String newFoodDate = "08.07.2019";
            setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
        }
    }
}
