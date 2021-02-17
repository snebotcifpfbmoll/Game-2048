package com.serafinebot.dint.game_2048.scores;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serafinebot.dint.game_2048.R;
import com.serafinebot.dint.game_2048.data.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoresActivity extends AppCompatActivity implements RecyclerViewClickListener {
    private ScoresAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_layout);

        List<Score> scores = new ArrayList<>();
        for (int i = 0; i < 30; i++)
            scores.add(new Score(i, "test " + i));
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        this.adapter = new ScoresAdapter(this, scores, this);
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScoresDeleteCallback callback = new ScoresDeleteCallback(this.adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void recyclerViewClicked(View view, int position) {
        Toast.makeText(this, "pos: " + position, Toast.LENGTH_LONG).show();
        Score score = this.adapter.getScore(position);
        System.out.println(score.id);
    }
}
