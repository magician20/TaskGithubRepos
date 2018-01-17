package com.example.magic.taskgithubrepos;

import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.magic.taskgithubrepos.adapter.ReposAdapter;
import com.example.magic.taskgithubrepos.data.Repository;
import com.example.magic.taskgithubrepos.dialog.ChoiceDialog;
import com.example.magic.taskgithubrepos.sync.RepositoryLoader;
import com.example.magic.taskgithubrepos.utilities.Constants.*;
import com.example.magic.taskgithubrepos.utilities.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Repository>>,
        ReposAdapter.ReposAdapterOnClickHandler {
    private static final String LOG_TAG = MainActivity.class.getName();

    private RecyclerView mReposRecyclerView;
    private ReposAdapter mReposAdapter;
    private LoaderManager mLoaderManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    private static final int REPOSITORY_LOADER_ID = 1;
//    /*
//   * The columns of data that we are interested in displaying within our MainActivity's list of
//   * Repos data.
//   */
//    public static final String[] MAIN_REPOSITORY_PROJECTION = {
//            RepositoryEntry._ID,
//            RepositoryEntry.COLUMN_REPOS_NAME,
//            RepositoryEntry.COLUMN_REPOS_DESCRIPTION,
//            RepositoryEntry.COLUMN_REPOS_OWNER,
//            RepositoryEntry.COLUMN_REPOS_FORK
//    };
//
//    /*
//     *these indices must be adjusted to match the order of the Strings in projection.
//     */
//    public static final int INDEX_REPOSITORY_ID = 0;
//    public static final int INDEX_REPOSITORY_NAME = 1;
//    public static final int INDEX_REPOSITORY_DESCRIPTION = 2;
//    public static final int INDEX_REPOSITORY_OWNER = 3;
//    public static final int INDEX_REPOSITORY_FORK = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        mLoaderManager = getLoaderManager();

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        //create adapter object
        mReposAdapter = new ReposAdapter(this, this);
        mReposRecyclerView = (RecyclerView) findViewById(R.id.repos_list);

        mReposRecyclerView.setLayoutManager(layoutManager);
        //  mReposRecyclerView.setHasFixedSize(true);
        //animation
        mReposRecyclerView.setItemAnimator(new DefaultItemAnimator());
         /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mReposRecyclerView.setAdapter(mReposAdapter);
  //for framework PullRefreshLayout
//        int[] colors = {Color.rgb(0xFF, 0xFF, 0xFF),
//                Color.rgb(0xAF, 0xAF, 0xAF),
//                Color.rgb(0x83, 0x83, 0x83),
//                Color.rgb(0x00, 0x00, 0x00)};
//        mSwipeRefreshLayout.setColorSchemeColors(colors);//not work
//        mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

           mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.red);

         /*
         * Initializes the CursorLoader. The URL_LOADER value is eventually passed
         * to onCreateLoader().
         */
        if (NetworkUtils.isNetworkConnectionAvailable(this)) {
            mLoaderManager.initLoader(REPOSITORY_LOADER_ID, null, this);
            Log.i(LOG_TAG, "initLoader ");
        } else {
            Toast.makeText(this, "No Connection.", Toast.LENGTH_LONG).show();
        }

    }

    //used to load the new data from the API Data ,this must be run on thread aother than UI thread
    private void refreshContent() {
        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
        //Todo: build refresh method
        mLoaderManager.restartLoader(REPOSITORY_LOADER_ID, null, this);

        //This notifies the SwipeRefreshLayout widget instance that the work we wanted to
        // do in onRefresh completed, and to stop displaying the loader animation.
      mSwipeRefreshLayout.setRefreshing(false);//before u show data use it to>>Disable the refresh icon
    }

    /**
     * This method is called after this activity has been paused or restarted.
     * Often, this is after new data has been inserted through an AddTaskActivity,
     * so this restarts the loader to re-query the underlying data for any changes.
     */

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        // re-queries for all tasks
//        mLoaderManager.restartLoader(REPOSITORY_LOADER_ID, null, this);
//    }
    @Override
    public Loader<List<Repository>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case REPOSITORY_LOADER_ID:
                return new RepositoryLoader(this, NetworkConstants.formatUri());
            default:
                // An invalid id was passed in
                Log.v(LOG_TAG, "An invalid id");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Repository>> loader, List<Repository> data) {
        // Set the new data in the adapter with old one , or if null stay with old one
        if (mLoaderManager.getLoader(REPOSITORY_LOADER_ID).isReset() && data == null) {
            return;
        }
        mReposAdapter.setData(data);

    }

    @Override
    public void onLoaderReset(Loader<List<Repository>> loader) {
        // This is called when the last Repository data provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.

        // Clear the data in the adapter.
        mReposAdapter.setData(null);
    }

    // this click is long click
    @Override
    public void onClick(Repository repos) {
        showChoiceDialog(repos);
    }

    // open new dialog
    private void showChoiceDialog(Repository repos) {
        Bundle args = new Bundle();
        args.putString(getString(R.string.repos_page_key), repos.getRepo_url_string());
        args.putString(getString(R.string.owner_page_key), repos.getOwner_url_string());

        ChoiceDialog choiceDialog = new ChoiceDialog();
        choiceDialog.setArguments(args);
        choiceDialog.show(getFragmentManager(), "ChoiceDialog");
    }

}
