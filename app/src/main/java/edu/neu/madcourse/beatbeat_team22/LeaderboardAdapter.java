package edu.neu.madcourse.beatbeat_team22;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardViewHolder>{
    private List<LeaderboardCard> users;
    private LeaderboardCardClickListener listener;
    private AppCompatActivity mContext;

    public LeaderboardAdapter(AppCompatActivity context, List<LeaderboardCard> users) {
        this.users = users;
        this.mContext = context;
    }

    public void setLinkListener(LeaderboardCardClickListener listener) {
        this.listener = listener;
    }

    @Override
    public LeaderboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_card_layout,parent,false);
        return new LeaderboardViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(LeaderboardViewHolder holder, int position) {
        LeaderboardCard currUser = users.get(position);
        holder.userName.setText(currUser.getUserName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
