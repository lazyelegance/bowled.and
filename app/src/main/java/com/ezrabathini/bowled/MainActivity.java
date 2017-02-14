package com.ezrabathini.bowled;

import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ezrabathini.bowled.utilities.Match;
import com.ezrabathini.bowled.utilities.MatchUtils;
import com.ezrabathini.bowled.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList> {

    private RecyclerView mRecyclerView;
    private MatchListAdapter mMatchListAdapter;
    private ProgressBar mLoadingIndicator;
    private static final int BOWLED_MACTHES_LOADER = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_matches);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMatchListAdapter = new MatchListAdapter();

        mRecyclerView.setAdapter(mMatchListAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        getResources().getString(R.string.bowledServiceMatchesURL);
        getSupportLoaderManager().initLoader(BOWLED_MACTHES_LOADER, null, this);
        loadMatchData();

    }

    private void loadMatchData() {
        Bundle queryBundle = new Bundle();
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(BOWLED_MACTHES_LOADER, queryBundle, this);
    }

    @Override
    public Loader<ArrayList> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList>(this) {

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    Log.d("ERRRRR", "onStartLoading: args are null (!?)");
                    return;
                }

                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public ArrayList loadInBackground() {
                try {

                    String jsonMatchResponse = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl());
                    ArrayList<Match> simpleJsonMatchData = MatchUtils.getSimpleMatchListFromJson(MainActivity.this, jsonMatchResponse);
                    return simpleJsonMatchData;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMatchListAdapter.setMatchData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
    }
}

