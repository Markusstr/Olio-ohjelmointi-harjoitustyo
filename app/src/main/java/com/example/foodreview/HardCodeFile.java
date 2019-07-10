package com.example.foodreview;

import android.content.ContentValues;

import java.util.ArrayList;

public class HardCodeFile {

    private static HardCodeFile instance = null;
    private DatabaseManager dbms;

    private HardCodeFile() {
        this.dbms = DatabaseManager.getInstance(null);
    }

    static HardCodeFile getInstance () {
        if (instance == null) {
            instance = new HardCodeFile();
        }
        else {
            System.out.println("Instance already exists.");
        }
        return instance;
    }

    public static ArrayList<ContentValues> hardCodeFoods(int counter, String newFoodDate) {
        switch (counter) {
            //Date 1
            case 1:
                //Foods from now on:
                ArrayList<ContentValues> contentValues = new ArrayList<>();
                ContentValues cv = new ContentValues();
                cv.put(UserIdContract.tableFood.COLUMN_FOODID, 1);
                cv.put(UserIdContract.tableFood.COLUMN_RESTAURANTID, 1);
                cv.put(UserIdContract.tableFood.COLUMN_FOODNAME, "Jauhelihapullat ja muusi");
                cv.put(UserIdContract.tableFood.COLUMN_FOODPRICE, 2.60f);
                cv.put(UserIdContract.tableFood.COLUMN_DATE, newFoodDate);
                contentValues.add(cv);

                cv = new ContentValues();
                cv.put(UserIdContract.tableFood.COLUMN_FOODID, 2);
                cv.put(UserIdContract.tableFood.COLUMN_RESTAURANTID, 1);
                cv.put(UserIdContract.tableFood.COLUMN_FOODNAME, "Hampurilaisateria");
                cv.put(UserIdContract.tableFood.COLUMN_FOODPRICE, 5.40f);
                cv.put(UserIdContract.tableFood.COLUMN_DATE, newFoodDate);
                contentValues.add(cv);

                cv = new ContentValues();
                cv.put(UserIdContract.tableFood.COLUMN_FOODID, 3);
                cv.put(UserIdContract.tableFood.COLUMN_RESTAURANTID, 1);
                cv.put(UserIdContract.tableFood.COLUMN_FOODNAME, "Lehtisalaatti ja keitto");
                cv.put(UserIdContract.tableFood.COLUMN_FOODPRICE, 2.60f);
                cv.put(UserIdContract.tableFood.COLUMN_DATE, newFoodDate);
                contentValues.add(cv);

                cv = new ContentValues();
                cv.put(UserIdContract.tableFood.COLUMN_FOODID, 4);
                cv.put(UserIdContract.tableFood.COLUMN_RESTAURANTID, 2);
                cv.put(UserIdContract.tableFood.COLUMN_FOODNAME, "Broileritortillat");
                cv.put(UserIdContract.tableFood.COLUMN_FOODPRICE, 2.60f);
                cv.put(UserIdContract.tableFood.COLUMN_DATE, newFoodDate);
                contentValues.add(cv);

                cv = new ContentValues();
                cv.put(UserIdContract.tableFood.COLUMN_FOODID, 5);
                cv.put(UserIdContract.tableFood.COLUMN_RESTAURANTID, 2);
                cv.put(UserIdContract.tableFood.COLUMN_FOODNAME, "Uunilohi ja perunamuusi");
                cv.put(UserIdContract.tableFood.COLUMN_FOODPRICE, 5.40f);
                cv.put(UserIdContract.tableFood.COLUMN_DATE, newFoodDate);
                contentValues.add(cv);

                cv = new ContentValues();
                cv.put(UserIdContract.tableFood.COLUMN_FOODID, 6);
                cv.put(UserIdContract.tableFood.COLUMN_RESTAURANTID, 3);
                cv.put(UserIdContract.tableFood.COLUMN_FOODNAME, "Jauhelihakastike ja perunat");
                cv.put(UserIdContract.tableFood.COLUMN_FOODPRICE, 2.20f);
                cv.put(UserIdContract.tableFood.COLUMN_DATE, newFoodDate);
                contentValues.add(cv);

                cv = new ContentValues();
                cv.put(UserIdContract.tableFood.COLUMN_FOODID, 7);
                cv.put(UserIdContract.tableFood.COLUMN_RESTAURANTID, 3);
                cv.put(UserIdContract.tableFood.COLUMN_FOODNAME, "Chili con Carne");
                cv.put(UserIdContract.tableFood.COLUMN_FOODPRICE, 2.60f);
                cv.put(UserIdContract.tableFood.COLUMN_DATE, newFoodDate);
                contentValues.add(cv);

                cv = new ContentValues();
                cv.put(UserIdContract.tableFood.COLUMN_FOODID, 8);
                cv.put(UserIdContract.tableFood.COLUMN_RESTAURANTID, 4);
                cv.put(UserIdContract.tableFood.COLUMN_FOODNAME, "Meksikolainen Uunimakkara");
                cv.put(UserIdContract.tableFood.COLUMN_FOODPRICE, 2.60f);
                cv.put(UserIdContract.tableFood.COLUMN_DATE, newFoodDate);
                contentValues.add(cv);

                cv = new ContentValues();
                cv.put(UserIdContract.tableFood.COLUMN_FOODID, 9);
                cv.put(UserIdContract.tableFood.COLUMN_RESTAURANTID, 4);
                cv.put(UserIdContract.tableFood.COLUMN_FOODNAME, "Lehtikaalikeitto");
                cv.put(UserIdContract.tableFood.COLUMN_FOODPRICE, 5.40f);
                cv.put(UserIdContract.tableFood.COLUMN_DATE, newFoodDate);
                contentValues.add(cv);

                cv = new ContentValues();
                cv.put(UserIdContract.tableFood.COLUMN_FOODID, 10);
                cv.put(UserIdContract.tableFood.COLUMN_RESTAURANTID, 5);
                cv.put(UserIdContract.tableFood.COLUMN_FOODNAME, "Jauheliha-Perunaviipalelaatikko");
                cv.put(UserIdContract.tableFood.COLUMN_FOODPRICE, 2.60f);
                cv.put(UserIdContract.tableFood.COLUMN_DATE, newFoodDate);
                contentValues.add(cv);

                cv = new ContentValues();
                cv.put(UserIdContract.tableFood.COLUMN_FOODID, 11);
                cv.put(UserIdContract.tableFood.COLUMN_RESTAURANTID, 5);
                cv.put(UserIdContract.tableFood.COLUMN_FOODNAME, "Broileria Currykastikkeessa");
                cv.put(UserIdContract.tableFood.COLUMN_FOODPRICE, 5.40f);
                cv.put(UserIdContract.tableFood.COLUMN_DATE, newFoodDate);
                contentValues.add(cv);
                return contentValues;

            default:
                return null;
            //Date 2
//            case 2:
//                if (!dbms.checkIdExistance(12, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_DATE)) {
//                    String newFoodName = "Isot lihapullat tomaattikastikkeessa";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 1;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(13, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Lohiperunavuoka";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 1;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(14, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Sienikeitto";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 1;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(15, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Broileritortillat";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 2;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(16, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Karjalanpaisti";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 2;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(17, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Soijaa ja kasviksia hapanimelä";
//                    float newFoodPrice = 2.20f;
//                    int newRestaurantId = 3;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(18, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Kasvissosekeitto";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 3;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(19, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Kreikkalainen lihapata";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 4;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(20, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Smetana                        Broileri";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 4;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(21, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Suppilovahvero-savuporokeitto";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 5;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(22, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Grillimakkaroita";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 5;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                break;
//            case 3:
//                if (!dbms.checkIdExistance(23, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_DATE)) {
//                    String newFoodName = "Isot lihapullat tomaattikastikkeessa";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 1;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(24, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Lohiperunavuoka";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 1;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(25, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Sienikeitto";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 1;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(26, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Broileritortillat";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 2;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(27, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Karjalanpaisti";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 2;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(28, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Soijaa ja kasviksia hapanimelä";
//                    float newFoodPrice = 2.20f;
//                    int newRestaurantId = 3;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(29, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Kasvissosekeitto";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 3;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(30, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Kreikkalainen lihapata";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 4;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(31, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Smetana                        Broileri";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 4;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(32, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Suppilovahvero-savuporokeitto";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 5;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(33, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Grillimakkaroita";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 5;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                break;
///* This is day 4                            ***                                 ***                             ****                        ****/
//            case 4:
//                if (!dbms.checkIdExistance(34, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_DATE)) {
//                    String newFoodName = "Uunilohi ja tillikermaviili";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 1;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(35, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Tomaattiset lihapyörykät";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 1;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(36, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Kasviskevätkääryleet";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 1;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(37, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Suurustettu kanakeitto";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 2;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(38, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Karjalanpaisti";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 2;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(39, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Soijaa ja kasviksia hapanimelä";
//                    float newFoodPrice = 2.20f;
//                    int newRestaurantId = 3;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(40, UserIdContract.tableFood.TABLE_NAME, UserIdContract.tableFood.COLUMN_FOODID)) {
//                    String newFoodName = "Kasvissosekeitto";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 3;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(30, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Kreikkalainen lihapata";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 4;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(31, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Smetana                        Broileri";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 4;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(32, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Suppilovahvero-savuporokeitto";
//                    float newFoodPrice = 2.60f;
//                    int newRestaurantId = 5;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                if (!dbms.checkIdExistance(33, UserIdContract.tableReview.TABLE_NAME, UserIdContract.tableReview.COLUMN_REVIEWID)) {
//                    String newFoodName = "Grillimakkaroita";
//                    float newFoodPrice = 5.40f;
//                    int newRestaurantId = 5;
//                    dbms.setNewFood(newFoodName, newFoodPrice, newRestaurantId,newFoodDate);
//                }
//                break;
///* This is day 5                            ***                                 ***                             ****                        ****/
//            case 5:
//                break;
//            case 6:
//                break;
//            case 7:
//                break;
//            case 8:
//                break;
//            case 9:
//                break;
//            case 10:
//                break;


        }
    }

}
