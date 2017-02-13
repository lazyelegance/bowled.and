package com.ezrabathini.bowled.utilities;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezra on 13/02/17.
 */

public class MatchUtils {
    public static ArrayList<Match> getSimpleMatchListFromJson(Context context, String matchesJsonStr) throws JSONException {
        ArrayList<Match> parsedMatchData;

        JSONObject resultsJson = new JSONObject(matchesJsonStr);
        JSONObject matchesJson = resultsJson.getJSONObject("matchList");
        JSONArray matchesArray = matchesJson.getJSONArray("matches");

//        Log.d("ONE.FIVE", "getSimpleMatchListFromJsonLENGTH:   + matchesArray.length());
//        Log.d("ONE", "getSimpleMatchListFromJson: " + matchesArray);

        parsedMatchData = Match.matchesFromJsonArray(matchesArray);
        Log.d("SIZE", "getSimpleMatchListFromJson: " + parsedMatchData.size());
        Log.d("DATA", "getSimpleMatchListFromJson: " + parsedMatchData);
        return parsedMatchData;
    }
}
