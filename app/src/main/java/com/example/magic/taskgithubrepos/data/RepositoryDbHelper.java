package com.example.magic.taskgithubrepos.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.magic.taskgithubrepos.data.RepositoryContract.RepositoryEntry;

/**
 * Created by magic on 12/24/2017.
 * RepositoryDbHelper
 */

public class RepositoryDbHelper extends SQLiteOpenHelper {
    /*
    * This is the name of our database.
    */
    public static final String DATABASE_NAME = "gitHub.db";

    private static final int DATABASE_VERSION = 1;

    public RepositoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
/*
         * This String will contain a simple SQL statement that will create a table that will
         * cache our weather data.
         */
        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + RepositoryEntry.TABLE_NAME + " (" +
                        RepositoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RepositoryEntry.COLUMN_REPOS_NAME + " TEXT NOT NULL, " +
                        RepositoryEntry.COLUMN_REPOS_DESCRIPTION + " TEXT NOT NULL," +
                        RepositoryEntry.COLUMN_REPOS_OWNER + " TEXT NOT NULL, " +
                        RepositoryEntry.COLUMN_REPOS_FORK + " BOOLEAN NOT NULL," +
                        RepositoryEntry.COLUMN_REPOS_URL_ + " TEXT NOT NULL, " +
                        RepositoryEntry.COLUMN_REPOS_OWNER_URL + " TEXT NOT NULL, ";

        /*
         * After we've spelled out our SQLite table creation statement above, we actually execute
         * that SQL with the execSQL method of our SQLite database object.
         */
        db.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RepositoryEntry.TABLE_NAME);
        onCreate(db);
    }
}
