package com.serafinebot.dint.game_2048.scores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.serafinebot.dint.game_2048.R;
import com.serafinebot.dint.game_2048.data.Score;
import com.serafinebot.dint.game_2048.data.ScoreHelper;

import java.util.ArrayList;
import java.util.List;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ScoresViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ScoreHelper scoreHelper;
    private List<Score> scores;
    private RecyclerViewClickListener listener;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public ScoresAdapter(Context context, RecyclerViewClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.scoreHelper = new ScoreHelper(this.context);

        List<Score> scores = new ArrayList<>();
        for (int i = 0; i < 30; i++)
            scores.add(new Score(i, "test " + i));

        this.scores = scoreHelper.getAll();
        if (this.scores.size() == 0) {
            scores.forEach(score -> {
                scoreHelper.add(score);
            });
            this.scores = scoreHelper.getAll();
        }
    }

    public Score getScore(int position) {
        if (this.scores == null || position >= this.scores.size()) return null;
        return this.scores.get(position);
    }

    @NonNull
    @Override
    public ScoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.score_element, parent, false);
        return new ScoresViewHolder(view, this, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoresViewHolder holder, int position) {
        Score score = this.scores.get(position);
        holder.score_result.setText(String.valueOf(score.score));
        holder.player_result.setText(String.valueOf(score.player));
    }

    @Override
    public int getItemCount() {
        return this.scores.size();
    }

    public void delete(int position) {
        if (position >= this.scores.size()) return;
        new AlertDialog.Builder(this.context)
                .setTitle("Delete Score")
                .setMessage("Are you sure you want to delete this score?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    this.scores.remove(position);
                    notifyItemRemoved(position);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    notifyItemChanged(position);
                })
                .show();
    }

    static class ScoresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "ScoresViewHolder";
        private RecyclerViewClickListener listener;
        private ScoresAdapter adapter;
        public TextView score_result;
        public TextView player_result;

        public ScoresViewHolder(@NonNull View itemView, ScoresAdapter adapter, RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            this.score_result = itemView.findViewById(R.id.score_result);
            this.player_result = itemView.findViewById(R.id.player_result);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.listener.recyclerViewClicked(v, getAdapterPosition());
        }
    }
}
