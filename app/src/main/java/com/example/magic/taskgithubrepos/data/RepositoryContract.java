package com.example.magic.taskgithubrepos.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Magician on 12/24/2017.
 * RepositoryContract
 */

public class RepositoryContract {
    public static final String CONTENT_AUTHORITY = "com.example.magic.taskgithubrepos";
    /*
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider for Sunshine.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_REPOSITORY = "repository";

    public static final class RepositoryEntry implements BaseColumns {
        /* The base CONTENT_URI used to query the repository table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_REPOSITORY).build();

        public static final String TABLE_NAME = "repository";
        public static final String COLUMN_REPOS_NAME = "name";
        public static final String COLUMN_REPOS_DESCRIPTION = "description";
        public static final String COLUMN_REPOS_OWNER= "owner";
        public static final String COLUMN_REPOS_URL_= "repositoryUrl";
        public static final String COLUMN_REPOS_OWNER_URL= "ownerUrl";
        public static final String COLUMN_REPOS_FORK= "fork";
    }

}
