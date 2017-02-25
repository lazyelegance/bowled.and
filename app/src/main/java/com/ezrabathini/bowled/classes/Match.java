package com.ezrabathini.bowled.classes;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.ezrabathini.bowled.utilities.MatchStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by ezra on 13/02/17.
 */



public class Match implements Parcelable {
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

    private Match(Parcel in) {
        String[] stringArray = new String[7];
        int[] intArray = new int[4];

        in.readStringArray(stringArray);
        in.readIntArray(intArray);

        // FIXME: 15/02/17 test
        this.seriesName = stringArray[0];
        this.matchId = intArray[0];
        this.seriesId = intArray[1];

        this.homeTeam = new Team(intArray[2], stringArray[2]);
        this.awayTeam = new Team(intArray[3], stringArray[3]);
        this.matchSummary = stringArray[1];
        this.hometeamScore = stringArray[4];
        this.awayteamScore = stringArray[5];

        switch (stringArray[6]) {
            case "LIVE":
                this.matchStatus = MatchStatus.LIVE;
            case "COMPLETED":
                this.matchStatus = MatchStatus.COMPLETED;
            case "UPCOMING":
                this.matchStatus = MatchStatus.UPCOMING;
            case "NONE":
                this.matchStatus = MatchStatus.NONE;
        }
    }

    public static final Parcelable.Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel source) {
            return new Match(source);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[0];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {seriesName, matchSummary, homeTeam.name, awayTeam.name, hometeamScore, awayteamScore, matchStatus.toString()});
        dest.writeIntArray(new int[] {matchId, seriesId, homeTeam.teamId, awayTeam.teamId});
//        dest.writeBooleanArray(new boolean[] {isMultiday, isWomensMatch, homeTeam.isBatting, awayTeam.isBatting});
    }
}
