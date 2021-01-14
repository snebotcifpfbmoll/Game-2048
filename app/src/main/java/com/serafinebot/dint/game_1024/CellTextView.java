package com.serafinebot.dint.game_1024;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CellTextView extends androidx.appcompat.widget.AppCompatTextView {
    public CellTextView(@NonNull Context context) {
        super(context);
        setup();
    }

    public CellTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public CellTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public void setup() {
        this.setTextSize(24);
        this.setGravity(Gravity.CENTER);
        this.setBackgroundColor(Color.LTGRAY);
        this.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }
}
