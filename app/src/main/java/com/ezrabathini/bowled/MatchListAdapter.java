package com.ezrabathini.bowled;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezrabathini.bowled.utilities.Match;

import java.util.ArrayList;

/**
 * Created by ezra on 13/02/17.
 */

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.MatchListAdapterViewHolder> {
    private ArrayList mMatchData;
    public MatchListAdapter() {

    }

    public class MatchListAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mHometeamName;
        public final TextView mAwayteamName;
        public final TextView mHometeamScore;
        public final TextView mAwayteamScore;

        public final TextView mSeriesName;
        public final TextView mMatchStatus;

        public MatchListAdapterViewHolder(View view) {
            super(view);
            mHometeamName = (TextView) view.findViewById(R.id.tv_hometeam_name);
            mAwayteamName = (TextView) view.findViewById(R.id.tv_awayteam_name);
            mHometeamScore = (TextView) view.findViewById(R.id.tv_hometeam_score);
            mAwayteamScore = (TextView) view.findViewById(R.id.tv_awayteam_score);
            mSeriesName = (TextView) view.findViewById(R.id.tv_series_name);
            mMatchStatus = (TextView) view.findViewById(R.id.tv_match_status);
        }
    }

    @Override
    public MatchListAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.match_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MatchListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchListAdapterViewHolder matchListAdapterViewHolder, int position) {
        Match match = (Match) mMatchData.get(position);
        String hometeamName = match.homeTeam.name;
        String awayteamName = match.awayTeam.name;

        matchListAdapterViewHolder.mHometeamName.setText(hometeamName);
        matchListAdapterViewHolder.mAwayteamName.setText(awayteamName);
        matchListAdapterViewHolder.mMatchStatus.setText(match.matchStatus);
        matchListAdapterViewHolder.mSeriesName.setText(match.seriesName);
    }

    @Override
    public int getItemCount() {
        if (null == mMatchData) return 0;
        return mMatchData.size();
    }

    public void setMatchData(ArrayList matchData) {
        mMatchData = matchData;
        notifyDataSetChanged();
    }
}
