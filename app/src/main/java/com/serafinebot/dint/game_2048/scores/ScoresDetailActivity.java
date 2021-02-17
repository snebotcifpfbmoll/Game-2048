package com.serafinebot.dint.game_2048.scores;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.serafinebot.dint.game_2048.R;
import com.serafinebot.dint.game_2048.data.Score;
import com.serafinebot.dint.game_2048.data.ScoreHelper;

import java.util.Objects;

public class ScoresDetailActivity extends AppCompatActivity {
    public static final String ID_KEY = "id";

    private final ScoreHelper scoreHelper = new ScoreHelper(this);
    private TextView scoreView;
    private TextView playerView;
    private TextView dateView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_detail_layout);

        this.scoreView = findViewById(R.id.detail_score);
        this.playerView = findViewById(R.id.detail_player);
        this.dateView = findViewById(R.id.detail_date);

        long id = Objects.requireNonNull(getIntent().getExtras()).getLong(ID_KEY);
        Score score = this.scoreHelper.get(id);

        this.scoreView.setText(String.valueOf(score.score));
        this.playerView.setText(score.player);
        this.dateView.setText(score.date);
    }
}
