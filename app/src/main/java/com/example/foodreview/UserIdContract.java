package com.example.foodreview;

import android.provider.BaseColumns;

class UserIdContract{

    private UserIdContract() {}

     static final class newUserId  implements BaseColumns {

        static final String TABLE_NAME = "userIDs";
        static final String COLUMN_USERNAME = "username";
        static final String COLUMN_PASSWORD = "password";
        static final String COLUMN_SALT = "salt";
    }



}