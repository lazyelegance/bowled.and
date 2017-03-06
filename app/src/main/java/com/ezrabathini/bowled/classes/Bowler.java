package com.ezrabathini.bowled.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by ezra on 25/02/17.
 */

/*
 var id: NSNumber
    var name: String
    var overs: String
    var maidens: String
    var runsConceded: String
    var wides = "0"
    var noBalls = "0"
    var wickets: String
    var ecomony: String
    var isTopWickettaker = false

    init(id: NSNumber, name: String, overs: String, maidens: String, runsConceded: String, wickets: String, economy: String) {
        self.id = id
        self.name = name
        self.overs = overs
        self.runsConceded = runsConceded
        self.maidens = maidens
        self.ecomony = economy
        self.wickets = wickets
    }
 */

public class Bowler {
    int id;
    String name;
    String overs = "0";
    String maidens = "0";
    String runsConceded = "0";
    String wides = "0";
    String noBalls = "0";
    String wickets = "0";
    String economy = "0";
    boolean isTopWicketTaker = false;

    // TODO: 25/02/17 bowler imageURL

    public Bowler(int id, String name, String overs,String maidens, String runsConceded, String wickets, String economy) {
        this.id = id;
        this.name = name;
        this.overs = overs;
        this.maidens = maidens;
        this.runsConceded = runsConceded;
        this.wickets = wickets;
        this.economy = economy;

    }

    private static Bowler bowlerFromJsonObject(JSONObject bowlerJsonObject) {
        Bowler bowler = null;

        try {
            int id = bowlerJsonObject.getInt("id");
            String name = bowlerJsonObject.getString("name");
            String overs = bowlerJsonObject.getString("overs");
            String maidens = bowlerJsonObject.getString("maidens");
            String runsConceded = bowlerJsonObject.getString("runsConceded");
            String wickets = bowlerJsonObject.getString("wickets");
            String ecomony = bowlerJsonObject.getString("economy");

            bowler = new Bowler(id, name, overs, maidens, runsConceded, wickets, ecomony);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bowler;
    }

    public static ArrayList<Bowler> bowlerArrayListFromJson(JSONArray bowlerJsonArray) {
        ArrayList<Bowler> bowlers = null;

        if (bowlerJsonArray.length() > 0) {
            for (int i = 0; i < bowlerJsonArray.length(); i++) {
                try {
                    JSONObject bowlerObject = bowlerJsonArray.getJSONObject(i);
                    Bowler bowler = bowlerFromJsonObject(bowlerObject);
                    if (bowlers == null) {
                        bowlers = new ArrayList<Bowler>(0);
                    }
                    bowlers.add(bowler);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return bowlers;
    }
}
