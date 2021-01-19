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
                textView.setText(String.format("%d", i * 100000));
                cells.add(textView);
            }
        }
    }

    @Override
    public void didSwipe(SwipeDirection direction) {
        Toast.makeText(this, direction.name(), Toast.LENGTH_SHORT).show();
    }
}
