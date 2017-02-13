package com.ezrabathini.bowled;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ezrabathini.bowled.utilities.Match;
import com.ezrabathini.bowled.utilities.MatchUtils;
import com.ezrabathini.bowled.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MatchListAdapter mMatchListAdapter;
    private ProgressBar mLoadingIndicator;

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
        loadMatchData();

    }

    private void loadMatchData() {
        new BowledServiceTask().execute(NetworkUtils.buildUrl());
    }

    public class BowledServiceTask extends AsyncTask<URL, Void, ArrayList> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }



        @Override
        protected ArrayList<Match> doInBackground(URL... urls) {
            ArrayList<Match> matchResults = null;

            try {
                String jsonMatchResponse = NetworkUtils.getResponseFromHttpUrl(urls[0]);
                ArrayList<Match> simpleJsonMatchData = MatchUtils.getSimpleMatchListFromJson(MainActivity.this, jsonMatchResponse);
                return simpleJsonMatchData;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return matchResults;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mMatchListAdapter.setMatchData(arrayList);
        }

    }
}

