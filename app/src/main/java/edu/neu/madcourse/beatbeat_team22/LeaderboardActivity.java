package edu.neu.madcourse.beatbeat_team22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    LeaderboardUser user;
    List<LeaderboardCard> allUsers = new ArrayList<>();
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";

    private RecyclerView rView;
    private LeaderboardAdapter LeaderboardAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //FirebaseDatabase firebaseDatabase;
    //DatabaseReference database;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        //username = (String) getIntent().getSerializableExtra("username");

        //firebaseDatabase = FirebaseDatabase.getInstance("https://assignment7-4edd6-default-rtdb.firebaseio.com/");
        //database = firebaseDatabase.getReference().child("Users").child(username);

        //user = (User) getIntent().getSerializableExtra("user_details");

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference ref = database.getReference("Users");

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        initializeData(savedInstanceState);
        createRecyclerView();
    }

    private void createRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        rView = findViewById(R.id.LeaderboardRecycler);
        //rView.setHasFixedSize(true);

        //Pull data from database
        //String testuser = database.child("Users").child("LeaderboardTest1").get().getResult().child("username").getValue(String.class);
        //Query testquery = database.equalTo("LeaderboardTest1");
        //String testuser = "";
        /*database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                Map<String, List<String>> map = (Map<String, List<String>>)snapshot.child("stickersReceived").getValue();
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    for (String id : entry.getValue()) {
                        Integer imageId = stickerImages.get(id);
                        allUsers.add(new LeaderboardCard(entry.getKey(),imageId));
                    }
                }
                //allUsers.add(new LeaderboardCard(datasnapshot.child("username").getValue(String.class)));
                //String value = snapshot.getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                //testuser = value;
                //allUsers.add(new LeaderboardCard(value));
                LeaderboardAdapter.notifyDataSetChanged();
                rView.setAdapter(LeaderboardAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                //Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });*/


        //allUsers.add(new LeaderboardCard(database.get().getResult().getValue(String.class)));
        //allUsers.add(new LeaderboardCard("username2"));
        //allUsers.add(new LeaderboardCard("username3"));
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
    }

    private void initializeData(Bundle savedInstanceState) {
        allUsers.add(new LeaderboardCard("Username1"));
        allUsers.add(new LeaderboardCard("Username2"));
        allUsers.add(new LeaderboardCard("Username3"));
        allUsers.add(new LeaderboardCard("Username4"));
        allUsers.add(new LeaderboardCard("Username5"));
        allUsers.add(new LeaderboardCard("Username6"));

        /*if(savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if(allUsers == null || allUsers.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);
                for (int i = 0; i < size; i++) {
//                    String linkName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
//                    String linkUrl = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
//
//                    LinkCard linkCard = new LinkCard(linkName, linkUrl);
//
//                    links.add(linkCard);
                    String userName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    LeaderboardCard receiver = new LeaderboardCard(userName);
                    allUsers.add(receiver);
                }
            }
        }*/
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