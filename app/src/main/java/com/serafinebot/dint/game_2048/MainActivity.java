package com.serafinebot.dint.game_2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    public void play(@NonNull View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void scores(@NonNull View view) {
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }
}
