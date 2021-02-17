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
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_detail_layout);

        long id = Objects.requireNonNull(getIntent().getExtras()).getLong(ID_KEY);
        Score score = this.scoreHelper.get(id);
        this.textView = findViewById(R.id.details);
        this.textView.setText(String.format("id: %d\nscore: %d\nplayer: %s", score.id, score.score, score.player));
    }
}