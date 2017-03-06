package com.ezrabathini.bowled.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by ezra on 20/02/17.
 */

public class Innings {

    int id;
    String name;
    int teamId;
    String teamShortName;
    String wicket = "0";
    String run = "0";
    String over = "0";
    String extra = "0";
    String bye = "0";
    String legBye = "0";
    String wide = "0";
    String noBall = "0";
    String runRate = "0.00";
    String requiredRunRate = "";
    ArrayList<Batsman> batsmen;
    ArrayList<Bowler> bowlers;

    public Innings(int id, String name, int teamId, String teamShortName) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
        this.teamShortName = teamShortName;
    }


    public static Innings getInningsFromJson(JSONObject inningsJson) {
        Innings innings = null;
        try {
            int id = inningsJson.getInt("id");
            String name = inningsJson.getString("name");
            JSONObject team = inningsJson.getJSONObject("team");
            int teamId = team.getInt("id");
            String teamShortName = team.getString("shortName");

            innings = new Innings(id, name, teamId, teamShortName);

            innings.wicket = inningsJson.getString("wicket");
            innings.run = inningsJson.getString("run");
            innings.over = inningsJson.getString("over");
            innings.extra = inningsJson.getString("extra");
            innings.bye = inningsJson.getString("bye");
            innings.legBye = inningsJson.getString("legBye");
            innings.wide = inningsJson.getString("wide");
            innings.noBall = inningsJson.getString("noBall");
            innings.runRate = inningsJson.getString("runRate");
            innings.requiredRunRate = inningsJson.getString("requiredRunRate");

            JSONArray batsmenJsonArray = inningsJson.getJSONArray("batsmen");
            innings.batsmen = Batsman.batsmanArrayListFromJson(batsmenJsonArray);
            JSONArray bowlersJsonArray = inningsJson.getJSONArray("bowlers");
            innings.bowlers = Bowler.bowlerArrayListFromJson(bowlersJsonArray);


        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "getInningsFromJson: " + innings.teamShortName + " -> " + innings.run + "/" + innings.wicket);

        for (Batsman batsman : innings.batsmen ) {
            Log.i(TAG, "getInningsFromJson: " + batsman.name + " - " + batsman.runsScored + " (" + batsman.ballsFaced + ") - " + batsman.strikeRate + " - ");
        }
        for (Bowler bowler : innings.bowlers ) {
            Log.i(TAG, "getInningsFromJson: " + bowler.name + " - " + bowler.overs + " /" + bowler.wickets + " - " + bowler.economy + " - ");
        }
        return innings;
    }

}
