package com.example.magic.taskgithubrepos.utilities;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.magic.taskgithubrepos.data.Repository;
import com.example.magic.taskgithubrepos.utilities.Constants.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by magic on 12/24/2017.
 * ReposJsonUtils
 */

public final class ReposJsonUtils {
    private static final String LOG_TAG = ReposJsonUtils.class.getName();

    private static List<Repository> sRepos = null;

    /**
     * This method parses JSON from a web response and returns an array of Repository.
     *
     * @param context    for access data preference .
     * @param requestUrl URL string.
     * @return List
     */
    public static List<Repository> fetchReposData(Context context, String requestUrl) {
        // Making a request to url and getting response
        //create the URL For request server
        URL repoURL = NetworkUtils.createURL(requestUrl);
        if (repoURL == null) {
            Log.e(LOG_TAG, "URL not found. ");
            return null;
        }

        // Perform HTTP request to the URL and receive a JSON String response back
        String jsonString = null;
        try {
            jsonString = NetworkUtils.getResponseFromHttpUrl(repoURL);
        } catch (IOException e) {
            Log.w(LOG_TAG, "URLConnection notOpen OR inputStream error: " + e.getMessage());
        }

        //extract information from JSON string
        try {
            sRepos = extractRepos(jsonString);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the Repo JSON results: " + e.getMessage());
        }

        return sRepos;
    }

    /**
     * This method parses JSON string to the data we need it.
     *
     * @param jsonStr JSOn string hold data .
     */

    private static List<Repository> extractRepos(String jsonStr) throws JSONException {
        ArrayList<Repository> reposList = new ArrayList<>();

        if (TextUtils.isEmpty(jsonStr)) {
            Log.w(LOG_TAG, "JSON string is empty. ");
            return null;
        }
        // Create a JSONObject from the JSON response string
        JSONArray root = new JSONArray(jsonStr);
        //Log.v(LOG_TAG, "root data:\n" + root.toString());
        //TODO:Add code to fetch daata here
        for (int i = 0; i < root.length(); i++) {
            JSONObject repos = root.getJSONObject(i);
            String reposName = repos.getString(JSONConstants.REPOS_NAME);//1

            JSONObject ownerObject = repos.getJSONObject(JSONConstants.REPOS_OWNER);
            String ownerName = ownerObject.getString(JSONConstants.REPOS_OWNER_NAME);//2
            String ownerUrlString = ownerObject.getString(JSONConstants.REPOS_OWNER_URL);//3

            String reposUrlString = repos.getString(JSONConstants.REPOS_HTML_URL);//4
            String reposDescription = repos.getString(JSONConstants.REPOS_DESCRIPTION);//5
            Boolean reposFork = repos.getBoolean(JSONConstants.REPOS_FORK);//6

            Repository repository = new Repository
                    (reposName, reposDescription, ownerName, reposFork, reposUrlString, ownerUrlString);
            reposList.add(repository);

        }

        return reposList;
    }

}
