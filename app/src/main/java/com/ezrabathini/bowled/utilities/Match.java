package com.ezrabathini.bowled.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezra on 13/02/17.
 */

public class Match {
    public Integer matchId;
    public Integer seriesId;
    public String seriesName;
    public String matchName;
    public String matchStatus;
    public String matchType;
    public Team homeTeam;
    public Team awayTeam;
    public Boolean isMultiday;
    public Boolean isWomensMatch;
    public Boolean hasScore;

    public Match(Integer matchId, Integer seriesId, String matchStatus, Team homeTeam, Team awayTeam) {
        this.matchId = matchId;
        this.seriesId = seriesId;
        this.matchStatus = matchStatus;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }


    public static ArrayList<Match> matchesFromJsonArray(JSONArray matchesArray) {
        ArrayList<Match> matches = new ArrayList<Match>();
//        Match[] matches = null;
        if (matchesArray.length() > 0) {
            for (int i=0; i < matchesArray.length(); i++) {
                try {
                    JSONObject matchObject = matchesArray.getJSONObject(i);
                    Match match = Match.matchFromJsonObject(matchObject);
                    matches.add(match);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        return  matches;
    }

    static Match matchFromJsonObject(JSONObject matchObject) {
        Match match = null;

        try {
            Integer matchId = matchObject.getInt("id");
            String matchStatus = matchObject.getString("status").toUpperCase();
            String matchName = matchObject.getString("name").toUpperCase();
            String matchType = matchObject.getString("cmsMatchType");

            JSONObject homeTeamObject = matchObject.getJSONObject("homeTeam");
            JSONObject awayTeamObject = matchObject.getJSONObject("awayTeam");


            Team homeTeam = Team.teamFromJSONObject(homeTeamObject);
            Team awayTeam = Team.teamFromJSONObject(awayTeamObject);

            JSONObject seriesObject = matchObject.getJSONObject("series");

            Integer seriesId = seriesObject.getInt("id");
            String seriesName = seriesObject.getString("name").toUpperCase();

            match = new Match(matchId, seriesId, matchStatus, homeTeam, awayTeam);
            match.seriesName = seriesName;

            //TODO rest of the match attributes


        } catch (Exception e) {
            e.printStackTrace();
        }

        return match;
    }


}
