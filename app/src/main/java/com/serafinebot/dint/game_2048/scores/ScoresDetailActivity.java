package com.serafinebot.dint.game_2048.scores;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.serafinebot.dint.game_2048.R;
import com.serafinebot.dint.game_2048.data.Score;
import com.serafinebot.dint.game_2048.data.ScoreHelper;

import java.util.Objects;

public class ScoresDetailActivity extends AppCompatActivity {
    public static final String ID_KEY = "id";

    private final ScoreHelper scoreHelper = new ScoreHelper(this);
    private long id = 0;
    private Button editButton;
    private TextView scoreView;
    private EditText playerView;
    private TextView dateView;
    private boolean editing = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_detail_layout);

        this.editButton = findViewById(R.id.detail_edit_button);
        this.scoreView = findViewById(R.id.detail_score);
        this.playerView = findViewById(R.id.detail_player);
        this.dateView = findViewById(R.id.detail_date);

        this.id = Objects.requireNonNull(getIntent().getExtras()).getLong(ID_KEY);
        Score score = this.scoreHelper.get(this.id);

        this.scoreView.setText(String.valueOf(score.score));
        this.playerView.setText(score.player);
        this.dateView.setText(score.date);
    }

    public void editButtonPressed(@NonNull View view) {
        this.editing = !this.editing;
        final String editText = this.editing ? getString(R.string.detail_done) : getString(R.string.detail_edit);
        this.editButton.setText(editText);
        this.playerView.setEnabled(this.editing);
        this.playerView.setFocusable(this.editing);
        this.playerView.setCursorVisible(this.editing);
        this.playerView.setInputType(this.editing ? InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS : InputType.TYPE_NULL);
        if (!this.editing) {
            Score data = this.scoreHelper.get(this.id);
            data.player = this.playerView.getText().toString();
            this.scoreHelper.update(this.id, data);
        }
    }
}
