package com.serafinebot.dint.game_1024;

import android.os.Bundle;
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
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                int random = (int) (Math.random() * (100 + 1) + 0);
                if (random >= 50) setVal(x, y, random);
            }
        }
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
            int next = getVal(nextX, nextY);
            if (next == 0 && (inBounds(nextX, 0, GRID_WIDTH - 1) && inBounds(nextY, 0, GRID_HEIGHT - 1))) {
                move(x, y, nextX, nextY);
                x = nextX;
                y = nextY;
            } else {
                break;
            }
        } while (inBounds(nextX, 0, GRID_WIDTH - 1) && inBounds(nextY, 0, GRID_HEIGHT - 1));
    }

    public boolean getCondition(int add, int current, int end) {
        return add > 0 ? current <= end : current >= end;
    }

    @Override
    public void didSwipe(SwipeDirection direction) {
        Toast.makeText(this, direction.name(), Toast.LENGTH_SHORT).show();
        int xinc = 0;
        int yinc = 0;
        int startX = 0;
        int startY = 0;
        int endX = GRID_WIDTH;
        int endY = GRID_HEIGHT;
        switch (direction) {
            case LEFT:
                startX = 0;
                endX = GRID_WIDTH - 1;
                xinc = -1;
                break;
            case RIGHT:
                startX = GRID_WIDTH - 1;
                endX = 0;
                xinc = 1;
                break;
            case TOP:
                startY = 0;
                endY = GRID_HEIGHT - 1;
                yinc = -1;
                break;
            case BOTTOM:
                yinc = 1;
                startY = GRID_HEIGHT - 1;
                endY = 0;
                break;
        }
        int xadd = 1;
        int yadd = 1;
        if (Math.min(startX, endX) == endX) xadd = -1;
        if (Math.min(startY, endY) == endY) yadd = -1;
        for (int y = startY; getCondition(yadd, y, endY); y += yadd) {
            for (int x = startX; getCondition(xadd, x, endX); x += xadd) {
                int val = getVal(x, y);
                if (val == 0) continue;
                makeMove(x, y, xinc, yinc);
            }
        }
        updateGrid();
    }
}
