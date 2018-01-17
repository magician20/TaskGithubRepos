package com.example.magic.taskgithubrepos.sync;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.magic.taskgithubrepos.data.Repository;
import com.example.magic.taskgithubrepos.utilities.Constants;
import com.example.magic.taskgithubrepos.utilities.ReposJsonUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by Magician on 12/25/2017.
 * RepositoryLoader
 */

public class RepositoryLoader extends AsyncTaskLoader<List<Repository>> {
    //Tag for log messages
    private static final String LOG_TAG = RepositoryLoader.class.getName();

    private String mUrlString;
    //store  data retrived
    private List<Repository> mRepositoryList;

    public RepositoryLoader(Context context, String url) {
        super(context);
        mUrlString = url;
        Log.i(LOG_TAG, "Call RepositoryLoader Constructor ");
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "Call onStartLoading ");
        if (isReset()) {
        // rebuild this string with new next data (by uri)
          mUrlString=Constants.NetworkConstants.formatUri();//then pass the new string here
        }
        forceLoad();
    }

    @Override
    public List<Repository> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null.or there's no more data to fetch
        if (mUrlString == null) {
            Log.e(LOG_TAG, "reposList is null.");
            return null ;// Collections.emptyList() here this is better code(NPE)
        }

        // Perform the network request, parse the response, and extract a list of Repos.
        List<Repository> repositoryList = ReposJsonUtils.fetchReposData(getContext(), mUrlString);
//        // if repositoryList==null and isReset() we end process ,better if i can do it in fetchReposData
//        if (repositoryList == null &&isReset()) {
//            cancelLoadInBackground();
//        }
        assert repositoryList != null;
        Log.i(LOG_TAG, "loadInBackground" + repositoryList.toString());
        return repositoryList;
    }

    /*
    *   this method will deliver the data with new change(that change is add the new data to the old one )
    *   everytime I run the background .this data is need to update here because of orientation do mRepositoryList
    *   still have the last data before orientation happended
    *
    * @param  data  the new data that comming from background
    * */
    @Override
    public void deliverResult(List<Repository> data) {
        if (isReset()) {
            // We need to save the new result with the old one.
            if (data != null) {
                mRepositoryList.addAll(data);//add to the end of this list,
            }
            // no data here mean the uri no getting data for reasons like no more data to fetch
            //or url mailframe
            Log.i(LOG_TAG, "deliverResult Reset");
        }

        if (isStarted()) {
            mRepositoryList = data;
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(data);
            Log.i(LOG_TAG, "deliverResult Start");
        }

    }


    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(List<Repository> data) {
        super.onCanceled(data);
        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(data);
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(List<Repository> apps) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'mRepositoryList'
        // if needed.
        if (mRepositoryList != null) {
            onReleaseResources(mRepositoryList);
            mRepositoryList = null;
        }
    }

}
