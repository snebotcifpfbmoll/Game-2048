package com.serafinebot.dint.game_1024;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.serafinebot.dint.game_1024.touch.OnSwipeListener;
import com.serafinebot.dint.game_1024.touch.OnSwipeListenerDelegate;
import com.serafinebot.dint.game_1024.touch.SwipeDirection;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSwipeListenerDelegate {
    private static final String TAG = "MainActivity";
    private static final int COL_COUNT = 4;
    private static final int ROW_COUNT = 4;
    private GridLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.gameLayout = findViewById(R.id.gameLayout);
        OnSwipeListener onSwipeListener = new OnSwipeListener(this, this, this.gameLayout);
        List<TextView> cells = new ArrayList<>();
        for (int i = 0; i < this.gameLayout.getChildCount(); i++) {
            View child = this.gameLayout.getChildAt(i);
            if (child instanceof TextView) {
                TextView textView = (TextView) child;
                textView.setText(String.format("%d", i));
                textView.setTextSize(24);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    void setup() {
        GridLayout gridLayout = findViewById(R.id.gameLayout);
        gridLayout.setBackgroundColor(Color.GREEN);
        for (int i = 0; i < COL_COUNT; i++) {
            for (int j = 0; j < ROW_COUNT; j++) {
                TextView textView = new TextView(this);
                textView.setText(R.string.item);
                textView.setTextSize(24);
                textView.setBackgroundColor(Color.RED);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setPadding(10, 10, 10, 10);
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                textView.setMinWidth(100);
                textView.setMinHeight(100);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 100;
                params.height = 100;
                params.topMargin = 5;
                params.leftMargin = 5;
                params.rightMargin = 5;
                params.bottomMargin = 5;
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1.f);
                params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1.f);
                params.setGravity(Gravity.CENTER);

                textView.setLayoutParams(params);
                gridLayout.addView(textView);
            }
        }
    }

    @Override
    public void didSwipe(SwipeDirection direction) {
        Toast.makeText(this, direction.name(), Toast.LENGTH_SHORT).show();
    }
}
