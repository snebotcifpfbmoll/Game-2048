package com.serafinebot.dint.game_2048.scores;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serafinebot.dint.game_2048.R;
import com.serafinebot.dint.game_2048.data.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoresActivity extends AppCompatActivity {
    private final List<Score> scores = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_layout);

        for (int i = 0; i < 30; i++) {
            this.scores.add(new Score(i, "test " + i));
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ScoresAdapter adapter = new ScoresAdapter(this, this.scores);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScoresDeleteCallback callback = new ScoresDeleteCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }
}
