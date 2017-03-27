package com.ezrabathini.bowled;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ezrabathini.bowled.data.StaticData;
import com.ezrabathini.bowled.utilities.BowledUtils;
import com.ezrabathini.bowled.classes.Match;
import com.ezrabathini.bowled.utilities.NetworkUtils;

import java.util.ArrayList;

public class MatchDetail extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList> {

    private static String tag;

    private TextView mHometeamName;
    private TextView mAwayteamName;
    private TextView mHometeamScore;
    private TextView mAwayteamScore;

    private TextView mSeriesName;
    private TextView mMatchStatus;

    private ProgressBar mLoadingIndicator;
    private static final int BOWLED_SCORECARD_LOADER = 33;

    private Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        tag = getClass().getSimpleName();

        mHometeamName = (TextView) findViewById(R.id.include_match_header_teamOneName);
        mAwayteamName = (TextView) findViewById(R.id.include_match_header_teamTwoName);
        mHometeamScore = (TextView) findViewById(R.id.include_match_header_teamOneScore);
        mAwayteamScore = (TextView) findViewById(R.id.include_match_header_teamTwoScore);
        mSeriesName = (TextView) findViewById(R.id.include_match_header_series);
        mMatchStatus = (TextView) findViewById(R.id.include_match_header_status);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.activty_match_detail_progressBar);

        getSupportLoaderManager().initLoader(BOWLED_SCORECARD_LOADER, null, this);
        Bundle queryBundle = new Bundle();
        LoaderManager loaderManager = getSupportLoaderManager();
        getSupportLoaderManager().restartLoader(BOWLED_SCORECARD_LOADER, queryBundle, this);


        Intent intentPassed = getIntent();
        if (intentPassed.hasExtra("matchTEST")) {
            match = (Match) intentPassed.getParcelableExtra("matchTEST");
            if (match != null) {

                mHometeamName.setText(match.homeTeam.name);
                mAwayteamName.setText(match.awayTeam.name);

                mHometeamScore.setText(match.hometeamScore);
                mAwayteamScore.setText(match.awayteamScore);

                mSeriesName.setText(match.seriesName);
                mMatchStatus.setText(match.matchSummary);

            }


        }


    }

    @Override
    public Loader<ArrayList> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList>(this) {

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    Log.d(tag, "onStartLoading: args are null (!?)");
                    return;
                }

                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public ArrayList loadInBackground() {
                try {
                    String jsonScorecardResponse = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl(StaticData.BW_SCORECARD_URL, match.matchId, match.seriesId));
                    Log.i(tag, "loadInBackground: " + BowledUtils.getScorecardFromJson(MatchDetail.this, jsonScorecardResponse));
//                    return simpleJsonScorecard;
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {

    }
}
