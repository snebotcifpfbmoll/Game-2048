package com.serafinebot.dint.game_2048.view;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ButtonCycle extends androidx.appcompat.widget.AppCompatButton {
    private List<String> cycles = new ArrayList<>();

    public ButtonCycle(@NonNull Context context) {
        super(context);
    }

    public int getIndex() {
        String text = String.valueOf(this.getText());
        return this.cycles.indexOf(text);
    }

    public void cycle() {
        int index = getIndex();
    }
}
