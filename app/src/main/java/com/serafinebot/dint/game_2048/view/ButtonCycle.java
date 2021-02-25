package com.serafinebot.dint.game_2048.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ButtonCycle extends androidx.appcompat.widget.AppCompatButton {
    private List<String> cycles = new ArrayList<>();

    public List<String> getCycles() {
        return cycles;
    }

    public void setCycles(List<String> cycles) {
        this.cycles = cycles;
    }

    public ButtonCycle(@NonNull Context context) {
        super(context);
    }

    public ButtonCycle(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonCycle(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addCycle(String cycle) {
        this.cycles.add(cycle);
    }

    public void removeCycle(String cycle) {
        this.cycles.remove(cycle);
    }

    public int getIndex() {
        String text = String.valueOf(this.getText());
        return this.cycles.indexOf(text);
    }

    public void cycle() {
        int index = getIndex() + 1;
        String text = this.cycles.get(index % this.cycles.size());
        setText(text);
    }
}
