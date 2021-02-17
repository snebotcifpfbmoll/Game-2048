package com.serafinebot.dint.game_2048.scores;

import android.content.Intent;
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

public class ScoresActivity extends AppCompatActivity implements RecyclerViewClickListener {
    private ScoresAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_layout);
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
        Toast.makeText(this, "pos: " + position, Toast.LENGTH_LONG).show();
        Score score = this.adapter.getScore(position);
        Intent intent = new Intent(this, ScoresDetailActivity.class);
        intent.putExtra(ScoresDetailActivity.ID_KEY, score.id);
        startActivity(intent);
    }
}
