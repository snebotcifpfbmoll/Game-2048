package com.serafinebot.dint.game_2048;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serafinebot.dint.game_2048.data.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ScoresViewHolder> {
    private LayoutInflater inflater;
    private List<Score> scores = new ArrayList<>();

    public ScoresAdapter(Context context, List<Score> scores) {
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
        String text = this.scores.get(position).toString();
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return this.scores.size();
    }

    class ScoresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "ScoresViewHolder";
        private ScoresAdapter adapter;
        public TextView textView;

        public ScoresViewHolder(@NonNull View itemView, ScoresAdapter adapter) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.test);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: ");
        }
    }
}
