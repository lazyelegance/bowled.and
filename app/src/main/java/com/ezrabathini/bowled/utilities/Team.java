package com.ezrabathini.bowled.utilities;

import org.json.JSONObject;

/**
 * Created by ezra on 13/02/17.
 */

public class Team {
    public String name;
    public String shortName;
    public String logoURL;
    public String teamColor;
    public Boolean isBatting;
    public Integer teamId;

    public Team(Integer teamId, String name) {
        this.name = name;
        this.shortName = name;
        this.teamId = teamId;
        this.isBatting = false;
        this.logoURL = "";
        this.teamColor = "#ffffff";
    }

    public static Team teamFromJSONObject(JSONObject teamObject) {
        Team team = null;

        try {

            String name = teamObject.getString("name");
            if (name.endsWith("Men")) {
               name = name.replace(" Men", "");
            }else if (name.endsWith("WBBL")) {
                name = name.replace("WBBL", "Women");
            } else if (name.endsWith("BBL")) {
                name = name.replace(" BBL", "");
            }

            Boolean isBatting = teamObject.getBoolean("isBatting");
            String shortName = teamObject.getString("shortName").toUpperCase();
            Integer teamId = teamObject.getInt("id");

            team = new Team(teamId, name.toUpperCase());
            team.isBatting = isBatting;
            team.shortName = shortName;

            //// TODO: 13/02/17 rest of the team attributes -- logoUrl, teamColor
        } catch (Exception e) {
            e.printStackTrace();
        }

        return team;
    }
}
