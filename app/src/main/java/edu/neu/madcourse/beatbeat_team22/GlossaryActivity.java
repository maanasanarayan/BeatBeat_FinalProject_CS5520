package edu.neu.madcourse.beatbeat_team22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class GlossaryActivity extends AppCompatActivity {

    LeaderboardUser user;
    List<LeaderboardCard> allUsers = new ArrayList<>();
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";

    private RecyclerView rView;
    private GlossaryAdapter GlossaryAdapter;
    private RecyclerView.LayoutManager layoutManager;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        initializeData(savedInstanceState);
        createRecyclerView();
    }

    private void createRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        rView = findViewById(R.id.LeaderboardRecycler);

        GlossaryAdapter = new GlossaryAdapter(this, allUsers);

        LeaderboardCardClickListener clkListener = new LeaderboardCardClickListener() {
            @Override
            public void onItemClick(int position) {
                GlossaryAdapter.notifyItemChanged(position);
            }
        };

        GlossaryAdapter.setLinkListener(clkListener);
        rView.setAdapter(GlossaryAdapter);
        rView.setLayoutManager(layoutManager);
    }

    private void initializeData(Bundle savedInstanceState) {
        allUsers.add(new LeaderboardCard("Username1"));
        allUsers.add(new LeaderboardCard("Username2"));
        allUsers.add(new LeaderboardCard("Username3"));
        allUsers.add(new LeaderboardCard("Username4"));
        allUsers.add(new LeaderboardCard("Username5"));
        allUsers.add(new LeaderboardCard("Username6"));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = allUsers == null ? 0 : allUsers.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        for (int i = 0; i < size; i++) {
            outState.putString(KEY_OF_INSTANCE + i + "0", allUsers.get(i).getUserName());
        }
        super.onSaveInstanceState(outState);
    }


}