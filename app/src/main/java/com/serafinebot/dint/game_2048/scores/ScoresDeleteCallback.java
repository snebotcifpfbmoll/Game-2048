package com.serafinebot.dint.game_2048.scores;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ScoresDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private ScoresAdapter adapter;

    public ScoresDeleteCallback(ScoresAdapter adapter) {
        super(0, ItemTouchHelper.LEFT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        this.adapter.delete(position);
    }
}
