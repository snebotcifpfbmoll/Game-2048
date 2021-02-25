package com.serafinebot.dint.game_2048;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 3000;

    private TextView counter = null;
    private ValueAnimator counterAnimator = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        this.counter = findViewById(R.id.counter_text);

        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            this.counter.clearAnimation();
            this.counterAnimator.end();
            startActivity(i);
            finish();
        }, SPLASH_TIME_OUT);
        startAnimation();
    }

    private void startAnimation() {
        this.counter.post(() -> {
            final int cx = this.counter.getMeasuredWidth() / 2;
            final int cy = this.counter.getMeasuredHeight() / 2;
            Animator roundedAnimation = ViewAnimationUtils.createCircularReveal(this.counter, cx, cy, 0, this.counter.getWidth() + 100);
            roundedAnimation.setInterpolator(new AccelerateInterpolator());
            roundedAnimation.setDuration(1000);

            this.counterAnimator = ValueAnimator.ofInt(0, 2048);
            this.counterAnimator.setDuration(2500);
            this.counterAnimator.addUpdateListener((animator) -> {
                try {
                    int value = Integer.parseInt(animator.getAnimatedValue().toString());
                    this.counter.setText(String.valueOf(value));
                } catch (Exception ignored) {
                }
            });

            roundedAnimation.start();
            this.counterAnimator.start();
        });
    }
}
