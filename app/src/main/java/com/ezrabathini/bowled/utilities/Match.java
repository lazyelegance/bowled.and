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
    public MatchStatus matchStatus;
    public String matchType;
    public Team homeTeam;
    public Team awayTeam;
    public String hometeamScore;
    public String awayteamScore;
    public Boolean isMultiday;
    public Boolean isWomensMatch;
    public Boolean hasScore;
    public String matchSummary;

    public Match(Integer matchId, Integer seriesId, MatchStatus matchStatus, Team homeTeam, Team awayTeam) {
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

            String matchName = matchObject.getString("name").toUpperCase();
            String matchType = matchObject.getString("cmsMatchType");
            String matchSummary = matchObject.getString("matchSummaryText").toUpperCase();

            JSONObject homeTeamObject = matchObject.getJSONObject("homeTeam");
            JSONObject awayTeamObject = matchObject.getJSONObject("awayTeam");

            Team homeTeam = Team.teamFromJSONObject(homeTeamObject);
            Team awayTeam = Team.teamFromJSONObject(awayTeamObject);

            Boolean hasScore = false;
            String hometeamScore = "";
            String awayteamScore = "";

            if (matchObject.has("scores")) {
                JSONObject scoreObject = matchObject.getJSONObject("scores");
                hasScore = true;
                hometeamScore = scoreObject.getString("homeScore");
                awayteamScore = scoreObject.getString("awayScore");
            }




            JSONObject seriesObject = matchObject.getJSONObject("series");

            Integer seriesId = seriesObject.getInt("id");
            String seriesName = seriesObject.getString("name").toUpperCase();


            String matchStatusText = matchObject.getString("status").toUpperCase();

            MatchStatus matchStatus = MatchStatus.NONE;

            switch (matchStatusText) {
                case "LIVE":
                    matchStatus = MatchStatus.LIVE;
                case "COMPLETED":
                    matchStatus = MatchStatus.COMPLETED;
                case "UPCOMING":
                    matchStatus = MatchStatus.UPCOMING;
                default:
                    break;

            }

            match = new Match(matchId, seriesId, matchStatus, homeTeam, awayTeam);
            match.seriesName = seriesName;

            //TODO rest of the match attributes
            match.hasScore = hasScore;
            match.hometeamScore = hometeamScore;
            match.awayteamScore = awayteamScore;
            match.matchSummary = matchSummary;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return match;
    }


}
