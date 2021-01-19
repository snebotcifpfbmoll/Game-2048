package com.serafinebot.dint.game_1024;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.serafinebot.dint.game_1024.touch.OnSwipeListener;
import com.serafinebot.dint.game_1024.touch.OnSwipeListenerDelegate;
import com.serafinebot.dint.game_1024.touch.SwipeDirection;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSwipeListenerDelegate {
    private static final String TAG = "MainActivity";
    private static final int GRID_WIDTH = 4;
    private static final int GRID_HEIGHT = 4;
    private final int[] grid = new int[GRID_HEIGHT * GRID_WIDTH];
    private final List<CellTextView> cells = new ArrayList<>();
    private GridLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.gameLayout = findViewById(R.id.gameLayout);
        OnSwipeListener onSwipeListener = new OnSwipeListener(this, this, this.gameLayout);
        for (int i = 0; i < this.gameLayout.getChildCount(); i++) {
            View child = this.gameLayout.getChildAt(i);
            if (child instanceof CellTextView) {
                CellTextView cell = (CellTextView) child;
                cells.add(cell);
            }
        }

        // post setup
        grid[2] = 9;
        grid[3] = 1;
        grid[6] = 2;
        grid[9] = 3;
        grid[12] = 4;
        updateGrid();
    }

    public void updateGrid() {
        for (int i = 0; i < this.cells.size(); i++) {
            CellTextView cell = this.cells.get(i);
            int val = this.grid[i];
            cell.setText(val == 0 ? "" : String.valueOf(val));
        }
    }

    public boolean inBounds(int val, int x, int y) {
        return val >= x && val <= y;
    }

    public int getVal(int x, int y) {
        if (!inBounds(x, 0, GRID_WIDTH - 1) || !inBounds(y, 0, GRID_HEIGHT - 1)) return 0;
        return this.grid[(y * GRID_WIDTH) + x];
    }

    public void setVal(int x, int y, int val) {
        if (!inBounds(x, 0, GRID_WIDTH - 1) || !inBounds(y, 0, GRID_HEIGHT - 1)) return;
        this.grid[(y * GRID_WIDTH) + x] = val;
    }

    public void move(int srcX, int srcY, int dstX, int dstY) {
        if (!inBounds(srcX, 0, GRID_WIDTH - 1) || !inBounds(srcY, 0, GRID_HEIGHT - 1) ||
                !inBounds(srcX, 0, GRID_WIDTH - 1) || !inBounds(srcY, 0, GRID_HEIGHT - 1)) return;
        int val = getVal(srcX, srcY);
        setVal(dstX, dstY, val);
        setVal(srcX, srcY, 0);
    }

    public void makeMove(int x, int y, final int xInc, final int yInc) {
        int nextX = x;
        int nextY = y;
        do {
            nextX += xInc;
            nextY += yInc;
            int next = getVal( nextX, nextY);
            if (next == 0 && (inBounds(nextX, 0, GRID_WIDTH - 1) && inBounds(nextY, 0, GRID_HEIGHT - 1))) {
                move(x, y, nextX, nextY);
                x = nextX;
                y = nextY;
            } else {
                break;
            }
        } while (inBounds(nextX, 0, GRID_WIDTH - 1) && inBounds(nextY, 0, GRID_HEIGHT - 1));
    }

    @Override
    public void didSwipe(SwipeDirection direction) {
        Toast.makeText(this, direction.name(), Toast.LENGTH_SHORT).show();
        int xinc = 0;
        int yinc = 0;
        switch (direction) {
            case LEFT:
                xinc = -1;
                break;
            case RIGHT:
                xinc = 1;
                break;
            case TOP:
                yinc = -1;
                break;
            case BOTTOM:
                yinc = 1;
                break;
        }
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                int val = getVal(x, y);
                if (val == 0) continue;
                makeMove(x, y, xinc, yinc);
            }
        }
        updateGrid();
    }
}
