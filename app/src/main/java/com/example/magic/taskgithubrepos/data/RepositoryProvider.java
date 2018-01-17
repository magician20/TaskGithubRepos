package com.example.magic.taskgithubrepos.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.magic.taskgithubrepos.data.RepositoryContract.RepositoryEntry;

public class RepositoryProvider extends ContentProvider {
    private static final int CODE_REPOSITORY = 100;
    private static final int CODE_REPOSITORY_WITH_ID = 101;

    private static UriMatcher sUriMatcher = buildUriMatcher();
    private RepositoryDbHelper mDbHelper;

    /*
    * @return A UriMatcher that correctly matches the constants for CODE_WEATHER and CODE_WEATHER_WITH_DATE
    */
    public static android.content.UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(android.content.UriMatcher.NO_MATCH);
        final String authority = RepositoryContract.CONTENT_AUTHORITY;

        /*
         * For each type of URI you want to add, create a corresponding code.
         */

        /* This URI is content://com.example.magic.taskgithubrepos/repository/ */
        matcher.addURI(authority, RepositoryContract.PATH_REPOSITORY, CODE_REPOSITORY);

        /*
         * This URI would look something like content://com.example.android.sunshine/weather/5
         * The "/#" signifies to the UriMatcher that if PATH_WEATHER is followed by ANY number,
         * that it should return the CODE_REPOSITORY_WITH_ID code
         */
        matcher.addURI(authority, RepositoryContract.PATH_REPOSITORY + "/#", CODE_REPOSITORY_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new RepositoryDbHelper(getContext());
        return true;
    }

    public RepositoryProvider() {
    }

    /**
     * Handles query requests from clients. We will use this method in app to query for all
     * of our Repos data as well as to query for the repos on a particular Id this used if we
     * want to show the dialog with urls.
     *
     * @param uri           The URI to query
     * @param projection    The list of columns to put into the cursor. If null, all columns are
     *                      included.
     * @param selection     A selection criteria to apply when filtering rows. If null, then all
     *                      rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *                      the values from selectionArgs, in order that they appear in the
     *                      selection.
     * @param sortOrder     How the rows in the cursor should be sorted.
     * @return A Cursor containing the results of the query. In our implementation,
     */

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            /*
             * When sUriMatcher's match method is called with a URI that looks EXACTLY like this
             *    /* This URI is content://com.example.magic.taskgithubrepos/repository/*
             */
            case CODE_REPOSITORY: {
                cursor = mDbHelper.getReadableDatabase().query(
                        RepositoryContract.RepositoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            /*
              * This URI would look something like content://com.example.android.sunshine/weather/5
             */
            case CODE_REPOSITORY_WITH_ID: {
                cursor = mDbHelper.getReadableDatabase().query(
                        RepositoryContract.RepositoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
         /* Users of the delete method will expect the number of rows deleted to be returned. */
        int numRowsDeleted;

        /*
         * If we pass null as the selection to SQLiteDatabase#delete, our entire table will be
         * deleted. However, if we do pass null and delete all of the rows in the table, we won't
         * know how many rows were deleted. According to the documentation for SQLiteDatabase,
         * passing "1" for the selection will delete all rows and return the number of rows
         * deleted, which is what the caller of this method expects.
         */
        if (selection == null) selection = "1";

        switch (sUriMatcher.match(uri)) {

            case CODE_REPOSITORY:
                numRowsDeleted = mDbHelper.getWritableDatabase().delete(
                        RepositoryContract.RepositoryEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /* If we actually deleted any rows, notify that a change has occurred to this URI */
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
