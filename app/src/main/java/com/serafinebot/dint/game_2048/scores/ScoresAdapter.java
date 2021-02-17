package com.serafinebot.dint.game_2048.scores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.serafinebot.dint.game_2048.R;
import com.serafinebot.dint.game_2048.data.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ScoresViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Score> scores = new ArrayList<>();

    public ScoresAdapter(Context context, List<Score> scores) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.scores = scores;
    }

    @NonNull
    @Override
    public ScoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.score_element, parent, false);
        return new ScoresViewHolder(view, this);
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
        private ScoresAdapter adapter;
        public TextView score_result;
        public TextView player_result;

        public ScoresViewHolder(@NonNull View itemView, ScoresAdapter adapter) {
            super(itemView);
            this.score_result = itemView.findViewById(R.id.score_result);
            this.player_result = itemView.findViewById(R.id.player_result);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: ");
        }
    }
}
