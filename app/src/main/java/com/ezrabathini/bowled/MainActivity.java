package com.ezrabathini.bowled;

import android.content.Context;
import android.content.Intent;
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

import com.ezrabathini.bowled.data.StaticData;
import com.ezrabathini.bowled.classes.Match;
import com.ezrabathini.bowled.utilities.BowledUtils;
import com.ezrabathini.bowled.utilities.NetworkUtils;
import com.ezrabathini.bowled.utilities.RecyclerItemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList> {

    private RecyclerView mRecyclerView;
    private MatchListAdapter mMatchListAdapter;
    private ProgressBar mLoadingIndicator;
    private static final int BOWLED_MATCHES_LOADER = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerView);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMatchListAdapter = new MatchListAdapter();

        mRecyclerView.setAdapter(mMatchListAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.activity_main_progressBar);
        getSupportLoaderManager().initLoader(BOWLED_MATCHES_LOADER, null, this);
        loadMatchData();

    }

    private void loadMatchData() {
        Bundle queryBundle = new Bundle();
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(BOWLED_MATCHES_LOADER, queryBundle, this);
    }

    @Override
    public Loader<ArrayList> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList>(this) {

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }

                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public ArrayList loadInBackground() {
                try {

                    String jsonMatchResponse = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl(StaticData.BW_MATCHES_URL));
                    ArrayList<Match> simpleJsonMatchData = BowledUtils.getMatchListFromJson(MainActivity.this, jsonMatchResponse);
                    return simpleJsonMatchData;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, final ArrayList data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMatchListAdapter.setMatchData(data);

        final Context context = MainActivity.this;

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, MatchDetail.class);
                Match match = (Match) data.get(position);
                intent.putExtra("matchTEST", match);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
    }
}

