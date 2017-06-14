package com.hfad.learnmachinelearning;

/**
 * Created by Aniket on 10-Jun-2017.
 */

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

public class MachineLearningDatabaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "machinelearning"; // the name of our database
    private static final int DB_VERSION = 3; // the version of the database

    MachineLearningDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }
    private static void insertMainTopics(SQLiteDatabase db, String name){
        ContentValues maintopicValues = new ContentValues();
        maintopicValues.put("NAME", name);
        db.insert("MAIN_TOPICS", null, maintopicValues);
    }

    private static void insertSubTopics(SQLiteDatabase db, int main_topic_id, String name){
        ContentValues subtopicValues = new ContentValues();
        subtopicValues.put("MAIN_TOPIC_ID", main_topic_id);
        subtopicValues.put("NAME", name);
        db.insert("SUB_TOPICS", null, subtopicValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE MAIN_TOPICS (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)");
            insertMainTopics(db, "INTRODUCTION");
            insertMainTopics(db, "DATA PREPROCESSING");
            insertMainTopics(db, "REGRESSION");
            insertMainTopics(db, "CLASSIFICATION");

            db.execSQL("CREATE TABLE SUB_TOPICS (_id INTEGER PRIMARY KEY AUTOINCREMENT, MAIN_TOPIC_ID INT, NAME TEXT)");
            insertSubTopics(db, 0, "What is Machine Learning?");
            insertSubTopics(db, 0, "Unsupervised Learning");
            insertSubTopics(db, 0, "Supervised Learning");
            insertSubTopics(db, 1, "Get the Dataset");
            insertSubTopics(db, 1, "Importing the Libraries");
            insertSubTopics(db, 1, "Importing the Dataset");
            insertSubTopics(db, 1, "Missing Data");
        }
        if(oldVersion < 2){
            insertSubTopics(db, 2, "Simple Regression");
            insertSubTopics(db, 2, "Multiple Regression");
            insertSubTopics(db, 2, "Polynomial Regression");
            insertSubTopics(db, 2, "Support Vector Regression");
        }
        if(oldVersion < 3){
            insertSubTopics(db, 3, "Logistic Regression");
            insertSubTopics(db, 3, "K Nearest Neighour");
            insertSubTopics(db, 3, "Support Vector Machines");
        }
    }
}
