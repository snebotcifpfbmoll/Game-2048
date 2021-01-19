package com.serafinebot.dint.game_1024;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CellTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final int MIN_TEXT_SIZE = 1;
    private static final int MAX_TEXT_SIZE = 100;

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

    @SuppressLint("RestrictedApi")
    public void setup() {
        this.setMaxLines(1);
        this.setTextSize(24);
        this.setGravity(Gravity.CENTER);
        this.setBackgroundColor(Color.LTGRAY);
        this.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        this.setAutoSizeTextTypeUniformWithConfiguration(MIN_TEXT_SIZE, MAX_TEXT_SIZE, 1, TypedValue.COMPLEX_UNIT_DIP);
    }
}
