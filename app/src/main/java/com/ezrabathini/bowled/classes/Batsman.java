package com.ezrabathini.bowled.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

/**
 * Created by ezra on 25/02/17.
 */

public class Batsman {
    int id;
    String name;
    String runsScored = "0";
    String ballsFaced = "0";
    String foursHit = "0";
    String sixesHit = "0";
    String strikeRate = "0";
    String fallOfWicket = "0";
    String fallOfWicketOver = "0";
    String fowOrder = "0";
    String howOut = "To Bat";
    boolean isTopScorer = false;
    int indexForPartnership = 0;
    // TODO: 25/02/17 batsman imageURL

    public Batsman(int id, String name, String runsScored, String ballsFaced) {
        this.id = id;
        this.name = name;
        this.runsScored = runsScored;
        this.ballsFaced = ballsFaced;
    }

    private static Batsman batsmanFromJsonObject(JSONObject batsmanJsonObject) {
        Batsman batsman = null;

        try {
            int id = batsmanJsonObject.getInt("id");
            String name = batsmanJsonObject.getString("name");
            String runsScored = batsmanJsonObject.getString("runs");
            String ballsFaced = batsmanJsonObject.getString("balls");
            batsman = new Batsman(id, name, runsScored, ballsFaced);

            batsman.foursHit = batsmanJsonObject.getString("fours");
            batsman.sixesHit = batsmanJsonObject.getString("sixes");
            batsman.strikeRate = batsmanJsonObject.getString("strikeRate");
            batsman.howOut = batsmanJsonObject.getString("howOut");
            // TODO: 25/02/17 fallOfWicket, fowOrder 

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "batsmanFromJsonObject: " + batsman);
        return batsman;
    }

    public static ArrayList<Batsman> batsmanArrayListFromJson(JSONArray batsmanJsonArray) {
        ArrayList<Batsman> batsmen = null;

        if (batsmanJsonArray.length() > 0) {
            for (int i = 0; i < batsmanJsonArray.length(); i++) {
                try {
                    JSONObject batsmanObject = batsmanJsonArray.getJSONObject(i);
                    Batsman batsman = batsmanFromJsonObject(batsmanObject);
                    if (batsmen == null) {
                        batsmen = new ArrayList<Batsman>(0);
                    }
                    batsmen.add(batsman);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return batsmen;
    }

}
