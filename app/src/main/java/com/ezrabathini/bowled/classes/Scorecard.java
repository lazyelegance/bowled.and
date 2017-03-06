package com.ezrabathini.bowled.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by ezra on 20/02/17.
 */



public class Scorecard {
    public String status;
    public int inningsCount = 0;
    public ArrayList<Innings> innings;
    public String momName;
    public boolean hasMOM = false;
    public ManOfTheMatch manOfTheMatch;

    public Scorecard(String status) {
        this.status = status;
    }

    public static Scorecard scorecardFromJson(JSONObject scorecardObject) {
        Scorecard scorecard = null;

        try {
            String status = scorecardObject.getString("status");

            JSONObject fullScorecard = scorecardObject.getJSONObject("fullScorecard");
            JSONArray inningsArray = fullScorecard.getJSONArray("innings");
            scorecard = new Scorecard(status);
            scorecard.inningsCount = inningsArray.length();

            for (int i = 0; i < inningsArray.length() ; i++) {
                JSONObject inn = inningsArray.getJSONObject(i);
                Innings innings = Innings.getInningsFromJson(inn);
                if (scorecard.innings == null) {
                    scorecard.innings = new ArrayList<Innings>(0);
                }
                scorecard.innings.add(innings);
            }

            Log.i(TAG, "scorecardFromJson: " + scorecard.innings);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return scorecard;
    }


}
