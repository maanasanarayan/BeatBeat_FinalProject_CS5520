package edu.neu.madcourse.beatbeat_team22;

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

import java.util.ArrayList;
import java.util.logging.Level;

public class LevelSelector extends AppCompatActivity {

    private ArrayList<LevelSelectorItem> levelSelectorList;

    private RecyclerView recyclerView;
    private LevelSeletorAdaptor adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CardView cardView;
    //boolean levelEnabled = true;
    private int currLevel;

    private MainChallengeActivity mainChallengeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);

        createLevelSelectorList();
        buildRecyclerView();
    }

    public void createLevelSelectorList() {
        levelSelectorList = new ArrayList<>();
        levelSelectorList.add(new LevelSelectorItem(R.drawable.ic_baseline_music_note_24, "Level 1", "If I had a Quarter", true));
        levelSelectorList.add(new LevelSelectorItem(R.drawable.ic_baseline_music_note_24, "Level 2", "The Quarter Rest", false));
        levelSelectorList.add(new LevelSelectorItem(R.drawable.ic_baseline_music_note_24, "Level 3", "Mixin' It Up", false));
        levelSelectorList.add(new LevelSelectorItem(R.drawable.ic_baseline_music_note_24, "Level 4", "Give It a Rest", false));
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
                unlockLevel(position, view);
            }

        });
    }



    public void unlockLevel(int position, View view) {

        int nextPosition = position + 1;

        if (levelSelectorList.get(position).getLevelUnlocked()){

            view.setBackgroundColor(Color.GREEN);
            levelUnlocked(position);

            // if (levelCompleted)
            levelSelectorList.get(nextPosition).setLevelUnlocked();

        }
    }

    public void levelUnlocked (int position) {
        Intent intent = new Intent(LevelSelector.this, MainChallengeActivity.class);
        intent.putExtra("level", levelSelectorList.get(position).getmLevel());
        startActivity(intent);
    }


}