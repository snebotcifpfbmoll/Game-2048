package com.serafinebot.dint.game_2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    public static String USER_EXTRA = "user";

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.username = findViewById(R.id.username);
        this.password = findViewById(R.id.password);
    }

    public void login(@NonNull View view) {
        String user = String.valueOf(this.username.getText());
        String password = String.valueOf(this.password.getText());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USER_EXTRA, user);
        startActivity(intent);
    }
}
