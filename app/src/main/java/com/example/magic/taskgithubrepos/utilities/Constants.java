package com.example.magic.taskgithubrepos.utilities;

import android.net.Uri;

/**
 * Created by magic on 12/26/2017.
 * contain json data names && Network Constants
 */

public final class Constants {
    private Constants() {
    }
    /*
     * First Network URL Constants
     */

    public static final class NetworkConstants {

        public final static String BASE_URL = "https://api.github.com/";
        public final static String SQUARE = "users/square/repos";
        public final static String PAGE_PARAM = "page";//for ?page=2&per_page=1
        public final static String PAGE_NUMBER = "1";
        public final static String PER_PAGE_PARAM = "per_page";
        public final static String ITEMS_PER_PAGE = "10";
        public final static int NUMBER_OF_ITEMS = 10;


        //Uri= https://api.github.com/users/square/repos?page=2&per_page=1
        public static String formatUri() {
            Uri BASE_URL_SQUARE = Uri.parse(BASE_URL + SQUARE);
            Uri.Builder builder = BASE_URL_SQUARE.buildUpon()
                    .appendQueryParameter(PAGE_PARAM, PAGE_NUMBER)
                    .appendQueryParameter(PER_PAGE_PARAM, ITEMS_PER_PAGE);
            return builder.build().toString();
        }
        public static String formatUri(int pageNumber) {
            Uri BASE_URL_SQUARE = Uri.parse(BASE_URL + SQUARE);
            Uri.Builder builder = BASE_URL_SQUARE.buildUpon()
                    .appendQueryParameter(PAGE_PARAM, String.valueOf(pageNumber))
                    .appendQueryParameter(PER_PAGE_PARAM, ITEMS_PER_PAGE);
            return builder.build().toString();
        }

    }

    /*
     * Seconnd Network URL Constants
     */
    public static final class JSONConstants {
        public final static String REPOS_NAME = "name";
        public final static String REPOS_OWNER = "owner";
        public final static String REPOS_OWNER_NAME = "login";
        public final static String REPOS_OWNER_URL = "html_url";
        public final static String REPOS_HTML_URL = "html_url";
        public final static String REPOS_DESCRIPTION = "description";
        public final static String REPOS_FORK = "fork";

    }


}
