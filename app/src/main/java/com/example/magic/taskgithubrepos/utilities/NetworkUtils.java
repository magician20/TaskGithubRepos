package com.example.magic.taskgithubrepos.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Magician on 12/24/2017.
 * NetworkUtils
 */

public final class NetworkUtils {
    public static final String LOG_TAG = NetworkUtils.class.getName();

    public final static String BASE_URL = "https://api.github.com/";
    public final static String SQUARE = "users/square/repos";
    public final static String PAGE_PARAM = "?page=";//for ?page=
    public final static String PAGE = "page";
    public final static int ITEMS_PER_PAGE = 10;  // https://api.github.com/users/square/repos?page=2&per_page=1
    public final static String PER_PAGE_PARAM = "&per_page=";


    private NetworkUtils() {
    }

    //method to determine whether the device has internet connection
    public static boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    /**
     * Helper method to create {@link}URL object.
     *
     * @param url takes a String url,the string comes from Uri.Builder
     * @return URL object
     */
    @Nullable
    public static URL createURL(String url) {
        URL Url = null;
        try {
            Url = new URL(url);

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException: " + e.getMessage());
        }
        return Url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param repoUrl The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL repoUrl) throws IOException {
        if (repoUrl == null) {
            return null;
        }
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        String response = null;
        try {
            urlConnection = (HttpURLConnection) repoUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(20000);//millisecond 20sec
            urlConnection.setConnectTimeout(40000); //millisecond 40sec
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                response = convertStreamToString(inputStream);
            } else {// if we there's no more data what is returned?
                Log.e(LOG_TAG, "URL Error " + urlConnection.getResponseCode());
            }

        } finally {
            //close connection then
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            //close inputStream
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String convertStreamToString(InputStream is) throws IOException {
        String response = null;
        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        if (scanner.hasNext()) {
            response = scanner.next();
        }
        scanner.close();
        return response;
    }

}
