package com.ezrabathini.bowled.utilities;

/**
 * Created by ezra on 14/02/17.
 */

public enum MatchStatus {
    LIVE,
    COMPLETED,
    UPCOMING,
    NONE;

    @Override
    public String toString() {
        switch (this) {
            case LIVE:
                return "LIVE";
            case COMPLETED:
                return "COMPLETED";
            case UPCOMING:
                return "UPCOMING";
            case NONE:
                return "NONE";
        }
        return null;
    }
}
