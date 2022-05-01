package edu.neu.madcourse.beatbeat_team22;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.logging.Level;

import edu.neu.madcourse.beatbeat_team22.model.User;

public class LevelSelector extends AppCompatActivity {

    DatabaseReference dbRef;

    private ArrayList<LevelSelectorItem> levelSelectorList;

    private RecyclerView recyclerView;
    private LevelSeletorAdaptor adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Integer currLevel;
    private User user;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);

        createLevelSelectorList();
        buildRecyclerView();

        Intent intent = getIntent();
        if (intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            Log.d("User details level selector on create","user: " + user);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void createLevelSelectorList() {
        levelSelectorList = new ArrayList<>();
        levelSelectorList.add(new LevelSelectorItem(R.drawable.ic_baseline_music_note_24, "Level 1", "If I had a Quarter"));
        levelSelectorList.add(new LevelSelectorItem(R.drawable.ic_baseline_music_note_24, "Level 2", "The Quarter Rest"));
        levelSelectorList.add(new LevelSelectorItem(R.drawable.ic_baseline_music_note_24, "Level 3", "Mixin' It Up"));
        levelSelectorList.add(new LevelSelectorItem(R.drawable.ic_baseline_music_note_24, "Level 4", "Give It a Rest"));
    }

    public ArrayList<LevelSelectorItem> returnList() {
        return levelSelectorList;
    }



    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new LevelSeletorAdaptor(levelSelectorList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new LevelSeletorAdaptor.OnItemClickerListener() {
            @Override
            public void onItemClick(int position, View view) {
                readLevelDB(position, view);
            }
        });
    }



    public void openLevelActivity (int position) {
        Intent intent = new Intent(LevelSelector.this, MainChallengeActivity.class);
        intent.putExtra("level", levelSelectorList.get(position).getmLevel());
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void readLevelDB (int position, View view) {

        dbRef = FirebaseDatabase.getInstance().getReference();
        username = user.getUsername();

        Log.d(TAG, "readLevelDB user name: " + username);
        Log.d(TAG, "readLevelDB child user status: " + dbRef.child("Users").child(username));


        dbRef.child("Users").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().exists()) {

                        DataSnapshot snapshot = task.getResult();
                        String level;
                        if(snapshot.child("levelPassed").exists()) {
                            level = String.valueOf(snapshot.child("levelPassed").getValue());
                            currLevel = Integer.parseInt(level);
                        } else {
                            currLevel = 1;
                        }

                        int openLevel = levelSelectorList.get(position).getmLevel();

                        Log.d(TAG, "completeLevel currLevel: " + currLevel);
                        if (currLevel >= openLevel) {
                            openLevelActivity(position);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Sorry, you have not yet unlocked level " + openLevel, Toast.LENGTH_SHORT);
                            toast.show();
                        }


                        Log.d(TAG, "onComplete currlevel: " + currLevel);
                    } else {
                        Toast.makeText(LevelSelector.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LevelSelector.this, "Failed to read data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}