package edu.neu.madcourse.beatbeat_team22;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LeaderboardViewHolder extends RecyclerView.ViewHolder{
    public TextView userName;

    RelativeLayout parentLayout;

    public LeaderboardViewHolder(View linkView, final LeaderboardCardClickListener listener) {
        super(linkView);
        userName = linkView.findViewById(R.id.UserName);

        linkView.setOnClickListener(view -> {
            if(listener != null) {
                int position = getLayoutPosition();
                if(position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);

                }
            }
        });
        parentLayout = linkView.findViewById(R.id.LeaderboardCardConstraintLayout);

    }
}
