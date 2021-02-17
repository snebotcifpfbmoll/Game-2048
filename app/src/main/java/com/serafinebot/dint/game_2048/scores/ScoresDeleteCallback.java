package com.serafinebot.dint.game_2048.scores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.serafinebot.dint.game_2048.R;

public class ScoresDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private final Context context;
    private final ScoresAdapter adapter;
    private final Drawable icon;
    private final Drawable background;

    @SuppressLint("UseCompatLoadingForDrawables")
    public ScoresDeleteCallback(ScoresAdapter adapter) {
        super(0, ItemTouchHelper.LEFT);
        this.adapter = adapter;
        this.context = adapter.getContext();
        this.icon = ContextCompat.getDrawable(this.adapter.getContext(), R.drawable.delete_white);
        this.background = ContextCompat.getDrawable(this.context, R.drawable.rounded_corners);
        if (this.background != null)
            this.background.setTint(Color.RED);
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

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;

        float backgroundCornerOffset = this.context.getResources().getDimension(R.dimen.corner_radius);
        this.background.setBounds((int) (itemView.getRight() + dX - backgroundCornerOffset * 2), itemView.getTop(), itemView.getRight(), itemView.getBottom());

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();
        int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
        int iconRight = itemView.getRight() - iconMargin;
        this.icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

        this.background.draw(c);
        this.icon.draw(c);
    }
}
