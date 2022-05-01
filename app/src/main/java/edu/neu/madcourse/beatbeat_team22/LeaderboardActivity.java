package edu.neu.madcourse.beatbeat_team22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity {

    //LeaderboardUser user;
    List<LeaderboardCard> allUsers = new ArrayList<>();
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";

    private RecyclerView rView;
    private LeaderboardAdapter LeaderboardAdapter;
    private RecyclerView.LayoutManager layoutManager;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        //username = (String) getIntent().getSerializableExtra("username");

        database = FirebaseDatabase.getInstance().getReference();
        database = database.child("Users");

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        initializeData(savedInstanceState);
        createRecyclerView();
    }

    private void createRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        rView = findViewById(R.id.LeaderboardRecycler);

        LeaderboardAdapter = new LeaderboardAdapter(this, allUsers);

        LeaderboardCardClickListener clkListener = new LeaderboardCardClickListener() {
            @Override
            public void onItemClick(int position) {
                LeaderboardAdapter.notifyItemChanged(position);
            }
        };

        LeaderboardAdapter.setLinkListener(clkListener);
        rView.setAdapter(LeaderboardAdapter);
        rView.setLayoutManager(layoutManager);
        //rView.setHasFixedSize(true);

        //Pull data from database
        //String testuser = database.child("Users").child("LeaderboardTest1").get().getResult().child("username").getValue(String.class);
        //Query testquery = database.equalTo("LeaderboardTest1");
        //String testuser = "";
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> usernames = new ArrayList<String>();
                List<Integer> levels = new ArrayList<Integer>();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //Getting the data from snapshot
                    DataSnapshot user = postSnapshot;
                    DataSnapshot level = user.child("levelPassed");
                    if (level.getValue() != null) {
                        String username = (String)user.getKey();
                        long levelPassed = (long)level.getValue();
                        usernames.add(username);
                        levels.add((int)levelPassed);
                        //allUsers.add(new LeaderboardCard(username + " Levels Passed: " + levelPassed));
                    }
                }

                int prevmax = 9999;
                List<String> sortedUsers = new ArrayList<String>();
                for (int i = 0; i < levels.size(); i++) {
                    int maxLevel = 0;
                    String maxUser = "";
                    for (int j = 0; j < levels.size(); j++) {
                        if (levels.get(j) > maxLevel && levels.get(j) <= prevmax) {
                            if (!sortedUsers.contains(usernames.get(j) + " Levels Passed: " + levels.get(j))) {
                                maxLevel = levels.get(j);
                                maxUser = usernames.get(j);
                            }
                        }
                    }
                    sortedUsers.add(maxUser + " Levels Passed: " + maxLevel);
                    prevmax = maxLevel;
                }

                for (int i = 0; i < sortedUsers.size(); i++) {
                    int position = i+1;
                    allUsers.add(new LeaderboardCard(position + ".  " + sortedUsers.get(i)));
                }

                LeaderboardAdapter.notifyDataSetChanged();
                rView.setAdapter(LeaderboardAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initializeData(Bundle savedInstanceState) {

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