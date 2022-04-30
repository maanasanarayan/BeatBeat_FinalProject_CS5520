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
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);

        createLevelSelectorList();
        buildRecyclerView();


        Intent intent = getIntent();
        userName = intent.getStringExtra(LoginActivity.EXTRA_USERNAME);
        Log.d(TAG, "user name in levelselector oncreate: " + userName);

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
                completeLevel(position, view);
            }
        });
    }



    public void completeLevel(int position, View view) {


        Log.d(TAG, "user name: " + userName);
        readLevelDB(userName);


        Log.d(TAG, "completeLevel: " + currLevel);

        int level = levelSelectorList.get(position).getmLevel();



        if (true) {
            openLevelActivity(position);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Sorry, you have not yet unlocked level" + level, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void openLevelActivity (int position) {
        Intent intent = new Intent(LevelSelector.this, MainChallengeActivity.class);
        intent.putExtra("level", levelSelectorList.get(position).getmLevel());
        startActivity(intent);
    }

    public void readLevelDB (String userName) {
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        dbRef.child(userName).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().exists()) {

                        Toast.makeText(LevelSelector.this, "Read data successfully", Toast.LENGTH_SHORT).show();

                        DataSnapshot snapshot = task.getResult();
                        String level = String.valueOf(snapshot.child("levelPassed").getValue());
                        currLevel = Integer.parseInt(level);
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