package com.ezrabathini.bowled.utilities;

import android.content.Context;
import android.util.Log;

import com.ezrabathini.bowled.classes.Match;
import com.ezrabathini.bowled.classes.Scorecard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezra on 13/02/17.
 */

public class BowledUtils {
    public static ArrayList<Match> getMatchListFromJson(Context context, String matchesJsonStr) throws JSONException {
        ArrayList<Match> parsedMatchData;
        // TODO: 14/02/17 check failure condition 
        
        JSONObject resultsJson = new JSONObject(matchesJsonStr);
        JSONObject matchesJson = resultsJson.getJSONObject("matchList");
        JSONArray matchesArray = matchesJson.getJSONArray("matches");
        parsedMatchData = Match.matchesFromJsonArray(matchesArray);
        return parsedMatchData;
    }

    // TODO: 14/02/17 other methods to handle matchdetails, scorecard etc

    public static ArrayList<String> getScorecardFromJson(Context context, String scrorecardJsonStr) throws JSONException {

        // TODO: 14/02/17 check failure condition

        JSONObject resultsJson = new JSONObject(scrorecardJsonStr);

//        Log.i("SCORECARD", "getScorecardFromJson: " + resultsJson);
        final Scorecard scorecard = Scorecard.scorecardFromJson(resultsJson);

//        JSONObject matchesJson = resultsJson.getJSONObject("matchList");
//        JSONArray matchesArray = matchesJson.getJSONArray("matches");
//
////        Log.d("ONE.FIVE", "getSimpleMatchListFromJsonLENGTH:   + matchesArray.length());
////        Log.d("ONE", "getSimpleMatchListFromJson: " + matchesArray);
//
//        parsedMatchData = Match.matchesFromJsonArray(matchesArray);
//        Log.d("SIZE", "getSimpleMatchListFromJson: " + parsedMatchData.size());
//        Log.d("DATA", "getSimpleMatchListFromJson: " + parsedMatchData);
//        return parsedMatchData;
        return  null;
    }
}
