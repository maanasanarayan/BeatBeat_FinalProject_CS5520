package edu.neu.madcourse.beatbeat_team22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class GlossaryActivity extends AppCompatActivity {

    List<LessonItem> allLessons;
//    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";
//    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";

    private RecyclerView rView;
    private GlossaryAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        initializeData(savedInstanceState);
        createRecyclerView();
    }

    private void createRecyclerView() {
        rView = findViewById(R.id.GlossaryRecycler);
        rView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new GlossaryAdapter(allLessons);

        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GlossaryAdapter.OnItemClickerListener() {
            @Override
            public void onItemClick(int position) {
                cardView = findViewById(R.id.lessonCardView);
                changeItem(position, "clicked", cardView);
                Intent intent = new Intent(GlossaryActivity.this, LessonActivity.class);
                intent.putExtra("title", allLessons.get(position).getmTitle());
                startActivity(intent);
            }
        });
    }

    public void changeItem(int position, String text, CardView cardView) {
        allLessons.get(position).changeBodyText(text);
        cardView.setCardBackgroundColor(Color.BLACK);
        adapter.notifyItemChanged(position);
    }

    private void initializeData(Bundle savedInstanceState) {
        allLessons = new ArrayList<LessonItem>();
        allLessons.add(new LessonItem(getString(R.string.quarter_note_title),
                R.drawable.quarter_note, ""));
        allLessons.add(new LessonItem(getString(R.string.quarter_rest_title),
                R.drawable.quarter_rest_90, ""));
    }

}