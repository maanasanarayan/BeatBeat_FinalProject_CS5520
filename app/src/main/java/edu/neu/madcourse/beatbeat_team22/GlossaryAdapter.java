package edu.neu.madcourse.beatbeat_team22;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GlossaryAdapter extends RecyclerView.Adapter<GlossaryViewHolder>{
    private List<LeaderboardCard> users;
    private LeaderboardCardClickListener listener;
    private AppCompatActivity mContext;

    public GlossaryAdapter(AppCompatActivity context, List<LeaderboardCard> users) {
        this.users = users;
        this.mContext = context;
    }

    public void setLinkListener(LeaderboardCardClickListener listener) {
        this.listener = listener;
    }

    @Override
    public GlossaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_card_layout,parent,false);
        return new GlossaryViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(GlossaryViewHolder holder, int position) {
        LeaderboardCard currUser = users.get(position);
        holder.userName.setText(currUser.getUserName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
