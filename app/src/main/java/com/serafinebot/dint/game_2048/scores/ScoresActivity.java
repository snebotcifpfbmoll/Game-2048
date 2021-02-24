package com.serafinebot.dint.game_2048.scores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serafinebot.dint.game_2048.R;
import com.serafinebot.dint.game_2048.data.Score;
import com.serafinebot.dint.game_2048.data.ScoreHelper;
import com.serafinebot.dint.game_2048.data.ScoreOrderBy;

import java.util.List;

public class ScoresActivity extends AppCompatActivity implements RecyclerViewClickListener {
    private ScoreHelper scoreHelper;
    private ScoresAdapter adapter;
    private EditText searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_layout);

        this.searchView = findViewById(R.id.search_view);
        this.scoreHelper = new ScoreHelper(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        this.adapter = new ScoresAdapter(this, this);
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScoresDeleteCallback callback = new ScoresDeleteCallback(this.adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void recyclerViewClicked(View view, int position) {
        Score score = this.adapter.getScore(position);
        Intent intent = new Intent(this, ScoresDetailActivity.class);
        intent.putExtra(ScoresDetailActivity.ID_KEY, score.id);
        startActivity(intent);
    }

    public void searchPressed(@NonNull View view) {
        String search = this.searchView.getText().toString();
        List<Score> scores = this.scoreHelper.searchByPlayer(search, ScoreOrderBy.DESC);
        this.adapter.setScores(scores);
        this.adapter.notifyDataSetChanged();
    }
}
