package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class LevelSelector extends AppCompatActivity {

    private ArrayList<LevelSelectorItem> levelSelectorList;

    private RecyclerView recyclerView;
    private LevelSeletorAdaptor adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);

        createLevelSelectorList();
        buildRecyclerView();
    }

    public void createLevelSelectorList() {
        levelSelectorList = new ArrayList<>();
        levelSelectorList.add(new LevelSelectorItem(R.drawable.ic_twotone_music_note_24, "Level 1", "sub title"));
        levelSelectorList.add(new LevelSelectorItem(R.drawable.ic_baseline_music_note_24, "Level 2", "sub title"));
        levelSelectorList.add(new LevelSelectorItem(R.drawable.ic_baseline_music_note_24, "Level 3", "sub title"));
    }

    public void changeItem(int position, String text) {
        levelSelectorList.get(position).changeText1(text);
        adapter.notifyItemChanged(position);
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
            public void onItemClick(int position) {
                changeItem(position, "clicked");
            }
        });
    }

//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.level1:
//                //openlevel  activity
//                break;
//            case R.id.level2:
//                //openlevel  activity
//                break;
//            case R.id.level3:
//                //openlevel  activity
//                break;
//            case R.id.level4:
//                //openlevel  activity
//                break;
//            case R.id.level5:
//                //openlevel  activity
//                break;
//            case R.id.level6:
//                //openlevel  activity
//                break;
//            case R.id.level7:
//                //openlevel  activity
//                break;
//            case R.id.level8:
//                //openlevel  activity
//                break;
//            case R.id.level9:
//                //openlevel  activity
//                break;
//            case R.id.level10:
//                //openlevel  activity
//                break;
//        }
//    }
}