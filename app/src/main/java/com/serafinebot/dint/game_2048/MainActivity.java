package com.serafinebot.dint.game_2048;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.serafinebot.dint.game_2048.touch.OnSwipeListener;
import com.serafinebot.dint.game_2048.touch.OnSwipeListenerDelegate;
import com.serafinebot.dint.game_2048.touch.SwipeDirection;
import com.serafinebot.dint.game_2048.view.CellTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSwipeListenerDelegate {
    private static final String TAG = "MainActivity";
    private static final int GRID_WIDTH = 4;
    private static final int GRID_HEIGHT = 4;
    private static final int GRID_SIZE = GRID_WIDTH * GRID_HEIGHT;
    private static final int START_PERCENT_SMALL = 70;
    private int[] grid = new int[GRID_SIZE];
    private final int[] sum = new int[GRID_SIZE];
    private int[] previous = null;
    private final List<CellTextView> cells = new ArrayList<>();

    private String user = null;
    private TextView title = null;
    private TextView counter = null;
    private Button undo_button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.title = findViewById(R.id.title);
        this.counter = findViewById(R.id.counter);
        this.undo_button = findViewById(R.id.back_button);

        /*Intent intent = getIntent();
        this.user = intent.getStringExtra(LoginActivity.USER_EXTRA);
        this.title.setText(String.format("%s: 0", this.user));*/

        GridLayout gameLayout = findViewById(R.id.grid);
        new OnSwipeListener(this, this, gameLayout);
        for (int i = 0; i < gameLayout.getChildCount(); i++) {
            View child = gameLayout.getChildAt(i);
            if (child instanceof CellTextView) {
                CellTextView cell = (CellTextView) child;
                this.cells.add(cell);
            }
        }
        addRandom();
        updateGrid();
    }

    public void undoPressed(@NonNull View view) {
        if (this.previous != null) {
            this.grid = this.previous.clone();
            this.previous = null;
            updateGrid();
        } else {
            Toast.makeText(this, "Unable to undo", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isFilled() {
        for (int i = 0; i < GRID_SIZE; i++) if (this.grid[i] == 0) return false;
        return true;
    }

    public void addRandom() {
        if (isFilled()) return;
        int random = (int) (Math.random() * GRID_SIZE);
        for (int i = random; i < GRID_SIZE + random; i++) {
            int index = i % GRID_SIZE;
            int val = this.grid[index];
            if (val == 0) {
                int chance = (int) (Math.random() * 100);
                int num = chance <= START_PERCENT_SMALL ? 2 : 4;
                this.grid[index] = num;
                break;
            }
        }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    public void updateGrid() {
        int sum = 0;
        for (int i = 0; i < this.cells.size(); i++) {
            CellTextView cell = this.cells.get(i);
            int val = this.grid[i];
            cell.setText(val == 0 ? "" : String.valueOf(val));
            sum += val;
        }
        this.counter.setText(String.valueOf(sum));
        int color = this.previous == null ? R.color.undo_disabled : R.color.undo_enabled;
        this.undo_button.setBackgroundTintList(getResources().getColorStateList(color));
    }

    public boolean inBounds(int val, int x, int y) {
        return val >= x && val <= y;
    }

    public int getVal(int x, int y) {
        return getVal(x, y, this.grid);
    }

    public int getVal(int x, int y, int[] matrix) {
        if (!inBounds(x, 0, GRID_WIDTH - 1) || !inBounds(y, 0, GRID_HEIGHT - 1)) return 0;
        return matrix[(y * GRID_WIDTH) + x];
    }

    public void setVal(int x, int y, int val) {
        setVal(x, y, this.grid, val);
    }

    public void setVal(int x, int y, int[] matrix, int val) {
        if (!inBounds(x, 0, GRID_WIDTH - 1) || !inBounds(y, 0, GRID_HEIGHT - 1)) return;
        matrix[(y * GRID_WIDTH) + x] = val;
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
            if (!(inBounds(nextX, 0, GRID_WIDTH - 1) && inBounds(nextY, 0, GRID_HEIGHT - 1))) break;
            int val = getVal(x, y);
            int nextSum = getVal(nextX, nextY, this.sum);
            int next = getVal(nextX, nextY);
            if (next == 0) {
                move(x, y, nextX, nextY);
                x = nextX;
                y = nextY;
            } else if (next == val && nextSum == 0) {
                int sum = next + val;
                setVal(nextX, nextY, sum);
                setVal(nextX, nextY, this.sum, 1);
                setVal(x, y, 0);
            } else {
                break;
            }
        } while (inBounds(nextX, 0, GRID_WIDTH - 1) && inBounds(nextY, 0, GRID_HEIGHT - 1));
    }

    public boolean getCondition(int add, int current, int end) {
        return add > 0 ? current <= end : current >= end;
    }

    public boolean equal(int[] v1, int[] v2) {
        if (v1 == null || v2 == null) return false;
        if (v1.length != v2.length) return false;
        for (int i = 0; i < v1.length; i++)
            if (v1[i] != v2[i]) return false;
        return true;
    }

    @Override
    public void didSwipe(SwipeDirection direction) {
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
        Arrays.fill(this.sum, 0);
        int[] tmp = this.grid.clone();
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
        addRandom();
        if (!equal(tmp, this.grid))
            this.previous = tmp;
        updateGrid();
    }
}
